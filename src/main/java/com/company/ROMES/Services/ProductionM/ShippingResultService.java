package com.company.ROMES.Services.ProductionM;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.functions.SessionMethod;

@Service
public class ShippingResultService {
	@Autowired
	SessionFactory factory;
	
	public JSONArray CompleteWork() {
		Session session = null;
		JSONArray ret = new JSONArray();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
			session = factory.openSession();
			List<WorkOrderInfo> info = session.createQuery("Select this_ from WorkOrderInfo this_ where this_.isFinished = true order by this_.endTime").getResultList();
			
			for(WorkOrderInfo work : info) {
				JSONObject ob = new JSONObject();
				String endTime = work.getEndTime().format(format);
				ob.put("id", work.getId());
				ob.put("product", work.getProduct());
				ob.put("branch", work.getBranch() == null?"정보 없음":work.getBranch().getTitle());
				ob.put("brand",work.getOrderInfo() == null? "정보없음":work.getOrderInfo().getBrand().getCompanyName());
				ob.put("count", work.getMakeCount());
				ob.put("date", endTime);
				ob.put("user", work.getManager()==null?"정보 없음":work.getManager().getName());
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
