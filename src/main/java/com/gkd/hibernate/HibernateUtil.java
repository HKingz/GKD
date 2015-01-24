package com.gkd.hibernate;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static ServiceRegistry serviceRegistry;

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration config = new Configuration().configure("hibernate.cfg.xml");
			config.setProperty("hibernate.connection.url", "jdbc:h2:" + new File(".").getAbsolutePath() + "/jmpDB");
			List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
			classLoadersList.add(ClasspathHelper.contextClassLoader());
			classLoadersList.add(ClasspathHelper.staticClassLoader());

			Reflections reflections = new Reflections(new ConfigurationBuilder().setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
					.setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
					.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("com.gkd.instrument.callgraph"))));
			Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
			Iterator<Class<?>> iterator = classes.iterator();
			while (iterator.hasNext()) {
				config.addAnnotatedClass(iterator.next());
			}

			serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
			return config.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session openSession() {
		return getSessionFactory().openSession();
	}

	public static StatelessSession openStatelessSession() {
		return getSessionFactory().openStatelessSession();
	}

	public static void shutdown() {
		getSessionFactory().close();
	}

	public static List createQuery(String query) {
		Session session = openSession();
		List list = session.createQuery(query).list();
		session.close();
		return list;
	}

}