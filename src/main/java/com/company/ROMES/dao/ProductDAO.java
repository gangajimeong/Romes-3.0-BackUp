package com.company.ROMES.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.interfaces.dao.StandardInfo.ProductDAOInterface;
@Repository
public class ProductDAO implements ProductDAOInterface {

	@SuppressWarnings("unchecked")
	@Override
	public List<ManufactureProduct> SelectPrList(Session session) {
		// TODO Auto-generated method stub
		return session.createCriteria(ManufactureProduct.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManufactureProduct> SelectPrLIstByFalse(Session session) {
		// TODO Auto-generated method stub
		return session.createQuery("Select this_ from ManufactureProduct this_ where this_.isDisable = false Order By this_.id").getResultList();
	}

	@Override
	public ManufactureProduct product(Session session, int id) {
		// TODO Auto-generated method stub
		return session.find(ManufactureProduct.class, id);
	}

	@Override
	public void createProduct(Session session, ManufactureProduct manufactureProduct) {
		LocalDateTime Create = LocalDateTime.now();
		manufactureProduct.setCreateDate(Create);
		session.save(manufactureProduct);
		
	}

	@Override
	public void updateProduct(Session session, ManufactureProduct manufactureProduct) {
		LocalDateTime Update = LocalDateTime.now();
		manufactureProduct.setLastUpdateDate(Update);
		session.update(manufactureProduct);
		
	}

	@Override
	public void deleteProduct(Session session, int id) {
	ManufactureProduct m =session.find(ManufactureProduct.class,id);
	LocalDateTime Delete = LocalDateTime.now();
	m.setDisabledDate(Delete);
//	m.setProductCode("disabled");
	m.setDisable(true);
	session.update(m);	
	}

}
