package controllers;

import play.*;
import play.mvc.*;

import models.*;

public class Contact extends Controller {
	public static void index() 
	{
		Logger.info("Landed in Contact Page");
		render();
	}

	public static void send(String firstName, String lastName, String email, String message)
	{	
		Logger.info("data recorded" + " " + firstName + " " + lastName + " " + email + " " + message);
		Message input = new Message(firstName, lastName, email, message);
		input.save();
		Logger.info("Message saved");
		render("Contact/thank.html");
}
}