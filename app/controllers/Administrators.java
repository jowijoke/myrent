package controllers;

import play.*;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.*;
import utils.ResidenceRentComparator;
import models.*;
import java.util.*;

import org.json.simple.JSONObject;

public class Administrators extends Controller {
	public static void signup() {
		render();
	}

	public static void login() {
		render();
	}

	public static void logout() {

		session.remove("logged_in_adminid");
		Logger.info("admin out");
		Welcome.index();
	}

	public static void index() 
	{
		Administrator administrator = Administrators.getCurrentAdmin();
		if (administrator == null) 
		{
			Logger.info("Admin class : Unable to getCurrentAdmin");
			Administrators.login();
		}
		else 
		{
			List<Landlord> allLandlords = Landlord.findAll();
			List<Tenant> allTenants = Tenant.findAll();
			Logger.info("Landed in Admin Page");
			render(administrator, allLandlords, allTenants );
		}
		
	}
	

	public static void register(Administrator administrator)
	{
		Logger.info(administrator.email + " " + administrator.password);
		administrator.save();
		login();
	}

	public static Administrator getCurrentAdmin() 
	{
		String adminId = session.get("logged_in_adminId");
		if (adminId == null) {
			return null;
		}

		Administrator logged_in_admin = Administrator.findById(Long.parseLong(adminId));
		Logger.info("Logged in admin is " + logged_in_admin.email);
		return logged_in_admin;

	}

	public static void authenticate(String email, String password) 
	{
		Logger.info("Attempting to authenticate with " + email + ":" + password);

		Administrator administrator = Administrator.findByEmail(email);
		if ((administrator != null) && (administrator.checkPassword(password) == true)) {
			Logger.info("Successful authentication of " + administrator.email);
			session.put("logged_in_adminId", administrator.id);
			Administrators.index();
		} else {
			Logger.info("Authentication failed");
			login();
		}
	}
	
	public static void deleteLandlord(String email_landlord)
	{
		Landlord landlord = Landlord.findByEmail(email_landlord);
		Logger.info("Email: " + email_landlord);
		
	//if a landlord has tenants, the tenant/residence relationship must be terminated first before the landlord is removed.
		for(Residence res : landlord.residences)
		{
			Tenant t = res.tenant;
			if (t != null)
			{
				t.residence = null; //end the relationship between tenant and residence
				t.save();
			}
		}
	    landlord.delete();
	    index();
	}
	
	public static void deleteTenant(String email_tenant)
	{
		Tenant tenant = Tenant.findByEmail(email_tenant);
		Logger.info("Email: " + email_tenant);
		tenant.delete();
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
			else
			{
				Double lat = res.getGeolocation().getLatitude();
				Double lng = res.getGeolocation().getLongitude();
				vacantResidence.add(0, Arrays.asList(" " + res.eircode + " : " + "Tenant is " + res.tenant.firstName + " " + res.tenant.lastName ,lat.toString(),lng.toString()));
			}
		}
	      	
    renderJSON(vacantResidence);	
	}
	
	public static void charts()
	{
		Administrator administrator = Administrators.getCurrentAdmin();
		if (administrator == null) 
		{
			Logger.info("Admin class : Unable to getCurrentAdmin");
			Administrators.login();
		}
		else 
		{
			List<Residence> allRes = Residence.findAll();
			Logger.info("Landed in Admin Page");
			render(administrator, allRes );
		}
	}
	
	public static void retrieveChartInfo()
	{
		List<List<String>> chartInfo = new ArrayList<List<String>>();
		
		List<Landlord> lanAll = Landlord.findAll();
		for (Landlord lan : lanAll)
		{
			int rent = lan.getRentPercentage();
			chartInfo.add(0, Arrays.asList(lan.firstName + " " + lan.lastName, ""+rent));// "" is used to convert rent into a string.
			
		}
	      	
    renderJSON(chartInfo);
	}
	
	
	public static void reports()
	{
		Administrator administrator = Administrators.getCurrentAdmin();
		if (administrator == null) 
		{
			Logger.info("Admin class : Unable to getCurrentAdmin");
			Administrators.login();
		}
		else 
		{
			List<Residence> allResidences= Residence.findAll();
			Logger.info("Landed in Admin Report Page");
			render(administrator, allResidences );
		}
	}
	
	public static void byRented(String rentedStatus)
	{
		Administrator administrator = Administrators.getCurrentAdmin();
		if (administrator == null) 
		{
			Logger.info("Admin class : Unable to getCurrentAdmin");
			Administrators.login();
		}
		else
		{	
			List<Residence> selectedRes = null; 
			if (rentedStatus != null) {
			switch (rentedStatus) {
			case "rented":
				selectedRes = Residence.findRentedResidences();	
				break;
				
			case "vacant":
				selectedRes = Residence.findVacantResidences();
				break;
			
			}
			}
			render(administrator, selectedRes);
		}
	}
	
	public static void byResidenceType(String residenceType)
	{
		Administrator administrator = Administrators.getCurrentAdmin();
		if (administrator == null) 
		{
			Logger.info("Admin class : Unable to getCurrentAdmin");
			Administrators.login();
		}
		else
		{	List<Residence> selectedRes = null; 
			if (residenceType != null) {
			switch (residenceType) {
			case "flat":
				selectedRes = Residence.findFlatResidences();	
				break;
				
			case "studio":
				selectedRes = Residence.findStudioResidences();
				break;
			
			case "house":
				selectedRes = Residence.findHouseResidences();
				break;
			}
			}
			render(administrator, selectedRes);
		}
	}
	
	public static void byCost(String sortDirection)
	{
		Administrator administrator = Administrators.getCurrentAdmin();
		if (administrator == null) 
		{
			Logger.info("Admin class : Unable to getCurrentAdmin");
			Administrators.login();
		}
		else
		{	
			List<Residence> allRes = Residence.findAll();
			if (sortDirection != null) {
			switch (sortDirection) {
			case "ascending":
				Collections.sort(allRes, new ResidenceRentComparator());
				break;
				
			case "descending":
				Collections.sort(allRes, new ResidenceRentComparator());
				Collections.reverse(allRes);
				break;
			
			}
			}
			render(administrator, allRes);
		}
	}
	
}