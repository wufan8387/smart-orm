package org.smart.orm.operations.text;

import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.ResultData;
import org.smart.orm.functions.Func;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class QueryStatement extends AbstractStatement {
    
    private RelationNode<QueryStatement> relRoot;
    
    private RelationNode<QueryStatement> relLast;
    
    private ConditionNode<QueryStatement> whereRoot;
    
    private ConditionNode<QueryStatement> whereLast;
    
    private OrderByNode<QueryStatement> orderByRoot;
    
    private GroupByNode<QueryStatement> groupByRoot;
    
    private LimitNode<QueryStatement> limitRoot;
    
    @Override
    public StatementType getType() {
        return StatementType.DQL;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T extends SqlNode<?, ?>> void doAttach(T node) {
        
        switch (node.getType()) {
            case NodeType.RELATION:
                RelationNode<QueryStatement> relNode = (RelationNode<QueryStatement>) node;
                relRoot = relRoot == null ? relNode : relRoot;
                relLast = relNode;
                break;
            case NodeType.CONDITION:
                ConditionNode<QueryStatement> whereNode = (ConditionNode<QueryStatement>) node;
                whereRoot = whereRoot == null ? whereNode : whereRoot;
                whereLast = whereNode;
                break;
            case NodeType.GROUP_BY:
                GroupByNode<QueryStatement> groupByNode = (GroupByNode<QueryStatement>) node;
                groupByRoot = groupByRoot == null ? groupByNode : groupByRoot;
                break;
            case NodeType.ORDER_BY:
                OrderByNode<QueryStatement> orderByNode = (OrderByNode<QueryStatement>) node;
                orderByRoot = orderByRoot == null ? orderByNode : orderByRoot;
                break;
            case NodeType.LIMIT:
                LimitNode<QueryStatement> limitNode = (LimitNode<QueryStatement>) node;
                limitRoot = limitRoot == null ? limitNode : limitRoot;
                break;
        }
    }
    
    public RelationNode<QueryStatement> from(String rel) {
        
        return findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(rel, relLast).attach(this)
        );
        
    }
    
    public RelationNode<QueryStatement> from(String rel, String alias) {
        
        return findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(rel, relLast).attach(this)
        ).setAlias(alias);
        
    }
    
    public RelationNode<QueryStatement> join(String rel) {
        
        return findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(rel, relLast).attach(this)
        ).setJoinType(JoinType.INNER);
        
    }
    
    public RelationNode<QueryStatement> join(String rel, String alias) {
        return findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(rel, relLast).attach(this)
        )
                .setAlias(alias)
                .setJoinType(JoinType.INNER);
    }
    
    public AttributeNode<QueryStatement> select(String attr) {
        return new AttributeNode<QueryStatement>(attr).attach(this);
    }
    
    public ConditionNode<QueryStatement> where(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                    .attach(this);
        } else {
            return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                    .setLogicalType(LogicalType.AND)
                    .attach(this);
        }
    }
    
    public ConditionNode<QueryStatement> where(String rel, String attr, Func<String> op, Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(rel, attr, op, this.whereLast, params).attach(this);
        } else {
            return new ConditionNode<>(rel, attr, op, this.whereLast, params)
                    .setLogicalType(LogicalType.AND)
                    .attach(this);
        }
    }
    
    
    public ConditionNode<QueryStatement> and(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public ConditionNode<QueryStatement> and(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public ConditionNode<QueryStatement> or(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    public ConditionNode<QueryStatement> or(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    public AttributeNode<QueryStatement> select(String attr, String alias) {
        return new AttributeNode<QueryStatement>(attr, alias).attach(this);
        
    }
    
    public AttributeNode<QueryStatement> select(SqlNode<QueryStatement, ?> attr, String alias) {
        return new AttributeNode<>(attr, alias).attach(this);
    }
    
    public OrderByNode<QueryStatement> orderBy(String rel, String attr) {
        if (orderByRoot == null) {
            new OrderByNode<>().attach(this);
        }
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public OrderByNode<QueryStatement> orderByDesc(String rel, String attr) {
        if (orderByRoot == null) {
            new OrderByNode<>().attach(this);
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    public GroupByNode<QueryStatement> groupBy(String rel, String attr) {
        if (groupByRoot == null) {
            new GroupByNode<>().attach(this);
        }
        groupByRoot.add(rel, attr);
        return groupByRoot;
    }
    
    
    public LimitNode<QueryStatement> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<QueryStatement>().attach(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public QueryStatement limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<QueryStatement>().attach(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    @Override
    public ResultData execute(Connection connection, Executor executor) {
        return null;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        this.getParams().clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.SELECT);
        
        List<AttributeNode<QueryStatement>> attrList = getNodes()
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<QueryStatement>) t)
                .collect(Collectors.toList());
        
        int attrSize = attrList.size();
        if (attrSize > 0) {
            for (int i = 0; i < attrSize; i++) {
                SqlNode<?, ?> node = attrList.get(i);
                node.toString(sb);
                if (i < attrSize - 1)
                    sb.append(",");
            }
        } else {
            sb.append(" * ");
        }
        
        sb.append(Token.FROM);
        
        relRoot.toString(sb);
        
        if (whereRoot != null) {
            sb.append(Token.WHERE);
            whereRoot.toString(sb);
        }
        
        if (groupByRoot != null) {
            groupByRoot.toString(sb);
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
