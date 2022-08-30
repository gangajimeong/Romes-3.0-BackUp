package com.company.ROMES.interfaces.service.StandardInfo;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.CommonCode;
import com.company.ROMES.entity.Company;

public interface CommonCodeServiceInterface {
	// 공통 코드 조회
	public List<CommonCode> selectCommonCode();
	// 삭제 되지 않은 공통 코드 조회
	public List<CommonCode> selectCommonCodeByFalse();
	// id로 데이터 조회
	public CommonCode SelectCommonCodeById(int id);
	// 공통 코드 생성
	public boolean createCommonCode(CommonCode code);
	// 공통 코드 수정
	public boolean updateCommonCode(CommonCode code);
	// 공통 코드 삭제 (숨기기)
	public boolean deleteCommonCode(CommonCode code);
	// 특성 코드 리스트 
	public List<CommonCode>characteristicCode(String text);
	// codeID로 탐색
	public List<CommonCode>SelectByCodeId(String codeId);
	//특성 코드, 칼럼 코드 가져오기
	public List<String[]>getTypes();
	
	
}
