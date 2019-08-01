package org.smart.orm.execution;

import org.smart.orm.Model;
import org.smart.orm.SmartORMException;
import org.smart.orm.jdbc.ResultTypeHandler;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.PropertyInfo;

import javax.jws.WebParam;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;


public class ModelHandler<T extends Model<T>> implements ResultHandler<List<T>> {

    private ResultTypeHandler resultTypeHandler = new ResultTypeHandler();

    private ResultListener<List<T>> listener;

    private Class<T> entityClass;

    public ModelHandler(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void handle(ResultSet resultset) {

        List<T> resultList = new ArrayList<>();

        EntityInfo entityInfo = Model.getMeta(entityClass);

        List<String> nameList = new ArrayList<>();
        List<Class<?>> typeList = new ArrayList<>();

        try {

            ResultSetMetaData metaData = resultset.getMetaData();

            for (int i = 0, n = metaData.getColumnCount(); i < n; i++) {
                nameList.add(metaData.getColumnLabel(i + 1));
                typeList.add(Class.forName(metaData.getColumnClassName(i + 1)));
            }
            while (resultset.next()) {

                T data = entityInfo.newInstance();

                for (int i = 0, n = nameList.size(); i < n; i++) {
                    String name = nameList.get(i);
                    Class<?> cls = typeList.get(i);
                    Object cellData = resultTypeHandler.handle(cls, resultset, i);

                    PropertyInfo prop = entityInfo.getPropInfo(name);
                    prop.getField().set(data, cellData);
                }

                resultList.add(data);
            }
            if (listener != null)
                listener.handle(resultList);
        } catch (SQLException | IllegalAccessException | ClassNotFoundException ex) {
            throw new SmartORMException(ex);
        }

    }

    @Override
    public void addListener(ResultListener<List<T>> listener) {
        this.listener = listener;
    }

}
