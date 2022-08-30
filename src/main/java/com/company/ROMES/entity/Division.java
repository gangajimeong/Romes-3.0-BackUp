package com.company.ROMES.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name ="division")
public class Division {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty(message = "부서가 입력되지 않았습니다.")
	@Column
	private String division;
	
	@Column
	private String workInfo = "No Info";
	
	@Column
	private boolean disabled= false;
	
	@Column(columnDefinition = "boolean default false") // 시공부서 확인 데이터
	private boolean isConstruction = false;
}
