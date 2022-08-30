/**
 * 
 */
 import * as RESULT from './ResultCodes.js';
export default class orderinfoEvt {
	evt() {
		// crud button 이벤트
		const createBtn = document.getElementById("createOrderInfo");
		const updateBtn = document.getElementById("updateOrderInfo");
		const deleteBtn = document.getElementById("deleteOrderInfo")
		createBtn.addEventListener('click', create);
		updateBtn.addEventListener('click', update);
		deleteBtn.addEventListener('click', orderInfoDelete);

		//탭 이벤트 
		//const updateTab = document.getElementsByClassName("OrderUpdate")[0];
		//updateTab.addEventListener('cilck', noti);

		// 메인 테이블 행 이벤트
		const tbody = document.getElementById("OrderHistoryContents");
		let tr = tbody.children;
		[...tr].forEach((ele) => {
			ele.addEventListener('click', MainTrEvt);
		})

		//서브 테이블 행 이벤트
		const tbody2 = document.getElementById("OrderHistoryContents2");
		let tr2 = tbody2.children;
		[...tr2].forEach((ele) => {
			ele.addEventListener('click', SubTrEvt);
		})

		//작업지시 이벤트 버튼
		const workOrderBtn = document.getElementsByClassName("workOrder");
		[...workOrderBtn].forEach((btn) => {
			btn.addEventListener('click', MakeWorkOrder);
		})

		//작업지시 행 추가 버튼 
		const addRowBtn = document.getElementById("addRowBtn");
		addRowBtn.addEventListener('click', addWorOrderTr);

		//작업지시 행 삭제 버튼
		const deleteRowBtn = document.getElementsByClassName('deleteWorkOrder')[0];
		deleteRowBtn.addEventListener('click', deleteRow);

		//작업 지시서 등록 버튼
		const rgWorkOrderBtn = document.getElementsByClassName("rgWorkOrderBtn");
		[...rgWorkOrderBtn].forEach((btn) => {
			btn.addEventListener('click', registWorkOrder);
			//			btn.addEventListener('click', getOrderInfo);
		})

		//excelImport
		const ExcelImportBtn = document.getElementById("receive_order_excelImport");
		ExcelImportBtn.addEventListener('change', readExcel);

		//수주 등록 다중 select
		const orderSelectCompany = document.getElementById("order_select_company");
		orderSelectCompany.addEventListener('change', selectBrand);

		//업데이트 다중 select
		const orderUpdateCompany = document.getElementById("order_update_company");
		orderUpdateCompany.addEventListener('change', selectBrand);

		//작업지시 삭제 버튼
		const deleteWorkOrderBtn = document.getElementsByClassName('delete_work_order');
		[...deleteWorkOrderBtn].forEach((btn) => {
			btn.addEventListener('click', deleteWorkOrder);
		})
	}

	uploadEvts(input_id, labelClassName, img_id) {
		const input = document.getElementById(input_id);
		input.addEventListener('change', function() {
			let filename = input.files[0].name;

			let label = document.getElementsByClassName(labelClassName)[0];
			label.innerText = filename;
		})
		function readImage(input) {

			if (input.files && input.files[0]) {

				const reader = new FileReader()
				// 이미지가 로드가 된 경우
				reader.onload = e => {
					const previewImage = document.getElementById(img_id)
					previewImage.style.removeProperty('max-height')
					previewImage.style.setProperty('height', '100%');
					previewImage.style.setProperty('width', '100%');
					previewImage.style.setProperty('margin-top', '0px');

					previewImage.src = e.target.result
				}
				// reader가 이미지 읽도록 하기
				reader.readAsDataURL(input.files[0])
			}
		}
		const inputImage = document.getElementById(input_id)
		inputImage.addEventListener("change", e => {
			readImage(e.target)
		})
	}


}


function create() {
	const form = document.getElementById("createOrderInfoForm");
	const dates = document.getElementsByClassName("receivedOrderDate")[0].value;
	let localTypeDates = dates + " " + "00:00:00";
	if (!!dates === false) { alert("수주일이 입력 되지 않았습니다.") }
	let data = new FormData(form);
	for (let key of data.keys()) {
		console.log(key + ":" + data.get(key))
		if (!!data.get(key) === false) {
			alert("선택하지 않았거나 빈 항목이 있습니다.")
			return;
		}
	}
	data.append("receivedOrderDate", localTypeDates);
	console.log(localTypeDates)
	axios.post("createOrderBasicInfo", data)
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}

