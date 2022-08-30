package com.company.ROMES.Services.ProductionM;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NoResultException;
import javax.print.Doc;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.ROMES.Services.FCMService;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.DesignMaster;
import com.company.ROMES.entity.DocumentForApproaval;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.webUpload;
import com.company.ROMES.interfaces.service.ProductionM.DocumentForWorkOrderService;

@Service
public class DocumentForWorkOrderImp implements DocumentForWorkOrderService {
	@Autowired
	SessionFactory factory;

	@Autowired
	UserDAO us;

	@Autowired
	webUpload upload;

	@Autowired
	FCMService message;
	final static int notUser = 0;
	final static int success = 1;
	final static int otherError = 2;
	final static int existproduct = 3;

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray orderInfos() {
		Session session = null;

		JSONArray rets = new JSONArray();
		try {
			session = factory.openSession();
			List<ReceivedOrderInfo> lists = new ArrayList<ReceivedOrderInfo>();
			lists = session.createQuery(
					"Select this_ from ReceivedOrderInfo this_ where this_.isConfirm = false and this_.isDisabled=false order by this_.receivedOrderDate desc")
					.getResultList();
			for (ReceivedOrderInfo r : lists) {

				JSONObject ret = new JSONObject();
				ret.put("id", r.getId());
				ret.put("title", r.getTitle());
//				List<ManufactureProduct> products = r.getProducts();
				JSONArray productArr = new JSONArray();
//				for (ManufactureProduct p : products) {
//					JSONObject product = new JSONObject();
//					product.put("id", p.getId());
//					product.put("title", p.getName());
//					productArr.add(product);
//				}
				ret.put("products", productArr);
				rets.add(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rets.add(-1);
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	@Override
	public JSONObject registerDesignDocument(HashMap<Object, Object> datas, MultipartFile file)
			throws InvalidFormatException, IOException {
		Session session = null;
		Transaction transaction = null;

		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			// 데이터 파싱 부분
			String title = datas.get("title").toString();
			int orderInfo_id = Integer.parseInt(datas.get("orderInfo_id").toString());
			int product_id = Integer.parseInt(datas.get("product_id").toString());
			int user_id = Integer.parseInt(datas.get("user_id").toString());
			String remark = datas.get("remark").toString().replace("\r\n", "<br>");
			DocumentForApproaval doc = new DocumentForApproaval();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			ReceivedOrderInfo info = new ReceivedOrderInfo();

			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				ret.put("state", notUser);
				return ret;
			}

			try {
				info = session.find(ReceivedOrderInfo.class, orderInfo_id);
				@SuppressWarnings("unchecked")
				DocumentForApproaval exist = (DocumentForApproaval) session.createQuery(
						"select this_ from DocumentForApproaval this_ where this_.isDisabeld = false and this_.product_id="
								+ product_id + " and this_.info.id=" + info.getId())
						.getSingleResult();
				ret.put("state", existproduct);
				return ret;
			} catch (NoResultException e) {
				doc.setDocumentOfTitle(title);
				doc.setWriter(user);
				doc.setWrittenDate(LocalDateTime.now());

				doc.setInfo(info);
				doc.setProduct_id(product_id);
				doc.addCooperater(user, true);
				doc.addCooperater(session.find(User.class, user_id), false);
				doc.setRemark(remark);
				if (file != null) {
					String url = upload.upload(file, "Design", 1);
					DesignMaster design = new DesignMaster();
					//design.setInfo(info);
					design.setRegistrationDate(LocalDateTime.now());
					design.setRegistUser(user);
					design.setUrl(url);
					session.save(design);
					doc.addDesign(design);
					ret.put("state", success);
				} else {
					DesignMaster design = new DesignMaster();
					//design.setInfo(info);
					design.setRegistrationDate(LocalDateTime.now());
					design.setRegistUser(user);
					design.setUrl(null);
					session.save(design);
					doc.addDesign(design);
					ret.put("state", success);
				}
				session.save(doc);
				transaction.commit();
				message.sendTopickMessage(user.getTel(), "디지안 시안 등록", title);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive())
				transaction.rollback();
			ret.put("state", otherError);
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject updateDesignDocument(HashMap<Object, Object> datas, MultipartFile file) {
		Session session = null;
		Transaction transaction = null;

		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();

			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				ret.put("state", notUser);
				return ret;
			}
			User cooperator = new User();
			// 데이터 파싱 부분
			int id = Integer.parseInt(datas.get("id").toString());
			String title = datas.get("title").toString();
			int user_id = Integer.parseInt(datas.get("user_id").toString());
			String remark = datas.get("remark").toString().replace("\r\n", "<br>");

			DocumentForApproaval doc = new DocumentForApproaval();
			doc = session.find(DocumentForApproaval.class, id);
			if (doc.getDocumentOfTitle() != title) {
				doc.setDocumentOfTitle(title);
			}
			if (doc.getRemark() != remark) {
				doc.setRemark(remark);
			}
			cooperator = session.find(User.class, user_id);
			doc.addCooperater(cooperator, false);
			doc.getApproveState().put(user, true);
			doc.setDocUpdateDate(LocalDateTime.now());
			if (file != null) {
				String url = upload.upload(file, "Design", 1);
				DesignMaster design = new DesignMaster();
				//design.setInfo(doc.getInfo());
				design.setRegistrationDate(LocalDateTime.now());
				design.setRegistUser(user);
				design.setUrl(url);
				session.save(design);
				doc.addDesign(design);
			}
			ret.put("state", success);

			session.update(doc);
			transaction.commit();
			message.sendTopickMessage(cooperator.getTel(), "디자인 시안 수정", title);

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive())
				transaction.rollback();
			ret.put("state", otherError);
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONArray selectForGrant() {
		Session session = null;
		JSONArray rets = new JSONArray();
		try {
			session = factory.openSession();
			List<DocumentForApproaval> docs = new ArrayList<DocumentForApproaval>();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			String state = null;
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				return rets;
			}
			docs = session.createQuery(
					"select this_ from DocumentForApproaval this_ where isDisabeld = false order by this_.writtenDate desc")
					.getResultList();
			for (DocumentForApproaval doc : docs) {
				if (doc.getApproveState().containsKey(user)) {
					JSONObject ret = new JSONObject();
					ret.put("id", doc.getId());
					ret.put("design", doc.getDesigns().get(doc.getDesigns().size() - 1));
					ret.put("title", doc.getDocumentOfTitle());
					ret.put("info_id", doc.getInfo().getId());
					ret.put("info_title", doc.getInfo().getTitle());
					ret.put("writtenDate", doc.getWrittenDate().toLocalDate());
					ret.put("writer", doc.getWriter().getName());
					ret.put("updateDate", doc.getDocUpdateDate() != null ? doc.getDocUpdateDate().toLocalDate() : "");
//					for (ManufactureProduct mp : doc.getInfo().getProducts()) {
//						if (mp.getId() == doc.getProduct_id()) {
//							ret.put("product", mp.getName());
//							break;
//						}
//					}
					ret.put("url", doc.getDesigns().get(doc.getDesigns().size() - 1).getUrl());
					if (doc.isContinue()) {
						state = "수정 중";
					} 
					if (doc.isContinue() == false) {
						state = "수정 완료";
					} 
					if (doc.isAdminConfrim() && doc.isContinue() == false) {
						state = "관리자 승인";
					}
					if (doc.isCustomConfirm()) {
						state = "고객 승인";
					} 
					if (doc.isCustomConfirm() == false && doc.isAdminConfrim() && doc.isContinue() == false) {
						state = "승인 대기";
					}
					ret.put("state", state);
					rets.add(ret);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	@Override
	public JSONArray selectAllDoc() {

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray selectReadyApprovalDoc() {
		Session session = null;
		JSONArray rets = new JSONArray();
		try {
			session = factory.openSession();
			List<DocumentForApproaval>docs = new ArrayList<DocumentForApproaval>();
			String state = null;
			docs = session.createQuery("select this_ from DocumentForApproaval this_ where this_.isDisabeld = false and this_.isContinue = false and this_.isAdminConfrim=true order by this_.writtenDate desc").getResultList();
			for(DocumentForApproaval doc: docs) {
				JSONObject ret = new JSONObject();
				ret.put("id", doc.getId());
				ret.put("design", doc.getDesigns().get(doc.getDesigns().size() - 1));
				ret.put("title", doc.getDocumentOfTitle());
				ret.put("info_id", doc.getInfo().getId());
				ret.put("info_title", doc.getInfo().getTitle());
				ret.put("updateDate", doc.getDocUpdateDate() != null ? doc.getDocUpdateDate().toLocalDate() : doc.getWrittenDate().toLocalDate());
//				for (ManufactureProduct mp : doc.getInfo().getProducts()) {
//					if (mp.getId() == doc.getProduct_id()) {
//						ret.put("product", mp.getName());
//						break;
//					}
//				}
				ret.put("url", doc.getDesigns().get(doc.getDesigns().size() - 1).getUrl());
				if (doc.isContinue()) {
					state = "수정 중";
				} 
				if (doc.isContinue() == false) {
					state = "수정 완료";
				} 
				if (doc.isAdminConfrim() && doc.isContinue() == false) {
					state = "관리자 승인";
				}
				if (doc.isCustomConfirm()) {
					state = "고객 승인";
				} 
				if (doc.isCustomConfirm() == false && doc.isAdminConfrim() && doc.isContinue() == false) {
					state = "승인 대기";
				}
				ret.put("state", state);
				rets.add(ret);

			}
		}  catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	@Override
	public JSONArray selectDocToId(int id) {
		Session session = null;
		DocumentForApproaval doc = null;
		JSONArray ret = null;
		try {
			session = factory.openSession();
			doc = new DocumentForApproaval();
			ret = new JSONArray();

			doc = session.find(DocumentForApproaval.class, id);
			ret.add(doc.getId());
			ret.add(doc.getDocumentOfTitle());
			ret.add(doc.getInfo().getId());
			ret.add(doc.getProduct_id());
			ret.add(doc.getDesigns().get(doc.getDesigns().size() - 1).getUrl());
			ret.add(doc.getRemark());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();

		}
		return ret;
	}

	@Override
	public JSONArray selectMakingDesignDoc() {
		Session session = null;
		JSONArray rets = null;
		String state = null;
		try {
			session = factory.openSession();
			List<DocumentForApproaval> lists = new ArrayList<DocumentForApproaval>();
			rets = new JSONArray();
			lists = session.createQuery(
					"Select this_ from DocumentForApproaval this_ where isDisabeld = false and isAdminConfrim = false and isContinue = true order by this_.writtenDate desc")
					.getResultList();
			for (DocumentForApproaval dfa : lists) {
				JSONObject ret = new JSONObject();
				ret.put("id", dfa.getId());
				ret.put("design", dfa.getDesigns().get(dfa.getDesigns().size() - 1));
				ret.put("title", dfa.getDocumentOfTitle());
				ret.put("info_id", dfa.getInfo().getId());
				ret.put("info_title", dfa.getInfo().getTitle());
				ret.put("writtenDate", dfa.getWrittenDate().toLocalDate());
				ret.put("writer", dfa.getWriter().getName());
				ret.put("updateDate", dfa.getDocUpdateDate() != null ? dfa.getDocUpdateDate().toLocalDate() : "");
//				for (ManufactureProduct mp : dfa.getInfo().getProducts()) {
//					if (mp.getId() == dfa.getProduct_id()) {
//						ret.put("product", mp.getName());
//						break;
//					}
//				}
				ret.put("url", dfa.getDesigns().get(dfa.getDesigns().size() - 1).getUrl());
				if (dfa.isContinue()) {
					state = "수정 중";
				} 
				if (dfa.isContinue() == false) {
					state = "수정 완료";
				} 
				if (dfa.isAdminConfrim() && dfa.isContinue() == false) {
					state = "관리자 승인";
				}
				if (dfa.isCustomConfirm()) {
					state = "고객 승인";
				} 
				if (dfa.isCustomConfirm() == false && dfa.isAdminConfrim() && dfa.isContinue() == false) {
					state = "승인 대기";
				}
				ret.put("state", state);
				rets.add(ret);
			}

		} catch (Exception e) {
			e.printStackTrace();
			rets.add("error");
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	@Override
	public boolean deleteDesignDocument(List<Integer> ids) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			for (int id : ids) {
				DocumentForApproaval doc = new DocumentForApproaval();
				doc = session.find(DocumentForApproaval.class, id);
				doc.setDisabeld(true);
				session.update(doc);
			}
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
	public JSONObject confirmCustomer(int id) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();

		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			if (userID.equals("anonymousUser")) {
				ret.put("state", notUser);
				return ret;
			}
			session = factory.openSession();
			transaction = session.beginTransaction();
			DocumentForApproaval doc = new DocumentForApproaval();
			doc = session.find(DocumentForApproaval.class, id);
			doc.setAdminConfrim(true);
			doc.setApprovedDate(LocalDateTime.now());
			doc.setContinue(false);
			session.update(doc);
			transaction.commit();
//			message.sendTopickMessage(doc.getInfo().getOrderCompany().getPhone(), "시안 확인 공지", doc.getDocumentOfTitle());

			ret.put("state", success);
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive())
				transaction.rollback();
			ret.put("state", otherError);
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public String checkGrant() {
		Session session = null;
		String userRole = null;
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();

			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
				userRole = user.getAuthority().getAuthority();
			} else {
				userRole = userID;
			}
		} catch (Exception e) {
			e.printStackTrace();
			userRole = "anonymousUser";
		}
		return userRole;
	}

	@Override
	public JSONObject getDocInfo(int id) {
		Session session = null;
		JSONObject ret = null;
		try {
			ret = new JSONObject();
			session = factory.openSession();
			DocumentForApproaval doc = new DocumentForApproaval();
			doc = session.find(DocumentForApproaval.class, id);
			ret.put("title", doc.getDocumentOfTitle());
			ret.put("writtenDate",doc.getWrittenDate());
			ret.put("writer", doc.getWriter().getName());
			ret.put("division", doc.getWriter().getDivision().getDivision());
			
			ReceivedOrderInfo info =  doc.getInfo();
			ret.put("orderInfo",info.getTitle());
			
			ManufactureProduct product =  session.find(ManufactureProduct.class, doc.getProduct_id());
			ret.put("product",product.getName());
			ret.put("remark", doc.getRemark());
			JSONArray coopers = new JSONArray();
			for(User user: doc.getApproveState().keySet()) {
				JSONObject cooperInfo = new JSONObject();
				cooperInfo.put("name", user.getName());
				cooperInfo.put("division", user.getDivision().getDivision());
				coopers.add(cooperInfo);
			}
			ret.put("coopers", coopers);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

}
