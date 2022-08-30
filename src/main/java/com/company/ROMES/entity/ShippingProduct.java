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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Session;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="shipping_product")
public class ShippingProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
//	@Column
//	private int productKey;
	@ManyToOne(targetEntity = ManufactureProduct.class,fetch = FetchType.LAZY)
	@JoinColumn
	private ManufactureProduct product;
	@Column
	private int planCount=0;
	
	@Column
	private int shippingCount =0;
	
	@ManyToOne(targetEntity = ShippingPlan.class,fetch = FetchType.LAZY)
	@JoinColumn
	private ShippingPlan shippingPlan;
	
	public void checkIsShippingComplete(Session session) {
//		ReceivedOrderInfo info = this.shippingPlan.getPlan().getOrderInfo();
//		int shippingCount = info.getOrderProductCount(product);
//		List<Integer> products = session.createQuery("Select this_.shippingCount from ShippingProduct this_ where this_.product.id="+product.getId()+" And this_.shippingPlan.info.id="+info.getId()).getResultList();
//		int totalShippingCount = 0;
//		for(int count : products) {
//			totalShippingCount = totalShippingCount+ count;
//		}
//		if(shippingCount == totalShippingCount) {
//			info.setShipment(true);
//			session.update(info);
//		}
	}
	
	
	public boolean isShippingComplete() {
		return this.shippingCount >= this.planCount;
	}
	public boolean isShippingStart() {
		return this.shippingCount != 0;
	}
}
