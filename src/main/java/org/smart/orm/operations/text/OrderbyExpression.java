package org.smart.orm.operations.text;

import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.OrderByInfo;
import org.smart.orm.data.OrderbyType;
import org.smart.orm.operations.AbstractExpression;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.List;

public class OrderbyExpression extends AbstractExpression {
    
    private final static String EXPRESSION_ORDERBY = " ORDER BY %s ";
    
    private final static String EXPRESSION_DESC = " DESC ";
    private final static String EXPRESSION_ASC = " ASC ";
    
    private List<OrderByInfo> orderbyList = new ArrayList<>();
    
    private EntityInfo entityInfo;
    
    
    public OrderbyExpression(Statement statement) {
    }
    
    public OrderbyExpression(Statement statement, String table) {
    }
    
    public OrderbyExpression(Statement statement, String table, String alias) {
    }
    
    public OrderbyExpression(Statement statement, TableInfo table) {
    }
    
    
    public final OrderbyExpression asc(String... properties) {
        
        for (String property : properties) {
            OrderByInfo orderByInfo = new OrderByInfo();
            orderByInfo.orderbyType = OrderbyType.ASC;
            orderByInfo.property = property;
            orderbyList.add(orderByInfo);
        }
        
        return this;
    }
    
    public final OrderbyExpression desc(String... properties) {
        
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
    public String build() {
        
        
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
        
        return sb.toString();
        
        
    }
    
    
}
