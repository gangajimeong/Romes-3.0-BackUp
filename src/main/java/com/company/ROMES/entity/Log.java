package com.company.ROMES.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="log")
public class Log {
	final static String SELECT = "조회";
	final static String UPDATE="수정";
	final static String CREATE="등록";
	final static String DELETE="삭제";
//	final static String BUSSINESS="거래처 관리";
//	final static String LOCATION = "위치정보 관리";
//	final static String CONSTRUCTION = "시공사정보 관리";
//	final static String METERIAL = "원부자재정보 관리";
//	final static String PROCCESS = "공정 관리";
//	final static String MACHINE="생산설비 관리";
//	final static String ERROR = "불량코드 관리";
//	
//	final static String RECEIVED_ORDER = "수주관리";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(targetEntity =User.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column
	private String category = "Manager";
	
	@Column(nullable = false)
	private String action;
	
	@Column(nullable = false)
	private LocalDateTime time = LocalDateTime.now();
	
	@Column
	private int connectedId=0;
	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getConnectedId() {
		return connectedId;
	}

	public void setConnectedId(int connectedId) {
		this.connectedId = connectedId;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
