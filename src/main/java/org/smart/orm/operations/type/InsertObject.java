package org.smart.orm.operations.type;

import com.sun.media.sound.PortMixerProvider;
import org.smart.orm.Model;
import org.smart.orm.annotations.IdType;
import org.smart.orm.data.NodeType;
import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.GeneratedKeysHandler;
import org.smart.orm.execution.ResultData;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;
import org.smart.orm.operations.text.ValuesNode;
import org.smart.orm.reflect.PropertyInfo;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

public class InsertObject<T extends Model<T>> extends AbstractStatement {
    
    private RelationNode<InsertObject<T>, ?> relRoot;
    
    
    private List<T> modelList = new ArrayList<>();
    
    public InsertObject(Class<T> cls) {
        relRoot = new RelationNode<InsertObject<T>, T>(cls).attach(this);
    }
    
    @Override
    public StatementType getType() {
        return StatementType.DML;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <K extends SqlNode<?, ?>> void doAttach(K node) {
    }
    
    public List<T> getModelList() {
        return modelList;
    }
    
    public InsertObject<T> values(Object... value) {
        new ValuesNode<InsertObject>(value).attach(this);
        return this;
    }
    
    
    @Override
    public ResultData<T> execute(Connection connection, Executor executor) {
        
        String sql = toString();
        System.out.println(sql);
        
        GeneratedKeysHandler<T> handler = new GeneratedKeysHandler<>(relRoot.getEntityInfo());
        
        handler.getAll().addAll(modelList);
        
        List<Object> params = getParams();
        
        int cnt = executor.insert(connection, sql, handler, handler.autoGenerateKeys(), params.toArray());
        
        return new ResultData<>(cnt);
        
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        this.getParams().clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.INSERT_INTO.apply(relRoot.getName()));
        
        List<AttributeNode<InsertObject<T>, ?>> attrList = getNodes()
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<InsertObject<T>, ?>) t)
                .collect(Collectors.toList());
        
        int attrSize = attrList.size();
        
        if (attrSize == 0) {
            
            List<PropertyInfo> propList = relRoot.getEntityInfo().getPropList();
            
            for (PropertyInfo prop : propList) {
                if (prop.getIdType() == IdType.Auto)
                    continue;
                AttributeNode<InsertObject<T>, ?> attrNode = new AttributeNode<>(relRoot, prop);
                attrNode.setOp(Token.ATTR_INSERT);
                attrList.add(attrNode);
                attrNode.attach(this);
            }
        }
        
        
        attrSize = attrList.size();
        
        sb.append("(");
        for (int i = 0; i < attrSize; i++) {
            AttributeNode<InsertObject<T>, ?> node = attrList.get(i);
            node.setAlias(node.getName());
            node.toString(sb);
            if (i < attrSize - 1)
                sb.append(",");
        }
        sb.append(")");
        
        
        sb.append(Token.VALUES);
        
        List<ValuesNode<InsertObject>> valuesList = getNodes()
                .stream().filter(t -> t.getType() == NodeType.VALUES)
                .map(t -> (ValuesNode<InsertObject>) t)
                .collect(Collectors.toList());
        
        int valSize = valuesList.size();
        for (int i = 0; i < valSize; i++) {
            ValuesNode<InsertObject> node = valuesList.get(i);
            node.toString(sb);
            if (i < valSize - 1)
                sb.append(",");
        }
        
        return sb.toString();
    }
    
    
}
