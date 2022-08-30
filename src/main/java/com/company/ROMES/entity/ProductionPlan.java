package com.company.ROMES.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="production_plan")
public class ProductionPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column
	private LocalDateTime predictStartTime = LocalDateTime.now();
	
	@Column
	private LocalDateTime predictEndTime = LocalDateTime.now();
	
	@Column
	private boolean isAuto = false;
	
	@Column(columnDefinition = "boolean default false")
	private boolean isCoating = false;
	
	@Column(columnDefinition = "boolean default false")
	private boolean isBack = false;
	
	@Column(columnDefinition = "boolean default false")
	private boolean isEmergency = false;
	
	@JoinColumn
	@ManyToOne(targetEntity = WorkingLine.class,fetch = FetchType.LAZY)
	private WorkingLine line;

	@JoinColumn
	@OneToOne(targetEntity = ManufactureProduct.class,fetch = FetchType.LAZY)
	private ManufactureProduct makeProduct;
	
//	@Column
//	private int planCount = 0;
	
	@Column
	private boolean isFinish = false;
	
	@Column
	private String remark="No Info";
	
	@OneToOne(targetEntity = WorkOrderInfo.class,fetch = FetchType.LAZY)
	@JoinColumn
	private WorkOrderInfo workOrder;
	
	@ManyToOne(targetEntity=User.class,fetch = FetchType.LAZY)
	@JoinColumn
	private User user;
	
	@Column
	private LocalDate releaseDate;
	
	@Column
	private String direction = "No Info";

	public int getPlanCount() {
		return this.makeProduct.getOrderCount();
	}
	
}
