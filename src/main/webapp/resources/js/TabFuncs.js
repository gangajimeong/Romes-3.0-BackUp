/**
 * 
 */
import { Events } from "./Events.js"

export function createTab(pageName, openUrl, state, reload) {
	let bool = state
	if (state == true) {
		bool = state
	} else {
		bool = OpenedPageCheck(pageName);
	}

	if (bool == true) {
		const tabLinkDrawStandardTag = document.getElementById("tablinkList");
		const tabContentDrawStandardTag = document.getElementById("tabcontentsList");

		//tablink
		const newTab = document.createElement("div");
		newTab.className = "tablink"
		newTab.id = openUrl
		newTab.textContent = pageName;
		tabLinkDrawStandardTag.appendChild(newTab);


		const newbtn = document.createElement('button');
		newbtn.type = 'button';
		newbtn.className = 'close btn btn-sm'
		newTab.appendChild(newbtn);


		const newSpan = document.createElement('span');
		newSpan.className = 'closeIcon';
		newSpan.setAttribute("aria-hidden", "true");
		newSpan.innerHTML = "&times;"
		newbtn.appendChild(newSpan);


		//tabcontents
		const newTabContents = document.createElement('div');
		newTabContents.className = 'tabcontent mt-0'
		newTabContents.id = openUrl + "Content"
		newTabContents.style.display = "none";
		tabContentDrawStandardTag.appendChild(newTabContents);


		const Key = StoredTab(newTab, newTabContents, openUrl)
		OpenTabContent(newTab, newTabContents, openUrl);
		if (reload == false) {
			storedOpenPage(Key, openUrl)
		}

		openPage(newTab, newTabContents, openUrl, Key)
		closePage(newbtn, newTab, newTabContents, Key)
	};

}

function openPage(tablink, newtabcontent, openUrl, Key) {

	tablink.addEventListener("click", function(e) {
		e.preventDefault();

		OpenTabContent(tablink, newtabcontent, openUrl);
		storedOpenPage(Key, openUrl)


	});

}
function OpenTabContent(tablink, newtabcontent, openUrl) {
	console.log(tablink)
	let a;
	const tabcontent = document.getElementsByClassName("tabcontent");
	for (a = 0; a < tabcontent.length; a++) {
		tabcontent[a].style.display = "none";
	}
	const tablinks = document.getElementsByClassName("tablink");
	for (a = 0; a < tablinks.length; a++) {
		tablinks[a].style.backgroundColor = "";
		tablinks[a].style.borderBottom = "";
		tablinks[a].style.borderColor = "";
		tablinks[a].style.color = "white";

	}

	tablink.style.backgroundColor = "#D8EBF9";
	tablink.style.borderBottom = "solid";
	tablink.style.borderColor = "#3C5B92";
	tablink.style.color = "black";
	newtabcontent.style.display = "block";
	newtabcontent.style.backgroundColor = "#D8EBF9";


	$(newtabcontent).load(openUrl + ".do #main", function() { AssignEvtFunc(openUrl) });
}
function closePage(btn, tablink, tabcontent, Key) {

	btn.addEventListener("click", function(e) {
		e.stopPropagation();

		const obj = localStorage.getItem('Open');
		const ParseData = JSON.parse(obj);
		const StateOpen = ParseData.OpenTab;
		const firstTAB = sessionStorage.key(0);
		console.log(StateOpen)
		if (Key != firstTAB && Key == StateOpen) {
			preTabOpen(Key)
		}
		if (sessionStorage.length != 0) {
			sessionStorage.removeItem(Key);
		}
		if (sessionStorage.length === 0) {
			localStorage.clear();

		}
		tablink.remove();
		tabcontent.remove();


	});


}
function preTabOpen(Key) {

	let tabNum = Key.substring(3);
	let preTabNum;
	let obj, preTab;
	let openUrl;
	if (tabNum != 0) {
		preTabNum = tabNum - 1;
		preTab = "tab" + preTabNum;
		if (!!sessionStorage.getItem(preTab)) {
			obj = JSON.parse(sessionStorage.getItem(preTab))
			openUrl = obj.url;
		}


	}
	storedOpenPage(preTab, openUrl)
	reOpen(preTab)
}


function StoredTab(tablink, tabcontent, openUrl) {
	const tabIdx = $(tablink).index(this);
	const tabName = $(tablink).text().replace('×', '');
	const stablink = $(tablink).attr('id');
	const stabcontent = $(tabcontent).attr('id');
	const url = openUrl
	const state = true;

	const storedInfo = { "tabName": tabName, "tablinkID": stablink, "tabcontentID": stabcontent, "url": url, "state": state }
	const Key = "tab" + tabIdx
	sessionStorage.setItem(Key, JSON.stringify(storedInfo));
	return Key;


}
// reload tabPage
export function reloadTabPage() {
	let tabIdx, jsonTab, tabName, reUrl, state, reload
	for (let i = 0; i < sessionStorage.length; i++) {
		//tab 이름 포함 json만 가져오기
		tabIdx = sessionStorage.getItem("tab" + i)
		console.log(tabIdx + ":" + !!tabIdx)
		if (!!tabIdx) {
			jsonTab = JSON.parse(tabIdx);
			tabName = jsonTab.tabName
			reUrl = jsonTab.url
			state = jsonTab.state
			reload = true;
			createTab(tabName, reUrl, state, reload)
		}

	}
}
// 열어둔 탭 저장
export function reOpen(Opentab) {
	console.log(Opentab)
	const Basic = sessionStorage.getItem(Opentab)
	const tabInfo = JSON.parse(Basic)
	const tablinkss = tabInfo.tablinkID
	const tabContents = tabInfo.tabcontentID
	const url = tabInfo.url
	let tablink
	let a, tablinks, oritabcontent
	let newtabcontent
	tablink = document.getElementById(tablinkss);
	newtabcontent = document.getElementById(tabContents)

	oritabcontent = document.getElementsByClassName("tabcontent");
	for (a = 0; a < oritabcontent.length; a++) {
		oritabcontent[a].style.display = "none";
	}

	tablinks = document.querySelector(".tablink");
	for (a = 0; a < tablinks.length; a++) {
		tablinks[a].style.backgroundColor = "";
		tablinks[a].style.borderBottom = "";
		tablinks[a].style.borderColor = "";
		tablinks[a].style.color = "white";
		tablinks[a].style.borderColor = "#3C5B92";

	}

	tablink.style.backgroundColor = "#D8EBF9";
	tablink.style.borderBottom = "solid";
	tablink.style.borderColor = "#3C5B92";
	tablink.style.color = "black";
	newtabcontent.style.display = "block";
	newtabcontent.style.backgroundColor = "#D8EBF9";

	/*탭 url 및 function load*/
	/*$(newtabcontent).load(url + ".do #main", function() { CommonCodeEvt() });*/

}

