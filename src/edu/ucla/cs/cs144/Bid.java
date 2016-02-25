package edu.ucla.cs.cs144;

public class Bid {
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
}