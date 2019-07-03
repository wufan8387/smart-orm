package org.smart.orm.jdbc;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.tools.JavaCompiler;
import java.sql.*;
import java.util.*;

public class SqlRunner {

    public static final int NO_GENERATED_KEY = Integer.MIN_VALUE + 1001;


    private Connection connection;
    private boolean useGeneratedKeySupport;

    public SqlRunner(Connection connection) {
        this.connection = connection;
    }


    public ResultSet select(String sql, Object... args) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setParameters(ps, args);
            return ps.executeQuery();
        }
    }


    public int insert(String sql, Object... args) throws SQLException {
        PreparedStatement ps;
        if (useGeneratedKeySupport) {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            ps = connection.prepareStatement(sql);
        }

        try (ps) {
            setParameters(ps, args);
            ps.executeUpdate();
            if (useGeneratedKeySupport) {
                List<Map<String, Object>> keys = convertToMap(ps.getGeneratedKeys());
                if (keys.size() == 1) {
                    Map<String, Object> key = keys.get(0);
                    Iterator<Object> i = key.values().iterator();
                    if (i.hasNext()) {
                        Object genkey = i.next();
                        if (genkey != null) {
                            try {
                                return Integer.parseInt(genkey.toString());
                            } catch (NumberFormatException e) {
                                //ignore, no numeric key suppot
                            }
                        }
                    }
                }
            }
            return NO_GENERATED_KEY;
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


    public void run(String sql) throws SQLException {

        try(Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            //ignore
        }
    }

    //设置参数
    private void setParameters(PreparedStatement ps, Object... args) throws SQLException {
        for (int i = 0, n = args.length; i < n; i++) {

        }
    }

    private List<Map<String, Object>> convertToMap(ResultSet rs) throws SQLException {
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            List<String> columns = new ArrayList<String>();
            List<TypeHandler<?>> typeHandlers = new ArrayList<TypeHandler<?>>();
            ResultSetMetaData rsmd = rs.getMetaData();
            //先计算要哪些列，已经列的类型（TypeHandler）
            for (int i = 0, n = rsmd.getColumnCount(); i < n; i++) {
                columns.add(rsmd.getColumnLabel(i + 1));
                try {

                    Class<?> type = Resources.classForName(rsmd.getColumnClassName(i + 1));
                    TypeHandler<?> typeHandler = typeHandlerRegistry.getTypeHandler(type);
                    if (typeHandler == null) {
                        typeHandler = typeHandlerRegistry.getTypeHandler(Object.class);
                    }
                    typeHandlers.add(typeHandler);
                } catch (Exception e) {
                    typeHandlers.add(typeHandlerRegistry.getTypeHandler(Object.class));
                }
            }
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 0, n = columns.size(); i < n; i++) {
                    String name = columns.get(i);
                    TypeHandler<?> handler = typeHandlers.get(i);
                    //巧妙的利用TypeHandler来取得结果
                    row.put(name.toUpperCase(Locale.ENGLISH), handler.getResult(rs, name));
                }
                list.add(row);
            }
            return list;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }


}
