/**
 * 공통코드 이벤트, 거래처 관리
 */

export class BtnFunctions {
	// id hidden 태그 생성 
	detail(pageName) {

		let MainEvt = function() {
			if (!document.getElementsByClassName(pageName + "Create")[0].classList.contains("active")) return;
			const td = this.children;
			const id = td[2].innerText;

			try {
				axios.get("Select" + pageName, {

					params: {
						id: id
					}
				}).then(function(response) {
					const ret = response.data;
					console.log(ret)
					$("#" + pageName + "-Modal").modal();
					const inputs = document.getElementsByClassName(pageName + "-u");
					const btn = document.getElementById("update-company-btn");
					ret.forEach(function(e, i) {
						inputs[i].value = e;
						inputs[i].disabled = true;

					})
					btn.disabled = true;
					btn.innerText = "상세 정보"

				}).catch(function(e) { console.log(e) })
			} catch (e) {
				console.log(e)
			}
		}
		let MainTr = document.getElementById(pageName + "Body").children;


		for (let i = 0; i < MainTr.length; i++) {
			MainTr[i].addEventListener('click', MainEvt)
		}

	}



	createHiddenInput(inputClass) {

		const newInput = document.createElement("input")
		newInput.setAttribute("type", "hidden");
		newInput.className = "form-control " + inputClass;
		newInput.setAttribute("name", "id");
		document.getElementsByClassName(inputClass)[0].before(newInput);
	}
	//거래처 관리 활성화
	trTotable(pageName) { // 담장자 활성화 
		const tbody = document.getElementsByClassName(pageName + "-M-list")[0];
		function ManagerList(value) {

			let newCell;

			axios.get("DB" + pageName, {

				params: {
					id: value
				}
			})
				.then(function(response) {
					const data = response.data;
					const tr = tbody.children;

					if (tr.length > 1) {
						for (let i = 0; i < tr.length; i++) {
							tbody.deleteRow(-1);
						}
					}
					if (data[0].hasOwnProperty('NoInfo')) {
						setTimeout(function() {
							alert("담당자 정보를 등록 해주세요")
						}, 150)


					} else {

						data.forEach(function(e) {
							let elen = document.getElementsByClassName(e.id).length;
							if (elen == 0) {
								let len = Object.keys(e).length + 1
								let newRow = tbody.insertRow();
								newRow.className = e.id
								for (let i = 0; i < len; i++) {
									newCell = newRow.insertCell();
									if (i == 0) {
										newCell.style.display = "none";
									}
									if (i == len - 1) {

										let newIcon;

										newIcon = document.createElement("i")
										newIcon.className = "bx bxs-trash btn";
										newIcon.addEventListener('click', function() {
											axios.get("deleteManager", {
												params: {
													id: e.id
												}
											}).then(function(response) {
												document.getElementsByClassName(e.id)[0].remove();

											}).catch(function(error) {
												console.log(error)

											})
										})
										newCell.appendChild(newIcon);
									}

								}

							}
							var td = $("." + e.id).children();
							td.eq(0).text(e.id);
							td.eq(1).text(e.name);
							td.eq(2).text(e.pos);
							td.eq(3).text(e.phone);
							td.eq(4).text(e.email);


						})
					}

				})
				.catch(function(error) {
					console.log(error);
				})
		}
		let miniTableEvt = function() {
			const selMiniTr = this;
			const td = selMiniTr.children;
			const values = document.getElementsByClassName("manager-U-control");
			const companyVal = document.getElementById("companyMini").innerText;

			for (let i = 0; i < td.length - 1; i++) {
				values[i].value = td[i].innerText;
			}

			document.getElementById("update-" + pageName + "-manager").addEventListener('click', function() {

				const csrf = document.getElementsByName("_csrf")[0].value;
				axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
				const paramss = {
					"id": values[0].value,
					"name": values[1].value,
					"position": values[2].value,
					"phone": values[3].value,
					"email": values[4].value
				}
				axios.post('updateManager/' + companyVal, paramss).then(function(response) {
					const ret = response.data
					if (ret.ret == true) { alert("수정") }
					ManagerList(companyVal);


				}).catch(function(error) {
					console.log(error)
				})
			})

		}
		let data = function() {

			const selTr = this;
			const td = selTr.children;
			const value = td[2].innerText;
			const csrf = document.getElementsByName("_csrf")[0].value;
			const inputs = document.getElementsByClassName(pageName + "-m-control");
			const activeTab = document.getElementsByClassName("sub-" + pageName + "-create")[0];
			const updateTab = document.getElementsByClassName("sub-" + pageName + "-update")[0];
			const title = document.getElementById("companyMini");
			title.innerText = value;




			for (let i = 0; i < inputs.length; i++) {

				inputs[i].disabled = false;
			}
			ManagerList(value);
			let addManager = function() {
				document.getElementById("addManager").addEventListener("click", function() {
					axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
					let params = {
						"name": inputs[0].value,
						"position": inputs[1].value,
						"phone": inputs[2].value,
						"email": inputs[3].value
					}

					axios.post("registerForcompany/" + value, params).then(function(response) {

						ManagerList(value);
						for (let i = 0; i < inputs.length; i++) {
							inputs[i].value = "";
						}
					}).catch(error => { console.log('failed', error) })


				})
			}
			let updateManager = function() {
				const tbody = document.getElementsByClassName(pageName + "-M-list")[0];
				const tr = tbody.children;

				for (let i = 1; i < tr.length; i++) {
					tr[i].addEventListener('click', miniTableEvt);
				}
			}
			if (activeTab.classList.contains("active")) { addManager() }
			activeTab.addEventListener('click', addManager)
			if (updateTab.classList.contains("active")) { updateManager() }
			updateTab.addEventListener('click', updateManager)

		}
		const tr = document.getElementById(pageName + "Body").children;

		for (let i = 0; i < tr.length; i++) {

			tr[i].addEventListener('click', data)
		}


	}
	// 메인 테이블 데이터 가져와서 목록 만들기(새로추가)
	SetRowTableData(...para) {
		//checkBtn, pageName, tableclass, inputClass, option\
		const tr = para[0].parent().parent().parent();
		const td = tr.children();
		const table = document.getElementsByClassName(para[2])[0];
		const plen = para.length
		let newRow = table.insertRow();
		let newCell;
		let len = td.length;
		let opt, startIdx = 1, endIdx = len - 1;
		newRow.className = td[1].innerText + "mini";

		if (plen > 4) {
			opt = para[4];
		} else {
			opt = para[3];
		}

		let updateInputEvt = function() {
			let td_val = this.children
			const isModal = !!document.getElementById(para[1] + "-Modal");
			console.log(isModal)
			if (isModal === true) {
				const set = new BtnFunctions
				set.axiosToInputs(para[1], td_val[0].innerText)
			} else {
				const input = document.getElementsByClassName(para[3]);
				//				console.log(input);
				//				console.log(td_val);
				//				console.log(td_val.length);
				//				console.log(td_val[5].innerText);
				//				console.log(input);

				const sets = new BtnFunctions
				if (input.length < td_val.length) {
					for (let i = 0; i < td_val.length - 1; i++) {
						let value = td_val[i].innerText;
						input[i].value = value;
					}
					sets.NotModelAxiosToInputs(para[1]);

				} else {
					if (para[1] === "Division") sets.NotModelAxiosToInputs(para[1]);
					for (let i = 0; i < td_val.length; i++) {
						let value = td_val[i].innerText;
						if (value.indexOf(':') != -1) {
							input[i].value = value.substring(0, 5);
						} else {
							input[i].value = value;
						}
					}
				}
			}

		}

		let ModalEvt = function() {
			const selTr = this;
			const td = selTr.children;
			const value = td[0].innerText;
			console.log(value)
			const input = document.getElementsByClassName(para[1] + "-u")
			$("#" + para[1] + "-Modal").modal();
			axios.get("Select" + para[1], {
				params: { id: value }
			})
				.then(function(response) {
					console.log("Where am I?")
					const data = response.data;
					if (input[0].disabled == true) {
						for (let i = 0; i < input.length; i++) {
							input[i].disabled = false;
						}
						const btn = document.getElementById("update-company-btn");
						btn.disabled = false;
						btn.innerText = "수정"

					}
					data.forEach(function(e, i) {
						input[i].value = e;
					})

				})
				.catch(function(error) {
					console.log(error);
				})
		}
		let Devt = function(value) {
			console.log(value)
			console.log("material delete")
			console.log(para[1])

			axios.get("delete" + para[1], {
				params: {
					"id": value
				}
			})
				.then(function(response) {
					const ret = response.data;
					console.log(ret.ret)
					if (ret.ret == true) {
						alert("삭제")
					}
				})
				.catch(function(e) { console.log(e) })
				.finally(function() {
					document.getElementsByClassName(value)[0].remove();
					document.getElementsByClassName(value + "mini")[0].remove();
				})
		};



		if (opt == "U" || opt == "UM") {
			if (plen > 5) {
				startIdx = para[5];
				endIdx = para[6];

			}
			for (var i = startIdx; i <= endIdx; i++) {

				if (i == startIdx) {
					newCell = newRow.insertCell();
					newCell.style.display = "none";

				} else { newCell = newRow.insertCell(); }

				newCell.innerText = td.eq(i).text();
			};
			if (opt == "U") newRow.addEventListener("click", updateInputEvt)
			if (opt == "UM") newRow.addEventListener("click", ModalEvt)
		} else {
			if (plen > 5) {
				startIdx = para[4];
				endIdx = para[5];

			}
			for (var i = startIdx; i <= endIdx + 1; i++) {


				if (i == startIdx) {
					newCell = newRow.insertCell();
					newCell.style.display = "none";

				}
				else {
					newCell = newRow.insertCell();

				}
				if (i == endIdx + 1) {

					let newIcon;
					newIcon = document.createElement("i")
					newIcon.className = "bx bxs-trash btn ";
					newCell.appendChild(newIcon);
					newCell.addEventListener('click', () => Devt(td.eq(1).text()));




				} else {
					newCell.innerText = td.eq(i).text();
				}
			}
		}
	}

