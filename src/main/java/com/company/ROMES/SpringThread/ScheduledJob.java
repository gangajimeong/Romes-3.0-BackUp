package com.company.ROMES.SpringThread;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.postgresql.copy.CopyOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.company.ROMES.Controller.Android.FileUpload;
import com.company.ROMES.entity.ConstructionResult;

public class ScheduledJob {
//	String filefolder = FileUpload.uploadPath;
//	@Autowired
//	SessionFactory factory;
//	@Scheduled(cron = "0 0 1 1 * *")
//	public void moveImageFile() {
//		LocalDate endDay = LocalDate.now().minusDays(1); 
//		LocalDate startDay = LocalDate.now().minusMonths(1);
//		Session session = null;
//		Transaction transaction = null;
//		try {
//			session = factory.openSession();
//			transaction = session.beginTransaction();
//			List<ConstructionResult> list = session.createQuery("Select this_ from ConstructionResult this_ where this_.time between :start and :end")
//					.setParameter("start", startDay.atTime(0,0,0))
//					.setParameter("end", endDay.atTime(23,59,59)).getResultList();
//			
//			for(ConstructionResult result : list) {
//				List<String> urls = result.getResultImageUrls();
//				String previousFolderPath = result.getLocation().getLocation().getTitle().replace(" ", "_")+"/이전사진";
//				File previousFolder = new File(filefolder+"/"+previousFolderPath);
//				if(!previousFolder.exists())
//					previousFolder.mkdirs();
//				for(String url: urls) {
//					File file = new File(filefolder + "/"+url);
//					String filename = file.getName();
//					String movePath = previousFolderPath+"/"+filename;
//					File copy = new File(filefolder+"/"+movePath);
//					Files.move(file.toPath(), copy.toPath());
//					result.removeResultImage(url);
//					result.addResultImage(movePath);
//				}
//				session.update(result);
//			}
//			
//			transaction.commit();
//		}catch(Exception e) {
//			e.printStackTrace();
//			if(transaction != null)
//				if(transaction.isActive())
//					transaction.rollback();
//		}finally {
//			if(session != null)
//				if(session.isOpen())
//					session.close();
//		}
//	}
}
