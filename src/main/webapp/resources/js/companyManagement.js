/**
 * 
 */
export default class companyManagement {

	Buttonevt() {
		const creates = document.getElementsByClassName('companyCreate')[0];
		const updates = document.getElementsByClassName('companyUpdate')[0];
		const deletes = document.getElementsByClassName('companyDelete')[0];
		const managers = document.getElementsByClassName('companyResponsibility')[0];
		const inputs = document.getElementsByClassName("addressSearch")
		const detailAddress = document.getElementsByClassName('detailAddress');
		creates.addEventListener('click', createEvt);
		updates.addEventListener('click', updateEvt);
		deletes.addEventListener('click', deleteEvt);
		managers.addEventListener('click', ManagerEvt)

		inputs[0].addEventListener('click',getAddress);
		detailAddress[0].addEventListener('focusout',plusDetailAddr)
		
		const updateCompanyBtn = document.getElementById('update-company-btnn');
		updateCompanyBtn.addEventListener('click',update);
		
		//excelImport
		const ExcelImportBtn = document.getElementById("companyExcel");
		ExcelImportBtn.addEventListener('change', readExcel);

	}
}

function update() {
	const form = document.getElementById("update-company-list");
	let data = new FormData(form);
	for (let key of data.keys()) {
		if (!!data.get(key) === false && key != "remarks") {
			alert("빈 항목이 있습니다.")
			return;
		};
	}
	

	axios.post("updateCompanyBtn", data)
		.then(function(res) {
			alert("수정되었습니다");
			location.reload();
			/*let data = res.data;
			console.log(data)
			let row = findRowAndUpdate(data.id)
		    let td = row.children;
		    td[1].innerText = data.id
		    td[2].innerText = data.companyName;
		    td[3].innerText = data.company;
		    td[4].innerText = data.CEO_Name;
		    td[5].innerText = data.phone;
		    td[6].innerText = data.fax;*/
		}).catch(function(e) {
			console.log(e);
			alert("오류")
		})
}

function findRowAndUpdate(id){
	let row = document.getElementById("Company-"+id);
	console("1");
	return row;
}

function createEvt() {

	resetCheck();
	DisabledAllCheckBox();
	DisabledCheckBox();

}
function updateEvt() {
	resetCheck();
	activateAllCheckBox();
	activateCheckBox();
}
function deleteEvt() {
	resetCheck();
	activateAllCheckBox();
	activateCheckBox();
}