	CheckEvt(...para) {
		//pageName, tableclass, inputClass, option
		//pageName, tableclass, inputClass, option, startIdx, endIdx

		//pageName, tableclass, inputClass, option
		//pageName, tableclass, inputClass, option, startIdx, endIdx
		const set = new BtnFunctions;
		const checkRow = document.getElementsByClassName(para[0] + "ChkRow")
		const checkBox = document.getElementsByName(para[0] + "Chk");
		const Allcheck = document.getElementsByName(para[0] + "ChkAll");
		const plen = para.length;
		let opt;


		if (plen > 3) {
			opt = para[3];
		} else {
			opt = para[2];
		}

		let update_checkEvt = function() {
			let checkBtn = $(this);

			if (checkBtn.is(":checked")) {
				if (plen > 4) {
					set.SetRowTableData(checkBtn, para[0], para[1], para[2], para[3], para[4], para[5]);
				} else {

					set.SetRowTableData(checkBtn, para[0], para[1], para[2], para[3]);
				}
			} else {
				const len = document.getElementsByClassName(para[1])[0].rows.length;
				if (len > 1) {
					document.getElementsByClassName(para[1])[0].deleteRow(-1);
				}

			}


		}
		let delete_checkEvt = function() {

			let checkBtn = $(this);

			if (checkBtn.is(":checked")) {
				if (plen > 4) {
					set.SetRowTableData(checkBtn, para[0], para[1], para[2], para[3], para[4]);
				} else {
					set.SetRowTableData(checkBtn, para[0], para[1], para[2]);
				}
			} else {
				const len = document.getElementsByClassName(para[1])[0].rows.length;
				if (len > 1) {
					document.getElementsByClassName(para[1])[0].deleteRow(-1);
				}

			}


		}
		let update = function() {

			if (!this.classList.contains('active')) { removeEventListener('click', this.update); }
			checkBox.forEach(function(element) {
				element.checked = false;

			})
			set.createHiddenInput(para[2]);
			if (checkRow[0].style.display == "none") {
				for (var i = 0; i < checkRow.length; i++)checkRow[i].style.display = "";
			}

			Allcheck[0].addEventListener('click', function() {
				let checkBtn = $(this);

				if (checkBtn.is(":checked")) {
					checkBox.forEach(function(element) {
						element.checked = true;
						let chkbox = $(element);
						if (plen > 4) {
							set.SetRowTableData(chkbox, para[0], para[1], para[2], para[3], para[4], para[5]);
						} else {

							set.SetRowTableData(chkbox, para[0], para[1], para[2], para[3]);
						}

					})
				} else {
					checkBox.forEach(function(element) {
						element.checked = false;
						const len = document.getElementsByClassName(para[1])[0].rows.length;
						if (len > 1) {
							document.getElementsByClassName(para[1])[0].deleteRow(-1);
						}
					})
				}

			})
			for (var i = 0; i < checkBox.length; i++) {

				checkBox[i].removeEventListener('click', delete_checkEvt);
				checkBox[i].addEventListener('click', update_checkEvt);
			}
			if (para[0] === "DownTime")
				set.updateBtn(para[0], para[2]);
		}
		let deletes = function() {
			if (!this.classList.contains('active')) { removeEventListener('click', this.deletes); }
			checkBox.forEach(function(element) {
				element.checked = false;

			})
			if (checkRow[0].style.display == "none") {
				for (var i = 0; i < checkRow.length; i++)checkRow[i].style.display = "";
			}
			Allcheck[0].addEventListener('click', function() {
				let checkBtn = $(this);

				if (checkBtn.is(":checked")) {
					checkBox.forEach(function(element) {
						element.checked = true;
						let chkbox = $(element);
						if (plen > 4) {
							set.SetRowTableData(chkbox, para[0], para[1], para[2], para[3], para[4]);
						} else {
							set.SetRowTableData(chkbox, para[0], para[1], para[2]);
						}
					})
				} else {
					checkBox.forEach(function(element) {
						element.checked = false;
						const len = document.getElementsByClassName(para[1])[0].rows.length;
						if (len > 1) {
							document.getElementsByClassName(para[1])[0].deleteRow(-1);
						}
					})
				}

			})
			for (var i = 0; i < checkBox.length; i++) {
				checkBox[i].removeEventListener('click', update_checkEvt)
				checkBox[i].addEventListener('click', delete_checkEvt)
			}
		}


		// 체크 박스 활성/비활성화


		if (opt == "U" || opt == "UM") {
			const checkboxEvt = document.getElementsByClassName(para[0] + "Update")[0]
			checkboxEvt.addEventListener('click', update);
		} else {
			const checkboxDEvt = document.getElementsByClassName(para[0] + "Delete")[0]
			checkboxDEvt.addEventListener('click', deletes)
		}


	}
	//세션 작업 필수
	// 행 모달 버튼


