package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.NodeType;
import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.KeyMapHandler;
import org.smart.orm.execution.ResultData;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;
import org.smart.orm.reflect.PropertyInfo;

import java.sql.Statement;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InsertObject<T extends Model<T>> extends AbstractStatement {
    
    private boolean autoGenerateKeys = false;
    
    private RelationNode<InsertObject<T>, T> relRoot;
    
    public InsertObject(Class<T> cls) {
        relRoot = new RelationNode<InsertObject<T>, T>(cls).attach(this);
    }
    
    @Override
    public StatementType getType() {
        return StatementType.DML;
    }
    
    public boolean isAutoGenerateKeys() {
        return autoGenerateKeys;
    }
    
    public void setAutoGenerateKeys(boolean autoGenerateKeys) {
        this.autoGenerateKeys = autoGenerateKeys;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <K extends SqlNode<?, ?>> void doAttach(K node) {
    }
    
    public InsertObject<T> attributes(PropertyGetter<T>... attributes) {
        for (int i = 0; i < attributes.length; i++) {
            PropertyGetter<T> attr = attributes[i];
            new AttributeNode<>(this, attr).from(relRoot);
        }
        
        return this;
    }
    
    public InsertObject<T> attributes(PropertyInfo... attributes) {
        for (int i = 0; i < attributes.length; i++) {
            PropertyInfo attr = attributes[i];
            new AttributeNode<>(relRoot, attr).attach(this);
        }
        
        return this;
    }
    
    public InsertObject<T> values(Object... value) {
        new ValuesNode<InsertObject<T>>(value).attach(this);
        return this;
    }
    
    public InsertObject<T> values(Supplier<Object[]> value) {
        new ValuesNode<InsertObject<T>>(value).attach(this);
        return this;
    }
    
    @Override
    public void execute(Executor executor) {
        
        String sql = toString();
        System.out.println(sql);
        
        KeyMapHandler handler = new KeyMapHandler();
        
        List<Object> params = getParams();
        
        int keyFlag = autoGenerateKeys
                ? Statement.RETURN_GENERATED_KEYS
                : Statement.NO_GENERATED_KEYS;
        
        int cnt = executor.insert(sql, handler, keyFlag, params.toArray());
        
        
        if (autoGenerateKeys) {
            setResult(new ResultData<>(cnt, handler.getAll()));
        } else {
            setResult(new ResultData<>(cnt));
        }
        
        
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
