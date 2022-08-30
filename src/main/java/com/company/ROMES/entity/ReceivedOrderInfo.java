package com.company.ROMES.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Session;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="recevied_order_info")
public class ReceivedOrderInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column
	private String title;
	// 수주일
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column
	private LocalDateTime receivedOrderDate;
	//작성일
	@Column
	private LocalDateTime writtenDate;
	
	@JoinColumn
	@ManyToOne(targetEntity = Brand.class,fetch = FetchType.LAZY)
	private Brand brand; 

	@JoinColumn
	@ManyToOne(targetEntity = Company.class,fetch = FetchType.LAZY)
	private Company orderCompany;
	
	@Column
	private LocalDateTime updateDate;
	
	@ManyToOne(targetEntity = User.class, fetch= FetchType.LAZY)
	@JoinColumn(name="lastUpdateUser_id")
	private User lastUpdateUser;
	
	// 작업 지시서들
	@OneToMany(targetEntity = WorkOrderInfo.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<WorkOrderInfo> workOrders = new ArrayList<>();
	// 수주 생성자
	@ManyToOne(targetEntity = User.class, fetch= FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	// 생산 완료
	@Column
	private boolean isProduction = false;
	// 최종 완료
	@Column
	private boolean isComplete = false;
	//----------------------------------------------
	@Column(columnDefinition = "boolean default false")
	boolean isDisabled= false;
	
	public void addWorkInfo(WorkOrderInfo info) {
		this.workOrders.add(info);
	}
	
	public boolean isCompare() {
		for(WorkOrderInfo info : this.workOrders) {
			if(!info.isSalesTeamCheck())
				return false;
		}
		return true;
	}

}
