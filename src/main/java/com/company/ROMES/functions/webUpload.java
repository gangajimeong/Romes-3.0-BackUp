package com.company.ROMES.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class webUpload {
	static String BasicPath = System.getProperty("user.home")+"/ROMES/";
	static String sujuImg = "[OrderInfo]";
	static String DesignDocImg = "[Design]";

	public String upload(MultipartFile file, String Saveurl, int option) {
		
		String path = BasicPath ; 
		String title = null;
		String fileName = null;
		try {

			if (!Saveurl.isEmpty()) {
				path = BasicPath+ Saveurl+"/";
			}
			File Dir = new File(path);
			if (!Dir.exists()) {
				Dir.mkdirs();
			}
			if (option == 0) {
				title = sujuImg;
			} else {
				title = DesignDocImg;
			}
			String originFileName = file.getOriginalFilename();
			fileName = title +LocalDate.now()+ "_"+ originFileName;
			String safeFile = path + fileName;
			try {
				file.transferTo(new File(safeFile));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}

		return Saveurl+"/"+fileName;
	}

	public List<String> uploadAndGetUrls(List<MultipartFile> lists, String Saveurl) {
		List<String> urlsList = new ArrayList<String>();
		String path = BasicPath + "images/";
		try {

			if (!Saveurl.isEmpty()) {
				path = BasicPath + Saveurl;
			}
			File Dir = new File(path);
			if (!Dir.exists()) {
				Dir.mkdirs();
			}

			if (lists.size() > 0) {

				for (MultipartFile mf : lists) {
					String originFileName = mf.getOriginalFilename();
					// String originalFileExtension =
					// originFileName.substring(originFileName.lastIndexOf("."));
					String safeFile = path + "[Suju]" + originFileName;
					urlsList.add(safeFile);
					try {
						mf.transferTo(new File(safeFile));
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return urlsList;
	}

	public void getExcelForm(HttpServletResponse response) throws IOException, InvalidFormatException {
		OutputStream ExcelStream = null;
		XSSFWorkbook workbook = null;

		String path = BasicPath+"forms\\DesignForm.xlsx";
		File file = new File(path);
		System.out.println("File exist : " + file.exists());
		if (file.exists())
			workbook = new XSSFWorkbook(file);
		ExcelStream = response.getOutputStream();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");
		workbook.write(ExcelStream);
		workbook.close();
		ExcelStream.close();
	}

	public XSSFWorkbook getBasicExcelForm(String filename) throws InvalidFormatException, IOException {

		XSSFWorkbook workbook = null;
		if (filename == null)
			filename = "DesignForm.xlsx";

		String path = BasicPath + filename;
		File file = new File(path);
		System.out.println("File exist : " + file.exists());
		if (file.exists())
			workbook = new XSSFWorkbook(file);

		return workbook;
	}

	public ResponseEntity<byte[]> imageView(String imagename, String url) throws IOException {
		InputStream imageStream = null;
		String path = BasicPath + imagename;
		if (url!=null) {
			path = BasicPath + url + imagename;
		}
		byte[] imageByteArray = null;
		System.out.println("Image Resource Request:" + path);
		File file = new File(path);
		System.out.println("File exist : " + file.exists());
		if (file.exists())
			imageStream = new FileInputStream(path);

		imageByteArray = IOUtils.toByteArray(imageStream);
		imageStream.close();
		return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
	}

	public String base64ImageView(String imagename, String url) throws IOException {
		InputStream imageStream = null;
		String path = BasicPath  + imagename;
		if (url!=null) {
			path = BasicPath + url + imagename;
		}
		if (imagename.contains("C:") || imagename.contains("D:"))
			path = imagename;
		byte[] imageByteArray = null;
		System.out.println("Image Resource Request:" + imagename);
		File file = new File(path);
		System.out.println("File exist : " + file.exists());
		if (file.exists())
			imageStream = new FileInputStream(path);

		imageByteArray = IOUtils.toByteArray(imageStream);
		imageStream.close();
		return Base64.getEncoder().encodeToString(imageByteArray);
	}
}
