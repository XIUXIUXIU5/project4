package edu.ucla.cs.cs144;

public class Item {
   private String id;
   private String name;
   private String[] categories;
   private String currently;
   private String buy_price;
   private String first_bid;
   private String number_of_bids;
   private Bid[] bids;
   private String started;
   private String ends;
   private String location;
   private String latitude;
   private String longitude;
   private String country;
   
   private User seller;

   private String description;	

   public Item() {}
    
	public String getId() {
		return id;
	}
	    
	public String getName() {
		return name;
	}
	    
	public String[] getCategories() {
		return categories;
	}
	    
	public String getCurrently() {
		return currently;
	}
	    
	public String getBuy_price() {
		return buy_price;
	}
	    
	public String getFirst_bid() {
		return first_bid;
	}
	    
	public String getNumber_of_bids() {
		return number_of_bids;
	}
	    
	public Bid[] getBids() {
		return bids;
	}
	    
	public String getStarted() {
		return started;
	}
	
	public String getEnds() {
		return ends;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public String getCountry() {
		return country;
	}
	
	public User getSeller() {
		return seller;
	}
	
	public String getDescription() {
		return description;
	}
    
    //setter
	public void setId(String id) {
		this.id = id;
	}
	    
	public void setName(String name) {
		this.name = name;
	}
	    
	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	    
	public void setCurrently(String currently) {
		this.currently = currently;
	}
	    
	public void setBuy_price(String buy_price) {
		this.buy_price = buy_price;
	}
	    
	public void setFirst_bid(String first_bid) {
		this.first_bid = first_bid;
	}
	    
	public void setNumber_of_bids(String number_of_bids) {
		this.number_of_bids = number_of_bids;
	}
	    
	public void setBids(Bid[] bids) {
		this.bids = bids;
	}
	    
	public void setStarted(String started) {
		this.started = started;
	}
	
	public void setEnds(String ends) {
		this.ends = ends;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setSeller(User seller) {
		this.seller = seller;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
