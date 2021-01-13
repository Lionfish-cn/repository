package com.code.repository.dbcp;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DsAction<T> implements IDsAction {
    private T actionParam = null;

    public DsAction(T param) {
        this.actionParam = param;
    }

    public Object doAction(Connection connection){
        try{
            return this.doAction(connection,this.actionParam);
        }catch(Exception e){
            e.printStackTrace();
            return "Exception:"+ e.getMessage();
        }
    }

    public abstract Object doAction(Connection connection,T param) throws SQLException;

}
