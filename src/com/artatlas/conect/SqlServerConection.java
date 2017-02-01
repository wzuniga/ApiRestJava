/*
 * Program made by wzuniga - System Engineer
 * https://github.com/wzuniga/ApiRestJava
 */
package com.artatlas.conect;

import com.artatlas.constans.ConfiguratorConstans;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.artatlas.result.ResultConector;

/**
 *
 * @author wzuniga
 */
public class SqlServerConection extends ConectorSql{
    
    @Override
    public Connection getConnection(){
        if (conector != null) 
            return conector;
        else{
            String ip = ConfiguratorConstans.DB_IP;
            String port = ConfiguratorConstans.DB_PORT;
            String name = ConfiguratorConstans.DB_NAME;
            
            return getConnection(ip, port, name);
        }
    }
    
    @Override
    public Connection getConnection(String ip, String port, String database) {
        //String url = "jdbc:sqlserver://192.168.0.7:1433;databaseName=SBO_ART_PRUEBAS";
        String url = "jdbc:sqlserver://"+ip+":"+port+";databaseName="+database;
        
        String path = ConfiguratorConstans.PATH_CLASS_DRIVER;
        try {
            Class.forName(path);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        try {
            String user = ConfiguratorConstans.DB_USER;
            String pass = ConfiguratorConstans.DB_PASSWORD;
            conector = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.err.println(e);
        }
        return conector;
    }
    
    @Override
    public ResultSet querySQL(String query) {
        System.out.println(query);
        Connection conn = getConnection();
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            stmt.executeQuery(query);
            rs = stmt.getResultSet();
        } catch (SQLException e) {
            System.out.println("ERROR QUERY");
            rs = querySQL(query, 0);
        }
        return rs;
    }
    
    @Override
    protected ResultSet querySQL(String query, int n) {
        if (n == 50 )return null;
        Connection conn = getConnection();
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            stmt.executeQuery(query);
            rs = stmt.getResultSet();
        } catch (SQLException e) {
            System.out.println("ERROR "+n);
            System.out.println(query);
            rs = querySQL(query, n+1);
        }
        return rs;
    }
    
    @Override
    public void showData(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmeta = rs.getMetaData();
        int numColumnas = rsmeta.getColumnCount();

        while (rs.next()) {
            for (int i = 1; i <= numColumnas; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
    }

    @Override
    public ResultConector getData(ResultSet rs) throws SQLException {

        ArrayList<ArrayList<ArrayList<String>>> data = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<String> colName = new ArrayList<String>();

        ResultSetMetaData rsmeta = rs.getMetaData();
        if (rs == null) {
            System.out.println("NULLLLL"); 
        }
        int numColumnas = rsmeta.getColumnCount();
        while (rs.next()) {
            ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
            //temp.add(rsmeta.getColumnName(j++));
            for (int i = 1; i <= numColumnas; i++) {
                ArrayList<String> t = new ArrayList<String>();
                t.add(rsmeta.getColumnName(i));
                t.add(rs.getString(i));
                temp.add(t);
            }
            data.add(temp);
        }
        
        for (int i = 1; i <= numColumnas; i++) {
            colName.add(rsmeta.getColumnName(i));
        }
        
        ResultConector rc = new ResultConector(data, colName);
        
        return rc;
    }

    @Override
    public void close() {
        try {
            conector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