function update() {
	const form = document.getElementById("updateOrderInfoForm");
	const dates = document.getElementsByClassName("receivedOrderDate")[1].value;
	let localTypeDates = dates + " " + "00:00:00";
	if (!!dates === false) { alert("수주일이 입력 되지 않았습니다.") }
	let data = new FormData(form);
	for (let key of data.keys()) {
		if (!!data.get("id") === false) {
			alert("수주목록에서 수정할 수주 정보를 선택해 주세요");
			return;
		}
		if (!!data.get(key) === false && key != "id") {
			alert("선택하지 않았거나 빈 항목이 있습니다.")
			return;
		}
	}
	data.append("receivedOrderDate", localTypeDates);
	console.log(localTypeDates)
	axios.post("updateOrderBasicInfo", data)
		.then(function(res) {
			let ret = res.data;
			if (ret == 0) {
				location.reload();
			} else if (ret == RESULT.FAIL_RESULT) {
				alert("등록 실패");
			}
		})
}

function orderInfoDelete() {
	let id = document.getElementsByClassName("info-u")[0].value;
	if (!!id === false) {
		alert("수주목록에서 삭제할 수주 정보를 선택해 주세요");
		return;
	}
	let state = confirm("정말 삭제 하시겠습니까?");
	if (state === false) return;

	axios.get("deleteOrderBasicInfo", { params: { id: id } })
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}

function MainTrEvt() {
	const updateTab = document.getElementsByClassName("OrderUpdate")[0];
	const inputs = document.getElementsByClassName("info-u");
	let tr = this;
	let id = tr.children[0].innerText;
	selectTr(id);
	if (updateTab.classList.contains("active")) {
		for (let i = 0; i < inputs.length; i++) {
			inputs[i].value = "";
		}
		axios.get('selectOrderInfoById', { params: { id: id } })
			.then(function(res) {
				let data = res.data;
				console.log(data);
				if (data[0] === false) {
					alert("error")
					return;
				}
				for (let i = 0; i < data.length; i++) {
					if (i == 2) {
						let date = data[i];
						let month = date[1];
						let day = date[2];
						console.log("0" + date[1].toString())
						if (parseInt(date[1]) < 10) {
							month = "0" + date[1].toString();
						}
						if (parseInt(date[2]) < 10) {
							day = "0" + date[2].toString();
						}
						inputs[i].value = date[0] + "-" + month + "-" + day;
					}
					else {
						inputs[i].value = data[i];
					}
				}

			})
			.catch(function(e) {
				console.log(e);
				alert("오류: 관리자 문의")
			})
	}
}

function MakeWorkOrder() {
	const orderName = document.getElementById("orderName");
	const companyDis = document.getElementById("orderCompany");
	const writterDis = document.getElementById("writter")
	const writedDate = document.getElementById("writterDate");
	const brandName = document.getElementById("orderInfoBrandname");
	const orderInfoID = document.getElementById("orderInfoID");

	$('#myModal').modal('show');

	let tr = this.parentNode.parentNode;
	console.log(tr);
	let td = tr.children;
	let id = td[0].innerText;
	const IdInput = document.getElementById("orderInfoIdForWorkOrder");
	IdInput.value = id;

	orderInfoID.innerText = id;
	orderName.innerText = td[1].innerText;
	companyDis.innerText = td[3].innerText;
	writterDis.innerText = td[4].innerText;
	writedDate.value = td[5].innerText;
	brandName.value = td[7].innerText;

	let bId = td[8].innerText;
	console.log(bId);
	axios.get("setWorkorderBrand", { params: { id: bId } })
		.then(function(res) {
			let ret = res.data;
			console.log(ret);
			let length = ret.length;
			const selectEl = document.querySelector("#second_select");
			removeAll();

			for (let i = 0; i < length; i++) {
				let Option = document.createElement("option");
				Option.text = ret[i].branch;
				Option.value = ret[i].id;
				selectEl.options.add(Option);

			}


		})
}

function addWorOrderTr() {
	const tbody = document.getElementById("workOrderTbody");
	const tr = document.getElementsByClassName("ori-row")[0];
	let newTr = tr.cloneNode(true);
	let newTd = newTr.children
	let len = newTd.length;
	let btn = newTd[len - 1].children[0];
	btn.addEventListener('click', deleteRow);
	tbody.appendChild(newTr);
}

function deleteRow() {
	const tbody = document.getElementById("workOrderTbody");
	let row = this.parentNode.parentNode;
	let idx = row.rowIndex - 1;
	if (idx === 0) {
		alert("첫번째 행입니다.");
		return;
	}
	tbody.deleteRow(idx);
	console.log("godd")
}


