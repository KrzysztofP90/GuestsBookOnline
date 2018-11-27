package com.codecool.GuestBook.Server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class ServerHandler {

//    public void createServerAndRunIt() throws Exception {
//
//        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
//
//        server.createContext("/hello", new Hello());
//        server.setExecutor(null); // creates a default executor
//
//        // start listening
//        server.start();
//
//    }

    private HttpContext createNewContext(String path, HttpHandler handler, HttpServer server) {
        return server.createContext(path, handler);
    }
}
