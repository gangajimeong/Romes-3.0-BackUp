package com.company.ROMES.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Error_code.ResultCode;

@Repository
public class ServerStateCheck {
	@Autowired
	SessionFactory factory;
	
	public String checkDBConnection() {
		String ret = "";
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ret = Integer.toString(ResultCode.SUCCESS);
			transaction.commit();
		}catch(Exception e) {
			e.printStackTrace();
			if(transaction != null && transaction.isActive())
				transaction.commit();
			
			ret = e.getMessage();
		}finally {
			if(session != null)
				session.close();
		}
		return ret;
	}
}
