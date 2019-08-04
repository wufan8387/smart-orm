package org.smart.orm.operations;

import org.smart.orm.functions.Func;
import org.smart.orm.data.JoinType;

public class Token {
    
    public final static String WHERE = " WHERE ";
    
    public final static String SELECT = " SELECT ";
    
    public final static String FROM = " FROM ";
    
    public final static String ORDER_BY = " ORDER BY ";
    
    public final static String GROUP_BY = " GROUP BY ";
    
    
    public final static Func<String> JOIN = t -> {
        
        JoinType joinType = (JoinType) t[0];
        switch (joinType) {
            case INNER:
                return " INNER JOIN ";
            case LEFT:
                return " LEFT JOIN ";
            case RIGHT:
                return " RIGHT JOIN ";
            default:
                return ",";
        }
    };
    
    public final static Func<String> UPDATE_AS_SET = t -> String.format(" UPDATE `%s` AS `%s` SET ", t);
    
    public final static Func<String> ASSIGN = t -> String.format(" `%s` = %s ", t);
    
    
    public final static Func<String> INSERT_INTO = t -> String.format(" INSERT INTO `%s` ", t);
    
    public final static String VALUES = " VALUES ";
    
    public final static Func<String> AS = t -> String.format(" AS `%s` ", t);
    
    public final static Func<String> DEL_FROM_AS = t -> String.format(" DELETE FROM `%s` AS `%s` ", t);
    
    
    public final static Func<String> REL = t -> String.format(" `%s` ", t);
    
    public final static Func<String> REL_AS = t -> String.format(" `%s` AS `%s` ", t);
    
    
    public final static Func<String> ATTR_INSERT = t -> String.format(" `%s` ", t[1]);
    
    public final static Func<String> REL_ATTR = t -> String.format(" `%s`.`%s` ", t);
    
    public final static Func<String> REL_ATTR_AS = t -> String.format(" `%s`.`%s` AS `%s` ", t);
    
    
    public final static Func<String> FUNC = t -> String.format(" `%s` ", t);
    
    public final static Func<String> FUNC_AS = t -> String.format(" (%s) AS `%s` ", t);
    
    
    public final static Func<String> ASC = t -> String.format(" `%s`.`%s` ASC ", t);
    
    public final static Func<String> DESC = t -> String.format(" `%s`.`%s` DESC ", t);
    
    
    public final static String ON = " ON ";
    
    public final static Func<String> LIMIT = t -> {
        if (t[1] == null)
            return String.format("  LIMIT %s  ", t);
        return String.format("  LIMIT %s, %s ", t);
    };
    
    
}
