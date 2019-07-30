package org.smart.orm.operations.text;

import org.smart.orm.Func;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;

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
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends SqlNode<?>> T attach(T node) {
        
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
        nodeList.add(node);
        return node;
    }
    
    public RelationNode<QueryStatement> from(String rel) {
        
        return findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(this, rel, relLast)
        );
        
    }
    
    public RelationNode<QueryStatement> from(String rel, String alias) {
        
        return findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(this, rel, relLast)
        ).setAlias(alias);
        
    }
    
    public RelationNode<QueryStatement> join(String rel) {
        
        return findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(this, rel, relLast)
        ).setJoinType(JoinType.INNER);
        
    }
    
    public RelationNode<QueryStatement> join(String rel, String alias) {
        return findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(this, rel, relLast)
        )
                .setAlias(alias)
                .setJoinType(JoinType.INNER);
    }
    
    public AttributeNode<QueryStatement> select(String attr) {
        return new AttributeNode<>(this, attr);
    }
    
    public ConditionNode<QueryStatement> where(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast);
        } else {
            return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                    .setLogicalType(LogicalType.AND);
        }
    }
    
    public ConditionNode<QueryStatement> where(String rel, String attr, Func<String> op, Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, rel, attr, op, this.whereLast, params);
        } else {
            return new ConditionNode<>(this, rel, attr, op, this.whereLast, params)
                    .setLogicalType(LogicalType.AND);
        }
    }
    
    
    public ConditionNode<QueryStatement> and(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.AND);
    }
    
    public ConditionNode<QueryStatement> and(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND);
    }
    
    public ConditionNode<QueryStatement> or(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.OR);
    }
    
    public ConditionNode<QueryStatement> or(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR);
    }
    
    public AttributeNode<QueryStatement> select(String attr, String alias) {
        AttributeNode<QueryStatement> node = new AttributeNode<>(this, attr, alias);
        return node;
    }
    
    public AttributeNode<QueryStatement> select(SqlNode<QueryStatement> attr, String alias) {
        return new AttributeNode<>(this, attr, alias);
    }
    
    public OrderByNode<QueryStatement> orderBy(String rel, String attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public OrderByNode<QueryStatement> orderByDesc(String rel, String attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    public GroupByNode<QueryStatement> groupBy(String rel, String attr) {
        if (groupByRoot == null) {
            attach(new GroupByNode<>(this));
        }
        groupByRoot.add(rel, attr);
        return groupByRoot;
    }
    
    
    public LimitNode<QueryStatement> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public QueryStatement limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        this.paramList.clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.SELECT);
        
        List<AttributeNode<QueryStatement>> attrList = nodeList
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<QueryStatement>) t)
                .collect(Collectors.toList());
        
        int attrSize = attrList.size();
        if (attrSize > 0) {
            for (int i = 0; i < attrSize; i++) {
                SqlNode<?> node = attrList.get(i);
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
