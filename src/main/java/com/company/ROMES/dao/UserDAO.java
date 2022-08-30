package com.company.ROMES.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.Authorities;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;
import com.company.ROMES.interfaces.dao.UserDAOInterface;
@Repository
public class UserDAO implements UserDAOInterface {
	@Autowired
	SessionFactory factory;
	@Autowired
	BCryptPasswordEncoder encoder;
	@Override
	public List<User> SelectAllByFalse(Session session) {
		return session.createQuery("Select this_ from User this_ where this_.enabled = true Order By this_.JoinDate desc").getResultList();
	}
	@Override
	public List<User> selectAll(Session session) {
		return session.createCriteria(User.class).list();
	}
	
	@Override
	public boolean createUser(Session session, User user, Authorities auth, Division division ) {
		try {
			session.save(auth);
			session.save(division);
			session.save(user);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	@Override
	public boolean updateUser(Session session, User user) {
		try {
			session.update(user);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUser(Session session, User user) {
		try {
			session.delete(user);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User selectByPk(Session session, int pk) {
		return session.find(User.class, pk);
	}

	@Override
	public User selectByIdPw(Session session, String id, String pw) {
		User user = (User) session.createQuery("Select this_ from User this_ where this_.LoginId='"+id+"' and this_.enabled=true").getSingleResult();
		encoder = new BCryptPasswordEncoder();
		if(encoder.matches(pw, user.getLoginPw())) {
			return user;
		}else {
			System.out.println("Wrong PassWord");
			return null;
		}
	}

	@Override
	public User selectByWorkerId(Session session, String workerId) {
		User user = null;
		try {
			user = (User) session.createQuery("Select this_ from User this_ where this_.workerId='"+workerId+"'").getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	@Override
	public String selectByLoginId(Session session, String LoginId) {
		return (String) session.createQuery("Select this_.name from User this_ where this_.LoginId='"+LoginId+"'").getSingleResult();
	}
	@Override
	public User selectByIdForUser(Session session, String id) {
		// TODO Auto-generated method stub
		session = factory.openSession();
		return (User) session.createQuery("Select this_ from User this_ where this_.LoginId='"+id+"'").getSingleResult();
	}

	@Override
	public User findAdmin(Session session, String id) {
		session = factory.openSession();
		return (User) session.createQuery("Select this_ from User this_ where this_.LoginId= '"+id+"' and this_.authority.authority like '%ROLE#_ADMIN%' escape '#' or this_.LoginId= '"+id+"' and this_.authority.authority like '%ROLE#_MANAGER%' escape '#'").getSingleResult();
	}

	

}
