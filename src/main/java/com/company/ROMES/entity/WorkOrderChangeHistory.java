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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@Entity
@Table(name = "work_order_change_history")
public class WorkOrderChangeHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	// 변경이력 등록인
	@ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;
	// 작업지시 정보
	@ManyToOne(fetch = FetchType.LAZY,targetEntity = WorkOrderInfo.class)
	@JoinColumn(name = "workinfo_id")
	private WorkOrderInfo workInfo;
	// 생성 시간
	@Column
	private LocalDateTime generateTime;
	// 완료 여부
	@Column(columnDefinition = "boolean default false")
	private boolean isFinished = false;
	// 비활성화 여부( 새로운 작업지시 변경이력 등록시 true)
	@Column(columnDefinition = "boolean default false")
	private boolean isDisable;
	// 재작 개수
	@Column
	private int makeCount = 0;
	// 비고
	@Column
	private String remark = "No Info";
	// 오류 정보
	@ManyToOne(targetEntity = ErrorCode.class,fetch = FetchType.LAZY)
	@JoinColumn
	private ErrorCode error;
	
	@Column
	private LocalDateTime startTime;
	
	@Column
	private LocalDateTime endTime;
	
	public int getPlanCount() {
		return this.workInfo.getPlanCount();
	}
}
