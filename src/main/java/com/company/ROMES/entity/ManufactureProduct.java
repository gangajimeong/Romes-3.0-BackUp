package com.company.ROMES.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name="manufacture_product")
public class ManufactureProduct {
	// Process, E-Bom,
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(nullable = false)
	private String name="No Info";
//	
//	@Column(nullable = false)
//	private String standard = "No Info";
	
	@Column
	private boolean isDisable = false;
//
//	@Column(nullable = false,unique = true)
//	private String productCode;//-> disabled
//	
	@Column
	private LocalDateTime createDate;
	
	@Column
	private LocalDateTime disabledDate;
	
	@Column
	private LocalDateTime lastUpdateDate;
	
//	// 자재그룹 ex) 노즐, 카풀링, 알맹이 등등
//	//@Column
//	//private String productGroup;
//	@ManyToOne(targetEntity = CommonCode.class)
//	@JoinColumn(name = "groupcode_id")
//	private CommonCode productGroup;
//	// 자재특성 ex) 목재, 알루미늄, 철 등등
//	//@Column
//	//private String productFeature;
//	@ManyToOne(targetEntity = CommonCode.class)
//	@JoinColumn(name = "featurecode_id")
//	private CommonCode productFeature;
//	
//	@Column(nullable = false)
//	private int defaultSalePrice = 0;
//	
//	@ManyToOne(targetEntity = LocationMaster.class)
//	@JoinColumn(name = "default_location_id")
//	private LocationMaster defaultLocation;
//	
//	@ManyToOne(targetEntity = CommonCode.class)
//	@JoinColumn
//	private CommonCode unitType;
//	
//	@ManyToOne(targetEntity = CommonCode.class)
//	@JoinColumn
//	private CommonCode quantitiesType;
//	
//	@Column(nullable = false)
//	private int unitCount = 1;
//	
//	@Column(nullable = false)
//	private int properQuentity = 0;
//	
//	@Column(nullable = false)
//	private int stockCount = 0;
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@OneToMany(targetEntity = ConstructionLocation.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<ConstructionLocation> location = new ArrayList<>();
	
	@Column(columnDefinition = "int default 0")
	private int orderCount = 0;
	
	@ManyToOne(targetEntity = ReceivedOrderInfo.class,fetch = FetchType.LAZY)
	@JoinColumn
	private ReceivedOrderInfo orderInfo;
	
	@OneToOne(targetEntity = ProductionPlan.class,fetch = FetchType.LAZY)
	@JoinColumn
	private ProductionPlan plan;

	@ManyToOne(targetEntity = Brand.class,fetch = FetchType.LAZY)
	@JoinColumn
	private Brand brand;
	
	//시공 완료
	@Column(columnDefinition = "boolean default false")
	private boolean isCompleteConstruction;
	//최종승인 완료
	@Column(columnDefinition = "boolean default false")
	private boolean isComplete;
	
	@ManyToOne(targetEntity = Division.class,fetch = FetchType.LAZY)
	@JoinColumn
	private Division constructionTeam;
	
	public void addLocation(ConstructionLocation l) {
		if(!this.location.contains(l))
			location.add(l);
	}
	
	public void removeLocaiton(ConstructionLocation l) {
		location.remove(l);
	}

	
	@OneToMany(targetEntity = DesignMaster.class,fetch = FetchType.LAZY)
	private List<DesignMaster> sampleDesignPath = new ArrayList<>();
	
	public void addSample(DesignMaster master) {
		if(!this.sampleDesignPath.contains(master))
			this.sampleDesignPath.add(master);
	}
	
	public void removeSample(DesignMaster master) {
		if(this.sampleDesignPath.contains(master))
			this.sampleDesignPath.remove(master);
	}
	
}
