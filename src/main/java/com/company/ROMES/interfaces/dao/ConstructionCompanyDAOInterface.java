package com.company.ROMES.interfaces.dao;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.ConstructionCompany;

public interface ConstructionCompanyDAOInterface {

	List<ConstructionCompany> selectData(Session session);

}
