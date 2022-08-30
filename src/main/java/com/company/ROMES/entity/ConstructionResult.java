package com.company.ROMES.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@Entity
@Table(name="construction_result")
public class ConstructionResult {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne(targetEntity = ConstructionLocation.class,fetch = FetchType.LAZY)
	@JoinColumn
	private ConstructionLocation location;
	
	@OneToOne(targetEntity = User.class,fetch = FetchType.LAZY)
	@JoinColumn
	private User user;
	
	@Column
	private LocalDateTime time;
	
	@Column(columnDefinition = "boolean default false")
	private boolean failed = false;
	
	@Column
	private String remark;

	@ElementCollection
	@CollectionTable(name="construction_result_images",joinColumns = @JoinColumn(name="construction_result_id"))
	@Column
	private List<String> resultImageUrls = new ArrayList<String>();
	
//	@ManyToOne(targetEntity = BrandUser.class,fetch=FetchType.LAZY)
//	@JoinColumn
//	private BrandUser branduser;
	
	@ManyToOne(targetEntity = Brand.class, fetch=FetchType.LAZY)
	@JoinColumn
	private Brand brand;

	@JoinColumn
	@OneToOne(targetEntity = ConstructionPlan.class, fetch =FetchType.LAZY)
	private ConstructionPlan plan;
	
	public void addResultImage(String url) {
		if(!this.resultImageUrls.contains(url))
			this.resultImageUrls.add(url);
	}
	public void removeResultImage(String url) {
		if(this.resultImageUrls.contains(url))
			this.resultImageUrls.remove(url);
	}

	
}
