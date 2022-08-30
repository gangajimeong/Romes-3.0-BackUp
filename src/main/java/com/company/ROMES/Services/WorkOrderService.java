package com.company.ROMES.Services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.format.DateTimeFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.Dto.WorkOrderDto;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.dao.WorkOrderDAO;
import com.company.ROMES.entity.ConstructionLocation;
import com.company.ROMES.entity.ConstructionPlan;
import com.company.ROMES.entity.CycleTime;
import com.company.ROMES.entity.DesignMaster;
import com.company.ROMES.entity.DocumentForApproaval;
import com.company.ROMES.entity.DownTime;
import com.company.ROMES.entity.ErrorCode;
import com.company.ROMES.entity.ErrorProductHistory;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.ProductionPlan;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.SRHistory;
import com.company.ROMES.entity.ShippingHistory;
import com.company.ROMES.entity.ShippingPlan;
import com.company.ROMES.entity.ShippingProduct;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkOrderChangeHistory;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.WorkOrderServiceInterface;

import Error_code.ResultCode;

@Service
public class WorkOrderService implements WorkOrderServiceInterface {

	@Autowired
	SessionFactory factory;

	@Autowired
	WorkOrderDAO workOrderDao;

	@Autowired
	UserDAO us;
	
	public List<WorkOrderDto> getWorkOrderDto(){
		Session session = factory.openSession();
		List<WorkOrderDto> dtos = workOrderDao.getWorkOrderInfo(session);
		session.close();
		return dtos;
	}
	
