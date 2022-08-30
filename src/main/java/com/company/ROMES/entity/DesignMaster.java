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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="design_master")
public class DesignMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(targetEntity = ManufactureProduct.class,fetch = FetchType.LAZY)
	@JoinColumn
	private ManufactureProduct product;
	
	@Column
	private LocalDateTime registrationDate;
	
	@Column
	private LocalDateTime updateDate;
	
	@Column
	private LocalDateTime disableDate;
	
	@ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
	@JoinColumn
	private User registUser;
	
	@Column
	private String url;

	@Column
	private boolean isConfirm = false;
	
	@Column
	private boolean isDisable = false;
}
