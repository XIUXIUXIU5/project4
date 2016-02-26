package edu.ucla.cs.cs144;

import java.io.IOException;
import java.net.HttpURLConnection;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.net.URI;
import java.io.BufferedReader;
import javax.servlet.ServletOutputStream;
import java.io.InputStreamReader;

public class ProxyServlet extends HttpServlet implements Servlet {
      private final String USER_AGENT = "Mozilla/5.0";
    
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String query = request.getParameter("q");

        query = escapeSpace(query);

      String urlString = "";
      try{
         // escape characters
         URI uri = new URI(
            "http", 
            "google.com", 
            "/complete/search",
            "output=toolbar&q=" + query,
            null);
         
         urlString = uri.toASCIIString();
      } catch(Exception e){
        
      }
      
         
        URL obj = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
        // optional default is GET
        con.setRequestMethod("GET");
 
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
 
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + urlString);
        System.out.println("Response Code : " + responseCode);
 
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response1 = new StringBuffer();
      
      	response.addHeader("Access-Control-Allow-Origin", "*"); 	
       	response.setContentType("text/xml");

        ServletOutputStream sout = response.getOutputStream();
  
        while ((inputLine = in.readLine()) != null) {
            response1.append(inputLine);
            sout.write(inputLine.getBytes());
        }
        in.close();
 
        sout.flush();
 
    }

    public String escapeSpace(String q)
    {
        String result = "";
        for (int i = 0; i < q.length() ;i++ ) {
            char c = q.charAt(i);
            if (c == ' ') {
                result += "%20";
            }
            else
                result += c;
        }
        return result;
    }
}
