package com.company.ROMES.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import javax.persistence.CascadeType;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*
 * 
 * workNumber 생성 식을 적어두고 변경시 관리자에게 문의할 것을 적음.
 * 
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "location_table")
public class LocationMaster implements Comparable<LocationMaster>, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(nullable=false)
	private String name="No Info";

	@ManyToOne(targetEntity = CommonCode.class,fetch = FetchType.EAGER)
	@JoinColumn(name = "locationtypecode")
	private CommonCode locationTypeCode;
	@Column
	private boolean disabeld = false;

	@Column
	private String path="No Info";
	
	@Column
	private int level;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "item_stock_map", joinColumns = {
			@JoinColumn(name = "location_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "product_id")
	@Column(name = "counts")
	private Map<Integer, Integer> productStocksMap = new HashMap<Integer, Integer>();
	

	public Map<Integer, Integer> getProductStocksMap() {
		return productStocksMap;
	}

	public void setProductStocksMap(Map<Integer, Integer> productStocksMap) {
		this.productStocksMap = productStocksMap;
	}
	public void setStockCount(int productId,int count) {
		this.productStocksMap.put(productId,count);
	}
	@OneToMany(targetEntity = Lot.class,fetch = FetchType.LAZY)
	private List<Lot> lots = new ArrayList<Lot>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_location_id")
	private LocationMaster parentLocation= null;
    
	
	@OneToMany(mappedBy = "parentLocation", targetEntity = LocationMaster.class, orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<LocationMaster> subLocations = new HashSet<LocationMaster>();
	
	@Column
	private String position = makeName();
	
	public void addSubLocation(LocationMaster locationMaster) {
		subLocations.add(locationMaster);
	}
	
	public List<LocationMaster> getSubLocationsAsList(){
		List<LocationMaster> ret = new ArrayList<LocationMaster>(subLocations);
		Collections.sort(ret,new Comparator<LocationMaster>() {

			public int compare(LocationMaster o1, LocationMaster o2) {
				int i = 0;
				if(o1.getId() > o2.getId())
					i = 1;
				else
					i = -1;
				return i;
			}
			
		});
		return ret;
	}

	public int getProductCount(int id) {
		if(this.productStocksMap.containsKey(id)) {
			return this.productStocksMap.get(id);
		}else {
			return 0;
		}
	}
	public void addLot(Lot lot) {
		this.lots.add(lot);
		if(this.productStocksMap.containsKey(lot.getProductId())) {
			this.productStocksMap.put(lot.getProductId(), this.productStocksMap.get(lot.getProductId())+lot.getCount());
		}else {
			this.productStocksMap.put(lot.getProductId(),lot.getCount());
		}
	}
	public void removeLot(Lot lot) {
		this.lots.remove(lot);
		if(this.productStocksMap.containsKey(lot.getProductId())) {
			this.productStocksMap.put(lot.getProductId(), this.productStocksMap.get(lot.getProductId())-lot.getCount());
			if(this.productStocksMap.get(lot.getProductId()) < 1) {
				this.productStocksMap.remove(lot.getProductId());
			}
		}
	}
	public String getLcoationType() {
		if (getLocationTypeCode() == null)
			return "";
		else
			return getLocationTypeCode().getValue();
	}
	// 뜰카페-2층-회의실2번     
	public String makeName() {
		ArrayList<String> names = new ArrayList<String>();
		LocationMaster location = this;
		while (true) {
			names.add(location.getName());
			if (location.getParentLocation() == null) {// 최상위 로케이션 정보 가져오기
				break;
			} else {
				location = location.getParentLocation();
			}
		}
		Collections.reverse(names);
		StringBuffer returnName = new StringBuffer();
		for(String e : names) {
			returnName.append("-" + e);
		}
		return returnName.substring(1, returnName.length());
	}

	public String makeSubLocString() {
		ArrayList<String> list = new ArrayList<String>();
		for(LocationMaster e : getSubLocations()) {
			list.add(e.getName());
		}
		return list.toString();
	}

	public int compareTo(LocationMaster l) {
		if (getName() == null || l.getName() == null) {
			return 0;
		}
		return getName().compareTo(l.getName());
	}
}
