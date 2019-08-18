package org.smart.orm.operations.text;


import org.smart.orm.data.NodeType;
import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.KeyMapHandler;
import org.smart.orm.execution.ResultData;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;

import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InsertStatement extends AbstractStatement {
    
    private boolean returnGeneratedKEeyS;
    
    private RelationNode<InsertStatement> relRoot;
    
    public InsertStatement(String rel) {
        relRoot = new RelationNode<InsertStatement>(rel).attach(this);
    }
    
    public boolean isReturnGeneratedKEeyS() {
        return returnGeneratedKEeyS;
    }
    
    public void setReturnGeneratedKEeyS(boolean returnGeneratedKEeyS) {
        this.returnGeneratedKEeyS = returnGeneratedKEeyS;
    }
    
    @Override
    public StatementType getType() {
        return StatementType.DML;
    }
    
    @Override
    protected <T extends SqlNode<?, ?>> void doAttach(T node) {
    }
    
    
    public InsertStatement values(Object[] params) {
        new ValuesNode<>(params).attach(this);
        return this;
    }
    
    @Override
    public void execute(Executor executor) {
        String sql = toString();
        System.out.println(sql);
        
        KeyMapHandler handler = new KeyMapHandler();
        
        
        List<Object> params = getParams();
        
        int autoGenKeys = returnGeneratedKEeyS
                ? Statement.RETURN_GENERATED_KEYS
                : Statement.NO_GENERATED_KEYS;
        executor.insert(sql, handler, autoGenKeys, params.toArray());
        
        setResult(new ResultData<>(handler.getAll()));
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        this.getParams().clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.INSERT_INTO.apply(relRoot.getName()));
        
        
        List<AttributeNode<InsertStatement>> attrList = getNodes()
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<InsertStatement>) t)
                .collect(Collectors.toList());
        
        int attrSize = attrList.size();
        if (attrSize > 0) {
            sb.append("(");
            for (int i = 0; i < attrSize; i++) {
                AttributeNode<InsertStatement> node = attrList.get(i);
                node.setOp(Token.ATTR_INSERT);
                node.toString(sb);
                if (i < attrSize - 1)
                    sb.append(",");
            }
            sb.append(")");
        }
        
        sb.append(Token.VALUES);
        
        List<ValuesNode<InsertStatement>> valuesList = getNodes()
                .stream().filter(t -> t.getType() == NodeType.VALUES)
                .map(t -> (ValuesNode<InsertStatement>) t)
                .collect(Collectors.toList());
        
        int valSize = valuesList.size();
        for (int i = 0; i < valSize; i++) {
            ValuesNode<InsertStatement> node = valuesList.get(i);
            node.toString(sb);
            if (i < valSize - 1)
                sb.append(",");
        }
        
        return sb.toString();
    }
    
    
}
