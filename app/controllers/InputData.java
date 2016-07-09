package controllers;

import org.json.simple.JSONObject;

import models.Landlord;
import models.Residence;
import play.Logger;
import play.mvc.Controller;

public class InputData extends Controller {
	public static void index() 
	{
		Landlord landlord = Landlords.getCurrentLandlord();
		if (landlord == null)
		{
			Logger.info("InputData class : Unable to getCurrentLandlord");
			Landlords.login();
		}
		else
		{
		Logger.info("Landed in InputData Page");
		render(landlord);
		}
	}

public static void capture(String geolocation, String eircode, int area, boolean rented, int rent, int numberBedrooms, int numberBathrooms, String residenceType)
{	
	Logger.info("data recorded: " + "geolocation: "  + geolocation + " area: " + area + " rented: " + rented + " rent: " + rent + " numberBedrooms: " + numberBedrooms  + " numberBathrooms: " + numberBathrooms + " residenceType: " + residenceType);	
	Landlord landlord = Landlords.getCurrentLandlord();
	if(landlord == null)
	{
		Logger.info("Residence class : Unable to getCurrentLandlord");
		Landlords.login();
	}
	else
	{
		addData(landlord, geolocation, eircode, area, rented, rent, numberBedrooms, numberBathrooms, residenceType);
	}
	//index();
		JSONObject obj = new JSONObject();
	    String value = "<div class=\"ui tertiary inverted green fluid form segment\">"
	    		+ "Congratulations. You have successfully registered your " + residenceType +".</div>";
	    obj.put("inputdata", value);
	    renderJSON(obj);
}

private static void addData(Landlord landlord, String geolocation, String eircode, int area, boolean rented, int rent, int numberBedrooms, int numberBathrooms, String residenceType)
{
	Residence input = new Residence(landlord, geolocation, eircode, area, rented, rent, numberBedrooms, numberBathrooms, residenceType);
	input.save();
	Logger.info("Residence saved");
}
}