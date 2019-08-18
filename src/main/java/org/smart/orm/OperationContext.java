package org.smart.orm;

import org.smart.orm.data.NodeType;
import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.ObjectHandler;
import org.smart.orm.execution.ResultData;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.type.AttributeNode;
import org.smart.orm.operations.type.ConditionNode;
import org.smart.orm.operations.type.QueryObject;
import org.smart.orm.reflect.AssociationInfo;
import org.smart.orm.reflect.PropertyInfo;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class OperationContext {
    
    private final static Map<UUID, List<BatchItem>> statementMap = new HashMap<>();
    
    private final static Map<UUID, IncludeTree> includeMap = new HashMap<>();
    
    
    private Executor executor;
    
    private UUID batchId = UUID.randomUUID();
    
    public Executor getExecutor() {
        return executor;
    }
    
    public UUID getBatchId() {
        return batchId;
    }
    
    public void add(Model<?> model, Statement statement) {
        statementMap.putIfAbsent(batchId, new ArrayList<>());
        statementMap.get(batchId).add(new BatchItem(model, statement));
    }
    
    public void add(UUID batchId, Model<?> model, Statement statement) {
        statementMap.putIfAbsent(batchId, new ArrayList<>());
        statementMap.get(batchId).add(new BatchItem(model, statement));
    }
    
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
    
    
    public <T extends Model<T>, K extends Model<K>> void include(QueryObject<?> statement, Class<T> thisCls
            , Class<K> includeCls
            , PropertyGetter<T> getter) {
        if (!includeMap.containsKey(statement.getId())) {
            IncludeTree tree = new IncludeTree(statement);
            includeMap.put(statement.getId(), tree);
        }
        IncludeTree tree = includeMap.get(statement.getId());
        
        tree.include(thisCls, includeCls, getter);
        
    }
    
    
    public void saveChanges() {
        List<BatchItem> batchItemList = statementMap.get(batchId)
                .stream()
                .filter(t -> t.statement.getType() == StatementType.DML)
                .collect(Collectors.toList());
        for (BatchItem item : batchItemList) {
            item.statement.execute(executor);
            afterSaveChanges(item);
        }
    }
    
    
    public void saveChanges(UUID batchId) {
        List<BatchItem> batchItemList = statementMap.get(batchId)
                .stream()
                .filter(t -> t.statement.getType() == StatementType.DML && t.statement.getId() == batchId)
                .collect(Collectors.toList());
        for (BatchItem item : batchItemList) {
            item.statement.execute(executor);
            afterSaveChanges(item);
        }
    }
    
    public void load(UUID statementId) {
        
        IncludeTree tree = includeMap.get(statementId);
        
        tree.root.queryObject.execute(executor);
        
        List<Model<?>> resultList = new ArrayList<>(tree.root.queryObject.<Model<?>>getResult().all());
        
        tree.root.children.forEach(t -> load(t, resultList));
        
    }
    
    private void load(IncludeTreeNode node, List<Model<?>> resultList) {
        
        node.queryObject.execute(executor);
        
        fillAssoc(resultList, node);
        
        resultList.addAll(node.queryObject.<Model<?>>getResult().all());
        
        node.children.forEach(t -> load(t, resultList));
        
    }
    
    private void fillAssoc(List<Model<?>> dataList, IncludeTreeNode node) {
        
        
        AssociationInfo assoc = node.assoc;
        
        Class<?> thisCls = assoc.getThisEntity().getType();
        
        
        for (Model<?> thisData : dataList) {
            
            if (!thisData.getClass().equals(thisCls)) {
                continue;
            }
            
            Object thisKey = assoc.getThisKeyProp().get(thisData);
            
            ResultData<Model<?>> resultData = node.queryObject.getResult();
            
            if (assoc.get(thisData) == null) {
                
                assoc.shouldInitialize(thisData);
                
                for (Model<?> item : resultData.all()) {
                    Object otherKey = assoc.getOtherKeyProp().get(item);
                    if (thisKey.equals(otherKey)) {
                        assoc.set(thisData, item);
                        break;
                    }
                }
            }
            
        }
        
        
    }
    
    
    @SuppressWarnings("unchecked")
    private void afterSaveChanges(BatchItem item) {
        Statement statement = item.statement;
        
        ResultData<?> resultData = statement.getResult();
        Model<?> model = item.model;
        if (model != null && resultData.all().size() > 0) {
            Object[] keysData = ((List<Map<String, Object>>) resultData.all()).get(0).values().toArray();
            Model.getMetaManager().fillAutoGenerateKeys(model, keysData);
            model.getChangeMap().clear();
        }
    }
    
    
    private static class BatchItem {
        public Model<?> model;
        
        public Statement statement;
        
        public BatchItem(Model<?> model, Statement statement) {
            this.model = model;
            this.statement = statement;
        }
    }
    
    
    private static class IncludeTree {
        
        final IncludeTreeNode root;
        
        IncludeTree(QueryObject<?> statement) {
            root = new IncludeTreeNode(this, statement);
        }
        
        IncludeTreeNode find(IncludeTreeNode node, Class<?> cls) {
            
            if (node.queryObject.getRelRoot().getEntityInfo().getType() == cls) {
                return node;
            }
            
            for (IncludeTreeNode child : node.children) {
                if (child.queryObject.getRelRoot().getEntityInfo().getType() == cls) {
                    return child;
                }
                IncludeTreeNode result = find(child, cls);
                if (result != null) {
                    return result;
                }
            }
            
            return null;
            
        }
        
        
        <T extends Model<T>, K extends Model<K>> void include(Class<T> thisCls
                , Class<K> includeCls
                , PropertyGetter<T> getter) {
            
            IncludeTreeNode parent = find(root, thisCls);
            assert parent != null;
            
            
            QueryObject<K> queryObject = new QueryObject<>(includeCls);
            
            @NotNull AssociationInfo assoc = Model.getMetaManager().findAssoc(thisCls, getter);
            
            PropertyInfo thisKey = assoc.getThisKeyProp();
            PropertyInfo includeKey = assoc.getOtherKeyProp();
            
            
            queryObject.where(includeKey
                    , Op.IN
                    , () -> {
                        ResultData<Model<?>> resultData = parent.queryObject.getResult();
                        List<Object> paramList = new ArrayList<>();
                        for (Model<?> model : resultData.all()) {
                            paramList.add(thisKey.get(model));
                        }
                        return paramList.toArray();
                    });
            
            IncludeTreeNode node = new IncludeTreeNode(this, queryObject);
            node.assoc = assoc;
            parent.children.add(node);
            
        }
        
        
    }
    
    private static class IncludeTreeNode {
        
        final IncludeTree tree;
        
        final QueryObject<?> queryObject;
        
        AssociationInfo assoc;
        
        List<IncludeTreeNode> children = new ArrayList<>();
        
        IncludeTreeNode(IncludeTree tree, QueryObject<?> queryObject) {
            this.tree = tree;
            this.queryObject = queryObject;
        }
        
    }
    
}
