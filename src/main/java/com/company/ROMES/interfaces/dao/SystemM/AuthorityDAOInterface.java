package com.company.ROMES.interfaces.dao.SystemM;

import java.util.List;

import org.hibernate.Session;



public interface AuthorityDAOInterface {
	//계정 삭제 
	public boolean removeUser(Session session,int Id);
	//계정 DB에서 삭제
	public boolean removeDBUserData(Session session, int Id);
	//해당 계정 권한 추가
	public boolean updateAuth(Session session, int Id, String role);
	
	
	
}
