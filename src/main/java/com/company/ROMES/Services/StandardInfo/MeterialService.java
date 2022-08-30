
package com.company.ROMES.Services.StandardInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.print.PrintService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.LoggerUtil;
import com.company.ROMES.dao.MeterialProductDAO;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.entity.LabelPrinter;
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkingLine;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.PrintingObject;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.StandardInfo.MeterialServiceInterface;
import com.google.gson.JsonObject;

@Service
public class MeterialService implements MeterialServiceInterface {
	Logger logger = Logger.getLogger("MaterialService");

	@Autowired
	SessionFactory factory;
	@Autowired
	MeterialProductDAO meterialDao;
	@Autowired
	UserDAO us;

	@Override
	public List<Material> selectMeterials(Set<Integer> keylist) {
		List<Material> retlist = new ArrayList<>();
		Session session = null;
		try {
			session = factory.openSession();
			Iterator<Integer> it = keylist.iterator();
			while (it.hasNext()) {
				Material m = meterialDao.selectById(session, it.next());
				if (m != null)
					retlist.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retlist = null;
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return retlist;
	}

	@Override
	public List<Material> selectMeterials(Session session, Set<Integer> keylist) {
		List<Material> retlist = new ArrayList<>();
		Iterator<Integer> it = keylist.iterator();
		while (it.hasNext()) {
			Material m = meterialDao.selectById(session, it.next());
			if (m != null)
				retlist.add(m);
		}
		return retlist;
	}

	@Override
	public List<Material> selectAll() {

		List<Material> lists = new ArrayList<Material>();
		Session s = null;
		try {
			s = factory.openSession();
			lists = meterialDao.selectAll(s);

		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
		} finally {
			if (s != null)
				if (s.isOpen())
					s.close();
		}
		return lists;
	}

	@Override
	public Material selectById( int id) {
		Session s = null;
		Material material = new Material();
		try {
			s = factory.openSession();
			material = meterialDao.selectById(s, id);
		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
		} finally {
			if (s != null)
				if (s.isOpen())
					s.close();
		}

		return material;
	}

	@Override
	public boolean createMaterial(Material material) {
		Session s = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			s = factory.openSession();
			transaction = s.beginTransaction();
			state = meterialDao.saveMeterial(s, material);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(s, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(s, user, "Manager", "원부자재 등록: "+material.getName());
			
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
		} finally {
			SessionMethod.closeSession(s);
		}
		return state;
	}

	public boolean updateMaterial(Material material) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Material ma = new Material();
			ma = session.find(Material.class, material.getId());
			
			if (ma.getName() != material.getName())
				ma.setName(material.getName());
			if (ma.getProductCode() != material.getProductCode())
				ma.setProductCode(material.getProductCode());
			if (ma.getStandard() != material.getStandard())
				ma.setStandard(material.getStandard());
//			if (ma.getDefaultSalePrice() != material.getDefaultSalePrice())
//				ma.setDefaultSalePrice(material.getDefaultSalePrice());
			if (ma.getUnitCount() != material.getUnitCount())
				ma.setUnitCount(material.getUnitCount());
			if (ma.getProperQuentity() != material.getProperQuentity())
				ma.setProperQuentity(material.getProperQuentity());
			if (ma.getStockCount() != material.getStockCount())
				ma.setStockCount(material.getStockCount());
			
			ma.setLastUpdateDate(LocalDateTime.now());

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "원부자재 업데이트: "+material.getName());
			
			session.update(ma);
			transaction.commit();
			state = true;
		} catch (Exception e) {
			// TODO: handle exception
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return state;
	}

	@Override
	public boolean deleteMaterial(int id) {
		Session s = null;
		Transaction tr = null;
		boolean state = false;
		try {
			s= factory.openSession();
			tr = s.beginTransaction();
			Material m = s.find(Material.class, id);
			meterialDao.deleteMeterial(s, id);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(s, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(s, user, "Manager", "원부자재 삭제: "+m.getName());
			
			tr.commit();
			
		} catch (Exception e) {
			if (tr.isActive())
				tr.rollback();
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
		}finally {
			SessionMethod.closeSession(s);
		}
		return state;
	}

	@Override
	public JSONArray getlocation() {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			List<LocationMaster> locate = new ArrayList<LocationMaster>();
			locate = session.createQuery("Select this_ from LocationMaster this_").getResultList();
			for(LocationMaster lo : locate) {
				JSONObject object = new JSONObject();
				object.put("id", lo.getId());
				object.put("name", lo.getName());
				ret.add(object);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public JSONArray printList() {
		PrintingObject o = new PrintingObject();
		Session session = null;
		JSONArray printList = new JSONArray();
		try {
			session = factory.openSession();
			for(PrintService s: PrintingObject.getPrintList()) {
				printList.add(s.getName());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(session!= null)
				if(session.isOpen())
					session.close();
		}
		return printList;
	}
	
	public JSONObject printBarcode(int id, String printName) {
		Session session = null;
		PrintingObject o = new PrintingObject();
		JSONObject ret = null;
		try {
			ret= new JSONObject();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
			String date = LocalDateTime.now().format(format);
			session = factory.openSession();
			Material m = session.find(Material.class,id);
			LabelPrinter lp = new LabelPrinter();
			lp = (LabelPrinter) session.createQuery("select this_ from LabelPrinter this_ where this_.printerName='"+printName+"'").getSingleResult();
			o.setMargin_h(lp.getMargin_h());
			o.setMargin_w(lp.getMargin_w());
			o.setFontSize(lp.getFontSize());
			o.addPrintingLine(m.getName());
			o.addPrintingLine(m.getProductCode()+"-"+date);
			o.setBarcode(m.getProductCode());
			o.print(printName);
			ret.put("state", "출력 완료");
			
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("state", "출력 실패");
		}finally {
			if(session!= null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
	public JSONArray selectMaterialById(int id) {
		Session session = null;
		Transaction transaction = null;
		JSONArray ret = null;
		try {

			session = factory.openSession();
			transaction = session.beginTransaction();
			ret = new JSONArray();
			Material ma = new Material();
			ma = session.find(Material.class, id);
			
			ret.add(ma.getId());
			ret.add(ma.getName());
			ret.add(ma.getProductCode());
			ret.add(ma.getStandard());
//			ret.add(ma.getDefaultSalePrice());
			ret.add(ma.getUnitCount());
			ret.add(ma.getProperQuentity());
			ret.add(ma.getStockCount());

		} catch (Exception e) {
			e.printStackTrace();
			ret.add(false);
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}
	
	public List<Material> getMaterialList(){
		Session session = null;
		List<Material> list = null;
		try {
			session = factory.openSession();
			list = session.createQuery("Select this_ from Material this_ where this_.isDisable = false order by this_.id").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return list;
	}
	
	@Override
	public boolean importExcel(JsonObject datas) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();


			String MaterialName = datas.get("ProductName").getAsString();
			String MaterialCode = datas.get("ProductCode").getAsString();
			String MaterialSize = datas.get("Size").getAsString();
			int UnitCount = datas.get("UnitCount").getAsInt();
			int Properquentity = datas.get("Properquentity").getAsInt();
			int StockCount = datas.get("StockCount").getAsInt();
			
			
			Material material = new Material();
			material.setName(MaterialName);
			material.setProductCode(MaterialCode);
			material.setUnitCount(UnitCount);
			material.setProperQuentity(Properquentity);
			material.setStockCount(StockCount);
			material.setStandard(MaterialSize);

			session.save(material);

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
