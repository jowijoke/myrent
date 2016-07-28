package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

import models.Landlord;
import models.Residence;
import models.Tenant;
import play.Logger;
import play.mvc.Controller;

public class Tenants extends Controller {
	public static void signup() {
		render();
	}

	public static void login() {
		render();
	}

	public static void logout() {
		session.clear();
		Logger.info("tenant out");
		Welcome.index();
	}

	
	public static void index() {
		Tenant tenant = Tenants.getCurrentTenant();
		if (tenant == null)
		{
			Logger.info("Tenant class : Unable to getCurrentTenant");
			Tenants.login();
		}
		else
		{
			List<Residence> residenceAll = Residence.findAll();
			List<Residence> unoccupied = new ArrayList();
			for (Residence res : residenceAll)
			{
				
				//stating if a residence has no tenant, add it to the unoccupied ArrayList.
				
				if (res.tenant == null) 
				{
					unoccupied.add(res);
					
				}
			}
			
			
			List<Residence> tenantStatus = new ArrayList(); //an ArrayList that stores the Tenant's status whether they're renting or not.
			for(Residence r : residenceAll)
			{
				 if (tenant.residence == null) // if a tenant is not renting
				{
					Residence msg = new Residence(null, null, "Seeking Residence", 0, 0, 0, 0, null); //create fake residence with message "not renting" as the eircode.
					tenantStatus.add(msg);
					Logger.info(tenant.firstName + " rent status: is not renting");
					break;
				}
				 
				 else if (r.eircode == tenant.residence.eircode) // if tenant is renting, show the residence eircode
				{
					tenantStatus.add(r);
					Logger.info(tenant.firstName + " rent status: is renting " + tenant.residence.eircode);
				}
				
			}
			Logger.info("Landed in Tenant Page");
			render(tenant, unoccupied, tenantStatus);
		}
	}

	public static void register(Tenant tenant) {
		Logger.info(tenant.firstName + " " + tenant.lastName + " " + tenant.email
				+ " " + tenant.password);
		tenant.save();
		login();
	}
	
	public static Tenant getCurrentTenant()
	{
		String tenantId = session.get("logged_in_tenantid");
		if (tenantId == null)
		{
			return null;
		}
		
		Tenant logged_in_tenant = Tenant.findById(Long.parseLong(tenantId));
		Logger.info("Logged in tenant is " + logged_in_tenant.firstName);
		return logged_in_tenant;
		
	}

	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate with " + email + ":" + password);

		Tenant tenant = Tenant.findByEmail(email);
		if ((tenant != null) && (tenant.checkPassword(password) == true)) {
			Logger.info("Successful authentication of " + tenant.firstName);
			session.put("logged_in_tenantid", tenant.id);
			Tenants.index();
		} else {
			Logger.info("Authentication failed");
			login();
		}
	}
	
	public static void endTenancy(String eircode)
	{
		Tenant tenant = Tenants.getCurrentTenant();
		Residence residence = Residence.findByEircode(eircode);
		tenant.removeResidence(residence);// removing residence from the Tenant.
	    tenant.save();
	    index();
	}
	
	public static void retrieveMarker()
	{
		List<List<String>> vacantResidence = new ArrayList<List<String>>();
		
		List<Residence> residenceAll = Residence.findAll();
		for (Residence res : residenceAll)
		{
			//stating if a residence has no tenant, add it to the vacantResidence ArrayList.
			
			if (res.tenant == null) 
			{
				Double lat = res.getGeolocation().getLatitude();
				Double lng = res.getGeolocation().getLongitude();
				vacantResidence.add(0, Arrays.asList(" " + res.eircode + " : " + "No Tenant" ,lat.toString(),lng.toString()));
			}
			
		}
	      	
    renderJSON(vacantResidence);	
	}

}
