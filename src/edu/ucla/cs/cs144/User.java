package edu.ucla.cs.cs144;

public class User {
   private String id;
   private String rating;
   private String location;
   private String country;

   public User() {}
    
	public String getId() {
		return id;
	}

	public String getRating() {
		return rating;
	}

	public String getLocation() {
		return location;
	}

	public String getCountry() {
		return country;
	}

	public void setId(String id) {
		this.id = id;
	}
	    
	public void setRating(String rating) {
		this.rating = rating;
	}
	    	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
}