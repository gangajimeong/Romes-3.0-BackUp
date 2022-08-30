package com.company.ROMES.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="bom_table")
public class E_Bom {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@JoinColumn(name="menufacture_product_id")
	@OneToOne(targetEntity = ManufactureProduct.class,fetch = FetchType.LAZY)
	private ManufactureProduct product;
	
	@OneToMany(targetEntity = BluePrint.class,fetch = FetchType.LAZY)
	private List<BluePrint> blueprints = new ArrayList<BluePrint>();
	
	@OneToMany(targetEntity = BluePrint.class,fetch = FetchType.LAZY)
	private List<ProcessUnit> process = new ArrayList<ProcessUnit>();
	
	public void addBluePrint(BluePrint bluePrint) {
		this.blueprints.add(bluePrint);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ManufactureProduct getProduct() {
		return product;
	}

	public void setProduct(ManufactureProduct product) {
		this.product = product;
	}

	public List<BluePrint> getBlueprints() {
		return blueprints;
	}

	public void setBlueprints(List<BluePrint> blueprints) {
		this.blueprints = blueprints;
	}

	public List<ProcessUnit> getProcess() {
		return process;
	}

	public void setProcess(List<ProcessUnit> process) {
		this.process = process;
	}
	
	
}