	// 업데이트 버튼 이벤트
	updateButtonEvt(...para) {
		const obj = localStorage.getItem("Open");
		const data = JSON.parse(obj);
		const url = data.url;
		let page = para[0];
		if (url.indexOf("Management") > 0) page += "Management";
		if (url.indexOf("Master") > 0) page += "Master";
		if (page.toUpperCase() === url.toUpperCase()) {
			const setRow = new BtnFunctions;
			if (para.length > 4) {
				setRow.CheckEvt(para[0], para[1], para[2], para[3], para[4], para[5]);
			} else {
				setRow.CheckEvt(para[0], para[1], para[2], para[3]);
			}
		}
	}

	// 등록 버튼 이벤트
	createButtonEvt(inputClass, pageName, hasModal) {
		const obj = localStorage.getItem("Open");
		const data = JSON.parse(obj);
		const url = data.url;
		let page = pageName;
		if (url.indexOf("Management") > 0) page += "Management";
		if (url.indexOf("Master") > 0) page += "Master";
		if (page.toUpperCase() === url.toUpperCase()) {
			const evt = new BtnFunctions;
			const create = document.getElementsByClassName(pageName + "Create")[0];
			const checkCol = document.getElementsByClassName(pageName + "ChkRow")
			const input = document.getElementsByClassName(inputClass)
			if (create.classList.contains('active') && pageName == "company") {
				evt.detail(pageName);
			}
			let createEvt = function() {

				if (!this.classList.contains('active')) { removeEventListener('click', this.createEvt); if (hasModal == true) evt.getDetail(pageName, this) }


				if (create.classList.contains('active') && pageName == "company") {
					evt.detail(pageName);
				}

				for (let i = 0; i < checkCol.length; i++) {
					checkCol[i].style.display = "none";
				}
				for (let i = 0; i < input.length; i++) {
					input[i].value = "";
				}



			}
			create.addEventListener('click', createEvt);
		}
	}
	deleteButtonEvt(...para) {
		//button className,pageName, tableClass, option
		const obj = localStorage.getItem("Open");
		const data = JSON.parse(obj);
		const url = data.url;
		let page = para[1];
		if (url.indexOf("Management") > 0) page += "Management";
		if (url.indexOf("Master") > 0) page += "Master";

		if (page.toUpperCase() === url.toUpperCase()) {
			const checkBox = document.getElementsByName(para[1] + "Chk");
			const newEvt = new BtnFunctions;
			$("." + para[0]).on('click', function() {
				let check_len = checkBox.length;
				let chkArr = new Array();
				let cnt = 0;
				for (var i = 0; i < check_len; i++) {
					if (checkBox[i].checked == true) {
						chkArr[cnt] = checkBox[i].value;
						cnt++;
					}
				}
				axios.get("SelectDelete" + para[1], {
					params: { chkArr: chkArr.join(",") }
				})
					.then(function(response) {
						alert("저장 되었습니다.")
						alert("삭제")
						console.log(response.data);
					})
					.catch(function(error) {
						console.log(error);
					}).finally(function() {
						location.reload()
					});

			})
			if (para.length > 4) {
				newEvt.CheckEvt(para[1], para[2], para[3], para[4], para[5]);
			} else {
				newEvt.CheckEvt(para[1], para[2], para[3]);
			}
		}
	}
	subUpdate(pageName) {
		let evt = function() {

			if (this.classList.contains("active")) { removeEventListener('click', this.evt); return; }

			const sub = new BtnFunctions;
			const tbody = document.getElementsByClassName(pageName + "-M-list")[0];
			const inputs = document.getElementsByClassName(pageName + "-m-control");
			const tr = tbody.children;
			let len = tr.length;
			const checkCol = document.getElementsByClassName(pageName + "ChkRow")
			for (let i = 0; i < checkCol.length; i++) {
				checkCol[i].style.display = "none";
			}
			sub.trTotable(pageName);
			if (len == 1) {
				alert("거래처를 선택해 주세요")
				for (let i = 0; i < inputs.length; i++) {
					inputs[i].disabled = true;
				}
			}
		}
		const subs = document.getElementsByClassName(pageName + "Responsibility")[0]
		subs.addEventListener('click', evt)

	}
	timeValueCheck() {

		const preTime = document.getElementsByName("startTime");
		const afterTime = document.getElementsByName("downTime");
		let timeCompare = function(pre, after) {
			var startTime = pre.value;
			var downTime = after.value;
			var blank = ""
			console.log(startTime)
			console.log(downTime)
			if (downTime < startTime && startTime != blank && downTime != blank) {
				alert("시작시간이 종료시간보다 빠르도록 설정해 주세요.")
				pre.value = blank;
				after.value = blank;
			}
		}

		for (var i = 0; i < preTime.length; i++) {
			let pre = preTime[i];
			let after = afterTime[i];
			after.addEventListener('blur', event => timeCompare(pre, after));
			pre.addEventListener('blur', event => timeCompare(pre, after));
		}
	}
	updateBtn(pageName, input) {
		const updateButton = document.getElementById("update-" + pageName + "-btn");
		updateButton.addEventListener('click', function() {
			const uInput = document.getElementsByClassName(input)
			const values = [];
			for (var i = 0; i < uInput.length; i++) {
				values[i] = uInput[i].value;
				console.log(values[i])
			}
			const params = {
				"id": parseInt(uInput[0].value),
				"title": uInput[1].value,
				"startTime": uInput[2].value,
				"downTime": uInput[3].value,
				"remark": uInput[4].value
			}
			const csrf = document.getElementsByName("_csrf")[0].value;
			axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
			axios.post("update" + pageName, params)
				.then(function(response) {
					const ret = response.data;

					if (ret.ret == false) alert("수정 실패");
					else {
						const target = document.getElementsByClassName(ret.id)[0];
						const target2 = document.getElementsByClassName(ret.id + "mini")[0];
						target2.remove();
						console.log(values)
						const td = target.children;
						for (var i = 0; i < values.length; i++) {
							td[i + 1].innerText = values[i];
						}


					}
				}).catch(function(e) { console.log(e) });
		})

	}
	// select 라인 가져오기
	getSelectData() {

		const csrf = document.getElementsByName("_csrf")[0].value;
		axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
		axios.post('CommonCodeList').then(function(response) {
			console.log(response.data)
		})
	}
	getDetail(pageName) {

		let MainTrEvt = function() {
			if (document.getElementsByClassName(pageName + "Create")[0].classList.contains("active")) {


				const td = this.children;
				const id = td[1].innerText;

				try {
					axios.get("Select" + pageName, {

						params: {
							id: id
						}
					}).then(function(response) {
						console.log("여기는?")
						console.log(pageName)
						const ret = response.data;
						console.log(ret)
						$("#" + pageName + "-Modal").modal();
						const inputs = document.getElementsByClassName(pageName + "-detail");
						const title = document.getElementById(pageName + "Name");
						for (var i = 0; i < inputs.length; i++) {
							inputs[i].disabled = true;
						}
						const { name, standard, productCode, lastUpdateDate, productGroup, productFeature, defaultSalePrice, defaultLocation, unitType, quantitiesType, unitCount, properQuentity, stockCount }
							= ret;
						title.innerText = "제품명: " + name;
						inputs[0].value = name;
						inputs[1].value = standard;
						inputs[2].value = productCode;
						//inputs[3].value = productGroup.value;
						//inputs[4].value = productFeature.value;
						inputs[5].value = defaultSalePrice;
						//inputs[6].value = defaultLocation;
						//inputs[7].value = unitType;
						//inputs[8].value = quantitiesType;
						inputs[9].value = unitCount;
						inputs[10].value = properQuentity;
						inputs[11].value = stockCount;


					}).catch(function(e) { console.log(e) })
				} catch (e) {
					console.log(e)
				}
			}
		}
		let MainTr = document.getElementById(pageName + "Body").children;


		for (let i = 0; i < MainTr.length; i++) {
			MainTr[i].addEventListener('click', MainTrEvt)
		}
	}

