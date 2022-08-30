/**
 * 
 */
import { InfoSearch, FilterkeyWord, InfoSearchForNoneCheck, setFilterEvt } from "./SearchFuncs.js"
import { BtnFunctions } from "./ButtonEvt.js"
import { EvtToLocation } from "./locationEvents.js"
import { OrderHistoryEvt } from "./Orderhistory.js"
import ReceivedOrder from "./ReceivedOrder.js"
import { ConstructionPerformance } from "./ConstructionPerfomance.js"
import { Srhistory } from "./Srhistory.js";
import ProductStock from "./ProductStock.js";
import ProductionPlan from "./ProductionPlan.js";
import WorkOrder from "./WorkOrder.js";
import documentForApproval from "./DocumentForApproval.js";
import companyManagement from "./companyManagement.js";
import BrandManagement from "./BrandManagement.js";
import CapaEvent from './CapaEvent.js';
import KpiPageEvent from './KpiPageEvent.js';
import LogEvents from './LogEvents.js';
import orderinfoEvt from './OrderInfoForKS.js';
import OkEvent from './OkEvent'; // ok event 처리 js test 페이지
import ConstructionOrder from './ConstructionOrder.js';
import Simulation from './Simulation.js';
import WorkReport from './WorkReport.js';
import ConstructionManagement from './constructionManagement.js';
import ShippingResult from './ShippingResult.js';
import EquipmentDefect from './EquipmentDefect.js';
import ProductDefect from './ProductDefect.js';
import Setting from './Setting.js';
import ProcessMaster from './ProcessMaster.js';
import MaterialBacode from './MaterialBacode.js';
import DivisionManagement from './DivisionManagement.js';
import UserInfo from './UserInfo.js';
import WorkingLine from './WorkingLine.js';
import MaterialMaster from  './MaterialMaster.js';

export class Events {

	CommonCodeEvt() {

		// 상위 검색 셀렉트 박스 옵션 채우는 메소드
		InfoSearch("commonCodeColName", "commonCodeInfoSearch");
		const create = document.getElementsByClassName("CommonCodeCreate")[0]
		const update = document.getElementsByClassName("CommonCodeUpdate")[0]
		const data = new BtnFunctions;
		if (create.classList.contains("active")) data.getTypes("regi-select", "register-control");
		create.addEventListener('click', function() {

			data.getTypes("regi-select", "register-control");
		})
		//if(update.classList.contains("active"))data.getTypes("regi-u-select","register-u");
		update.addEventListener('click', function() {
			data.getTypes("regi-u-select", "register-u");
		})

		data.createButtonEvt("register-control", "CommonCode")
		data.updateButtonEvt("CommonCode", "CommonCode-update-list", "register-u", "U");
		data.deleteButtonEvt("delete-checkBox", "CommonCode", "CommonCode-delete-list", "D")
		// search table information
		document.getElementById("CommonCodeSearch").addEventListener("keyup", function() {
			FilterkeyWord('CommonCodeSearch', 'commonCodeTable', "commonCodeInfoSearch");
		})
	}

	DownTimeEvt() {
		InfoSearch("DownTimeColName", "DownTimeInfoSearch");

		const data = new BtnFunctions;
		data.createButtonEvt("DT-control", "DownTime");
		data.updateButtonEvt("DownTime", "DownTime-update-list", "DT-u-control", "U");
		data.deleteButtonEvt("DT-delete-checkBox", "DownTime", "DownTime-delete-list", "D");
		data.timeValueCheck();

		document.getElementById("downTimeSearch").addEventListener("keyup", function() {
			FilterkeyWord("downTimeSearch", "downTimeTable", "DownTimeInfoSearch");
		});

	}

