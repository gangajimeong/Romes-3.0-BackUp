package com.company.ROMES.interfaces.dao.StandardInfo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.ROMES.entity.CommonCode;

public interface CommonCodeInterface {
	//전체선택
	public List<CommonCode> SelectCommonCode(Session session);
	public List<CommonCode> SelectCommonCodeByFalse(Session session);
	//id 값으로 검색
	public CommonCode SelectCommonCodeById(Session session ,int id);
	public List<CommonCode> SelectCharactericticsCode(Session session, String text);
	public List<CommonCode> SelectCommonCodeByCodeID(Session session , String text);
	public void UpdateCommonCode(Session session,CommonCode commonCode);
	public void CreateCommonCode(Session session,CommonCode commonCode);
	public void DeleteCommonCode(Session session,CommonCode commonCode);
	
}