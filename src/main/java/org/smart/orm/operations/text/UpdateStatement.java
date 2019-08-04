package org.smart.orm.operations.text;


import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.ResultData;
import org.smart.orm.functions.Func;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;

import java.sql.Connection;

public class UpdateStatement extends AbstractStatement {
    
    
    private ConditionNode<UpdateStatement> whereRoot;
    
    private ConditionNode<UpdateStatement> whereLast;
    
    private LimitNode<UpdateStatement> limitRoot;
    
    private OrderByNode<UpdateStatement> orderByRoot;
    
    private final RelationNode<UpdateStatement> relRoot;
    
    private AssignNode<UpdateStatement> setRoot;
    
    
    public UpdateStatement(String rel) {
        relRoot = new RelationNode<UpdateStatement>(rel).attach(this);
    }
    
    
    public UpdateStatement(String rel, String alias) {
        relRoot = new RelationNode<UpdateStatement>(rel)
                .setAlias(alias)
                .attach(this);
    }
    
    @Override
    public StatementType getType() {
        return StatementType.DML;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T extends SqlNode<?, ?>> void doAttach(T node) {
        
        switch (node.getType()) {
            case NodeType.CONDITION:
                ConditionNode<UpdateStatement> whereNode = (ConditionNode<UpdateStatement>) node;
                whereRoot = whereRoot == null ? whereNode : whereRoot;
                whereLast = whereNode;
                break;
            case NodeType.LIMIT:
                LimitNode<UpdateStatement> limitNode = (LimitNode<UpdateStatement>) node;
                limitRoot = limitRoot == null ? limitNode : limitRoot;
                break;
            case NodeType.ORDER_BY:
                OrderByNode<UpdateStatement> orderByNode = (OrderByNode<UpdateStatement>) node;
                orderByRoot = orderByRoot == null ? orderByNode : orderByRoot;
                break;
            
        }
    }
    
    
    public ConditionNode<UpdateStatement> where(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                    .attach(this);
        } else {
            return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                    .setLogicalType(LogicalType.AND)
                    .attach(this);
        }
    }
    
    public ConditionNode<UpdateStatement> where(String rel, String attr, Func<String> op, Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(rel, attr, op, this.whereLast, params)
                    .attach(this);
        } else {
            return new ConditionNode<>(rel, attr, op, this.whereLast, params)
                    .setLogicalType(LogicalType.AND)
                    .attach(this);
        }
    }
    
    
    public ConditionNode<UpdateStatement> and(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public ConditionNode<UpdateStatement> and(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public ConditionNode<UpdateStatement> or(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    public ConditionNode<UpdateStatement> or(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    
    public LimitNode<UpdateStatement> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<UpdateStatement>().attach(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public UpdateStatement limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<UpdateStatement>().attach(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    public OrderByNode<UpdateStatement> orderBy(String rel, String attr) {
        if (orderByRoot == null) {
            new OrderByNode<UpdateStatement>().attach(this);
        }
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public OrderByNode<UpdateStatement> orderByDesc(String rel, String attr) {
        if (orderByRoot == null) {
            new OrderByNode<UpdateStatement>().attach(this);
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    
    public AssignNode<UpdateStatement> set(String attr, Object value) {
        if (setRoot == null)
            setRoot = new AssignNode<UpdateStatement>().attach(this);
        setRoot.assign(attr, value);
        return setRoot;
    }
    
    @Override
    public ResultData execute(Connection connection, Executor executor) {
        return null;
    }
    
    @Override
    public String toString() {
        this.getParams().clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.UPDATE_AS_SET.apply(relRoot.getName(), relRoot.getAlias()));
        
        setRoot.toString(sb);
        
        
        if (whereRoot != null) {
            sb.append(Token.WHERE);
            whereRoot.toString(sb);
        }
        
        if (orderByRoot != null) {
            orderByRoot.toString(sb);
        }
        
        if (limitRoot != null) {
            limitRoot.toString(sb);
        }
        
        return sb.toString();
    }
}
