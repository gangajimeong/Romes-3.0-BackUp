package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;




public class HibernateServer {

	private static String port;
	private static String db;
	private static String user;
	private static String pw;
	private SessionFactory factory;

	
	public SessionFactory getFactory() {
		return factory;
	}

	public static SessionFactory getFactoryInstance() {
		return LazyHolder.INSTANCE;
	}

	private static class LazyHolder {
		private static final SessionFactory INSTANCE = new HibernateServer().getFactory();
	}

	protected HibernateServer() {
		super();
		Configuration con = new Configuration();
		con.configure("hibernate.cfg.xml");
		con.setProperty("hibernate.connection.url", "jdbc:postgresql://127.0.0.1:"+port+"/"+db);
		con.setProperty("hibernate.connection.username", user);
		con.setProperty("hibernate.connection.password", pw);
		factory = con.buildSessionFactory();
	}

	public static void setcfg(String port, String db, String user, String pw) {
		// TODO Auto-generated method stub
		HibernateServer.port = port;
		HibernateServer.db = db;
		HibernateServer.user = user;
		HibernateServer.pw = pw;
	}
}
