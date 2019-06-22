package org.smart.orm.operations;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.SmartORMException;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyInfo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FromOperation<T> implements Operation {
    
    private Integer limit;
    
    private List<WhereOperation> whereList = new ArrayList<>();
    private List<OrderByInfo> orderbyList = new ArrayList<>();
    
    private OperationContext context;
    
    public FromOperation(OperationContext context) {
        this.context = context;
    }
    
    public FromOperation(OperationContext context, EntityInfo entityInfo) {
        this(context);
    }
    
    
    public FromOperation<T> include(String... properties) {
        return this;
    }
    
    public FromOperation<T> include(Getter<T>... properties) {
        return this;
    }
    
    public FromOperation<T> where(WhereOperation<T>... operations) {
        for (WhereOperation<T> operation : operations) {
            whereList.add(operation);
        }
        return this;
    }
    
    public FromOperation<T> limit(Integer count) {
        this.limit = count;
        return this;
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
    
    public FromOperation<T> orderby(OrderbyType orderbyType, Getter<T>... properties) {
        
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
    
    public T execute() {
        try {
            
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class cls = (Class) type.getActualTypeArguments()[0];
            T obj = (T) cls.newInstance();
            
            String sql = buildSql(cls);
            
            
            return obj;
            
            
        } catch (Exception e) {
            throw new SmartORMException(e);
        }
        
        
    }
    
    @Override
    public OperationContext getContext() {
        return context;
    }
    
    private static class OrderByInfo {
        
        public String property;
        
        public OrderbyType orderbyType;
    }
    
    
}
