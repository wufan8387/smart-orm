package org.smart.orm.execution;

import org.smart.orm.SmartORMException;
import org.smart.orm.jdbc.ResultTypeHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class KeyMapHandler implements ResultHandler<Map<String, Object>> {
    
    private ResultTypeHandler resultTypeHandler = new ResultTypeHandler();
    
    private List<Map<String, Object>> result = new ArrayList<>();
    
    @Override
    public Map<String, Object> getFirst() {
        if (result.size() > 0)
            return result.get(0);
        return null;
    }
    
    @Override
    public List<Map<String, Object>> getAll() {
        return result;
    }
    
    @Override
    public void handle(ResultSet resultset) {
        
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
                result.add(row);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new SmartORMException(ex);
        }
        
        
    }
    
    
}