	axiosToInputs(pageName, id) {
		console.log(id)

		axios.get('Select' + pageName, {
			params: {
				id: id
			}
		})
			.then(function(response) {
				console.log("material update")
				console.log("pageName:" + pageName)
				const ret = response.data;
				console.log(ret)
				const inputs = document.getElementsByClassName(pageName + "-u");
				const selects = document.getElementsByClassName(pageName + "-Select" + "-u");
				const { id, name, standard, productCode, remark, lastUpdateDate, productGroup, productFeature, defaultLocation, defaultSalePrice, unitType, quantitiesType, unitCount, properQuentity, stockCount, size, price }
					= ret;

				if (pageName == "Material") {
					inputs[0].value = id;
					inputs[1].value = name;
					inputs[2].value = productCode;
					inputs[3].value = standard;
					inputs[4].value = defaultSalePrice;
					inputs[5].value = unitCount;
					inputs[6].value = properQuentity;
					inputs[7].value = stockCount;
				}
				if (pageName == "Product") {
					inputs[0].value = id;
					inputs[1].value = name;
					inputs[2].value = productCode;
					inputs[3].value = size;
					inputs[4].value = price;
					inputs[5].value = remark;
				}
				//selects[0].value = productGroup != null?productGroup.value:0
				//selects[1].value = productFeature != null?productFeature.value:0;
				//selects[2].value = defaultLocation != null?defaultLocation.value:0;
				//selects[3].value = unitType != null?unitType.value:0;
				//selects[4].value = quantitiesType!= null?quantitiesType.value:0;

			}).catch(function(e) {
				console.log(e);
			})

		const updateButton = document.getElementById("update-" + pageName + "-btn");
		updateButton.addEventListener('click', function() {
			const inputs = document.getElementsByClassName(pageName + "-u");
			const values = [];
			for (var i = 0; i < inputs.length; i++) {
				values[i] = inputs[i].value;
			}
			console.log("update button");
			let params;
			if (pageName == "Material") {
				params = {
					id: inputs[0].value,
					name: inputs[1].value,
					standard: inputs[2].value,
					productCode: inputs[3].value,
					defaultSalePrice: inputs[4].value,
					unitCount: inputs[5].value,
					properQuentity: inputs[6].value,
					stockCount: inputs[7].value
				}
			}
			if (pageName == "Product") {
				params = {
					id: inputs[0].value,
					name: inputs[1].value,
					productCode: inputs[2].value,
					size: inputs[3].value,
					price: inputs[4].value,
					remark: inputs[5].value
				}
			}

			const csrf = document.getElementsByName("_csrf")[0].value;
			axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
			axios.post("update" + pageName, params)
				.then(function(response) {
					const ret = response.data;

					if (ret.ret == false) alert("수정 실패");
					else {
						const target = document.getElementsByClassName(ret.id)[0];
						const target2 = document.getElementsByClassName(ret.id + "mini")[0];
						target2.remove();

						const td = target.children;
						const { id, name, standard, size, price, remark, productCode, lastUpdateDate, productGroup, productFeature, defaultLocation, defaultSalePrice, unitType, quantitiesType, unitCount, properQuentity, stockCount }
							= ret;

						if (pageName == "Material") {
							td[2].innerText = name;
							td[3].innerText = productCode;
							td[4].innerText = standard;
							td[5].innerText = defaultSalePrice;
							td[6].innerText = unitCount;
							td[7].innerText = properQuentity;
							td[8].innerText = stockCount;
						}
						if (pageName == "Product") {
							td[2].innerText = name;
							td[3].innerText = productCode;
							td[4].innerText = size;
							td[5].innerText = price;
							td[6].innerText = remark;
						}


					}
				}).catch(function(e) { console.log(e) })
				.finally(function() {
					for (var i = 0; i < inputs.length; i++) {
						inputs[i].value = "";

					}
				});
		})


	}
	dateFormat(date) {
		let month = date.getMonth() + 1;
		let day = date.getDate();
		let hour = date.getHours();
		let minute = date.getMinutes();
		let second = date.getSeconds();

		return date.getFullYear() + '-' + month + '-' + day + 'T' + hour + ':' + minute + ':' + second;
	}

