package com.code.repository.dbcp;


import com.code.repository.entity.DataConnectPool;
import com.code.repository.util.StringUtil;
import com.code.repository.util.database.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;

public class DsTemplate {
    private Connection conn = null;
    private String exception_message = "";

    public Object execute(DataConnectPool pool, DsAction dsAction) {
        try {
            conn = getConn(pool);
            conn.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try{
                    conn.close();
                    conn = null;
                }catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        }
        return doTryAction(conn, dsAction);
    }

    private Connection getConn(DataConnectPool pool) {
        try {
            Class.forName(pool.getDriverClassname()).newInstance();
            String url = pool.getUrl();
            if (StringUtil.isNull(url)) {
                url = DBUtil.getUrl(pool);
            }
            conn = DriverManager.getConnection(url, pool.getUsername(), pool.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            exception_message = e.getMessage();
            if (conn != null) {
                try{
                    conn.close();
                    conn = null;
                }catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        }
        return conn;
    }

    private Object doTryAction(Connection connection, DsAction dsAction) {
        try {
            Object var = dsAction.doAction(connection);
            conn.close();
            conn = null;
            return var;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try{
                    conn.close();
                    conn = null;
                }catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        }
        return "Exception:" + exception_message;
    }

}
