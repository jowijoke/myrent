package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import controllers.Tenants;
import play.Logger;
import play.db.jpa.Model;

	@Entity
	public class Administrator extends Model {
		public String email;
		public String password;
		
		public Administrator() 
		{
			email = "admin@witpress.ie";
			password = "secret";

		}
		
		public static Administrator findByEmail(String email) {
			return find("email", email).first();
		}

		public boolean checkPassword(String password) {
			return this.password.equals(password);
		}

		
	}
