package hibernate;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class config extends Configuration {
//	 Local Server
//	public static String username = "postgres";
//	public static String password = "1028";
//	public static String Database = "Eunjin";
//	public static String port = "5432";
//	public static String url = "127.0.0.1";
	//����
//	public static String username = "postgres";
//	public static String password = "romes";
//	public static String Database = "ROMES 3.0";
//	public static String port = "5432";
	public static String url = "127.0.0.1";	
//	public static String url = "192.168.0.206";
	public static String username = "postgres";
	public static String password = "romes";
	public static String Database = "ROMES 3.0";
	public static String port = "5432";
//	public static String url = "127.0.0.1";
	// GW Server
//	public static String username = "giwonjung";
//	public static String password = "1";
//	public static String Database = "cjproject";
//	public static String port = "5432";
//	public static String url ="192.168.0.22";
	// HJ Server
//	public static String username = "postgres";
//	public static String password = "1234";
//	public static String Database = "DS_Server_Clone";
//	public static String port = "5432";
//	public static String url ="192.168.0.92";
	// Server Rack

	// �Ϲ�
//	   public static String username = "postgres";
//	   public static String password = "romes";
//	   public static String Database = "ROMES 2.0";
//	   public static String port = "5432";
//	   public static String url ="172.168.0.20";
//�Ͼᳪ��
//	public static String username = "postgres";
//	public static String password = "romes";
//	public static String Database = "ROMES 2.0";
//	public static String port = "5432";
//	public static String url = "192.168.0.17";

//	public static String username = "postgres";
//	public static String password = "romes";
//	public static String Database = "Test_HN";
//	public static String port = "5432";
//	public static String url = "192.168.0.131";
////����	
//	public static String username = "postgres";
//	public static String password = "romes";
//	public static String Database = "ROMES 2.0";
//	public static String port = "5432";
//	public static String url = "192.168.0.206";
//	
//	public static String username = "postgres";
//	public static String password = "romes"; // public static String Database = "ROMES 2.0"; public static String
//	public static String Database = "Romes 2.0";
//	public static String port = "5432";
//	public static String url = "192.168.10.141";

//	 Server for Local
//	public static String username = "postgres";
//	public static String password = "romes";
//	public static String Database = "ROMES 2.0";
////	public static String Database = "testBackup";
//	public static String port = "5432";
//	public static String url ="127.0.0.1";
//	public static String url ="192.168.0.11";

	public config() {
		this.configure("hibernate.cfg.xml");
		this.setProperty("hibernate.connection.username", username);
		this.setProperty("hibernate.connection.password", password);
		this.setProperty("hibernate.connection.url", "jdbc:postgresql://" + url + ":" + port + "/" + Database);
		this.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
	}

	public static void main(String[] args) {
		Session session = hibernate.factory.openSession();
		session.close();
	}

//	public String getUrl() {
//		return url;
//	}
}