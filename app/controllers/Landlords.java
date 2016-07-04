package controllers;

import play.*;
import play.mvc.*;

import models.*;
import models.Landlord;

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
		render();
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
			InputData.index();
		} else {
			Logger.info("Authentication failed");
			login();
		}
	}
}