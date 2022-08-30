package com.company.ROMES.Services.SystemM;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.interfaces.dao.SystemM.AuthorityDAOInterface;
import com.company.ROMES.interfaces.service.SystemM.AuthConrollInterface;

@Service
public class AuthControllService implements AuthConrollInterface {

	@Autowired
	SessionFactory factory;

	@Autowired
	AuthorityDAOInterface authDaoService;

	@Override
	public boolean removeUser(int UserTable_id) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			state=authDaoService.removeUser(session, UserTable_id);
			transaction.commit();
			return state;
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();

		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public boolean updateAuth(int Id, String role) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			state=authDaoService.updateAuth(session, Id, role);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();

		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}
	

}
