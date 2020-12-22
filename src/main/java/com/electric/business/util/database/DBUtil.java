package com.electric.business.util.database;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.electric.business.constants.Constants;
import com.electric.business.entity.DataConnectPool;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DBUtil {
    private DBUtil dbUtil;
    private ComboPooledDataSource comboPooledDataSource;

    @PostConstruct
    public void init(){

    }
    private static Connection getConnection(DataConnectPool dataSource){
        try{
            Connection conn = null;
            Class.forName(dataSource.getDriverClassname()).newInstance();
            conn = DriverManager.getConnection(getUrl(dataSource));
            return conn;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public JSONArray querySQL(String sql, Object[] parameter) throws SQLException, JSONException {
        Connection conn = null;
        //conn = //dbUtil.dataSource.getConnection();
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

        JSONArray array = resultSetToJsonArry(rs);
        rs.close();
        ps.close();
        conn.close();
        return array;
    }

    public static JSONArray resultSetToJsonArry(ResultSet rs) throws SQLException, JSONException {
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

    public static List<Map<String, Object>> getTablename(DataConnectPool dataSource) {
        List<Map<String,Object>> rtnList = null;
        Connection connection = null;
        Statement st = null;
        try {
            connection = getConnection(dataSource);
            DatabaseMetaData dbMetadata = connection.getMetaData();
            ResultSet rs = dbMetadata.getTables(null,null,null,new String[]{"TABLE"});
            while(rs.next()){
                Map<String,Object> map = new HashMap<>();
                String table = rs.getString("TABLE_NAME");
                String tablename = rs.getString("REMARKS");
                map.put("id",table);
                map.put("name",tablename);
                rtnList.add(map);
            }
            return rtnList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<String> getColumns(String table_name,DataConnectPool dataSource){
       try{
           String sql = "select * from " + table_name;
           Connection conn = getConnection(dataSource);
           PreparedStatement ps = conn.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
           rs.getMetaData();
       }catch(Exception e){
           e.printStackTrace();
       }
       return null;
    }

    public static String getUrl(DataConnectPool dataSource) {
        String defaultUrl = "";
        String type = dataSource.getType().toLowerCase();
        if ("mysql".equals(type)) {
            defaultUrl = Constants.MYSQL_URL;
        } else if ("sqlserver".equals(type)) {
            defaultUrl = Constants.SQLSERVER_URL;
        } else if ("oracle".equals(type)) {
            defaultUrl = Constants.ORACLE_URL;
        }
        String url = defaultUrl.replaceAll("[host]", dataSource.getHost()).replaceAll("[port]", dataSource.getPort().toString());
        return url;
    }

}
