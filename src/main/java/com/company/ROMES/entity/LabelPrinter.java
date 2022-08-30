package com.company.ROMES.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.print.PrintService;

import com.company.ROMES.functions.PrintingObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class LabelPrinter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column
	private String printerName;
	
	@Column
	private int fontSize;
	
	@Column
	private int margin_w;
	
	@Column
	private int margin_h;
	
	public boolean setPrinterName(String name) {
		for(PrintService s : PrintingObject.getPrintList()) {
			if(s.getName().equals(name)) {
				this.printerName = name;
				return true;
			}
		}
		return false;
	}
}