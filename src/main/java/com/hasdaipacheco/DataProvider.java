package com.hasdaipacheco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DataProvider {
    boolean connect(String url, String user, String pass);
    boolean disconnect();
    ResultSet executeSelectQuery(PreparedStatement st);
    int executeUpdateQuery(PreparedStatement st);
    Connection getConnection();
}