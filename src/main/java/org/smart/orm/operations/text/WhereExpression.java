package org.smart.orm.operations.text;

import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.WhereType;
import org.smart.orm.operations.*;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.List;

public class WhereExpression extends AbstractExpression {
    
    
    private List<Column> propList = new ArrayList<>();
    
    private List<Formatter> opList = new ArrayList<>();
    
    private List<WhereType> connList = new ArrayList<>();
    
    public WhereExpression() {
    }
    
    
    public WhereExpression(Statement statement, String table) {
        this.statement = statement;
        statement.add(this);
        this.tableInfo = statement.getTable(table);
    }
    
    public WhereExpression(Statement statement, String table, String property, Formatter exp, Object... value) {
        this.statement = statement;
        statement.add(this);
        this.tableInfo = statement.getTable(table);
        
        this.connList.add(WhereType.WHERE);
        this.propList.add(new Column(property));
        this.opList.add(exp);
        this.params.add(value);
    }
    
    
    public WhereExpression(Statement statement, TableInfo table, String property, Formatter exp, Object... value) {
        this.statement = statement;
        statement.add(this);
        this.tableInfo = statement.getTable(table);
        
        this.connList.add(WhereType.WHERE);
        this.propList.add(new Column(property));
        this.opList.add(exp);
        this.params.add(value);
        
    }
    
    
    @Override
    public int getPriority() {
        return OperationPriority.WHERE;
    }
    
    
    public WhereExpression and(String table, String property, Formatter exp, Object... value) {
        WhereExpression operation = new WhereExpression(statement, table);
        operation.and(new Column(property), exp, value);
        return operation;
    }
    
    public WhereExpression or(String table, String property, Formatter exp, Object... value) {
        WhereExpression operation = new WhereExpression(statement, table);
        operation.or(new Column(property), exp, value);
        return operation;
    }
    
    public WhereExpression and(String property, Formatter exp, Object... value) {
        this.connList.add(WhereType.AND);
        this.propList.add(new Column(property));
        this.opList.add(exp);
        this.params.add(value);
        return this;
    }
    
    public WhereExpression or(String property, Formatter exp, Object... value) {
        this.connList.add(WhereType.OR);
        this.propList.add(new Column(property));
        this.opList.add(exp);
        this.params.add(value);
        return this;
    }
    
    
    public WhereExpression and(String table, Expression property, Formatter exp, Object... value) {
        WhereExpression operation = new WhereExpression(statement, table);
        operation.and(new Column(property), exp, value);
        return operation;
    }
    
    public WhereExpression or(String table, Expression property, Formatter exp, Object... value) {
        WhereExpression operation = new WhereExpression(statement, table);
        operation.or(new Column(property), exp, value);
        return operation;
    }
    
    public WhereExpression and(Column property, Formatter exp, Object... value) {
        this.connList.add(WhereType.AND);
        this.propList.add(property);
        this.opList.add(exp);
        this.params.add(value);
        return this;
    }
    
    public WhereExpression or(Column property, Formatter exp, Object... value) {
        this.connList.add(WhereType.OR);
        this.propList.add(property);
        this.opList.add(exp);
        this.params.add(value);
        return this;
    }
    
    public LimitExpression limit(int count) {
        return new LimitExpression(statement, count);
    }
    
    @Override
    public String build() {
        
        int len = connList.size();
        StringBuilder sb = new StringBuilder();
        sb.append(Exp.WHERE.format(WhereType.WHERE));
        
        for (int i = 0; i < len; i++) {
            WhereType whereType = connList.get(i);
            sb.append(Exp.WHERE.format(whereType));
            Formatter exp = opList.get(i);
            String prop = propList.get(i);
            sb.append(exp.format(tableInfo.getAlias(), prop));
        }
        
        children.forEach(t -> t.build(sb));
        
        
        return sb.toString();
        
    }
    
    @Override
    public void build(StringBuilder sb) {
        
        
        int len = connList.size();
        
        for (int i = 0; i < len; i++) {
            WhereType whereType = connList.get(i);
            
            switch (whereType) {
                case NONE:
                case WHERE:
                    whereType = WhereType.AND;
                    break;
            }
            
            sb.append(Exp.WHERE.format(whereType));
            Formatter exp = opList.get(i);
            String prop = propList.get(i);
            sb.append(exp.format(tableInfo.getAlias(), prop));
        }
        
        children.forEach(t -> t.build(sb));
        
        
    }
    
}
