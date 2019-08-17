package org.smart.orm.operations.text;

import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.ResultData;
import org.smart.orm.functions.Func;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;

import java.util.List;

public class DeleteStatement extends AbstractStatement {
    
    
    private ConditionNode<DeleteStatement> whereRoot;
    
    private ConditionNode<DeleteStatement> whereLast;
    
    private LimitNode<DeleteStatement> limitRoot;
    
    private OrderByNode<DeleteStatement> orderByRoot;
    
    private final RelationNode<DeleteStatement> relRoot;
    
    public DeleteStatement(String rel) {
        relRoot = new RelationNode<DeleteStatement>(rel).attach(this);
    }
    
    public DeleteStatement(String rel, String alias) {
        relRoot = new RelationNode<DeleteStatement>(rel).setAlias(alias).attach(this);
    }
    
    @Override
    public StatementType getType() {
        return StatementType.DML;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T extends SqlNode<?, ?>> void doAttach(T node) {
        
        switch (node.getType()) {
            case NodeType.LIMIT:
                LimitNode<DeleteStatement> limitNode = (LimitNode<DeleteStatement>) node;
                limitRoot = limitRoot == null ? limitNode : limitRoot;
                break;
            case NodeType.ORDER_BY:
                OrderByNode<DeleteStatement> orderByNode = (OrderByNode<DeleteStatement>) node;
                orderByRoot = orderByRoot == null ? orderByNode : orderByRoot;
                break;
            case NodeType.CONDITION:
                ConditionNode<DeleteStatement> whereNode = (ConditionNode<DeleteStatement>) node;
                whereRoot = whereRoot == null ? whereNode : whereRoot;
                whereLast = whereNode;
                break;
        }
    }
    
    public ConditionNode<DeleteStatement> where(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                    .attach(this);
        } else {
            return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                    .setLogicalType(LogicalType.AND)
                    .attach(this);
        }
    }
    
    public ConditionNode<DeleteStatement> where(String rel, String attr, Func<String> op, Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(rel, attr, op, this.whereLast, params).attach(this);
        } else {
            return new ConditionNode<>(rel, attr, op, this.whereLast, params)
                    .setLogicalType(LogicalType.AND)
                    .attach(this);
        }
    }
    
    
    public ConditionNode<DeleteStatement> and(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public ConditionNode<DeleteStatement> and(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public ConditionNode<DeleteStatement> or(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    public ConditionNode<DeleteStatement> or(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    
    public LimitNode<DeleteStatement> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<DeleteStatement>().attach(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public DeleteStatement limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<DeleteStatement>().attach(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    public OrderByNode<DeleteStatement> orderBy(String rel, String attr) {
        if (orderByRoot == null) {
            new OrderByNode<DeleteStatement>().attach(this);
        }
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public OrderByNode<DeleteStatement> orderByDesc(String rel, String attr) {
        if (orderByRoot == null) {
            new OrderByNode<DeleteStatement>().attach(this);
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    @Override
    public ResultData execute(Executor executor) {
        String sql = toString();
        List<Object> params = this.getParams();
        System.out.println(sql);
        int cnt = executor.delete(sql, params.toArray());
        return new ResultData<>(cnt);
    }
    
    @Override
    public String toString() {
        this.getParams().clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.DEL_FROM_AS.apply(relRoot.getName(), relRoot.getAlias()));
        
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
