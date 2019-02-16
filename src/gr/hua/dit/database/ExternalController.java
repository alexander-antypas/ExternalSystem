package gr.hua.dit.database;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import gr.hua.dit.classes.Application;

@Controller
public class ExternalController {

	
	@RequestMapping("/definer")
	public String Definer() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getName();
		
		boolean hasUserRole = authentication.getAuthorities().stream()
		          .anyMatch(r -> r.getAuthority().equals("ROLE_USER"));
		
		if(hasUserRole){
			List<Application> applications = DBApplication.showApplications();
			for (int i=0; i<applications.size(); i++) {
				Application app= applications.get(i);
				if (app.getApplication_id().equals(id)) {
					return "info_user";
				}
			}	
			return "application";
		}
		
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
	
	@RequestMapping("/signin")
	public String Signinpage() {
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
}
