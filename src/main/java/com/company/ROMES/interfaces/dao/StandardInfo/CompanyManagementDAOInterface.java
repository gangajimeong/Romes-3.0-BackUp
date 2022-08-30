package com.company.ROMES.interfaces.dao.StandardInfo;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.Company_manager;

public interface CompanyManagementDAOInterface {
	//전체 검색
	public List<Company>SelectCompanyList(Session session);
	//삭제 되지 않은 값 검색
	public List<Company>SelectCompanyByFalse(Session session);
	public Company SelectCompanyById(Session session, int id);
	public List<Company_manager>SelectManagerList(Session session, int id);
	public Company_manager company_manager(Session session, int id);
	public void registerManager(Session session, Company_manager company_manager);
	public void updateManager(Session session, Company_manager company_manager);
	public void deleteManager(Session session, Company_manager company_manager);
	public void createCompany(Session session, Company company);
	public void UpdateCompany(Session session , Company company);
	public void DeleteCompany(Session session, Company company);
}
