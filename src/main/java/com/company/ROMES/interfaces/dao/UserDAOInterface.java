package com.company.ROMES.interfaces.dao;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.Authorities;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;

public interface UserDAOInterface {
	public List<User> selectAll(Session session);
	public List<User>SelectAllByFalse(Session session);
	public boolean createUser(Session session, User user, Authorities auth, Division division);
	public boolean updateUser(Session session, User user);

	public boolean deleteUser(Session session, User user);
	public String selectByLoginId(Session session, String LoginId);
	public User selectByPk(Session session, int pk);
	public User selectByWorkerId(Session session,String workerId);
	public User selectByIdPw(Session session, String id, String pw);
	public User selectByIdForUser(Session session, String id);
	public User findAdmin(Session session, String id);
}
