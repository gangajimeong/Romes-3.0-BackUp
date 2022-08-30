package com.company.ROMES.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="srhistory")
public class SRHistory {
	public final static int STORE = 0;
	public final static int RELEASE = 1;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column
	private LocalDateTime time;
	
	@Column
	private int type = -1;
	
	@JoinColumn
	@ManyToOne(targetEntity = LocationMaster.class,fetch = FetchType.LAZY)
	private LocationMaster location;
	
	@Column
	private int count = 0;
	
	@Column(name="product_id")
	private int productId = 0;
	
	@ManyToMany(targetEntity = Lot.class,fetch = FetchType.LAZY)
	private List<Lot> lots = new ArrayList<Lot>();
	
	@Column
	private String flaw = "No Info";
	
	@Column
	private boolean isComplete = false;
	
	@Column
	private String remark = "No Info";
	
	
	


	public void addLot(Lot lot) {
		this.lots.add(lot);
	}
	public void removeLot(Lot lot) {
		this.lots.remove(lot);
	}
	
	 
}
