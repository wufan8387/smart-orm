package org.smart.orm.reflect;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.SmartORMException;
import org.smart.orm.annotations.Association;
import org.smart.orm.data.AssociationType;
import org.smart.orm.data.FetchType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class AssociationInfo {
    
    private AssociationType assocType;
    
    private FetchType fetchType;
    
    private EntityInfo thisEntity;
    
    private EntityInfo otherEntity;
    
    private PropertyInfo thisKeyProp;
    
    private PropertyInfo otherKeyProp;
    
    private Field field;
    
    
    public AssociationInfo(Class<?> thisCls
            , Field field
            , Association assoc) {
        
        this.assocType = assoc.type();
        this.fetchType = assoc.fetch();
        
        thisEntity = Model.getMetaManager().findEntityInfo(thisCls);
        
        this.field = field;
        field.setAccessible(true);
        
        String thisKey = assoc.thisKey();
        
        if (StringUtils.isNotEmpty(thisKey)) {
            this.thisKeyProp = thisEntity.getProp(thisKey);
        }
        
        otherEntity = Model.getMetaManager().findEntityInfo(assoc.otherEntity());
        
        String otherKey = assoc.otherKey();
        
        if (StringUtils.isNotEmpty(otherKey)) {
            this.otherKeyProp = otherEntity.getProp(otherKey);
        }
        
    }
    
    
    public AssociationType getAssocType() {
        return assocType;
    }
    
    public FetchType getFetchType() {
        return fetchType;
    }
    
    public EntityInfo getThisEntity() {
        return thisEntity;
    }
    
    public PropertyInfo getThisKeyProp() {
        return thisKeyProp;
    }
    
    public EntityInfo getOtherEntity() {
        return otherEntity;
    }
    
    public PropertyInfo getOtherKeyProp() {
        return otherKeyProp;
    }
    
    public Field getField() {
        return field;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(Object obj) {
        try {
            return (T) field.get(obj);
        } catch (IllegalAccessException ex) {
            throw new SmartORMException(ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void set(Object obj, Object value) {
        try {
            
            Class<?> propType = field.getType();
            
            if (Iterable.class.isAssignableFrom(propType)) {
                Collection list = (Collection) field.get(obj);
                list.add(value);
            } else {
                field.set(obj, value);
            }
            
            
        } catch (IllegalAccessException ex) {
            throw new SmartORMException(ex);
        }
    }
    
    
    public void shouldInitialize(Object obj){
        try {
        
            Class<?> propType = field.getType();
            if (Iterable.class.isAssignableFrom(propType)) {
            
                if (field.get(obj) == null) {
                    field.set(obj, new ArrayList<>());
                }
            }
        } catch (IllegalAccessException ex) {
            throw new SmartORMException(ex);
        }
    
    }
}
