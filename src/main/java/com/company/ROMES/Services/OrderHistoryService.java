package com.company.ROMES.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.dao.LotDAO;
import com.company.ROMES.dao.MeterialProductDAO;
import com.company.ROMES.dao.OrderHistoryDAO;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.LabelPrinter;
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.Lot;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.OrderHistory;
import com.company.ROMES.entity.SRHistory;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.PrintingObject;
import com.company.ROMES.interfaces.service.OrderHistoryServiceInterface;
import com.sun.jdi.Location;

import hibernate.LotFactory;

@Service
public class OrderHistoryService implements OrderHistoryServiceInterface {

	@Autowired
	SessionFactory factory;

	@Autowired
	OrderHistoryDAO orderDao;

	@Autowired
	UserDAO userDao;

	@Autowired
	LotDAO lotDao;

	@Autowired
	MeterialProductDAO meterialDao;

	@Override
	public List<OrderHistory> selectTodayOrderHistory() {
		Session session = null;
		List<OrderHistory> historys = null;
		try {
			session = factory.openSession();
			historys = orderDao.selectByArriveDate(session, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return historys;
	}

	@Override
	public OrderHistory selectOrderHistory(int id) {
		OrderHistory ret = null;
		Session session = null;
		try {
			session = factory.openSession();
			ret = orderDao.selectById(session, id);

		} catch (Exception e) {
			e.printStackTrace(); // TODO: handle exception
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public boolean completeOrder(int orderId, int userId, int count, int printerId, int locationId, String remark) {
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		LotFactory lotFactory = new LotFactory();
		List<PrintingObject> objs = new ArrayList<PrintingObject>();
		String printName = "";
		System.out.println("orderId : " + orderId);
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = userDao.selectByPk(session, userId);
			OrderHistory history = orderDao.selectById(session, orderId);
			LocationMaster master = session.find(LocationMaster.class, locationId);
			LabelPrinter printer = session.find(LabelPrinter.class, printerId);
			int productId = history.getProduct_id();
			int packNum = 1;

			if (printerId != 0)
				printName = printer.getPrinterName();
			if (history.isMaterial()) {
				System.out.println("wh");
				objs = storeMaterialProduct(session, history, count, user, printer, master, remark);
			} else {
				System.out.println("this");
				objs = storeManufatureProduct(session, history, count, user, printer, master, remark);
			}
			session.update(history);
			LoggingTool.createLog(session, user, "Android", "발주 완료 처리");
			transaction.commit();
			if (printerId != 0) // 바코드 출력
				for (PrintingObject o : objs) {
					o.print(printName);
				}
			ret = true;
		} catch (Exception e) {
			ret = false;
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	public List<PrintingObject> storeManufatureProduct(Session session, OrderHistory history, int count, User user,
			LabelPrinter printer, LocationMaster master, String remark) {
		Material product = session.find(Material.class, history.getProduct_id());
		int packCount = count / (product.getUnitCount() < 1 ? 1 : product.getUnitCount());
		LotFactory lotFactory = new LotFactory();
		SRHistory srhistory = new SRHistory();
		srhistory.setType(SRHistory.STORE);
		srhistory.setProductId(history.getProduct_id());
		srhistory.setTime(LocalDateTime.now());
		srhistory.setLocation(master);
		srhistory.setCount(count);
		srhistory.setUser(user);
		srhistory.setRemark(remark);
		System.out.println("now");
		List<PrintingObject> ret = new ArrayList<PrintingObject>();
		for (int i = 0; i < packCount; i++) {
			String lotnumber = lotFactory.getGeneratedLot(session, product);
			System.out.println("here");
			System.out.println(lotnumber);
			Lot lot = new Lot();
			lot.setLot(lotnumber);
			lot.setProductId(product.getId());
			lot.setCount(product.getUnitCount());
			lot.setLocation(master);
			lotDao.saveLot(session, lot);
			srhistory.addLot(lot);
			history.addLot(lot);
			if (printer != null) {
				PrintingObject ob = new PrintingObject();
				ob.addPrintingLine(product.getName());
				ob.addPrintingLine(lotnumber);
				ob.setFontSize(printer.getFontSize());
				ob.setMargin_h(printer.getMargin_h());
				ob.setMargin_w(printer.getMargin_w());
				ob.setBarcode(lotnumber);
				ret.add(ob);
			}
		}

		// history.addStoreProduct(meterialId, storeCount);
		session.update(master);
		session.save(srhistory);
		product.setStockCount(product.getStockCount() + count);
		session.update(product);
		history.setStoredCount(history.getStoredCount() + count);
		if (history.getStoredCount() >= history.getOrderCount())
			history.setComplete(true);
		session.update(history);
		return ret;
	}

	public List<PrintingObject> storeMaterialProduct(Session session, OrderHistory history, int count, User user,
			LabelPrinter printer, LocationMaster master, String remark) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		String date = LocalDateTime.now().format(format);
		Material product = session.find(Material.class, history.getProduct_id());
		
		int labelCount = count / (product.getUnitCount() < 1 ? 1 : product.getUnitCount());	// Number of label 
		int remainder = count % product.getUnitCount(); // The rest, remainder
		
		if(remainder != 0) {
			labelCount += 1;
		}
		
		int	unitCount = 0;
		
		if(count < product.getUnitCount())
			unitCount = count;
		else
			unitCount = product.getUnitCount();
		
		
		LotFactory lotFactory = new LotFactory();
		SRHistory srhistory = new SRHistory();
		srhistory.setType(SRHistory.STORE);
		srhistory.setProductId(history.getProduct_id());
		srhistory.setTime(LocalDateTime.now());
		srhistory.setCount(count);
		srhistory.setLocation(master);
		srhistory.setUser(user);
		srhistory.setRemark(remark);
		
		List<PrintingObject> ret = new ArrayList<PrintingObject>();
		for (int i = 0; i < labelCount; i++) {
			String lotnumber = lotFactory.getGeneratedLot(session, product); 
			if(remainder != 0) {
				if(i == labelCount -1) {
					Lot lot = new Lot();
					lot.setLot(lotnumber);
					lot.setProductId(product.getId());
					lot.setLocation(master);
//					lot.setCount(product.getUnitCount());
					lot.setCount(remainder);
					lotDao.saveLot(session, lot);
					System.out.println("generated lot id="+lot.getId());
					srhistory.addLot(lot);
					history.addLot(lot);
					master.addLot(lot);
				} else {
					Lot lot = new Lot();
					lot.setLot(lotnumber);
					lot.setProductId(product.getId());
					lot.setLocation(master);
//					lot.setCount(product.getUnitCount());
					lot.setCount(unitCount);
					lotDao.saveLot(session, lot);
					System.out.println("generated lot id="+lot.getId());
					srhistory.addLot(lot);
					history.addLot(lot);
					master.addLot(lot);
				}
			} else {
				Lot lot = new Lot();
				lot.setLot(lotnumber);
				lot.setProductId(product.getId());
				lot.setLocation(master);
//				lot.setCount(product.getUnitCount());
				lot.setCount(unitCount);
				lotDao.saveLot(session, lot);
				System.out.println("generated lot id="+lot.getId());
				srhistory.addLot(lot);
				history.addLot(lot);
				master.addLot(lot);
			}
			
			if (printer != null) {
				// 바코드 출력
				PrintingObject ob = new PrintingObject();
				ob.addPrintingLine(product.getName());
				ob.addPrintingLine(lotnumber);
//				ob.addPrintingLine(product.getProductCode() + "-" + date);
				ob.setFontSize(printer.getFontSize());
				ob.setMargin_h(printer.getMargin_h());
				ob.setMargin_w(printer.getMargin_w());
				ob.setBarcode(lotnumber);
//				ob.setBarcode(product.getProductCode());
				ret.add(ob);
			}
		}
//		history.addStoreProduct(meterialId, storeCount);
		session.update(master);
		session.save(srhistory);
		product.setStockCount(product.getStockCount() + count);
		session.update(product);
		history.setStoredCount(history.getStoredCount() + count);
		if (history.getStoredCount() >= history.getOrderCount())
			history.setComplete(true);
		session.update(history);
		return ret;
	}

	public List<Material> selectMeterials(Set<Integer> keylist) {
		List<Material> retlist = new ArrayList<>();
		Session session = null;
		try {
			session = factory.openSession();
			Iterator<Integer> it = keylist.iterator();
			while (it.hasNext()) {
				Material m = meterialDao.selectById(session, it.next());
				if (m != null)
					retlist.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retlist = null;
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return retlist;
	}

	@Override
	public List<OrderHistory> selectTodayOrderHistory(Session session) {
		return orderDao.selectByArriveDate(session, LocalDate.now());
	}
}