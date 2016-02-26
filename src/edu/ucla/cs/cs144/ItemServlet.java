package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ItemServlet extends HttpServlet implements Servlet {       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String id = request.getParameter("id");
        String result = new AuctionSearchClient().getXMLDataForItemId(id);
        try {
	        xmlParser parser = new xmlParser();
	        Item item = parser.parseXML(result);
	        request.setAttribute("result", item);
        } catch(Exception e){
        	StringWriter sw = new StringWriter();
  			e.printStackTrace(new PrintWriter(sw));
  			String stackTrace = sw.toString();
        	request.setAttribute("result", stackTrace);
        	System.out.println(e);
        }

        request.getRequestDispatcher("/getItemResult.jsp").forward(request, response);
    }
}
