package gr.hua.dit.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import gr.hua.dit.classes.Application;
//import gr.hua.dit.classes.External_Department;
import gr.hua.dit.classes.External_User;
//import gr.hua.dit.classes.INTERNAL_USER;
import gr.hua.dit.classes.authorities;
import gr.hua.dit.classes.user;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

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
	/*
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String button = request.getParameter("button");

		if ("Submit".equals(button)) {
			Admin admin = new Admin();

			String documents = request.getParameter("documents");

			if (documents == "") {
				String decision = "ΣΥΜΠΛΗΡΩΣΤΕ ΟΛΑ ΤΑ ΚΕΝΑ";

				HttpSession session = request.getSession();
				session.setAttribute("decision", decision);
				request.getRequestDispatcher("/documents").forward(request, response);
			} else {
				int number = Integer.parseInt(documents);
				String decision;
				if (number < 0) {
					decision = "!!";
					HttpSession session = request.getSession();
					session.setAttribute("decision", decision);
					request.getRequestDispatcher("/documents").forward(request, response);
				} else {
					decision = admin.documents(number);

					HttpSession session = request.getSession();
					session.setAttribute("decision", decision);
					request.getRequestDispatcher("/documents").forward(request, response);
				}
			}
		}

		String score = request.getParameter("score");

		if ("Points".equals(score)) {
			Admin admin = new Admin();

			String par1 = request.getParameter("stdsibling");
			String par2 = request.getParameter("numbersiblings");
			String par3 = request.getParameter("income");
			String par4 = request.getParameter("town");

			String appid = request.getParameter("appid").toString();

			if (par1 == "" || par2 == "" || par3 == "" || par4 == "") {
				String message = "ΣΥΜΠΛΗΡΩΣΤΕ ΟΛΑ ΤΑ ΚΕΝΑ";

				HttpSession sess = request.getSession();
				sess.setAttribute("message", message);
				request.getRequestDispatcher("/points").forward(request, response);

			} else {
				int stdsibling = Integer.parseInt(par1);
				int numbersiblings = Integer.parseInt(par2);
				int income = Integer.parseInt(par3);
				int town = Integer.parseInt(par4);

				int points = admin.points(stdsibling, numbersiblings, income, town);

				HttpSession sess = request.getSession();
				sess.setAttribute("points", points);
				sess.setAttribute("appid", appid);
				request.getRequestDispatcher("/points").forward(request, response);
			}

		}

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

		// VIEW ALL APPLICATIONS THAT HAVE NOT YET CHECKED

		String viewall = request.getParameter("viewall");

		if ("Repository".equals(viewall)) {

			List<Application> applications = DBApplication.showApplications();

			HttpSession sess = request.getSession();
			sess.setAttribute("applications", applications);
			request.getRequestDispatcher("/user-professor").forward(request, response);
		}

		// SEARCH

		String searchuserid = request.getParameter("searchuserid");

		if ("searchuserid".equals(searchuserid)) {
			String appid = request.getParameter("appid");

			if (appid == "") {
				String message = "ΠΛΗΚΤΡΟΛΟΓΕΙΣΤΕ ΚΑΠΟΙΟ ID";

				HttpSession sess = request.getSession();
				sess.setAttribute("message", message);
				request.getRequestDispatcher("/user-professor").forward(request, response);
			} else {

				List<Application> applications = DBApplication.showApplications();
				int b = 0;
				for (int i = 0; i < applications.size(); i++) {
					Application app = applications.get(i);
					if (app.getApplication_id().equals(appid)) {
						b = 1;
					}
				}
				if (b == 1) {
					HttpSession sess = request.getSession();
					sess.setAttribute("appid", appid);
					request.getRequestDispatcher("/documents").forward(request, response);
				} else {
					String message = "ΤΟ ID ΔΕΝ ΥΠΑΡΧΕΙ, ΠΛΗΚΤΡΟΛΟΓΕΙΣΤΕ ΚΑΠΟΙΟ ΑΛΛΟ";
					HttpSession sess = request.getSession();
					sess.setAttribute("message", message);
					request.getRequestDispatcher("/user-professor").forward(request, response);
				}
			}
		}

		// OPEN FILES OF APPLICATION WITH USER_ID
		String familydownload = request.getParameter("family");

		if ("family".equals(familydownload)) {
			String appid = request.getParameter("appid").toString();
			byte[] family = DBApplication.loadFamily(appid);
			ServletOutputStream os1 = response.getOutputStream();
			response.setContentType("file/pdf");
			os1.write(family);
			os1.close();
		}

		String financiallydownload = request.getParameter("financially");

		if ("financially".equals(financiallydownload)) {
			String appid = request.getParameter("appid").toString();

			byte[] financially = DBApplication.loadFinancially(appid);
			ServletOutputStream os2 = response.getOutputStream();
			response.setContentType("file/pdf");
			os2.write(financially);
			os2.close();
		}

		String localitydownload = request.getParameter("locality");

		if ("locality".equals(localitydownload)) {
			String appid = request.getParameter("appid").toString();

			byte[] locality = DBApplication.loadLocality(appid);
			ServletOutputStream os3 = response.getOutputStream();
			response.setContentType("file/pdf");
			os3.write(locality);
			os3.close();
		}
		// approved
		String check = request.getParameter("check");

		if ("check".equals(check)) {

			Date date = new Date();
			int is_Approved = Integer.parseInt(request.getParameter("is_Approved"));
			int points = Integer.parseInt(request.getParameter("points"));
			String check_id = request.getParameter("appid");

			String result = DBApplication.check_application(date, is_Approved, points, check_id);
			DBApplication.updateCheck_id(check_id);

			HttpSession sess = request.getSession();
			sess.setAttribute("result", result);
			request.getRequestDispatcher("/points").forward(request, response);
		}
		
		//disapproved 
		String declined = request.getParameter("declined");
		if ("declined".equals(declined)) {
			
			Date date = new Date();
			int is_Approved = Integer.parseInt(request.getParameter("is_Approved"));
			int points = Integer.parseInt(request.getParameter("points"));
			String check_id = request.getParameter("appid");
			
			String result = DBApplication.check_application(date, is_Approved, points, check_id);
			
			DBApplication.updateCheck_id(check_id);
			
			HttpSession sess = request.getSession();
			sess.setAttribute("result", result);
			request.getRequestDispatcher("/user-professor").forward(request, response);
		}
		
		//SOPHIASOPHIASOPHIASOPHIASOPHIASOPHIASOPHIASOPHIASOPHIASOPHIASOPHIA TERMINAL LINE
		
		
	//// alekosalekosalekosalekosalekosalekosalekosalekosalekosalekos
			/// ADD EXTERNAL_USER
			String message_for_admin = "ERROR";
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

			/// ADD INTERNAL_USER
			String add_internal = request.getParameter("add_internal");
			if ("add_internal".equals(add_internal)) {

				String username = request.getParameter("username").toString();////// USERNAME
				String surname = request.getParameter("surname").toString();//////// SURNAME
				String id = request.getParameter("id").toString();////////////////// ID
				String password = request.getParameter("password").toString();////// PASSWORD
				int year = Integer.parseInt(request.getParameter("year"));////////// YEAR OF BIRTH
				int age = Integer.parseInt(request.getParameter("age"));//////////// AGE
				String email = request.getParameter("email").toString();//////////// EMAIL
				String role = request.getParameter("role").toString();////////////// ROLE
				int yoe = Integer.parseInt(request.getParameter("yoe"));//////////// YEAR OF ENROLLMENT
				String department = request.getParameter("department").toString();// DEPARTMENT

				String pw = password;
				try {
					pw = new BCryptPasswordEncoder().encode(password);/////////////////// ENCODED PASSWORD
				} catch (NullPointerException e) {
					System.out.println("null!!!!");
					e.printStackTrace();
				}

				user user1 = new user(id, pw, 1);
				authorities auth = new authorities(id, role);
				INTERNAL_USER user2 = new INTERNAL_USER(id, username, surname, pw, year, age, email, role, yoe, department);

				message_for_admin = InternalService.registerNewUserAccount(user1, user2, auth);

				HttpSession sess = request.getSession();
				sess.setAttribute("message_for_admin", message_for_admin);
				request.getRequestDispatcher("/definer").forward(request, response);

			}
			//// SEARCH INTERNAL_USER
			String search = request.getParameter("search");
			if ("search".equals(search)) {

				String id = request.getParameter("user_id").toString();

				INTERNAL_USER user = InternalService.searchInternal(id);

				HttpSession sess = request.getSession();
				sess.setAttribute("user", user);
				request.getRequestDispatcher("/update_internal").forward(request, response);

			}

			///// SHOW INTERNAL_USER
			String show_users = request.getParameter("show_users");
			if ("show_users".equals(show_users)) {

				List<INTERNAL_USER> users = InternalService.showusers();

				HttpSession sess = request.getSession();
				sess.setAttribute("users", users);
				request.getRequestDispatcher("/Internal_id_finder").forward(request, response);
			}
			//// UPDATE INTERNAL_USER
			String update_internal = request.getParameter("update_internal");

			if ("update_internal".equals(update_internal)) {

				String username = request.getParameter("username").toString();////// USERNAME
				String surname = request.getParameter("surname").toString();//////// SURNAME
				String id = request.getParameter("id").toString();////////////////// ID
				String password = request.getParameter("password").toString();////// PASSWORD
				int year = Integer.parseInt(request.getParameter("year"));////////// YEAR OF BIRTH
				int age = Integer.parseInt(request.getParameter("age"));//////////// AGE
				String email = request.getParameter("email").toString();//////////// EMAIL
				String role = request.getParameter("role").toString();////////////// ROLE
				int yoe = Integer.parseInt(request.getParameter("yoe"));//////////// YEAR OF ENROLLMENT
				String department = request.getParameter("department").toString();// DEPARTMENT

				String pw = request.getParameter("pw").toString();
				if (!(password.equals(pw))) {
					try {
						pw = new BCryptPasswordEncoder().encode(password);/////////////////// ENCODED PASSWORD
					} catch (NullPointerException e) {
						System.out.println("null!!!!");
						e.printStackTrace();
					}

				}
				String identity =request.getParameter("identity").toString();
				user user1 = new user(id, pw, 1);
				authorities auth = new authorities(id, role);
				INTERNAL_USER user2 = new INTERNAL_USER(id, username, surname, pw, year, age, email, role, yoe, department);

				message_for_admin = InternalService.Update(user1, user2, auth,identity);

				HttpSession sess = request.getSession();
				sess.setAttribute("message_for_admin", message_for_admin);
				request.getRequestDispatcher("/definer").forward(request, response);
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
			
			String delete = request.getParameter("delete");
			if ("delete".equals(delete)) {
				String id = request.getParameter("user_id").toString();

				message_for_admin = InternalService.delete(id);

				HttpSession sess = request.getSession();
				sess.setAttribute("message_for_admin", message_for_admin);
				request.getRequestDispatcher("/definer").forward(request, response);
			}

			//// alekosalekosalekosalekosalekosalekosalekosalekosalekosalekos
	

		// ������ ������ ���� ����
		String Updateposi = request.getParameter("Updateposi");
		if ("Submit".equals(Updateposi)) {

			int informatics = Integer.parseInt(request.getParameter("informatics"));
			int health_science = Integer.parseInt(request.getParameter("health_science"));
			int home_economics = Integer.parseInt(request.getParameter("home_economics"));
			int geography = Integer.parseInt(request.getParameter("geography"));
			String name = "informatics";
			String name2 = "health_science";
			String name3 = "home_economics";
			String name4 = "geography";

			DBPositions.addpositions(informatics, name);
			DBPositions.addpositions(health_science, name2);
			DBPositions.addpositions(home_economics, name3);
			DBPositions.addpositions(geography, name4);

			request.getRequestDispatcher("/secretary_menu").forward(request, response);

		}

		// ��������� ������ �������������
		String SystemSub = request.getParameter("SystemSub");

		if ("Submit".equals(SystemSub)) {

			String checkboxValue1 = request.getParameter("opa");
			String checkboxValue2 = request.getParameter("ekpa");
			String checkboxValue3 = request.getParameter("unipi");
			String checkboxValue4 = request.getParameter("uoa");
			String checkboxValue5 = request.getParameter("teicrete");
			String checkboxValue6 = request.getParameter("agean");
			String checkboxValue7 = request.getParameter("apth");
			String checkboxValue8 = request.getParameter("uth");

			if (checkboxValue1 != null) {
				String name = "2-OPA";
				int status = 1;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			} else {

				String name = "2-OPA";
				int status = 0;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			}

			if (checkboxValue2 != null) {
				String name = "1-EKPA";
				int status = 1;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			} else {

				String name = "1-EKPA";
				int status = 0;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			}

			if (checkboxValue3 != null) {
				String name = "1-UNIPI";
				int status = 1;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			} else {
				String name = "1-UNIPI";
				int status = 0;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			}

			if (checkboxValue4 != null) {
				String name = "4-UOA";
				int status = 1;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			} else {

				String name = "4-UOA";
				int status = 0;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			}
			if (checkboxValue5 != null) {
				String name = "2-TEICRETE";
				int status = 1;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			} else {

				String name = "2-TEICRETE";
				int status = 0;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			}
			if (checkboxValue6 != null) {
				String name = "3-AGEAN";
				int status = 1;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			} else {

				String name = "3-AGEAN";
				int status = 0;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			}
			if (checkboxValue7 != null) {
				String name = "1-APTH";
				int status = 1;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			} else {

				String name = "1-APTH";
				int status = 0;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			}

			if (checkboxValue8 != null) {
				String name = "1-UTH";
				int status = 1;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			} else {

				String name = "1-UTH";
				int status = 0;
				String sstatus = Integer.toString(status);
				DBExternal_Department.Update(name, sstatus);
			}

			request.getRequestDispatcher("/Update_position").forward(request, response);

		}

		String SelectS = request.getParameter("SelectS");

		if ("SelectS".equals(SelectS)) {

			List<Check_Application> usersID = DBPositions.showAprovedStudents();

			HttpSession sess = request.getSession();
			sess.setAttribute("usersID", usersID);
			request.getRequestDispatcher("/aprovedStudents").forward(request, response);
		}

		String match = request.getParameter("match");

		if ("match".equals(match)) {

			for (int j = 0; j <= 3; j++) {
				if (j == 0) {
					String starts = "it";// βαζω το καταλληλο για καθε τμημα

					List<Check_Application> usersIDDEP = DBPositions.hasDep(starts); // επιστεφει τα αντικειμενα τα
																						// οποια εχοθν
																						// aproved 1 kai am pou xekinaei
																						// me it
					String dep = "informatics";
					int Num_av = DBPositions.posDep(dep); // αριθμος θεσεων
					int count = usersIDDEP.size();
					int i = 0;
					while (i < Num_av && count != 0) {
						String cur_student = usersIDDEP.get(i).getCheck_id();
						DBPositions.Update_Progg(cur_student);

						count--;
						i++;
					}
				} else if (j == 1) {
					String starts = "hs";// βαζω το καταλληλο για καθε τμημα

					List<Check_Application> usersIDDEP = DBPositions.hasDep(starts); // επιστεφει τα αντικειμενα τα
																						// οποια εχοθν
																						// aproved 1 kai am pou xekinaei
																						// me it
					String dep = "health_science";
					int Num_av = DBPositions.posDep(dep); // αριθμος θεσεων
					int count = usersIDDEP.size();
					int i = 0;
					while (i < Num_av && count != 0) {
						String cur_student = usersIDDEP.get(i).getCheck_id();
						DBPositions.Update_Progg(cur_student);

						count--;
						i++;
					}
				} else if (j == 2) {
					String starts = "ge";// βαζω το καταλληλο για καθε τμημα

					List<Check_Application> usersIDDEP = DBPositions.hasDep(starts); // επιστεφει τα αντικειμενα τα
																						// οποια εχοθν
																						// aproved 1 kai am pou xekinaei
																						// me it
					String dep = "geography";
					int Num_av = DBPositions.posDep(dep); // αριθμος θεσεων
					int count = usersIDDEP.size();
					int i = 0;
					while (i < Num_av && count != 0) {
						String cur_student = usersIDDEP.get(i).getCheck_id();
						DBPositions.Update_Progg(cur_student);

						count--;
						i++;

					}
				} else {
					String starts = "he";// βαζω το καταλληλο για καθε τμημα

					List<Check_Application> usersIDDEP = DBPositions.hasDep(starts); // επιστεφει τα αντικειμενα τα
																						// οποια εχοθν
																						// aproved 1 kai am pou xekinaei
																						// me it
					String dep = "home_economics";
					int Num_av = DBPositions.posDep(dep); // αριθμος θεσεων
					int count = usersIDDEP.size();
					int i = 0;
					while (i < Num_av && count != 0) {
						String cur_student = usersIDDEP.get(i).getCheck_id();
						DBPositions.Update_Progg(cur_student);

						count--;
						i++;
					}
				}
				System.out.println("Done Updating!");

			}
			DBPositions.setAsDeclined(); // enhmerwnei autous pou den perasan
			request.getRequestDispatcher("/secretary_menu").forward(request, response);
		}

	}*/
}
