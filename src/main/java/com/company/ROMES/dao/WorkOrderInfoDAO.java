package com.company.ROMES.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.interfaces.dao.WorkOrderInfoDAOInterface;

@Repository
public class WorkOrderInfoDAO implements WorkOrderInfoDAOInterface{
	@Autowired
	SessionFactory factory;
	@Override
	public List<WorkOrderInfo> selectAll(Session session) {
		List<WorkOrderInfo> info = null;
		try {
			info = session.createCriteria(WorkOrderInfo.class).list();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	@Override
	public List<WorkOrderInfo> selectTodayWork(User user,Session session) {
		List<WorkOrderInfo> orders = session.createQuery("Select this_ from WorkOrderInfo this_ where this_.workingDivision.id="+user.getDivision().getId()+" And this_.isFinished="+false+" And this_.plan.predictStartTime between :start And :end")
					.setParameter("start", LocalDate.now().atTime(0,0,0))
					.setParameter("end", LocalDate.now().atTime(23,59,59))
					.getResultList();
		return orders;
	}

	@Override
	public List<WorkOrderInfo> searchWorkInfo(Session session,Division division, LocalDate startDate, LocalDate endDate){
			boolean date = false;
			String sql = "Select this_ from WorkOrderInfo this_ where ";
			if(division != null) {
				sql = sql+"this_.workingDivision.id="+division.getId()+" And ";
			}if(startDate != null && endDate != null) {
				sql = sql+"this_.startTime between :start And :end";
				date = true;
			}
			if(sql.substring(sql.length()-4).equals("And "))
				sql = sql.substring(0,sql.length()-4);
			Query query = session.createQuery(sql);
			List<WorkOrderInfo> infos = query.getResultList();			
		return infos;
	}
	
	
}
