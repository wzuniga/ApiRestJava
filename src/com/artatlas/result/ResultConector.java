/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artatlas.result;

import java.util.ArrayList;

/**
 *
 * @author wzuniga
 */
public class ResultConector {
    
    ArrayList<ArrayList<ArrayList<String>>> data;
    ArrayList<String> colNames;

    public ResultConector(ArrayList<ArrayList<ArrayList<String>>> data, ArrayList<String> colNames) {
        this.data = data;
        this.colNames = colNames;
    }
    
    public ArrayList<ArrayList<ArrayList<String>>> getData(){
        return data;
    }
    
    public ArrayList<String> getNames(){
        return colNames;
    }
}
