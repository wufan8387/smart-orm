package org.smart.orm.execution;

import org.smart.orm.SmartORMException;
import org.smart.orm.jdbc.ResultTypeHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class KeyMapHandler implements ResultHandler<List<Map<String, Object>>> {
    
    private ResultTypeHandler resultTypeHandler;
    
    private ResultListener<List<Map<String, Object>>> listener;
    
    @Override
    public void handle(ResultSet resultset) {
        
        List<Map<String, Object>> list = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<Class<?>> typeList = new ArrayList<>();
        
        try {
    
            ResultSetMetaData metaData = resultset.getMetaData();
    
            for (int i = 0, n = metaData.getColumnCount(); i < n; i++) {
                nameList.add(metaData.getColumnLabel(i + 1));
                typeList.add(Class.forName(metaData.getColumnClassName(i + 1)));
            }
            while (resultset.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 0, n = nameList.size(); i < n; i++) {
                    String name = nameList.get(i);
                    Class<?> cls = typeList.get(i);
                    row.put(name.toUpperCase(Locale.ENGLISH), resultTypeHandler.handle(cls, resultset, i));
                }
                list.add(row);
            }
            
            if (listener != null)
                listener.handle(list);
            
        } catch (ClassNotFoundException ex) {
            throw new SmartORMException(ex);
        } catch (SQLException ex) {
            throw new SmartORMException(ex);
        }
        
        
    }
    
    @Override
    public void addLisenter(ResultListener<List<Map<String, Object>>> listener) {
        this.listener = listener;
    }
    
    
}
