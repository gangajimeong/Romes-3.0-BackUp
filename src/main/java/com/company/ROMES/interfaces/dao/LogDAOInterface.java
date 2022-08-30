package com.company.ROMES.interfaces.dao;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.Log;
import com.company.ROMES.entity.User;

public interface LogDAOInterface {
	public List<Log> selectAllLog(Session session);
	public List<Log> selectAllLog(Session session,User user);
	public boolean saveLog(Session session,Log log);
	public boolean updateLog(Session session,Log log);
	public boolean deleteLog(Session session,Log log);
}
