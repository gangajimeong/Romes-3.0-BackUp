package com.company.ROMES.entity;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="process_unit")
public class ProcessUnit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column
	private String processName = "No info";
	
	@Column
	private int processLevel = 0;
	
	@Column
	private Duration processTime = Duration.ZERO;
	
	@ManyToOne(targetEntity = CommonCode.class,fetch = FetchType.LAZY)
	@JoinColumn
	private CommonCode processType;
	
	@ManyToMany(targetEntity = M_Bom.class,fetch = FetchType.LAZY)
	private List<M_Bom> requireMeterialList = new ArrayList<M_Bom>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public int getProcessLevel() {
		return processLevel;
	}

	public void setProcessLevel(int processLevel) {
		this.processLevel = processLevel;
	}

	public Duration getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Duration processTime) {
		this.processTime = processTime;
	}

	public CommonCode getProcessType() {
		return processType;
	}

	public void setProcessType(CommonCode processType) {
		this.processType = processType;
	}

}
