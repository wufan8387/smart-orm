package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.OrderByInfo;
import org.smart.orm.data.OrderbyType;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyGetter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OrderbyOperation<T extends Model<T>> extends AbstractOperation {
    
    private final static String EXPRESSION_ORDERBY = " ORDER BY %s ";
    
    private final static String EXPRESSION_DESC = " DESC ";
    private final static String EXPRESSION_ASC = " ASC ";
    
    private List<OrderByInfo> orderbyList = new ArrayList<>();
    
    private String expression;
    
    private EntityInfo entityInfo;
    
    
    public OrderbyOperation() {
        this.entityInfo = T.getMeta(this.getClass());
    }
    
    @SafeVarargs
    public final OrderbyOperation<T> asc(PropertyGetter<T>... properties) {
        
        for (PropertyGetter<T> property : properties) {
            OrderByInfo orderByInfo = new OrderByInfo();
            orderByInfo.orderbyType = OrderbyType.ASC;
            Field field = LambdaParser.getGetter(property);
            orderByInfo.property = field.getName();
            orderbyList.add(orderByInfo);
        }
        
        return this;
    }
    
    @SafeVarargs
    public final OrderbyOperation<T> desc(PropertyGetter<T>... properties) {
        
        for (PropertyGetter<T> property : properties) {
            OrderByInfo orderByInfo = new OrderByInfo();
            orderByInfo.orderbyType = OrderbyType.DESC;
            Field field = LambdaParser.getGetter(property);
            orderByInfo.property = field.getName();
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
