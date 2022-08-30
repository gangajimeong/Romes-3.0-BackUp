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
@Table(name = "error_product_history")
public class ErrorProductHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@ManyToOne(targetEntity = ErrorCode.class,fetch = FetchType.LAZY)
	@JoinColumn(name="error_id")
	private ErrorCode errorCode;
	
	@ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(targetEntity = Lot.class,fetch = FetchType.LAZY)
	@JoinColumn(name="lot_id")
	private Lot lot;
	
	@Column
	private int errorCount = 0;
	
	@Column
	private LocalDateTime time = LocalDateTime.now();
	
	@Column
	private String type;
	
	@ManyToOne(targetEntity = WorkOrderInfo.class, fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private WorkOrderInfo  order;
	
	@ManyToOne(targetEntity = SRHistory.class,fetch = FetchType.LAZY)
	@JoinColumn(name="srhistory_id")
	private SRHistory srhistory;
	
	public SRHistory getSrhistory() {
		return srhistory;
	}

	public void setSrhistory(SRHistory srhistory) {
		this.srhistory = srhistory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	public WorkOrderInfo getOrder() {
		return order;
	}

	public void setOrder(WorkOrderInfo order) {
		this.order = order;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
