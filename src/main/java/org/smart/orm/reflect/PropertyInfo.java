package org.smart.orm.reflect;

import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.ColumnFillType;
import org.smart.orm.annotations.IdType;

import java.lang.reflect.Field;

public class PropertyInfo {
    
    private String column;
    
    private Field field;
    
    private ColumnFillType[] fillType;
    
    private boolean isPrimaryKey;
    
    private IdType idType;
    
    
    public PropertyInfo() {
    }
    
    public PropertyInfo(Column column) {
        this.column = column.value();
        this.fillType = column.fillType();
        this.isPrimaryKey = column.isPrimaryKey();
        this.idType = column.idType();
    }
    
    
    public String getColumn() {
        return column;
    }
    
    public void setColumn(String column) {
        this.column = column;
    }
    
    public Field getField() {
        return field;
    }
    
    public void setField(Field field) {
        this.field = field;
    }
    
    public ColumnFillType[] getFillType() {
        return fillType;
    }
    
    public void setFillType(ColumnFillType[] fillType) {
        this.fillType = fillType;
    }
    
    public IdType getIdType() {
        return idType;
    }
    
    public void setIdType(IdType idType) {
        this.idType = idType;
    }
    
    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }
    
    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }
}
