package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Message extends Model {
	
	public String firstName;
	public String lastName;
	public String email;
	public String message;


public Message(String firstName, String lastName, String email, String message) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.message = message;
}
}
