export default class ConstructionManagement {
	Btnevt() {
		const createConstructionBtn = document.getElementById('create-construction-btn');
		createConstructionBtn.addEventListener('click', createBtn);

		const updateConstructionBtn = document.getElementById('update-construction-btn');
		updateConstructionBtn.addEventListener('click', updateBtn);

		const deletes = document.getElementsByClassName('constructionDelete')[0];
		deletes.addEventListener('click', deleteEvt);

		const creates = document.getElementsByClassName('constructionCreate')[0];
		creates.addEventListener('click', createEvt);

		const updates = document.getElementsByClassName('constructionUpdate')[0];
		updates.addEventListener('click', updateEvt);
		
		const employees = document.getElementsByClassName('constructionEmployee')[0];
		employees.addEventListener('click', setEmployeeEvt);		
		
		//excelImport
		const ExcelImportBtn = document.getElementById("constructionExcel");
		ExcelImportBtn.addEventListener('change', readExcel);
	}
}

function createBtn() {
	const form = document.getElementById("createConstruction");
	let data = new FormData(form);
	console.log(data.values);
	for (let key of data.keys()) {
		if (!!data.get(key) === false && key != "remark") {
			alert("빈 항목이 있습니다.")
			return;
		};
	}

	axios.post("createConstruction", data)
		.then(function(res) {
			alert("저장되었습니다");
			location.reload();
		}).catch(function(e) {
			console.log(e);
			alert("오류")
		})
}

function createEvt() {
	resetCheck();
	DisabledAllCheckBox();
	DisabledCheckBox();
}

function updateBtn() {
	const form = document.getElementById("update-construction-list");
	let data = new FormData(form);
	for (let key of data.keys()) {
		if (!!data.get(key) === false && key != "remark") {
			alert("빈 항목이 있습니다.")
			return;
		};
	}

	axios.post("updateConstructionBtn", data)
		.then(function(res) {
			alert("수정되었습니다");
			location.reload();
		}).catch(function(e) {
			console.log(e);
			alert("오류")
		})
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

function setEmployeeEvt() {
	resetCheck();
	clearEmTable();
	activateAllCheckBox();
	activateCheckBox();
}


function resetCheck() {
	const tbody = document.getElementById('constructionBody');
	let tr = tbody.children;
	[...tr].forEach(function(ele) {
		let check = ele.children[0].children[0].children[0];
		$(check).prop("checked", false);

	})
}

function activateAllCheckBox() {
	const thead = document.getElementById('ConstructionCompanyColName');
	const deletes = document.getElementsByClassName('constructionDelete')[0];
	const tbody = document.getElementById('constructionBody');
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

function deleteTableClear() {
	const delTable = document.getElementsByClassName('construction-delete')[0];
	let len = delTable.children.length
	for (let i = 0; i < len; i++) {
		delTable.deleteRow(-1);
	}
}

function activateCheckBox() {
	const tbody = document.getElementById('constructionBody');
	let tr = tbody.children;
	[...tr].forEach(function(ele) {
		let check = ele.children[0];
		check.style.display = "";
		check.addEventListener('click', checkevt);
	})
}

function checkevt() {
	let tr = this.parentNode;
	const delTable = document.getElementsByClassName('construction-delete-list')[0];
	const updates = document.getElementsByClassName('constructionUpdate')[0];
	const deletes = document.getElementsByClassName('constructionDelete')[0];
	const employees = document.getElementsByClassName('constructionEmployee')[0];
	const inputs = document.getElementsByClassName('construction-u');

	if (updates.classList.contains('active')) {
		let isCheck = $(tr.children[0].children[0].children[0]).is(':checked')
		if (isCheck === true) {
			let id = tr.children[2].innerText;
			axios.get('SelectConstruction', { params: { id: id } })
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
		}

	}
	if (deletes.classList.contains('active')) {
		let isCheck = $(tr.children[0].children[0].children[0]).is(':checked')

		if (isCheck === true) {
			let id = tr.children[2].innerText;
			let company = tr.children[3].innerText;
			let trash = document.getElementsByClassName('con-delete-checkBox')[0].cloneNode();
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

			let cell4 = row.insertCell();
			cell4.style.width = "5%";
			let btn = document.createElement('button')
			btn.style.padding = "0px";
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
	if (employees.classList.contains('active')) {
		let isCheck = $(tr.children[0].children[0].children[0]).is(':checked')
		if (isCheck === true) {
			let id = tr.children[2].innerText;
//			console.log(tr)
			axios.get('setEmployees', { params: { id: id } })
				.then(function(res) {
					let ret = res.data;
					const tbody = document.getElementsByClassName("construction-employee-list")[0];
					for (let i = 0; i < ret.length; i++) {
				
						let row = tbody.insertRow();
						let cell = row.insertCell();
						cell.innerText = ret[i].name;
				
						let cell1 = row.insertCell();
						cell1.innerText = ret[i].position;
				
						let cell2 = row.insertCell();
						cell2.innerText = ret[i].tel;
				
					}
				}).catch(function(e) {
					console.log(e)
					alert("오류")
					return;
				})
		} else {
			clearEmTable();
			for (let i = 0; i < inputs.length; i++) {
				inputs[i].value = "";
			}
		}

	}
}

function clearEmTable() {
	const tbody = document.getElementsByClassName("construction-employee-list")[0];
	let tr = tbody.children;
	let len = tr.length;
	for (let i = 0; i < len; i++) {
		tbody.deleteRow(-1);
	}
}

function DisabledAllCheckBox() {
	const thead = document.getElementById('ConstructionCompanyColName');
	let tr = thead.children[0];
	let check = tr.children[0];
	check.style.display = "none";
}

function DisabledCheckBox() {
	const tbody = document.getElementById('constructionBody');
	let tr = tbody.children;
	[...tr].forEach(function(ele) {
		let check = ele.children[0];
		check.style.display = "none";
		check.removeEventListener('click', checkevt);
	})
}

function deleteAxios() {
	const tbody = document.getElementById('constructionBody');
	const delTable = document.getElementsByClassName('construction-delete-list')[0];
	let tr = this.parentNode.parentNode;
	let id = parseInt(tr.children[0].innerText);
	let len = tbody.children.length;
	axios.get('deleteConstruction', { params: { id: id } })
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

function readExcel() {
	let input = event.target;
	let reader = new FileReader();
	let formData = new FormData();
	let test;
	reader.onload = function() {
		let data = reader.result;
		let workBook = XLSX.read(data, { type: 'binary' });
		workBook.SheetNames.forEach(function(sheetName) {
			console.log('SheetName: ' + sheetName);

			let rows = XLSX.utils.sheet_to_json(workBook.Sheets[sheetName]);
			test = JSON.stringify(rows);
			formData.append(sheetName, test);
			//console.log(test)
			//console.log(formData.get("hi"))
			console.log("hi");
			let csrf = document.getElementsByName("_csrf")[0].value;
			axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
			axios.post("constructionExcel", formData)
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
