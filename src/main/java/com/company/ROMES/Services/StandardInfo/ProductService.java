package com.company.ROMES.Services.StandardInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.Controller.StandardInfo.Standard_TypeCode;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.CommonCode;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.Product;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.StandardInfo.ProductServiceInterface;
import com.google.gson.JsonObject;

@Service
public class ProductService implements ProductServiceInterface{
	Logger logger = Logger.getLogger("ProductService");
	@Autowired
	SessionFactory factory;
	@Autowired
	UserDAO us;
	
	@Override
	public List<Product> getList(){
		Session session = null;
		List<Product> product =  null;
		try {
			session = factory.openSession();
			product = session.createCriteria(Product.class).list();
		} catch (Exception e) {
			// TODO: handle exception
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		} finally {
			SessionMethod.closeSession(session);
		}
		return product;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getFalseList() {
		Session session = null;
		List<Product> list = null;
		try {
			session = factory.openSession();
			list = session.createQuery("select this_ from product this_ where this_.disabled = false order by this_.id").getResultList();
		} catch (Exception e) {
			logger.error(e);
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return list;
	}
	
	@Override
	public Product getProductById(int id) {
		Session session = null;
		Product m = null;
		try {
			session = factory.openSession();
			m = session.find(Product.class, id);
		} catch (Exception e) {
			logger.error(e);
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return m;
	}
	
	@Override
	public void createProduct(Product product) {
		Session session = null;
		Transaction transaction = null;
		boolean ret = false;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "완제품 등록: "+product.getName());
			
			session.save(product);
			transaction.commit();
			ret = true;
		} catch (Exception e) {
			// TODO: handle exception
			if (transaction.isActive())
				transaction.rollback();
			logger.error(e);
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();

		}
	}
	
	@Override
	public boolean isExistProduct(String code) {
		Session session = null;
		boolean state = false;
		try {
			System.out.println(code);
			session = factory.openSession();
			Product m = (Product) session.createQuery("select this_ from product this_ where this_.disabled= false and this_.productCode='"+code+"'").getSingleResult();
			state=  true;
		} catch (NoResultException e) {
			state = false;
		} catch(Exception e) {
		e.printStackTrace();	
		}finally {
			if(session!=null)
				if(session.isOpen())
					session.close();
		}
		return state;
	}
	
	@Override
	public Product updateProduct(Product product) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction transaction = null;
		Product p = product;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "완제품 업데이트: "+product.getName());
			
			session.update(p);
			
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			logger.error(e);
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return p;
	}
	
	@Override
	public int deleteProduct(int id) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			
			Product p =session.find(Product.class,id);
			p.setDisabled(true);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "완제품 삭제: "+p.getName());
			
			session.update(p);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			logger.error(e);
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return id;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getCommonCode() {
		Session session = null;
		JSONObject ret = new JSONObject();
		List<CommonCode> lists = new ArrayList<>();
		try {
			session = factory.openSession();
			Query query = session
					.createQuery("Select this_ from CommonCode this_ where this_.disabled=false Order By this_.id");
			lists = query.getResultList();
			Collections.sort(lists, new Comparator<CommonCode>() {

				@Override
				public int compare(CommonCode o1, CommonCode o2) {
					int code1 = Integer.parseInt(o1.getCodeId());
					int code2 = Integer.parseInt(o2.getCodeId());
					int result = 0;
					if (code1 == code2) {
						if (o1.getId() > o2.getId())
							result = 1;
						else
							result = -1;
					} else if (code1 < code2)
						result = -1;
					else
						result = 1;
					return result;
				}
			});
			List<CommonCode> MG = new ArrayList<CommonCode>();
			List<CommonCode> MF = new ArrayList<CommonCode>();
			List<CommonCode> MT = new ArrayList<CommonCode>();
			List<CommonCode> QT = new ArrayList<CommonCode>();
			for (CommonCode c : lists) {
				if (c.getCodeId().equals(Standard_TypeCode.MaterialGroup[0])) {
					MG.add(c);
				} 
				if (c.getCodeId().equals(Standard_TypeCode.MaterialFeature[0])) {
					MF.add(c);
				}
				if(c.getCodeId().equals(Standard_TypeCode.MaterialType[0])) {
					MT.add(c);
				}
				if(c.getCodeId().equals(Standard_TypeCode.QuantitiesType[0])){
					QT.add(c);
				}
			}
			ret.put("MG",MG );
			ret.put("MF",MF );
			ret.put("MT",MT );
			ret.put("QT",QT );
			
		} catch (Exception e) {
			logger.error(e);
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}
	
	@Override
	public boolean importExcel(JsonObject datas) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();


			String ProductName = datas.get("ProductName").getAsString();
			String ProductCode = datas.get("ProductCode").getAsString();
			String Standard = datas.get("Standard").getAsString();
			
			Product p = new Product();
			p.setProductCode(ProductCode);
			p.setName(ProductName);
			p.setSize(Standard);

			session.save(p);
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}
}
