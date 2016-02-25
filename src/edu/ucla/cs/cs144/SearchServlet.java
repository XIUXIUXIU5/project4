package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String query = request.getParameter("q");
        int numResultsToSkip = Integer.parseInt(request.getParameter("numResultsToSkip"));
        int numResultsToReturn = Integer.parseInt(request.getParameter("numResultsToReturn"));
        SearchResult[] result = new AuctionSearchClient().basicSearch(query,numResultsToSkip, numResultsToReturn);

        request.setAttribute("result", result);
        request.setAttribute("numResultsToReturn",numResultsToReturn);
        request.setAttribute("numResultsToSkip",numResultsToSkip);
        request.setAttribute("query",query);
        request.getRequestDispatcher("/searchResult.jsp").forward(request, response);
    }
}
