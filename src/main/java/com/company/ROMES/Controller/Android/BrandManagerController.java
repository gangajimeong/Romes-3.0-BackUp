package com.company.ROMES.Controller.Android;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.BrandUserService;
import com.company.ROMES.entity.BrandUser;
import com.company.ROMES.entity.ConstructionResult;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;

import Error_code.ResultCode;

@Controller
public class BrandManagerController {
	@Autowired
	BrandUserService service;
	
	@RequestMapping(value = "/Android/BrandUserLogin",method = RequestMethod.POST,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String brandManager(@RequestParam(name = "id") String id, @RequestParam(name = "pw") String pw) {
		JSONObject object = new JSONObject();
		try {	
			if(id.length() == 0 || pw.length()== 0) {
				object.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
			}else {
				BrandUser brandUser = service.getBrandUserByIdPw(id, pw);
				boolean isAuth = true;
				
				if (brandUser != null) {
					if (brandUser.isEnable() == false)
						isAuth = false;
				}
				
				
				if(brandUser == null) {
					object.put("result", ResultCode.NO_RESULT);
				}else if (!isAuth) {
					object.put("result", ResultCode.AUTHORITY_ERROR); 
				}else {
					object.put("result", ResultCode.SUCCESS);
					object.put("name", brandUser.getName());
					object.put("id", brandUser.getId());
				}
			}
		}catch(NullPointerException e){
			e.printStackTrace();
			object.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
		}catch(Exception e){
			e.printStackTrace();
			object.put("result", ResultCode.UNKNOWN_ERROR);
		}
		return object.toString();
	}
//	@RequestMapping(value = "/Android/createDeviceName", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
//	@ResponseBody
//	public String createDeviceName(@RequestParam(name = "device") String device) {
//		JSONObject ret = service.saveDeviceName(device);
//		return ret.toJSONString();
//	}
//
//	@RequestMapping(value = "/Android/changeCommute", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
//	@ResponseBody
//	public String changeUserCommute(@RequestParam(name = "workercode") String workerCode,
//			@RequestParam(name = "commutecode") String commuteCode) {
//		JSONObject ret = new JSONObject();
//		ret.put("result", service.changeUserCommute(workerCode, commuteCode));
//		return ret.toJSONString();
//	}

	@RequestMapping(value = "/Android/BrandCheckId", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String checkId(@RequestParam(name = "id") String id) {
		JSONObject ret = service.checkId(id);
		return ret.toJSONString();
	}
	
	@RequestMapping(value = "/Android/BrandJoinMember", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String joinMember(@RequestParam(name = "id")String id,@RequestParam(name = "pw") String pw,@RequestParam(name = "name")String name,@RequestParam(name = "division")int divisionId,@RequestParam(name = "phone")String phoneNum,
			@RequestParam(name = "email")String email) {
		JSONObject ret = service.joinMember(id, pw, name, divisionId, phoneNum, email);
		return ret.toJSONString();
	}
	
	@RequestMapping(value = "/Android/BrandUserFindId", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String findUserId(@RequestParam(name = "name")String name,@RequestParam(name = "phone")String phoneNum,@RequestParam(name = "email")String email) {
		JSONObject ret = service.findUser(name, phoneNum, email);
		return ret.toJSONString();
	}
	
	@RequestMapping(value = "/Android/isBrandUser", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String isUser(@RequestParam(name = "name")String name,@RequestParam(name = "phone")String phoneNum,@RequestParam(name = "email")String email,@RequestParam(name="id")String id) {
		JSONObject ret = service.findUser(name, phoneNum, email, id);
		return ret.toJSONString();
	}
	
	@RequestMapping(value = "/Android/BrandresetPassword", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String isUser(@RequestParam(name="userid")int userid,@RequestParam(name="pw")String newPw) {
		JSONObject ret = service.resetPassword(userid, newPw);
		return ret.toJSONString();
	}
	@RequestMapping(value = "/Android/getBrandList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getDivisionList() {
		JSONObject ret = service.getBrandList();
		return ret.toJSONString();
	}
	
//	@RequestMapping(value = "/Android/acceptJoinRequest", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
//	@ResponseBody
//	public String acceptJoinRequest(@RequestParam(name="userid")int userid) {
//		JSONObject ret = service.acceptJoinRequest(userid);
//		return ret.toJSONString();
//	}
	
	@RequestMapping(value="/Android/history", method = RequestMethod.POST, produces= "text/plain;charset=UTF-8")
	@ResponseBody
	public String getHistorys(@RequestParam(name = "userid") int userId,
			@RequestParam(name = "year") int year, @RequestParam(name = "month") int month) {
		JSONObject o = service.getCompleteConstructionResult(userId, year, month);
		System.out.println(o);
		return o.toJSONString();
	}
	
	@RequestMapping(value="/Android/Approve", method = RequestMethod.POST, produces= "text/plain;charset=UTF-8")
	@ResponseBody
	public String approveConstruction(@RequestParam(name = "itemId") int id) {
		JSONObject o = service.approve(id);
		System.out.println(o);
		return o.toJSONString();
	}
	
	@RequestMapping(value="/Android/ApproveList", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String approveList(@RequestParam("userid") int userId,
			@RequestParam(name = "year") int year, @RequestParam(name = "month") int month) {
		JSONObject o = service.getApproveList(userId, year, month);
		System.out.println(o);
		return o.toJSONString();
	}
	
	
//	@RequestMapping(value = "/Android/changeAuthority", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
//	@ResponseBody
//	public String changeAuthority(@RequestParam(name="userid")int userid,@RequestParam(name="admin")boolean authid) {
//		JSONObject ret = service.changeAuthorities(userid, authid);
//		return ret.toJSONString();
//	}
//	@RequestMapping(value = "/Android/userList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
//	@ResponseBody
//	public String userList(@RequestParam(name="userid")int userid) {
//		JSONObject ret = service.getUserList(userid);
//		return ret.toJSONString();
//	}
//	@RequestMapping(value = "/Android/Dashboard", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
//	@ResponseBody
//	public String getDashBoard(@RequestParam(name="userid")int userid) {
//		JSONObject ret = service.getDashBoard(userid);
//		return ret.toJSONString();
//	}
}
