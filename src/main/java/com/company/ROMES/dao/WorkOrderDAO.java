package com.company.ROMES.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.Dto.WorkOrderDto;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.interfaces.dao.WorkOrderDAOInterface;

@Repository
public class WorkOrderDAO implements WorkOrderDAOInterface{

	@Override
	public List<WorkOrderInfo> getWorkOrder(Session session, LocalDate date, boolean isComplete) {
		return session.createQuery("Select this_ from WorkOrderInfo this_ where (this_.plan.predictStartTime between :start and :end) or this_.isFinished="+isComplete)
				.setParameter("start", date.atTime(0, 0, 0))
				.setParameter("end", date.atTime(23,59,59))
				.getResultList();
	}

	@Override
	public List<WorkOrderInfo> getWorkOrder(Session session, LocalDate start, LocalDate End, boolean isComplete) {
		// TODO Auto-generated method stub
		return session.createQuery("Select this_ from WorkOrderInfo this_ where (this_.plan.predictStartTime between :start and :end) or this_.isFinished="+isComplete)
				.setParameter("start", start.atTime(0, 0, 0))
				.setParameter("end", End.atTime(23,59,59))
				.getResultList();
	}

	@Override
	public List<WorkOrderInfo> getWorkOrder(Session session, boolean isComplete) {
		// TODO Auto-generated method stub
		return session.createQuery("Select this_ from WorkOrderInfo this_ where this_.isFinished="+isComplete+" and this_.printer != null order by this_.id")
				.getResultList();
	}
	
	public List<WorkOrderDto> getWorkOrderInfo(Session session){
		List<WorkOrderInfo> infos = session.createQuery("Select this_ from WorkOrderInfo this_ where this_.isFinished=false and this_.printer != null order by this_.isEmergency desc, this_.id").getResultList();
		List<WorkOrderDto> dtos= new ArrayList<WorkOrderDto>();
		infos.stream().forEach(e->dtos.add(new WorkOrderDto(e)));
		return dtos;
	}
	
	public List<WorkOrderDto> getCompleteWork(Session session, LocalDate date, boolean isComplete) {
		List<WorkOrderInfo> infos = session.createQuery("Select this_ from WorkOrderInfo this_ where (this_.endTime between :start and :end) and this_.isFinished="+isComplete + " order by this_.endTime asc")
				.setParameter("start", date.atTime(0, 0, 0))
				.setParameter("end", date.atTime(23,59,59))
				.getResultList();
		List<WorkOrderDto> dtos = new ArrayList<WorkOrderDto>();
		infos.stream().forEach(e -> dtos.add(new WorkOrderDto(e)));
		
		return dtos;
	}
}
