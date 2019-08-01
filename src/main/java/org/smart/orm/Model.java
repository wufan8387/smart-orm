package org.smart.orm;

import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Table;

import org.smart.orm.operations.text.QueryStatement;
import org.smart.orm.operations.type.QueryObject;
import org.smart.orm.operations.type.RelationNode;
import org.smart.orm.reflect.*;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Model<T extends Model<T>> {

    private final static Map<String, EntityInfo> metaMap = new HashMap<>();

    private List<ChangeInfo> changeList = new ArrayList<>();

    public static Map<String, EntityInfo> getMetaMap() {
        return metaMap;
    }

    private EntityInfo entityInfo;

    private OperationContext context;

    public EntityInfo getMeta() {
        if (entityInfo == null) {
            Class<?> cls = this.getClass();
            String clsName = cls.getName();
            synchronized (metaMap) {
                entityInfo = metaMap.get(clsName);
                if (entityInfo == null) {
                    entityInfo = buildEntityInfo(cls);
                    metaMap.put(clsName, entityInfo);
                }
            }
        }
        return entityInfo;
    }


    @NotNull
    public static EntityInfo getMeta(Class<?> cls) {

        String clsName = cls.getName();

        synchronized (metaMap) {
            EntityInfo entityInfo = metaMap.get(clsName);
            if (entityInfo == null) {
                entityInfo = buildEntityInfo(cls);
                metaMap.put(clsName, entityInfo);
            }
            return entityInfo;
        }
    }


    private static EntityInfo buildEntityInfo(Class<?> cls) {
        EntityInfo entityInfo = new EntityInfo();

        Table table = cls.getDeclaredAnnotation(Table.class);
        TableInfo tableInfo = new TableInfo(table);
        entityInfo.setTable(tableInfo);
        entityInfo.setEntityClass(cls);

        Field[] fieldList = cls.getDeclaredFields();
        for (Field field : fieldList) {

            Column column = field.getAnnotation(Column.class);
            if (column == null)
                continue;

            PropertyInfo propertyInfo = new PropertyInfo(column, field);
            entityInfo.add(propertyInfo);
        }

        return entityInfo;

    }

    protected void propertyChange(String property, Object value) {

        ChangeInfo changeInfo = new ChangeInfo();
        changeInfo.propertyName = property;
        // changeInfo.value = value;

        changeList.remove(changeInfo);
        changeList.add(changeInfo);

    }

    protected void propertyChange(PropertyGetter<T> property, Object value) {
        String propertyName = LambdaParser.getGetter(property).getName();
        this.propertyChange(propertyName, value);
    }





//    public InsertStatement<T> insert() {
//        InsertStatement<T> insOp = new InsertStatement<>();
//        return insOp;
//    }
//
//    public DeleteStatement<T> delete() {
//        DeleteStatement<T> delOp = new DeleteStatement<>();
//        return delOp;
//    }

//    @SuppressWarnings("unchecked")
//    public UpdateStatement<T> update() {
//        UpdateStatement<T> upOp = new UpdateStatement<>(context, (T) this);
//        return upOp;
//    }

    private static class ChangeInfo {

        public String propertyName;

        // public Object value;

        @Override
        public int hashCode() {
            return this.propertyName.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return false;
            if (obj.getClass() == this.getClass()) {
                ChangeInfo changeInfo = (ChangeInfo) obj;

                if (this.propertyName.equals(changeInfo.propertyName))
                    return true;
            } else {
                if (this.propertyName.equals(obj))
                    return true;
            }

            return false;
        }

    }

}
