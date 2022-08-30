package com.company.ROMES.Services.StandardInfo;

import java.nio.channels.NonReadableChannelException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.entity.ApprovalInfo;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;

@Service
public class ApprovalInfoService implements com.company.ROMES.interfaces.service.StandardInfo.ApprovalInfoService {
	@Autowired
	SessionFactory factory;

	@Override
	public JSONArray aboutApprovalInfoByFalse() {
		Session session = null;
		List<ApprovalInfo> lists = new ArrayList<ApprovalInfo>();
		JSONArray rets = new JSONArray();
		try {
			session = factory.openSession();
			lists = session.createQuery(
					"Select this_ from ApprovalInfo this_ where this_.isDisabeld= false order by this_.createdDate desc")
					.getResultList();
			for (ApprovalInfo ai : lists) {
				JSONObject ret = new JSONObject();
				JSONArray users = new JSONArray();

				List<User> userLists = new ArrayList<User>();
				ret.put("id", ai.getId());
				ret.put("title", ai.getApprovalInfoName());
				userLists = ai.getApprovalPhase();
				for (User user : userLists) {
					users.add(user.getName());
				}
				ret.put("users", users);
				ret.put("createDate", ai.getCreatedDate());
				ret.put("updateDate", ai.getUpdateDate() != LocalDateTime.MIN ? ai.getUpdateDate() : "변경 이력 없음");
				rets.add(ret);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return rets;
	}

	@Override
	public JSONObject approvalInfoOrder(int id) {
		Session session = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();

			JSONArray users = new JSONArray();
			ApprovalInfo ai = new ApprovalInfo();
			ai = session.find(ApprovalInfo.class, id);
			List<User> userLists = new ArrayList<User>();

			ret.put("title", ai.getApprovalInfoName());

			userLists = ai.getApprovalPhase();
			for (User user : userLists) {
				JSONObject userInfo = new JSONObject();
				userInfo.put("user", user.getName());
				userInfo.put("division", user.getDivision().getDivision());
				userInfo.put("position", user.getPosition());
				users.add(userInfo);
			}
			ret.put("users", users);
			ret.put("createDate", ai.getCreatedDate());
			ret.put("updateDate", ai.getUpdateDate() != LocalDateTime.MIN ? ai.getUpdateDate() : "변경 이력 없음");

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
	public JSONArray divisions() {
		Session session = null;
		List<Division> lists = new ArrayList<Division>();
		JSONArray rets = new JSONArray();
		try {
			session = factory.openSession();
			lists = session.createQuery("Select this_ from Division this_ where this_.disabled=false order by this_.id").getResultList();
			for (Division d : lists) {
				rets.add(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray filteredPostions(int division_id) {
		Session session = null;
		JSONArray rets = new JSONArray();

		try {
			JSONObject id = new JSONObject();
			JSONArray positions = new JSONArray();
			session = factory.openSession();
			List<String> lists = new ArrayList<String>();
			Division div = session.find(Division.class, division_id);
			
			lists = session.createQuery(
					"Select this_.position from User this_  where this_.division.division ='" + div.getDivision() + "' and this_.enabled = true and this_.isLock=false order by this_.id")
					.getResultList();
			id.put("division_id", division_id);
			rets.add(id);
			for (String position : lists) {
				if(!positions.contains(position))
				positions.add(position);
			}
			rets.add(positions);

			// users = session.createQuery("Select this_ from User this_ where
			// this_.division.id = "+division_id+" and this_.enabled = true and
			// this_.isLock=fasle order by this_.id ").getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return rets;
	}

	@Override
	public JSONObject createApprovalInfo(ApprovalInfo approvalInfo) {
		Session session = null;
		Transaction transaction = null;
		try {
			// session = factory.openSession();
			// transaction = session.beginTransaction();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public JSONObject updateApprovalInfo(ApprovalInfo approvalInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject deleteApprovalInfo(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray filterdUsers(int division_id, String position) {
		Session session = null;
		JSONArray rets = new JSONArray();
		try {
			List<User> users = new ArrayList<User>();
			session = factory.openSession();
			users = session.createQuery("Select this_ from User this_ where this_.division.id = " + division_id
					+ " and this_.position='"+position+"' and this_.enabled = true and this_.isLock=false order by this_.id ").getResultList();
			System.out.println(users.toString());
			for(User user: users) {
				JSONObject ret = new JSONObject();
				System.out.println(user.getId()+" "+user.getName());
				ret.put("id", user.getId());
				ret.put("name", user.getName());
				rets.add(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return rets;
	}

}
