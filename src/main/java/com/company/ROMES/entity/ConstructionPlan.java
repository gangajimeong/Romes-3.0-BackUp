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

import org.hibernate.Session;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="construction_plan")
public class ConstructionPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne(targetEntity = User.class, fetch= FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	// 시공사
	@JoinColumn
	@ManyToOne(targetEntity=ConstructionCompany.class,fetch=FetchType.LAZY)
	private ConstructionCompany company;
	// 작업지시 정보
	@ManyToOne(targetEntity=WorkOrderInfo.class,fetch=FetchType.LAZY)
	@JoinColumn
	private WorkOrderInfo workOrderInfo;
	// 등록 날짜
	@Column
	private LocalDateTime planDate;
	// 시공 위치 
	@OneToMany(targetEntity = ConstructionLocation.class,fetch = FetchType.LAZY)
	private List<ConstructionLocation> locations = new ArrayList<ConstructionLocation>();
    // 완료 여부
	@Column
	private boolean isComplete = false;
	// 비활성화 여부
	@Column(columnDefinition = "boolean default false")
	private boolean isDisabled;
	//관리자 확인
	@Column(columnDefinition = "boolean default false")
	private boolean isConfirm;
	
	public boolean checkComplete() {
		for(ConstructionLocation l : locations) {
			if(!l.isComplete()) {
				return false;
			}
		}
		return true;
	}
	
	public void addLocation(ConstructionLocation location) {
		if(!this.locations.contains(location))
			this.locations.add(location);
	}
	
	public void removeLocation(ConstructionLocation location) {
		if(this.locations.contains(location))
			this.locations.remove(location);
	}
	
	public List<DesignMaster> getDesigns(Session session){
		return this.workOrderInfo.getSampleDesigns();
	}

	public boolean isCompare() {
		for(ConstructionLocation l : locations) {
			if(!l.isComplete())
				return false;
		}
		return true;
	}
}