	public List<WorkOrderDto> getcompleteWork(){
		Session session = factory.openSession();
		LocalDate date = LocalDate.now();
		System.out.println(date);
		List<WorkOrderDto> info = workOrderDao.getCompleteWork(session, date, true);
		session.close();
		return info;
	}
	
	
	@Override
	public JSONObject getWorkOrder(boolean isComplete) {
		JSONObject ret = new JSONObject();
		Session session = null;
		try {
			session = factory.openSession();
			JSONArray arry = new JSONArray();
			List<WorkOrderInfo> infos = workOrderDao.getWorkOrder(session, isComplete);
			for (WorkOrderInfo info : infos) {
				JSONObject o = new JSONObject();
//				ProductionPlan plan = info.getPlan();
				if (info.isFailed()) {
					WorkOrderChangeHistory change = (WorkOrderChangeHistory) session
							.createQuery("Select this_ from WorkOrderChangeHistory this_ where this_.workInfo.id="
									+ info.getId() + " order by this_.generateTime desc")
							.getResultList().get(0);
					if (change.isDisable())
						continue;
					else {
						o.put("id", change.getId());
						o.put("remark", change.getRemark());
						o.put("makecount", change.getMakeCount());
					}
				} else {
					o.put("id", info.getId());
					o.put("makecount", info.getMakeCount());
					o.put("remark", info.getRemark());
				}
				o.put("title", info.getOrderInfo().getTitle());
				o.put("company", info.getOrderInfo().getBrand().getCompanyName());
				o.put("product", info.getProduct()==null?"정보 없음":info.getProduct());

				o.put("plancount", info.getPlanCount());

//				o.put("size", plan.getMakeProduct().getStandard());
				o.put("size", info.getSize());
				o.put("isBack", info.isBack() ? "사용" : "미사용");
				o.put("isCoating", info.isCoating() ? "사용" : "미사용");
				o.put("line", info.getPrinter()==null?"정보 없음":info.getPrinter().getLine());
//				o.put("releaseDate", plan.getReleaseDate() == null ? "No Info"
//						: plan.getReleaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				o.put("direction", info.getBranch() == null ? "No Info" : info.getBranch().getTitle());
//				JSONArray designs = new JSONArray();
//				for (DesignMaster m : info.getSampleDesigns()) {
//					JSONObject d = new JSONObject();
//					d.put("url", m.getUrl());
//					designs.add(d);
//				}
//				if(designs.size() == 0) {
//					JSONObject d = new JSONObject();
//					d.put("url", "/no_image.png");
//					designs.add(d);
//				}
				
				JSONArray designs = new JSONArray();
				JSONObject d = new JSONObject();
				d.put("url", info.getDesign() == null ? "/no_image.png":info.getDesign().getUrl());
				designs.add(d);

				
				JSONArray changes = new JSONArray();
				o.put("urls", designs);
				o.put("changes", changes);
				arry.add(o);

			}
			ret.put("data", arry);
			ret.put("result", ResultCode.SUCCESS);
		} catch (NullPointerException e) {
			ret.put("result", ResultCode.NO_RESULT);
			e.printStackTrace();
		} catch (Exception e) {
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		System.out.println(ret);
		return ret;
	}

	@Override
	public JSONObject completeWorkOrder(int userid, int workorderId, int count, int errorid) {
		JSONObject ret = new JSONObject();
		Session session = null;
		Transaction transaction = null;
		System.out.println(errorid);
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = session.find(User.class, userid);
			
			ConstructionPlan conPlan = new ConstructionPlan();
			ConstructionLocation location = new ConstructionLocation();
			List<ConstructionLocation> locate = new ArrayList<ConstructionLocation>();
			WorkOrderInfo info = session.find(WorkOrderInfo.class, workorderId);
			if (errorid != 0) {
				ErrorCode errorCode = session.find(ErrorCode.class,errorid);
				ErrorProductHistory history = new ErrorProductHistory();
				history.setErrorCount(count);
				history.setTime(LocalDateTime.now());
				history.setErrorCode(errorCode);
				history.setUser(user);
				history.setOrder(info);
				session.save(history);
			} else {
				if (info != null) {
//					ProductionPlan woplan = info.getPlan();
					if (info.getStartTime() == null)
						info.setStartTime(LocalDateTime.now());
					info.setMakeCount(info.getMakeCount() + count);
					if (info.isCompare()) {
						info.setFinished(true);
						info.setEndTime(LocalDateTime.now());
						info.setMakingTeamCheck(true);
						info.setManager(user);
						if(info.isConstruction()) {
							
							//시공지시 생성
							conPlan.setPlanDate(LocalDateTime.now());
							conPlan.setCompany(info.getBranch().getCompany());
							conPlan.setWorkOrderInfo(info);
							
							//로케이션 생성
							location.setLocation(conPlan.getWorkOrderInfo().getBranch());
							location.setPlan(conPlan);
							
							session.save(location);
							System.out.println(location.getId());
							session.save(conPlan);
							
							locate = session.createQuery("Select this_ from ConstructionLocation this_ where this_.id = "+location.getId()+"").getResultList();
							conPlan.setLocations(locate);
							session.update(conPlan);
						}
					}
					session.update(info);
				}else{
					WorkOrderChangeHistory history = session.find(WorkOrderChangeHistory.class, workorderId);
					info = history.getWorkInfo();
					
					if(history.getStartTime() == null) {
						history.setStartTime(LocalDateTime.now());
					}
					history.setMakeCount(history.getMakeCount()+count);
					info.setMakeCount(info.getMakeCount()+count);
					if(history.getPlanCount() <= history.getMakeCount()) {
						history.setFinished(true);
						info.setFinished(true);
						info.setEndTime(LocalDateTime.now());
						if (info.isCompare()) {
							info.setFinished(true);
							info.setEndTime(LocalDateTime.now());
							info.setMakingTeamCheck(true);
							
							if(info.isConstruction()) {
								
								//시공지시 생성
								conPlan.setPlanDate(LocalDateTime.now());
								conPlan.setCompany(info.getBranch().getCompany());
								conPlan.setWorkOrderInfo(info);
								
								//로케이션 생성
								location.setLocation(conPlan.getWorkOrderInfo().getBranch());
								location.setPlan(conPlan);
								
								session.save(location);
								session.save(conPlan);
								
								locate = session.createQuery("Select this_ from ConstructionLocation this_ where this_.id = "+location.getId()+"").getResultList();
								conPlan.setLocations(locate);
								session.update(conPlan);
							}
						}
					}
					
					session.update(info);
					session.update(history);
				}
				ReceivedOrderInfo order = info.getOrderInfo();
				if(order.isCompare()) {
					order.setProduction(true);
					session.update(order);
				}
			}
			ShippingHistory ship = new ShippingHistory();
			ship.setWorkInfo(info);
			ship.setUser(user);
			ship.setTime(LocalDateTime.now());
			session.save(ship);
			LoggingTool.createLog(session, user,"Android" ,info.getProduct()+"/"+info.getOrderInfo().getBrand().getCompanyName()+"/"+info.getBranch().getTitle()+" : 작업지시 완료");
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
		} catch (NullPointerException e) {
			e.printStackTrace();
			ret.put("result", ResultCode.NO_RESULT);
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		} finally {
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}
	//생산 시간 계산
	@Override
	public JSONArray calculateProduction(int manufactureId, int count, LocalDateTime startTime) {
		Session session = null;
		JSONArray ret = new JSONArray();
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
//		try {
//			session = factory.openSession();
//			ManufactureProduct product = session.find(ManufactureProduct.class, manufactureId);
//			List<CycleTime> cts = session
//					.createQuery("Select this_ from CycleTime this_ where this_.manufacture.id=" + manufactureId)
//					.getResultList();
//			List<DownTime> dts = session.createQuery("Select this_ from DownTime this_ order by this_.startTime")
//					.getResultList();
//			List<LocalTime[]> upTimes = new ArrayList<>();
//			LocalTime attendTime = LocalTime.of(9, 0, 0);
//			LocalTime endTime = LocalTime.of(18, 0, 0);
//			for (int i = 0; i < dts.size(); i++) {
//				LocalTime[] upt = new LocalTime[2];
//				if (i == 0) {
//					upt[0] = attendTime;
//				} else
//					upt[0] = dts.get(i - 1).getDownTime();
//				upt[1] = dts.get(i).getStartTime();
//				upTimes.add(upt);
//			}
//			upTimes.add(new LocalTime[] { dts.get(dts.size() - 1).getDownTime(), endTime });
//			LocalDateTime tmp = null;
//			if (startTime.isBefore(startTime.toLocalDate().atTime(attendTime)))
//				tmp = startTime.toLocalDate().atTime(attendTime);
//			else
//				tmp = startTime;
//			for (CycleTime t : cts) {
//				JSONObject object = new JSONObject();
//				object.put("line_id", t.getWorkingLine().getId());
//				object.put("line", t.getWorkingLine().getLine());
//				object.put("startTime", tmp.format(format));
//				int totalSec = (int) t.getProcessDuration().getSeconds() * count;
//				while (totalSec > 0) {
//					if (tmp.toLocalDate().getDayOfWeek().getValue() > 5) {
//						tmp = tmp.plusDays(1);
//						continue;
//					}
//					LocalTime ts = null;
//					for (LocalTime[] time : upTimes) {
//						int sec = getDurationToSec(time);
//						if (ts == null)
//							ts = tmp.toLocalTime();
//						else
//							ts = time[0];
//						if (sec < totalSec) {
//							totalSec = totalSec - sec;
//						} else {
//							tmp = tmp.toLocalDate().atTime(ts).plusSeconds(totalSec);
//							totalSec = 0;
//						}
//					}
//					if (totalSec > 0)
//						tmp = tmp.toLocalDate().plusDays(1).atTime(attendTime);
//				}
//				object.put("endTime", tmp.format(format));
//				ret.add(object);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (session != null)
//				if (session.isOpen())
//					session.close();
//		}
		return ret;
	}

	public int getDurationToSec(LocalTime[] times) {
		return (int) Duration.between(times[0], times[1]).getSeconds();
	}

	@Override
	public JSONObject failWorkInfo(int userId, int workInfoId,int errorId, boolean remake, String remark) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = session.find(User.class, userId);
			WorkOrderInfo wInfo = session.find(WorkOrderInfo.class, workInfoId);
			ErrorCode code = session.find(ErrorCode.class, errorId);
//			ManufactureProduct product;
			int releaseCount = 0;
			if (wInfo == null) {
				WorkOrderChangeHistory history = session.find(WorkOrderChangeHistory.class, workInfoId);
				history.setDisable(true);
				history.setMakeCount(0);
				session.update(history);
				wInfo = history.getWorkInfo();
			}
			if (!wInfo.isFailed()) {
				wInfo.setFailed(true);
				wInfo.setFinished(false);
			}
//			product =wInfo.getPlan().getMakeProduct();
			releaseCount = wInfo.getMakeCount();
			wInfo.setMakeCount(0);
			session.update(wInfo);
//			SRHistory srhistory= new SRHistory();
//			srhistory.setCount(releaseCount);
//			srhistory.setLocation(wInfo.getPlan().getLine().getLocation());
//			srhistory.setTime(LocalDateTime.now());
//			srhistory.setUser(user);
//			srhistory.setProductId(product.getId());
//			srhistory.setType(SRHistory.RELEASE);
//			srhistory.setRemark("생산 이력 변경");
//			session.save(srhistory);
			
		//	product.setStockCount(product.getStockCount()-releaseCount);
//			session.update(product);
			if (remake) {
				WorkOrderChangeHistory history = new WorkOrderChangeHistory();
				history.setDisable(false);
				history.setGenerateTime(LocalDateTime.now());
				history.setMakeCount(0);
				history.setRemark(remark);
				history.setWorkInfo(wInfo);
				history.setUser(user);
				history.setError(code);
				session.save(history);
			}
			LoggingTool.createLog(session, user,"Android" , wInfo.getProduct()+"/"+ wInfo.getOrderInfo().getBrand().getCompanyName()+"/"+ wInfo.getBranch().getTitle()+" : 작업지시 변경");
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
		}catch(NoResultException e) {
			System.out.println("Need Login");
			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret;
	}

