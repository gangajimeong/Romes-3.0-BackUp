package com.company.ROMES.functions;

import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.company.ROMES.Services.UserService;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.Log;
import com.company.ROMES.entity.User;
@Component
public class LoggingTool {
	@Autowired
	SessionFactory factory;
	@Autowired
	UserDAO dao;	

	public static void createLog(Session session,User user,String category,String msg) {
		Log log = new Log();
		log.setTime(LocalDateTime.now());
		log.setAction(msg);
		log.setCategory(category);
		log.setUser(user);
		session.save(log);
	}
	
	public void createLog(String category,String msg) {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		Session session =null;
		Transaction transaction = null;
		try {
			User user = dao.selectByIdForUser(session, userId);
			Log log = new Log();
			log.setCategory(category);
			log.setTime(LocalDateTime.now());
			log.setAction(msg);
			log.setCategory(category);
			log.setUser(user);
			session.save(log);
			transaction.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			SessionMethod.closeSession(session, transaction);
		}
	}
	
}
