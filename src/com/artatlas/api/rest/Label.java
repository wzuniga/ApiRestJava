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
//import javax.ws.rs.PathParam;
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
@Path("/label")
public class Label {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/get")
    public String getLabel(String data) {
        JSONObject jsonObj;
        String request = null;
        try {
            jsonObj = new JSONObject(data);
            request = jsonObj.get("query").toString();
        } catch (JSONException ex) {
            Logger.getLogger(Label.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        try {
            return $getResult(request);
        } catch (SQLException ex) {
            Logger.getLogger(Combo.class.getName()).log(Level.SEVERE, null, ex);
            return "Error 500";
        }
    }
    //select [LicTradNum] from ocrd
    //cardname
    //A PEACE TREATY
    String $getResult(String query) throws SQLException {
        ConectorSql conectorSql = new SqlServerConection();
        conectorSql.getConnection();
        
        ResultSet rs = conectorSql.querySQL(query);
        System.out.println("Label---------------------------------");
        System.out.println(rs);
        System.out.println("---------------------------------");
        if (rs == null){
            return "RS null en Label";
        }
        ResultConector rc = conectorSql.getData(rs);
        
        ArrayList<ArrayList<ArrayList<String>>> arr = rc.getData();

        Element root=new Element("LABEL");
        Document doc=new Document();
        doc.setDocType(new DocType("xml"));
        
        for(int i = 0; i < arr.size(); i++){
            Element child=new Element("ELEMENT");
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
