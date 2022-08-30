package com.company.ROMES.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.firestore.v1beta1.StructuredQuery.Order;

@Entity
@Table(name = "order_history")
public class OrderHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column
	private String product = "정보 없음";
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate plannedArriveDate = LocalDate.now();

	@Column
	private LocalDate realArriveDate = null;

	@Column
	private LocalDate registerDate = null;
	@Column
	private boolean disabled = false;
	@Column(nullable = false)
	private boolean isMaterial = true;
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate orderDate;

	@Column // (nullable = false, unique = true)
	private String documentNumber = "정보 없음";
	@Column
	private String companyName = "정보 없음";

	@Column
	private int OrderCount = 0;
	@Column 
	private String Manager = "정보 없음";
	@Column
	private int price = 0;
	@Column
	private String remark = "No Info";
	@Column
	private String DocTitle = "문서";
	@JoinColumn(name = "company_id")
	@ManyToOne
	private Company company;

	@Column
	int product_id = 0;
	
	@Column
	int storedCount =0;

	@OneToMany(targetEntity = Lot.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Lot> lots = new ArrayList<Lot>();

	@Column(name = "complete")
	private boolean isComplete = false;

	

	

	public String getDocTitle() {
		return DocTitle;
	}

	public void setDocTitle(String docTitle) {
		DocTitle = docTitle;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getStoredCount() {
		return storedCount;
	}

	public void setStoredCount(int storedCount) {
		this.storedCount = storedCount;
	}

	public void setOrderCount(int orderCount) {
		OrderCount = orderCount;
	}

	public String getManager() {
		return Manager;
	}

	public void setManager(String manager) {
		Manager = manager;
	}

	public boolean isMaterial() {
		return isMaterial;
	}

	public void setMaterial(boolean isMaterial) {
		this.isMaterial = isMaterial;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName() {
		this.companyName = company.getCompanyName();
	}

	public int getOrderCount() {
		return OrderCount;
	}

	

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public LocalDate getRealArriveDate() {
		return realArriveDate;
	}

	public void setRealArriveDate(LocalDate realArriveDate) {
		this.realArriveDate = realArriveDate;
	}

	

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	/*
	 * public String getAllProduct() { List<Integer> list = new
	 * ArrayList<Integer>(orderProducts.keySet()); int cnt = 1; String Keys = ""; if
	 * (list.size() > 1) { for (int i = 0; i < list.size(); i++) { if (i < 3) { if
	 * (i < 2) Keys = Integer.toString(list.get(i)) + " / "; else Keys =
	 * Integer.toString(list.get(i)); } else { cnt++; } Keys += Keys + "외" + cnt +
	 * "건"; } } else { Keys = Integer.toString(list.get(0)); } return Keys; }
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getPlannedArriveDate() {
		return plannedArriveDate;
	}

	public void setPlannedArriveDate(LocalDate plannedArriveDate) {
		this.plannedArriveDate = plannedArriveDate;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void addLot(Lot lot) {
		this.lots.add(lot);
	}

	public void removeLot(Lot lot) {
		this.lots.remove(lot);
	}

	public List<Lot> getLots() {
		return lots;
	}

	public void setLots(List<Lot> lots) {
		this.lots = lots;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