function activateAllCheckBox() {
	const thead = document.getElementById('companyManagementColName');
	const deletes = document.getElementsByClassName('companyDelete')[0];
	const tbody = document.getElementById('companyBody');
	let tr = thead.children[0];
	let check = tr.children[0];
	check.style.display = "";
	let allcheck = check.children[0].children[0];
	$(allcheck).change(function() {
		if (deletes.classList.contains('active') === false) return;
		if (this.checked) {

			let tr = tbody.children;
			[...tr].forEach(function(ele) {
				let check = ele.children[0].children[0].children[0];
				$(check).prop("checked", true);

			})
			allCheckData();
		} else {
			let tr = tbody.children;
			[...tr].forEach(function(ele) {
				let check = ele.children[0].children[0].children[0];
				$(check).prop("checked", false);

			})
			deleteTableClear();
		}
	});




}
function DisabledAllCheckBox() {
	const thead = document.getElementById('companyManagementColName');
	let tr = thead.children[0];
	let check = tr.children[0];
	check.style.display = "none";


}
//체크 박스 활성화/비활성화
function activateCheckBox() {
	const tbody = document.getElementById('companyBody');
	let tr = tbody.children;
	[...tr].forEach(function(ele) {
		let check = ele.children[0];
		check.style.display = "";
		check.addEventListener('click', checkevt);
	})
}
function DisabledCheckBox() {
	const tbody = document.getElementById('companyBody');
	let tr = tbody.children;
	[...tr].forEach(function(ele) {
		let check = ele.children[0];
		check.style.display = "none";
		check.removeEventListener('click', checkevt);
	})
}
function checkevt() {
	let tr = this.parentNode;
	const delTable = document.getElementsByClassName('company-delete-list')[0];
	const inputs = document.getElementsByClassName('company-u');
	const updates = document.getElementsByClassName('companyUpdate')[0];
	const deletes = document.getElementsByClassName('companyDelete')[0];
	const managers = document.getElementsByClassName('companyResponsibility')[0];


	if (updates.classList.contains('active')) {
		let isCheck = $(tr.children[0].children[0].children[0]).is(':checked')
		if (isCheck === true) {
			let id = tr.children[2].innerText;
			axios.get('Selectcompany', { params: { id: id } })
				.then(function(res) {
					let ret = res.data;
					for (let i = 0; i < ret.length; i++) {
						inputs[i].value = ret[i];
					}
				}).catch(function(e) {
					console.log(e)
					alert("오류")
					return;
				})
		} else {
			for (let i = 0; i < inputs.length; i++) {
				inputs[i].value = "";
			}
			return;
		}


	}
	if (deletes.classList.contains('active')) {
		let isCheck = $(tr.children[0].children[0].children[0]).is(':checked')
		if (isCheck === true) {
			let id = tr.children[2].innerText;
			let company = tr.children[3].innerText;
			let ceo = tr.children[4].innerText;
			let number = tr.children[5].innerText;
			let trash = document.getElementsByClassName('c-delete-checkBox')[0].cloneNode();
			for (let i = 0; i < delTable.children.length; i++) {
				console.log(id + ":" + delTable.children[i].children[0].innerText)
				if (id === delTable.children[i].children[0].innerText) return;
			}

			let row = delTable.insertRow();
			row.className = "del-" + id;
			
			let cell = row.insertCell();
			cell.style.display = "none";
			cell.innerText = id;

			let cell1 = row.insertCell();
			cell1.style.width = "30%";
			cell1.innerText = company;

			let cell2 = row.insertCell();
			cell2.style.width = "20%";
			cell2.innerText = ceo;

			let cell3 = row.insertCell();
			cell3.style.width = "45%";
			cell3.innerText = number;

			let cell4 = row.insertCell();
			cell4.style.width = "5%";
			let btn = document.createElement('button')
			btn.style.padding = "0px 0px 0px 0px";
			btn.className = "btn btn-secondary"
			btn.appendChild(trash);

			cell4.appendChild(btn);
			btn.addEventListener('click', deleteAxios);

		} else {
			let id = tr.children[2].innerText;
			for (let i = 0; i < delTable.children.length; i++) {
				console.log(id + ":" + delTable.children[i].children[0].innerText)
				if (id === delTable.children[i].children[0].innerText) {
					delTable.children[i].remove();
				};
			}
			return;
		}


	}

}

