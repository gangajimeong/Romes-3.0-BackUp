package com.company.ROMES.entity;

import java.time.Duration;

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
@Table(name ="workingline")
public class WorkingLine {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column
	private String line="No Info";
	@Column 
	private String remarks="No Info";
	@Column
	private boolean isDisabled = false;
	@OneToOne(targetEntity = LocationMaster.class, fetch = FetchType.EAGER)
	@JoinColumn
	LocationMaster location;

	@Column
	private int Index;
	
	@Column
	private int workload;
	
	@Column
	private int mRequirement;
	
	@Column
	private String type;
			
}
