package com.company.ROMES.Services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.ini4j.Ini;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.entity.Brand;
import com.company.ROMES.entity.ErrorCode;
import com.company.ROMES.entity.LatLng;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.functions.JSONParsers;
import com.google.gson.JsonObject;

import Error_code.ResultCode;

@Service
public class GeoCoderService {
	@Autowired
	SessionFactory factory;

	public List<LatLng> selectLatLng() {
		Session session = null;
		List<LatLng> ret = new ArrayList<>();
		try {
			ret = session.createQuery("Select this_ from LatLng this_ order by this_.id").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	public List<LatLng> selectLatLng(int brandId) {
		Session session = null;
		List<LatLng> ret = new ArrayList<>();
		try {
			ret = session
					.createQuery(
							"Select this_ from LatLng this_ where this_.brand.id=" + brandId + " order by this_.id")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	public LatLng addLocation(Session session, String title, String address, Brand Brand) {
		LatLng ret = addressToLocation(session, address);
		if (ret != null) {
			ret.setBrand(Brand);
			ret.setTitle(title);
			session.update(ret);
		}
		return ret;
	}
	//addLocation;
	public LatLng addLocation(Session session, String title, String address, String detail, Brand Brand) {
		LatLng ret = addressToLocation(session, address);
		ret.setBrand(Brand);
		ret.setTitle(title);
		ret.setDetail(detail);
		session.update(ret);
		return ret;
	}

	public int addLocation(String title, String address, String detail, int receiveOrderId, int brandId) {
		Session session = null;
		Transaction transaction = null;
		int ret = 0;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			LatLng r = addressToLocation(session, address);
			ReceivedOrderInfo info = session.find(ReceivedOrderInfo.class, receiveOrderId);
			Brand brand = session.find(Brand.class, brandId);
			r.setBrand(brand);
			r.setTitle(title);
			r.setDetail(detail);
			session.update(ret);
			transaction.commit();
			ret = Error_code.ResultCode.SUCCESS;
		} catch (NullPointerException e) {
			ret = Error_code.ResultCode.REQUIRE_ELEMENT_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			ret = Error_code.ResultCode.UNKNOWN_ERROR;
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

	public int addLocation(String title, String address, int receiveOrderId) {
		Session session = null;
		Transaction transaction = null;
		int ret = 0;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			LatLng r = addressToLocation(session, address);
			ReceivedOrderInfo info = session.find(ReceivedOrderInfo.class, receiveOrderId);
			r.setBrand(null);
			r.setTitle(title);
			session.update(ret);
			transaction.commit();
			ret = Error_code.ResultCode.SUCCESS;
		} catch (NullPointerException e) {
			ret = Error_code.ResultCode.REQUIRE_ELEMENT_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			ret = Error_code.ResultCode.UNKNOWN_ERROR;
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
	public JSONObject getLocationPoint(String address) {
		JSONObject ret = new JSONObject();
		try {
			Ini ini = new Ini(new File("./setting.ini"));
			StringBuffer sb = new StringBuffer();
			String addr = URLEncoder.encode(address, "UTF-8");
			String api = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;
			String clientId = ini.get("geocoder-api", "client_id");
			String clientSecret = ini.get("geocoder-api", "client_secret");
			URL url = new URL(api);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
			conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
			conn.setRequestMethod("GET");
			conn.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line).append("\n");
			}
			System.out.println();
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(sb.toString());
			System.out.println(object);
			if (object.get("status").equals("OK")) {
				try {
					JSONArray arry = (JSONArray) parser.parse(object.get("addresses").toString());
					JSONObject o = (JSONObject) parser.parse(arry.get(0).toString());
					ret.put("lat", 0);
					ret.put("lng", 0);
					ret.put("roadAddress", o.get("roadAddress").toString());
					ret.put("oldAddress", o.get("jibunAddress").toString());
					ret.put("lat", Double.parseDouble(o.get("y").toString()));
					ret.put("lng", Double.parseDouble(o.get("x").toString()));
				
				
				} catch (IndexOutOfBoundsException e1) {
					ret.put("lat", 0);
					ret.put("lng", 0);
					ret.put("RESULT", ResultCode.NO_RESULT);
				}
			} else {
				ret.put("lat", 0);
				ret.put("lng", 0);
				ret.put("RESULT", ResultCode.REQUIRE_ELEMENT_ERROR);
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			ret.put("RESULT", ResultCode.UNKNOWN_ERROR);
		}
		return ret;
	}

	public LatLng addressToLocation(Session session, String address) {
		LatLng ret = null;

		try {
			ret = (LatLng) session.createQuery("Select this_ from LatLng this_ where this_.address='" + address
					+ "' or this_.address2='" + address + "'").getResultList().get(0);
		} catch (IndexOutOfBoundsException e) {
			try {
				Ini ini = new Ini(new File("./setting.ini"));
				StringBuffer sb = new StringBuffer();
				String addr = URLEncoder.encode(address, "UTF-8");
				String api = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;
				String clientId = ini.get("geocoder-api", "client_id");
				String clientSecret = ini.get("geocoder-api", "client_secret");
				URL url = new URL(api);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
				conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
				conn.setRequestMethod("GET");
				conn.connect();
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line;
				while ((line = in.readLine()) != null) {
					sb.append(line).append("\n");
				}
				System.out.println();
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(sb.toString());
				System.out.println(object);
				if (object.get("status").equals("OK")) {
					try {
						JSONArray arry = (JSONArray) parser.parse(object.get("addresses").toString());
						JSONObject o = (JSONObject) parser.parse(arry.get(0).toString());
						ret = new LatLng();
						ret.setAddress(o.get("roadAddress").toString());
						ret.setAddress2(o.get("jibunAddress").toString());
						ret.setLat(Double.parseDouble(o.get("y").toString()));
						ret.setLng(Double.parseDouble(o.get("x").toString()));
						session.save(ret);
					} catch (IndexOutOfBoundsException e1) {
						ret = new LatLng();
						ret.setLat(0);
						ret.setLng(0);
						session.save(ret);
					}
				} else {
					ret = new LatLng();
					ret.setLat(0);
					ret.setLng(0);
					session.save(ret);
				}
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		return ret;
	}

	public String LocationToAddress(double lat, double lng) {
		String ret = "";
		try {
			Ini ini = new Ini(new File("./setting.ini"));
			StringBuffer sb = new StringBuffer();
			String api = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords=" + lng + "," + lat
					+ "&orders=roadaddr&output=json";
			String clientId = ini.get("geocoder-api", "client_id");
			String clientSecret = ini.get("geocoder-api", "client_secret");
			URL url = new URL(api);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
			conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
			conn.setRequestMethod("GET");
			conn.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line).append("\n");
			}
			JSONParser parser = new JSONParser();
			System.out.println(sb);
			JSONObject object = (JSONObject) parser.parse(sb.toString());
			JSONObject result = (JSONObject) parser.parse(object.get("status").toString());

			if (result.get("code").toString().equals("0")) {
				try {
					JSONArray arry = (JSONArray) parser.parse(object.get("results").toString());
					JSONObject o = (JSONObject) parser.parse(arry.get(0).toString());
					JsonObject ob = JSONParsers.parseStringtoJsonObject(o.get("region"));
					ret = ret + ob.getAsJsonObject("area1").get("name").getAsString() + " ";
					ret = ret + ob.getAsJsonObject("area2").get("name").getAsString() + " ";
//					ret = ret + ob.getAsJsonObject("area3").get("name").getAsString() + " ";
//					ret = ret + ob.getAsJsonObject("area4").get("name").getAsString() + " ";
					JsonObject land = JSONParsers.parseStringtoJsonObject(o.get("land"));
					ret = ret + land.get("name").getAsString() + " ";
//					ret = ret + land.getAsJsonObject("addition0").get("value").getAsString() + " ";
					ret = ret + land.get("number1").getAsString() + (land.get("number2").getAsString().equals("") ? ""
							: ("-" + land.get("number2").getAsString()));
					ret = ret.replace("  ", " ");

				} catch (IndexOutOfBoundsException e1) {
					e1.printStackTrace();
					ret = null;
				}
			} else {
				ret = "";
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			ret = "";

		}
		return ret;
	}

	public String[] readGeoConfigFile() {
		String[] ret = { "NoData", "NoData" };
		try {
			File file = new File("./config.txt");
			if (!file.exists())
				file.createNewFile();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			ret[0] = reader.readLine();
			ret[1] = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
