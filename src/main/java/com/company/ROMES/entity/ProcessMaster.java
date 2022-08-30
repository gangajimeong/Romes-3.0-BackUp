package com.company.ROMES.entity;

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
@Table
@Entity(name = "ProcessMaster")
public class ProcessMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column
	private String code;
	
	@Column
	private String processName;
	
	@ManyToOne(targetEntity = ProcessMaster.class, fetch = FetchType.LAZY)
	@JoinColumn
	private ProcessMaster beforeProcess;
	@ManyToOne(targetEntity = ProcessMaster.class, fetch = FetchType.LAZY)
	@JoinColumn
	private ProcessMaster afterProcess;
	
	@Column
	private String detailProcess;
}
