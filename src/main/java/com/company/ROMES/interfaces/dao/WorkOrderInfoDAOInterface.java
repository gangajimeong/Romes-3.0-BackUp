package com.company.ROMES.interfaces.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkOrderInfo;

public interface WorkOrderInfoDAOInterface {
	public List<WorkOrderInfo>selectAll(Session session) ;
	public List<WorkOrderInfo>selectTodayWork(User user,Session session);
	public List<WorkOrderInfo>searchWorkInfo(Session session,Division division,LocalDate startDate,LocalDate endDate);
}
