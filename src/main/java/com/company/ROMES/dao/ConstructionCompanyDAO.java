package com.company.ROMES.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.interfaces.dao.ConstructionCompanyDAOInterface;

@Repository
public class ConstructionCompanyDAO implements ConstructionCompanyDAOInterface {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConstructionCompany> selectData(Session session){
		return session.createQuery("Select this_ from ConstructionCompany this_  where this_.disabled = false order by this_.id").getResultList();
	}
}
