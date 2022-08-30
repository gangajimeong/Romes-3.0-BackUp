package com.company.ROMES.Services.StandardInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.print.PrintService;
import javax.transaction.Transactional;

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
import org.springframework.web.multipart.MultipartFile;

import com.company.ROMES.Controller.StandardInfo.Standard_TypeCode;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.CommonCode;
import com.company.ROMES.entity.LabelPrinter;
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.PrintingObject;
import com.company.ROMES.interfaces.dao.StandardInfo.LocationMasterInterface;
import com.company.ROMES.interfaces.service.StandardInfo.CommonCodeServiceInterface;
import com.company.ROMES.interfaces.service.StandardInfo.LocationMasterService;

import net.sf.json.JSON;

@Service
public class LocationMasterImp implements LocationMasterService {

	Logger logger = Logger.getLogger(getClass());
	@Autowired
	SessionFactory factory;

	@Autowired
	LocationMasterInterface locationDAO;
	@Autowired
	CommonCodeServiceInterface cs;
	@Autowired
	UserDAO us;
	
	@Autowired
	Standard_TypeCode stc;
	public static String url = "/location/";

	@Override
	public boolean SaveLocationImg(MultipartFile file) {
		String saveDir = System.getProperty("user.home") + "/uploadImages" + url;
		System.out.println(saveDir);
		boolean state = false;
		try {
			File Dir = new File(saveDir);
			if (!Dir.exists()) {
				Dir.mkdir();
			}
			file.transferTo(new File(saveDir + file.getOriginalFilename()));
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public List<LocationMaster> SelectAllbyfalse() {
		Session session = null;
		List<LocationMaster> list = new ArrayList<LocationMaster>();
		try {
			session = factory.openSession();
			list = locationDAO.SelectAllByfalse(session);

		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace())
				logger.error(ste);
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return list;
	}

	@Override
	public LocationMaster SelectById(int id) {
		Session session = null;
		LocationMaster lm = new LocationMaster();
		try {
			session = factory.openSession();
			lm = locationDAO.SelectById(session, id);
		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace())
				logger.error(ste);
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return lm;
	}

	@Override
	public boolean updateLocation(LocationMaster locationMaster, String filename) {
//		System.out.println(locationMaster.getParentLocation().getId());
//		System.out.println(locationMaster.getPath());
		System.out.println(locationMaster);
		Session session = null;
		Transaction transaction = null;
		LocationMaster lm = new LocationMaster();
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			lm = locationDAO.SelectById(session, locationMaster.getId());
			lm.setLocationTypeCode(locationMaster.getLocationTypeCode());
			lm.setName(locationMaster.getName());
			if(locationMaster.getParentLocation().getId() !=0) {
				lm.setParentLocation(locationMaster.getParentLocation());	
			}
			
			lm.getSubLocations().clear();
			lm.setPath(url + filename);
			
			if(locationMaster.getParentLocation() !=null) {
				LocationMaster upper = session.find(LocationMaster.class, locationMaster.getParentLocation().getId());
				lm.setParentLocation(upper);
				lm.setLevel(upper.getLevel()+1);
			}
			state = locationDAO.updateLocation(session, lm);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "로케이션 업데이트: "+locationMaster.getName());
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive())
				transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public boolean deleteLocation(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			state = locationDAO.deleteLocation(session, id);
			LocationMaster lm = session.find(LocationMaster.class, id);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "로케이션 삭제: "+lm.getName());
			
			transaction.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive())
				transaction.rollback();
		} finally {
			if(session!= null)
				if(session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public boolean createLocation(LocationMaster locationMaster, String filename) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		LocationMaster parenLocationMaster = new LocationMaster();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			if (locationMaster.getParentLocation().getId() != 0) {
				parenLocationMaster = locationDAO.SelectById(session, locationMaster.getParentLocation().getId());
				locationMaster.setLevel(parenLocationMaster.getLevel() + 1);
				parenLocationMaster.addSubLocation(locationMaster);
				state = locationDAO.updateLocation(session, parenLocationMaster);
			} else {
				locationMaster.setParentLocation(null);
				locationMaster.setLevel(0);
			}
			if (filename != null)
				locationMaster.setPath(url + filename);
			else
				locationMaster.setPath(url + "no_image.png");
			state = locationDAO.createLocation(session, locationMaster);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "로케이션 등록: "+locationMaster.getName());
			
			transaction.commit();

		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public org.json.simple.JSONArray SelectSubLocation(int id) {
		Session session = null;
		JSONArray ret = new JSONArray();
		LocationMaster locationMaster = new LocationMaster();
		try {
			session = factory.openSession();
			locationMaster = locationDAO.SelectById(session, id);
			ret = getChildrensData(locationMaster);

		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace())
				logger.error(ste);
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject SelectTopLocation(int id) {
		Session session = null;
		JSONObject ret = new JSONObject();
		LocationMaster location = new LocationMaster();
		try {
			session = factory.openSession();
			location = locationDAO.SelectById(session, id);
			ret = getParentData(location);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public List<CommonCode> SelectAboutLocation() {
		Session session = null;
		List<CommonCode> list = new ArrayList<CommonCode>();
		try {
			session = factory.openSession();
			list = cs.SelectByCodeId(stc.LocationTypeCodeName[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getImageUrl(int id) {
		Session session = null;
		LocationMaster location = new LocationMaster();
		String url = "img/no_image.png";
		try {
			session = factory.openSession();
			location = locationDAO.SelectById(session, id);
			url = location.getPath();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return url;
	}

	@Override
	public JSONArray LocationNavi(int id) { // 가장 높은 index가 최상위
		Session session = null;
		LocationMaster ParentLocation = new LocationMaster();
		LocationMaster lm = new LocationMaster();
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			lm = locationDAO.SelectById(session, id);
			ret = getNavi(lm);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject getLocationMainEventData(int id) {
		Session session = null;
		JSONObject res = new JSONObject();
		LocationMaster lm = new LocationMaster();
		try {
			session = factory.openSession();
			lm = locationDAO.SelectById(session, id);
			res.put("Navi", getNavi(lm));
			res.put("Top", getParentData(lm));
			res.put("Subs", getChildrensData(lm));
		} catch (Exception e) {
			e.printStackTrace();
			res.put("Navi", "정보 없음");
			res.put("Top", "정보 없음");
			res.put("Subs", "정보 없음");
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return res;
	}

	@Override
	public List<LocationMaster> SelectTop() {
		Session session = null;
		List<LocationMaster> list = new ArrayList<LocationMaster>();
		try {
			session = factory.openSession();
			list = locationDAO.SelectByLevel0(session);

		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace())
				logger.error(ste);
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return list;

	}

	public JSONObject getParentData(LocationMaster location) {
		JSONObject ret = new JSONObject();
		LocationMaster parent = new LocationMaster();
		try {
			parent = location.getParentLocation();
			if (parent != null) {
				ret.put("id", parent.getId());
				ret.put("name", parent.getName());
				ret.put("locationtypecode",
						parent.getLocationTypeCode() != null ? parent.getLocationTypeCode().getValue() : "정보 없음");
				ret.put("img", parent.getPath() != null ? parent.getPath() : "정보 없음");
			} else {
				ret.put("Data", "No Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public JSONArray getChildrensData(LocationMaster locationMaster) {
		JSONArray ret = new JSONArray();
		List<LocationMaster> subLocation = null;
		try {
			subLocation = new ArrayList<LocationMaster>();
			for (LocationMaster lm : locationMaster.getSubLocations()) {
				if(lm.isDisabeld() == false) {
				JSONObject sub = new JSONObject();
				sub.put("id", lm.getId());
				sub.put("name", lm.getName());
				sub.put("type", lm.getLocationTypeCode() != null ? lm.getLocationTypeCode().getValue() : "정보 없음");
				sub.put("img", lm.getPath() != null ? lm.getPath() : "정보 없음");
				ret.add(sub);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	public JSONArray getNavi(LocationMaster locationMaster) {
		LocationMaster ParentLocation = new LocationMaster();
		LocationMaster lm = new LocationMaster();
		JSONArray ret = new JSONArray();
		int ParentId = 0;
		try {
			lm = locationMaster;
			if (lm.getParentLocation() != null) {
				ParentLocation = lm.getParentLocation();
				ParentId = ParentLocation.getId();
			}
			while (ParentId != 0) {
				JSONObject data = new JSONObject();
				data.put("id", ParentLocation.getId());
				data.put("name", ParentLocation.getName());
				data.put("type",
						ParentLocation.getLocationTypeCode() != null ? ParentLocation.getLocationTypeCode().getValue()
								: "정보 없음");
				data.put("url", ParentLocation.getPath());
				ret.add(data);
				if (ParentLocation.getParentLocation() != null) {
					ParentLocation = ParentLocation.getParentLocation();
				} else {
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	public JSONObject printBarcode(int id, String printName) {
		Session session = null;
		PrintingObject o = new PrintingObject();
		JSONObject ret = null;
		try {
			ret= new JSONObject();
			session = factory.openSession();
			LocationMaster loc = session.find(LocationMaster.class,id);
			LabelPrinter lp = new LabelPrinter();
			lp = (LabelPrinter) session.createQuery("select this_ from LabelPrinter this_ where this_.printerName='"+printName+"'").getSingleResult();
			o.setMargin_h(lp.getMargin_h());
			o.setMargin_w(lp.getMargin_w());
			o.setFontSize(lp.getFontSize());
			o.addPrintingLine(loc.getName());
			o.setBarcode(String.valueOf(loc.getId()));
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
//			try {
//				List<LabelPrinter> label = new ArrayList<LabelPrinter>();
//				label = session.createQuery("select this_ from LabelPrinter this_").getResultList();
//				
//				for(LabelPrinter lp:label ) {
//					printList.add(lp.getPrinterName());
//				}
//			} catch (IndexOutOfBoundsException e) {
//				
//				for(PrintService s: PrintingObject.getPrintList()) {
//					printList.add(s.getName());
//				}
//				
//			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(session!= null)
				if(session.isOpen())
					session.close();
		}
		return printList;
	}

}
