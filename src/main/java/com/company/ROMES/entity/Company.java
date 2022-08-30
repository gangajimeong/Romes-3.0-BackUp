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

import org.json.simple.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "company")
@Getter
@Setter
public class Company {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	@Column
	private String companyName;
	@Column
	private String CEO_Name;
	@Column
	private String businessNumber;

	@Column
	private String postalCode;
	
	@Column
	private String realAddress;
	@Column
	private String fax;
	@Column
	private String phone;
	@Column
	private String email;
	@Column
	private String remarks;
//	@Column(name = "image")
//	private byte[] image;
	@Column(name = "disabled")
	private boolean disabled = false;
	
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	private List<Company_manager> managers = new ArrayList<Company_manager>();

//	@OneToMany(mappedBy = "company",fetch=FetchType.LAZY,targetEntity = Brand.class)
//	private List<Brand> brands = new ArrayList<>();
	
//	public void addBrand(Brand brand) {
//		this.brands.add(brand);
//		brand.setCompany(this);
//	}
	public void addManager(Company_manager manager) {
		this.managers.add(manager);
	}
//	public void removeBrand(Brand brand) {
//		this.brands.remove(brand);
//	}
}
