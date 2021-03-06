package controllers;

import play.*;
import play.mvc.*;

import models.*;
import java.util.*;


public class Landlords extends Controller {
	public static void signup() {
		render();
	}

	public static void login() {
		render();
	}

	public static void logout() {
		
		session.remove("logged_in_landlordid");
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
				Logger.info("Landed in Landlord Page");
				render(landlord, landlord.residences); //rendering the landlord that's logged in and that lanlord's list of residences.
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
	
	public static void deleteResidence(String eircode_delete)
	{
		Landlord landlord = Landlords.getCurrentLandlord();
		Residence residence = Residence.findByEircode(eircode_delete);
		for(Residence res : landlord.residences)
		{
			Tenant t = res.tenant;
			if (t != null)
			{
				t.residence = null; //end the relationship between tenant and residence
				t.save();
			}
		}
		landlord.residences.remove(residence);// removing residence from the Landlord's List of residences.
	    landlord.save(); // Updating landlord's list with the old residence removed.
	    residence.delete();//deleting residence form the database in the residence model.

	    index();
	}
	
	
	/**
	 * Render edit Residence page using the eircode the landlord has chosen
	 * @param eircode_edit
	 */
	public static void editResidence(String eircode_edit)
	
	{
		Residence residence = Residence.findByEircode(eircode_edit);
		Logger.info("Opening editResidence page for eircode " + eircode_edit);
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
