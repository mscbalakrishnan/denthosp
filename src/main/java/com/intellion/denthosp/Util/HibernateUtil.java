package com.intellion.denthosp.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new AnnotationConfiguration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			System.out.println(" Erron in hibernate session factory" +ex );
			ex.printStackTrace();
			
			throw new ExceptionInInitializerError(ex);
		}
	}
	public static Connection getDBConnection(String url, String userName, String password)throws HibernateException, SQLException {
		Connection connection = DriverManager.getConnection( url, userName, password );
		return connection;
	}
	public static Session getSession(String url, String userName, String password ) throws HibernateException, SQLException {
        //Connection connection = getDBConnection( url, userName, password );
        Session session = sessionFactory.openSession();
        return session;
	}
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
