package com.code.repository.dbcp;

import java.sql.Connection;

public interface IDsAction {
     Object doAction(Connection connection) throws Exception;
}