	NotModelAxiosToInputs(pageName) {
		const set = new BtnFunctions;
		console.log(pageName)
		const updateButton = document.getElementById("update-" + pageName + "-btn");
		updateButton.addEventListener('click', function() {
			const inputs = document.getElementsByClassName(pageName + "-u");
			const values = [];
			const csrf = document.getElementsByName("_csrf")[0].value;
			axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;

			for (var i = 0; i < inputs.length; i++) {
				values[i] = inputs[i].value;
			}
			//ErrorCode, Workingline, Division에 대한 CRUD작업 수행 
			switch (pageName) {
				case "ErrorCode": {
					const params = {
						id: inputs[0].value,
						errorCode: inputs[1].value,
						errorType: inputs[2].value,
						errorMeasure: inputs[3].value
					}

					axios.post("update" + pageName, params)
						.then(function(response) {
							const ret = response.data;
							console.log(ret)
							if (ret.ret == false) alert("수정 실패");
							else {
								const target = document.getElementsByClassName(ret.id)[0];
								const target2 = document.getElementsByClassName(ret.id + "mini")[0];
								target2.remove();

								const td = target.children;
								const { errorCode, errorType, errorMeasure, createDate } = ret;

								td[2].innerText = errorCode;
								td[3].innerText = errorType;
								td[4].innerText = errorMeasure;
								var date = new Date(createDate[0], createDate[1], createDate[2], createDate[3], createDate[4], createDate[5])
								td[5].innerText = set.dateFormat(date);
								alert("수정완료")
								location.reload();
							}
						}).catch(function(e) { console.log(e) })
						.finally(function() {
							for (var i = 0; i < inputs.length; i++) {
								inputs[i].value = "";

							}
						});
					break;
				}
				case "WorkingLine": {
					let location = document.getElementById("workingline-update-location")
					let selectData = location.options[location.selectedIndex].value
					console.log(selectData)
					let data = document.getElementById("update-workingline");
					let form = new FormData(data);

					/*const params = {
						"id": inputs[0].value,
						"line": inputs[1].value,
						"remarks": inputs[2].value,
						"location.id": selectData,
						"index":0
					}
					form.append('id',inputs[0].value)
					form.append('line')*/
					axios.post("update" + pageName, form)
						.then(function(response) {
							const ret = response.data;
							console.log(ret)
							alert("수정");
							window.location.reload();
							if (ret === null) alert("수정 실패");
							else {
								const target = document.getElementsByClassName(ret.id)[0];
								const target2 = document.getElementsByClassName(ret.id + "mini")[0];
								target2.remove();

								const td = target.children;
								const { line, remarks, } = ret;

								td[2].innerText = line;
								td[3].innerText = remarks;
								//td[4].innerText = location;
								td[5].innerText = workload;
								td[6].innerText = mRequirement;
							}
						}).catch(function(e) { console.log(e) })
						.finally(function() {
							for (var i = 0; i < inputs.length; i++) {
								inputs[i].value = "";

							}
						});

					break;
				}
				case "Division": {
					console.log("s")//params: 수정 창에서 입력한 내용
					const params = {
						id: inputs[0].value,
						division: inputs[1].value, //부서명
						workInfo: inputs[2].value, //업무
						// workInfo: inputs[3].value //팀명 
					}
					console.log(params.id)
					console.log(params.division)
					console.log(params.team)//정상출력
					console.log(params.workInfo)

					axios.post("update" + pageName, params)
						.then(function(response) {
							const ret = response.data;
							console.log("1Param:")
							console.log(params)
							console.log("2Ret:")
							console.log(ret)
							if (ret === null) alert("수정 실패");
							else {
								const target = document.getElementsByClassName(ret.id)[0];
								console.log(ret.id)
								console.log(ret.division)
								console.log(ret.team)//undefined
								console.log(ret.workInfo)
								const target2 = document.getElementsByClassName(ret.id + "mini")[0];
								target2.remove();

								const td = target.children;
								const { division, workInfo } = ret;
								console.log("2ndret")
								console.log(ret)

								td[2].innerText = division;
								td[3].innerText = workInfo;
								//td[3].innerText = team;
								//td[4].innerText = workInfo;
								console.log("innerTexts")
								console.log(division)
								//console.log(team)
								console.log(workInfo)
							}
						}).catch(function(e) { console.log(e) })
						.finally(function() {
							for (var i = 0; i < inputs.length; i++) {
								inputs[i].value = "";
							}
						});

					break;
				}
			}
		})


	}

