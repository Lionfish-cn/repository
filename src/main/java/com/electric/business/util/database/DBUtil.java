package com.electric.business.util.database;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.*;

@Component
public class DBUtil {

    private ComboPooledDataSource dataSource;

    private static DBUtil dbUtil;

    @PostConstruct
    public void init(){
        dbUtil = this;
        dbUtil.dataSource = this.dataSource;
    }

    public JSONArray querySQL(String sql,Object [] parameter ) throws SQLException, JSONException {
        Connection conn=null;
        conn=dbUtil.dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i=0;i<parameter.length;i++){
            if (parameter[i] instanceof Integer) {
                ps.setInt(i, Integer.parseInt(parameter[i].toString()));
            }
            if (parameter[i] instanceof String) {
                ps.setString(i, parameter[i].toString());
            }
        }
        ResultSet rs=ps.executeQuery();

        JSONArray array=resultSetToJsonArry(rs);
        rs.close();
        ps.close();
        conn.close();
        return array;
    }

    public static JSONArray resultSetToJsonArry(ResultSet rs) throws SQLException,JSONException {
        JSONArray array = new JSONArray();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        // 遍历ResultSet中的每条数据
        while (rs.next()) {
            JSONObject jsonObj = new JSONObject();
            // 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName =metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value==null?"":value);
            }
            array.add(jsonObj);
        }
        return array;
    }

}
