package com.company.ROMES.Services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.dao.LotDAO;
import com.company.ROMES.entity.Lot;
import com.company.ROMES.interfaces.service.LotServiceInterface;

@Service
public class LotService implements LotServiceInterface{

	@Autowired
	SessionFactory factory;
	
	@Autowired
	LotDAO lotDao;
	
	@Override
	public String getProductName(Session session, Lot lot) {
		
		return lotDao.getProductName(session, lot);
	}

	@Override
	public String getProductName(Lot lot) {
		String ret = null;
		Session session = null;
		try {
			session = factory.openSession();
			ret = lotDao.getProductName(session, lot);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}

}
