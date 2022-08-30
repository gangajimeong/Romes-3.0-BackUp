package com.company.ROMES.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table
public class DocumentForApproaval {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
//	// 결제 정보 테이블 맵핑
//	@ManyToOne(targetEntity = ApprovalInfo.class, fetch = FetchType.LAZY)
//	@JoinColumn(name = "approvalInfo_id")
//	private ApprovalInfo approvalInfo;

	// 작성자

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "writer")
	private User writer;

	// 결제 상태
	@ElementCollection
	@CollectionTable(name = "approval_state", joinColumns = {
			@JoinColumn(name = "document_for_approve_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "user_id")
	@Column(name = "is_approve")
	private Map<User, Boolean> approveState = new HashMap<User, Boolean>();

	// 진행중 완료 시 false, 미완료시 true
	@Column(columnDefinition = "boolean default true")
	private boolean isContinue = true;
	
	@Column(columnDefinition = "boolean default false")
	private boolean isAdminConfrim = false;
	// 소비자 승인
	@Column(columnDefinition = "boolean default false")
	private boolean isCustomConfirm = false;

	@Column
	private String remark;

	
	// 작성 날짜
	@Column
	private LocalDateTime writtenDate = LocalDateTime.now();
	// 수정 날짜 시간
	@Column
	private LocalDateTime DocUpdateDate = null;
	// 승인 날짜
	@Column
	private LocalDateTime ApprovedDate = null;

	// 결제 서류 제목
	@Column
	private String DocumentOfTitle;

	@OneToMany(targetEntity = DesignMaster.class, fetch = FetchType.LAZY)
	List<DesignMaster> designs = new ArrayList<DesignMaster>();
	// 삭제 활성화/비활성화
	@Column
	private boolean isDisabeld = false;

	@ManyToOne(targetEntity = ReceivedOrderInfo.class, fetch = FetchType.LAZY)
	@JoinColumn
	private ReceivedOrderInfo info;

	@Column(columnDefinition = "int default 0")
	private int product_id = 0;

	public void addCooperater(User user, boolean state) {
		if (!this.approveState.containsKey(user)) {
			this.approveState.put(user, true);
		}
	}

	public void removeCooperater(User user) {
		if (this.approveState.containsKey(user)) {
			this.approveState.remove(user);
		}
	}

	public void addDesign(DesignMaster design) {
		if (!this.designs.contains(design)) {
			this.designs.add(design);
		}
	}
	public void removerDesign(DesignMaster design) {
		if (this.designs.contains(design)) {
			this.designs.remove(design);
		}
	}

}
