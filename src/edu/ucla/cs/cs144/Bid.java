package edu.ucla.cs.cs144;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;

public class Bid implements Comparable<Bid> {
   private User bidder;
   private String time;
   private String amount;

   public Bid() {}
    
	public User getBidder() {
		return bidder;
	}

	public String getTime() {
		return time;
	}

	public String getAmount() {
		return amount;
	}

	public void setBidder(User bidder) {
		this.bidder = bidder;
	}
	    
	public void setTime(String time) {
		this.time = time;
	}
	    	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
 	@Override
  	public int compareTo(Bid o) {
  		DateFormat format = new SimpleDateFormat("MMM-dd-yy hh:mm:ss");
  		try{
		Date date = format.parse(this.time);
		Date o_date = format.parse(o.time);
    	return o_date.compareTo(date);
  		} catch (ParseException e ){
  			System.out.println(e);
  		}
  		return 0;
    }
}
