package controllers;

import play.*;
import play.mvc.*;


import java.util.Date;

import models.*;

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

public static void capture(String geolocation, boolean rented, int rent, int numberBedrooms, String residenceType)
{	
	Logger.info("data recorded" + " " + geolocation + " " + rented + " " + rent + " " + numberBedrooms  + residenceType);
	
	User user = Accounts.getCurrentUser();
	if(user == null)
	{
		Logger.info("Residence class : Unable to getCurrentuser");
		Accounts.login();
	}
	else
	{
		addData(user, geolocation, rented, rent, numberBedrooms, residenceType);
	}
	index();
}

private static void addData(User user, String geolocation, boolean rented, int rent, int numberBedrooms, String residenceType)
{
	Residence input = new Residence(user, geolocation, rented, rent, numberBedrooms, residenceType);
	input.save();
}
}