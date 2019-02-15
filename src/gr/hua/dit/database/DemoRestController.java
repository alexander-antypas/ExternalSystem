package gr.hua.dit.database;

import org.springframework.web.bind.annotation.RequestMapping;

import gr.hua.dit.classes.Application;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/*
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import antlr.collections.List;
import gr.hua.dit.classes.*;*/

@Controller
public class DemoRestController {

	@RequestMapping("/definer")
	public String Definer() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getName();

		boolean hasUserRole = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_USER"));

		boolean hasAdminRole = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

		boolean hasSecretariatnRole = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_SECRETARIAT"));

		boolean hasProfessorRole = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_PROFESSOR"));

		if (hasUserRole) {
			List<Application> applications = DBApplication.showApplications();
			for (int i = 0; i < applications.size(); i++) {
				Application app = applications.get(i);
				if (app.getApplication_id().equals(id)) {
					return "info_user";
				}
			}
			return "application";
		} else if (hasProfessorRole) {
			return "user-professor";
		} else if (hasSecretariatnRole) {
			return "secretary_menu";
		} else if (hasAdminRole) {
			return "admin";
		}
		return "signin";
	}

	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}
	
	@RequestMapping("/SignUp_External")
	public String SignUp_External() {
		return "SignUp_External";
	}
	
	@RequestMapping("/info_user")
	public String info_user() {
		return "info_user";
	}
	
	@RequestMapping("/denied")
	public String denied() {
		return "access_denied";
	}
	
	@RequestMapping("/")
	public String ShowMyPage() {
		return "signin";
	}
	
	@RequestMapping("/application")
	public String Application() {
		return "application";
	}
	
	@RequestMapping("/about")
	public String About() {
		return "about";
	}
	
	@RequestMapping("/help")
	public String Help() {
		return "help";
	}
	
	@RequestMapping("/contact")
	public String Contact() {
		return "contact";
	}

	/*
	 * 
	 * // inject the customer dao
	 * 
	 * @Autowired private CustomerDAO customerDAO;
	 * 
	 * @GetMapping("/customers") public List<Customer> listCustomers() { // get
	 * customers from dao List<Customer> customers = customerDAO.getCustomers();
	 * return customers; }
	 * 
	 * @GetMapping("/customers/{customerId}") public Customer
	 * getCustomer(@PathVariable int customerId) {
	 * 
	 * Customer theCustomer = customerDAO.getCustomer(customerId);
	 * 
	 * if (theCustomer == null) { throw new
	 * CustomerNotFoundException("Customer id not found " + customerId); }
	 * 
	 * return theCustomer; }
	 * 
	 * @PostMapping("/customers") public Customer addCustomer(@RequestBody Customer
	 * theCustomer) {
	 * 
	 * // also just in case the pass an id in JSON ... set id to 0 // this is force
	 * a save of new item ... instead of update // sample data
	 * (raw-application/json) // { // "firstName": "Alekos", // "lastName":
	 * "Sakellarios", // "email": "alekos@hua.gr" // }
	 * 
	 * theCustomer.setId(0);
	 * 
	 * customerDAO.saveCustomer(theCustomer);
	 * 
	 * return theCustomer; }
	 * 
	 * @PutMapping("/customers") public Customer updateCustomer(@RequestBody
	 * Customer theCustomer) {
	 * 
	 * // sample data (raw-application/json) // { // "id": "78", // "firstName":
	 * "Alekos", // "lastName": "Sakellarios", // "email": "alekos@hua.gr" // }
	 * 
	 * customerDAO.saveCustomer(theCustomer);
	 * 
	 * return theCustomer; }
	 * 
	 * @DeleteMapping("/customers/{customerId}") public String
	 * deleteCustomer(@PathVariable int customerId) {
	 * 
	 * Customer theCustomer = customerDAO.getCustomer(customerId);
	 * 
	 * if (theCustomer == null) { throw new
	 * CustomerNotFoundException("Customer id not found " + customerId); }
	 * customerDAO.deleteCustomer(customerId); return "Deleted customer id - " +
	 * customerId; }
	 */

}