	getTypes(SelectCommonCodeBox, inputClassName) {

		axios.get("types")
			.then(function(response) {
				const ret = response.data;
				const classification = [];
				const selectBox = document.getElementsByClassName(SelectCommonCodeBox)[0];
				const frag = document.createDocumentFragment();
				const input = document.getElementsByClassName(inputClassName);
				const optLen = selectBox.children.length;
				if (optLen === 1) {
					ret.forEach(function(arr, i) {
						const option = document.createElement("option");
						option.value = arr[1];
						option.innerText = arr[1];
						classification[i] = arr[1];
						frag.append(option);
					})
					selectBox.appendChild(frag);

					selectBox.addEventListener('change', function() {

						const arr = ret[classification.indexOf(this.value)];
						const codeId = arr[0];
						const columnCode = arr[2];
						if (inputClassName === "register-u") {
							input[1].value = codeId;
							input[4].value = columnCode;
						} else {
							input[0].value = codeId;
							input[3].value = columnCode;
						}
					})

				}
			}).catch(function(e) {
				console.log(e);
			})
	}
	DynamicOption(SelectBoxElement, values, text) {
		const selectBox = SelectBoxElement;
		const frag = document.createDocumentFragment();
		for (var i = 0; i < values.length; i++) {
			const newOption = document.createElement('option');
			if (i == 0) {
				newOption.selected = true;
			}
			newOption.value = values[i];
			newOption.innerText = text[i]
			frag.append(newOption);
		}
		selectBox.appendChild(frag);

	}
	getSelectOptData(element) {
		const opt = new BtnFunctions;
		const select = document.getElementsByClassName(element);
		axios.get("SelectNeedLists")
			.then(function(response) {
				console.log(element)
				console.log(response)
				const result = response.data;
				console.log(result)
				const values = [];
				const text = [];
				values.length = 0;
				text.length = 0;

				if (result.MT.length == 0) {
					values[0] = 0;
					text[0] = "정보 없음"
					opt.DynamicOption(select[3], values, text);
				} else {
					result.MT.forEach(function(data, i) {
						values[i] = data.id;
						text[i] = data.value;
					})
					opt.DynamicOption(select[3], values, text);
				}
				/*if(result.LT.length==0){
					values[0] = 0;
					text[0] = "정보 없음"
					opt.DynamicOption(select[2],values,text);
				}else{
					
					result.LT.forEach(function(data,i){
						console.log(data)
						values[i] = data.id;
						text[i]= data.value;
					})
					opt.DynamicOption(select[2],values,text);
				}*/
				if (result.MG.length == 0) {
					values[0] = 0;
					text[0] = "정보 없음"
					opt.DynamicOption(select[0], values, text);
				} else {
					result.MG.forEach(function(data, i) {
						values[i] = data.id;
						text[i] = data.value;
					})
					opt.DynamicOption(select[0], values, text);
				}
				if (result.MF.length == 0) {
					values[0] = 0;
					text[0] = "정보 없음"
					opt.DynamicOption(select[1], values, text);
				} else {
					result.MF.forEach(function(data, i) {
						values[i] = data.id;
						text[i] = data.value;
					})
					opt.DynamicOption(select[1], values, text);
				}
				if (result.QT.length == 0) {
					values[0] = 0;
					text[0] = "정보 없음"
					opt.DynamicOption(select[4], values, text);
				} else {
					result.QT.forEach(function(data, i) {
						values[i] = data.id;
						text[i] = data.value;
					})
					opt.DynamicOption(select[4], values, text);
				}


			})


	}
	/*uploadEvts() {
		const input = document.getElementById("file-regi");
		input.addEventListener('change', function() {
			let filename = input.files[0].name;

			let label = document.getElementsByClassName("file-select-name")[0];
			label.innerText = filename;
		})
		function readImage(input) {
			
			if (input.files && input.files[0]) {
				
				const reader = new FileReader()
				// 이미지가 로드가 된 경우
				reader.onload = e => {
					const previewImage = document.getElementById("preview")
					previewImage.style.removeProperty('max-height')
					previewImage.style.setProperty('height','100%');
					previewImage.style.setProperty('width','100%');
					previewImage.style.setProperty('margin-top','0px');
					
					previewImage.src = e.target.result
				}
				// reader가 이미지 읽도록 하기
				reader.readAsDataURL(input.files[0])
			}
		}
		const inputImage = document.getElementById("file-regi")
		inputImage.addEventListener("change", e => {
			readImage(e.target)
		})
	}*/

