package com.company.ROMES.dao;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.ErrorCode;
import com.company.ROMES.interfaces.dao.StandardInfo.ErrorCodeDAOInterface;

@Repository
public class ErrorCodeDAO implements ErrorCodeDAOInterface {

	@SuppressWarnings("unchecked")
	@Override
	public List<ErrorCode> selectAllError(Session session) {
		return session.createQuery("Select this_ from ErrorCode this_ Order By this_.id").getResultList();
	}

	@Override
	public List<ErrorCode> selectAllError(Session session, String type) {
		return session.createQuery("Select this_ from ErrorCode this_ where this_.errorType='"+type+"'").getResultList();
	}

	@Override
	public ErrorCode selectError(Session session, int id) {
		return session.find(ErrorCode.class,id);
	}

	@Override
	public boolean saveError(Session session, ErrorCode code) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String time = LocalDateTime.now().format(formatter);
		LocalDateTime create = LocalDateTime.parse(time,formatter);
		try {
			code.setCreateDate(create);
			session.save(code);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
	}

	@Override
	public boolean updateError(Session session, ErrorCode code) {
		// TODO Auto-generated method stub
	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String time = LocalDateTime.now().format(formatter);
		LocalDateTime update = LocalDateTime.parse(time,formatter);
		try {
			code.setUpdateDate(update);
			session.update(code);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteError(Session session, int id) {

		try {
			ErrorCode ec = session.find(ErrorCode.class, id);
			session.delete(ec);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
}
