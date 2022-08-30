package com.company.ROMES.functions;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class SessionMethod {
	public static void closeSession(Session session) {
		if(session != null)
			if(session.isOpen())
				session.close();
	}
	public static void closeSession(Session session,Transaction transaction) {
		if(transaction != null)
			if(transaction.isActive())
				transaction.rollback();
		if(session != null)
			if(session.isOpen())
				session.close();
	}
}
