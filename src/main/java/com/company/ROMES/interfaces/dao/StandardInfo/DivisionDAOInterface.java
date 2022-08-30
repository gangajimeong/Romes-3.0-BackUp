package com.company.ROMES.interfaces.dao.StandardInfo;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;

public interface DivisionDAOInterface {
	
	public List<Division>SelectAll(Session session);
	public List<Division>SelectAllByFalse(Session session);
	public List<User>SelectEmployee(Session session, int DivId);
	public Division SelectDivision(Session session, int id);
	public boolean createDivision(Session session, Division division);
	public boolean updateDivision(Session session, Division division);
	public boolean deleteDivision(Session session, int id);
	

}
