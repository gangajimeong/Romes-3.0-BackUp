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
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "BrandUser")
public class BrandUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	
	@Column(name = "loginid")
	private String LoginId;
	
	@Column(name = "loginpw")
	private String LoginPw;
	
	@Column
	private String name;
	
//	@Column(columnDefinition = "boolean default true")
//	private boolean enabled = true;
	
	@ManyToOne(targetEntity = Brand.class,fetch = FetchType.LAZY)
	@JoinColumn
	private Brand brand;
	
	@Column
	private String phone;

	@Column
	private String email;
	
	@Column
	private boolean enable;
	
	public BrandUser() {
		super();
	}
	
}
