package org.smart.orm.reflect;

import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.ColumnFillType;
import org.smart.orm.annotations.IdType;

import java.lang.reflect.Field;

public class PropertyInfo {
    
    private String columnName;
    
    private Field field;
    
    private ColumnFillType[] fillType;
    
    private boolean isPrimaryKey;
    
    private IdType idType;
    
    private String propertyName;
    
    public PropertyInfo() {
    }
    
    public PropertyInfo(Column column, Field field) {
        this.columnName = column.value();
        this.fillType = column.fillType();
        this.isPrimaryKey = column.isPrimaryKey();
        this.idType = column.idType();
        this.propertyName = field.getName();
        this.field = field;
        if (this.columnName.equals("")) {
            this.columnName = this.propertyName;
        }
    }
    
    public String getPropertyName() {
        return propertyName;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public Field getField() {
        return field;
    }
    
    public void setField(Field field) {
        this.field = field;
        this.propertyName = field.getName();
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
