package com.company.ROMES.Services.StandardInfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.LoggerUtil;
import com.company.ROMES.dao.CompanyManagementDAO;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.Brand;
import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.Company_manager;
import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.StandardInfo.CommonCodeServiceInterface;
import com.company.ROMES.interfaces.service.StandardInfo.CompanyServiceInterface;
import com.google.gson.JsonObject;

@Service
public class CompanyService implements CompanyServiceInterface {

	@Autowired
	SessionFactory factory;

	@Autowired
	CompanyManagementDAO bmDAO;

	@Autowired
	UserDAO us;

	@Override
	public List<Company> SelectCompanyList() {
		// TODO Auto-generated method stub
		Session session = null;
		List<Company> ret = null;
		try {
			session = factory.openSession();
			ret = bmDAO.SelectCompanyList(session);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ret = null;
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return ret;
	}

	@Override
	public List<Company> SelectCompanyByFalse() {
		// TODO Auto-generated method stub
		Session session = null;
		List<Company> ret = null;
		try {
			session = factory.openSession();
			ret = bmDAO.SelectCompanyByFalse(session);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ret = null;
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public Company SelectCompanyById(int id) {
		// TODO Auto-generated method stub
		Session session = null;
		Company ret = null;
		try {
			session = factory.openSession();
			ret = bmDAO.SelectCompanyById(session, id);
		} catch (Exception e) {
			e.printStackTrace();
			ret = null;
			// TODO: handle exception
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public boolean createCompany(Company company) {
		// TODO Auto-generated method stub
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			bmDAO.createCompany(session, company);

			LoggingTool.createLog(session, user, "Manager", "거래처 등록: " + company.getCompanyName());
			LoggerUtil.info("company: " + company.getId(), userID, "create");

			ret = true;
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
			ret = false;
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return ret;
	}

	@Override
	public boolean UpdateCompany(Company company) {
		// TODO Auto-generated method stub
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			bmDAO.UpdateCompany(session, company);

			LoggingTool.createLog(session, user, "Manager", "거래처 업데이트: " + company.getCompanyName());
			LoggerUtil.info("company: " + company.getId(), userID, "update");
			ret = true;
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
			ret = false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ret = false;
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret;
	}

	@Override
	public boolean DeleteCompany(Company company) {
		// TODO Auto-generated method stub
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			bmDAO.DeleteCompany(session, company);

			LoggingTool.createLog(session, user, "Manager", "거래처 삭제: " + company.getCompanyName());
			LoggerUtil.info("company: " + company.getId(), userID, "delete");
			ret = true;
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ret = false;
		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return ret;
	}

	// 담당자 등록
	@Override
	public boolean ManagerRegister(Company_manager company_manager, int id) {
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			Company company = bmDAO.SelectCompanyById(session, id);
			company.addManager(company_manager);
			company_manager.setCompany(company);
			bmDAO.registerManager(session, company_manager);
			bmDAO.UpdateCompany(session, company);

			LoggerUtil.info("companyManager:" + company_manager.getId(), userID, "ManagerRegist");
			ret = true;
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ret = false;
		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return ret;
	}

	// 담당자 등록 파라미터 id 는 모두 company ID
	@Override
	public boolean MangerInfoUpdate(Company_manager company_manager, int id) {
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			session.update(company_manager);

			LoggerUtil.info("companyManager:" + company_manager.getId(), userID, "ManagerUpdate");
			ret = true;
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ret = false;
		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return ret;
	}

	@Override
	public boolean ManagerInfoDelete(int id) {
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			Company_manager cm = bmDAO.company_manager(session, id);
			Company company = bmDAO.SelectCompanyById(session, cm.getCompany().getId());
			// company.removeManager(cm);
			bmDAO.deleteManager(session, cm);
			company.getManagers().remove(cm);
			session.update(company);

			LoggerUtil.info("companyManager: " + id, userID, "ManagerDelete");
			ret = true;
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ret = false;
		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return ret;
	}

	@Override
	public JSONArray managerLists(int id) {
		// TODO Auto-generated method stub
		Session session = null;
		JSONArray result = new JSONArray();
		List<Company_manager> lists = new ArrayList<Company_manager>();
		try {

			session = factory.openSession();
			Company com = session.find(Company.class, id);
			lists = com.getManagers();
			if (lists.size() != 0) {
				for (Company_manager cm : lists) {
					if (cm.isDisable())
						continue;
					JSONObject ret = new JSONObject();
					ret.put("id", cm.getId());
					ret.put("name", cm.getName());
					ret.put("pos", cm.getPosition());
					ret.put("phone", cm.getPhone());
					ret.put("email", cm.getEmail());

					result.add(ret);

				}
			} else {
				JSONObject ret = new JSONObject();
				ret.put("NoInfo", "NoInfo");
				result.add(ret);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return result;
	}

	@Override
	public JSONArray company() {
		Session session = null;
		List<Company> lists = new ArrayList<Company>();
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			lists = bmDAO.SelectCompanyByFalse(session);
			for (Company c : lists) {
				JSONObject res = new JSONObject();
				res.put("id", c.getId());
				res.put("name", c.getCompanyName());
				ret.add(res);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ret.add("정보없음");
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONArray constructionCompany() {
		Session session = null;
		List<ConstructionCompany> lists = new ArrayList<ConstructionCompany>();
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			lists = session.createQuery("Select this_ from ConstructionCompany this_ where this_.disabled = false").getResultList();

			for (ConstructionCompany con : lists) {
				JSONObject res = new JSONObject();
				res.put("id", con.getId());
				res.put("name", con.getCompanyName());
				ret.add(res);
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session != null)
				if (session.isOpen())
					;
			session.close();
		}
		return ret;
	}

	@Override
	public JSONObject updateCompanyBtn(Company company) {
		// TODO Auto-generated method stub
		JSONObject ret = new JSONObject();
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			Company com = new Company();
			com = session.find(Company.class, company.getId());
			if (com.getCompanyName() != company.getCompanyName())
				com.setCompanyName(company.getCompanyName());
			if (com.getCEO_Name() != company.getCEO_Name())
				com.setCEO_Name(company.getCEO_Name());
			if (com.getBusinessNumber() != company.getBusinessNumber())
				com.setBusinessNumber(company.getBusinessNumber());
			if (com.getPostalCode() != company.getPostalCode())
				com.setPostalCode(company.getPostalCode());
			if (com.getRealAddress() != company.getRealAddress())
				com.setRealAddress(company.getRealAddress());
			if (com.getPhone() != company.getPhone())
				com.setPhone(company.getPhone());
			if (com.getFax() != company.getFax())
				com.setFax(company.getFax());
			if (com.getEmail() != company.getEmail())
				com.setEmail(com.getEmail());
			if (com.getRemarks() != company.getRemarks())
				com.setRemarks(company.getRemarks());

			ret.put("id", com.getId());
			ret.put("company", com.getCompanyName());
			ret.put("name", com.getCEO_Name());
			ret.put("businessNum", com.getBusinessNumber());
			ret.put("post", com.getPostalCode());
			ret.put("address", com.getRealAddress());
			ret.put("tel", com.getPhone());
			ret.put("fax", com.getFax());
			ret.put("email", com.getEmail());
			ret.put("remark", com.getRemarks());

			LoggingTool.createLog(session, user, "Manager", "거래처 업데이트: " + company.getCompanyName());
			LoggerUtil.info("company", userID, "update");

			session.update(com);
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {
			SessionMethod.closeSession(session, transaction);
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
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			String CompanyName = datas.get("companyName").getAsString();
			String CEO_Name = datas.get("CEO_Name").getAsString();
			String businessNumber = datas.get("businessNumber").getAsString();
			String realAddress = datas.get("realAddress").getAsString();

			System.out.println(CompanyName);

			List<Company> com = session.createQuery("Select this_ from Company this_").getResultList();
			for (Company company : com) {
				if (company.getCompanyName().equals(CompanyName)) {
					state = true;
					break;
				}
			}

			System.out.println(state);
			if (state == false) {
				System.out.println("회사 없음 / 회사 등록");
				Company company = new Company();
				company.setCompanyName(CompanyName);
				company.setCEO_Name(CEO_Name);
				company.setBusinessNumber(businessNumber);
				company.setRealAddress(realAddress);
				session.save(company);
			} else {
				System.out.println("회사 있음 / break");
				return true;
			}

			LoggingTool.createLog(session, user, "Manager", "거래처Excel 등록");
			LoggerUtil.info("company", CompanyName, "ExcelRegist");
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
