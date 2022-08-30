package com.company.ROMES.dao;



import org.hibernate.Session;

import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.Authorities;
import com.company.ROMES.entity.User;
import com.company.ROMES.interfaces.dao.SystemM.AuthorityDAOInterface;

@Repository
public class AuthorityDAO implements AuthorityDAOInterface {

	@Override
	public boolean removeUser(Session session, int Id) {
		// TODO Auto-generated method stub
		try {
			User user = session.find(User.class, Id);
			user.setEnabled(false);
			session.update(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean removeDBUserData(Session session, int Id) {
		// TODO Auto-generated method stub
		try {
			User user = session.find(User.class, Id);
			session.delete(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}

	@Override
	public boolean updateAuth(Session session, int Id, String role) {
		// TODO Auto-generated method stub
		try {
			User user = session.find(User.class, Id);
			Authorities auths = user.getAuthority();
			auths.setAuthority(role);
			session.update(auths);
			session.update(user);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}


}
