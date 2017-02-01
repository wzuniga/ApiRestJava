/*
 * Program made by wzuniga - System Engineer
 * https://github.com/wzuniga/ApiRestJava
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
