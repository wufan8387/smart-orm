package org.smart.orm.operations.text;

import org.smart.orm.data.JoinType;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.*;
import org.smart.orm.reflect.TableInfo;

public class FromNode extends AbstractExpression {
    
    
    
    private final static String EXPRESSION = " FROM `%s` AS `%S` ";
    
    
    public FromNode(Statement statement) {
        this.statement = statement;
        statement.add(this);
    }
    
    public FromNode(Statement statement, String table) {
        this.statement = statement;
        statement.add(this);
        this.tableInfo = statement.getTable(table);
    }
    
    public FromNode(Statement statement, String table, String alias) {
        this.statement = statement;
        statement.add(this);
        this.tableInfo = statement.getTable(table, alias);
    }
    
    public FromNode(Statement statement, TableInfo tableInfo) {
        this.statement = statement;
        statement.add(this);
        this.tableInfo = statement.getTable(tableInfo);
    }
    
    public SelectExpression select() {
        return new SelectExpression(this.statement, this.tableInfo);
    }
    
    
    public SelectExpression select(String... properties) {
        SelectExpression expression = new SelectExpression(this.statement, this.tableInfo);
        expression.columns(properties);
        return expression;
    }
    
    public FromNode join(JoinExpression expression) {
        statement.add(expression);
        return this;
    }
    
    public WhereExpression where(String property, Formatter exp, Object... value) {
        return new WhereExpression(statement, tableInfo, property, exp, value);
    }
    
    public WhereExpression where(String table, String property, Formatter exp, Object... value) {
        return new WhereExpression(statement, table, property, exp, value);
    }
    
    
    public LimitExpression limit(int count) {
        return new LimitExpression(this.statement, count);
    }
    
    public FromNode orderBy(OrderbyExpression expression) {
        expression.setStatement(this.statement);
        statement.add(expression);
        return this;
    }
    
    public FromNode join(JoinType joinType, String table, SqlNode on) {
        
        return this;
    }
    
    public FromNode join(JoinType joinType, String table, String alias, SqlNode on) {
        return this;
    }


//    @SafeVarargs
//    public final FromNode<T> orderby(OrderbyType orderbyType, PropertyGetter<T>... properties) {
//
//        for (PropertyGetter<T> property : properties) {
//            OrderByInfo orderByInfo = new OrderByInfo();
//            orderByInfo.orderbyType = orderbyType;
//            Field field = LambdaParser.getGetter(property);
//            orderByInfo.property = field.getName();
//            orderbyList.add(orderByInfo);
//        }
//
//        return this;
//    }
    
    // private String buildSql(Class cls) {
    
    //     // EntityInfo entityInfo = Model.getMetaMap().get(cls.getName());
    //     // Map<String, PropertyInfo> propertyMap = entityInfo.getPropertyMap();
    //     // StringBuilder columnsSb = new StringBuilder();
    //     // if (columnList.size() > 0) {
    //     // columnList.forEach(t -> {
    //     // String column = propertyMap.get(t.property).getColumn().name();
    //     // if (StringUtils.isNotEmpty(t.alias)) {
    //     // columnsSb.append(String.format(" `%s` as `%s`, ", column, t.alias));
    //     // } else {
    //     // columnsSb.append(String.format(" `%s`, ", column));
    //     // }
    //     //
    //     // });
    //     // columnsSb.delete(columnsSb.length() - 2, columnsSb.length());
    //     // } else {
    //     // columnsSb.append(" * ");
    //     // }
    //     //
    //     //
    //     // List<Object> paramList = new ArrayList<>();
    //     //
    //     // StringBuilder whereSb = new StringBuilder();
    //     // if (whereList.size() > 0) {
    //     // whereSb.append(" where ");
    //     // whereList.forEach(t -> {
    //     // t.build();
    //     // whereSb.append(String.format(" %s, ", t.getExpression()));
    //     // });
    //     // }
    //     //
    //     // StringBuilder orderbySb = new StringBuilder();
    //     //
    //     // if (orderbyList.size() > 0) {
    //     // orderbySb.append(" order by ");
    //     // orderbyList.forEach(t -> {
    //     // String column = propertyMap.get(t).getColumn().name();
    //     // switch (t.orderbyType) {
    //     // case ASC:
    //     // orderbySb.append(String.format(" `%s`, ", column));
    //     // break;
    //     // case DESC:
    //     // orderbySb.append(String.format(" `%s` desc, ", column));
    //     // break;
    //     // }
    //     //
    //     // orderbySb.delete(orderbySb.length() - 2, orderbySb.length());
    //     //
    //     // });
    //     // }
    //     //
    //     // String tableName = entityInfo.getTable().name();
    //     //
    //     // String select = String.format("select %s from `%s` %s"
    //     // , columnsSb.toString()
    //     // , tableName
    //     // , whereSb.toString()
    //     // , orderbySb.toString());
    //     //
    //     // return select;
    
    //     return null;
    // }
    
    
    @Override
    public int getPriority() {
        return OperationPriority.FROM;
    }
    
    @Override
    public String build() {
        return String.format(EXPRESSION, tableInfo.getName(), tableInfo.getAlias());
    }
    
}
