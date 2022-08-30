package com.company.ROMES.Dto;

import java.io.Serializable;

import org.json.simple.JSONObject;

import com.company.ROMES.entity.WorkOrderInfo;

import lombok.Getter;
@Getter
public class WorkOrderDto implements Serializable {
	private int id;
	private String product;
	private String remark;
	private String size;
	private String back;
	private String coating;
	private String company;
	private String branch;
	private int count;
	private String manufacture;
	private String printer;
	private boolean emergency;
	private String url;
	private String user;
	
	public WorkOrderDto(WorkOrderInfo info) {
		this.id = info.getId();
		this.product = info.getProduct();
		this.remark = info.getRemark();
		this.size = info.getSize();
		this.back = info.isBack() == true?"사용":"";
		this.coating = info.isCoating() == true?"사용":"";
		this.company = info.getOrderInfo().getOrderCompany().getCompanyName();
		this.branch = info.getBranch()==null?"No info":info.getBranch().getTitle();
		this.count = info.getPlanCount();
		this.printer = info.getPrinter()==null?"No info":info.getPrinter().getLine();
		this.manufacture = info.getManufacturing();
		this.emergency = info.isEmergency();
		this.url = info.getDesign()==null?"/no_image.png":info.getDesign().getUrl()==null?"/no_image.png":info.getDesign().getUrl();
		this.user = info.getOrderInfo().getUser() == null? "No info" : info.getOrderInfo().getUser().getName();  
	}
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		object.put("id", this.id);
		object.put("product", this.product);
		object.put("remark", this.remark);
		object.put("size", this.size);
		object.put("back", this.back);
		object.put("coating", this.coating);
		object.put("company", this.company);
		object.put("branch", this.branch);
		object.put("count", this.count);
		object.put("manufacture", this.manufacture);
		object.put("printer", this.printer);
		object.put("emergency", this.emergency);
		object.put("url", this.url);		
		object.put("user", this.user);
		return object;
	}
}
