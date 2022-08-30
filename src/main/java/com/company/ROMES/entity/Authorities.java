package com.company.ROMES.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "authorities")

public class Authorities {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
		
	@Column
	private String authority;
	
	@Column
	private LocalDateTime AuthUpdateDate = LocalDateTime.now();
	
	public LocalDateTime getAuthUpdateDate() {
		return AuthUpdateDate;
	}
	public void setAuthUpdateDate(LocalDateTime authUpdateDate) {
		AuthUpdateDate = authUpdateDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		
		this.authority ="ROLE_"+authority.toUpperCase();
	}
	 public boolean isRole(String role) {
		    return authority.equals("ROLE_" + role.toUpperCase());
	    }

}
