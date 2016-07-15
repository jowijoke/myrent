package models;

import play.*;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import controllers.Landlords;
import play.Logger;
import play.db.jpa.Model;
import utils.LatLng;

@Entity
public class Residence extends Model{
	 
		public String geolocation;
		public int area;
		public int rent;
		public int numberBedrooms;
		public String residenceType;
		public int numberBathrooms;
		public String eircode;
		public Date dateRegistered;
		
		
		@ManyToOne
		public Landlord landlord;
		
		@OneToOne(mappedBy = "residence")
		public Tenant tenant;
		
		public Residence(Landlord landlord, String geolocation,String eircode, int area, int rent, int numberBedrooms, int numberBathrooms, String residenceType)
		{
			this.landlord = landlord;
			this.geolocation = geolocation;
			this.eircode = eircode;
			this.area = area;
			this.rent = rent;
			this.numberBedrooms = numberBedrooms;
			this.numberBathrooms = numberBathrooms;
			this.residenceType = residenceType;
			dateRegistered = new Date();
		}
		
		public static Residence findByEircode(String eircode) {
			return find("eircode", eircode).first();
		}

		/*
		 * Send String geolocation to LatLng.java to change geolocaton's primitive data type.
		 */
		public LatLng getGeolocation() {
			return LatLng.toLatLng(geolocation);
			
		}
		
		
		
		}
		