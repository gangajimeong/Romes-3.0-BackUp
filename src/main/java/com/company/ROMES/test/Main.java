package com.company.ROMES.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.company.ROMES.Services.GeoCoderService;
import com.company.ROMES.entity.Brand;
import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.entity.ConstructionLocation;
import com.company.ROMES.entity.ConstructionPlan;
import com.company.ROMES.entity.ErrorCode;
import com.company.ROMES.entity.LatLng;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.ProductionPlan;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkOrderChangeHistory;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.entity.WorkingLine;
import com.company.ROMES.functions.SessionMethod;

import hibernate.hibernate;

public class Main {

	public static void main(String[] args) {
//		Session session = hibernate.factory.openSession();
//		Transaction transaction = session.beginTransaction();
//		try {
//		session = hibernate.factory.openSession();
//		transaction = session.beginTransaction();
//		Random random = new Random();
//		List<WorkOrderInfo> infos = session.createQuery("Select this_ from WorkOrderInfo this_ where this_.isFinished=true").getResultList();
//		ConstructionCompany c = new ConstructionCompany();
//		c.setCompanyName("시공사A");
//		session.save(c);
//		User user = session.find(User.class, 5);
//		user.setConstructionCompany(c);
//		session.update(user);
//
//		int num = 1;
//		for(int i = 0; i < 3; i++) {
//			ConstructionPlan plan = new ConstructionPlan();
//			int idx = random.nextInt(infos.size());
//			plan.setWorkOrderInfo(infos.get(idx));
//			plan.setCompany(c);
//			plan.setPlanDate(LocalDateTime.now());
//			session.save(plan);
//			infos.remove(idx);
//			
//			for(int j = 0; j < random.nextInt(10)+1;j++) {
//				LatLng location= new LatLng();
//				location.setBrand(plan.getWorkOrderInfo().getOrderInfo().getBrand());
//				location.setCompany(c);
//				location.setTitle("지점"+num);
//				num++;
//				session.save(location);
//				ConstructionLocation l = new ConstructionLocation();
//				l.setLocation(location);
//				l.setPlan(plan);
//				session.save(l);
//				plan.addLocation(l);
//			}
//			session.update(plan);
//		}
//		transaction.commit();
//		System.out.println("DONE");
//		}catch(Exception e) {
//			e.printStackTrace();
//		}finally {
//			SessionMethod.closeSession(session, transaction);
//				
//		}
		GeoCoderService service = new GeoCoderService();
		System.out.println(service.getLocationPoint("경기도 수원시 권선구 고색로 18번길 32-3"));
		
	}
	public void test(Session session) {
		List<WorkingLine> lines = session.createQuery("Select this_ from WorkingLine this_").getResultList();
		List<User> users = session.createQuery("Select this_ from User this_ where this_.division.isConstruction=false").getResultList();
		List<User> allUsers = session.createQuery("Select this_ from User this_").getResultList();
	
		List<ErrorCode> codes = session.createQuery("Select this_ from ErrorCode this_ ").getResultList();
		Company c = session.find(Company.class, 33);
		Random random = new Random();
		Brand b = new Brand();
		b.setCompanyName("TestBrand1");
		b.setCompany(c);
		session.save(b);
		ReceivedOrderInfo info = new ReceivedOrderInfo();
		info.setTitle("test수주");
		info.setComplete(true);
		info.setUser(users.get(random.nextInt(users.size())));
		info.setBrand(b);
		info.setReceivedOrderDate(LocalDateTime.of(2021, 12,5,13,20,0));
		session.save(info);
		
		for(int i = 0 ; i < 30;i++) {
			LatLng l = new LatLng();
			l.setBrand(b);
			l.setTitle("지점_"+(i+1));
			session.save(l);
			WorkOrderInfo w = new WorkOrderInfo();
			w.setBranch(l);
			w.setProduct("10x"+((i+1)*10));
			w.setFinished(true);
			w.setStartTime(info.getReceivedOrderDate());
			w.setEndTime(LocalDateTime.of(2022, random.nextInt(4)+1,random.nextInt(20)+1,random.nextInt(23)+1,random.nextInt(60),random.nextInt(60)));
			w.setDesigner(users.get(random.nextInt(users.size())));
			w.setPrinter(lines.get(random.nextInt(lines.size())));
			w.setOrderInfo(info);
			session.save(w);
			info.addWorkInfo(w);
			if(random.nextBoolean()) {
				for(int j = 0;j < (random.nextInt(10)+1);j++) {
					WorkOrderChangeHistory history = new WorkOrderChangeHistory();
					history.setError(codes.get(random.nextInt(codes.size())));
					history.setWorkInfo(w);
					session.save(history);
				}
			}
		}
		session.update(info);
	}
}