function resetCheck() {
	const tbody = document.getElementById('companyBody');
	let tr = tbody.children;
	[...tr].forEach(function(ele) {
		let check = ele.children[0].children[0].children[0];
		$(check).prop("checked", false);

	})
}
function allCheckData() {
	const tbody = document.getElementById('companyBody');
	const delTable = document.getElementsByClassName('company-delete-list')[0];
	let trs = tbody.children;
	[...trs].forEach(function(tr) {
		let td = tr.children;
		let id = td[2].innerText;
		let trash = document.getElementsByClassName('c-delete-checkBox')[0].cloneNode();
		for (let i = 0; i < delTable.children.length; i++) {
			console.log(id + ":" + delTable.children[i].children[0].innerText)
			if (id === delTable.children[i].children[0].innerText) return;
		}

		let row = delTable.insertRow();
		let cell = row.insertCell();
		cell.style.display = "none";
		cell.innerText = td[2].innerText;

		let cell1 = row.insertCell();
		cell1.style.width = "30%";
		cell1.innerText = td[3].innerText;;

		let cell2 = row.insertCell();
		cell2.style.width = "20%";
		cell2.innerText = td[4].innerText;;

		let cell3 = row.insertCell();
		cell3.style.width = "45%";
		cell3.innerText = td[5].innerText;;

		let cell4 = row.insertCell();
		cell4.style.width = "5%";

		let btn = document.createElement('button')
		btn.style.padding = "0px";
		btn.className = "btn btn-secondary"
		btn.appendChild(trash);

		cell4.appendChild(btn);
		btn.addEventListener('click', deleteAxios);

	})
}
function deleteTableClear() {
	const delTable = document.getElementsByClassName('company-delete-list')[0];
	let len = delTable.children.length
	for (let i = 0; i < len; i++) {
		delTable.deleteRow(-1);
	}
}
function deleteAxios() {
	const tbody = document.getElementById('companyBody');
	const delTable = document.getElementsByClassName('company-delete-list')[0];
	let tr = this.parentNode.parentNode;
	let id = parseInt(tr.children[0].innerText);
	let len = tbody.children.length;
	axios.get('deletecompany', { params: { id: id } })
		.then(function(res) {
			if (res.data) {
				delTable.deleteRow(tr.rowIdx)
				for (let i = 0; i < len; i++) {
					if (tbody.children[i].children[2].innerText === id) {
						tbody.children[i].remove();
						break;
					}
				}
				alert("삭제 되었습니다.")
				location.reload();
			} else {
				alert("오류")
			}

		}).catch(function(e) {
			console.log(e)
			
		})
}
function ManagerEvt() {
	const tbody = document.getElementById('companyBody');
	const create = document.getElementById("addManager");
	const updatebtn = document.getElementById('update-company-manager-btn');
	let tr = tbody.children;
	let len = tr.length;
	resetCheck();
	DisabledAllCheckBox();
	DisabledCheckBox();
	let ret = confirm("거래처를 선택하세요")
	console.log(ret)
	create.addEventListener('click', createManager);
	updatebtn.addEventListener('click', updateManager)
	if (ret) {
		for (let i = 0; i < len; i++) {
			tr[i].addEventListener('click', getManagerList);
		}
	}
}
function getManagerList() {
	const managers = document.getElementsByClassName('companyResponsibility')[0];
	const create = document.getElementById("addManager");
	if (managers.classList.contains("active") === false) return;
	let id = this.children[2].innerText;
	const company_id = document.getElementById("companyMini");
	company_id.innerText = id;


	axios.get('SearchDBcompany', { params: { id: id } })
		.then(function(res) {
			let ret = res.data;
			if (ret.length === 0) return;
			drawRow(ret);

		})
	create.addEventListener('click', createManager);

}
function clearTable() {
	const tbody = document.getElementsByClassName("company-M-list")[0];
	let tr = tbody.children;
	let len = tr.length;
	for (let i = 0; i < len; i++) {
		tbody.deleteRow(-1);
	}
}
function drawRow(ret) {
	clearTable();
	console.log(ret)
	if (!!ret[0].NoInfo) {
		alert("등록된 담당자가 없습니다.")
		return;
	}
	let len = ret.length;
	const tbody = document.getElementsByClassName("company-M-list")[0];
	for (let i = 0; i < len; i++) {

		let trash = document.getElementsByClassName('c-delete-checkBox')[0].cloneNode();
		let row = tbody.insertRow();
		row.addEventListener('click', mangerTrBtn);
		let cell = row.insertCell();
		cell.style.display = "none";
		cell.innerText = ret[i].id;

		let cell1 = row.insertCell();
		cell1.style.width = "10%";
		cell1.innerText = ret[i].name;

		let cell2 = row.insertCell();
		cell2.style.width = "20%";
		cell2.innerText = ret[i].pos;

		let cell3 = row.insertCell();
		cell3.style.width = "30%";
		cell3.innerText = ret[i].phone;

		let cell4 = row.insertCell();
		cell4.style.width = "35%";
		cell4.innerText = ret[i].email;

		let cell5 = row.insertCell();
		cell5.style.width = "5%";

		let btn = document.createElement('button')
		btn.style.padding = "0px";
		btn.className = "btn btn-secondary"
		btn.appendChild(trash);

		cell5.appendChild(btn);
		btn.addEventListener('click', deleteManager);
	}

}
function mangerTrBtn() {
	const update = document.getElementsByClassName('sub-company-update')[0];
	if (update.classList.contains("active") === false) return;
	let tr = this;
	let td = tr.children;
	let inputs = document.getElementsByClassName('manager-U-control');
	for (let i = 0; i < inputs.length; i++) {
		console.log(td[i].innerText)
		inputs[i].value = td[i].innerText;
	}

}
function createManager() {

	const form = document.getElementById("company-regiManager");
	let data = new FormData(form);
	let id = document.getElementById("companyMini").innerText;
	console.log(id)
	data.append("companyId", id);
	for (let key of data.keys()) {

		if (!!data.get(key) === false) {
			console.log(key)
			alert("입력되지 않은 항목이 있습니다.")
			return;
		}
	}
	axios.post('registerForcompany', data)
		.then(function(res) {
			if (res.data.ret) {
				location.reload();
			}
		})
}
function updateManager() {
	console.log("접근")
	const form = document.getElementById("update-manger-form");
	let data = new FormData(form);
	let id = document.getElementById("companyMini").innerText;
	data.append("company.id",id);
	for (let key of data.keys()) {
			console.log(key+":"+data.get(key))
		if (!!data.get(key) === false) {
			console.log(key)
			alert("입력되지 않은 항목이 있습니다.")
			return;
		}
	}
	axios.post('updateManager', data)
		.then(function(res) {
			if (res.data.ret) {
				location.reload();
			}
		})
}
function deleteManager() {
	const tbody = document.getElementById('company-M-list');
	let id = this.parentNode.parentNode.children[0].innerText;
	if (!!id) {
		axios.get('deleteManager', { params: { id: id } })
			.then(function(res) {
				tbody.deleteRow(this.parentNode.parentNode.rowIdx);
				
		})
		alert("삭제되었습니다")
		location.reload();
	}

}
function getAddress() {
	const postal = document.getElementsByClassName('postal-input');
	
	let address = this;
	
	new daum.Postcode({

		oncomplete: function(data) {
			console.log(data)
			postal[0].value = data.zonecode;
			address.value = data.roadAddress;
			
		}
	}).open();
}
function plusDetailAddr(){
	const inputs = document.getElementsByClassName("addressSearch")
	const details = document.getElementsByClassName('detailAddress')
	let addr = inputs[0].value;
	let detail = details[0].value;
	details[0].value = addr + " "+ detail;
	
}

function readExcel() {
	let input = event.target;
	let reader = new FileReader();
	let formData = new FormData();
	let arry = [];
	let test;
	reader.onload = function() {
		let data = reader.result;
		let workBook = XLSX.read(data, { type: 'binary' });
		workBook.SheetNames.forEach(function(sheetName) {
			console.log('SheetName: ' + sheetName);

			let rows = XLSX.utils.sheet_to_json(workBook.Sheets[sheetName]);
			test = JSON.stringify(rows);
			formData.append("Company", test);
			//console.log(test)
			//console.log(formData.get("hi"))
			let csrf = document.getElementsByName("_csrf")[0].value;
			axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
			axios.post("companyExcel", formData)
				.then(function(res) {
					let ret = res.data;
					console.log(ret);
					if (ret) {
						alert("등록 완료");
						location.reload();
					} else {
						alert("등록 실패")
						location.reload();
					}
				}).catch(function(e) {
					console.log(e);
					alert("오류: 관리자 문의")
				})


		})

	};
	reader.readAsBinaryString(input.files[0]);
}