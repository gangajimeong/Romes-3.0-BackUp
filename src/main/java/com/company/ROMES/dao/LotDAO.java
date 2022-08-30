package com.company.ROMES.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.Lot;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;
import com.company.ROMES.interfaces.dao.LotDAOInterface;

@Repository
public class LotDAO implements LotDAOInterface {

	@Override
	public List<Lot> selectAll(Session session) {
		return session.createCriteria(Lot.class).list();
	}

	@Override
	public Lot selectLotById(Session session, int id) {
		// TODO Auto-generated method stub
		return session.find(Lot.class, id);
	}

	@Override
	public Lot selectLotByLot(Session session, String lot) {
		return (Lot) session.createQuery("Select this_ from Lot this_ where this_.lot='" + lot + "'").getSingleResult();
	}

	@Override
	public List<Lot> selectLotsByProductId(Session session, int productId) {
		// TODO Auto-generated method stub
		return session.createQuery("Select this_ from Lot this_ where this_.productId=" + productId + "")
				.getResultList();
	}

	@Override
	public boolean saveLot(Session session, Lot lot) {
		try {
			session.save(lot);
			return true;
		} catch (Exception e) {
			return false;
		}
		// TODO Auto-generated method stub
	}

	@Override
	public boolean updateLot(Session session, Lot lot) {
		try {
			session.update(lot);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteLot(Session session, Lot lot) {
		try {
			session.delete(lot);
			;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getProductName(Session session, Lot lot) {
		String ret = null;
		Material Material;
		try {
			Material = (Material) session.createQuery("Select this_ from Material this_ where this_.id=" + lot.getProductId())
					.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			Material = null;
		}
		if (Material == null) {
			ret = session.find(ManufactureProduct.class, lot.getProductId()).getName();
		} else {
			ret = Material.getName();
		}

		return ret;
	}

}
