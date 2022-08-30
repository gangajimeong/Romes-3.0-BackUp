package com.company.ROMES.Services.StandardInfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.LoggerUtil;
import com.company.ROMES.Controller.StandardInfo.Standard_TypeCode;
import com.company.ROMES.dao.CommonCodeDAO;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.CommonCode;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.interfaces.service.StandardInfo.CommonCodeServiceInterface;

@Service
public class CommonCodeService implements CommonCodeServiceInterface {
	@Autowired
	SessionFactory factory;
	@Autowired
	CommonCodeDAO commoncodeDao;
	@Autowired
	Standard_TypeCode stcode;
	@Autowired
	UserDAO us;

	@Override
	public List<CommonCode> selectCommonCode() {
		Session session = null;
		List<CommonCode> ret = null;
		try {
			session = factory.openSession();
			ret = commoncodeDao.SelectCommonCode(session);
		} catch (Exception e) {
			e.printStackTrace();
			ret = null;
			// TODO: handle exception
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public List<CommonCode> selectCommonCodeByFalse() {
		// TODO Auto-generated method stub
		Session session = null;
		List<CommonCode> ret = null;
		try {
			session = factory.openSession();
			ret = commoncodeDao.SelectCommonCodeByFalse(session);
		} catch (Exception e) {
			e.printStackTrace();
			ret = null;
			// TODO: handle exception
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public CommonCode SelectCommonCodeById(int id) {
		// TODO Auto-generated method stub
		Session session = null;
		CommonCode ret = null;
		try {
			session = factory.openSession();
			ret = commoncodeDao.SelectCommonCodeById(session, id);
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
			ret = null;
			// TODO: handle exception
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public boolean createCommonCode(CommonCode code) {
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			code.setColumnCode(code.getColumnCode() + stcode.getGenNum());
			commoncodeDao.CreateCommonCode(session, code);

			LoggingTool.createLog(session, user, "Manager", "공통코드 등록: " + code.getColumnCode());
			LoggerUtil.info("commonCode: " + code.getId(), userID, "create");

			ret = true;
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public boolean updateCommonCode(CommonCode code) {
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {

			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

//			code.setColumnCode(code.getColumnCode()+stcode.getGenNum());
			code.setColumnCode(code.getColumnCode());
			commoncodeDao.UpdateCommonCode(session, code);

			LoggingTool.createLog(session, user, "Manager", "공통코드 업데이트: " + code.getId());
			LoggerUtil.info("commonCode: " + code.getId(), userID, "update");

			ret = true;
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public boolean deleteCommonCode(CommonCode code) {
		// TODO Auto-generated method stub
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			commoncodeDao.DeleteCommonCode(session, code);

			LoggingTool.createLog(session, user, "Manager", "공통코드 삭제: " + code.getColumnCode());
			LoggerUtil.info("commonCode: "+ code.getId(), userID, "delete");
			ret = true;
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public List<CommonCode> characteristicCode(String text) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session session = null;
		List<CommonCode> ret = null;

		try {
			session = factory.openSession();
			ret = commoncodeDao.SelectCharactericticsCode(session, text);
			System.out.println(ret);

		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;

	}

	@Override
	public List<String[]> getTypes() {
		// TODO Auto-generated method stub
		Standard_TypeCode stc = new Standard_TypeCode();
		List<String[]> types = null;
		try {
			types = stc.getTypeList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return types;
	}

	@Override
	public List<CommonCode> SelectByCodeId(String codeId) {
		List<CommonCode> lists = new ArrayList<CommonCode>();
		Session session = null;
		try {
			session = factory.openSession();
			lists = commoncodeDao.SelectCommonCodeByCodeID(session, codeId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return lists;
	}
}
