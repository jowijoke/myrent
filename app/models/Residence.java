package models;

import play.*;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import controllers.Landlords;
import play.Logger;
import play.db.jpa.Model;
import utils.LatLng;

@Entity
public class Residence extends Model{
	 
		public boolean rented;
		public String geolocation;
		public int area;
		public int rent;
		public int numberBedrooms;
		public String residenceType;
		public int numberBathrooms;
		public String eircode;
		
		
		@ManyToOne
		public Landlord from;
		
		public Residence(Landlord from, String geolocation,String eircode, int area, boolean rented, int rent, int numberBedrooms, int numberBathrooms, String residenceType)
		{
			this.from = from;
			this.geolocation = geolocation;
			this.eircode = eircode;
			this.area = area;
			this.rented = rented;
			this.rent = rent;
			this.numberBedrooms = numberBedrooms;
			this.numberBathrooms = numberBathrooms;
			this.residenceType = residenceType;
		}

		/*
		 * Send String geolocation to LatLng.java to change geolocaton's primitive data type.
		 */
		public LatLng getGeolocation() {
			return LatLng.toLatLng(geolocation);
			
		}
		
}