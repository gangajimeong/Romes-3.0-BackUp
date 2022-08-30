package com.company.ROMES.Services.SystemM;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.LoggerUtil;
import com.company.ROMES.entity.Division;
import com.company.ROMES.functions.SessionMethod;

@Service
public class SessionService {
	@Autowired
	SessionFactory factory;
	public JSONArray getDivision() {
		Session session = null;
		JSONArray ret = new JSONArray();
		
		try {
			session = factory.openSession();
			LoggerUtil.info("테스","한글테스트");
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
}
