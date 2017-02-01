/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artatlas.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
 
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

import com.artatlas.cors.CORSFilter;
/**
 * @author wzuniga
 * 
 */
 
@SuppressWarnings("restriction")
public class HTTPServerArtAtlasMain {
 
    public static void main(String[] args) throws IOException {
        System.out.println("Starting Embedded Jersey HTTPServer...\n");
        HttpServer HTTPServer = createHttpServer();
        HTTPServer.start();
        System.out.println(String.format("\nJersey Application Server started with WADL available at " + "%sapplication.wadl\n", getURI()));
        System.out.println("Started Embedded Jersey HTTPServer Successfully !!!");
    }
 
        private static HttpServer createHttpServer() throws IOException {
        ResourceConfig ResourceConfig = new PackagesResourceConfig("com.artatlas.api.rest");
        
        ResourceConfig.getContainerResponseFilters().add(CORSFilter.class);
        return HttpServerFactory.create(getURI(), ResourceConfig);
    }
 
    private static URI getURI() {
        return UriBuilder.fromUri("http://" + GetHostName() + "/").port(8080).build();
    }
 
    private static String GetHostName() {
        String hostName = "localhost";
        try {
            hostName = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostName;
    }
}