	companyManagementEvt() {

		InfoSearch("companyManagementColName", "companyManagementInfoSearch");
		const data = new companyManagement
		data.Buttonevt();

		document.getElementById("companySearch").addEventListener("keyup", function() {
			FilterkeyWord('companySearch', 'companyManagementTable', 'companyManagementInfoSearch');
		})

	}
	//브랜드관리
	brandManagementEvt() {

		InfoSearch("BrandManagementColName", "BrandManagementInfoSearch");
		const data = new BrandManagement
		data.Buttonevt();

		document.getElementById("BrandSearch").addEventListener("keyup", function() {
			FilterkeyWord('BrandSearch', 'BrandManagementTable', 'BrandManagementInfoSearch');
		})
		
		InfoSearchForNoneCheck("BrandManagement2ColName", "DocumentForWorkOrderSearch");
		document.getElementById("DocumentForWorkOrderInputSearch").addEventListener("keyup", function() {
			FilterkeyWord('DocumentForWorkOrderInputSearch', 'BranchBody', 'DocumentForWorkOrderSearch');
		})
		
	}
	//완제품 관리
	prodctuctMaster() {
		InfoSearch("productMasterColName", "productMasterInfoSearch");
		const PMData = new BtnFunctions;
		const creates = document.getElementsByClassName("ProductCreate")[0];
		const updates = document.getElementsByClassName("ProductUpdate")[0];
		if (creates.classList.contains('active')) //PMData.getSelectOptData("Product-Select-c");
		updates.addEventListener('click', function() {
			//PMData.getSelectOptData("Product-Select-u");
		})
		PMData.CreateProduct();
		PMData.getDetail("Product");
		PMData.createButtonEvt("Product-c", "Product")
		PMData.updateButtonEvt("Product", "Product-update-list", "Product-u", "U");
		PMData.deleteButtonEvt("P-delete-checkBox", "Product", "Product-delete-list", "D");
		document.getElementById("ProductSearch").addEventListener("keyup", function() {
			FilterkeyWord('ProductSearch', 'ProductTable', 'productMasterInfoSearch');
		})
		
		const ExcelImport = document.getElementById('ProductExcel');
		ExcelImport.addEventListener('change', PMData.readExcel);
	}
	//원/부자재 관리
	MaterialMaster() {
		InfoSearch("MaterialMasterColName", "MaterialMasterInfoSearch");
		
		const Barcode = new MaterialBacode;
		Barcode.printTrEvt();
		Barcode.BarcodeModal();

		document.getElementById("MaterialSearch").addEventListener("keyup", function() {
			FilterkeyWord('MaterialSearch', 'MaterialTable', 'MaterialMasterInfoSearch');
		})
		const e = new MaterialMaster;
		e.UDevt();
	}
	ErrorCode() {
		InfoSearch("ErrorCodeColName", "ErrorCodeInfoSearch");

		const EData = new BtnFunctions;
		EData.createButtonEvt("ErrorCode-c", "ErrorCode")
		EData.updateButtonEvt("ErrorCode", "ErrorCode-update-list", "ErrorCode-u", "U");
		EData.deleteButtonEvt("E-delete-checkBox", "ErrorCode", "ErrorCode-delete-list", "D");

		document.getElementById("ErrorCodeSearch").addEventListener("keyup", function() {
			FilterkeyWord('ErrorCodeSearch', 'ErrorCodeTable', 'ErrorCodeInfoSearch');
		})
	}

	WorkingLine() {
		InfoSearch("WorkingLineColName", "WorkingLineInfoSearch");

//		const WData = new BtnFunctions;
//		WData.createButtonEvt("WorkingLine-c", "WorkingLine")
//		WData.updateButtonEvt("WorkingLine", "WorkingLine-update-list", "WorkingLine-u", "U");
//		WData.deleteButtonEvt("WL-delete-checkBox", "WorkingLine", "WorkingLine-delete-list", "D");

		document.getElementById("WorkingLineSearch").addEventListener("keyup", function() {
			FilterkeyWord('WorkingLineSearch', 'WorkingLineTable', 'WorkingLineInfoSearch');
		})
		const e = new WorkingLine;
		e.UDEvt();
	}
	Division() {
		InfoSearch("DivisionColName", "DivisionInfoSearch");

		const DData = new BtnFunctions;
		DData.createButtonEvt("Division-c", "Division")
		DData.updateButtonEvt("Division", "Division-update-list", "Division-u", "U");
		DData.deleteButtonEvt("Div-delete-checkBox", "Division", "Division-delete-list", "D");
		const e = new DivisionManagement;
		e.trEvt(); 
		const employee = document.getElementsByClassName("DivisionEmployee")[0];
		employee.addEventListener("click",e.clearTable);
		e.setEmployee();

		document.getElementById("DivisionSearch").addEventListener("keyup", function() {
			FilterkeyWord('DivisionSearch', 'DivisionTable', 'DivisionInfoSearch');
		})
	}
	LocationMaster() {
		const locdata = new EvtToLocation;
		//input_id,labelClassName,img_id

		locdata.uploadEvts("file-regi", "file-select-name", "preview");
		locdata.uploadEvts("file-regi-u", "file-select-name-u", "preview-u");
		locdata.deleteTab();
		locdata.drawLocationOption();
		locdata.LocationMainEvent();
		locdata.printTrEvt();
		locdata.MainEvtToTr();
		
		InfoSearchForNoneCheck("LocationMasterColName", "LocationMasterInfoSearch");			
		document.getElementById("LocationSearch").addEventListener("keyup", function() {
			FilterkeyWord('LocationSearch', 'LocationTable', "LocationMasterInfoSearch");
		})
	}

