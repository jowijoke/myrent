package controllers;

import play.*;
import play.mvc.*;

import models.*;
import java.util.*;

/**
 * @author User
 *
 */
/**
 * @author User
 *
 */
public class Landlords extends Controller {
	public static void signup() {
		render();
	}

	public static void login() {
		render();
	}

	public static void logout() {
		session.clear();
		Logger.info("landlord out");
		Welcome.index();
	}

	public static void index() {
		{
			Landlord landlord = Landlords.getCurrentLandlord();
			if (landlord == null)
			{
				Logger.info("Landlord class : Unable to getCurrentLandlord");
				Landlords.login();
			}
			else
			{
				List<Residence> residenceAll = Residence.findAll();
				List<Residence> landlordId = new ArrayList();
				for (Residence res : residenceAll){
					
					//stating if logged in landlord's id equals to residences with the same 'from' id  
					
					if (landlord.id == res.landlord.id) 
					{
						
						landlordId.add(res);
					}
				}
				
				Logger.info("Landed in Landlord Page");
				render(landlord, landlordId);
			}
		}
	}

	public static void register(Landlord landlord) {
		Logger.info(landlord.firstName + " " + landlord.lastName + " " + landlord.email
				+ " " + landlord.password);
		landlord.save();
		login();
	}
	
	public static Landlord getCurrentLandlord()
	{
		String landlordId = session.get("logged_in_landlordid");
		if (landlordId == null)
		{
			return null;
		}
		
		Landlord logged_in_landlord = Landlord.findById(Long.parseLong(landlordId));
		Logger.info("Logged in landlord is " + logged_in_landlord.firstName);
		return logged_in_landlord;
		
	}

	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate with " + email + ":" + password);

		Landlord landlord = Landlord.findByEmail(email);
		if ((landlord != null) && (landlord.checkPassword(password) == true)) {
			Logger.info("Successful authentication of " + landlord.firstName);
			session.put("logged_in_landlordid", landlord.id);
			Landlords.index();
		} else {
			Logger.info("Authentication failed");
			login();
		}
	}
	
/**
 * Render edit Profile page for landlord to edit their profile.
 */
	public static void editProfile() {
		
		{
			Landlord landlord = Landlords.getCurrentLandlord();
			if (landlord == null)
			{
				Logger.info("Landlords class : Unable to getCurrentLandlord");
				Landlords.login();
			}
			else
			{
			Logger.info("Landed in editProfile Page");
			render(landlord);
			}
		}
	}

	
	public static void changeProfile(String firstName, String lastName, String address1, String address2, String city, String county ) 
	{
		String landlordId = session.get("logged_in_landlordid");
		Landlord landlord = Landlord.findById(Long.parseLong(landlordId));
		landlord.firstName = firstName;
		landlord.lastName = lastName;
		landlord.address1 = address1;
		landlord.address2 = address2;
		landlord.city = city;
		landlord.county = county;
			
		landlord.save();
		Logger.info("Profile saved");
		Landlords.index();

	}
	
	public static void deleteResidence(String eircode)
	{
		
		Residence residence = Residence.findByEircode(eircode);
	    residence.delete();//deleting residence form the dataabse in the residence model.
	    index();
	}
	
	
	/**
	 * Render edit Residence page using the eircode the landlord has chosen
	 * @param eircode
	 */
	public static void editResidence(String eircode)
	
	{
		Residence residence = Residence.findByEircode(eircode);
		Logger.info("Opening editResidence page for eircode " + eircode);
		render(residence);
	}
	
	public static void updateResidence(String eircode, int rent)
	{
		Residence residence = Residence.findByEircode(eircode);
		Logger.info("Updating residence " + eircode + " rent to : " + rent);
		residence.rent = rent;
		residence.save();
		Logger.info("Residence " + eircode + "Updated");
		Landlords.index();
	}
	
}
