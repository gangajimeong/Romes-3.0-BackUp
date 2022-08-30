package com.company.ROMES.Controller.ProductionManagement;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.company.ROMES.functions.webUpload;
import com.company.ROMES.interfaces.service.ProductionM.DocumentForWorkOrderService;

@Controller
public class DocumentForWorkOrder {
	
	@Autowired
	webUpload upload;
	
	@Autowired
	DocumentForWorkOrderService service;
	
	@RequestMapping(value = "DocumentForWorkOrder", method = RequestMethod.GET)
	public String DocumentForWorkOrderHome(Model model) {
		JSONArray up = new JSONArray();
		JSONArray down = new JSONArray(); 
		
		up = service.selectMakingDesignDoc();
		down = service.selectReadyApprovalDoc();
		model.addAttribute("makingList", up);
		model.addAttribute("readyDocs", down);
		model.addAttribute("mylist", service.selectForGrant());
		
		return "ProductionM/DocumentForWorkOrder";
	}
	
	@ResponseBody
	@GetMapping(value = "/imageViewForExcel", produces = { MediaType.IMAGE_JPEG_VALUE, "text/plain;charset=UTF-8" })
	public String documentForImage(@RequestParam("imagename") String imagename) throws IOException {
		String decodeResult = URLDecoder.decode(imagename, "UTF-8");
		System.out.println(decodeResult);
		return upload.base64ImageView(decodeResult, null);
	}
	@ResponseBody
	@RequestMapping(value = "/getExcelForm",method = RequestMethod.GET)
	public void getExcelForm(HttpServletResponse response) throws IOException, InvalidFormatException{
		upload.getExcelForm(response);
	}
	@ResponseBody
	@GetMapping(value = "/imageViewForDoc", produces = { MediaType.IMAGE_JPEG_VALUE, "text/plain;charset=UTF-8" })
	public ResponseEntity<byte[]> imageViewForDoc(@RequestParam("imagename") String imagename) throws IOException {
		return upload.imageView(imagename,null);
	}
	@ResponseBody
	@RequestMapping(value = "/selectDocForId",method = RequestMethod.GET)
	public JSONArray selectDocForId(@RequestParam(name = "id")int id) {
		return service.selectDocToId(id);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/orderInfosForDocument",method = RequestMethod.GET)
	public JSONArray orderInfosForDocument(){
		return service.orderInfos();
	}
	
	@ResponseBody
	@RequestMapping(value = "/registerDesignDocument",method = RequestMethod.POST)
	public JSONObject registerDesignDocument(@RequestParam HashMap<Object, Object>datas,@RequestParam(name = "Design",required = false)MultipartFile file ) throws InvalidFormatException, IOException{
		JSONObject ret = new JSONObject();
		ret = service.registerDesignDocument(datas, file);
		return ret;
	}
	@ResponseBody
	@RequestMapping(value = "/updateForDocument",method = RequestMethod.POST)
	public JSONObject updateForDocument(@RequestParam HashMap<Object, Object>datas,@RequestParam(name = "Design",required = false) MultipartFile file ) throws InvalidFormatException, IOException{
		
		JSONObject ret = new JSONObject();
		ret = service.updateDesignDocument(datas, file);
		return ret;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteForDocument",method = RequestMethod.GET)
	public boolean deleteForDocument(@RequestParam(name = "ids")List<Integer>ids ){
		
		boolean ret = false;
		ret = service.deleteDesignDocument(ids);
		return ret;
	}
	@ResponseBody
	@RequestMapping(value = "/approvalForCustomer",method = RequestMethod.GET)
	public JSONObject approvalForCustomer(@RequestParam(name = "id")int id ){
		
		JSONObject ret = new JSONObject();
		ret = service.confirmCustomer(id);
		return ret;
	}
	@ResponseBody
	@RequestMapping(value = "/getDocInfo",method = RequestMethod.GET)
	public JSONObject getDocInfo(@RequestParam(name = "id")int id ){
		
		JSONObject ret = new JSONObject();
		ret = service.getDocInfo(id);
		return ret;
	}
	
}
