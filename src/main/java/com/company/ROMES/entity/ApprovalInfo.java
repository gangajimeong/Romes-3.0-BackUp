package com.company.ROMES.entity;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
@Table
public class ApprovalInfo {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 @Column
 private int id;
 
 //결제 유저 순서
 @NotNull
 @OneToMany(fetch = FetchType.LAZY, targetEntity = User.class) 
 private List<User>ApprovalPhase;
 
 //결제 순서 이름 ex) 디자인부 결제 라인
 @Column
 private String approvalInfoName= "No info";

 //활성화 삭제 , 비삭제
 @Column
 private boolean isDisabeld = false;

 //등록 날짜
 @Column 
 private LocalDateTime createdDate= LocalDateTime.now();
 
 //최근 변경 날짜
 @Column
 private LocalDateTime updateDate = LocalDateTime.MIN;
 
 //삭제 날짜
 @Column
 private LocalDateTime removeDate = LocalDateTime.MIN;
 
 
 
}