export function OpenedPageCheck(pagename) {
	let tabIdx, jsonTab, bool;
	bool = true;
	if (sessionStorage.length != 0) {
		for (let i = 0; i < sessionStorage.length; i++) {
			tabIdx = sessionStorage.getItem("tab" + i)
			jsonTab = JSON.parse(tabIdx);
			if (jsonTab.tabName == pagename) {
				alert("이미 열린페이지 입니다.")
				bool = false;
			}
		}
		return bool;
	} else {
		bool = true;
		return bool;
	}

}
export function storedOpenPage(idx, openUrl) {
	const OpenKey = idx
	const bool = true;
	const url = openUrl;
	const openObj = { "OpenTab": OpenKey, "state": bool, "url": url }
	localStorage.setItem("Open", JSON.stringify(openObj));
}
export function checkCurrentPage() {
	let openInfo = localStorage.getItem("Open");
	let openTab = JSON.parse(openInfo);
	let tabKey = openTab.OpenTab
	reOpen(tabKey);


}
function AssignEvtFunc(url) {
	const evt = new Events();
	switch (url) {
		case "commonCode": evt.CommonCodeEvt(); break;
		case "companyManagement": evt.companyManagementEvt(); break;
		case "BrandManagement": evt.brandManagementEvt();  break;
		case "MaterialMaster": evt.MaterialMaster(); break;
		case "DownTime": evt.DownTimeEvt(); break;
		case "productMaster": evt.prodctuctMaster(); break;
		case "purchaseManagement": evt.MaterialMaster(); break;
		case "ApprovalInfo": evt.ApprovalInfo(); break;
		case "DocumentForWorkOrder": evt.DocumentForApproval(); break;
		case "ErrorCode": evt.ErrorCode(); break;
		case "WorkingLine": evt.WorkingLine(); break;
		case "Division": evt.Division(); break;
		case "LocationMaster": evt.LocationMaster(); break;
		case "UserManagement": evt.userManagementEvt(); break;
		case "SRHistoryList": evt.SRHistoryEvt(); break;
		case "ConstructionPerformance": evt.ConstructionPerformance(); break;
		case "ProductStock": evt.ProductStockEvt(); break;
		case "ProductionPlan": evt.ProductionPlan(); break;
		case "WorkOrder": evt.WorkOrder(); break;
		case "OrderInfo": {
			
			let today = new Date();

			let year = today.getFullYear(); // 년도
			let month = today.getMonth() + 1;  // 월
			let date = today.getDate();  // 날짜
			let day = today.getDay();  // 요일


			$('input[name="daterange"]').daterangepicker({
				"locale": {
					"format": "YYYY-MM-DD",
					"separator": " ~ ",
					"applyLabel": "확인",
					"cancelLabel": "취소",
					"fromLabel": "From",
					"toLabel": "To",
					"customRangeLabel": "Custom",
					"weekLabel": "W",
					"daysOfWeek": ["월", "화", "수", "목", "금", "토", "일"],
					"monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
					"firstDay": 1
				},
				"startDate": year + '-' + month + '-' + date,
				"endDate": year + '-' + month + '-' + date,
				"drops": "down"
			}, function(start, end, label) {
				console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
			});

			evt.OrderInfo();
		
			break;
		}
		case "OrderHistory": {
			evt.OrderHistory();
			break;
		}
		case "LogInfo":evt.LogEvt();break;
		case "Capa":evt.CapaEvt(); break;
		case "kpipage":evt.KpiEvt();break;
		case "ConstructionOrder":evt.ConstructionOrder(); break;
		case "Simulation":evt.Simulation(); break;
		case "WorkReport":evt.WorkReport(); break;
		case "ConstructionCompany":evt.construction(); break;
		case "ShippingResult": evt.ShippingResult(); break;
		case "EquipmentDefect": evt.EquipmentDefect(); break;
		case "ProductDefect": evt.ProductDefect(); break;
		case "Setting": evt.Setting(); break;
		case "ProcessMaster": evt.ProcessMaster(); break;
		case "UserInfo": evt.UserInfo(); break;
		case "WorkingLine": evt.WorkingLine(); break;
		case "MaterialMaster": evt.MaterialMaster(); break;
	}

}


