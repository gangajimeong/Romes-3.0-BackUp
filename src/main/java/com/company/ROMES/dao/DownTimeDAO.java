package com.company.ROMES.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.DownTime;

@Repository
public class DownTimeDAO {
	public List<DownTime> getAllDownTime(Session session){
		return session.createQuery("Select this_ from DownTime this_ ").getResultList();
	}

}
