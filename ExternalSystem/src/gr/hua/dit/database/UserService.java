package gr.hua.dit.database;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import gr.hua.dit.classes.Application;
//import gr.hua.dit.classes.External_Department;
import gr.hua.dit.classes.External_User;
import gr.hua.dit.classes.authorities;
import gr.hua.dit.classes.user;

public class UserService {

	static String registerNewUserAccount(user user1, External_User user2,authorities auth) throws SQLException {
		
		// create session factory
		SessionFactory factory_External_User = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(External_User.class)
				.buildSessionFactory();

		// create session
		Session session1 = factory_External_User.getCurrentSession();

		try {
			// start a transaction
			session1.beginTransaction();

			// save the student object
			session1.save(user2);
			
			// commit transaction
			session1.getTransaction().commit();
			System.out.println("Done!");

		}
		
		finally {
			factory_External_User.close();
		}
		
		// create session factory
		SessionFactory factory_user = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(user.class)
				.buildSessionFactory();

		// create session
		Session session2 = factory_user.getCurrentSession();

		try {
			// start a transaction
			session2.beginTransaction();

			// save the student object
			session2.save(user1);
			
			// commit transaction
			session2.getTransaction().commit();
			System.out.println("Done!"); 
			
		}
		
		finally {
			factory_user.close();
		}
		
		// create session factory
		SessionFactory factory_authorities = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(authorities.class)
				.buildSessionFactory();

		// create session
		Session session3 = factory_authorities.getCurrentSession();

		try {
			// start a transaction
			session3.beginTransaction();

			// save the student object
			session3.save(auth);
			
			// commit transaction
			session3.getTransaction().commit();
			System.out.println("Done!");

			String message = "Η ΕΓΓΡΑΦΗ ΟΛΟΚΛΗΡΩΘΗΚΕ"; 
			
			return message;
		}
		
		finally {
			factory_authorities.close();
		}
		
	}
/*	
	public static List<External_Department> showDepartments() {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(External_Department.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			// start a transaction
			session.beginTransaction();

			// query applications
			@SuppressWarnings("unchecked")
			List<External_Department> departments = session.createQuery("from External_Department where is_Selected = 1").getResultList();

			// commit transaction
			session.getTransaction().commit();

			System.out.println("Done!");
			
			return departments;
		} finally {
			factory.close();
		}
	}*/
	
//////CHECK APP
	public static String check(String id) {
		
		int pro;
		int app_check;
		String answer="ERROR";
		
		SessionFactory factory1 = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(External_User.class).buildSessionFactory();

		Session session1 = factory1.getCurrentSession();

		try {
			// start a transaction
			session1.beginTransaction();

			// query applications
			External_User user = session1.get(External_User.class, id);

			// commit transaction
			session1.getTransaction().commit();

			System.out.println("Done!");
			
			pro = user.getApp_progress();
			

		} finally {
			factory1.close();
		}
		
		SessionFactory factory2 = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Application.class).buildSessionFactory();

		Session session2 = factory2.getCurrentSession();

		try {
			// start a transaction
			session2.beginTransaction();

			// query applications
			Application app = session2.get(Application.class, id);

			// commit transaction
			session2.getTransaction().commit();

			System.out.println("Done!");
			
			app_check = app.getIsChecked();
			

		} finally {
			factory2.close();
		}
		
		if(app_check== 0) {
			answer="Η ΑΙΤΗΣΗ ΣΑΣ ΔΕΝ ΕΧΕΙ ΕΛΕΓΧΘΕΙ";
		}else {
			if(pro == 0) {
				answer="ΕΧΕΙ ΓΙΝΕΙ ΕΛΕΓΧΟΣ ΤΗΣ ΑΙΤΗΣΗΣ ΣΑΣ";
			}else if(pro == 1) {
				answer="Η ΑΙΤΗΣΗ ΣΑΣ ΕΧΕΙ ΕΓΚΡΙΘΕΙ";
			}else if(pro == 2){
				answer="Η ΑΙΤΗΣΗ ΣΑΣ ΕΧΕΙ ΑΠΟΡΙΦΘΕΙ";
			}
		}
		return answer;
	}

}
