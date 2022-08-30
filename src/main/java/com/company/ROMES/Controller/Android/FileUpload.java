package com.company.ROMES.Controller.Android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Source;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.company.ROMES.Services.GeoCoderService;
import com.company.ROMES.entity.Brand;
import com.company.ROMES.entity.ConstructionLocation;
import com.company.ROMES.entity.ConstructionPlan;
import com.company.ROMES.entity.ConstructionResult;
import com.company.ROMES.entity.LatLng;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;

import Error_code.ResultCode;
import io.swagger.annotations.ApiOperation;

@Controller
public class FileUpload {
	public final static String uploadPath = System.getProperty("user.home") + "/uploadImages";
	public final static String noImagePath = System.getProperty("user.home") + "/uploadImages/no_image.png";
	@Autowired
	SessionFactory factory;
	@Autowired
	GeoCoderService geoService;

	@GetMapping(value = "/image", produces = { MediaType.IMAGE_JPEG_VALUE, "text/plain;charset=UTF-8" })
	public ResponseEntity<byte[]> imageSearch(@RequestParam("imagename") String imagename) throws IOException {
		InputStream imageStream = null;
		System.out.println(uploadPath);
		File folder = new File(uploadPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (!folder.canRead())
			folder.setReadable(true);
		if (!folder.canWrite())
			folder.setWritable(true);
		String path = uploadPath + "/" + imagename;
		if (imagename.contains("C:") || imagename.contains("D:"))
			path = imagename;
		byte[] imageByteArray = null;
		File file = new File(path.replaceAll(" ", ""));
		System.out.println(path+":"+file.exists());
		if (file.exists())
			imageStream = new FileInputStream(path.replaceAll(" ", ""));
		else
			imageStream = new FileInputStream(noImagePath);

		imageByteArray = IOUtils.toByteArray(imageStream);
		imageStream.close();
		return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
	}

	@PostMapping(value = "/Android/getCurrentAddress", produces = "application/text;charset=UTF-8")
	@ResponseBody
	public String getCurrentLocation(double lat, double lng) {
		System.out.println(lat + "/" + lng);
		JSONObject object = new JSONObject();
		object.put("location", geoService.LocationToAddress(lat, lng));
		System.out.println(object);
		return object.toString();
	}

	@PostMapping(value = "/Android/imageUpload", produces = "application/text;charset=UTF-8")
	@ResponseBody
	public String imageUpload(@RequestParam("files[]") MultipartFile[] files,
			@RequestParam(name = "constructid") int constructId, @RequestParam(name = "userid") int userId,
			@RequestParam(name = "planid") int planid, @RequestParam(name = "branchid") int branchid,
			@RequestParam(name = "address") String address, @RequestParam(name = "detail") String detail,
			@RequestParam(name = "branch") String branch , @RequestParam(name ="isFail") boolean isFail, @RequestParam(name="failInfo") String failInfo) {
		JSONObject ret = new JSONObject();
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = session.find(User.class, userId);
			ConstructionPlan plan = session.find(ConstructionPlan.class, planid);
			ConstructionLocation location = session.find(ConstructionLocation.class, constructId);

			

			Brand brand = plan.getWorkOrderInfo().getOrderInfo().getBrand();

			ConstructionResult result = null;
			try {
				result = (ConstructionResult) session
						.createQuery(
								"Select this_ from ConstructionResult this_ where this_.location.id=" + plan.getId())
						.getSingleResult();
			} catch (NoResultException e) {
				result = new ConstructionResult();
				result.setLocation(location);
				result.setUser(user);
				result.setPlan(plan);
				result.setFailed(isFail);
				result.setRemark(failInfo);
				session.save(result);
			} catch (NonUniqueResultException e) {
				result = (ConstructionResult) session
						.createQuery(
								"Select this_ from ConstructionResult this_ where this_.location.id=" + plan.getId())
						.getResultList().get(0);
			}
			result.setTime(LocalDateTime.now());
			session.update(result);
			location.setComplete(true);
			if (plan.isCompare()) {
				plan.setComplete(true);
				session.update(plan);
			}
			LatLng latlng = session.find(LatLng.class, branchid);

			if(!latlng.getAddress().equals(address)) {
				JSONObject o = geoService.getLocationPoint(address);
				if(Integer.parseInt(o.get("RESULT").toString()) == ResultCode.SUCCESS) {
					latlng.setAddress(o.get("loadAddress").toString());
					latlng.setAddress2(o.get("oldAddress").toString());
					latlng.setLat(Double.parseDouble(o.get("lat").toString()));
					latlng.setLng(Double.parseDouble(o.get("lng").toString()));
					latlng.setDetail(detail);
					session.update(latlng);
				}
			}
			session.update(latlng);
			location.setLocation(latlng);
			session.update(location);
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
			int i = 1;
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					String filename = file.getOriginalFilename();
					String fileForm = filename.substring(filename.lastIndexOf("."));
					String folderPath = 
//							brand.getCompany().getCompanyName() + "/" 
							brand.getCompanyName().replace(" ", "_") + "/"
							+ latlng.getTitle().replace(" ","_");
					String fileName = user.getConstructionCompany().getCompanyName().replace(" ", "_") + "_"
							+ latlng.getTitle().replace(" ", "_") + "_" 
							+ LocalDateTime.now().format(format) + i
							+ fileForm;
					File folder = new File(uploadPath + "/" + folderPath);
					if (!folder.exists())
						folder.mkdirs();
					File saveFile = new File(uploadPath + "/" + folderPath + "/" + fileName);
					result.addResultImage(folderPath + "/" + fileName);
					file.transferTo(saveFile);
					i++;
				}
			}
			session.update(result);
			LoggingTool.createLog(session, user, "Android", "시공 실적 생성");
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				transaction.rollback();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret.toJSONString();
	}

	@RequestMapping(value = "/Android/fileUpload")
	@ResponseBody
	public String fileUpload(@RequestParam("files[]") MultipartFile file) {
		String uploadPath = System.getProperty("user.home") + "/location/";

		String filename = "";
		String ret = "";
		try {
			if (!file.isEmpty()) {
				filename = file.getOriginalFilename();
				try {
					File folder = new File(uploadPath);
					if (!folder.exists())
						folder.mkdir();
					File saveFile = new File(uploadPath + filename);

					file.transferTo(saveFile);
					FileInputStream fis = new FileInputStream(saveFile);
					ZipInputStream zis = new ZipInputStream(fis);
					ZipEntry zip = null;
					while ((zip = zis.getNextEntry()) != null) {
						try {
							String imagename = zip.getName();
							File image = new File(uploadPath, imagename);
							if (zip.isDirectory() && !image.exists())
								image.mkdirs();
							else
								createFile(image, zis);
							System.out.println("File Save : " + image.getAbsolutePath());
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
					saveFile.delete();
					fis.close();
					zis.close();
					ret = "saved";
				} catch (Exception e) {
					e.printStackTrace();
					ret = "save Fail";
				}
			}
		} catch (NullPointerException e) {
			ret = "NoFiles";
		}
		System.out.println(ret);
		return ret;
	}

	private static void createFile(File file, ZipInputStream zis) throws Throwable {
		File parentDir = new File(file.getParent());
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		try (FileOutputStream fos = new FileOutputStream(file)) {
			byte[] buffer = new byte[256];
			int size = 0;
			while ((size = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, size);
			}
			fos.close();
		} catch (Throwable e) {
			throw e;
		}
	}
}
