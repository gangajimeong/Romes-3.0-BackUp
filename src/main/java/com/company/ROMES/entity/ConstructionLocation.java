package com.company.ROMES.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.Session;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="constuction_location")
public class ConstructionLocation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column
	private boolean isComplete = false;
	
	@ManyToOne(targetEntity = LatLng.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private LatLng location =null;

	
	@ManyToOne(targetEntity =  ConstructionPlan.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "construction_plan_id")
	private ConstructionPlan plan;
	
	@Column(columnDefinition = "boolean default false")
	private boolean isBrandConfirm = false;
	

//	@ManyToOne(targetEntity =  ManufactureProduct.class,fetch = FetchType.LAZY)
//	@JoinColumn(name = "manufacture_id")
//	private ManufactureProduct product;
//	
//	public boolean checkConstructionComplete() {
//		List<ConstructionLocation> locations = this.product.getLocation();
//		for(ConstructionLocation l : locations) {
//			if(!l.isComplete)
//				return false;
//		}
//		return true;
//	}
	
}
