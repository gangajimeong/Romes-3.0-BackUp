package hibernate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.Query;
import org.hibernate.Session;

import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;


public class LotFactory {
	public static String getGeneratedLot(Session session,Material product) {
		Query query= session.createSQLQuery("Select lotgenerator() as text");
		String ret = query.getSingleResult().toString();
		ret = product.getProductCode()+ ret;
		return ret;
	}
	
	public static String getGeneratedLot(Session session,Material product,String date) {
		String ret = product.getProductCode() + "-" + date;
		
		return ret;
	}
	
	public static String getGeneratedLot(Session session) {
		Query query= session.createSQLQuery("Select lotgenerator() as text");
		String ret = query.getSingleResult().toString();
		
		return ret;
	}
}