	@Override
	public JSONArray getWorkOrderWithChangeLog() {
		Session session = null;
		JSONArray ret = new JSONArray();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
			session = factory.openSession();
			List<WorkOrderInfo> infos = session.createQuery("Select this_ from WorkOrderInfo this_ where this_.isFinished=false order by this_.isEmergency desc, this_.id").getResultList();
			for(WorkOrderInfo info:infos) {
				JSONObject o = new JSONObject();
				o.put("tagId", "#"+info.getId());
				o.put("id", info.getId());
				o.put("user", info.getDesigner() == null? "No Info": info.getDesigner().getName());
				o.put("company", info.getOrderInfo()==null?"No info":info.getOrderInfo().getOrderCompany()==null?"Np info":info.getOrderInfo().getOrderCompany().getCompanyName());
				o.put("workingLine",info.getPrinter()==null? "No info": info.getPrinter().getLine());
				o.put("product", info.getProduct());
				o.put("size", info.getSize()==null?"No info":info.getSize());
				o.put("isBack", info.isBack() ? "사용" : "미사용");
				o.put("isCoating", info.isCoating() ? "사용" : "미사용");
				o.put("makecount", info.getMakeCount());
				o.put("ordercount", info.getPlanCount());
				o.put("isemerency", info.isEmergency());
//				o.put("releaseDate", info.getPlan().getReleaseDate() == null ? "No Info"
//						: info.getPlan().getReleaseDate().format(dateFormat));
				o.put("direction", info.getBranch() == null ? "No Info" : info.getBranch().getTitle());
				o.put("remark", info.getRemark()==null?"특이사항 없음":info.getRemark());
				List<WorkOrderChangeHistory> historys = session.createQuery("Select this_ from WorkOrderChangeHistory this_ where this_.workInfo.id="+info.getId()+" order by this_.generateTime").getResultList();
				JSONArray hi = new JSONArray();
				for(WorkOrderChangeHistory history:historys) {
					JSONObject h = new JSONObject();
					h.put("id", history.getId());
					h.put("generateDay",history.getGenerateTime() == null?"정보 없음": history.getGenerateTime().format(timeFormat));
					h.put("ordercount", history.getPlanCount());
					h.put("makecount", history.getMakeCount());
					h.put("remark", history.getRemark());
//					h.put("error", history.getError().getErrorCode());
					hi.add(h);
				}
				o.put("history", hi);
				ret.add(o);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

	@Override
	public JSONObject failWorkInfo(int workInfoId,int errorId ,boolean remake, String remark) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = us.selectByIdForUser(session, userId);
			ErrorCode error = session.find(ErrorCode.class, errorId);
			WorkOrderInfo wInfo = session.find(WorkOrderInfo.class, workInfoId);
			ManufactureProduct product;
			int releaseCount = 0;
			if (wInfo == null) {
				WorkOrderChangeHistory history = session.find(WorkOrderChangeHistory.class, workInfoId);
				history.setDisable(true);
				history.setMakeCount(0);
				session.update(history);
				wInfo = history.getWorkInfo();
			}
			if (!wInfo.isFailed() && remake) {
				wInfo.setFailed(true);
				wInfo.setFinished(false);
			}
//			product =wInfo.getPlan().getMakeProduct();
//			releaseCount = wInfo.getMakeCount();
//			wInfo.setMakeCount(0);
//			session.update(wInfo);
//			SRHistory srhistory= new SRHistory();
//			srhistory.setCount(releaseCount);
//			srhistory.setLocation(wInfo.getPlan().getLine().getLocation());
//			srhistory.setTime(LocalDateTime.now());
//			srhistory.setUser(user);
//			srhistory.setProductId(product.getId());
//			srhistory.setType(SRHistory.RELEASE);
//			srhistory.setRemark("생산 이력 변경");
//			session.save(srhistory);
			
			//product.setStockCount(product.getStockCount()-releaseCount);
//			session.update(product);
			if (remake) {
				WorkOrderChangeHistory history = new WorkOrderChangeHistory();
				history.setDisable(false);
				history.setGenerateTime(LocalDateTime.now());
				history.setMakeCount(0);
				history.setRemark(remark);
				history.setWorkInfo(wInfo);
				history.setUser(user);
				history.setError(error);
				session.save(history);
			}
			
			if(!remake) {
				wInfo.setRemark(remark);
			}
			LoggingTool.createLog(session, user,"Android" , wInfo.getProduct()+"/"+ wInfo.getOrderInfo().getBrand().getCompanyName()+"/"+ wInfo.getBranch().getTitle()+" : 작업지시 변경");
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
		}catch(NoResultException e) {
			System.out.println("Need Login");
			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret;
	}
	
}
