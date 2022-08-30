package com.company.ROMES.Services.StandardInfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.LoggerUtil;
import com.company.ROMES.Services.GeoCoderService;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.Brand;
import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.Company_manager;
import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.entity.LatLng;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.StandardInfo.BrandService;
import com.google.code.geocoder.Geocoder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class BrandServiceImp implements BrandService {
	@Autowired
	SessionFactory factory;

	@Autowired
	GeoCoderService geo;
	@Autowired
	UserDAO us;

	@Override
	public JSONArray SelectBrandByFalse() {
		// TODO Auto-generated method stub
		Session session = null;
		List<Brand> brands = null;
		JSONArray rets = null;
		try {
			session = factory.openSession();
			rets = new JSONArray();
			brands = session.createQuery("Select this_ from Brand this_ where this_.disabled=false order by this_.id")
					.getResultList();
			for (Brand brand : brands) {
				JSONObject ret = new JSONObject();
				ret.put("id", brand.getId());
				ret.put("brand", brand.getCompanyName());
				ret.put("company", brand.getCompany().getCompanyName());
				ret.put("ceo", brand.getCEO_Name());
				ret.put("tel", brand.getPhone());
				ret.put("remark", brand.getRemarks());
				rets.add(ret);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rets = null;
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	@Override
	public JSONArray SelectBranch(int id) {
		Session session = null;
		JSONArray rets = new JSONArray();
		try {
			session = factory.openSession();
			Brand brand = session.find(Brand.class, id);
			List<LatLng> stores = brand.getBranch();
			if (stores.size() > 0) {

				for (LatLng lg : stores) {
					JSONObject ret = new JSONObject();
					ret.put("id", lg.getId());
					ret.put("title", lg.getTitle());
					ret.put("addr", lg.getAddress());
					ret.put("cons", lg.getCompany() != null ? lg.getCompany().getCompanyName() : "정보 없음");
					rets.add(ret);
				}
			} else {
				rets.add("No Info");
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

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray SelectBrandAllDataById(int id) {
		// TODO Auto-generated method stub
		Session session = null;
		JSONArray ret = null;
		try {
			session = factory.openSession();
			Brand brand = session.find(Brand.class, id);
			ret = new JSONArray();
			JSONArray stores = new JSONArray();
			ret.add(brand.getId());
			ret.add(brand.getCompanyName());
			ret.add(brand.getCEO_Name());
			ret.add(brand.getPhone());
			ret.add(brand.getCompany().getId());
			ret.add(brand.getRemarks());

			for (LatLng lg : brand.getBranch()) {
				JSONObject store = new JSONObject();
				store.put("id", lg.getId());
				store.put("title", lg.getTitle());
				store.put("company", lg.getCompany() != null ? lg.getCompany().getCompanyName() : null);
				store.put("company_id", lg.getCompany() != null ? lg.getCompany().getId() : null);
				store.put("addr", lg.getAddress());
				store.put("detail", lg.getDetail() == null? null : lg.getDetail());
				stores.add(store);

			}
			ret.add(stores);
		} catch (Exception e) {
			e.printStackTrace();
			ret = null;
			// TODO: handle exception
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject createBrand(Brand brand) {
		// TODO Auto-generated method stub
		JSONObject ret = new JSONObject();
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			LoggingTool.createLog(session, user, "Manager", "브랜드 등록: " + brand.getCompanyName());
			LoggerUtil.info("BrandManagement", userID, "create");

			session.save(brand);
			transaction.commit();
			ret.put("id", brand.getId());
			ret.put("companyName", brand.getCompanyName());
			ret.put("CEO_Name", brand.getCEO_Name());
			ret.put("phone", brand.getPhone());
			ret.put("remarks", brand.getRemarks());

		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();

		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return ret;
	}

	@Override
	public JSONObject UpdateBrand(Brand brand) {
		// TODO Auto-generated method stub
		JSONObject ret = new JSONObject();
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Brand oriBrand = new Brand();
			oriBrand = session.find(Brand.class, brand.getId());
			if (oriBrand.getCompanyName() != brand.getCompanyName())
				oriBrand.setCompanyName(brand.getCompanyName());

			if (oriBrand.getCompany() != null && brand.getCompany() != null)
				if (oriBrand.getCompany().getId() != brand.getCompany().getId())
					oriBrand.setCompany(brand.getCompany());

			if (oriBrand.getCEO_Name() == null) {
				oriBrand.setCEO_Name(brand.getCEO_Name());
			} else {
				if (oriBrand.getCEO_Name() != brand.getCEO_Name())
					oriBrand.setCEO_Name(brand.getCEO_Name());
			}

			if (oriBrand.getPhone() == null) {
				oriBrand.setPhone(brand.getPhone());
			} else {
				if (!oriBrand.getPhone().equals(brand.getPhone()))
					oriBrand.setPhone(brand.getPhone());
			}

			if (oriBrand.getRemarks() == null) {
				oriBrand.setRemarks(brand.getRemarks());
			} else {
				if (oriBrand.getRemarks() != brand.getRemarks())
					oriBrand.setRemarks(brand.getRemarks());
			}

			

			session.update(oriBrand);
			ret.put("id", oriBrand.getId());
			ret.put("companyName", oriBrand.getCompanyName());
			ret.put("CEO_Name", oriBrand.getCEO_Name());
			ret.put("phone", oriBrand.getPhone());
			System.out.println(oriBrand.getCompany().getId());
			ret.put("company", oriBrand.getCompany() != null ? oriBrand.getCompany().getCompanyName() : "정보 없음");
			ret.put("remarks", oriBrand.getRemarks());

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			LoggingTool.createLog(session, user, "Manager", "브랜드 업데이트: " + brand.getCompanyName());
			LoggerUtil.info("BrandManagement", userID, "update");
			transaction.commit();

		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
			e.printStackTrace();

		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return ret;
	}

	@Override
	public boolean DeleteBrand(int id) {
		// TODO Auto-generated method stub
		boolean ret = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Brand brand = session.find(Brand.class, id);
			brand.setDisabled(true);

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			LoggingTool.createLog(session, user, "Manager", "브랜드 삭제: " + brand.getCompanyName());
			LoggerUtil.info("BrandManagement", userID, "delete");

			transaction.commit();
			ret = true;
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
			ret = false;
		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return ret;
	}

	@Override
	public JSONObject createBranch(List<String> lists, int id) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			Gson gson = new Gson();
			List<LatLng> stores = new ArrayList<LatLng>();
			Brand brand = session.find(Brand.class, id);
			for (String data : lists) {
				LatLng store = new LatLng();
				JsonObject obj = gson.fromJson(data, JsonObject.class);
				boolean consId = obj.get("cons").isJsonNull();
				String name = obj.get("name").getAsString();
				String addr = obj.get("address").getAsString();

				store = geo.addLocation(session, name, addr, brand);
				System.out.println(store.toString());
				if (consId) {
					ConstructionCompany cons = session.find(ConstructionCompany.class, obj.get("cons").getAsInt());
					store.setCompany(cons);
				} else {
					store.setCompany(null);
				}
				session.update(store);
				stores.add(store);
			}
			brand.setBranch(stores);
			session.update(brand);
			ret.put("state", true);
			LoggingTool.createLog(session, user, "Manager", "매장 등록");
			LoggerUtil.info("Branch", userID, "regist");
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			ret.put("state", false);
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret;
	}

	@Override
	public boolean updateBranch(List<String> lists, int id) {

		Session session = null;
		Transaction transaction = null;

		boolean ret = false;
		for (String s : lists)

			try {
				session = factory.openSession();
				transaction = session.beginTransaction();
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String userID = auth.getName();
				User user = new User();
				user = us.findAdmin(session, userID);

				Gson gson = new Gson();
				Brand brand = session.find(Brand.class, id);
				List<LatLng> branchs = brand.getBranch();
				for (String data : lists) {
					System.out.println(data);
					LatLng store = new LatLng();
					JsonObject obj = gson.fromJson(data, JsonObject.class);
					boolean consId = obj.get("cons").isJsonNull();
					System.out.println(consId);
					String name = obj.get("name").getAsString();
					String addr = obj.get("address").getAsString();
					int store_id = obj.get("id").getAsInt();
					if (store_id == 0) {
						System.out.println(addr);
						if (addr.isEmpty()) {
							store.setAddress(null);
							store.setBrand(brand);
							store.setLat(0);
							store.setLng(0);
							store.setTitle(name);
							session.save(store);
						} else {
							store = geo.addLocation(session, name, addr, brand);
						}

						if (consId == false) {
							ConstructionCompany cons = session.find(ConstructionCompany.class,
									obj.get("cons").getAsInt());
							store.setCompany(cons);
						} else {
							store.setCompany(null);
						}
						session.update(store);
						branchs.add(store);
					} else {

						store = session.find(LatLng.class, store_id);
						String oriAddr = store.getAddress();
						if (oriAddr != addr) {
							store.setAddress(addr);
						}
						if (store.getTitle() != name) {
							store.setTitle(name);
						}
						if (consId == false) {
							ConstructionCompany cons = session.find(ConstructionCompany.class,
									obj.get("cons").getAsInt());
							store.setCompany(cons);
						} else {
							store.setCompany(null);
						}
						session.update(store);
					}
				}
				brand.setBranch(branchs);
				session.update(brand);
				LoggingTool.createLog(session, user, "Manager", "매장 수정");
				LoggerUtil.info("Branch", userID, "update");
				transaction.commit();
				ret = true;
			} catch (NoResultException e) {
				// TODO: handle exception
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
				ret = false;
			} finally {
				SessionMethod.closeSession(session, transaction);
			}
		return ret;
	}

	@Override
	public boolean deleteBranch(int id, int brand) {
		Session session = null;
		Transaction transaction = null;
		boolean ret = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Brand br = session.find(Brand.class, brand);
			LatLng lg = session.find(LatLng.class, id);
			List<LatLng> stores = br.getBranch();
			stores.remove(lg);
			br.setBranch(stores);
			session.update(br);
			session.delete(lg);

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			LoggingTool.createLog(session, user, "Manager", "매장 삭제: " + br.getBranch());
			LoggerUtil.info("Branch", userID, "delete");
			transaction.commit();

		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			ret = false;
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret;
	}

	@Override
	public boolean updateBranch(String datas, int id) {

		Session session = null;
		Transaction transaction = null;

		boolean ret = false;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			Gson gson = new Gson();
			Brand brand = session.find(Brand.class, id);
			List<LatLng> branchs = brand.getBranch();
			LatLng store = new LatLng();
			JsonObject obj = gson.fromJson(datas, JsonObject.class);
			System.out.println(obj);
			boolean consId = obj.get("cons").isJsonNull();
			System.out.println(consId);
			String name = obj.get("name").getAsString();
			String addr = obj.get("address").getAsString();
			String detail = obj.get("detail").getAsString();
			int store_id = obj.get("id").getAsInt();
			if (store_id == 0) {
				System.out.println(addr);
				if (addr.isEmpty()) {
					store.setAddress(null);
					store.setBrand(brand);
					store.setLat(0);
					store.setLng(0);
					store.setTitle(name);
					store.setDetail(detail);
					session.save(store);
				} else {
					store = geo.addLocation(session, name, addr, brand);
				}

				if (consId == false) {
					ConstructionCompany cons = session.find(ConstructionCompany.class, obj.get("cons").getAsInt());
					store.setCompany(cons);
				} else {
					store.setCompany(null);
				}
				session.update(store);
				branchs.add(store);
			} else {

				store = session.find(LatLng.class, store_id);
				String oriAddr = store.getAddress();
				if (oriAddr != addr) {
					store.setAddress(addr);
				}
				if (store.getTitle() != name) {
					store.setTitle(name);
				}
				if (consId == false) {
					ConstructionCompany cons = session.find(ConstructionCompany.class, obj.get("cons").getAsInt());
					store.setCompany(cons);
				} else {
					store.setCompany(null);
				}
				store.setDetail(detail);
				
				session.update(store);
			}
			brand.setBranch(branchs);
			session.update(brand);
			LoggingTool.createLog(session, user, "Manager", "매장 수정");
			LoggerUtil.info("Branch", name, "update");
			transaction.commit();
			ret = true;
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			ret = false;
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret;
	}

	@Override
	public boolean importExcel(JsonObject datas) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			String com = datas.get("Company").getAsString();
			String bra = datas.get("Brand").getAsString();
			String Title = datas.get("Title").getAsString();

			
			List<Brand> br = session.createQuery("Select this_ from Brand this_ where this_.companyName = '" +bra+ "'").getResultList();
			
			if(br.size() == 0) {
				System.out.println("here");
				Brand brand = new Brand();
				Company company = (Company) session
						.createQuery("Select this_ from Company this_ where this_.companyName like '%" + com + "%'")
						.getSingleResult();
				
				List<LatLng> list = new ArrayList<>();
				
				LatLng branch = new LatLng();
				
				brand.setCompanyName(bra);
				brand.setCompany(company);
				
				branch.setAddress(bra + " " + Title);
				branch.setBrand(brand);
				branch.setTitle(Title);
				
				list.add(branch);
				brand.setBranch(list);
				
				session.save(branch);
				session.save(brand);
				
			} else {
				List<LatLng> branch = session.createQuery("Select this_ from LatLng this_ where this_.title = '" +Title+"' and this_.brand.companyName = '"+bra+"'").getResultList();
				Brand brand = (Brand) session.createQuery("Select this_ from Brand this_ where this_.companyName = '" +bra+ "'").getSingleResult();
				if(branch.size() == 0) {
					System.out.println("wt");
					LatLng latlng = new LatLng();
					latlng.setAddress(bra + " " + Title);
					latlng.setBrand(brand);
					latlng.setTitle(Title);
					
					branch = brand.getBranch();
					branch.add(latlng);
					brand.setBranch(branch);
					
					session.update(brand);
					session.save(latlng);
				}else {
					System.out.println("herw");
					return true;
				}
			}
			
			
//			String com = datas.get("Company").getAsString();
//			String Brand = datas.get("Brand").getAsString();
//			String Title = datas.get("Title").getAsString();
			
//			List<Brand> br = session.createQuery("Select this_ from Brand this_").getResultList();
//			Brand bra = new Brand();
//
//			for (Brand brand : br) {
//				if (brand.getCompanyName().equals(Brand)) {
//					state = true;
//					bra = brand;
//					break;
//				}
//			}
//
//			List<LatLng> stores = new ArrayList<LatLng>();
//
//			if (state == false) {
//				Brand brand = new Brand();
//				brand.setCompanyName(Brand);
//
//				Company company = (Company) session
//						.createQuery("Select this_ from Company this_ where this_.companyName like '%" + com + "%'")
//						.getSingleResult();
//				brand.setCompany(company);
//				session.save(brand);
//
//				LatLng branch = new LatLng();
//				branch.setAddress(Brand + " " + Title);
//				branch.setBrand(brand);
//				branch.setTitle(Title);
//
//				stores.add(branch);
//				brand.setBranch(stores);
//
//				session.saveOrUpdate(brand);
//				session.save(branch);
//			} else {
//				LatLng branch = new LatLng();
//				branch.setAddress(Brand + " " + Title);
//				branch.setBrand(bra);
//				branch.setTitle(Title);
//
//				stores = bra.getBranch();
//				stores.add(branch);
//				bra.setBranch(stores);
//				session.saveOrUpdate(branch);
//			}
//			LoggerUtil.info("Brand", Brand, "ExcelRegist");
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}
}