	userManagementEvt() {
		InfoSearchForNoneCheck("UserManagementColName", "UserInfoSearch");
		document.getElementById("UserSearch").addEventListener("keyup", function() {
			FilterkeyWord('UserSearch', 'UserTable', 'UserInfoSearch');
		})
	}

	OrderHistory() {
		const evt = new OrderHistoryEvt
		evt.selectDoc();
		evt.addOrder();
		evt.loadProduct();
		evt.loadCompany();
		evt.addUpdateOrder();
		evt.ordersUpdate();
		evt.addOrderProducts();
		evt.deleteDocButton();
		evt.predateNotInput();
	}
	OrderInfo() {
	//	const evt = new ReceivedOrder
	//	evt.setExcelImportEvt();
		const ord = new orderinfoEvt;
		ord.evt();
	/*	evt.register()
		evt.fileInputEvt();*/
		/*const evt = new  OrderInfo
		evt.updateCheckButton();
		evt.stepEvtForm();
		evt.fileInputEvt()
		evt.getAddr()
		evt.loadProduct();
		evt.addOrderProductsButton();
		evt.loadCompany()
		evt.registerButton()
		evt.loadTrEvt();
		evt.detailSample();
		evt.constructionPlanRegi();
		evt.loadConstructionDivisionList();
		evt.orderCreateClick();*/
		InfoSearchForNoneCheck("OrderInfoColName", "OrderInfoSearch");
		document.getElementById("OrderInfoInputSearch").addEventListener("keyup", function() {
			FilterkeyWord('OrderInfoInputSearch', 'OrderHistoryContents', 'OrderInfoSearch');
		})
		
		InfoSearchForNoneCheck("OrderInfo2ColName", "OrderInfoSearch2");
		document.getElementById("OrderInfoInputSearch2").addEventListener("keyup", function() {
			FilterkeyWord('OrderInfoInputSearch2', 'OrderHistoryContents2', 'OrderInfoSearch2');
		})
		
		setFilterEvt("orderinfoPreDate","orderinfoLastDate","OrderHistoryContents", 5);
		
		ord.uploadEvts("sample-regi", "sample-select-name", "noImage");

	}
	ConstructionPerformance() {
		const evt = new ConstructionPerformance
		evt.setFilterEvt();
		evt.setTableClickEvt();
		evt.detailImage();
	}
	SRHistoryEvt() {
		const evt = new Srhistory
		evt.setSearchEvt();
		evt.setTableColor();
		
		setFilterEvt("srhistory-PreDate","srhistory-LastDate","srhistory-table-Contents", 7);
	}
	ProductStockEvt() {
		const evt = new ProductStock;
		evt.initTableEvt();
		evt.setFilterEvt();
		evt.changeColor();
	}
	ProductionPlan() {
		const evt = new ProductionPlan;
		//evt.setCreateModal();
		evt.stepEvtForm();
		evt.setInfoEvt();
//		evt.DetailForProductionPlanEvt();
//		evt.TrToInput();
//		evt.updateTabClick();
		
		InfoSearch("ProductionPlanColName", "ProductionPlanInfoSearch");
		document.getElementById("ProductionPlanInputSearch").addEventListener("keyup", function() {
			FilterkeyWord('ProductionPlanInputSearch', 'ProductionPlanContents', 'ProductionPlanInfoSearch');
		})
	}
	WorkOrder() {
		const evt = new WorkOrder;
		/*
		evt.setTableCollapse();
		evt.setDIalogEvent();
		evt.setFilterEvt();
		evt.webSocketTest();
		evt.setWorkOrderDetail();*/
		evt.connectSse();
		evt.evt();
		evt.setmodal();
		//자동새로고침
		/*
		setTimeout(function(){
			location.reload();
			console.log("reload");
		},10000); */
	}
	/*ApprovalInfo(){
		const evt = new ApprovalInfo;
		evt.dragAndDropEvt("#ApprovalLists")
		evt.drawDivisionSelect();
		evt.selectEvts();
	}*/
	DocumentForApproval() {
		const evt = new documentForApproval;
		evt.previewEvt();
		evt.myList();
		evt.trToUpdate();
		evt.excelButton();
		evt.drawDivisionSelect();
		evt.selectEvts();
		evt.drawOrderInfo();
		evt.uploadDesignButton();
		evt.registerButton();
		evt.updateButton();
		evt.custumerForRequest();
		evt.deleteButton();
		evt.preConFirmDocEvt();
		evt.preMyDetailEvt();
		InfoSearchForNoneCheck("DocumentForWorkOrderColName", "DocumentForWorkOrderSearch");
		document.getElementById("DocumentForWorkOrderInputSearch").addEventListener("keyup", function() {
			FilterkeyWord('DocumentForWorkOrderInputSearch', 'DocumentForWorkOrderContents', 'DocumentForWorkOrderSearch');

		})
		InfoSearchForNoneCheck("DocumentForWorkOrder2ColName", "DocumentForWorkOrderSearch2");
		document.getElementById("DocumentForWorkOrderInputSearch2").addEventListener("keyup", function() {
			FilterkeyWord('DocumentForWorkOrderInputSearch2', 'DocumentForWorkOrderTable2', 'DocumentForWorkOrderSearch2');

		})
	}
	LogEvt(){
		
		const evt = new LogEvents;
		evt.initBtnEvt();
	}
	
