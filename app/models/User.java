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
public class User extends Model {
	public String firstName;
	public String lastName;
	public String email;
	public String password;
	public Date dateRegistered;
	
	@OneToMany(mappedBy = "from" , cascade = CascadeType.ALL)
	List<Residence> rent = new ArrayList<Residence>();
	
	
	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		dateRegistered = new Date();

	}

	public static User findByEmail(String email) {
		return find("email", email).first();
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
}