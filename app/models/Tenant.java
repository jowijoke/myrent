package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

	@Entity
	public class Tenant extends Model {
		public String firstName;
		public String lastName;
		public String email;
		public String password;
		
		@OneToOne
		public Residence residence;
		
		public Tenant(String firstName, String lastName, String email, String password) 
		{
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.password = password;

		}

		public static Tenant findByEmail(String email) {
			return find("email", email).first();
		}

		public boolean checkPassword(String password) {
			return this.password.equals(password);
		}

		public void removeResidence(Residence residence) {
			List<Residence> allresidence = Residence.findAll();
			Residence thisResidence = null;
			
			for (Residence res : allresidence){
				if (res.id == residence.id){
					thisResidence = res;
				}
			}
			allresidence.remove(thisResidence);
			
			save();
		}
	}
