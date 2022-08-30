package com.company.ROMES.interfaces.dao;

import org.hibernate.Session;

import com.company.ROMES.entity.BrandUser;

public interface BrandUserDAOInterFace {
	public BrandUser selectByIdPw(Session session, String id, String pw);
}
