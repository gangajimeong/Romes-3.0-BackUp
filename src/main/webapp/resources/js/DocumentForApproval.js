/**
 * 
 */
export default class documentForApproval {
	trToUpdate() {
		const tbody = document.getElementById("DocumentForWorkOrderContents");
		const inputs = document.getElementsByClassName('updateForDoc');
		const miniTbody = document.getElementById("deleteForDocument");
		let tr = tbody.children;
		[...tr].forEach(function(tr) {
			tr.addEventListener('click', function(event) {
				event.stopPropagation()
				if (document.getElementsByClassName('DocumentForWorkOrderUpdate')[0].classList.contains('active')) {
					let id = this.children[0].innerText;

					axios.get("selectDocForId", { params: { id: id } })
						.then(function(res) {
							let result = res.data
							console.log(result);
							[...inputs].forEach(function(res, idx) {
								if (idx != 4) {
									res.value = result[idx];
								}
							})
						})

				}
				if (document.getElementsByClassName('DocumentForWorkOrderDelete')[0].classList.contains('active')) {
					let td = this.children
					let datas = [];
					datas[0] = td[0].innerText;
					datas[1] = td[1].innerText;
					datas[2] = td[4].innerText;
					datas[3] = td[5].innerText;
					datas[4] = td[6].innerText;
					createTableRow(miniTbody, 5, datas);



				}
			})
		})

	}
	myList(){
		const my = document.getElementById("mymyList")
		my.addEventListener('click',mylist);
	}
	previewEvt() {

		let view = document.getElementsByClassName('previewForDoc');

		[...view].forEach(function(btn) {
			btn.addEventListener('click', function(event) {
				event.stopPropagation()
				let url = this.parentNode.children[0].innerText;
				const img = document.getElementById('designImgforDoc');
				let id = this.parentNode.parentNode.children[0].innerText;
				document.getElementById("docIdForModal").value = id;
				
				$('.DesignPreview').modal('show');
				
				if (!!url) {
					img.src = "imageViewForDoc?imagename=" + encodeURI(url);
					img.style.height = "100%";
					img.style.width = "100%";
					img.style.display = ""
					img.style.margin = ""
				}
				else {
					img.src = "img/no_image.png";
					img.style.height = "30%";
					img.style.width = "30%";
					img.style.display = "block"
					img.style.margin = "auto"

				}


			})
		})
		document.getElementsByClassName('excelForDesign')[0].addEventListener('click', ExportExcel);

	}
	preConFirmDocEvt() {

		let view = document.getElementsByClassName('preConFirmDoc');

		[...view].forEach(function(btn) {
			btn.addEventListener('click', function(event) {
				event.stopPropagation()
				let url = this.parentNode.children[0].innerText;
				const img = document.getElementById('designImgforDoc');
				let id = this.parentNode.parentNode.children[0].innerText;
				document.getElementById("docIdForModal").value = id;
				$('.DesignPreview').modal('show');
				if (!!url) {
					img.src = "imageViewForDoc?imagename=" + encodeURI(url);
					img.style.height = "100%";
					img.style.width = "100%";
					img.style.display = ""
					img.style.margin = ""
				}
				else {
				
					img.src = "img/no_image.png";
					img.style.height = "30%";
					img.style.width = "30%";
					img.style.display = "block"
					img.style.margin = "auto"

				}


			})
		})
		document.getElementsByClassName('excelForDesign')[0].addEventListener('click', ExportExcel);

	}
	preMyDetailEvt() {

		let view = document.getElementsByClassName('myDetail');

		[...view].forEach(function(btn) {
			btn.addEventListener('click', function(event) {
				event.stopPropagation()
				let url = this.parentNode.children[0].innerText;
				const img = document.getElementById('designImgforDoc');
				let id = this.parentNode.parentNode.children[0].innerText;
				document.getElementById("docIdForModal").value = id;
				
				$('.DesignPreview').modal('show');
				if (!!url) {
					img.src = "imageViewForDoc?imagename=" + encodeURI(url);
					img.style.height = "100%";
					img.style.width = "100%";
					img.style.display = ""
					img.style.margin = ""
				}
				else {
					
					img.src = "img/no_image.png";
					img.style.height = "30%";
					img.style.width = "30%";
					img.style.display = "block"
					img.style.margin = "auto"

				}


			})
		})
		document.getElementsByClassName('excelForDesign')[0].addEventListener('click', ExportExcel);

	}
	registerButton() {
		document.getElementById("DesignDocRegi").addEventListener('click', registerDesign)
	}
	updateButton() {
		document.getElementById("updateForDocConfirm").addEventListener('click', updateDesign)
	}
	deleteButton() {
		document.getElementById("deletForDocumentButton").addEventListener('click', deletForDocument)
	}
	custumerForRequest() {
		document.getElementById("customer-Confirm-request").addEventListener('click', approvalForCustomer)
	}
	excelButton() {
		if (document.getElementById("download")) {
			document.getElementById("download").addEventListener("click", ExportExcel);
		}
	}
	uploadDesignButton() {
		let file = document.getElementById("DesignUploadInput");
		let file1 = document.getElementById("DesignUploadInputForUpdate");

		file.addEventListener('change', getUploadfileName);
		document.getElementById("uploadDesign").addEventListener('click', uploadButtonEvt)

		file1.addEventListener('change', getUploadfileName);
		document.getElementById("uploadDesignForUpdate").addEventListener('click', uploadButtonEvt)

	}
	drawDivisionSelect() {

		let select = document.getElementsByClassName("selectDivisionForApproval");
		axios.get("getDivisionOrPositionOrUsers", {
			params: {
				option: 0,
				division_id: 0,
				position: 0
			}
		})
			.then(function(res) {

				let res0 = res.data;
				createSelectBox(select[0], res0);
				createSelectBox(select[1], res0);


			}).catch(function(e) {
				console.log(e);
			})
	}

