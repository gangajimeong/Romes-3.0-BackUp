package com.company.ROMES.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_table")
@Getter
@Setter
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@NotEmpty(message = "아이디가 입력되지 않았습니다.")
	@Column(name = "loginid")
	private String LoginId;

	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Column(name = "loginpw")
	private String LoginPw;

	
	@Column(columnDefinition = "boolean default true")
	private boolean enabled = true;

	@Column
	private int InconsistencyCount = 0;

	@Column
	private boolean isLock = false;

	@ManyToOne(targetEntity = CommuteCode.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "commute_code_id")
	private CommuteCode commuteCode;

	@Column
	private LocalDate DateOfLastPasswordChange;
	
	@Column
	private LocalDateTime removeDate;
	
	@NotEmpty(message = "이름이 입력되지 않았습니다.")
	@Column(name = "name")
	private String name;

	@Column(name = "worker_id")
	private String workerId = "No Info";

	@JoinColumn(name = "division")
	@ManyToOne(targetEntity = Division.class, fetch = FetchType.EAGER)
	private Division division;

	@NotEmpty(message = "직책이 입력되지 않았습니다.")
	@Column
	private String position;

	@Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "올바른 전화번호 형식이 아닙니다.")
	@Column
	private String tel;

	@NotEmpty(message = "이메일 형식이 맞지 않습니다.")
	@Column
	private String email;
	@Column
	private String responsibility = "No Info";

	@Column(name = "image")
	private String imageUrl;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Authorities.class)
	@JoinColumn(name = "authority_id")
	private Authorities authority ;
	//회원 가입 날짜
	@Column
	private LocalDateTime JoinDate = LocalDateTime.now();
	
	@ManyToOne(targetEntity = ConstructionCompany.class,fetch = FetchType.LAZY)
	@JoinColumn
	private ConstructionCompany constructionCompany;
	
	private boolean isConstruction() {
		return constructionCompany != null;
	}
	
	public boolean hasRole(String role) {
			
			if (getAuthority().isRole(role)) {
				return true;
			}

		return false;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
