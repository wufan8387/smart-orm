package org.smart.orm.reflect;

import org.smart.orm.annotations.Column;

import java.lang.reflect.Field;

public class PropertyInfo {
    
    private String name;
    
    private Field field;
    
    private Column column;
    
    public String getName() {
        return name;
    }
    
    public Field getField() {
        return field;
    }
    
    public void setField(Field field) {
        this.field = field;
        this.name = field.getName();
    }
    
    public Column getColumn() {
        return column;
    }
    
    public void setColumn(Column column) {
        this.column = column;
    }
}
