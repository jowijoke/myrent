package controllers;

import java.util.ArrayList;
import java.util.List;

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
			for (Residence res : residenceAll){
				
				//stating if a residence has no tenant, add it to the unoccupied arraylist.
				
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

}
