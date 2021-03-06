package org.smart.orm.operations;


import org.apache.commons.lang3.StringUtils;
import org.smart.orm.data.LogicalType;
import org.smart.orm.functions.Func;

public class Op {
    
    
    private final static String TEXT_EQUAL_PARAMS = " `%s`.`%s` = ? ";
    private final static String TEXT_EQUAL_ATTR = " `%s`.`%s` = `%s`.`%s` ";
    
    private final static String TEXT_GREAT_THAN_PARAMS = " `%s`.`%s` > ? ";
    private final static String TEXT_GREAT_THAN_ATTR = " `%s`.`%s` > `%s`.`%s` ";
    
    private final static String TEXT_GREAT_THAN_EQUAL_PARAMS = " `%s`.`%s` >=? ";
    private final static String TEXT_GREAT_THAN_EQUAL_ATTR = " `%s`.`%s` >= `%s`.`%s` ";
    
    private final static String TEXT_LESS_THAN_PARAMS = " `%s`.`%s` < ? ";
    private final static String TEXT_LESS_THAN_ATTR = " `%s`.`%s` < `%s`.`%s` ";
    
    private final static String TEXT_LESS_THAN_EQUAL_PARAMS = " `%s`.`%s` <= ? ";
    private final static String TEXT_LESS_THAN_EQUAL_ATTR = " `%s`.`%s` <= `%s`.`%s` ";
    
    private final static String TEXT_NOT_EQUAL_PARAMS = " `%s`.`%s` <>  ?  ";
    private final static String TEXT_NOT_EQUAL_ATTR = " `%s`.`%s` <> `%s`.`%s` ";
    
    
    private final static String TEXT_BETWEEN = " `%s`.`%s` between ? and ? ";
    
    private final static String TEXT_EXISTS = " `%s`.`%s` EXISTS ";
    
    private final static String TEXT_IN = " `%s`.`%s` IN  ";
    private final static String TEXT_IS_NULL = " `%s`.`%s` IS NULL";
    
    private final static String TEXT_LIKE = " `%s`.`%s` LIKE ? ";
    private final static String TEXT_NOT_EXISTS = " `%s`.`%s` NOT EXISTS ";
    private final static String TEXT_NOT_IN = " `%s`.`%s` NOT IN ";
    private final static String TEXT_NOT_nULL = " `%s`.`%s` IS NOT nULL ";
    
    
    public final static Func<String> EQUAL = t -> {
        if (t.length == 3)
            return String.format(TEXT_EQUAL_PARAMS, t);
        return String.format(TEXT_EQUAL_ATTR, t);
    };
    
    public final static Func<String> GREATER_THAN = t -> {
        if (t.length == 3)
            return String.format(TEXT_GREAT_THAN_PARAMS, t);
        return String.format(TEXT_GREAT_THAN_ATTR, t[0], t);
    };
    
    public final static Func<String> GREATER_THAN_EQUAL = t -> {
        if (t.length == 3)
            return String.format(TEXT_GREAT_THAN_EQUAL_PARAMS, t);
        return String.format(TEXT_GREAT_THAN_EQUAL_ATTR, t);
    };
    
    public final static Func<String> LESS_THAN = t -> {
        if (t.length == 3)
            return String.format(TEXT_LESS_THAN_PARAMS, t);
        return String.format(TEXT_LESS_THAN_ATTR, t);
    };
    
    public final static Func<String> LESS_THAN_EQUAL = t -> {
        if (t.length == 3)
            return String.format(TEXT_LESS_THAN_EQUAL_PARAMS, t);
        return String.format(TEXT_LESS_THAN_EQUAL_ATTR, t);
    };
    
    public final static Func<String> NOT_EQUAL = t -> {
        if (t.length == 3)
            return String.format(TEXT_NOT_EQUAL_PARAMS, t);
        return String.format(TEXT_NOT_EQUAL_ATTR, t);
    };
    
    
    public final static Func<String> BETWEEN = t -> String.format(TEXT_BETWEEN, t);
    
    
    public final static Func<String> EXISTS = t -> {
        if (t.length == 2) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder(String.format(TEXT_EXISTS, t));
        buildArrayParams(sb, ((Object[]) t[2]).length);
        return sb.toString();
    };
    
    
    public final static Func<String> IN = t -> {
        if (t.length == 2) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder(String.format(TEXT_IN, t));
        buildArrayParams(sb, ((Object[]) t[2]).length);
        return sb.toString();
    };
    
    public final static Func<String> NOT_EXISTS = t -> {
        if (t.length == 2) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder(String.format(TEXT_NOT_EXISTS, t));
        buildArrayParams(sb, ((Object[]) t[2]).length);
        return sb.toString();
        
    };
    
    public final static Func<String> NOT_IN = t -> {
        if (t.length == 2) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder(String.format(TEXT_NOT_IN, t));
        buildArrayParams(sb, ((Object[]) t[2]).length);
        return sb.toString();
    };
    
    
    private static void buildArrayParams(StringBuilder sb, int len) {
        sb.append(" ( ");
        for (int i = 0; i < len; i++) {
            sb.append(" ? ");
            if (i < len - 1)
                sb.append(" , ");
        }
        sb.append(" ) ");
    }
    
    
    public final static Func<String> IS_NULL = t -> String.format(TEXT_IS_NULL, t);
    
    
    public final static Func<String> LIKE = t -> String.format(TEXT_LIKE, t);
    
    
    public final static Func<String> NOT_nULL = t -> String.format(TEXT_NOT_nULL, t);
    
    public final static Func<String> LOGICAL = t -> {
        
        if (t[0] == null)
            return StringUtils.EMPTY;
        
        LogicalType logicalType = (LogicalType) t[0];
        switch (logicalType) {
            case AND:
                return " AND ";
            case OR:
                return " OR ";
        }
        return StringUtils.EMPTY;
        
    };
    
}
