package com.company.ROMES.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="shipping_plan")
public class ShippingPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;

	
	@ManyToOne(targetEntity = ProductionPlan.class,fetch = FetchType.LAZY)
	@JoinColumn
	private ProductionPlan plan;
	
	@ManyToOne(targetEntity = LatLng.class,fetch = FetchType.LAZY)
	@JoinColumn
	private LatLng location;
	

	@OneToMany(targetEntity = Lot.class,fetch = FetchType.LAZY)
	private List<Lot> lots = new ArrayList<Lot>();
	
	@OneToMany(targetEntity = ShippingProduct.class,fetch = FetchType.LAZY)
	private List<ShippingProduct> shipProducts = new ArrayList<ShippingProduct>();
	
	@Column
	boolean isShipping = false;
	
	@Column
	LocalDate plannedReleaseDate;
	
	public void addShippingProduct(ShippingProduct pr) {
		this.shipProducts.add(pr);
	}
}
