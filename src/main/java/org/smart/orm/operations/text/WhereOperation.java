package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.WhereType;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.operations.Exp;
import org.smart.orm.operations.Expression;
import org.smart.orm.operations.Operation;
import org.smart.orm.reflect.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WhereOperation extends AbstractOperation {
    
    protected String expression;
    
    private List<String> propList = new ArrayList<>();
    
    private List<Expression> opList = new ArrayList<>();
    
    private List<WhereType> connList = new ArrayList<>();
    
    public WhereOperation(UUID batch, OperationContext context, String table) {
        this.batch = batch;
        this.context = context;
        context.add(this);
        this.tableInfo = context.addTableIfAbsent(batch, table);
    }
    
    public WhereOperation(UUID batch, OperationContext context, String table, String property, Expression exp, Object... value) {
        this.batch = batch;
        this.context = context;
        context.add(this);
        this.tableInfo = context.addTableIfAbsent(batch, table);
        
        this.connList.add(WhereType.WHERE);
        this.propList.add(property);
        this.opList.add(exp);
        this.params.add(value);
    }
    
    
    public WhereOperation(UUID batch, OperationContext context, TableInfo tableInfo, String property, Expression exp, Object... value) {
        this.batch = batch;
        this.context = context;
        context.add(this);
        this.tableInfo = context.addTableIfAbsent(batch, tableInfo);
        
        this.connList.add(WhereType.WHERE);
        this.propList.add(property);
        this.opList.add(exp);
        this.params.add(value);
        
    }
    
    
    @Override
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
        
        int len = connList.size();
        StringBuilder sb = new StringBuilder();
        sb.append(Exp.WHERE.generate(WhereType.WHERE));
        
        for (int i = 0; i < len; i++) {
            WhereType whereType = connList.get(i);
            sb.append(Exp.WHERE.generate(whereType));
            Expression exp = opList.get(i);
            String prop = propList.get(i);
            sb.append(exp.generate(tableInfo.getAlias(), prop));
        }
        
        children.forEach(t->t.build(sb));

        
        this.expression = sb.toString();
        
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
            
            sb.append(Exp.WHERE.generate(whereType));
            Expression exp = opList.get(i);
            String prop = propList.get(i);
            sb.append(exp.generate(tableInfo.getAlias(), prop));
        }
    
        children.forEach(t->t.build(sb));
    
    
    
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.WHERE;
    }
    
    
    public WhereOperation and(String table, String property, Expression exp, Object... value) {
        WhereOperation operation = new WhereOperation(batch, context, table);
        operation.and(property, exp, value);
        return operation;
    }
    
    public WhereOperation or(String table, String property, Expression exp, Object... value) {
        WhereOperation operation = new WhereOperation(batch, context, table);
        operation.or(property, exp, value);
        return operation;
    }
    
    
    public WhereOperation and(String property, Expression exp, Object... value) {
        this.connList.add(WhereType.AND);
        this.propList.add(property);
        this.opList.add(exp);
        this.params.add(value);
        return this;
    }
    
    public WhereOperation or(String property, Expression exp, Object... value) {
        this.connList.add(WhereType.OR);
        this.propList.add(property);
        this.opList.add(exp);
        this.params.add(value);
        return this;
    }
    
    
    public LimitOperation limit(int count) {
        return new LimitOperation(this.context, count);
    }
    
    
}
