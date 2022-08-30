/**
 * 
 */

export class OrderHistoryEvt {
	drawOption(select, res) {
		select.options.length = 0;
		const frag = document.createDocumentFragment();
		if (select.children.length > 0) {
			for (let i = 0; i < select.children.length; i++) {
				select.children[i].remove();
			}
		}
		if (res.length != 0) {
			for (let i = 0; i < res.length; i++) {
				let newOption = document.createElement("option");
				newOption.value = res[i].id;
				newOption.innerText = res[i].name;
				if (i == 0) {
					newOption.selected = true;
				}
				frag.append(newOption);

			}

		} else {
			let newOption = document.createElement("option");
			newOption.value = 0;
			newOption.innerText = "데이터 없음";
			frag.append(newOption);
		}
		select.appendChild(frag);
	}
	loadProduct() {
		const select = document.getElementsByClassName("Orderhistory-Select-c")[0];
		const select_update = document.getElementsByClassName("Orderhistory-Select-u")[0];
		const value = document.getElementsByName("isMaterial")[0];
		const value_update = document.getElementsByClassName("Orderhistory-u")[0];
		const evt = new OrderHistoryEvt();
		axios.get("MaterialLists")
			.then(function(res) {
				evt.drawOption(select, res.data)
				evt.drawOption(select_update, res.data)
			}).catch(function(e) { console.log(e) });
		value.addEventListener('change', function() {

			if (this.value === "true") {
				axios.get("MaterialLists")
					.then(function(res) { evt.drawOption(select, res.data) })
					.catch(function(e) { console.log(e) })


			} else {
				axios.get("productLists").then(function(res) {
					evt.drawOption(select, res.data)
				}).catch(function(e) { console.log(e) })
			}
		})
		value_update.addEventListener('change', function() {

			if (this.value === "true") {
				axios.get("MaterialLists")
					.then(function(res) { evt.drawOption(select_update, res.data) })
					.catch(function(e) { console.log(e) })


			} else {
				axios.get("productLists").then(function(res) {
					evt.drawOption(select_update, res.data)
				}).catch(function(e) { console.log(e) })
			}
		})

	}
	loadCompany() {
		const select = document.getElementsByClassName("Orderhistory-Select-c")[1];
		const select_update = document.getElementsByClassName("Orderhistory-Select-u")[1];
		const evt = new OrderHistoryEvt();
		axios.get("companyLists")
			.then(function(res) {
				evt.drawOption(select, res.data);
				evt.drawOption(select_update, res.data);
			})
			.catch(function(e) {
				console.log(e)
			})
	}
	addOrder() {

		document.getElementsByClassName("deleteRow")[0].addEventListener('click', function() {
			const tbody = document.getElementById("OrderLists")
			const tr = tbody.children;
			if (tr.length > 0) {
				[...tr].forEach(function() {
					tbody.deleteRow(-1);
				})
			}
		})
		document.getElementById("addOrder").addEventListener('click', function() {
			console.log("수신")
			const tbody = document.getElementById("OrderLists");
			const values = document.getElementsByClassName("Orderhistory");
			const trash = document.getElementsByClassName("deleteRow")[0].cloneNode();
			var ret = +values[2].value
			if (ret === 0) {
				alert("수량이 0 입니다.")
				return;
			}
			if (!!values[5].value === false) {
				alert("주문일이 입력되지 않았습니다.")
				return;
			} else if (!!values[6].value === false) {
				alert("도착예정일이 입력되지 않았습니다.")
				return;
			}
			if (!!document.getElementsByClassName(values[1].value)[0] && !!document.getElementsByClassName(values[4].value)[0]) {
				alert("해당 회사 제품이 존재 합니다.")
				return;
			}
			let newRow = tbody.insertRow();
			for (let i = 0; i < values.length; i++) {
				if(i == 3)
					continue;
				let newCell = newRow.insertCell();
				let value = values[i].value
				
				if (i < 2 || i === 4) {
					newCell.className = value;
					value = values[i].options[values[i].selectedIndex].innerText;

				}
				let text = document.createTextNode(value);
				newCell.appendChild(text);
				
				if (i > 3) newCell.style.display = "none";
				else if (i === 0) newCell.style.width = "22%";
				else newCell.style.width = "25%";
			}
			let last = newRow.insertCell();
			last.appendChild(trash);
			last.style.width = "3%";

			last.addEventListener('click', function() {
				tbody.deleteRow(last.parentNode.rowIndex);

			})

		})
		const evt = new OrderHistoryEvt();
		document.getElementById("orderRegister").addEventListener('click', function() {
			evt.createOrder();
		})
	}
	predateNotInput(){
		const preC = document.getElementsByClassName("Orderhistory")[5];
		console.log(preC)
		preC.addEventListener('change',function(){
			const afterC= document.getElementsByClassName("Orderhistory")[6];
		 	afterC.setAttribute('min',preC.value)
		})
		
		const preU = document.getElementsByClassName("Orderhistory-u")[5];
		
		preU.addEventListener('change',function(){
			const afterU = document.getElementsByClassName("Orderhistory-u")[6];
		 	afterU.setAttribute('min',preU.value)
		})
		
		
		console.log(preC.value)
	}
	addUpdateOrder() {

		const trash = document.getElementsByClassName("deleteRow")[0].cloneNode();
		document.getElementById("update-addOrder").addEventListener('click', function() {

			const tbody = document.getElementById("update-OrderLists");
			const id = document.getElementById("updateDocID");
			const values = document.getElementsByClassName("Orderhistory-u");
			var ret = +values[2].value
			console.log(!!id.value)
			if (parseInt(id.value) ===0 || !!id.value != true) {
				alert("항목이 선택 되지 않았습니다.")
				return;
			}

			if (ret === 0) {
				alert("수량이 0 입니다.")
				return;
			}
			if (!!values[5].value === false) {
				alert("주문일이 입력되지 않았습니다.")
				return;
			} else if (!!values[6].value === false) {
				alert("도착예정일이 입력되지 않았습니다.")
				return;
			}
			if (!!document.getElementsByClassName(values[1].value)[0] && !!document.getElementsByClassName(values[4].value)[0]) {
				alert("해당 회사 제품이 존재 합니다.")
				return;
			}
			console.log(!!document.getElementsByClassName(id.value)[0])
			if (!!document.getElementsByClassName(id.value)[0] === false) {
				let newRow = tbody.insertRow();
				for (let i = 0; i < values.length; i++) {
					let newCell = newRow.insertCell();
					let value = values[i].value
					if (i < 2 || i === 4) {
						newCell.className = value;
						value = values[i].options[values[i].selectedIndex].innerText;

					}
					let text = document.createTextNode(value);
					newCell.appendChild(text);
					if (i > 3) newCell.style.display = "none";
					else if (i === 0) newCell.style.width = "22%";
					else newCell.style.width = "25%";
				}
				let last = newRow.insertCell();
				last.appendChild(trash);
				last.style.width = "3%";

				last.addEventListener('click', function() {
					tbody.deleteRow(last.parentNode.rowIndex);

				})
			} else {
				const tr = document.getElementsByClassName(id.value)[0]
				const td = tr.children;
				const inputs = document.getElementsByClassName("Orderhistory-u");
				let idx = 1;
				let oriIdx = 0;
				let tmpIdx = 0;
				let titleIdx = 0;
				let ret = false;
				let ori = [];
				let tmp = [];
				let changeTitle = [];


				[...inputs].forEach(function(e, i) {
					if (td[idx].innerText != e.value) {
						if (i === 0 || i === 1 || i === 4) {
							if (e.value != td[idx].className && i != 4) {

								changeTitle[titleIdx++] = i;
								ori[oriIdx++] = td[idx].className;
								tmp[tmpIdx++] = e.value;
							} else if (i === 4 && td[5].innerText != e.options[e.selectedIndex].innerText) {

								changeTitle[titleIdx] = i;
								ori[oriIdx++] = td[5].innerText;
								tmp[tmpIdx++] = e.options[e.selectedIndex].innerText;
							}
						} else {

							changeTitle[titleIdx++] = i;
							ori[oriIdx++] = td[idx].innerText;
							tmp[tmpIdx++] = e.value;

						}


					}
					idx++;

				});


				let str = "";
				const title = ["제품 유형", "제품", "주문량", "매입가", "거래처", "주문일", "도착 예정일"];
				ori.forEach(function(data, i) {

					let titles = title[changeTitle[i]]
					str += titles + ":" + " " + data + " >> " + tmp[i] + "로 변경\n"

				})
				if (str != "") ret = confirm(str);
				else { alert("변경 정보 없음") }
				idx = 1;
				if (ret) {
					[...inputs].forEach(function(e, i) {

						if (td[idx].innerText != e.value) {
							if (i === 0 || i === 1 || i === 4) {
								if (e.value != td[idx].className && i != 4) {
									td[idx].className = e.value
									td[idx].innerText = e.options[e.selectedIndex].innerText;

								} else if (i === 4) {
									td[idx].className = e.value
									td[idx].innerText = e.options[e.selectedIndex].innerText;
								}
							} else {
								td[idx].innerText = e.value;

							}

						}
						idx++;

					})
				} else {
					return;
				}
			}
		})
	}
	addOrderProducts() {
		document.getElementById("update-createOrder").addEventListener('click', function() {
			let tr = document.getElementById("update-OrderLists").children;
			const values = document.getElementsByClassName("Orderhistory-u");
			const product = values[1].value;
			const companyInputs = values[4];
			let company = companyInputs.options[companyInputs.selectedIndex].innerText
			let state = false;
			var ret = +values[2].value
			if (ret === 0) {
				alert("수량이 0 입니다.")
				return;
			}
			if (!!values[5].value === false) {
				alert("주문일이 입력되지 않았습니다.")
				return;
			} else if (!!values[6].value === false) {
				alert("도착예정일이 입력되지 않았습니다.")
				return;
			}
			for (let i = 0; i < tr.length; i++) {
				let td = tr[i].children;

				if (td[2].className === product && td[5].innerText === company) {
					alert("이미 추가된 거래처 제품입니다.")
					state = true;
					break;

				}
			}

			if (state === false) {
				const tbody = document.getElementById("update-OrderLists");
				const values = document.getElementsByClassName("Orderhistory-u");
				const trash = document.getElementsByClassName("deleteRow")[0].cloneNode();
				var ret = +values[2].value
				if (ret === 0) {
					alert("수량이 0 입니다.")
					return;
				}
				if (!!values[5].value === false) {
					alert("주문일이 입력되지 않았습니다.")
					return;
				} else if (!!values[6].value === false) {
					alert("도착예정일이 입력되지 않았습니다.")
					return;
				}
				if (!!document.getElementsByClassName(values[1].value)[0] && !!document.getElementsByClassName(values[4].value)[0]) {
					alert("해당 회사 제품이 존재 합니다.")
					return;
				}

				let newRow = tbody.insertRow();
				newRow.className = "newProduct"
				let IDCell = newRow.insertCell();
				IDCell.innerText = 0;
				IDCell.style.display = "none";
				for (let i = 0; i < values.length; i++) {
					if(i == 3){
						continue;
					}
					let newCell = newRow.insertCell();
					let value = values[i].value

					if (i < 2 || i === 4) {
						newCell.className = value;
						value = values[i].options[values[i].selectedIndex].innerText;

					}

					let text = document.createTextNode(value);
					newCell.appendChild(text);
					if (i > 3) newCell.style.display = "none";
					else if (i === 1) newCell.style.width = "22%";
					else newCell.style.width = "25%";
				}
				
				
				let last = newRow.insertCell();
				last.appendChild(trash);
				last.style.width = "3%";

				last.addEventListener('click', function() {
					tbody.deleteRow(last.parentNode.rowIndex);

				})
				const evt = new OrderHistoryEvt();
				evt.eraseInput();
			} else return;
		})
	}
	ordersUpdate() {
		document.getElementById("orders-update").addEventListener('click', function() {
			let tr = document.getElementById("OrderHistoryContents").children;
			let miniTr = document.getElementById("update-OrderLists").children;
			let changeData = [];
			let createData =[];
			let updateData = [];
			const form = new FormData;
			let changetitle = document.getElementsByClassName("UpdateDoctitle")[0].value;
			let oriTitle = tr[0].className;
			let title = "정보 없음"
			if(oriTitle != title){
				title = changetitle
			}else{
				title = oriTitle
			}
			for (let i = 0; i < miniTr.length; i++) {

				let state = false
				let td = tr[i].children;
				let miniTd = miniTr[i].children;
				if (parseInt(miniTd[0].innerText) != 0) {
					console.log(typeof miniTd[0].innerText)
					if (td[0].innerText === miniTd[0].innerText) {
						let orderDate = miniTd[6].innerText.split("-");
						let date = orderDate[0]+"-"+parseInt(orderDate[1])+"-"+parseInt(orderDate[2])
						let plan = miniTd[7].innerText.split("-");
						let plDate = plan[0]+"-"+parseInt(plan[1])+"-"+parseInt(plan[2])
						
						for (var j = 1; j < 4; j++) {
							if (td[j].innerText != miniTd[j].innerText) state = true;
							
						}
						if (td[4].innerText != miniTd[5].innerText)state = true; 
						
						if (td[5].innerText != miniTd[4].innerText + "원") state = true;
						
						if (td[6].innerText != date) state = true;
						if (td[7].innerText != plDate) state = true;

					}
					console.log(state)
					if (state) {
						changeData[i] = td[0].innerText;
					}
				} else {
					
						let obj = {

								"isMaterial": miniTd[1].className,
								"product_id": miniTd[2].className,
								"product": miniTd[2].innerText,
								"OrderCount": miniTd[3].innerText,
								"price": miniTd[4].innerText,
								"company.id": miniTd[5].className,
								"orderDate": miniTd[6].innerText,
								"plannedArriveDate": miniTd[7].innerText
							}

							createData.push(obj);
				}
			}
			form.append("createData", JSON.stringify(createData));
			if(changeData.length!=0){
			
			changeData.forEach(function(data) {
				let td = document.getElementsByClassName(data)[0].children;
				
				let uobj = {
					"id": td[0].innerText,
					"isMaterial": td[1].className,
					"product_id": td[2].className,
					"product": td[2].innerText,
					"OrderCount": td[3].innerText,
					"price": td[4].innerText,
					"company.id": td[5].className,
					"orderDate": td[6].innerText,
					"plannedArriveDate": td[7].innerText
				}
				updateData.push(uobj);
				
			})
			form.append("updateData", JSON.stringify(updateData))
			}else{
				alert("변경된 항목이 없습니다.")
			}
			if(updateData.length>0 || createData.length>0){
			const csrf = document.getElementsByName("_csrf")[0].value;
			axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
			axios.post('updateOrderHistory/' + title, form)
			.then(function(res) {
				if(res.data === true){
				location.reload();
				}else{
					alert("수정 오류")
				}
				console.log("success")
			})
			.catch(function(e) { console.log(e) })
			console.log("수정 완료")
			}else{
				alert("수정 되거나 추가된 항목이 없습니다.")
				return;
			}
			console.log(updateData.length)
			console.log(createData.length)
			console.log(createData)
			console.log(updateData)
			
		})
	}
	eraseInput() {
		const inputs = document.getElementsByClassName("Orderhistory-u");
		const id = document.getElementById("updateDocID")
		id.value = 0;
		for (let i = 0; i < inputs.length; i++) {
			if (i === 0 || i === 1 || i === 4) {
				inputs[i].value = inputs[i].children[0].value;
			} else {
				inputs[i].value = "";
			}
		}
	}
	createOrder() {
		const form = new FormData;
		const datas = [];
		const tr = document.getElementById("OrderLists").children;
		const title = document.getElementsByClassName("Doctitle")[0].value;

		[...tr].forEach(function(data) {
					console.log(data);
			const obj = {

				"isMaterial": data.children[0].className,
				"product_id": data.children[1].className,
				"product": data.children[1].innerText,
				"OrderCount": data.children[2].innerText,
//				"price": data.children[3].innerText,
				"company.id": data.children[3].className,
				"orderDate": data.children[4].innerText,
				"plannedArriveDate": data.children[5].innerText
				
			}

			datas.push(obj);
		})
		form.append("data", JSON.stringify(datas))
		console.log(datas)
		const csrf = document.getElementsByName("_csrf")[0].value;
		axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
		axios.post('CreateOrderHistory/' + title, form)
			.then(function(res) {
				location.reload();
			})
			.catch(function(e) { console.log(e) })
	}


