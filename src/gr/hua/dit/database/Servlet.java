package gr.hua.dit.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import gr.hua.dit.classes.External_User;
import gr.hua.dit.classes.authorities;
import gr.hua.dit.classes.user;
import gr.hua.dit.database.DBApplication;
import gr.hua.dit.classes.External_Department;

@MultipartConfig(maxFileSize = 1699999999)
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	// @Autowired
	// private PasswordEncoder passwordEncoder;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// ADD APPLICATION
				String application = request.getParameter("application");

				if ("Submit".equals(application)) {

					Date date = new Date();

					String id = request.getParameter("user_id").toString();
					int isChecked = 0;

					byte[] familyfile = null;
					byte[] financiallyfile = null;
					byte[] localityfile = null;

					try {
						Part family = request.getPart("family");
						InputStream is1 = family.getInputStream();
						familyfile = IOUtils.toByteArray(is1);

						Part financially = request.getPart("financially");
						InputStream is2 = financially.getInputStream();
						financiallyfile = IOUtils.toByteArray(is2);

						Part locality = request.getPart("locality");
						InputStream is3 = locality.getInputStream();
						localityfile = IOUtils.toByteArray(is3);

					} catch (IllegalStateException e) {
						System.out.print("FILE TOO BIG!");
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}

					DBApplication.addApplication(date, id, isChecked, familyfile, financiallyfile,
							localityfile);
					
					HttpSession sess = request.getSession();
					sess.setAttribute("id", id);
					request.getRequestDispatcher("/info_user").forward(request, response);
				}
		
				/// ADD EXTERNAL_USER
				String message_for_external = "ERROR";
				String add_external = request.getParameter("add_external");
				if ("add".equals(add_external)) {

					String username = request.getParameter("username").toString();// USERNAME
					String surname = request.getParameter("surname").toString();//// SURNAME
					String id = request.getParameter("id").toString();////////////// ID
					String password = request.getParameter("password").toString();// PASSWORD
					String uni = request.getParameter("uni").toString();//////////// UNIVERSITY
					int year = Integer.parseInt(request.getParameter("year"));////// YEAR OF BIRTH
					int age = Integer.parseInt(request.getParameter("age"));//////// AGE
					String email = request.getParameter("email").toString();//////// EMAIL
					int yoe = Integer.parseInt(request.getParameter("yoe"));//////// YEAR OF ENROLLMENT
					int pro = 0;//////////////////////////////////////////////////// PROGRESS

					Calendar cal = Calendar.getInstance();
					int yeartoday = cal.get(Calendar.YEAR);
					int sem = (yeartoday - yoe) * 2 + 1;//////////////////////////// SEMESTER

					String pw = password;
					try {
						pw = new BCryptPasswordEncoder().encode(password);/////////////////// ENCODED PASSWORD
					} catch (NullPointerException e) {
						System.out.println("null!!!!");
						e.printStackTrace();
					}

					user user1 = new user(id, pw, 1);
					authorities auth = new authorities(id, "ROLE_USER");
					External_User user2 = new External_User(id, username, surname, pw, year, age, email, uni, yoe, sem, pro);

					try {
						message_for_external = UserService.registerNewUserAccount(user1, user2, auth);
					} catch (SQLException e) {
						e.printStackTrace();
					}

					HttpSession sess = request.getSession();
					sess.setAttribute("message_for_external", message_for_external);
					request.getRequestDispatcher("/signin").forward(request, response);

				}

				///CHECK APP
				String check_app = request.getParameter("check_app");
				if ("check_app".equals(check_app)) {
					
					String id = request.getParameter("id").toString();//ID
					
					String message = UserService.check(id);

					HttpSession sess = request.getSession();
					sess.setAttribute("message", message);
					request.getRequestDispatcher("/info_user").forward(request, response);
				}
				////SHOW EXTERNAL DEPARTMENTS
				String show_dep = request.getParameter("show_dep");
				if ("show_dep".equals(show_dep)) {
					List<External_Department> departments = UserService.showDepartments();
					
					HttpSession sess = request.getSession();
					sess.setAttribute("departments", departments);
					request.getRequestDispatcher("/SignUp_External").forward(request, response);
					
				}

	}

}
