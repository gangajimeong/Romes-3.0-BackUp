package com.company.ROMES.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.CommonQueryContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.CommonCode;
import com.company.ROMES.interfaces.dao.StandardInfo.CommonCodeInterface;
@Repository
public class CommonCodeDAO implements CommonCodeInterface {

	@Override
	public List<CommonCode> SelectCommonCode(Session session) {
		return session.createCriteria(CommonCode.class).list();

	}
	
	//Exist Information(Not Delete)
	@Override
	public List<CommonCode> SelectCommonCodeByFalse(Session session) {
	return session.createQuery("Select this_ from CommonCode this_ where this_.disabled=false order by this_.classification").getResultList();
	}
	@Override
	public CommonCode SelectCommonCodeById(Session session, int id) {
		// TODO Auto-generated method stub
		return (CommonCode) session.createQuery("Select this_ from CommonCode this_ where this_.disabled=false and this_.id="+id).getSingleResult();//여기에 문제있음
	}

	@Override
	public void UpdateCommonCode(Session session, CommonCode commonCode) {
		// TODO Auto-generated method stub
		session.update(commonCode);

	}

	@Override
	public void CreateCommonCode(Session session, CommonCode commoncode) {
		// TODO Auto-generated method stub
		session.save(commoncode);
	}
	
	@Override
	public void DeleteCommonCode(Session session, CommonCode commonCode) {
		// TODO Auto-generated method stub
		commonCode.setDisabled(true);
		session.update(commonCode);
	}
	@Override
	public List<CommonCode> SelectCharactericticsCode(Session session, String text) {
		// TODO Auto-generated method stub
		return session.createQuery("Select this_.codeId from CommonCode this_ where this_.codeId ='"+text+"' and this_.disabled=false").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonCode> SelectCommonCodeByCodeID(Session session, String text) {
		System.out.println("입력:"+text);
		return session.createQuery("Select this_ from CommonCode this_ where this_.codeId ='"+text+"' and this_.disabled=false").getResultList();
	}
	

}