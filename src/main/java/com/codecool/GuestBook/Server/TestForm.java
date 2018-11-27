package com.codecool.GuestBook.Server;

import com.codecool.GuestBook.DAO.DaoGBpostgress;
import com.sun.net.httpserver.HttpExchange; import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestForm implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();


        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){

            DaoGBpostgress dao = new DaoGBpostgress();
            List<List<String>> listOfRecordFromDb = dao.getListOfRecordsFromDB();
            String unorderedListTag = createListOfRecordsInHTML(listOfRecordFromDb);

            response = "<html><body>" +
                    "<form method=\"POST\">\n" +
                    "<h1>Guest Book Online</h1>\n" +
                    unorderedListTag +
                    "  Name:<br>\n" +
                    "  <input type=\"text\" name=\"name\" value=\"Enter Your name\">\n" +
                    "  <br><br><br>\n" +
                    "  Your record in guest book:<br>\n" +
                    "  <textarea rows=\"4\" cols=\"50\" name=\"record\"></textarea>\n" +
                    "  <br><br>\n" +
                    "  <input type=\"submit\" value=\"Save record\">\n" +
                    "</form> " +
                    "</body></html>";
        }

        // If the form was submitted, retrieve it's content.
        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println(formData);
            Map inputs = parseFormData(formData);

            response = "<html><body>" +
                    "<h1>Hello " +
                    inputs.get("name") + " " +
                    "!</h1>" +
                    "</body><html>";
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     * TestForm data is sent as a urlencoded string. Thus we have to parse this string to get data that we want.
     * See: https://en.wikipedia.org/wiki/POST_(HTTP)
     */
    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private String createListOfRecordsInHTML(List<List<String>> listOfRecords) {
        String listItem = "<ul>";
        StringBuilder sb = new StringBuilder();
        for (List<String> record : listOfRecords ) {
            sb.append("<li>");
            sb.append("<p>Name: ");
            sb.append(record.get(0));
            sb.append("</p><br>");
            sb.append("<p>Date: ");
            sb.append(record.get(1));
            sb.append("</p><br>");
            sb.append("<p>");
            sb.append(record.get(2));
            sb.append("</p></li>");
        }
        sb.append("</ul><br><br>");
        return sb.toString();
    }
}
