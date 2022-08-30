package com.company.ROMES.interfaces.service.StandardInfo;

import java.util.List;

import com.company.ROMES.entity.ErrorCode;

public interface ErrorCodeServiceInterface {
	public ErrorCode selectErrorCodeById(int id);
	public List<ErrorCode> selectAllErrorCode();
	public boolean createErrorCode(ErrorCode code);
	public boolean updateErrorCode(ErrorCode code);
	public boolean deleteErrorCode(int id);
	public int productErrorProcess(int userId,int lotId,int errorId,int count,boolean isUseDiscount);
}
