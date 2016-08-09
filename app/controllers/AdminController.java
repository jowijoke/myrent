package controllers;

import play.*;
import play.mvc.*;

import models.*;
import java.util.*;

public class AdminController extends Controller {
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

	public static void index() {
		Administrator administrator = AdminController.getCurrentAdmin();
		if (administrator == null) {
			Logger.info("Admin class : Unable to getCurrentAdmin");
			AdminController.login();
		} else {
			render(administrator);
		}
	}

	public static void register(Administrator administrator) {
		Logger.info(administrator.firstName + " " + administrator.lastName + " " + administrator.email + " "
				+ administrator.password);
		administrator.save();
		login();
	}

	public static Administrator getCurrentAdmin() {
		String adminId = session.get("logged_in_adminId");
		if (adminId == null) {
			return null;
		}

		Administrator logged_in_admin = Administrator.findById(Long.parseLong(adminId));
		Logger.info("Logged in admin is " + logged_in_admin.firstName);
		return logged_in_admin;

	}

	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate with " + email + ":" + password);

		Administrator administrator = Administrator.findByEmail(email);
		if ((administrator != null) && (administrator.checkPassword(password) == true)) {
			Logger.info("Successful authentication of " + administrator.firstName);
			session.put("logged_in_adminId", administrator.id);
			AdminController.index();
		} else {
			Logger.info("Authentication failed");
			login();
		}
	}
}