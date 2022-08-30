package com.company.ROMES.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.Company_manager;
import com.company.ROMES.interfaces.dao.StandardInfo.CompanyManagementDAOInterface;
@Repository

public class CompanyManagementDAO implements CompanyManagementDAOInterface {

	@Override
	public List<Company> SelectCompanyList(Session session) {
		// TODO Auto-generated method stub

		return session.createCriteria(Company.class).list();
	}

	@Override
	public List<Company> SelectCompanyByFalse(Session session) {
		// TODO Auto-generated method stub
		return session.createQuery("Select this_ from Company this_ where this_.disabled=false").getResultList();
	}

	@Override
	public Company SelectCompanyById(Session session, int id) {
		// TODO Auto-generated method stub
		return session.find(Company.class, id);
	}
	
	@Override
	public void createCompany(Session session, Company company) {
		// TODO Auto-generated method stub
		session.save(company);
	}

	@Override
	public void UpdateCompany(Session session, Company company) {
		// TODO Auto-generated method stub
		session.update(company);
	}

	@Override
	public void DeleteCompany(Session session, Company company) {
		// TODO Auto-generated method stub
		company.setDisabled(true);
		session.update(company);

	}

	@Override
	public void registerManager(Session session, Company_manager company_manager) {
		// TODO Auto-generated method stub
		session.save(company_manager);
		
	}

	@Override
	public void updateManager(Session session, Company_manager company_manager) {
		// TODO Auto-generated method stub
		session.update(company_manager);
	}

	@Override
	public void deleteManager(Session session, Company_manager company_manager) {
		// TODO Auto-generated method stub
		company_manager.setDisable(true);
		session.update(company_manager);
		
	}

	@Override
	public List<Company_manager> SelectManagerList(Session session, int id) {
		// TODO Auto-generated method stub
		
		return session.createQuery("Select this_.managers from Company this_ where this_.id ="+id).getResultList();
	}

	@Override
	public Company_manager company_manager(Session session, int id) {
		// TODO Auto-generated method stub
		return session.find(Company_manager.class, id);
	}

	

}
