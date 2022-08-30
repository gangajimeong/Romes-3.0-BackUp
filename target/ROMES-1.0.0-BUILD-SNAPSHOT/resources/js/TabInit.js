/**
 * 
 */

import { createTab, reloadTabPage, checkCurrentPage } from "./TabFuncs.js"
// url 변경하기
const urlList = [
	
	{ 'title': "공통코드", "url": "commonCode" },
	{ "title": "부서관리", "url": "Division" },
	{ "title": "거래처관리", "url": "companyManagement" },
	{ "title": "시공사관리", "url": "ConstructionCompany" },  
	{ "title": "브랜드관리", "url": "BrandManagement" }, 
	{ "title": "생산라인관리", "url": "WorkingLine" }, 
	{ "title": "원/부자재관리", "url": "MaterialMaster" },
	{ "title": "로케이션마스터", "url": "LocationMaster" },
	{ "title": "공정마스터", "url": "ProcessMaster" },
	{ "title": "불량코드", "url": "ErrorCode" },
	{ "title": "제품정보", "url": "productMaster" },
	
	
	{ "title": "수주/작업지시", "url": "OrderInfo"},
	{ "title": "수주/작업지시 확인", "url": "WorkConfirmation"},
	{ "title": "생산계획", "url": "ProductionPlan"},
	{ "title": "시공지시", "url": "ConstructionOrder"},
	{ "title": "작업보고서", "url": "WorkReport"},
	{ "title": "시뮬레이션", "url": "Simulation"}, 	
	{ "title": "설비별불량", "url": "EquipmentDefect"},	
	{ "title": "CAPA", "url": "Capa"},	
	{ "title": "KPI", "url": "kpipage"},
	
	{ "title": "입고/출고", "url": "SRHistoryList"},
	{ "title": "재고현황", "url": "ProductStock"},
	{ "title": "발주관리", "url": "OrderHistory"},
	
	{ "title": "출하실적", "url": "ShippingResult"},
	{ "title": "시공실적", "url": "ConstructionPerformance"},
	{ "title": "제품/시공불량내역", "url": "ProductDefect"},
	
	{ "title": "작업지시모니터링", "url": ""},
	
	{ "title": "사용자관리", "url": "UserManagement" },
	{ "title": "사용자정보관리", "url": "UserInfo" },
	{ "title": "로그기록", "url": "LogInfo" }, 
	{ "title": "시스템정보관리", "url": "Setting" }, 
	
	{ "title": "설정", "url": "Setting" }, 
	
	
]

function TabInit() {


	const reload = false;
	const state = false;
	urlList.forEach(function(data, idx) {
		const tabList = document.getElementsByClassName("tabAdd");
		tabList[idx].addEventListener("click", function() {

			createTab(data.title, data.url, state, reload);
		});

	});
	if (sessionStorage.length != 0) {
		console.log('sessionStorage.length: '+ sessionStorage.length)
		reloadTabPage()
		if (localStorage.length != 0) {
			checkCurrentPage()
		}

	}


}

document.addEventListener("DOMContentLoaded", function() {
	TabInit();
})




