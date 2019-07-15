package org.smart.orm.operations;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.data.WhereType;


public class Exp {
    

    
    private final static String TEXT_BETWEEN = " `%s`.`%s` between ? and ? ";
    private final static String TEXT_EQUAL = " `%s`.`%s` = ? ";
    private final static String TEXT_EXISTS = " `%s`.`%s` EXISTS ( ? ) ";
    private final static String TEXT_GREATE_THAN = " `%s`.`%s` > ? ";
    private final static String TEXT_GREATE_THAN_EQUAL = " `%s`.`%s` >=? ";
    private final static String TEXT_IN = " `%s`.`%s` IN ( ? ) ";
    private final static String TEXT_IS_NULL = " `%s`.`%s` IS NULL";
    private final static String TEXT_LESS_THAN = " `%s`.`%s` < ? ";
    private final static String TEXT_LESS_THAN_EQUAL = " `%s`.`%s` <= ? ";
    private final static String TEXT_LIKE = " `%s`.`%s` LIKE ? ";
    private final static String TEXT_NOT_EQUAL = " `%s`.`%s` <>  ?  ";
    private final static String TEXT_NOT_EXISTS = " `%s`.`%s` NOT EXISTS ( ? ) ";
    private final static String TEXT_NOT_IN = " `%s`.`%s` NOT IN ( ? ) ";
    private final static String TEXT_NOT_nULL = " `%s`.`%s` IS NOT nULL ";
    
    private final static String TEXT_AND = " AND %s ";
    
    private final static String TEXT_OR = " OR %s ";
    
    private final static String TEXT_WHERE = " WHERE %s ";
    
    private final static String TEXT_SELECT = "  SELECT %s  ";
    
    private final static String TEXT_FROM = " FROM `%s` AS `%S` ";
    
    private final static String TEXT_INNER_JOIN = " INNER JOIN `%s` AS `%S` ON %s ";
    
    
    public final static Expression BETWEEN = t -> String.format(TEXT_BETWEEN, t[0], t[1]);
    
    public final static Expression EQUAL = t -> String.format(TEXT_EQUAL, t[0], t[1]);
    
    public final static Expression EXISTS = t -> String.format(TEXT_EXISTS, t[0], t[1]);
    
    public final static Expression GREATE_THAN = t -> String.format(TEXT_GREATE_THAN, t[0], t[1]);
    
    public final static Expression GREATE_THAN_EQUAL = t -> String.format(TEXT_GREATE_THAN_EQUAL, t[0], t[1]);
    
    public final static Expression IN = t -> String.format(TEXT_IN, t[0], t[1]);
    
    public final static Expression IS_NULL = t -> String.format(TEXT_IS_NULL, t[0], t[1]);
    
    public final static Expression LESS_THAN = t -> String.format(TEXT_LESS_THAN, t[0], t[1]);
    
    public final static Expression LESS_THAN_EQUAL = t -> String.format(TEXT_LESS_THAN_EQUAL, t[0], t[1]);
    
    public final static Expression LIKE = t -> String.format(TEXT_LIKE, t[0], t[1]);
    
    public final static Expression NOT_EQUAL = t -> String.format(TEXT_NOT_EQUAL, t[0], t[1]);
    
    public final static Expression NOT_EXISTS = t -> String.format(TEXT_NOT_EXISTS, t[0], t[1]);
    
    public final static Expression NOT_IN = t -> String.format(TEXT_NOT_IN, t[0], t[1]);
    
    public final static Expression NOT_nULL = t -> String.format(TEXT_NOT_nULL, t[0], t[1]);
    
    public final static Expression WHERE = t -> {
        
        WhereType whereType = (WhereType) t[0];
        switch (whereType) {
            case WHERE:
                return " WHERE ";
            case AND:
                return " AND ";
            case OR:
                return " OR ";
        }
        return StringUtils.EMPTY;
        
    };
    
}
