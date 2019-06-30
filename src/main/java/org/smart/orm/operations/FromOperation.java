package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.OrderByInfo;
import org.smart.orm.data.OrderbyType;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.TableInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FromOperation<T> implements Operation {
    
    private TableInfo tableInfo;
    
    private List<WhereOperation> whereList = new ArrayList<>();
    private List<OrderByInfo> orderbyList = new ArrayList<>();
    
    private OperationContext context;
    
    public FromOperation(OperationContext context) {
        this.context = context;
        context.add(this);
        this.tableInfo = Model.getMeta(this.getClass()).getTable();
    }
    
    public FromOperation(OperationContext context, String table) {
        this.context = context;
        context.add(this);
        this.tableInfo = new TableInfo(table);
    }
    
    public FromOperation(OperationContext context, TableInfo tableInfo) {
        this.context = context;
        context.add(this);
        this.tableInfo = tableInfo;
    }
    
    public SelectOperation<T> select() {
        return new SelectOperation<>(this.context);
    }
    
    @SafeVarargs
    public final SelectOperation<T> select(Getter<T>... properteis) {
        SelectOperation<T> operation = new SelectOperation<>(this.context);
        operation.columns(properteis);
        return operation;
    }
    
    public SelectOperation<T> select(String... properteis) {
        SelectOperation<T> operation = new SelectOperation<>(this.context);
        operation.columns(properteis);
        return operation;
    }
    
    
    public FromOperation<T> join(JoinOperation operation) {
        context.add(operation);
        return this;
    }
    
    
    public WhereOperation<T> where(WhereOperation<T> operation) {
        operation.setContext(this.context);
        this.context.add(operation);
        return operation;
    }
    
    public LimitOperation limit(int count) {
        return new LimitOperation(this.context, count);
    }
    
    public FromOperation<T> orderby(OrderbyType orderbyType, String... properties) {
        for (String property : properties) {
            OrderByInfo orderByInfo = new OrderByInfo();
            orderByInfo.orderbyType = orderbyType;
            orderByInfo.property = property;
            orderbyList.add(orderByInfo);
        }
        
        return this;
    }
    
    @SafeVarargs
    public final FromOperation<T> orderby(OrderbyType orderbyType, Getter<T>... properties) {
        
        for (Getter<T> property : properties) {
            OrderByInfo orderByInfo = new OrderByInfo();
            orderByInfo.orderbyType = orderbyType;
            Field field = LambdaParser.getGet(property);
            orderByInfo.property = field.getName();
            orderbyList.add(orderByInfo);
        }
        
        return this;
    }
    
    private String buildSql(Class cls) {


//        EntityInfo entityInfo = Model.getMetaMap().get(cls.getName());
//        Map<String, PropertyInfo> propertyMap = entityInfo.getPropertyMap();
//        StringBuilder columnsSb = new StringBuilder();
//        if (columnList.size() > 0) {
//            columnList.forEach(t -> {
//                String column = propertyMap.get(t.property).getColumn().name();
//                if (StringUtils.isNotEmpty(t.alias)) {
//                    columnsSb.append(String.format(" `%s` as `%s`, ", column, t.alias));
//                } else {
//                    columnsSb.append(String.format(" `%s`, ", column));
//                }
//
//            });
//            columnsSb.delete(columnsSb.length() - 2, columnsSb.length());
//        } else {
//            columnsSb.append(" * ");
//        }
//
//
//        List<Object> paramList = new ArrayList<>();
//
//        StringBuilder whereSb = new StringBuilder();
//        if (whereList.size() > 0) {
//            whereSb.append(" where ");
//            whereList.forEach(t -> {
//                t.build();
//                whereSb.append(String.format(" %s, ", t.getExpression()));
//            });
//        }
//
//        StringBuilder orderbySb = new StringBuilder();
//
//        if (orderbyList.size() > 0) {
//            orderbySb.append(" order by ");
//            orderbyList.forEach(t -> {
//                String column = propertyMap.get(t).getColumn().name();
//                switch (t.orderbyType) {
//                    case ASC:
//                        orderbySb.append(String.format(" `%s`, ", column));
//                        break;
//                    case DESC:
//                        orderbySb.append(String.format(" `%s` desc, ", column));
//                        break;
//                }
//
//                orderbySb.delete(orderbySb.length() - 2, orderbySb.length());
//
//            });
//        }
//
//        String tableName = entityInfo.getTable().name();
//
//        String select = String.format("select %s from `%s` %s"
//                , columnsSb.toString()
//                , tableName
//                , whereSb.toString()
//                , orderbySb.toString());
//
//        return select;
        
        return null;
    }
    
    
    @Override
    public int priority() {
        return OperationPriority.FROM;
    }
    
    @Override
    public OperationContext getContext() {
        return context;
    }
    
    public TableInfo getTableInfo() {
        return tableInfo;
    }
}
