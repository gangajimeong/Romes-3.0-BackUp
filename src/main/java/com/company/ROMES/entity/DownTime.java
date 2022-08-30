package com.company.ROMES.entity;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Entity
@Table(name = "down_time_table")
public class DownTime {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	
	@Column
	private String title = "No Info";
	
	@Column
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	@Column
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime downTime;
	
	@Column
	private String remark;
	
	public String getRemark() {
		return remark==null?"":remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public String getStartTimeToString() {
		return this.startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
	public String getDownTimeToString() {
		return  this.downTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
	public LocalTime getDownTime() {
		return downTime;
	}

	public void setDownTime(LocalTime downTime) {
		this.downTime = downTime;
	}

}
