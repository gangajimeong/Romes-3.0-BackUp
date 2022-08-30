package com.company.ROMES.interfaces.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.SRHistory;

public interface SRHistoryDAOInterface {
	//TEST 테스트 TEST 테스트
	public boolean saveSRHistory(Session session, SRHistory history);
	public boolean updateSRHistory(Session session, SRHistory history);
	public boolean deleteSRHistory(Session session, SRHistory history);
	public List<SRHistory> selectAll(Session session);
	public List<SRHistory> selectByPerid(Session session,LocalDate startdate,LocalDate endDate);
	public List<SRHistory> selectByPerid(Session session,LocalDate date);
	
}
