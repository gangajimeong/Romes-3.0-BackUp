package com.company.ROMES.interfaces.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.User;

public interface ReceivedOrderDAOInterface {
	public List<ReceivedOrderInfo> selectData(Session session);
	public List<ReceivedOrderInfo> selectData(Session session,LocalDate date);
	public List<ReceivedOrderInfo> selectData(Session session,LocalDateTime date,LocalDateTime date2);
	public List<ReceivedOrderInfo> selectData(Session session,LocalDate date,LocalDate date2,Company company,User user);
	public void saveOrUpdate(Session session,ReceivedOrderInfo info);
	public void delete(Session session,ReceivedOrderInfo info);
	
	
	
}
