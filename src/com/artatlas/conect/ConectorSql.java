/*
 * Program made by wzuniga - System Engineer
 * https://github.com/wzuniga/ApiRestJava
 */
package com.artatlas.conect;

import com.artatlas.result.ResultConector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wzuniga
 * 
 */

public abstract class ConectorSql {

    Connection conector = null;
    
    public abstract Connection getConnection();
    public abstract Connection getConnection(String ip, String port, String database);
    public abstract ResultSet querySQL(String query);
    protected abstract ResultSet querySQL(String query, int n);
    public abstract void showData(ResultSet rs) throws SQLException;
    public abstract ResultConector getData(ResultSet rs) throws SQLException;
    public abstract void close();
}
