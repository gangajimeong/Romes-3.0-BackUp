package com.company.ROMES.interfaces.service.SystemM;

import java.util.List;

import org.hibernate.Session;

public interface AuthConrollInterface {
	public boolean removeUser(int id);
	public boolean updateAuth(int Id, String role);

	}
