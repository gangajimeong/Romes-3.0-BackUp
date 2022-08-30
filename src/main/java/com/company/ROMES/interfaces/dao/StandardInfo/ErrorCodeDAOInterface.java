package com.company.ROMES.interfaces.dao.StandardInfo;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.ErrorCode;

public interface ErrorCodeDAOInterface {
	public List<ErrorCode> selectAllError(Session session);
	public List<ErrorCode> selectAllError(Session session,String type);
	public ErrorCode selectError(Session session,int id);
	public boolean saveError(Session session, ErrorCode code);
	public boolean updateError(Session session, ErrorCode code);
	public boolean deleteError(Session session, int id);	
}
