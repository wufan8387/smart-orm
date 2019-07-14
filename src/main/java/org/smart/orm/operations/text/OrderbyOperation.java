package org.smart.orm.operations.text;

import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.OrderByInfo;
import org.smart.orm.data.OrderbyType;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.List;

public class OrderbyOperation extends AbstractOperation {
    
    private final static String EXPRESSION_ORDERBY = " ORDER BY %s ";
    
    private final static String EXPRESSION_DESC = " DESC ";
    private final static String EXPRESSION_ASC = " ASC ";
    
    private List<OrderByInfo> orderbyList = new ArrayList<>();
    
    private String expression;
    
    private EntityInfo entityInfo;
    
    
    public OrderbyOperation(OperationContext context) {
    }
    
    public OrderbyOperation(OperationContext context, String table) {
    }
    
    public OrderbyOperation(OperationContext context, String table, String alias) {
    }
    
    public OrderbyOperation(OperationContext context, TableInfo table) {
    }
    
    
    public final OrderbyOperation asc(String... properties) {
        
        for (String property : properties) {
            OrderByInfo orderByInfo = new OrderByInfo();
            orderByInfo.orderbyType = OrderbyType.ASC;
            orderByInfo.property = property;
            orderbyList.add(orderByInfo);
        }
        
        return this;
    }
    
    public final OrderbyOperation desc(String... properties) {
        
        for (String property : properties) {
            OrderByInfo orderByInfo = new OrderByInfo();
            orderByInfo.orderbyType = OrderbyType.DESC;
            orderByInfo.property = property;
            orderbyList.add(orderByInfo);
        }
        
        return this;
    }
    
    
    @Override
    public int getPriority() {
        return OperationPriority.ORDERBY;
    }
    
    @Override
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
        
        
        StringBuilder sb = new StringBuilder();

//        if (orderbyList.size() > 0) {
//            orderbySb.append(" order by ");
//            Model.getMetaMap()
//
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
        
        expression = sb.toString();
        
        
    }
    
    
}
