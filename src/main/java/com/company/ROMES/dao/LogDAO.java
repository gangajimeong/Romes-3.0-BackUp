package com.company.ROMES.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.Log;
import com.company.ROMES.entity.User;
import com.company.ROMES.interfaces.dao.LogDAOInterface;

@Repository
public class LogDAO implements LogDAOInterface {

	@Override
	public List<Log> selectAllLog(Session session) {
		return session.createCriteria(Log.class).addOrder(Order.asc("time")).list();
	}

	@Override
	public List<Log> selectAllLog(Session session, User user) {
		// TODO Auto-generated method stub
		return session.createQuery("Select this_ from Log this_ where this_.user.id="+user.getId()+" order by this_.time asc").getResultList();
	}

	@Override
	public boolean saveLog(Session session, Log log) {
		try{
			session.save(log);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateLog(Session session, Log log) {
		try{
			session.update(log);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteLog(Session session, Log log) {
		try{
			session.delete(log);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
