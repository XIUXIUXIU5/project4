/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


public class  xmlParser {
    public xmlParser() {}
    static DocumentBuilder builder;    
    static final String[] typeName = {
    "none",
    "Element",
    "Attr",
    "Text",
    "CDATA",
    "EntityRef",
    "Entity",
    "ProcInstr",
    "Comment",
    "Document",
    "DocType",
    "DocFragment",
    "Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element root, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = root.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element n, String tagName) {
        Node child = n.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element n) {
        if (n.getChildNodes().getLength() == 1) {
            Text elementText = (Text) n.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element n, String tagName) {
        Element elem = getElementByTagNameNR(n, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    

    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    /* Process one items-???.xml file.
     */
    public static Item parseXML(String text) throws IOException, ParseException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }        
    
        Document doc = null;
        try {
            doc = builder.parse(new ByteArrayInputStream(text.getBytes()));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error");
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        Element n= doc.getDocumentElement();

        Item result = new Item();

        Attr itemID = n.getAttributeNode("ItemID");  
        Element name = getElementByTagNameNR(n,"Name");
        Element[] categories = getElementsByTagNameNR(n, "Category");
        Element currently = getElementByTagNameNR(n, "Currently");
        Element buy_Price = getElementByTagNameNR(n, "Buy_Price");
        Element first_Bid = getElementByTagNameNR(n, "First_Bid");
        Element number_of_Bids = getElementByTagNameNR(n, "Number_of_Bids");
        Element bids = getElementByTagNameNR(n, "Bids");
        Element[] bidlist = getElementsByTagNameNR(bids, "Bid");
        Element location = getElementByTagNameNR(n, "Location");
        Element country = getElementByTagNameNR(n, "Country");
        Element started = getElementByTagNameNR(n, "Started");
        Element ends = getElementByTagNameNR(n, "Ends");
        Element description = getElementByTagNameNR(n, "Description");
        Attr latitude = location.getAttributeNode("Latitude");
        Attr logitude = location.getAttributeNode("Longitude");;

        ArrayList<String> categorieslist = new ArrayList<String>();

       for(int i = 0; i < categories.length; i++)
        {
            categorieslist.add(getElementText(categories[i]));
        }
        
        String[] category = new String[categorieslist.size()];
            category = categorieslist.toArray(category);

        Element seller = getElementByTagNameNR(n, "Seller");
        Attr sellerID = seller.getAttributeNode("UserID");
        Attr sellerRating = seller.getAttributeNode("Rating");

        User sellerresult = new User();
        sellerresult.setId(sellerID.getValue());
        sellerresult.setRating(sellerRating.getValue());

        ArrayList<Bid> bidslist = new ArrayList<Bid>();

        for(int i = 0; i < bidlist.length; i++)
        {
            Element amount = getElementByTagNameNR(bidlist[i],"Amount");
            Element time = getElementByTagNameNR(bidlist[i],"Time");
            Element bidder = getElementByTagNameNR(bidlist[i],"Bidder");
            Attr bidderID = bidder.getAttributeNode("UserID");
            Attr bidderRating = bidder.getAttributeNode("Rating");
            Element bidderLoc = getElementByTagNameNR(bidder,"Location");
            Element bidderCoun = getElementByTagNameNR(bidder,"Country");
            
            User bidderresult = new User();
            bidderresult.setId(bidderID.getValue());
            bidderresult.setRating(bidderRating.getValue());
            bidderresult.setLocation(getElementText(bidderLoc));
            bidderresult.setCountry(getElementText(bidderCoun));

            Bid bid = new Bid();
            bid.setBidder(bidderresult);
            bid.setTime(getElementText(time));
            bid.setAmount(getElementText(amount));

            bidslist.add(bid);
        }

        Collections.sort(bidslist);

        Bid[] bidresult = new Bid[bidslist.size()];
            bidresult = bidslist.toArray(bidresult);

        result.setId(itemID.getValue());
        result.setName(getElementText(name));
        if (currently != null) {
            result.setCurrently(getElementText(currently));            
        }
        if (buy_Price != null) {
            result.setBuy_price(getElementText(buy_Price));
        }
        result.setFirst_bid(getElementText(first_Bid));
        result.setNumber_of_bids(getElementText(number_of_Bids));
        result.setStarted(getElementText(started));
        result.setEnds(getElementText(ends));
        result.setLocation(getElementText(location));

        if (logitude != null) {
            result.setLongitude(logitude.getValue());
        }

        if (latitude != null) {
            result.setLatitude(latitude.getValue());            
        }

        result.setCountry(getElementText(country));
        result.setDescription(getElementText(description));
        result.setCategories(category);

        result.setBids(bidresult);

        result.setSeller(sellerresult);

        return result;
    }

}