	KpiEvt(){
		const evt = new KpiPageEvent;
		evt.initBtnEvt();
	}
	CapaEvt(){
		const evt = new CapaEvent;
		evt.initBtnEvt();
	}
	
	ConstructionOrder(){
		const evt = new ConstructionOrder;
		evt.modalToggle();
		
		InfoSearch("ConstructionOrderColName", "ConstructionOrderInfoSearch");
		document.getElementById("ConstructionOrderInputSearch").addEventListener("keyup", function() {
			FilterkeyWord('ConstructionOrderInputSearch', 'ConstructionOrderContents', 'ConstructionOrderInfoSearch');
		})
		InfoSearchForNoneCheck("ConstructionOrder2ColName", "ConstructionOrder2InfoSearch");
		document.getElementById("ConstructionOrderInputSearch2").addEventListener("keyup", function() {
			FilterkeyWord('ConstructionOrderInputSearch2', 'ConstructionOrderContents2', 'ConstructionOrder2InfoSearch');

		})
	}
	
	Simulation(){
		const evt = new Simulation;
		evt.a();
	}
	WorkReport(){
		const evt = new WorkReport;
		evt.WorkReport();

	}
	construction(){
		const evt = new ConstructionManagement;
		evt.Btnevt();
		
		InfoSearch("ConstructionCompanyColName", "ConstructionCompanyInfoSearch");
		document.getElementById("ConstructionCompanySearch").addEventListener("keyup", function() {
			FilterkeyWord('ConstructionCompanySearch', 'ConstructionCompanyTable', "ConstructionCompanyInfoSearch");
		})
	}
	ShippingResult() {
		const e = new ShippingResult;
		e.a();
		
		document.getElementById("ShippingResultSearch").addEventListener("keyup", function() {
			FilterkeyWord('ShippingResultSearch', 'ShippingResultContents', "ShippingResultInfoSearch");
		})
		setFilterEvt("ShippingResult-PreDate","ShippingResult-LastDate","ShippingResultContents", 7);
	}
	EquipmentDefect() {
		const e = new EquipmentDefect;
		e.UDevt();
		
		InfoSearch("EquipmentDefectColName", "EquipmentDefectInfoSearch");
		document.getElementById("EquipmentDefectSearch").addEventListener("keyup", function() {
			FilterkeyWord('EquipmentDefectSearch', 'EquipmentDefectContents', "EquipmentDefectInfoSearch");
		})
	}
	ProductDefect(){
		const e = new ProductDefect;
		e.a();
		
		InfoSearch("ProductDefectColName", "ProductDefectInfoSearch");
		document.getElementById("ProductDefectSearch").addEventListener("keyup", function() {
			FilterkeyWord('ProductDefectSearch', 'ProductDefectContents', "ProductDefectInfoSearch");
		})
		InfoSearchForNoneCheck("ProductDefect2ColName", "ProductDefectInfoSearch2");
		document.getElementById("ProductDefectSearch2").addEventListener("keyup", function() {
			FilterkeyWord('ProductDefectSearch2', 'ProductDefectContents2', "ProductDefectInfoSearch2");
		})
		
	}
	Setting(){
		const e = new Setting;
		e.a();
	}
	ProcessMaster(){
		const e = new ProcessMaster;
		e.updateEvt();
		
//		const ProcessM = new BtnFunctions;
//		ProcessM.createButtonEvt("ProcessMaster-c", "Process")
//		ProcessM.updateButtonEvt("Process", "ProcessMaster-update-list", "ProcessMaster-u", "U");
//		ProcessM.deleteButtonEvt("ProcessMaster-delete-checkBox", "Process", "ProcessMaster-delete-list", "D");
		
	}
	UserInfo(){
		const e = new UserInfo;
		e.userInfo();
	}

	// Ok Event 처리 JS
	OkEvent() {
		const e = new OkEvent;

	}
}