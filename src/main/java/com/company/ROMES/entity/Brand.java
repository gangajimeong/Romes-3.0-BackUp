package com.company.ROMES.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "brand_table")
@Getter
@Setter
@ToString
public class Brand {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column
	private String companyName;
	@Column
	private String CEO_Name;

	/*
	 * @Column private String postalCode;
	 * 
	 * @Column private String realAddress;
	 */

	@Column
	private String phone;

	@Column
	private String remarks;
//	@Column(name = "image")
//	private byte[] image;
	@Column(name = "disabled")
	private boolean disabled = false;
	
	@ManyToOne(targetEntity = Company.class,fetch = FetchType.LAZY)
	@JoinColumn
	private Company company;
	
	@OneToMany(targetEntity = LatLng.class,fetch = FetchType.LAZY)
	private List<LatLng> branch;
	
	public void addBranch(LatLng lg) {
		this.branch.add(lg);
		lg.setBrand(this);
	}
}