function registWorkOrder() {
	//작업 지시서 등록 이벤트
	const tbody = document.getElementById("workOrderTbody");

	const orderdata = document.getElementById("OrderDataTbody");

	//작성일, 출고일, 시공일
	const date = document.querySelector("#writterDate").value;
	const shipDate = document.querySelector("#shipDate").value;
	const constructDate = document.querySelector("#constructDate").value;

	const rows = orderdata.getElementsByTagName("tr");
	const cells = rows[0].getElementsByTagName("td");

	let orderInfoID = cells[0].firstChild.data;	//수주ID	
	let brand = cells[4].firstChild.data;	//브랜드
	console.log(brand);
	let tr = tbody.children;
	let info = [];
	for (let i = 0; i < tr.length; i++) {
		let datas = [];
		let td = tr[i].children;
		[...td].forEach(function(ele, i) {
			let data = ele.children[0].value;
			if (ele.children[0].getAttribute('type') === "checkbox")
				datas[i] = ele.children[0].checked;
			else {
				datas[i] = data;
			}
		})
		info.push(datas);

	}

	let orderinfo = [];
	orderinfo.push(date);
	orderinfo.push(shipDate);
	orderinfo.push(constructDate);
	orderinfo.push(orderInfoID);
	orderinfo.push(brand);



	console.log(info)
	let jsonStr = new FormData()
	jsonStr.append("data", JSON.stringify(info))
	jsonStr.append("orderInfo", JSON.stringify(orderinfo))

	let csrf = document.getElementsByName("_csrf")[0].value;
	axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
	axios.post("createWorkOrderInfo", jsonStr)
		.then(function(res) {
			let ret = res.data;
			console.log(ret);
			if (ret) {
				alert("등록 완료");
				location.reload();
			} else {
				alert("등록 실패");
			}
		}).catch(function(e) {
			console.log(e);
			alert("오류: 관리자 문의")
		})

}

/*
function registWorkOrder() {
	//작업 지시서 등록 이벤트
	const tbody = Array.from(document.querySelectorAll('#workOrderTbody tr'));
	const textgroup = tbody.map(tr => {
		return Array.from(tr.querySelectorAll('input,select')).map(input => {
			console.log(input.checked);
			if(input.value == on) {
				return input.checked;
			}
			else {
				return input.value;
			}
			});
	});

	console.log(textgroup);
}
*/

function viewDraftTab() {
	const draftTab = document.querySelector(".draftTab");
	draftTab.classList.add("on");
}

function SubTrEvt() {
	const inputId = document.getElementsByClassName("Design-c");
	let tr = this;
	let id = tr.children[0].innerText;
	inputId[0].value = id;

	let brandName = tr.children[1].innerText;
	inputId[1].value = brandName;

	let product = tr.children[2].innerText;
	inputId[2].value = product;	

	let url = tr.children[5].innerText;
	console.log(url);
	drawImg(url);
}

function drawImg(url) {
	const img = document.getElementById("sampleImage");
	const noImage = document.getElementById("noImage");
	if (url == "/Design/no_image.png") {
		noImage.style.display = "";
		img.style.display = "none";
		img.src = "img/no_image.png";
	} else {
		noImage.style.display = "none";
		img.style.display = "";
		img.src = "image?imagename=" + url
	}
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
			formData.append("Company", test);
			//console.log(test)
			//console.log(formData.get("hi"))
			let csrf = document.getElementsByName("_csrf")[0].value;
			axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
			axios.post("importExcel", formData)
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

function removeAll() {
	const selectEl = document.querySelector("#second_select");
	selectEl.options.length = 1;
}

function selectBrand() {
	const id = document.getElementById('order_select_company').value;
	const selectEl = document.querySelector("#order_select_brand");


	document.getElementById("order_select_brand").disabled = true;
	selectEl.options.length = 0;

	axios.get("orderFindBrand", { params: { id: id } })
		.then(function(res) {
			let ret = res.data;
			let length = ret.length;
			for (let i = 0; i < length; i++) {

				let Option = document.createElement("option");
				Option.text = ret[i].name;
				Option.value = ret[i].id;
				selectEl.options.add(Option);
				document.getElementById("order_select_brand").disabled = false;
			}

		})
}

function selectTr(id) {
	const updateEl = document.querySelector("#order_update_brand");

	updateEl.options.length = 0;
	axios.get("updateOrderFindBrand", { params: { id: id } })
		.then(function(res) {
			let ret = res.data;
			let length = ret.length;
			for (let i = 0; i < length; i++) {

				let Option = document.createElement("option");
				Option.text = ret[i].name;
				Option.value = ret[i].id;
				updateEl.options.add(Option);
				document.getElementById("order_update_brand").disabled = false;
			}

		})
}

function deleteWorkOrder() {
	const tr = this.parentNode.parentNode;
	const id = tr.children[0].innerText;
	let formData = new FormData();
	formData.append("id", id);

	if (confirm("삭제하시겠습니까?") == true) {

		let csrf = document.getElementsByName("_csrf")[0].value;
		axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
		axios.post('deleteWorkOrder', formData)
			.then((res) => {
				if (res.data == true) {
					alert("삭제되었습니다.");
					location.reload();
				} else {
					alert("삭제 실패");
				}
			})
	}
}