	loadLocation() {

	}
	CreateProduct() {
		const inputs = document.getElementById("createProductForm")
		const textData = document.getElementsByClassName("Product-c");
		const inputData = document.getElementsByClassName("Product-Select-c");
		const btn = document.getElementById("createProductRegi")
		textData[1].addEventListener('focusout', function() {
			if (textData[1].value.length) {
				console.log(textData[1].value)
				axios.get('isRegi', { params: { productCode: textData[1].value } })
					.then(function(res) {
						console.log(res.data)
						if (res.data === true) {
							alert("등록된 제품 코드입니다.")
						} else {

							btn.addEventListener('click', function() {
								let form = new FormData(inputs);
								let state = true;
								[...inputData].forEach(function(data) {
									if (parseInt(data.value) === 0) {
										form.delete(data.getAttribute('name'))
									};
								});
								[...textData].forEach(function(data) {
									if (data.value === "") {
										state = false;
									}
								})
								if (!!form.get(key) === false && key != "remarks") {
									alert("빈 항목이 있습니다.")
									return;
								};

								axios.post('createProduct', form);
								/*for (var key of form.keys()) {
											console.log(key)
											console.log(form.get(key))
										
										}*/

							})
						}
					})
			}
		})
	}

	readExcel() {
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
				let csrf = document.getElementsByName("_csrf")[0].value;
				axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
				axios.post("productExcel", formData)
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
}