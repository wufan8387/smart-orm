package org.smart.orm.operations.text;

import org.smart.orm.data.JoinType;
import org.smart.orm.operations.Exp;
import org.smart.orm.operations.Formatter;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;


import java.util.ArrayList;
import java.util.List;

public class RelationNode<T extends Statement> implements SqlNode<T> {
    
    private T statement;
    
    private String name;
    
    private String alias;
    
    private List<SqlNode> conditionList = new ArrayList<>();
    
    private JoinType joinType = JoinType.NONE;
    
    private RelationNode internalNode;
    
    @Override
    public T getStatement() {
        return statement;
    }
    
    public RelationNode(T statement, String name) {
        this.statement = statement;
        this.name = name;
    }
    
    public RelationNode(T statement, String name, String alias) {
        statement.add(this);
        this.statement = statement;
        this.name = name;
        this.alias = alias;
    }
    
    public RelationNode(T statement, String name, RelationNode internalNode) {
        statement.add(this);
        this.statement = statement;
        this.name = name;
        this.internalNode = internalNode;
    }
    
    public RelationNode(T statement, String name, String alias, RelationNode internalNode) {
        statement.add(this);
        this.statement = statement;
        this.name = name;
        this.alias = alias;
        this.internalNode = internalNode;
    }
    
    @Override
    public int getType() {
        return 0;
    }
    
    @Override
    public void add(SqlNode node) {
    
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public RelationNode<T> join(String rel, String alias) {
        RelationNode<T> node = new RelationNode<>(this.statement, rel, alias, this);
        return node;
    }
    
    public ConditionNode<T> on(String leftAttr, Formatter op,, String rightAttr) {
        ConditionNode<T> node = new ConditionNode<>(this.internalNode.alias, leftAttr, op, , alias, rightAttr);
        this.conditionList.add(node);
        return node;
    }
    
    public ConditionNode<T> on(String leftRel, String leftAttr, Formatter op, String rightRel, String rightAttr) {
        ConditionNode<T> node = new ConditionNode<>(leftRel, leftAttr, op, , rightRel, rightAttr);
        this.conditionList.add(node);
        return node;
    }
    
    
    public void toString(StringBuilder sb) {
        
        if (internalNode != null) {
            internalNode.toString(sb);
        } else {
            
            String join = Exp.JOIN.format(joinType);
            
            sb.append(join);
            
            sb.append(Exp.AS.format(name, alias));
            
            if (conditionList.size() > 0) {
                sb.append(" ON ");
                conditionList.forEach(t -> sb.append(t.toString()));
                
            }
            
        }
        
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        toString(sb);
        
        return sb.toString();
    }
}
