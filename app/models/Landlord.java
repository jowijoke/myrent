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
	public Date dateRegistered;
	
	@OneToMany(mappedBy = "from" , cascade = CascadeType.ALL)
	List<Residence> rent = new ArrayList<Residence>();
	
	
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
		dateRegistered = new Date();

	}

	public static Landlord findByEmail(String email) {
		return find("email", email).first();
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
}