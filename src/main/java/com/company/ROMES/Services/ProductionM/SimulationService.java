package com.company.ROMES.Services.ProductionM;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.entity.CycleTime;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.ProductionM.SimulationServiceInterface;

@Service
public class SimulationService implements SimulationServiceInterface{

	@Autowired
	SessionFactory factory;
	
	@Override
	public JSONArray getMaterial() {
		Session session = null;
		JSONArray ret = new JSONArray();

		try {
			session = factory.openSession();
			List<Material> material = new ArrayList<Material>();
			material = session.createQuery("Select this_ from Material this_ where this_.isDisable = false").getResultList();
			for (Material list : material) {
				JSONObject ob = new JSONObject();
				ob.put("id", list.getId());
				ob.put("name", list.getName());
				ob.put("size", list.getStandard());
				ob.put("count", list.getStockCount());

				ret.add(ob);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}
	
	public JSONArray getLinePd() {
		Session session = null;
		JSONArray ret = new JSONArray();
		
		try {
			session = factory.openSession();
			List<WorkOrderInfo> info = new ArrayList<WorkOrderInfo>();
			info = session.createQuery("Select a from WorkOrderInfo a where a.isFinished = "+false+" and printer_id != null order by printer_id ").getResultList();
			for(WorkOrderInfo wo : info) {
				JSONObject ob = new JSONObject();
				ob.put("id", wo.getId());
				ob.put("line", wo.getPrinter().getLine());
				ob.put("name", wo.getProduct());
				ob.put("count", wo.getPlanCount());
				
				ret.add(ob);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}
	
	public JSONArray getSimulation() {
		Session session = null;
		JSONArray ret = new JSONArray();
		
		try {
			session = factory.openSession();
			List<WorkOrderInfo> info = new ArrayList<WorkOrderInfo>();
			info = session.createQuery("Select a from WorkOrderInfo a where a.isFinished = "+false+" and printer_id != null order by printer_id ").getResultList();
			for(WorkOrderInfo wo : info) {
				JSONObject ob = new JSONObject();
				ob.put("id", wo.getId());
				ob.put("line", wo.getPrinter().getLine());
				ob.put("name", wo.getProduct());
				ob.put("count", wo.getPlanCount());
				ob.put("mr", wo.getPlanCount()*wo.getPrinter().getMRequirement());
				
				
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
