package com.company.ROMES.interfaces.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.WorkOrderInfo;

public interface WorkOrderDAOInterface {
	public List<WorkOrderInfo> getWorkOrder(Session session,LocalDate date,boolean isComplete );
	public List<WorkOrderInfo> getWorkOrder(Session session,LocalDate start,LocalDate End,boolean isComplete);
	public List<WorkOrderInfo> getWorkOrder(Session session,boolean isComplete);

}
