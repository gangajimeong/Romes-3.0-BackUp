package com.company.ROMES.Controller.StandardInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Standard_TypeCode {
	
	public final static String[] LocationTypeCodeName = {"2000","위치","LT"};
	public final static String[] MaterialGroup ={"1112","자재그룹","MG"};
	public final static String[] MaterialFeature = {"0001","자재특성","MF"};
	public final static String[] MaterialType = {"1111","자재분류","MT"};
	public final static String[] ProductPackageUnit = {"1115","제품포장단위","PPU"};
	public final static String[] InspectionType = {"1005","검사유형","IT"};
	public final static String[] MaintenanceType = {"3554","생산설비","MT"};
	public final static String[] QuantitiesType = {"3897","수량 단위","QT"};
	
	public List<String[]>getTypeList(){
		List<String[]>typeList= new ArrayList<>();
		typeList.add(LocationTypeCodeName);
		typeList.add(MaterialGroup);
		typeList.add(ProductPackageUnit);
		typeList.add(MaterialFeature);
		typeList.add(MaterialType);
		typeList.add(MaintenanceType );
		typeList.add(QuantitiesType);
		typeList.add(InspectionType);
		return typeList;	
	}
	public String getGenNum() {
		String tmp =null;
		LocalDateTime number = LocalDateTime.now();
		tmp = String.valueOf(number.getYear()).substring(2,4);
		tmp+= String.valueOf(number.getMonthValue());
		tmp+= String.valueOf(number.getDayOfMonth());
		tmp+= String.valueOf(number.getHour());
		tmp+= String.valueOf(number.getMinute());
		tmp+= String.valueOf(number.getSecond());
		
		return tmp;
	}
}
