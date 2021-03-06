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
			Logger.info("Landed in Tenant Page");
			render(tenant, unoccupied);
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
	
	/**
	 * Disconnect the relationship between the tenant and the residence model
	 * @param eircode: the tenants post code that's displayed on the tenant's page 
	 */
	public static void endTenancy(String currenteircode)
	{
		Tenant tenant = Tenants.getCurrentTenant();
		if(tenant.residence == null)// if the tenant is not renting, refresh the page 
		{
			index();
			
		}
		else //remove relationship by making residence equal to null 
		{
			tenant.residence = null;	
		}
		Logger.info("removing residence from the Tenant");
	    tenant.save();
	    
	    index();
	}
	    
	
	
	public static void changeTenancy(String eircode_unoccupied)
	{
		Tenant tenant = Tenants.getCurrentTenant();
		Residence residence = Residence.findByEircode(eircode_unoccupied);
		Logger.info("Changing residence to " + eircode_unoccupied);
		residence.tenant = tenant;
		tenant.residence = residence;
		Logger.info(" Tenants new Residence " + eircode_unoccupied + " Updated ");
	    residence.save();
		tenant.save();
		Tenants.index();
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
