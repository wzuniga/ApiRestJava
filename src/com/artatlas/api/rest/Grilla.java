/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artatlas.api.rest;

import com.artatlas.result.ResultConector;

import com.artatlas.conect.ConectorSql;
import com.artatlas.conect.SqlServerConection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author wzuniga
 */
//@Stateless
@Path("/grilla")
public class Grilla {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/get")
    public String getGrilla(String query) {
        try {
            JSONObject jsonObj = new JSONObject(query);
            String request = jsonObj.get("query").toString();
            try {
                return $getResult(request);
            } catch (SQLException ex) {
                Logger.getLogger(Combo.class.getName()).log(Level.SEVERE, null, ex);
                return "Error 500";
            }
        } catch (JSONException ex) {
            Logger.getLogger(Combo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    String $getResult(String query) throws SQLException {
        ConectorSql conectorSql = new SqlServerConection();
        conectorSql.getConnection();

        ResultSet rs = conectorSql.querySQL(query);
        System.out.println("Grilla---------------------------------");
        System.out.println(rs);
        System.out.println("---------------------------------");
        if (rs == null){
            return "RS null en grilla";
        }
        ResultConector rc = conectorSql.getData(rs);
        
        ArrayList<ArrayList<ArrayList<String>>> arr = rc.getData();
        ArrayList<String> name = rc.getNames();
        
        Element root = new Element("GRILL");
        Document doc = new Document();
        doc.setDocType(new DocType("xml"));

        for (int i = 0; i < arr.size(); i++) {
            Element child = new Element("ROW");
            for (int j = 0; j < arr.get(i).size(); j++) {
                Element elem = new Element(arr.get(i).get(j).get(0).toUpperCase());
                for (int e = 1; e < arr.get(i).get(j).size(); e++) {
                    Element value = new Element("VALUE");
                    value.addContent(arr.get(i).get(j).get(e));
                    elem.addContent(value);
                }
                child.addContent(elem);
            }
            root.addContent(child);
        }
        doc.setRootElement(root);
        for (int i = 0; i < name.size(); i++) {
            Element child = new Element("NAME");
            child.addContent(name.get(i).toUpperCase());
            root.addContent(child);
        }
        
        XMLOutputter outter = new XMLOutputter();
        outter.setFormat(Format.getPrettyFormat());
        
        rs.close();
        conectorSql.close();
        
        return outter.outputString(doc);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/set")
    public String setGrilla(String query) {
        try {
            JSONObject jsonObj = new JSONObject(query);
            String request = jsonObj.get("query").toString();
            try {
                return $setData(request);
            } catch (SQLException ex) {
                Logger.getLogger(Combo.class.getName()).log(Level.SEVERE, null, ex);
                return "Error 500";
            }
        } catch (JSONException ex) {
            Logger.getLogger(Combo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    String $setData(String query) throws SQLException {
        ConectorSql conectorSql = new SqlServerConection();
        conectorSql.getConnection();

        ResultSet rs = conectorSql.querySQL(query);
        return "test";
    }
}
