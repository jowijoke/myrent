package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Landlord extends Model {
	public String firstName;
	public String lastName;
	public String email;
	public String password;
	public String address1;
	public String address2;
	public String city;
	public String county;
	
	@OneToMany(mappedBy = "landlord" , cascade = CascadeType.ALL)
	public List<Residence> residences = new ArrayList<Residence>();
	
	
	public Landlord(String firstName, String lastName, String email, String password, String address1, String address2, String city, String county) 
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.county = county;

	}

	public static Landlord findByEmail(String email) {
		return find("email", email).first();
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	/**
	 * Gets total rent for each particular landlord.
	 * @return total rent of the particular landlord
	 */
	public int getTotalRent()
	{
		int total = 0;
		for(Residence r : this.residences)
		{
			total += r.rent;
		}
		
		return total;
		
	}
	
	public static int getTotalRentForAllLandlords()
	{
		int total = 0;
		List<Landlord> landlordall = Landlord.findAll();
		for(Landlord l : landlordall)
		{
			total += l.getTotalRent();
		}
		return total;
	}
	
	public int getRentPercentage()
	{
		return getTotalRent()*100/getTotalRentForAllLandlords();//java convention to multiply first before division in relation to integers.
		
	}
}