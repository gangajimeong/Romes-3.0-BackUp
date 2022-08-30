package com.company.ROMES.functions;

import org.hibernate.Session;

import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;

public class ProductMethod {
	public static Object findProduct(int id,Session session) {
		Object object = session.find(ManufactureProduct.class, id);
		if(object == null)
			object = session.find(Material.class, id);
		return object;
	}
}
