package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Residence;
import models.Tenant;
import models.Landlord;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import utils.Circle;
import utils.Geodistance;
import utils.LatLng;

public class ReportController extends Controller
{
  /**
   * This method executed before each action call in the controller.
   * Checks that a tenant has logged in.
   * If no tenant logged in the tenant is presented with the log in screen.
   */
  @Before
  static void checkAuthentification()
  {
    if(session.contains("logged_in_tenantid") == false)
      Tenants.login();
  }

  /**
   *  Generates a Report instance relating to all residences within circle
   * @param radius    The radius (metres) of the search area
   * @param latcenter The latitude of the centre of the search area
   * @param lngcenter The longtitude of the centre of the search area
   */
  public static void generateReport(double radius, double latcenter, double lngcenter)
  {
    // All reported residences will fall within this circle
    Circle circle = new Circle(latcenter, lngcenter, radius);
    Tenant tenant = Tenants.getCurrentTenant();
    List<Residence> residences = new ArrayList<Residence>();
    // Fetch all residences and filter out those within circle and that are vacant
    List<Residence> residencesAll = Residence.findAll();
    for (Residence res : residencesAll)
    {
      LatLng residenceLocation = res.getGeolocation();
      if (res.tenant == null && Geodistance.inCircle(residenceLocation, circle))
      {
        residences.add(res);
        Logger.info("Residence Added");
      }
    }
    render("ReportController/report.html", tenant, circle, residences);
  }

  /**
   * Render the views/ReporController/index.html template
   * This presents a map and resizable circle to indicate a search area for residences
   */
  public static void index()
  {
    render();
  }
}