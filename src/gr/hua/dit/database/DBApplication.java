package gr.hua.dit.database;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import gr.hua.dit.classes.Application;

public class DBApplication {

	// пяосхгйг аитгсеым стгм басг
	public static String addApplication(Date date_of_submission, String application_id, int isChecked, byte[] family,
			byte[] financially, byte[] locality) {
		// create session factory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Application.class)
				.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();

		String result = "AN ERROR OCCURED WHILE UPLOADING THE FILES";

		try {

			if (family != null || financially != null || locality != null) {
				// create the student object
				Application application = new Application(date_of_submission, application_id, isChecked, family,
						financially, locality);

				// start a transaction
				session.beginTransaction();

				// save the student object
				session.save(application);

				// commit transaction
				session.getTransaction().commit();
				System.out.println("Done!");

				result = "Submittion completed";

			}

			return result;
		} finally {
			factory.close();
		}

	}
	
	// елжамисг окым тым аитгсеым апо тгм басг поу дем евоум екецхеи

		public static List<Application> showApplications() {

			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Application.class)
					.buildSessionFactory();

			Session session = factory.getCurrentSession();

			try {
				// start a transaction
				session.beginTransaction();

				// query applications
				@SuppressWarnings("unchecked")
				List<Application> applications = session.createQuery("from Application").getResultList();

				// commit transaction
				session.getTransaction().commit();

				System.out.println("Done!");

				return applications;

			} finally {
				factory.close();
			}

		}
		
}
