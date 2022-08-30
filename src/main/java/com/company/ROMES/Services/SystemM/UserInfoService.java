package com.company.ROMES.Services.SystemM;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.SessionMethod;

@Service
public class UserInfoService {
	@Autowired
	SessionFactory factory;
	
	@Autowired
	UserDAO us;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	public JSONObject userInfo() {
		Session session = null;
		JSONObject ob = new JSONObject();
		try {
			
			session = factory.openSession();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			Division D = new Division();
			D = session.find(Division.class, user.getDivision().getId());
			
			ob.put("loginId", user.getLoginId());
			ob.put("position", user.getPosition());
			ob.put("phone", user.getTel());
			ob.put("division",D==null?0:D.getId());
			ob.put("email", user.getEmail());
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ob;
	}
	
	public JSONArray getDivision() {
		Session session = null;
		JSONArray ret = new JSONArray();
		
		try {
			session = factory.openSession();
			List<Division> D = session.createQuery("Select this_ from Division this_ where this_.disabled = false").getResultList();
			
			for(Division division : D) {
				JSONObject ob = new JSONObject();
				ob.put("id", division.getId());
				ob.put("division", division.getDivision());
				
				ret.add(ob);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}
	
	
	public boolean checkPW(String pw) {
		Session session = null;
		boolean state = false;
		try {
			session = factory.openSession();
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			if(encoder.matches(pw, user.getLoginPw())) {
				state = true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return state;
	}
	
	public boolean modifyInfo(User userForm) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
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
			
			System.out.println(userForm.getPosition());
			System.out.println(user.getName());
			user.setDivision(userForm.getDivision());
			user.setTel(userForm.getTel());
			user.setPosition(userForm.getPosition());
			user.setEmail(userForm.getEmail());
			
			session.update(user);
			transaction.commit();
			state = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}
	
	public boolean modifyPassword(String newPassword) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
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
			
			String pw = encoder.encode(newPassword);
			user.setLoginPw(pw);
			session.update(user);
			transaction.commit();
			state = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}
	
	public int findDivision() {
		Session session = null;
		int DivisionId = 0;
		try {
			session = factory.openSession();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			DivisionId = user.getDivision().getId();
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			SessionMethod.closeSession(session);
		}
		return DivisionId;
	}
}
