package com.company.ROMES.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.Log;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.LogServiceInterface;

@Service
public class LogService implements LogServiceInterface {
	@Autowired
	SessionFactory factory;

	@Autowired
	UserDAO us;
	
	@Override
	public JSONObject getLogData(String preTime, String lastTime, int userId, String category) {
		JSONObject object = new JSONObject();
		Session session = null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		
		
		
		
		try {
			session = factory.openSession();
			LocalDateTime preDate = LocalDate.parse(preTime, format).atTime(0, 0, 0);
			LocalDateTime lastDate = LocalDate.parse(lastTime, format).atTime(23, 59, 59);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User u = new User();
			u = us.findAdmin(session, userID);
			
			if(u!= null) {
				String query = "Select this_ from Log this_ where (this_.time between :start and :end ) ";
				if (userId != 0) {
					query = query + "and this_.user.id=" + userId +" ";
				} 
				if (!category.equals("0")){
					query = query + " and this_.category='"+category+"' ";
				}
				query = query + "order by this_.time desc";
				List<Log> infos = session.createQuery(query).setParameter("start", preDate)
						.setParameter("end", lastDate).getResultList();
				JSONArray data = new JSONArray();
				for(Log l : infos) {
					JSONObject o = new JSONObject();
					o.put("id", l.getId());
					o.put("user", l.getUser()==null?"No info":l.getUser().getName());
					o.put("category", l.getCategory());
					o.put("action", l.getAction());
					o.put("time", l.getTime().format(dateformat));
					o.put("division",l.getUser().getDivision()==null?"시공사": l.getUser().getDivision().getDivision());
					o.put("loginId",l.getUser()==null?"No info": l.getUser().getLoginId());
					data.add(o);
				}
				object.put("data", data);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return object;
	}
	
	@Override
	public JSONArray getData() {
		Session session = null;

		JSONArray rets = new JSONArray();
		try {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			session = factory.openSession();
			List<Log> logs = new ArrayList<Log>();
			logs = session.createQuery("Select this_ from Log this_ where this_.category != null and this_.user != null order by this_.time desc").getResultList();
			
			for (Log r : logs) {
				JSONObject ret = new JSONObject();
				ret.put("id", r.getId());
				ret.put("action", r.getAction());
				ret.put("category", r.getCategory());
				ret.put("time", r.getTime().toLocalDate());
				ret.put("name", r.getUser().getName());
				ret.put("loginId", r.getUser() == null?"No info":r.getUser().getLoginId());
				ret.put("division", r.getUser().getDivision() == null?"시공사":r.getUser().getDivision().getDivision());
				rets.add(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}
}
