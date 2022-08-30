package com.company.ROMES.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.ConstructionPlan;
import com.company.ROMES.entity.User;
import com.company.ROMES.interfaces.dao.ConstructionDAOInterface;
@Repository
public class ConstructionDAO implements ConstructionDAOInterface {

	@Override
	public List<ConstructionPlan> getReceiveProductsList(Session session,User user) {
		// TODO Auto-generated method stub
		return session.createQuery("Select this_ from ConstructionPlan this_ where this_.isReceiveProduct=" + false + " And this_.constructionDivision.id="+user.getDivision().getId()+" And this_.receivedOrderInfo.isShipment="+true).getResultList();
	}

}
