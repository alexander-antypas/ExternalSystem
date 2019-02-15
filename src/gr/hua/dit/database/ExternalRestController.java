package gr.hua.dit.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.expression.ParseException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.dit.classes.External_Department;
import gr.hua.dit.classes.External_User;
import gr.hua.dit.classes.authorities;
import gr.hua.dit.classes.user;


@RestController
@RequestMapping("/ExternalUser")
public class ExternalRestController {
	String message_for_admin = "ERROR";
	String message_for_external = "ERROR";
	
    @RequestMapping(value = "/addexternal",method = RequestMethod.POST)
    public @ResponseBody String AddExternal(@RequestBody External_User ex_user) throws ParseException, IOException {
    	
    	String password = ex_user.getPassword();
    	String pw = password;
    	
		try {
			pw = new BCryptPasswordEncoder().encode(password);/////////////////// ENCODED PASSWORD
		} catch (NullPointerException e) {
			System.out.println("null!!!!");
			e.printStackTrace();
		}
		ex_user.setPassword(pw);
		
		user user = new user(ex_user.getId(),pw,1);
    	authorities auth = new authorities(ex_user.getId(),"ROLE_USER");
    	
    	try {
		message_for_external = UserService.registerNewUserAccount(user, ex_user, auth);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*HttpSession sess = request.getSession();
		sess.setAttribute("message_for_external", message_for_external);
		request.getRequestDispatcher("/signin").forward(request, response);*/
    	
        return "Done";
    }
    
    @RequestMapping(value = "/signupmenu",method = RequestMethod.GET)
    public String SignUpMenu () {
    	
    	List<External_Department> departments = UserService.showDepartments();
		
		/*HttpSession sess = request.getSession();
		sess.setAttribute("departments", departments);
		request.getRequestDispatcher("/SignUp_External").forward(request, response);*/
    	
    	return "hhh";
    }

}
