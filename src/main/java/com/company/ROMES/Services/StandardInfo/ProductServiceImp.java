package com.company.ROMES.Services.StandardInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.Controller.StandardInfo.Standard_TypeCode;
import com.company.ROMES.entity.CommonCode;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.dao.StandardInfo.ProductDAOInterface;
import com.company.ROMES.interfaces.service.StandardInfo.ProductService;

@Service
public class ProductServiceImp implements ProductService {
	Logger logger = Logger.getLogger("ProductService");
	@Autowired
	SessionFactory factory;
	@Autowired
	ProductDAOInterface productDAO;
	
	@Override
	public List<ManufactureProduct> getList() {
		Session session = null;
		List<ManufactureProduct> list = null;
		try {
			session = factory.openSession();
			list = productDAO.SelectPrList(session);
		} catch (Exception e) {
			// TODO: handle exception
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
	public List<ManufactureProduct> getFalseList() {
		Session session = null;
		List<ManufactureProduct> list = null;
		try {
			session = factory.openSession();
			list = productDAO.SelectPrLIstByFalse(session);
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
	public ManufactureProduct getProductById(int id) {
		Session session = null;
		ManufactureProduct m = null;
		try {
			session = factory.openSession();
			m = productDAO.product(session, id);
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
	public void createProduct(ManufactureProduct manufactureProduct) {
		Session session = null;
		Transaction transaction = null;
		boolean ret = false;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			productDAO.createProduct(session, manufactureProduct);
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
	public ManufactureProduct updateProduct(ManufactureProduct manufactureProduct) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction transaction = null;
		ManufactureProduct m = manufactureProduct;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			productDAO.updateProduct(session, m);
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
		return m;
	}

	@Override
	public int deleteProduct(int id) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			productDAO.deleteProduct(session, id);
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
	public boolean isExistProduct(String code) {
		Session session = null;
		boolean state = false;
		try {
			System.out.println(code);
			session = factory.openSession();
			ManufactureProduct m = (ManufactureProduct) session.createQuery("Select this_ from ManufactureProduct this_ where this_.isDisable= false and this_.productCode='"+code+"'").getSingleResult();
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
	public JSONArray getProduct() {
		Session session = null;
		JSONArray ret = new JSONArray();
		
		try {
			session = factory.openSession();
			List<ManufactureProduct> mp = new ArrayList<ManufactureProduct>();
			mp = session.createQuery("Select this_ from ManufactureProduct this_").getResultList();
			
			for(ManufactureProduct list : mp) {
				JSONObject ob = new JSONObject();
				ob.put("id", list.getId());
				ob.put("name", list.getName());
				
				ret.add(ob);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}
	
}
