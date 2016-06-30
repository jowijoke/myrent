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

public static void capture(String geolocation, boolean rented, int rent, int numberBedrooms, int numberBathrooms, int area, String residenceType)
{	
	Logger.info("data recorded" + " " + geolocation + " " + rented + " " + rent + " " + numberBedrooms  + numberBathrooms + area + residenceType);
	
	User user = Accounts.getCurrentUser();
	if(user == null)
	{
		Logger.info("Residence class : Unable to getCurrentuser");
		Accounts.login();
	}
	else
	{
		addData(user, geolocation, rented, rent, numberBedrooms, numberBathrooms, area, residenceType);
	}
	//index();
	JSONObject obj = new JSONObject();
    String value = "<div class=\"ui tertiary inverted green fluid form segment\">"
    		+ "Congratulations. You have successfully registered your " + residenceType +".</div>";
    obj.put("inputdata", value);
    renderJSON(obj);
}

private static void addData(User user, String geolocation, boolean rented, int rent, int numberBedrooms, int numberBathrooms, int area, String residenceType)
{
	Residence input = new Residence(user, geolocation, rented, rent, numberBedrooms, numberBathrooms, area, residenceType);
	input.save();
	Logger.info("Residence saved");
}
}