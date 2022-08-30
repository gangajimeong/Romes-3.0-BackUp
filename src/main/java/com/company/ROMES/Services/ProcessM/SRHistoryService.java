package com.company.ROMES.Services.ProcessM;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.entity.Lot;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.SRHistory;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.ProcessM.SRHistoryServiceInterface;
@Service
public class SRHistoryService implements SRHistoryServiceInterface {
	@Autowired
	SessionFactory factory;
	
	@Override
	public JSONArray getSRList() {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			List<SRHistory> arry = session.createQuery("Select this_ from SRHistory this_ order by this_.time desc").getResultList();
			for(SRHistory data : arry) {
				String productName = "No info";
				if(session.find(ManufactureProduct.class, data.getProductId()) != null) {
					productName = session.find(ManufactureProduct.class, data.getProductId()).getName();
				}else {
					if(session.find(Material.class, data.getProductId()) != null)
					productName = session.find(Material.class, data.getProductId()).getName();
				}
				JSONObject object = new JSONObject();
				object.put("productName", productName);
				object.put("location", data.getLocation()==null?"정보 없음":data.getLocation().getName());
				object.put("time", data.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				object.put("id", data.getId());
				object.put("count", data.getCount());
				object.put("type", data.getType()==SRHistory.STORE?"입고":"출고");
				List<String> lots = new ArrayList<String>();
				for(Lot lot: data.getLots()) {
					lots.add(lot.getLot());
				}
				object.put("lot",lots);
				object.put("user", data.getUser()==null?"정보 없음":data.getUser().getName());
				object.put("remark", data.getRemark());
				ret.add(object);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

}
