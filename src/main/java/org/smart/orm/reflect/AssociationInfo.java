package org.smart.orm.reflect;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.annotations.Association;
import org.smart.orm.data.AssociationType;
import org.smart.orm.data.FetchType;

import java.lang.reflect.Field;

public class AssociationInfo {
    
    private AssociationType assocType;
    
    private FetchType fetchType;
    
    private EntityInfo thisEntity;
    
    private EntityInfo otherEntity;
    
    private PropertyInfo thisKeyProp;
    
    private PropertyInfo otherKeyProp;
    
    private PropertyInfo prop;
    
    
    public AssociationInfo(Class<?> thisCls
            , Field field
            , Association assoc) {
        
        this.assocType = assoc.type();
        this.fetchType = assoc.fetch();
        
        thisEntity = Model.getMetaManager().findEntityInfo(thisCls);
        
        this.prop = thisEntity.getProp(field.getName());
        
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
    
    public PropertyInfo getProp() {
        return prop;
    }
}
