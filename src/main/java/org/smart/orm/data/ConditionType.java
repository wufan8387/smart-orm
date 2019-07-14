package org.smart.orm.data;

public enum ConditionType {
    
    EQUAL("=", 2),
    EXISTS("EXISTS (?)", 3),
    GREATE_THAN(">", 4);
    
    
    String op;
    int value;
    
    ConditionType(String op, int value) {
        this.op = op;
        this.value = value;
    }
}
