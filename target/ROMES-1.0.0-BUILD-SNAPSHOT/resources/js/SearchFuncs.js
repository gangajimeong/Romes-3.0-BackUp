/**
 * 
 */

export function AutoSearch(inputID) {
	const code = document.getElementsByClassName(inputID);
	const List = []
	for (var i = 0; i < code.length; i++) {
		List[i] = code[i].innerText;
	}
	const set = new Set(List);
	const uniqueCode = [...set]
	console.log(uniqueCode)
	$("#" + inputID).autocomplete({
		source: uniqueCode,    // source 는 자동 완성 대상
		select: function(event, ui) {    //아이템 선택시
			console.log(ui.item);
		},
		focus: function(event, ui) {    //포커스 가면
			return false;//한글 에러 잡기용도로 사용됨
		},
		minLength: 1,// 최소 글자수
		autoFocus: true, //첫번째 항목 자동 포커스 기본값 false
		classes: {    //잘 모르겠음
			"ui-autocomplete": "highlight"
		},
		delay: 100,    //검색창에 글자 써지고 나서 autocomplete 창 뜰 때 까지 딜레이 시간(ms)
		//            disabled: true, //자동완성 기능 끄기
		position: { my: "right top", at: "right bottom" },    //잘 모르겠음
		close: function(event) {    //자동완성창 닫아질때 호출
			console.log(event);
		}
	});


}
export function InfoSearch(theadID, SearchInputId) {
//	console.log("Infosearch is acivate")
	const obj = localStorage.getItem("Open");
	const data = JSON.parse(obj);
	const url = data.url;
	
	
	const page = theadID.replace("ColName", "");
	
	if (page === url) {
		const thead = document.getElementById(theadID).getElementsByTagName("tr");
		const cells = thead[0].getElementsByTagName("th");
		const colList = [];
		
		for (let i = 0; i < cells.length - 1; i++) {
			colList[i] = cells[i + 1].firstChild.data
		}
//		console.log(colList)
		$.each(colList, function(idx, col) {
			addOption(idx, col, SearchInputId);

		})
	}


}
export function InfoSearchForNoneCheck(theadID, SearchInputId) {
//	console.log(theadID)
	const obj = localStorage.getItem("Open");
	const data = JSON.parse(obj);
	const url = data.url;
	let page = theadID.replace("ColName", "");
	if(page.indexOf("2")>0)page = page.replace("2","");
//	console.log(page)
	if (page === url) {
		const thead = document.getElementById(theadID).getElementsByTagName("tr");
		const cells = thead[0].getElementsByTagName("th");
		const colList = [];
		
		for (let i = 0; i < cells.length; i++) {
			
			colList[i] = cells[i].firstChild.data
		}
		console.log(colList)
		$.each(colList, function(idx, col) {
			addOptionForNonCheck(idx, col, SearchInputId);
		})
	}


}
export function titleSearch(theadID) {
	const thead = document.getElementById(theadID).getElementsByTagName("tr");
	const cells = thead[0].getElementsByTagName("th");
	const colList = [];
	for (var i = 0; i < cells.length - 1; i++) {
		let str = cells[i].firstChild.data;

		colList[i] = str;

	}
	if (colList[0] == "No") {
		colList.shift();
	}
	console.log(colList)
	return colList
}

export function FilterkeyWord(inputID, tableID, SearchInputId) {
	
	const value = $("#" + SearchInputId + " option:selected").val()
	
	const input = document.getElementById(inputID);
	const filter = input.value.toLowerCase();
	const table = document.getElementById(tableID);
	const tr = table.getElementsByTagName("tr");
	let td;
	
	for (let i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[value];

		
		if (td) {

			if (td.innerText.toLowerCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

function addOption(idx, colName, SearchInputId) {

	const select = document.getElementById(SearchInputId);

	const newOption = document.createElement("option");
	newOption.value = idx + 2;
	newOption.innerText = colName;
	select.appendChild(newOption);
}
function addOptionForNonCheck(idx, colName, SearchInputId) {
	if(colName === "시안")return;
	const select = document.getElementById(SearchInputId);

	const newOption = document.createElement("option");
	newOption.value = idx+1;
	newOption.innerText = colName;
	select.appendChild(newOption);
}

// 기간검색
export function setFilterEvt(preDateId, lastDateId, rowId, datenum) {
	const previous = document.getElementById(preDateId);
	const last = document.getElementById(lastDateId);
	previous.addEventListener('change', function(event) {
		var date = new Date(previous.value);
		if (last.value != '') {
			var date2 = new Date(last.value);
			if (date > date2) {
				alert('시간 선택이 잘못되었습니다.');
				previous.value = '';
			} else {
				repaintTable(date, date2, rowId, datenum);
			}
		} else {
			if (previous.value != '')
				repaintTable(date, null, rowId, datenum);
			else
				repaintTable(null, null, rowId, datenum);
		}
	});
	last.addEventListener('change', function(event) {
		var date = new Date(last.value);

		if (previous.value != '') {
			var date2 = new Date(previous.value);
			if (date < date2) {
				alert('시간 선택이 잘못되었습니다.');
				last.value = '';
			} else {
				repaintTable(date2, date, rowId, datenum);
			}
		} else {
			if (previous.value != '')
				repaintTable(date, null, rowId, datenum);
			else
				repaintTable(null, null, rowId, datenum);
		}
	});
}
function repaintTable(date1, date2, rowId, datenum) {
	var rows = document.getElementById(rowId).rows;
	for (var i = 0; i < rows.length; i++) {
		if (date1 != null && date2 != null) {
			var date = new Date(rows[i].children[datenum].innerText);
			if (date >= date1 && date <= date2) 
				rows[i].classList.remove("searchHide")
			else
				rows[i].classList.add("searchHide")
		}
	} 
}