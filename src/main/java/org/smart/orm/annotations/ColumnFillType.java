package org.smart.orm.annotations;

public enum ColumnFillType {
    ALL(0), INSERT(1), UPDATE(2), READ(4);
    private int value;
    
    ColumnFillType(int value) {
        this.value = value;
    }
}
