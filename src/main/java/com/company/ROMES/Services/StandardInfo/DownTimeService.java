package com.company.ROMES.Services.StandardInfo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.dao.DownTimeDAO;
import com.company.ROMES.entity.DownTime;
@Service
public class DownTimeService {
	@Autowired
	SessionFactory factory;
	
	@Autowired
	DownTimeDAO downTimeDao;
	
	public List<DownTime> getDownTimes(){
		Session session = null;
		List<DownTime> ret = null;
		try {
			session = factory.openSession();
			ret = downTimeDao.getAllDownTime(session);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
	public boolean createDownTime(DownTime downTime) {
		Session session = null;
		Transaction transaction = null;
		boolean ret = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(downTime);
			transaction.commit();
			ret = true;
		}catch(Exception e) {
			ret = false;
			e.printStackTrace();
			if(transaction != null)
				if(transaction.isActive())
					transaction.rollback();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
	public boolean updateDownTime(DownTime downTime) {
		Session session = null;
		Transaction transaction = null;
		boolean ret = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.update(downTime);
			transaction.commit();
			ret = true;
		}catch(Exception e) {
			ret = false;
			e.printStackTrace();
			if(transaction != null)
				if(transaction.isActive())
					transaction.rollback();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	public boolean deleteDownTime(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean ret = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			DownTime downTime = session.find(DownTime.class, id);
			if(downTime != null)
				session.delete(downTime);
			transaction.commit();
			ret = true;
		}catch(Exception e) {
			ret = false;
			e.printStackTrace();
			if(transaction != null)
				if(transaction.isActive())
					transaction.rollback();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
}
