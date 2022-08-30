package com.company.ROMES.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.ROMES.dao.LogDAO;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.Authorities;
import com.company.ROMES.entity.CommuteCode;
import com.company.ROMES.entity.CommuteHistory;
import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.entity.Devices;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.LabelPrinter;
import com.company.ROMES.entity.Log;
import com.company.ROMES.entity.Notice;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.UserServiceInterface;

import Error_code.ResultCode;
import hibernate.hibernate;

@Service
public class UserService implements UserServiceInterface {

	@Autowired
	SessionFactory factory;

	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	UserDAO userDao;
	@Autowired
	LogDAO logDao;

	@Override
	public void createUser(User user) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Authorities auth = new Authorities();
			Division division = session.find(Division.class, user.getDivision().getId());
			System.out.println(user.getDivision().getId()+"//user" );
			String pw = encoder.encode(user.getLoginPw());
			user.setLoginPw(pw);
			auth.setAuthority("NOTUSER");
//			division.setDivision(user.getDivision().getDivision());
//			division.setTeam(user.getDivision().getTeam());
			user.setDivision(division);
			user.setAuthority(auth);
			userDao.createUser(session, user, auth, division);
			Log log = new Log();
			log.setUser(user);
			log.setAction("회원가입:" + user.getName());
			logDao.saveLog(session, log);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

	}

	@Override
	public User getUserByIdPw(String id, String pw) {
		Session session = null;
		User user = null;
		try {
			session = factory.openSession();
			user = userDao.selectByIdPw(session, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return user;
	}

	@Override
	public int changeUserCommute(String workerId, String commuteCode) {
		int retCode = ResultCode.SUCCESS;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = userDao.selectByWorkerId(session, workerId);
			CommuteCode code = null;
			try {
				code = (CommuteCode) session
						.createQuery("Select this_ from CommuteCode this_ where this_.state='" + commuteCode + "'")
						.getSingleResult();
			} catch (NoResultException e) {
				code = new CommuteCode();
				code.setState(commuteCode);
				session.save(code);
			}
			if (user.getCommuteCode().getId() == code.getId()) {
				if (transaction != null)
					if (transaction.isActive())
						transaction.rollback();
				if (session != null)
					if (session.isOpen())
						session.close();
				return ResultCode.REQUIRE_ELEMENT_ERROR;
			}
			CommuteHistory history = new CommuteHistory();
			history.setUser(user);
			history.setCommuteCode(code);
			session.save(history);
			user.setCommuteCode(code);
			transaction.commit();
			retCode = ResultCode.SUCCESS;
		} catch (NullPointerException e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
			retCode = ResultCode.NO_RESULT;
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
			retCode = ResultCode.UNKNOWN_ERROR;
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return retCode;
	}

	@Override
	public List<User> users() {
		// TODO Auto-generated method stub
		Session session = null;
		List<User> users = null;
		try {
			session = factory.openSession();
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = userDao.findAdmin(session, userID);
			
			if(user == null) {
				return users;
			}
			
			users = userDao.SelectAllByFalse(session);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return users;
	}

	@Override
	public JSONObject saveDeviceName(String device) {
		JSONObject obj = new JSONObject();
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Devices d = new Devices();
			d.setDeviceName(device);
			session.save(d);
			transaction.commit();
			obj.put("result",ResultCode.SUCCESS);
			obj.put("id", d.getId());
		}catch(PersistenceException e) {
			e.printStackTrace();
			obj.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
			if(transaction != null)
				if(transaction.isActive())
					transaction.rollback();
		}catch(Exception e) {
			e.printStackTrace();
			obj.put("result",ResultCode.UNKNOWN_ERROR);
			if(transaction != null)
				if(transaction.isActive())
					transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		System.out.println(obj);
		return obj;
	}

	@Override
	public JSONObject getDashBoard(int userId) {
		Session session = null;
		JSONObject ret = new JSONObject();
		int month_now = LocalDate.now().getMonthValue();
		JSONParser parser = new JSONParser();
		LocalDateTime startDay = LocalDateTime.now().toLocalDate().atStartOfDay();
		Calendar cal = Calendar.getInstance();
		cal.set(startDay.getYear(), startDay.getMonthValue()-1, 1);
		int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		LocalDateTime lastDay = LocalDate.of(startDay.getYear(), startDay.getMonthValue(), last).atTime(23,59, 59);
		try {
			session= factory.openSession();
			User user = session.find(User.class, userId);
			List<Notice> notices =null;
			if(user.getDivision() != null) {
				notices =session.createQuery("Select this_ from Notice this_ where this_.user.division.id="
						+ user.getDivision().getId() + " order by this_.time").setMaxResults(100).getResultList();
			}else {
				notices =session.createQuery("Select this_ from Notice this_ where this_.user.constructionCompany.id="
						+ user.getConstructionCompany().getId() + " order by this_.time").setMaxResults(100).getResultList();
			}
			JSONArray notice = new JSONArray();
			for(Notice n : notices) {
				JSONObject object = new JSONObject();
				object.put("id", n.getId());
				object.put("uploader", n.getUser().getName());
				object.put("time", n.getTime().format(DateTimeFormatter.ofPattern("yyyy년MM월dd일 HH:mm:ss")));
				object.put("title", n.getTitle());
				object.put("msg", n.getMsg());
				List<String> urls = new ArrayList<>();
				for (String u : n.getImages()) {
					urls.add(u);
				}
				object.put("urls", urls);
				notice.add(object);
			}
			ret.put("notice", notice);
			int numberOfUnconfirmOrder = Integer.parseInt(session.createQuery("Select count(this_) from WorkOrderInfo this_ where this_.isFinished=false and this_.printer != null").getSingleResult().toString());
			int numberOfWaitForMake = Integer.parseInt(session.createQuery("Select count(this_) from WorkOrderInfo this_ where (this_.endTime between :start and :end) and this_.isFinished=true").setParameter("start", LocalDate.now().atTime(0, 0,0)).setParameter("end", LocalDate.now().atTime(23, 59, 59)).getSingleResult().toString());
			int numberOfWaitForShip = Integer.parseInt(session.createQuery("Select count(this_) from WorkOrderInfo this_ where (this_.endTime between :start and :end) and this_.isFinished=true").setParameter("start", startDay).setParameter("end", lastDay).getSingleResult().toString());
			ret.put("number_unconfirm", numberOfUnconfirmOrder);
			ret.put("number_unmake", numberOfWaitForMake);
			ret.put("number_unship", numberOfWaitForShip);
			JSONArray graphOfReceiveOrder = new JSONArray();
			List<Integer> graphData = new ArrayList<>();
			List<String> labels = new ArrayList<>();
			
			for(int i = 0; i < 12;i++) {
				cal.set(LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1 - i, 1);
				int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				int data = Integer.parseInt(session.createQuery("Select count(this_) from WorkOrderInfo this_ where this_.endTime between :start and :end and this_.isFinished=true")
						.setParameter("start", LocalDate.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1, 1).atTime(0, 0,0))
						.setParameter("end", LocalDate.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1, maxDay).atTime(23,59, 59)).getSingleResult().toString());
				graphData.add(data);
				labels.add((cal.get(Calendar.MONTH)+1)+"월");
			}
			Collections.reverse(graphData);
			Collections.reverse(labels);
			JSONObject form = new JSONObject();
			form.put("labels",labels);
			JSONArray arry = new JSONArray();
			JSONObject d = new JSONObject();
			d.put("data", graphData);
			arry.add(d);
			form.put("datasets", arry);
			ret.put("receive_order_data", form);
			ret.put("result", ResultCode.SUCCESS);
			System.out.println(ret);
//			ret.put("", d)
		}catch(Exception e) {
			e.printStackTrace();
			if(session != null)
				if(session.isOpen())
					session.close();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		}
		return ret;
	}

	@Override
	public JSONObject checkId(String id) {
		JSONObject ret = new JSONObject();
		Session session= null;
		try {
			session = factory.openSession();
			User user = (User) session.createQuery("Select this_ from User this_ where this_.LoginId='"+id+"'").getSingleResult();
			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
		}catch(NoResultException e) {
			ret.put("result", ResultCode.SUCCESS);
		}catch(Exception e) {
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject joinMember(String id, String pw, String name, String position, int divisionId, String phoneNum,
			String email) {
		JSONObject ret = new JSONObject();
		Session session= null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = null;
			try {
				user = (User) session.createQuery("Select this_ from User this_ where this_.LoginId='"+id+"'").getSingleResult();
				ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
			}catch(NoResultException e) {
				Authorities notUser = findNotUserAuthorities(session);
				BCryptPasswordEncoder  encoder = new BCryptPasswordEncoder();
				String securePw = encoder.encode(pw);
				user = new User();
				user.setLoginId(id);
				user.setLoginPw(securePw);
				user.setAuthority(notUser);
//				user.setDivision(session.find(Division.class, divisionId));
				user.setConstructionCompany(session.find(ConstructionCompany.class, divisionId));
				user.setDateOfLastPasswordChange(LocalDate.now());
				user.setLock(false);
				user.setJoinDate(LocalDateTime.now());
				user.setEnabled(true);
				user.setEmail(email);
				user.setName(name);
				user.setTel(phoneNum);
				user.setInconsistencyCount(0);
				user.setPosition(position);
				session.save(user);
				ret.put("result", ResultCode.SUCCESS);	
			}
			transaction.commit();
		}catch(Exception e) {
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(transaction != null)
				if(transaction.isActive())
					transaction.rollback();
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	 
	public Authorities findNotUserAuthorities(Session session) {
		Authorities ret = null;
		try {
			ret =  (Authorities) session.createQuery("Select this_ from Authorities this_ where this_.authority='ROLE_NOTUSER'").getSingleResult();
		}catch(NoResultException e) {
			ret = new Authorities();
			ret.setAuthUpdateDate(LocalDateTime.now());
			ret.setAuthority("ROLE_NOTUSER");
			session.save(ret);
		}
		return ret;
	}

	@Override
	public JSONObject findUser(String name, String phoneNum, String email) {
		Session session = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			User user = (User) session.createQuery("Select this_ from User this_ where this_.name='"+name+"' and this_.email='"+email+"' and this_.tel='"+phoneNum+"'").getSingleResult();
			ret.put("id", user.getId());
			ret.put("loginid", user.getLoginId());
			ret.put("result", ResultCode.SUCCESS);
		}catch(NoResultException e) {
			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
		}catch(NonUniqueResultException e){
			ret.put("result", ResultCode.DATA_ALERT_ERROR);
		}catch(Exception e) {			
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject findUser(String name, String phoneNum, String email, String id) {
		Session session = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			User user = (User) session.createQuery("Select this_ from User this_ where this_.name='"+name+"' and this_.email='"+email+"' and this_.tel='"+phoneNum+"' and this_.LoginId='"+id+"'").getSingleResult();
			ret.put("id", user.getId());
			ret.put("result", ResultCode.SUCCESS);
		}catch(NoResultException e) {
			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
		}catch(NonUniqueResultException e){
			ret.put("result", ResultCode.DATA_ALERT_ERROR);
		}catch(Exception e) {			
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject resetPassword(int userId,String newPw) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			transaction= session.beginTransaction();
			User user = session.find(User.class, userId);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setLoginPw(encoder.encode(newPw));
			session.update(user);
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
		}catch(Exception e) {
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(transaction != null)
				if(transaction.isActive())
					transaction.rollback();
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject getDivisionList() {
		Session session = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			List<ConstructionCompany> companys = session.createQuery("Select this_ from ConstructionCompany this_ where disabled = false").getResultList(); 
			JSONArray arry = new JSONArray();
			for(ConstructionCompany company: companys) {
				JSONObject o = new JSONObject();
				o.put("id", company.getId());
				o.put("division", company.getCompanyName());
				arry.add(o);
			}
			ret.put("data", arry);
			ret.put("result", ResultCode.SUCCESS);
		}catch(Exception e) {
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject acceptJoinRequest(int userId) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = session.find(User.class, userId);
			Authorities auth = (Authorities) session.createQuery("Select this_ from Authorities this_ where this_.authority='ROLE_CTT_USER'").getSingleResult();
			user.setAuthority(auth);
			session.update(user);
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		}finally {
			if(transaction != null)
				if(transaction.isActive())
					transaction.rollback();
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject changeAuthorities(int userId, boolean isAdmin) {
		String att = "";
		if(isAdmin)
			att = "ROLE_CTT_ADMIN";
		else
			att = "ROLE_CTT_USER";
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = session.find(User.class, userId);
			Authorities auth = (Authorities) session.createQuery("Select this_ from Authorities this_ where this_.authority='"+att+"'").getSingleResult();
			user.setAuthority(auth);
			session.update(user);
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		}finally {
			if(transaction != null)
				if(transaction.isActive())
					transaction.rollback();
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject getUserList(int userId) {
		Session session = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			User user = session.find(User.class, userId);
			List<User> users = session.createQuery("Select this_ from User this_ where this_.enabled= true and this_.division.id="+user.getDivision().getId()+" order by this_.id").getResultList();
			JSONArray arry = new JSONArray();
			for(User d : users) {
				JSONObject o = new JSONObject();
				o.put("id",d.getId());
				o.put("create", d.getJoinDate()==null?"정보 없음":d.getJoinDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
				o.put("loginId", d.getLoginId());
				o.put("auth", d.getAuthority() == null?"No Info":d.getAuthority().getAuthority());
				o.put("name", d.getName());
				o.put("email", d.getEmail());
				o.put("phone", d.getTel());
				o.put("position", d.getPosition());
				o.put("respon",d.getResponsibility());
				arry.add(o);
				                                                                                                                                                                                                                                                                                                                                                   
			}
			ret.put("data", arry);
			ret.put("result", ResultCode.SUCCESS);
		}catch(Exception e) {
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
	@Override
	public JSONArray getDivision() {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			List<Division> divison = session.createQuery("Select this_ from Division this_").getResultList();
			for(Division d : divison) {
				JSONObject o = new JSONObject();
				o.put("id", d.getId());
				o.put("division", d.getDivision());
				
				ret.add(o);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}
}
