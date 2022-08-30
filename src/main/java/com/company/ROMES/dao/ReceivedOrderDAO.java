package com.company.ROMES.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.User;
import com.company.ROMES.interfaces.dao.ReceivedOrderDAOInterface;

@Repository
public class ReceivedOrderDAO implements ReceivedOrderDAOInterface{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReceivedOrderInfo> selectData(Session session) {
		return session.createQuery("Select this_ from ReceivedOrderInfo this_ where this_.isDisabled=false order by this_.receivedOrderDate desc").getResultList();
	}

	@Override
	public List<ReceivedOrderInfo> selectData(Session session, LocalDate date) {
		return session.createQuery("Select this_ from ReceivedOrderInfo this_ where this_.receivedOrderDate >= :date order by this_.receivedOrderDate desc")
				.setParameter("date", date)
				.getResultList();
	}

	@Override
	public List<ReceivedOrderInfo> selectData(Session session, LocalDateTime date, LocalDateTime date2) {
		return session.createQuery("Select this_ from ReceivedOrderInfo this_ where this_.isDisabled=false and (this_.receivedOrderDate between :start and :end) order by this_.receivedOrderDate desc")
				.setParameter("start", date.isBefore(date2)?date:date2)
				.setParameter("end", date.isBefore(date2)?date2:date)
				.getResultList();
	}

	@Override
	public List<ReceivedOrderInfo> selectData(Session session, LocalDate date, LocalDate date2, Company company,
			User user) {
		CriteriaBuilder builder = session.getCriteriaBuilder(); // query builder 생성
		CriteriaQuery<ReceivedOrderInfo> query = builder.createQuery(ReceivedOrderInfo.class); // instance 생성
		Root<ReceivedOrderInfo> root = query.from(ReceivedOrderInfo.class); // from 설정
		List<Predicate> pres = new ArrayList<Predicate>(); // Predicate 는 조건문
		if(date != null && date2 != null) 
			pres.add(builder.between(root.get("receivedOrderDate"),date, date2));
		else if(date2 == null && date != null)
			pres.add(builder.greaterThanOrEqualTo(root.get("receivedOrderDate"),date));
		if(company != null)
			pres.add(builder.equal(root.get("orderCompany"), company));
		if(user != null)
			pres.add(builder.equal(root.get("user"),user));
		Predicate[] arr = pres.toArray(new Predicate[pres.size()]);
		query.select(root);
		query.where(builder.and(arr));
		query.orderBy(builder.desc(root.get("receivedOrderDate")));
		return session.createQuery(query).getResultList();
	}

	@Override
	public void saveOrUpdate(Session session, ReceivedOrderInfo info) {
		session.saveOrUpdate(info);
		
	}

	@Override
	public void delete(Session session, ReceivedOrderInfo info) {
		session.delete(info);
	}
}