	drawPosition(id) {
		let select = document.getElementsByClassName("selectPositionForApproval");
		let select1 = document.getElementsByClassName("selectUsersForApproval");
		if (select1[0].children.length > 1) {
			optionClear(select1[0]);
		}
		if (select1[1].children.length > 1) {
			optionClear(select1[1]);
		}
		let div_id = id;
		if (div_id === 0) return;
		axios.get("getDivisionOrPositionOrUsers", {
			params: {
				option: 1,
				division_id: div_id,
				position: 0
			}
		})
			.then(function(res) {

				let res1 = res.data;
				createSelectBoxForPosition(select[0], res1)
				createSelectBoxForPosition(select[1], res1)

			}).catch(function(e) {
				console.log(e);
			})

	}

	drawUsers(id, position) {

		const select = document.getElementsByClassName("selectUsersForApproval");
		if (id === 0 || position === 0) return;
		else {
			axios.get("getDivisionOrPositionOrUsers", {
				params: {
					option: 2,
					division_id: id,
					position: position
				}
			})
				.then(function(res) {

					let res2 = res.data;
					if (res2.length === 0) return;
					createSelectBoxForUser(select[0], res2)
					createSelectBoxForUser(select[1], res2)
				}).catch(function(e) {
					console.log(e);
				})
		}
	}
	drawOrderInfo() {
		let datas = {};
		axios.get('orderInfosForDocument')
			.then(function(res) {
				const orderInfos = document.getElementsByClassName("selectOrderInfoForDoc");
				const selproducts = document.getElementsByClassName("selectOrderInfoToProductForDoc");
				[...res.data].forEach(function(data) {
					datas[data.id] = data.products;
				})
				createSelectBoxForDocument(orderInfos[0], res.data);
				createSelectBoxForDocument(orderInfos[1], res.data);

				orderInfos[0].addEventListener('change', function() {
					let orderInfoId = this.value;
					if (orderInfoId != 0) {
						let products = datas[orderInfoId];
						createSelectBoxForDocument(selproducts[0], products)
					} else {
						let options = selproducts[0].children;
						let len = options.length;
						for (let i = 1; i < len; i++) {
							options[1].remove();
						}
					}
				})
				for (let key in datas) {
					let orderInfoId = key;
					if (orderInfoId != 0) {
						let products = datas[orderInfoId];
						createSelectBoxForDocument(selproducts[1], products)
					} else {
						let options = selproducts[1].children;
						let len = options.length;
						for (let i = 1; i < len; i++) {
							options[1].remove();
						}
					}
				}
			})
			.catch(function(e) { console.log(e) })
	}
	selectEvts() {
		let select = document.getElementsByClassName("selectDivisionForApproval");
		let select1 = document.getElementsByClassName("selectPositionForApproval");
		let select2 = document.getElementsByClassName("selectUsersForApproval");



		const evt = new documentForApproval;
		select[0].addEventListener('change', function() {
			let id = this.value;
			if (select1[0].children.length > 1) {
				optionClear(select1[0].children);
			}
			if (select2[0].children.length > 1) {
				optionClear(select2[0].children);
			}

			if (id != 0) {
				evt.drawPosition(id);
			} else {
				return;
			}

		})
		select1[0].addEventListener('change', function() {
			const evt = new documentForApproval;
			let position = this.value;
			if (position != 0) {
				evt.drawUsers(select[0].value, position);
			} else {
				return;
			}
		})
		select[1].addEventListener('change', function() {
			let id = this.value;
			if (select1[1].children.length > 1) {
				optionClear(select1[1].children);
			}
			if (select2[1].children.length > 1) {
				optionClear(select2[1].children);
			}

			if (id != 0) {
				evt.drawPosition(id);
			} else {
				return;
			}

		})
		select1[1].addEventListener('change', function() {
			const evt = new documentForApproval;
			let position = this.value;
			if (position != 0) {
				evt.drawUsers(select[1].value, position);
			} else {
				return;
			}
		})

	}
}
function optionClear(options) {
	for (let i = 1; i < options.length; i++) {
		options[i].remove();
	}
}
function createSelectBox(select, datas) {

	let firstOption = select.children[0];

	let frag = document.createDocumentFragment();
	let options = select.children;
	if (options.length > 1) {
		for (let i = 1; i < options.length; i++) {
			options[i].remove();
		}

	}
	for (let i = 0; i < datas.length; i++) {
		let option = firstOption.cloneNode(true);
		let data = datas[i];
		option.value = data.id;
		option.innerText = data.division;
		frag.append(option);
	}
	select.appendChild(frag);
	firstOption.selected = true;
}
function createSelectBoxForPosition(select, datas) {
	let firstOption = select.children[0];

	let frag = document.createDocumentFragment();
	let options = select.children;
	if (options.length > 1) {
		for (let i = 1; i < options.length; i++) {
			options[i].remove();
		}
	}
	let result = datas[1];
	for (let i = 0; i < result.length; i++) {
		let option = firstOption.cloneNode(true);
		let data = result[i];
		option.value = data;
		option.innerText = data;
		frag.append(option);
	}
	select.appendChild(frag);
	firstOption.selected = true;
}

