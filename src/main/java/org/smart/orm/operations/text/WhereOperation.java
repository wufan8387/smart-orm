package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.WhereType;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.operations.Exp;
import org.smart.orm.operations.Expression;
import org.smart.orm.reflect.*;

import java.util.ArrayList;
import java.util.List;

public class WhereOperation extends AbstractOperation {
    
    protected String expression;
    
    private List<String> propList = new ArrayList<>();
    
    private List<Expression> opList = new ArrayList<>();
    
    private List<WhereType> connList = new ArrayList<>();
    
    public WhereOperation() {
    }
    
    public WhereOperation(WhereType whereType, String property, Expression op, Object... value) {
        this.connList.add(whereType);
        this.propList.add(property);
        this.opList.add(op);
        this.paramList.add(value);
    }
    
    public WhereOperation(String table, String property, Expression op, Object... value) {
        this.tableInfo = new TableInfo(table);
        this.connList.add(WhereType.NONE);
        this.propList.add(property);
        this.opList.add(op);
        this.params.add(value);
    }
    
    
    public WhereOperation(TableInfo tableInfo, String property, Expression op, Object... value) {
        this.tableInfo = tableInfo;
        this.connList.add(WhereType.NONE);
        this.propList.add(property);
        this.opList.add(op);
        this.params.add(value);
    }
    
    public WhereOperation(WhereType whereType, TableInfo tableInfo, String property, Expression op, Object... value) {
        this.tableInfo = tableInfo;
        this.connList.add(whereType);
        this.propList.add(property);
        this.opList.add(op);
        this.params.add(value);
    }
    
    public WhereOperation(WhereType whereType, String table, String property, Expression op, Object... value) {
        this.tableInfo = new TableInfo(table);
        this.connList.add(whereType);
        this.propList.add(property);
        this.opList.add(op);
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
        
        this.expression = sb.toString();
        
    }
    
    
    @Override
    public int getPriority() {
        if (whereType == WhereType.NONE)
            return OperationPriority.WHERE;
        return OperationPriority.WHERE_N;
    }
    
    
    public WhereOperation andFor(String table, String property, Expression exp, Object... value) {
        WhereOperation operation = new WhereOperation(WhereType.AND, table, property, exp, value);
        operation.setBatch(batch);
        this.connList.add(operation);
        return operation;
    }
    
    public WhereOperation orFor(String table, String property, Expression exp, Object... value) {
        WhereOperation operation = new WhereOperation(WhereType.OR, table, property, exp, value);
        operation.setBatch(batch);
        this.connList.add(operation);
        return operation;
    }
    
    public WhereOperation and(String property, Expression exp, Object... value) {
        this.connList.add(WhereType.AND)
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

//    private String whereText(WhereType whereType) {
//        switch (whereType) {
//            case WHERE:
//                return " WHERE ";
//            case AND:
//                return " AND ";
//            case OR:
//                return " OR ";
//        }
//        return StringUtils.EMPTY;
//    }
    
    
}
