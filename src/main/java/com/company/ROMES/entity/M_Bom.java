package com.company.ROMES.entity;

import java.util.HashMap;
import java.util.Map;

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

@Entity
@Table(name = "m_bom")
public class M_Bom {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@ElementCollection
	@CollectionTable(name = "m_bom_meterials", joinColumns = {
			@JoinColumn(name = "m_bom_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "meterial_id")
	@Column(name = "counts")
	private Map<Integer, Double> meterials = new HashMap();

	@ManyToOne(targetEntity = ProcessUnit.class, fetch = FetchType.LAZY)
	private ProcessUnit processUnit;
}
