package org.smart.orm.reflect;

import org.smart.orm.Model;
import org.smart.orm.annotations.Association;
import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Key;
import org.smart.orm.annotations.Table;
import org.smart.orm.functions.PropertyGetter;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaManager {
    
    private final static Map<String, EntityInfo> entityMap = new HashMap<>();
    
    private final static Map<String, List<AssociationInfo>> assocMap = new HashMap<>();
    
    private final static List<AssociationInfo> assocList = new ArrayList<>();
    
    @NotNull
    public EntityInfo findEntityInfo(Class<?> cls) {
        
        String clsName = cls.getName();
        synchronized (entityMap) {
            EntityInfo entityInfo = entityMap.get(clsName);
            if (entityInfo == null) {
                entityInfo = buildEntityInfo(cls);
                entityMap.put(clsName, entityInfo);
            }
            return entityInfo;
        }
    }
    
    @NotNull
    public List<AssociationInfo> findAssoc(Class<?> cls) {
        
        String clsName = cls.getName();
        synchronized (assocMap) {
            List<AssociationInfo> assocList = assocMap.get(clsName);
            if (assocList == null) {
                assocList = buildAssociationInfo(cls);
                assocMap.put(clsName, assocList);
                MetaManager.assocList.addAll(assocList);
            }
            return assocList;
        }
    }
    
    public AssociationInfo findAssoc(Class<?> cls, Field prop) {
        
        List<AssociationInfo> assocList = findAssoc(cls);
        return assocList.stream()
                .filter(t -> t.getField().equals(prop))
                .findFirst()
                .orElse(null);
        
    }
    
    
    public <T extends Model<T>> AssociationInfo findAssoc(Class<T> cls, PropertyGetter<T> getter) {
        
        Field field = LambdaParser.getGetter(getter);
        return findAssoc(cls, field);
    }
    
    @SuppressWarnings("unchecked")
    public void fillAutoGenerateKeys(Model<?> obj, Object[] keysData) {
        
        EntityInfo entityInfo = obj.getMeta();
        
        List<PropertyInfo> keyList = entityInfo.getKeys();
        for (int i = 0; i < keyList.size(); i++) {
            PropertyInfo key = keyList.get(i);
            if (obj.getChangeMap().containsKey(key.getPropName())) {
                continue;
            }
            entityInfo.setValue(obj, key, keysData[i]);
            fillAssocKeys(obj, key, keysData[i]);
        }
        
        
    }
    
    private void fillAssocKeys(Model<?> model, PropertyInfo prop, Object keyData) {
        
        findAssoc(model.getClass())
                .stream()
                .filter(t -> t.getThisKeyProp() == prop)
                .findFirst()
                .ifPresent(assoc -> {
                    Field assocProp = assoc.getField();
                    PropertyInfo otherKeyProp = assoc.getOtherKeyProp();
                    
                    switch (assoc.getAssocType()) {
                        case HAS_ONE: {
                            Model<?> otherEntity = assoc.get(model);
                            if (otherEntity == null) {
                                break;
                            }
                            otherEntity.getMeta().setValue(otherEntity, otherKeyProp, keyData);
                        }
                        break;
                        case HAS_MANY: {
                            Class<?> propType = assocProp.getType();
                            
                            if (Iterable.class.isAssignableFrom(propType)) {
                                Iterable<Model<?>> data = assoc.get(model);
                                for (Model<?> item : data) {
                                    item.getMeta().setValue(item, otherKeyProp, keyData);
                                }
                            } else if (propType.isArray()) {
                                Model<?>[] data = assoc.get(model);
                                for (Model<?> item : data) {
                                    item.getMeta().setValue(item, otherKeyProp, keyData);
                                }
                            }
                        }
                        break;
                    }
                    
                });
        
        
    }
    
    
    private EntityInfo buildEntityInfo(Class<?> cls) {
        
        
        Table table = cls.getDeclaredAnnotation(Table.class);
        EntityInfo entityInfo = new EntityInfo(cls, table);
        
        Field[] fieldList = cls.getDeclaredFields();
        for (Field field : fieldList) {
            
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                field.setAccessible(true);
                PropertyInfo propInfo = new PropertyInfo(entityInfo, field, column);
                Key key = field.getAnnotation(Key.class);
                propInfo.setKey(key);
                entityInfo.add(propInfo);
            }
        }
        
        
        return entityInfo;
        
    }
    
    private List<AssociationInfo> buildAssociationInfo(Class<?> cls) {
        List<AssociationInfo> assocList = new ArrayList<>();
        Field[] fieldList = cls.getDeclaredFields();
        for (Field field : fieldList) {
            Association association = field.getAnnotation(Association.class);
            if (association == null)
                continue;
            
            assocList.add(new AssociationInfo(cls, field, association));
        }
        return assocList;
    }
    
    
}
