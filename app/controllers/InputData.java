package controllers;

import play.*;
import play.mvc.*;
import utils.LatLng;

import java.util.Date;

import models.*;
import org.json.simple.JSONObject;

public class InputData extends Controller {
	public static void index() 
	{
		User user = Accounts.getCurrentUser();
		if (user == null)
		{
			Logger.info("InputData class : Unable to getCurrentUser");
			Accounts.login();
		}
		else
		{
		Logger.info("Landed in InputData Page");
		render(user);
		}
	}

public static void capture(String geolocation, int area, boolean rented, int rent, int numberBedrooms, int numberBathrooms, String residenceType)
{	
	Logger.info("data recorded: " + "geolocation: "  + geolocation + " area: " + area + " rented: " + rented + " rent: " + rent + " numberBedrooms: " + numberBedrooms  + " numberBathrooms: " + numberBathrooms + " residenceType: " + residenceType);	
	User user = Accounts.getCurrentUser();
	if(user == null)
	{
		Logger.info("Residence class : Unable to getCurrentuser");
		Accounts.login();
	}
	else
	{
		addData(user, geolocation, area, rented, rent, numberBedrooms, numberBathrooms, residenceType);
	}
	//index();
		JSONObject obj = new JSONObject();
	    String value = "<div class=\"ui tertiary inverted green fluid form segment\">"
	    		+ "Congratulations. You have successfully registered your " + residenceType +".</div>";
	    obj.put("inputdata", value);
	    renderJSON(obj);
}

private static void addData(User user, String geolocation, int area, boolean rented, int rent, int numberBedrooms, int numberBathrooms, String residenceType)
{
	Residence input = new Residence(user, geolocation, area, rented, rent, numberBedrooms, numberBathrooms, residenceType);
	input.save();
	Logger.info("Residence saved");
}
}