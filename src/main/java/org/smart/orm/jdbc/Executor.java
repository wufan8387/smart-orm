package org.smart.orm.jdbc;

import org.smart.orm.SmartORMException;

import java.sql.*;
import java.util.*;

public class Executor {
    
    public static final int NO_GENERATED_KEY = Integer.MIN_VALUE + 1001;
    
    private Connection connection;
    
    private DataAdapter dataAdapter;
    
    public Executor(DataAdapter adapter, Connection connection) {
        this.dataAdapter = adapter;
        this.connection = connection;
    }
    
    public ResultSet executeQuery(String sql, Object... args) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setParameters(ps, args);
            return ps.executeQuery();
        }
    }
    
    
    public void insert(String sql, boolean withKeys, Object... args) throws SQLException {
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setParameters(ps, args);
            ps.executeUpdate();
        }
    }
    
    
    public int insertWithKeys(String sql, Object... args) throws SQLException {
        
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(ps, args);
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                List<Map<String, Object>> keys = convertToMap(rs);
                if (keys.size() == 1) {
                    Map<String, Object> key = keys.get(0);
                    Iterator<Object> i = key.values().iterator();
                    if (i.hasNext()) {
                        Object genKey = i.next();
                        if (genKey != null) {
                            try {
                                return Integer.parseInt(genKey.toString());
                            } catch (NumberFormatException ex) {
                                throw new SmartORMException(ex);
                            }
                        }
                    }
                }
                return NO_GENERATED_KEY;
                
            }
        }
    }
    
    
    public int update(String sql, Object... args) throws SQLException {
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setParameters(ps, args);
            return ps.executeUpdate();
        }
    }
    
    public int delete(String sql, Object... args) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setParameters(ps, args);
            return ps.executeUpdate();
        }
    }
    
    public void execute(String sql, Object... args) throws SQLException {
        
        if (args.length == 0) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(sql);
            }
        } else {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                setParameters(stmt, args);
                stmt.executeUpdate();
            }
        }
        
        
    }
    
    
    public DataAdapter getDataAdapter() {
        return dataAdapter;
    }
    
    
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ignore) {
        
        }
    }
    
    private void setParameters(PreparedStatement ps, Object... args) throws SQLException {
        for (int i = 0, n = args.length; i < n; i++) {
            Object arg = args[i];
            dataAdapter.setValue(ps, i, arg);
        }
    }
    
    private List<Map<String, Object>> convertToMap(ResultSet rs) throws SQLException {
        
        List<Map<String, Object>> list = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<Class<?>> typeList = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        
        try {
            for (int i = 0, n = metaData.getColumnCount(); i < n; i++) {
                nameList.add(metaData.getColumnLabel(i + 1));
                typeList.add(Class.forName(metaData.getColumnClassName(i + 1)));
            }
        } catch (Exception ex) {
            throw new SmartORMException(ex);
        }
        
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 0, n = nameList.size(); i < n; i++) {
                String name = nameList.get(i);
                Class<?> cls = typeList.get(i);
                row.put(name.toUpperCase(Locale.ENGLISH), dataAdapter.getValue(cls, rs, i));
            }
            list.add(row);
        }
        return list;
        
    }
    
}
