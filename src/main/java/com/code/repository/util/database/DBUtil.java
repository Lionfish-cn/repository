package com.code.repository.util.database;

import com.code.repository.constants.Constants;
import com.code.repository.dbcp.DsAction;
import com.code.repository.dbcp.DsTemplate;
import com.code.repository.entity.DataConnectPool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DBUtil {
    public static JSONArray querySQL(Connection conn, String sql, Object[] parameter) throws Exception {
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < parameter.length; i++) {
            if (parameter[i] instanceof Integer) {
                ps.setInt(i, Integer.parseInt(parameter[i].toString()));
            }
            if (parameter[i] instanceof String) {
                ps.setString(i, parameter[i].toString());
            }
        }
        ResultSet rs = ps.executeQuery();

        JSONArray array = resultSetToJsonArray(rs);
        rs.close();
        ps.close();
        conn.close();
        return array;
    }

    public static JSONArray resultSetToJsonArray(ResultSet rs) throws Exception {
        JSONArray array = new JSONArray();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        // 遍历ResultSet中的每条数据
        while (rs.next()) {
            JSONObject jsonObj = new JSONObject();
            // 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value == null ? "" : value);
            }
            array.add(jsonObj);
        }
        return array;
    }

    public static JSONArray getTablename(Connection connection, String type) {
        JSONArray array = new JSONArray();
        try {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet rs = metadata.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                String table = rs.getString("TABLE_NAME");
                String tablename = rs.getString("REMARKS");
                jo.put("id", table);
                jo.put("name", tablename);
                array.add(jo);
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getColumns(String table_name, Connection conn, String type) {
        JSONArray array = new JSONArray();
        try {
            DatabaseMetaData metadata = conn.getMetaData();
            ResultSet rs = metadata.getColumns(conn.getCatalog(), null, table_name, null);
            while (rs.next()) {
                String column = rs.getString("COLUMN_NAME");
                if (column.toLowerCase().equals("id"))
                    continue;
                String columnName = rs.getString("REMARKS");
                JSONObject jo = new JSONObject();
                jo.put("id", column);
                jo.put("name", columnName);
                jo.put("type", switchDataType(rs.getInt("DATA_TYPE")));
                array.add(jo);
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String switchDataType(int val) {
        String type = "";
        switch (val) {
            case Types.VARCHAR:
                type = "VARCHAR";
                break;
            case Types.TIMESTAMP:
                type = "TIMESTAMP";
                break;
            case Types.INTEGER:
                type = "INTEGER";
                break;
        }
        return type;
    }

    public static String getUrl(DataConnectPool dataSource) {
        String defaultUrl = "";
        String type = dataSource.getType().toLowerCase();
        switch (type) {
            case "mysql":
                defaultUrl = Constants.MYSQL_URL;
                break;
            case "sqlserver":
                defaultUrl = Constants.SQLSERVER_URL;
                break;
            case "oracle":
                defaultUrl = Constants.ORACLE_URL;
                break;
        }
        return defaultUrl.replaceAll("##host##", dataSource.getHost()).
                replaceAll("##port##", dataSource.getPort().toString()).
                replaceAll("##database##", dataSource.getDatabase());
    }


    public static JSONArray execute(String sql, DataConnectPool pool) {
        try {
            return (JSONArray) new DsTemplate().execute(pool, new DsAction<Object>(sql) {
                @Override
                public Object doAction(Connection connection, Object sql) {
                    try {
                        return querySQL(connection, sql.toString(), new Object[]{});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
