package com.company.ROMES.interfaces.dao;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.Lot;

public interface LotDAOInterface {
	public List<Lot> selectAll(Session session);
	public boolean saveLot(Session session,Lot lot);
	public boolean updateLot(Session session,Lot lot);
	public boolean deleteLot(Session session,Lot lot);
	public Lot selectLotById(Session session,int id);
	public Lot selectLotByLot(Session session,String lot);
	public List<Lot> selectLotsByProductId(Session session,int productId);
	public String getProductName(Session session,Lot lot);
}
