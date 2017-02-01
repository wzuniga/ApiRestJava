/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artatlas.api.rest;

import com.artatlas.result.ResultConector;

//import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;

import com.artatlas.conect.ConectorSql;
import com.artatlas.conect.SqlServerConection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
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

@Path("/combo")
public class Combo {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/get")
    public String getCombo(String query) {
        try {
            System.out.println(query);
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
//select cardname from ocrd where cardtype = 'c'
    String $getResult(String query) throws SQLException {
        ConectorSql conectorSql = new SqlServerConection();
        conectorSql.getConnection();

        ResultSet rs = conectorSql.querySQL(query);
        System.out.println("Combo---------------------------------");
        System.out.println(rs);
        System.out.println("---------------------------------");
        if (rs == null){
            return "RS null en combo";
        }
        ResultConector rc = conectorSql.getData(rs);
        
        ArrayList<ArrayList<ArrayList<String>>> arr = rc.getData();

        Element root = new Element("COMBO");
        Document doc = new Document();
        doc.setDocType(new DocType("xml"));

        for (int i = 0; i < arr.size(); i++) {
            Element child = new Element("ELEMENT");
            for (int j = 0; j < arr.get(i).size(); j++) {
                child.addContent(arr.get(i).get(j).get(1));
            }
            root.addContent(child);
        }
        doc.setRootElement(root);

        XMLOutputter outter = new XMLOutputter();
        outter.setFormat(Format.getPrettyFormat());
        rs.close();
        conectorSql.close();
                
        return outter.outputString(doc);
    }

}
