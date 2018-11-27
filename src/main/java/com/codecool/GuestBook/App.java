package com.codecool.GuestBook;

import com.codecool.GuestBook.Server.TestForm;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;


public class App 
{
    public static void main ( String[] args ) throws Exception{

//        DaoGB dao = new DaoGBpostgress();


        /////  test mickey mouse:
            // create a server on port 8000
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            // set routes
            server.createContext("/form", new TestForm());
            server.setExecutor(null); // creates a default executor

            // start listening
            server.start();

    }
}
