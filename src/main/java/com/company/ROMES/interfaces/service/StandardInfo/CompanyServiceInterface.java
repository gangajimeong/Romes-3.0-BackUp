package com.company.ROMES.interfaces.service.StandardInfo;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.Company_manager;
import com.google.gson.JsonObject;

public interface CompanyServiceInterface {
		//거래처 관리 코드 
		public List<Company>SelectCompanyList();
		//거래처 관리 삭제 되지 않은 내용 조회
		public List<Company>SelectCompanyByFalse();
		
		public JSONArray company();
		// 거래처 담당자 조회 
	
		
		//거래처 관리 pk 로 검색
		public Company SelectCompanyById(int id);
		// 거래처 담당자 검색
		public JSONArray managerLists(int id);
		//거래처 관리 담당자 등록 (company Id)
		public boolean ManagerRegister(Company_manager company_manager,int id);
		// 거래처 담당자 정보 수정 
		public boolean MangerInfoUpdate(Company_manager company_manager,int id);
		// 거래처 담당자 정보 삭제
		public boolean ManagerInfoDelete(int id);
		//거래처 생성
		public boolean createCompany(Company company);
		public boolean UpdateCompany(Company company);
		public boolean DeleteCompany(Company company);
		public JSONArray constructionCompany();
		public JSONObject updateCompanyBtn(Company company);
		
		//Excel Import
		public boolean importExcel(JsonObject datas);
}
