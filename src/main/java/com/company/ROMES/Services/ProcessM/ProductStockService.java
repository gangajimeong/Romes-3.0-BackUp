package com.company.ROMES.Services.ProcessM;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.ProcessM.ProductStockServiceInterface;
@Service
public class ProductStockService implements ProductStockServiceInterface {
	@Autowired
	SessionFactory factory;
	@Override
	public JSONArray getProductStockData() {
		JSONArray ret = new JSONArray();
		Session session = null;
		try {
			session = factory.openSession();
			List<Material> materials = session
					.createQuery("Select this_ from Material this_ where this_.isDisable=false order by this_.id")
					.getResultList();
			List<ManufactureProduct> manufactures = session.createQuery(
					"Select this_ from ManufactureProduct this_ where this_.isDisable = false order by this_.id")
					.getResultList();
			for (Material m : materials) {
				JSONObject object = new JSONObject();
				object.put("id", m.getId());
				object.put("type", "원부자재");
				object.put("name", m.getName());
				object.put("url", m.getUrl());
				object.put("quentity", m.getStockCount());
				object.put("properquentity", m.getProperQuentity());
				object.put("spare", m.getStockCount()-m.getProperQuentity());
				List<LocationMaster> locations = session.createQuery(
						"Select this_ from LocationMaster this_ left join this_.productStocksMap m where index(m)="
								+ m.getId())
						.getResultList();
				JSONArray arry = new JSONArray();
				for (LocationMaster l : locations) {
					int i = l.getProductCount(m.getId());
					if (i > 0) {
						JSONObject o = new JSONObject();
						o.put("location", l.getName());
						o.put("count", i);
						arry.add(o);
					}
				}
				object.put("locations", arry);
				ret.add(object);
			}
			for (ManufactureProduct m : manufactures) {
				JSONObject object = new JSONObject();
				object.put("id", m.getId());
				object.put("type", "완제품");
				object.put("name", m.getName());
				object.put("url", "no_image.png");
//				object.put("quentity", m.getStockCount());
//				object.put("proper-quentity", m.getProperQuentity());
				List<LocationMaster> locations = session.createQuery(
						"Select this_ from LocationMaster this_ left join this_.productStocksMap m where index(m)="
								+ m.getId())
						.getResultList();
				JSONArray arry = new JSONArray();
				for (LocationMaster l : locations) {
					int i = l.getProductCount(m.getId());
					if (i > 0) {
						JSONObject o = new JSONObject();
						o.put("location", l.makeName());
						o.put("count", i);
						arry.add(o);
					}
				}
				object.put("locations", arry);
				ret.add(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

}
