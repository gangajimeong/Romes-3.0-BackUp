package com.company.ROMES.interfaces.service;

import org.hibernate.Session;

import com.company.ROMES.entity.Lot;

public interface LotServiceInterface {
	public String getProductName(Session session, Lot lot);
	public String getProductName(Lot lot);
}
