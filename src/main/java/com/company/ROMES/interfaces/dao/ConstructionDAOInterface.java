package com.company.ROMES.interfaces.dao;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.ConstructionPlan;
import com.company.ROMES.entity.User;

public interface ConstructionDAOInterface {
	public List<ConstructionPlan> getReceiveProductsList(Session session,User user);
}
