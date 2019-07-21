package org.smart.orm.operations;

import org.smart.orm.Func;
import org.smart.orm.data.JoinType;

public class Token {
    
    public final static String WHERE = " WHERE ";
    
    public final static String SELECT = " SELECT ";
    
    public final static String FROM = " FROM ";
    
    public final static String ORDERBY = " ORDER BY ";
    
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
    
    public final static Func<String> AS = t -> {
        
        return String.format(" AS `%s` ", t[0]);
    };
    
    
    public final static Func<String> REL = t -> String.format(" `%s` ", t[0]);
    
    public final static Func<String> REL_AS = t -> String.format(" `%s` AS `%s` ", t[0], t[1]);
    
    public final static Func<String> ATTR = t -> String.format(" `%s`.`%s` ", t[0], t[1]);
    
    public final static Func<String> ATTR_AS = t -> String.format(" `%s`.`%s` AS `%s` ", t[0], t[1], t[2]);
    
    public final static Func<String> FUNC = t -> String.format(" `%s` ", t[0]);
    
    public final static Func<String> FUNC_AS = t -> String.format(" (%s) AS `%s` ", t[0], t[1]);
    
    
    public final static Func<String> ASC = t -> String.format(" `%s`.`%s` ASC ", t[0], t[1]);
    
    public final static Func<String> DESC = t -> String.format(" `%s`.`%s` DESC ", t[0], t[1]);
    
    
    public final static String ON = " ON ";
    
    public final static Func<String> LIMIT = t -> {
        if (t[1] == null)
            return String.format("  LIMIT %s  ", t[0]);
        return String.format("  LIMIT %s, %s ", t[0], t[1]);
    };
    
    
}
