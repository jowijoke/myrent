package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

	@Entity
	public class Tenant extends Model {
		public String firstName;
		public String lastName;
		public String email;
		public String password;
		public String residence;
		public Date dateRegistered;
		
		public Tenant(String firstName, String lastName, String email, String password, String residence) 
		{
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.password = password;
			this.residence = residence;
			dateRegistered = new Date();

		}

		public static Tenant findByEmail(String email) {
			return find("email", email).first();
		}

		public boolean checkPassword(String password) {
			return this.password.equals(password);
		}
	}
