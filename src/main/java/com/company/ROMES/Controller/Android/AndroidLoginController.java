
package com.company.ROMES.Controller.Android;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.UserService;
import com.company.ROMES.entity.Authorities;
import com.company.ROMES.entity.User;

import Error_code.ResultCode;

@Controller
public class AndroidLoginController {
	@Autowired
	UserService service;

	@RequestMapping(value = "/Android/Login", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String requestLogin(@RequestParam(name = "id") String id, @RequestParam(name = "pw") String pw) {
		JSONObject ret = new JSONObject();

		try {
			if (id.length() == 0 || pw.length() == 0) {
				ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
			} else {
				User user = service.getUserByIdPw(id, pw);
				boolean isAuth = true;
				if (user != null) {

					if (user.getAuthority() == null) {
						isAuth = false;
					} else if (user.getAuthority().getAuthority().equals("ROLE_NOTUSER")) {
						isAuth = false;
					}
				}

				if (user == null) {
					ret.put("result", ResultCode.NO_RESULT);
				} else if (!isAuth) {
					ret.put("result", ResultCode.AUTHORITY_ERROR);
				} else {
					ret.put("result", ResultCode.SUCCESS);
					ret.put("name", user.getName());
					ret.put("auth", user.getAuthority().getAuthority());
					ret.put("position", user.getPosition());
					ret.put("id", user.getId());
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Request Parameter Error");
			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		}
		return ret.toJSONString();
	}

	@RequestMapping(value = "/Android/createDeviceName", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String createDeviceName(@RequestParam(name = "device") String device) {
		JSONObject ret = service.saveDeviceName(device);
		return ret.toJSONString();
	}

	@RequestMapping(value = "/Android/changeCommute", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String changeUserCommute(@RequestParam(name = "workercode") String workerCode,
			@RequestParam(name = "commutecode") String commuteCode) {
		JSONObject ret = new JSONObject();
		ret.put("result", service.changeUserCommute(workerCode, commuteCode));
		return ret.toJSONString();
	}

	@RequestMapping(value = "/Android/checkId", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String checkId(@RequestParam(name = "id") String id) {
		JSONObject ret = service.checkId(id);
		return ret.toJSONString();
	}
	
	@RequestMapping(value = "/Android/joinMember", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String joinMember(@RequestParam(name = "id")String id,@RequestParam(name = "pw") String pw,@RequestParam(name = "name")String name,@RequestParam(name = "position")String position,@RequestParam(name = "division")int divisionId,@RequestParam(name = "phone")String phoneNum,
			@RequestParam(name = "email")String email) {
		JSONObject ret = service.joinMember(id, pw, name, position, divisionId, phoneNum, email);
		return ret.toJSONString();
	}
	
	@RequestMapping(value = "/Android/findUserId", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String findUserId(@RequestParam(name = "name")String name,@RequestParam(name = "phone")String phoneNum,@RequestParam(name = "email")String email) {
		JSONObject ret = service.findUser(name, phoneNum, email);
		return ret.toJSONString();
	}
	
	@RequestMapping(value = "/Android/isUser", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String isUser(@RequestParam(name = "name")String name,@RequestParam(name = "phone")String phoneNum,@RequestParam(name = "email")String email,@RequestParam(name="id")String id) {
		JSONObject ret = service.findUser(name, phoneNum, email, id);
		return ret.toJSONString();
	}
	
	@RequestMapping(value = "/Android/resetPassword", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String isUser(@RequestParam(name="userid")int userid,@RequestParam(name="pw")String newPw) {
		JSONObject ret = service.resetPassword(userid, newPw);
		return ret.toJSONString();
	}
	@RequestMapping(value = "/Android/getDivisionList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getDivisionList() {
		JSONObject ret = service.getDivisionList();
		return ret.toJSONString();
	}
	@RequestMapping(value = "/Android/acceptJoinRequest", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String acceptJoinRequest(@RequestParam(name="userid")int userid) {
		JSONObject ret = service.acceptJoinRequest(userid);
		return ret.toJSONString();
	}
	@RequestMapping(value = "/Android/changeAuthority", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String changeAuthority(@RequestParam(name="userid")int userid,@RequestParam(name="admin")boolean authid) {
		JSONObject ret = service.changeAuthorities(userid, authid);
		return ret.toJSONString();
	}
	@RequestMapping(value = "/Android/userList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String userList(@RequestParam(name="userid")int userid) {
		JSONObject ret = service.getUserList(userid);
		return ret.toJSONString();
	}
	@RequestMapping(value = "/Android/Dashboard", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getDashBoard(@RequestParam(name="userid")int userid) {
		JSONObject ret = service.getDashBoard(userid);
		return ret.toJSONString();
	}
	
}