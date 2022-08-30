package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class hibernate {
   public static Configuration config = new config();
   public static SessionFactory factory = config.buildSessionFactory();
   public static Transaction transaction = null;
   public hibernate() {
      config.configure("hibernate.cfg.xml");
   }
   public void setHibernate() {
      config = new config();
      config.configure("hibernate.cfg.xml");
      factory = config.buildSessionFactory();
      transaction = null;
   }
}