function createSelectBoxForUser(select, datas) {
	let firstOption = select.children[0];

	let frag = document.createDocumentFragment();
	let options = select.children;
	if (options.length > 1) {
		for (let i = 1; i < options.length; i++) {
			options[i].remove();
		}
	}
	for (let i = 0; i < datas.length; i++) {
		let option = firstOption.cloneNode(true);
		let data = datas[i];
		option.value = data.id;
		option.innerText = data.name;
		frag.append(option);
	}
	select.appendChild(frag);
	firstOption.selected = true;
}
/*Excel functions*/

function getBase64ImgUrl(workbook, worksheet, imageName) {
	// 이미지 데이터 Base64 형식 로딩
	axios.get('imageViewForExcel', { params: { imagename: encodeURI(imageName) } })
		.then(function(res) {
			var imageId1 = workbook.addImage({
				base64: res.data,
				extension: "png",
			});
			worksheet.addImage(imageId1, 'B15:M47')

			workbook.xlsx.writeBuffer().then((data) => {

				let datas = new Blob([data]);
				let url = window.URL.createObjectURL(datas);
				let date = new Date();
				let strDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + (date.getDate());
				let elem = document.createElement("a");
				elem.href = url;
				elem.download = `디자인안 문서${strDate.replaceAll(" ", "")}.xlsx`;
				document.body.appendChild(elem);
				elem.style = "display: none";
				elem.click();
				elem.remove();
			});
		}).catch(function(e) { console.log(e); alert("사진이 없습니다.") })
};
function ExportExcel() {
	let image = document.getElementById('designImgforDoc').src;
	let uri = image.substring(image.indexOf("=") + 1);
	let imageName = decodeURI(uri);
	let id = document.getElementById("docIdForModal").value;
	let obj;
	axios.get("getDocInfo",{params :{id:id}})
	.then(function(res){
		obj = res.data;
		
		
	}).catch(function(e){
		console.log(e)
		alert("문서 생성 실패")
		return;
	})
	axios({
		method: 'get',
		url: "getExcelForm",
		responseType: 'blob'
	})
		.then(function(res) {
			let buffer = res.data;
			let workbook = new ExcelJS.Workbook();
			workbook.xlsx.load(buffer).then(workbook => {
				var worksheet = workbook.getWorksheet("디자인안 보고서");
				//문서명
				worksheet.getCell('D2').value = obj.title
				//작성일자
				let date = obj.writtenDate;
				worksheet.getCell('J2').value = date[0]+"-"+date[1]+"-"+date[2];
				//작성자명
				worksheet.getCell('D4').value = obj.writer;
				//부서명
				worksheet.getCell('J4').value = obj.division;
				//수주 명
				worksheet.getCell('D6').value = obj.orderInfo;
				//품목
				worksheet.getCell('J6').value = obj.product;
				//특이사항
				worksheet.getCell('D8').value = obj.remark;
				let cooper = obj.coopers
				for(let i =0; i< cooper.length;i++){
					let ascii = String.fromCharCode([68+i]);
					let num = ascii+49;
					let div = ascii+48;
					worksheet.getCell(num).value = cooper[i].name;
					worksheet.getCell(div).value = cooper[i].division;
				}
				//시안
				getBase64ImgUrl(workbook, worksheet, imageName);
				//직책
				//협의자


			})
		})
}
/*버튼 핸들러*/
function registerDesign() {
	const form = document.getElementById("register-Design");
	const file = document.getElementById("DesignUploadInput");
	let data = new FormData(form);
	if (file.files.length > 0) {
		data.append("Design", file.files[0]);
	} else {
		data.append("Design", null);
	}
	for (let key of data.keys()) {
		console.log(key + ":" + data.get(key))
		if (!!data.get(key) === false) {
			if (key != "Design") {
				alert("빈 항목이 있습니다.")
			}
			return;
		}
	}
	axios.post('registerDesignDocument', data)
		.then(function(res) {
			let state = res.data.state;
			switch (state) {
				case 0: { alert("로그인이 필요합니다."); location.href = "/ROMES/"; break; }
				case 1: { alert("등록 성공"); location.reload(); break; }
				case 2: { alert("에러"); location.reload(); break; }
				case 3: { alert("이미 등록된 제품 입니다."); location.reload(); break; }
			}
		});



}
function uploadButtonEvt() {
	if (document.getElementsByClassName("DocumentForWorkOrderUpdate")[0].classList.contains('active')) {
		let file1 = document.getElementById("DesignUploadInputForUpdate")
		file1.click();
	} else {
		let file = document.getElementById("DesignUploadInput")
		file.click();
	}






}
function getUploadfileName() {
	let file = this
	if (file.files.length > 0) {
		let display;
		if (document.getElementsByClassName("DocumentForWorkOrderUpdate")[0].classList.contains('active')) {
			display = document.getElementById("designReportForUpdate");
		} else {
			display = document.getElementById("designReport");
		}
		let fileName = file.files[0].name
		console.log(fileName)
		display.innerText = fileName;
	}
}
/*페이지 functions*/

