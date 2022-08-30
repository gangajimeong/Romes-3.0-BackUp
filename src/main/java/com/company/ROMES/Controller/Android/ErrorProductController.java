package com.company.ROMES.Controller.Android;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.StandardInfo.ErrorService;
import com.company.ROMES.entity.ErrorCode;

@Controller
public class ErrorProductController {
	@Autowired
	ErrorService errorService;

	@RequestMapping(value = "/Android/Error/getErrorCode", method = RequestMethod.GET,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getErrorCodeList() {
		JSONObject ret = new JSONObject();
		try {
			List<ErrorCode> codes = errorService.selectAllErrorCode();
			if(codes.size() == 0) {
				ret.put("result",Error_code.ResultCode.DB_CONNECT_ERROR);
			}else {
				ret.put("result",Error_code.ResultCode.SUCCESS);
				JSONArray arry = new JSONArray();
				for(ErrorCode code:codes) {
					JSONObject o = new JSONObject();
					o.put("id", code.getId());
					o.put("type",code.getErrorType());
					o.put("code", code.getErrorCode());
					arry.add(o);
				}
				ret.put("data", arry);
			}
		}catch (NullPointerException e) {
			e.printStackTrace();
			ret.put("result", Error_code.ResultCode.DB_CONNECT_ERROR);
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("result", Error_code.ResultCode.UNKNOWN_ERROR);
		}
		return ret.toJSONString();
	}
	
	@RequestMapping(value = "/Android/Error/errorProduct", method = RequestMethod.POST,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String productDiscountByError(@RequestParam(name="userid") int userId,@RequestParam(name="lotid")int lotId,@RequestParam(name="count")int count,@RequestParam(name="errorid")int errorId,@RequestParam(name="usediscount")boolean isUseDiscount) {
		JSONObject object = new JSONObject();
		object.put("result", errorService.productErrorProcess(userId, lotId, errorId, count, isUseDiscount));
		return object.toJSONString();
	}
}
