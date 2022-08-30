package com.company.ROMES.entity;

import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.math3.geometry.partitioning.Region.Location;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="cycle_time")
public class CycleTime {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(targetEntity = ManufactureProduct.class,fetch = FetchType.LAZY)
	@JoinColumn(name="manufacture_id")
	private ManufactureProduct manufacture;
	
	@Column
	private Duration processDuration = Duration.ZERO;

	@ManyToOne(targetEntity = WorkingLine.class,fetch = FetchType.LAZY)
	@JoinColumn(name="workingline_id")
	private WorkingLine workingLine;

	
	
}
