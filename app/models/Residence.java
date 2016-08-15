package models;

import play.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import controllers.Landlords;
import controllers.Tenants;
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
		
		public static List<Residence> findVacantResidences()
		{	
			List<Residence> allRes = Residence.findAll();
			List<Residence> vacantRes = new ArrayList();
			for(Residence r : allRes)
			{
				if(r.tenant == null)
				{
					vacantRes.add(r);
				}
			}
			return vacantRes;
		}
		public static List<Residence> findRentedResidences()
		{	
			List<Residence> allRes = Residence.findAll();
			List<Residence> rentedRes = new ArrayList();
			for(Residence r : allRes)
			{
				if(r.tenant != null)
				{
					rentedRes.add(r);
				}
			}
			return rentedRes;
		}
		
		/*
		 * Send String geolocation to LatLng.java to change geolocaton's primitive data type.
		 */
		public LatLng getGeolocation() {
			return LatLng.toLatLng(geolocation);
			
		}

		public static List<Residence> findFlatResidences() {
			List<Residence> allRes = Residence.findAll();
			List<Residence> flatRes = new ArrayList();
			for(Residence r : allRes)
			{
				if(r.residenceType.contains("flat"))
				{
					flatRes.add(r);
				}
			}
			return flatRes;
		}

		public static List<Residence> findStudioResidences() {
			List<Residence> allRes = Residence.findAll();
			List<Residence> studioRes = new ArrayList();
			for(Residence r : allRes)
			{
				if(r.residenceType.contains("studio"))
				{
					studioRes.add(r);
				}
			}
			return studioRes;
		}

		public static List<Residence> findHouseResidences() {
			List<Residence> allRes = Residence.findAll();
			List<Residence> houseRes = new ArrayList();
			for(Residence r : allRes)
			{
				if(r.residenceType.contains("house"))
				{
					houseRes.add(r);
				}
			}
			return houseRes;
		}

		
	}
		