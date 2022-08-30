package com.company.ROMES.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="notice")
@Getter
@Setter
public class Notice {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column
	private int id;
	
	@Column
	private String title;
	
	@Column
	private LocalDateTime time;
	
	@Column
	private String msg;
	
	@ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
//	@Column
//	private String imagePath;
	
	@ElementCollection
	private List<String> images = new ArrayList<>();
	
	public void addImage(String imagePath) {
		this.images.add(imagePath);
	}
	
	public void removeImage(String image) {
		this.images.remove(image);
	}
	
}
