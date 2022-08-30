package com.company.ROMES.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.Material;
import com.company.ROMES.interfaces.dao.StandardInfo.MeterialDAOInterface;

@Repository
public class MeterialProductDAO implements MeterialDAOInterface{

	@Override
	public Material selectById(Session session, int id) {
		return session.find(Material.class, id);
	}

	@Override
	public List<Material> selectAll(Session session) {
		return session.createQuery("Select this_ from Material this_ where this_.isDisable = false Order By this_.id").list();
	}

	@Override
	public boolean saveMeterial(Session session, Material Material) {
		try {
			LocalDateTime create = LocalDateTime.now();
			Material.setCreateDate(create);
			session.save(Material);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateMeterial(Session session, Material Material) {
		try {
			LocalDateTime update = LocalDateTime.now();
			Material.setLastUpdateDate(update);
			session.update(Material);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteMeterial(Session session, int id) {
		try {
			LocalDateTime delete = LocalDateTime.now();
			Material m = session.find(Material.class, id);
			m.setDisabledDate(delete);
			m.setDisable(true);
			session.update(m);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
