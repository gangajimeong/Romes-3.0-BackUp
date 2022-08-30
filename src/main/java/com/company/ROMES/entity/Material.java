package com.company.ROMES.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "material")
public class Material {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(nullable = false)
	private String name="No Info";
	
	@Column
	private String url;
	
	@Column(nullable = false)
	private String standard = "No Info";
	
	@Column
	private boolean isDisable = false;

	@Column(nullable = false,unique = true)
	private String productCode;
	
	@Column
	private LocalDateTime createDate;
	
	@Column
	private LocalDateTime disabledDate;
	
	@Column
	private LocalDateTime lastUpdateDate;
	
	@ManyToOne(targetEntity = CommonCode.class)
	@JoinColumn(name = "typecode_id")
	private CommonCode productType;
	//@Column
	//private String productGroup;
	@ManyToOne(targetEntity = CommonCode.class)
	@JoinColumn(name = "groupcode_id")
	private CommonCode productGroup;
	//@Column
	//private String productFeature;
	@ManyToOne(targetEntity = CommonCode.class)
	@JoinColumn(name = "featurecode_id")
	private CommonCode productFeature;
	
	@Column(nullable = false)
	private int defaultSalePrice = 0;
	
	@ManyToOne(targetEntity = LocationMaster.class)
	@JoinColumn(name = "default_location_id")
	private LocationMaster defaultLocation;
	
	@ManyToOne(targetEntity = CommonCode.class)
	@JoinColumn
	private CommonCode unitType;
	
	@ManyToOne(targetEntity = CommonCode.class)
	@JoinColumn
	private CommonCode quantitiesType;
	
	@Column(nullable = false)
	private int unitCount = 1;
	
	@Column(nullable = false)
	private int properQuentity = 0;
	
	@Column(nullable = false)
	private int stockCount = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public boolean isDisable() {
		return isDisable;
	}

	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getDisabledDate() {
		return disabledDate;
	}

	public void setDisabledDate(LocalDateTime disabledDate) {
		this.disabledDate = disabledDate;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public CommonCode getProductType() {
		return productType;
	}

	public void setProductType(CommonCode productType) {
		this.productType = productType;
	}

	public CommonCode getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(CommonCode productGroup) {
		this.productGroup = productGroup;
	}

	public CommonCode getProductFeature() {
		return productFeature;
	}

	public void setProductFeature(CommonCode productFeature) {
		this.productFeature = productFeature;
	}

	public int getDefaultSalePrice() {
		return defaultSalePrice;
	}

	public void setDefaultSalePrice(int defaultSalePrice) {
		this.defaultSalePrice = defaultSalePrice;
	}

	public LocationMaster getDefaultLocation() {
		return defaultLocation;
	}

	public void setDefaultLocation(LocationMaster defaultLocation) {
		this.defaultLocation = defaultLocation;
	}

	public CommonCode getUnitType() {
		return unitType;
	}

	public void setUnitType(CommonCode unitType) {
		this.unitType = unitType;
	}

	public CommonCode getQuantitiesType() {
		return quantitiesType;
	}

	public void setQuantitiesType(CommonCode quantitiesType) {
		this.quantitiesType = quantitiesType;
	}

	public int getUnitCount() {
		return unitCount==0?1:unitCount;
	}

	public void setUnitCount(int unitCount) {
		this.unitCount = unitCount;
	}

	public int getProperQuentity() {
		return properQuentity;
	}

	public void setProperQuentity(int properQuentity) {
		this.properQuentity = properQuentity;
	}

	public int getStockCount() {
		return stockCount;
	}

	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
}
