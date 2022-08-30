package com.company.ROMES.Services.StandardInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.dao.ErrorCodeDAO;
import com.company.ROMES.dao.ErrorProductHistoryDAO;
import com.company.ROMES.dao.LocationMasterDAO;
import com.company.ROMES.dao.LogDAO;
import com.company.ROMES.dao.LotDAO;
import com.company.ROMES.dao.MeterialProductDAO;
import com.company.ROMES.dao.SRHistoryDAO;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.ErrorCode;
import com.company.ROMES.entity.ErrorProductHistory;
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.Log;
import com.company.ROMES.entity.Lot;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.SRHistory;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.interfaces.service.StandardInfo.ErrorCodeServiceInterface;

import Error_code.ResultCode;

@Service
public class ErrorService implements ErrorCodeServiceInterface {

	@Autowired
	SessionFactory factory;

	@Autowired
	ErrorCodeDAO errorCodeDao;

	@Autowired
	LotDAO lotDao;

	@Autowired
	MeterialProductDAO meterialDao;

	@Autowired
	SRHistoryDAO srhistoryDao;

	@Autowired
	LocationMasterDAO locationDao;

	@Autowired
	UserDAO userDao;

	@Autowired
	ErrorProductHistoryDAO errorProductHistoryDao;

	@Autowired
	LogDAO logDao;

	@Override
	public ErrorCode selectErrorCodeById(int id) {
		ErrorCode ret = null;
		Session session = null;
		try {
			session = factory.openSession();
			ret = errorCodeDao.selectError(session, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public List<ErrorCode> selectAllErrorCode() {
		List<ErrorCode> ret = null;
		Session session = null;
		try {
			session = factory.openSession();
			ret = errorCodeDao.selectAllError(session);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public boolean createErrorCode(ErrorCode code) {
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction(); 
			ret = errorCodeDao.saveError(session, code);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = userDao.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "불량코드 등록: "+code.getErrorType());
			
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
			if(transaction.isActive())
				transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public boolean updateErrorCode(ErrorCode code) {
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ret = errorCodeDao.updateError(session, code);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = userDao.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "불량코드 업데이트: "+code.getErrorType());
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
			if(transaction.isActive())
				transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public boolean deleteErrorCode(int id) {
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ErrorCode ec = session.find(ErrorCode.class, id);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			
			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = userDao.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "불량코드 삭제: "+ec.getErrorType());
			ret = errorCodeDao.deleteError(session, id);			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
			if(transaction.isActive())
				transaction.rollback();
		
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}


	@Override
	public int productErrorProcess(int userId, int lotId, int errorId, int count, boolean isUseDiscount) {
		Session session = null;
		Transaction transaction = null;
		int ret = 0;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = userDao.selectByPk(session, userId);
			Lot lot = lotDao.selectLotById(session, lotId);
			ErrorCode code = null;
			if (!isUseDiscount) {
				code = errorCodeDao.selectError(session, errorId);
			} else {
				code = (ErrorCode) session
						.createQuery("Select this_ from ErrorCode this_ where this_.errorType='제품 사용(제고차감)'")
						.getSingleResult();
			}
			LocationMaster m = lot.getLocation();
			Material Material = session.find(Material.class, lot.getProductId());
//			if (Material == null) {
//				ManufactureProduct product = session.find(ManufactureProduct.class, lot.getProductId());
////				product.setStockCount(product.getStockCount() - count);
//				session.update(product);
//			} else {
				Material.setStockCount(Material.getStockCount() - count);
				if (!meterialDao.updateMeterial(session, Material)) {
					transaction.rollback();
					if (session != null)
						if (session.isOpen())
							session.close();
					return ResultCode.DATA_ALERT_ERROR;
				}
				;
//			}
			Map<Integer, Integer> stockMap = m.getProductStocksMap();
			m.setStockCount(lot.getProductId(), stockMap.getOrDefault(lot.getProductId(), 0) - count);
			if (!locationDao.updateLocation(session, m)) {
				transaction.rollback();
				if (session != null)
					if (session.isOpen())
						session.close();
				return ResultCode.DATA_ALERT_ERROR;
			}
			lot.setCount(lot.getCount() - count);
			if (!lotDao.updateLot(session, lot)) {
				transaction.rollback();
				if (session != null)
					if (session.isOpen())
						session.close();
				return ResultCode.DATA_ALERT_ERROR;
			}

			SRHistory history = new SRHistory();
			history.setLocation(m);
			history.addLot(lot);
			history.setType(SRHistory.RELEASE);
			history.setTime(LocalDateTime.now());
			history.setCount(count);
			history.setProductId(lot.getProductId());
			history.setUser(user);
			history.setRemark(code.getErrorType());
			if (!srhistoryDao.saveSRHistory(session, history)) {
				transaction.rollback();
				if (session != null)
					if (session.isOpen())
						session.close();
				return Error_code.ResultCode.DATA_ALERT_ERROR;
			}
			;

			if (!isUseDiscount) {
				ErrorProductHistory errorHistory = new ErrorProductHistory();
				errorHistory.setLot(lot);
				errorHistory.setErrorCount(count);
				errorHistory.setUser(user);
				errorHistory.setErrorCode(code);
				if (!errorProductHistoryDao.saveErrorHistory(session, errorHistory)) {
					transaction.rollback();
					if (session != null)
						if (session.isOpen())
							session.close();
					return ResultCode.DATA_ALERT_ERROR;
				}
			}
			Log log = new Log();
			log.setUser(user);
			log.setAction("");
			logDao.saveLog(session, log);
			transaction.commit();
			ret = Error_code.ResultCode.SUCCESS;
		} catch (NullPointerException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			ret = ResultCode.NO_RESULT;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			ret = ResultCode.UNKNOWN_ERROR;
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}
}
