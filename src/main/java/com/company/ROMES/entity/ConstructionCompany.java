package com.company.ROMES.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class ConstructionCompany {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column
	private String companyName;
	
//	@JoinColumn
//	@OneToOne(targetEntity = User.class,fetch = FetchType.LAZY)
//	User user;
	
	@Column
	private String remark;

	@Column(name = "disabled")
	private boolean disabled = false;
	
	@Column
	private LocalDateTime registDate;
}
