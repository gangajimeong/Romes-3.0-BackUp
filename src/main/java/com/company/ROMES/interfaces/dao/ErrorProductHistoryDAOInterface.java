package com.company.ROMES.interfaces.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.ErrorProductHistory;


public interface ErrorProductHistoryDAOInterface {
	public List<ErrorProductHistory> selectAllErrorHistory(Session session);
	public List<ErrorProductHistory> selectErrorHistoryByPeriod(Session session,LocalDate date);
	public List<ErrorProductHistory> selectErrorHistoryByPeriod(Session session,LocalDate date,LocalDate date2);
	public ErrorProductHistory selectErrorHistory(Session session,int id);
	public boolean saveErrorHistory(Session session, ErrorProductHistory history);
	public boolean updateErrorHistory(Session session, ErrorProductHistory history);
	public boolean deleteErrorHistory(Session session, ErrorProductHistory history);
}
