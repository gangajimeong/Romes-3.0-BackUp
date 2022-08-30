package com.company.ROMES.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;



/*
 * 
 * workNumber 생성 식을 적어두고 변경시 관리자에게 문의할 것을 적음.
 * 
 */
@Entity
@Table(name="common_table")
public class CommonCode {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name = "codeId")
	private String codeId;
	
	@Column(name="value")
	private String value;
	
	@Column(name="columncode")
	private String columnCode;
	
	@Column(name="classification")
	private String classification;
	
	@Column(name = "disabled")
	private boolean disabled = false;
	
	
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
		
	}

	@Column
	private boolean isAbsolute =false;

	public CommonCode() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public boolean isAbsolute() {
		return isAbsolute;
	}

	public void setAbsolute(boolean isAbsolute) {
		this.isAbsolute = isAbsolute;
	}

	@Override
	public String toString() {
		return "CommonCode [id=" + id + ", codeId=" + codeId + ", value=" + value
				+ ", columnCode=" + columnCode + "]";
	}
}
