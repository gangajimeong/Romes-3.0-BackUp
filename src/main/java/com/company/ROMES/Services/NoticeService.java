package com.company.ROMES.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.LoggerUtil;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.Notice;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.NoticeServiceInterface;

import Error_code.ResultCode;

@Service
public class NoticeService implements NoticeServiceInterface{
	@Autowired
	SessionFactory factory;
	
	@Override
	public JSONObject getNotice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getNotice(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getNotice(Division division) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject addNotice(String title,String msg, int userid) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = session.find(User.class, userid);
			Notice notice = new Notice();
			notice.setTitle(title);
			notice.setMsg(msg);
			notice.setUser(user);
			notice.setTime(LocalDateTime.now());
			session.save(notice);
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
			LoggerUtil.info("Android", "공지사항 추가 ");
		}catch(Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		}finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret;
	}

	
	
}
