package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import controllers.Accounts;
import play.Logger;
import play.db.jpa.Model;

@Entity
public class Residence extends Model{
	 
		public boolean rented;
		public int numberBedrooms;
		public String residenceType;
		
		@ManyToOne
		public User from;
		
		public Residence(User from, boolean rented, int numberBedrooms, String residenceType)
		{
			this.from = from;
			this.rented = rented;
			this.numberBedrooms = numberBedrooms;
			this.residenceType = residenceType;
		}
}