package com.company.ROMES.interfaces.dao.StandardInfo;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.ManufactureProduct;

public interface ProductDAOInterface {
	
	//제품 마스터 DAO
	public List<ManufactureProduct>SelectPrList(Session session);
	public List<ManufactureProduct>SelectPrLIstByFalse(Session session);
	public ManufactureProduct product(Session session, int id);
	public void createProduct(Session session, ManufactureProduct manufactureProduct);
	public void updateProduct(Session session, ManufactureProduct manufactureProduct);
	public void deleteProduct(Session session, int id);

	
}
