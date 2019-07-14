package org.smart.orm.operations.type;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.reflect.PropertyGetter;

import java.util.UUID;

public class FromOperation<T extends Model<T>> extends AbstractOperation {
    
    
    private final static String EXPRESSION = " FROM `%s` ";
    
    private final static String EXPRESSION_AS = " FROM `%s` AS `%S` ";
    
    private String expression;
    
    public FromOperation(UUID batch, OperationContext context) {
        this.batch = batch;
        this.context = context;
        context.add(this);
        this.tableInfo = T.getMeta(this.getClass()).getTable();
    }
    
    
    public FromOperation(UUID batch, OperationContext context, String alias) {
        this.batch = batch;
        this.context = context;
        context.add(this);
        this.tableInfo = T.getMeta(this.getClass()).getTable();
        this.tableInfo.setAlias(alias);
    }
    
    
    public SelectOperation<T> select() {
        return new SelectOperation<>(this.batch, this.context);
    }
    
    public final SelectOperation<T> select(PropertyGetter<T> property, String alias) {
        SelectOperation<T> operation = new SelectOperation<>(this.batch, this.context);
        operation.column(property, alias);
        return operation;
    }
    
    @SafeVarargs
    public final SelectOperation<T> select(PropertyGetter<T>... properties) {
        SelectOperation<T> operation = new SelectOperation<>(this.batch, this.context);
        operation.columns(properties);
        return operation;
    }
    
    
    public FromOperation<T> join(JoinOperation operation) {
        context.add(operation);
        return this;
    }
    
    public WhereOperation<T> where(WhereOperation<T> operation) {
        operation.setContext(this.context);
        operation.setBatch(batch);
        this.context.add(operation);
        return operation;
    }
    
    public LimitOperation limit(int count) {
        return new LimitOperation(this.context, count);
    }
    
    public FromOperation<T> orderBy(OrderbyOperation operation) {
        operation.setContext(this.context);
        operation.setBatch(batch);
        this.context.add(operation);
        return this;
    }

//    @SafeVarargs
//    public final FromOperation<T> orderby(OrderbyType orderbyType, PropertyGetter<T>... properties) {
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
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
        if (StringUtils.isEmpty(tableInfo.getAlias())) {
            expression = String.format(EXPRESSION, tableInfo.getName());
        } else {
            expression = String.format(EXPRESSION_AS, tableInfo.getName(), tableInfo.getAlias());
        }
    }
    
}