function createSelectBoxForDocument(select, datas) {
	let firstOption = select.children[0];

	let frag = document.createDocumentFragment();
	let options = select.children;
	if (options.length > 1) {
		let len = options.length
		for (let i = 1; i < len; i++) {
			options[1].remove();
		}
	}
	let result = datas;
	for (let i = 0; i < result.length; i++) {
		let option = firstOption.cloneNode(true);
		let data = result[i];
		option.value = data.id;
		option.innerText = data.title;
		if (select.classList.contains('updateForDoc')) {
			option.disabled = true;
		}
		frag.append(option);
	}
	select.appendChild(frag);
	firstOption.selected = true;
}

/*업데이트*/

function updateDesign() {
	const form = document.getElementById('updateDocumentForm')
	let data = new FormData(form);
	// 이미지 체크
	const file = document.getElementById("DesignUploadInputForUpdate");
	if (file.files.length > 0) {
		data.append("Design", file.files[0]);
	} else {
		data.append("Design", null);
	}
	//form 체크
	for (let key of data.keys()) {
		console.log(key + ":" + data.get(key))
		if (!!data.get(key) === false && key != "Design") {
			if (data.get(key) === 0) {
				alert("협의자가 선택되지 않았습니다.")
			} else {
				alert("빈 항목이 있습니다.")
			}

			return;
		}
	}

	// 통신
	axios.post('updateForDocument', data)
		.then(function(response) {
			let state = response.data.state;
			switch (state) {
				case 0: { alert("로그인이 필요합니다."); location.href = "/ROMES/"; break; }
				case 1: { alert("수정 완료"); location.reload(); break; }
				case 2: { alert("에러"); location.reload(); break; }
			}

		}).catch(function(e) {
			console.log(e);
			alert('수정 실패')
			location.reload();
		})

}
function approvalForCustomer() {
	let button = this;
	let docId = document.getElementsByClassName("updateForDoc")[0].value;
	if (!!docId === false) {
		alert("문서가 선택 되지 않았습니다.")
		return;
	}
	let res = confirm('문서 수정 완료 처리 하시겠습니까?')
	if (res) {
		res = confirm("고객 승인 요청을 진행 하시겠습니까?")
		if (res === false) return;
	}
	if (res) {
		axios.interceptors.request.use(
			function(config) {
				document.getElementById("loading-button").style.display = "";
				button.style.display = "none";
				return config;
			})
			
		axios.get("approvalForCustomer", { params: { id: docId } })
		
		.then(function(res) {
				let state = res.data.state;
				switch (state) {
					case 0: { alert("로그인이 필요합니다."); location.href = "/ROMES/"; break; }
					case 1: { alert("요청 완료"); location.reload(); break; }
					case 2: { alert("에러"); location.reload(); break; }
				}

			})
	} else {
		return;
	}

}
function createTableRow(tbody, tdCount, datas) {
	let tr = tbody.children;
	for (let i = 0; i < tr.length; i++) {
		let id = tr[i].children[0].innerText;
		if (id === datas[0]) {
			alert("이미 존재하는 문서 입니다.")
			return;
		}
	}
	let trash = document.getElementById("trashForDoc").cloneNode();
	let row = tbody.insertRow();
	for (let i = 0; i < tdCount + 1; i++) {

		let cell = row.insertCell();

		if (i == 0) cell.style.display = "none"
		if (i == 2) {
			cell.style.width = "23%";


		}
		if (i == tdCount) {
			cell.style.width = "2%";
			cell.style.padding = "3px"
			cell.style.verticalAlign = "middle"
			trash.addEventListener('click', function() {
				tbody.deleteRow(this.parentNode.parentNode.rowIdx)
			})
			cell.appendChild(trash);

		} else {
			cell.innerText = datas[i];

		}
		if (i != 0 && i != 2 && i != tdCount) {
			cell.style.width = "25%";
		}
	}
}
function deletForDocument() {
	const miniTbody = document.getElementById("deleteForDocument");
	let tr = miniTbody.children;
	let datas = [];
	[...tr].forEach(function(res) {
		let id = res.children[0].innerText;
		datas.push(id);
	})
	if (datas.length === 0) {
		alert("선택된 문서가 없습니다.")
		return;
	}
	axios.get('deleteForDocument', { params: { ids: datas.join(',') } })
		.then(function(res) {
			let ret = res.data
			if (ret) {
				alert("삭제 되었습니다.")
				location.reload();
			}
		})
}
function mylist(){
	
	$('.myList').modal('show')
}
