package com.company.ROMES.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import com.company.ROMES.Controller.Android.ConsturctionController;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name="latlng")
public class LatLng {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	@Column(columnDefinition = "int default 0")
	private int postCode;
	
	@Column
	private String address;
	
	@Column
	private String address2;
	
	@Column
	private String detail;
	
	@Column
	private String title;
	
	@ManyToOne(targetEntity = Brand.class, fetch = FetchType.LAZY)
	@JoinColumn(name ="brand_id")
	private Brand brand;
	
	@Column
	private double lat=0;
	
	@Column
	private double lng=0;
	
	public String getTitle() {
		return this.title == null?"정보 없음":title;
	}
	@ManyToOne(targetEntity = ConstructionCompany.class,fetch = FetchType.LAZY)
	@JoinColumn
	private ConstructionCompany company;
		
}
