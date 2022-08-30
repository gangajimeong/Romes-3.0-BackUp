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

@Entity
@Table(name="blue_print")
public class BluePrint {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(targetEntity = ManufactureProduct.class,fetch = FetchType.LAZY)
	private ManufactureProduct product;
	
	@Column(name="image_path")
	private String imagePath = "No Info";

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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}	
	
	
}
