package com.company.ROMES.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table
@Entity(name = "product")
public class Product  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column
	private String productCode;
	
	@Column
	private String name;
	
	@Column
	private int price;
	
	@Column
	private String size;
	
	@Column
	private boolean disabled = false;
	
	@Column
	private String remark;
	
	 
}
