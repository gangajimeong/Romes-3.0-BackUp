package com.company.ROMES.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@Entity
@Table(name = "work_order_info")
public class WorkOrderInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	// 품목 
	@Column
	private String product;
	// 비고 
	@Column
	private String remark;
	// 수주 정보
	@ManyToOne(targetEntity = ReceivedOrderInfo.class,fetch = FetchType.LAZY)
	@JoinColumn
	private ReceivedOrderInfo orderInfo;
	// 시작시간
	@Column
	private LocalDateTime startTime;
	// 완료시간
	@Column
	private LocalDateTime endTime;
	// 완료 여부(재작부 확인시 true)
	@Column
	private boolean isFinished = false;
	// 실패여부(WorkOrderChangeHistory 등록시 true)
	@Column(columnDefinition = "boolean default false")
	private boolean isFailed = false;
	// 디자이너
	@ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
	@JoinColumn
	private User designer;
	// 출력기(출력부)
	@ManyToOne(targetEntity = WorkingLine.class,fetch = FetchType.LAZY)
	@JoinColumn
	private WorkingLine printer;
	// 샘플 시안
	@OneToMany(targetEntity = DesignMaster.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<DesignMaster> sampleDesigns = new ArrayList<DesignMaster>();
	// 디자인부 확인
	@Column(columnDefinition = "boolean default false")
	private boolean isDesignTeamCheck = false;
	// 출력부 확인
	@Column(columnDefinition = "boolean default false")
	private boolean isPrintingTeamCheck = false;
	// 재작부 확인
	@Column(columnDefinition = "boolean default false")
	private boolean isMakingTeamCheck = false;
	// 영업부 확인
	@Column(columnDefinition = "boolean default false")
	private boolean isSalesTeamCheck = false;
	// 작업 지시 개수
	@Column(columnDefinition = "Integer default 0")
	private int planCount = 0;
	// 완료 개수
	@Column
	private int makeCount = 0;
	// 코팅 여부
	@Column(columnDefinition = "boolean default false")
	private boolean isCoating = false;
	// 배면 출력 여부
	@Column(columnDefinition = "boolean default false")
	private boolean isBack = false;
	// 긴급 여부
	@Column(columnDefinition = "boolean default false")
	private boolean isEmergency = false;
	// 지점 정보
	@ManyToOne(targetEntity = LatLng.class,fetch = FetchType.LAZY)
	@JoinColumn
	private LatLng branch;
	// 시공 필요 여부 
	@Column(columnDefinition = "boolean default false")
	private boolean isConstruction = false;
	// 규격 
	@Column
	private String size;
	// 가공방법 
	@Column
	private String manufacturing;
	//처리자
	@JoinColumn
	@OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User manager;
	
	
	@JoinColumn
	@OneToOne(targetEntity = Design.class, fetch = FetchType.LAZY)
	private Design design;
	
	public boolean isCompare() {
		return this.planCount <= this.makeCount;
	}
}
