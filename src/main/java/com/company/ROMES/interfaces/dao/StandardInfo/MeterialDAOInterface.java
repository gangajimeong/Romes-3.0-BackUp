package com.company.ROMES.interfaces.dao.StandardInfo;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.Material;

public interface MeterialDAOInterface {
	public Material selectById(Session session, int id);
	public List<Material> selectAll(Session session);
	public boolean saveMeterial(Session session,Material Material);
	public boolean updateMeterial(Session session,Material Material);
	public boolean deleteMeterial(Session session,int id);
	
}