	dateFormat(Y, M, D) {

		if (M < 10) {
			M = "0" + M;
		}
		if (D < 10) {
			D = "0" + D;
		}
		return Y + "-" + M + "-" + D;
	}
	selectDoc() {
		const tr = document.getElementById("OrderHistoryBody").children;
		const updateTab = document.getElementsByClassName('OrderUpdate')[0];
		const deletetab = document.getElementsByClassName('OrderDelete')[0];
		if (tr.length > 0) {

			[...tr].forEach(function(e) {
				e.addEventListener('click', function() {
					const title = e.children[1].innerText;
					document.getElementsByClassName("UpdateDoctitle")[0].value = title;
					for (let i = 0; i < tr.length; i++) {
						tr[i].style.backgroundColor = "";
					}
					e.style.backgroundColor = "#f5f5dc"
					let td = this.children;
					let DocNm = td[0].innerText;
					if (!!DocNm) {
						const params = {
							Doc: DocNm

						}
						const targetTr = document.getElementById("OrderHistoryContents").children;
						axios.get("SelectByDocNum", { params })
							.then(function(res) {
								[...targetTr].forEach(function(tr) {
									if(tr.hasAttribute('class')){
									tr.removeAttribute( 'class' );
									}
									const tTd = tr.children;
									[...tTd].forEach(function(td) {
										td.innerText = "-"
									})
								})
								const datas = res.data;

								[...datas].forEach(function(data, i) {
									targetTr[i].className = title;
									const td = targetTr[i].children;
									td[0].innerText = data.id;
									td[1].innerText = data.material != false ? "원/부자재" : "완제품";
									td[2].innerText = data.product;
									td[3].value = data.product_id;
									td[3].innerText = data.orderCount;
									td[4].innerText = data.companyName;
									//td[5].innerText = data.price + "원";
									td[6].innerText = data.orderDate[0] + "-" + data.orderDate[1] + "-" + data.orderDate[2]
									td[7].innerText = data.plannedArriveDate[0] + "-" + data.plannedArriveDate[1] + "-" + data.plannedArriveDate[2]
									td[8].innerText = data.realArriveDate != null ? data.realArriveDate : "정보 없음"
									td[9].innerText = data.manager
									td[10].innerText = data.complete != false ? "완료" : "미완료"
									
								})

								if (updateTab.classList.contains('active')) {
									const evt = new OrderHistoryEvt();
									const tbodys = document.getElementById("update-OrderLists");
									const tr = tbodys.children;
									evt.eraseTable(tbodys);
									evt.eraseInput();
									evt.drawTable(tbodys, datas);
									if (tr.length > 0) {
										[...datas].forEach(function(data, i) {
											if(data.complete !=true){
												console.log(data);
											const td = tr[i].children;
											tr[i].className = data.id;
											td[0].innerText = data.id;
											td[1].innerText = data.material != false ? "원/부자재" : "완제품";
											td[1].className = data.material
											td[2].innerText = data.product;
											td[2].className = data.product_id;
											td[3].innerText = data.orderCount;
										//	td[4].innerText = data.price;
											td[6].innerText = evt.dateFormat(data.orderDate[0], data.orderDate[1], data.orderDate[2])
											td[7].innerText = evt.dateFormat(data.plannedArriveDate[0], data.plannedArriveDate[1], data.plannedArriveDate[2])
										}
										})
									}


								} 
								
								if(deletetab.classList.contains('active')){
									
									let trash = document.getElementsByClassName("deleteRow")[0].cloneNode();
									let td = e.children;
									const tbody = document.getElementById("DocDeleteLists");
									
									if(!!document.getElementsByClassName(td[0].innerText)[0]!= true){
									
									let row = tbody.insertRow();
									row.className= td[0].innerText;
									let text1 = document.createTextNode(td[0].innerText);
									let text2 = document.createTextNode(td[1].innerText)
									let Cell1 = row.insertCell();
									Cell1.append(text1);
									let Cell2 = row.insertCell();
									Cell2.append(text2);
									Cell1.style.width = "50%";
									Cell2.style.width = "50%";
									let last = row.insertCell();
									last.appendChild(trash); 
									trash.addEventListener("click",function(){
										tbody.deleteRow(last.parentNode.rowIndex);
									})
									}									
									
								}
							}).catch(function(e) {
								console.log(e);
							})

					}
				})
			})
		}

	}
	deleteDocButton(){
		const documentDelete = document.getElementById('document-delete'); 
		const tbody = document.getElementById("DocDeleteLists");
		documentDelete.addEventListener('click',function(){
			let tr = tbody.children;
			let documents = [];
			[...tr].forEach(function(data,i){
			documents[i] = data.children[0].innerText;
			})
			console.log(documents);
			const params = {
				"DocNum":documents.join(",")
			}							
			axios.get('deleteDoc',{params})
			.then(function(res){
				if(res.data!= true){
					alert("삭제 실패")
				}else{
					alert("삭제 완료")
					location.reload();
				}
			})
			})
	}
	eraseTable(tbody) {
		const tr = tbody.children;
		if (tr.length > 0) {
			for (let i = 0; i <= tr.length; i++) {
				tbody.deleteRow(-1);
			}
		}

	}
	drawTable(tbody, datas) {
		const id = document.getElementById("updateDocID");
		const inputs = document.getElementsByClassName("Orderhistory-u")

		for (let i = 0; i < datas.length; i++) {
			const trash = document.getElementsByClassName("deleteRow")[0].cloneNode();
			var newRow = tbody.insertRow();
			for (let i = 0; i < 9; i++) {
				if(i == 4){
					continue;
				}
				
				let newCell = newRow.insertCell();
				if (i > 4 || i === 0) newCell.style.display = "none";
				else newCell.style.width = "25%";

			}
			let last = newRow.insertCell();
			last.appendChild(trash);
			last.style.width = "3%";

			last.addEventListener('click', function() {
				let id = last.parentNode.children[0].innerText;
				const params = {
					"id": id
				}
				
				axios.get("deleteEachOrderItems", {params})
				.then(function(res){
					if(res.data !=false){
					tbody.deleteRow(last.parentNode.rowIndex);
					}
				})
			})
			newRow.addEventListener('click', function() {
				const td = this.children
				if (td[0].innerText != " ") {
					id.value = td[0].innerText;
					inputs[0].value = td[1].className;
					inputs[1].value = td[2].className;
					inputs[2].value = td[3].innerText;
					inputs[3].value = td[4].innerText;
					let options = inputs[4].children;
					[...options].forEach(function(el) {
						if (el.innerText === td[5].innerText) {
							inputs[4].value = el.value;
						}
					})
					inputs[5].value = td[6].innerText;
					inputs[6].value = td[7].innerText;
				}
			})
		}

	}

	createTab() {
		const evt = new OrderHistoryEvt()
		const createTab = document.getElementsByClassName("OrderCreate")[0];
		createTab.addEventListener('click', function() {
			if (createTab.classList.contains('active')) {
				evt.addOrder();
			}
		})
	}
	UpdateTab() {
		const evt = new OrderHistoryEvt()
		const UpdateTab = document.getElementsByClassName("OrderUpdate")[0];
		UpdateTab.addEventListener('click', function() {
			if (UpdateTab.classList.contains('active')) {
				evt.addUpdateOrder();
			}
		})
	}

}