package com.company.ROMES.Services.ProductionM;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import com.company.ROMES.dao.UserDAO;

import com.company.ROMES.entity.DocAndTitle;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.OrderHistory;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.interfaces.dao.OrderHistoryDAOInterface;
import com.company.ROMES.interfaces.service.ProductionM.OrderHistoryService;


@Service
public class OrderHistoryServiceImp implements OrderHistoryService {
	@Autowired
	SessionFactory factory;

	@Autowired
	OrderHistoryDAOInterface dao;

	@Autowired
	UserDAO us;

	@Override
	public List<OrderHistory> selectAll() {
		Session session = null;
		List<OrderHistory> order = new ArrayList<OrderHistory>();
		try {
			session = factory.openSession();
			order = dao.selectAll(session);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return order;
	}

	@Override
	public OrderHistory selectById(int id) {
		Session session = null;
		OrderHistory order = new OrderHistory();
		try {
			session = factory.openSession();
			order = dao.selectById(session, id);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();

		}
		return order;
	}

	@Override
	public List<OrderHistory> selectByRegistedDate(LocalDate startDate, LocalDate endDate) {
		Session session = null;
		List<OrderHistory> lists = new ArrayList<OrderHistory>();
		try {
			session = factory.openSession();
			lists = dao.selectByRegistedDate(session, startDate, endDate);

		} catch (Exception e) {
			e.printStackTrace();
			session.clear();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return lists;
	}

	@Override
	public List<OrderHistory> selectByRegistedDate(LocalDate date) {
		Session session = null;
		List<OrderHistory> lists = new ArrayList<OrderHistory>();
		try {
			session = factory.openSession();
			lists = dao.selectByRegistedDate(session, date);

		} catch (Exception e) {
			e.printStackTrace();
			session.clear();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return lists;
	}

	@Override
	public List<OrderHistory> selectByArriveDate(LocalDate startDate, LocalDate endDate) {
		Session session = null;
		List<OrderHistory> lists = new ArrayList<OrderHistory>();
		try {
			session = factory.openSession();
			lists = dao.selectByArriveDate(session, startDate, endDate);

		} catch (Exception e) {
			e.printStackTrace();
			session.clear();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return lists;
	}

	@Override
	public List<OrderHistory> selectByArriveDate(LocalDate date) {
		Session session = null;
		List<OrderHistory> lists = new ArrayList<OrderHistory>();
		try {
			session = factory.openSession();
			lists = dao.selectByArriveDate(session, date);

		} catch (Exception e) {
			e.printStackTrace();
			session.clear();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return lists;
	}

	@Override
	public List<OrderHistory> selectByisComplete(boolean state) {
		Session session = null;
		List<OrderHistory> lists = new ArrayList<OrderHistory>();
		try {
			session = factory.openSession();
			lists = dao.selectByisComplete(session, state);

		} catch (Exception e) {
			e.printStackTrace();
			session.clear();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return lists;
	}

	@Override
	public boolean createOrderHistory(OrderHistory orderHistory) {
		Session session = null;
		Transaction transaction = null;
		LocalDate register = LocalDate.now();
		boolean state = false;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			orderHistory.setRegisterDate(register);
			state = dao.createOrderHistory(session, orderHistory);
			transaction.commit();
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive())
				transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public boolean deleteOrderHistory(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
		
			session = factory.openSession();
			transaction = session.beginTransaction();
			state = dao.deleteOrderHistory(session, id);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive())
				transaction.rollback();
		}finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public OrderHistory MateralORProductDecision(int product_id, int cnt, OrderHistory orderHistory) {
		Session session = null;
		try {
			session = factory.openSession();
			if (orderHistory.isMaterial() == true) {
				Material m = new Material();
				m = session.find(Material.class, product_id);
				orderHistory.setProduct(m.getName());
				orderHistory.setProduct_id(product_id);
			} else {
				ManufactureProduct mp = new ManufactureProduct();
				mp = session.find(ManufactureProduct.class, product_id);
				orderHistory.setProduct(mp.getName());
				orderHistory.setProduct_id(product_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return orderHistory;
	}

	@Override
	public boolean createAllOrderHistory(List<OrderHistory> lists) {
		Session session = null;
		Transaction transaction = null;
		LocalDate register = LocalDate.now();
		boolean state = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userID = auth.getName();
		String userName = "정보 없음";
		System.out.println(userID);
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			if (!userID.equals("anonymousUser")) {
				userName = us.selectByLoginId(session, userID);
			}
			String doc = "Doc"
					+ (String) session.createSQLQuery("select OrderDocGenerator() as text").getSingleResult();
			//String doc ="Doc102938109238";
			for (OrderHistory orderHistory : lists) {
				orderHistory.setRegisterDate(register);
				orderHistory.setDocumentNumber(doc);
				orderHistory.setCompanyName();
				orderHistory.setManager(userName);
				state = dao.createOrderHistory(session, orderHistory);
			}

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "발주 등록");
			
			transaction.commit();
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive())
				transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public List<DocAndTitle> selectByDoc() {
		Session session = null;
		List<Object[]> lists = new ArrayList<>();
		List<DocAndTitle> DT = new ArrayList<DocAndTitle>();
		try {
			session = factory.openSession();
			lists = dao.selectDoc(session);

			for (Object[] o : lists) {
				DocAndTitle d = new DocAndTitle();
				d.setDocNumber(o[0].toString());
				d.setTitle(o[1].toString());
				DT.add(d);
			}
			// System.out.println(lists.get(0).getDocumentNumber());
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return DT;
	}

	@Override
	public List<OrderHistory> selectByDocNum(String DocNum) {
		Session session = null;
		List<OrderHistory> lists = new ArrayList<OrderHistory>();
		try {
			session = factory.openSession();
			lists = dao.selectByDocNum(session, DocNum);
			for (OrderHistory o : lists) {
				o.setCompany(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	@Override
	public boolean updateAllOrderHistory(List<OrderHistory> create, List<OrderHistory> update) {
		Session session = null;
		Transaction transaction = null;
		String DocNumber = "Doc000000000";
		String title = "정보 없음";
		boolean state = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userID = auth.getName();
		String userName = "정보 없음";
		System.out.println(userID);
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			if (!userID.equals("anonymousUser")) {
				userName = us.selectByLoginId(session, userID);
			}
			if (update.size() > 0) {
				OrderHistory ori = new OrderHistory();
				for (OrderHistory order : update) {
					ori = dao.selectById(session, order.getId());
					DocNumber = ori.getDocumentNumber();
					
					if (ori.isMaterial() != order.isMaterial()) {
						if (ori.getProduct_id() != order.getProduct_id()) {
							ori.setMaterial(order.isMaterial());
							ori.setProduct_id(order.getProduct_id());
							ori.setProduct(order.getProduct());
						}
					}
					if (ori.getOrderCount() != order.getOrderCount()) {
						ori.setOrderCount(order.getOrderCount());
					}
					if(ori.getProduct() != order.getProduct()) {
						ori.setProduct(order.getProduct());
					}
					if(ori.getProduct_id() != order.getProduct_id()) {
						ori.setProduct_id(order.getProduct_id());
					}
					
					if (ori.getPrice() != order.getPrice()) {
						ori.setPrice(order.getPrice());
					}
					if (ori.getDocTitle() != order.getDocTitle()) {
						ori.setDocTitle(order.getDocTitle());
						title = order.getDocTitle();
					}else {
						title = ori.getDocTitle();
					}
					if (ori.getOrderDate() != order.getOrderDate()) {
						ori.setOrderDate(order.getOrderDate());
					}
					if (ori.getPlannedArriveDate() != order.getPlannedArriveDate()) {
						ori.setPlannedArriveDate(order.getPlannedArriveDate());
					}
					if (ori.getCompany().getId() != order.getCompany().getId()) {
						ori.setCompany(order.getCompany());
						ori.setCompanyName();
					}
					ori.setManager(userName);
					state = dao.updateOrderHistory(session, ori);
				}
			}
			if (create.size() > 0) {
				LocalDate register = LocalDate.now();
				for (OrderHistory orderHistory : create) {
					orderHistory.setRegisterDate(register);
					orderHistory.setCompanyName();
					orderHistory.setDocumentNumber(DocNumber);
					orderHistory.setDocTitle(title);
					orderHistory.setManager(userName);
					state = dao.createOrderHistory(session, orderHistory);
				}
			}
			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "발주 업데이트");
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive())
				transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public boolean deleteDocuments(List<String> DocLists) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			List<OrderHistory>deleteslists = new ArrayList<OrderHistory>();
			for(String doc: DocLists) {
				deleteslists.addAll(dao.selectByDocNum(session, doc));
			}
			for(OrderHistory o: deleteslists) {
				state=dao.deleteDocument(session, o);
			}
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "발주 삭제");
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive())
				transaction.rollback();
		}finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		
		return state;
	}

	

}
