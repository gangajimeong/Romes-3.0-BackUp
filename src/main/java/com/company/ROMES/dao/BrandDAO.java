package com.company.ROMES.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.BrandUser;
import com.company.ROMES.entity.User;
import com.company.ROMES.interfaces.dao.BrandUserDAOInterFace;

@Repository
public class BrandDAO implements BrandUserDAOInterFace {
	@Autowired
	SessionFactory factory;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	public BrandUser selectByIdPw(Session session, String id, String pw) {
		BrandUser brandUser = (BrandUser) session.createQuery("Select this_ from BrandUser this_ where this_.LoginId='"+id+"'").getSingleResult();
		encoder = new BCryptPasswordEncoder();
		if(encoder.matches(pw, brandUser.getLoginPw())) {
			return brandUser;
		}else {
			System.out.println("Wrong PassWord");
			return null;
		}
	}
}
