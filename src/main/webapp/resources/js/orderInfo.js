
export class OrderInfo {
	stepNextEvt() {
		//jQuery time
		var current_fs, next_fs; //fieldsets
		var opacity; //fieldset properties which we will animate
		var animating; //flag to prevent quick multi-click glitches

		if (animating) return false;
		animating = true;

		current_fs = $(this).parent().parent().parent().parent();
		next_fs = $(this).parent().parent().parent().parent().next();
		//activate next step on progressbar using the index of next_fs
		if (document.getElementsByClassName("OrderCreate")[0].classList.contains('active')) {
			$("#orderInfo-step li").eq($("fieldset").index(next_fs)).addClass("active");
		} else {
			$("#orderInfo-update li").eq(1).addClass("active");
		}

		//show the next fieldset
		next_fs.show();
		//hide the current fieldset with style
		current_fs.animate({ opacity: 0 }, {
			step: function(now, mx) {

				opacity = 1 - now;
				current_fs.css({
					'position': 'absolute'
				});
				next_fs.css({ 'opacity': opacity });
			},
			duration: 500,
			complete: function() {
				current_fs.hide();
				animating = false;
			},
			//this comes from the custom easing plugin
			easing: 'easeInOutBack'
		});
	}
	
	stepPreiousEvt() {
		var current_fs, previous_fs; //fieldsets
		var opacity; //fieldset properties which we will animate
		var animating; //flag to prevent quick multi-click glitches
		if (animating) return false;
		animating = true;

		current_fs = $(this).parent().parent().parent();
		previous_fs = $(this).parent().parent().parent().prev();

		//de-activate current step on progressbar
		console.log(document.getElementsByClassName("OrderCreate")[0].classList.contains('active'))
		if (document.getElementsByClassName("OrderCreate")[0].classList.contains('active')) {
			$("#orderInfo-step li").eq($("fieldset").index(current_fs)).removeClass("active");
		} else {
			$("#orderInfo-update li").eq(1).removeClass("active");
		}
		//show the previous fieldset
		previous_fs.show();
		//hide the current fieldset with style
		current_fs.animate({ opacity: 0 }, {
			step: function(now, mx) {
				//as the opacity of current_fs reduces to 0 - stored in "now"
				//1. scale previous_fs from 80% to 100%
				//scale = 0.8 + (1 - now) * 0.2;
				//2. take current_fs to the right(50%) - from 0%
				//left = ((1 - now) * 1) + "%";
				//3. increase opacity of previous_fs to 1 as it moves in
				opacity = 1 - now;
				//current_fs.css({ 'left': left });
				previous_fs.css({ 'opacity': opacity });
			},
			duration: 500,
			complete: function() {
				current_fs.hide();
				animating = false;
			},
			//this comes from the custom easing plugin
			easing: 'easeInOutBack'
		});

	}
	stepEvtForm() {
		const evt = new OrderInfo
		$(".next").on('click', evt.stepNextEvt);

		$(".previous").on('click', evt.stepPreiousEvt);

	}

	fileInputEvt() {
		const designButton = document.getElementById("uploadSamleImges");
		const sampleList = document.getElementById("SampleList");
		var fileData = document.getElementById("sampleUpload");
		designButton.addEventListener('click', function() { fileData.click() })
		const datas = new DataTransfer();
		fileData.addEventListener('change', function() {

			if (fileData.files.length > 0) {
				for (var i = 0; i < fileData.files.length; i++) {
					datas.items.add(fileData.files[i]);
					var Row = sampleList.insertRow();
					Row.className = fileData.files[i].lastModified
					var num = Row.insertCell();
					var cell = Row.insertCell();
					var deleteCell = Row.insertCell();
					cell.innerText = fileData.files[i].name
					num.innerText = num.parentNode.rowIndex + 1;
					deleteCell.innerHTML = "&times;"
					deleteCell.addEventListener('click', function() {
						datas.items.remove(this.parentNode.rowIndex);
						fileData.files = datas.files;
						sampleList.deleteRow(this.parentNode.rowIndex);
						console.log(fileData.files);
					})

				}
			}
			console.log(datas.files)
		})

	}
	getAddr() {
		const inputs = document.getElementsByClassName("addressForm");
		const trash = document.getElementsByClassName("deleteRegiLocationRow")[0];
		const tbody = document.getElementById("constructionLocationLists");
		const regibody = document.getElementById("registerConsLocation");
		const finalTbody = document.getElementById("constructionRegiLocaitonLists");
		const updateFinalTbody = document.getElementById("constructionUpdateLocaitonLists");
		document.getElementById("OpenLatRegiModal").addEventListener('click', function() {
			$('#LatLngRegister').modal('show');

			let confirmTr = finalTbody.children;
			let tr = regibody.children;
			if (tr.length > 0) {
				[...tr].forEach(function() {
					regibody.deleteRow(-1);
				});
			}
			if (confirmTr.length > 0) {
				[...confirmTr].forEach(function() {
					finalTbody.deleteRow(-1);
				});
			}
		})

		document.getElementById("OpenLatUpdateModal").addEventListener('click', function() {

			$('#LatLngRegister').modal('show');
			console.log(document.getElementById('LatLngRegister').classList)
			let confirmTr = updateFinalTbody.children;
			let tr = regibody.children;
			if (tr.length > 0) {
				[...tr].forEach(function() {
					regibody.deleteRow(-1);
				});
			}
			if (confirmTr.length > 0) {
				[...confirmTr].forEach(function() {
					finalTbody.deleteRow(-1);
				});
			}
		})


		document.getElementsByClassName("newConstructionLocation")[0].addEventListener('click', function() {
			document.getElementById("regiAddressModal").classList.remove('flip-Effect')
		});


		document.getElementById("SearchAddress").addEventListener('click', function() {
			new daum.Postcode({

				oncomplete: function(data) {
					inputs[1].value = data.zonecode;
					inputs[2].value = data.roadAddress;
					inputs[3].value = data.jibunAddress;
				}
			}).open();
		})

		document.getElementsByClassName("addLatCancer")[0].addEventListener('click', function() {
			document.getElementById("regiAddressModal").classList.add('flip-Effect');
			[...inputs].forEach(function(input) {
				input.value = "";
			})
		})

		document.getElementsByClassName("addLat")[0].addEventListener('click', function() {

			document.getElementById("regiAddressModal").classList.add('flip-Effect');
			let datas = document.getElementById("latRegisterForm");
			let form = new FormData(datas);
			let state = true;
			[...inputs].forEach(function(input) {
				if (!!input.value === false) {
					state = false;
				};
			})
			if (state === false) {
				alert("입력되지 않은 항목이 있습니다.")
				return;
			}
			for (var key of form.keys()) {
				console.log(key);
			}
			for (var value of form.values()) {
				console.log(value);
			}
			axios.post('createLatLng', form).then(function(res) {
				console.log(res.data)
				const data = res.data;
				let row = tbody.insertRow();
				row.className = data.id;
				let cell = row.insertCell();
				cell.style.display = "none";
				let cell2 = row.insertCell();
				cell2.style.width = "20%";
				let cell3 = row.insertCell();
				cell3.style.width = "60%";
				let cell4 = row.insertCell();
				cell4.style.width = "20%";

				row.children[0].innerText = data.id;
				row.children[1].innerText = data.title;
				row.children[2].innerText = data.address;
				row.children[3].innerText = data.detail;

				row.addEventListener('click', function() {

					let statess = false;
					if (regibody.children.length > 0) {
						for (var i = 0; i < regibody.children.length; i++) {

							if (regibody.children[i].className === this.className) {
								statess = true
								break;
							}
						}
					}
					if (statess === true) {
						alert("이미 추가된 장소 입니다.")

					}

					else if (statess === false) {

						let Row = this.cloneNode(true);
						let newCell = Row.insertCell()
						let ico = trash.cloneNode(true);
						ico.addEventListener("click", function() {
							regibody.deleteRow(this.parentNode.parentNode.rowIndex)
						})
						/*Row.children[1].style.width = "30%";
						Row.children[2].style.width = "47%";
						Row.children[3].style.width = "20%";
						Row.children[4].style.width = "3%";*/
						
						newCell.appendChild(ico);
						regibody.appendChild(Row);
						
						
					}
				})

			}).catch(function(e) { console.log(e) });
			[...inputs].forEach(function(input) {
				input.value = "";
			})

		})


		let tr = tbody.children;
		if (tr.length > 0) {
			[...tr].forEach(function(trs) {

				trs.addEventListener('click', function() {
					let statess = false;
					if (regibody.children.length > 0) {
						for (var i = 0; i < regibody.children.length; i++) {

							if (regibody.children[i].className === this.className) {
								statess = true
								break;
							}
						}
					}
					if (statess === true) {
						alert("이미 추가된 장소 입니다.")

					}

					else if (statess === false) {

						let Row = this.cloneNode(true);
						let newCell = Row.insertCell()
						let ico = trash.cloneNode(true);
						ico.addEventListener("click", function() {
							regibody.deleteRow(this.parentNode.parentNode.rowIndex)
						})
						newCell.appendChild(ico);
						regibody.appendChild(Row);
					}
				})
			})
		}
		const update = document.getElementsByClassName("OrderUpdate")[0];
		document.getElementById("confirmLocation").addEventListener('click', function() {
			let regiTr = regibody.children
			if (!update.classList.contains('active')) {

				if (regiTr.length === 0) {
					alert("등록된 지역이 없습니다.")
					return;
				} else {

					[...regiTr].forEach(function(tr) {
						let Row = tr.cloneNode(true)
						Row.deleteCell(-1);
						finalTbody.appendChild(Row);
					});
					$('#LatLngRegister').modal('hide');


				}
			} else {
				if (regiTr.length === 0) {
					alert("등록된 지역이 없습니다.")
					return;
				} else {

					[...regiTr].forEach(function(tr) {
						let Row = tr.cloneNode(true)
						let cell = Row.insertCell(1);
						cell.style.display="none";
						cell.innerText = 0;
						updateFinalTbody.appendChild(Row);
					});
					$('#LatLngRegister').modal('hide');


				}
			}

		})


	}

	drawOption(select, res) {

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
	drawDivisionOption(select, res) {

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
				newOption.innerText = res[i].team;
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
		const select = document.getElementById("productOption");
		const selectPr = document.getElementById("productUpdateOption");
		const evt = new OrderInfo();
		axios.get("productLists")
			.then(function(res) {
				evt.drawOption(select, res.data);
				evt.drawOption(selectPr, res.data);
			}).catch(function(e) { console.log(e) });

	}

	addOrderProductsButton() {
		const trash = document.getElementsByClassName("orderDeleteRow")[0];
		const productsInputs = document.getElementsByClassName("orderedProduct")
		const tbody = document.getElementById("OrderLists");
		let total = parseInt(document.getElementById("TotalPrice").value);

		trash.parentNode.addEventListener('click', function() {
			let tr = tbody.children;
			if (tr.length > 0) {
				[...tr].forEach(function() {
					tbody.deleteRow(-1);
				})
			}

		})
		document.getElementById("addOrderProducts").addEventListener('click', function() {
			const tr = tbody.children;
			if (parseInt(productsInputs[1].value) === 0) {
				alert("수량이 입력되지 않았습니다.")
				return;
			}
			let value = productsInputs[0].value;
			axios.get('SelectProduct', {
				params: {
					id: value
				}
			}).then(function(res) {
				let state = false;

				[...tr].forEach(function(tr) {
					if (tr.className === value) {
						let cnt = parseInt(tr.children[1].innerText);
						cnt += parseInt(productsInputs[1].value);
						tr.children[1].innerText = cnt;
						tr.children[2].innerText = cnt * parseInt(res.data.defaultSalePrice);
						state = true;
					}
				})
				if (state === false) {
					let row = tbody.insertRow();
					row.className = productsInputs[0].value;
					let text = productsInputs[0].options[productsInputs[0].selectedIndex].text
					let cell = row.insertCell();
					cell.style.width = "40%"
					cell.innerText = text;
					let cell2 = row.insertCell();
					cell2.style.width = "27%"
					cell2.innerText = parseInt(productsInputs[1].value);
					let cell3 = row.insertCell();
					cell3.style.width = "30%"
					cell3.innerText = parseInt(res.data.defaultSalePrice) * parseInt(productsInputs[1].value);
					let del = row.insertCell();
					del.style.width = "3%"
					let delCon = trash.cloneNode(true);

					delCon.addEventListener('click', function() {
						tbody.deleteRow(this.parentNode.parentNode.rowIndex);
						let total = parseInt(document.getElementById("TotalPrice").value);
						total -= parseInt(this.parentNode.parentNode.children[2].innerText)
						document.getElementById("TotalPrice").value = total;
					})
					del.appendChild(delCon);
				}
			}).catch(function(e) {
				console.log(e);
				alert("에러")
				return;
			}).finally(function(e) {
				productsInputs[1].value = 0;
				let totals = 0;
				if (tr.length > 0) {
					[...tr].forEach(function(tr) {

						totals += parseInt(tr.children[2].innerText);
					})
				}
				document.getElementById("TotalPrice").value = totals;
			})





		})

	}
	loadCompany() {
		const select = document.getElementById("companyOption");
		const updateSelect = document.getElementById("companyOptionForUpdate");
		const evt = new OrderInfo();
		axios.get("companyLists")
			.then(function(res) {
				evt.drawOption(select, res.data);
				evt.drawOption(updateSelect, res.data);
			})
			.catch(function(e) {
				console.log(e)
			})
	}

	registerButton() {
		document.getElementById("suju-button").addEventListener('click', function() {
			var fileData = document.getElementById("sampleUpload");
			let datas = document.getElementById("sujuData");
			/*기본 데이터*/
			let form = new FormData(datas);

			/*제품 Map 데이터*/
			const tbody = document.getElementById("OrderLists");
			let tr = tbody.children;
			let productDatas =[];
			[...tr].forEach(function(tr) {
				let td = tr.children;
				let datas = {
				product_id:tr.className,
				cnt:td[1].innerText
				}
				productDatas.push(datas);	
			})
			form.append("products",JSON.stringify(productDatas));
			/*이미지 smaple upload 처리*/
			console.log(fileData.files)
			for (var j = 0; j < fileData.files.length; j++) {
				form.append('file', fileData.files[j])
			}
			const csrf = document.getElementsByName("_csrf")[0].value;
			axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
			axios.post('createReceivedOrderInfo', form)
				.then(function(res) {
					console.log(res.data)
					if (res.data.result) {
						location.reload();
						/*
						document.getElementsByClassName("next")[0].disabled = false;
						document.getElementsByClassName('sujuTitle')[1].value = res.data.title;
						document.getElementById("suju_id").value = res.data.id;*/
						
						
					}
				})
				.catch(function(e) { console.log(e) });


		})
	}
	loadTrEvt() {
		const evt = new OrderInfo();
		const tbody = document.getElementById("OrderHistoryContents");

		let tr = tbody.children;
		[...tr].forEach(function(tr) {
			tr.addEventListener("click", function() {
				let create = document.getElementsByClassName("OrderCreate")[0];
				let update = document.getElementsByClassName("OrderUpdate")[0];
				let id = this.children[0].innerText;
				let title = this.children[1].innerText;
				let state = this.children[6].innerText;
				let company_id = this.children[4].className;
				let tr = this;
				axios.get('getReceivedOrderInfo/' + id)
					.then(function(res) {
						/*console.log(res.data.products)
						console.log(res.data.urls)
						console.log(res.data.ConstructionPlan);
						console.log(state)*/
						console.log(res.data.ConstructionPlan);
						evt.drawItems(res.data.urls)
						evt.drawProducts(res.data.products)
						evt.drawPlan(res.data.ConstructionPlan);
						if (create.classList.contains('active')) {
							
							evt.changeRightPageDependingOnState(state, title, id, company_id);
						} else if (update.classList.contains('active')) {
							
							evt.updateFieldSetEvt(title, res.data.products)
							evt.addUpdateOrderProductsButton()
							evt.updateTrForUpdate();
							evt.getTrToInput(tr, res.data.products, res.data.urls, res.data.urlsID);
							evt.sujuUpdateEvt();
							evt.deleteSuju();
							evt.constructionPlanTrToTrForUpdate();
							evt.constructionPlanUpdateConfirmEvt();

						}
					})
					.catch(function(e) { console.log(e) })

			})
		})

	}
	drawPlan(plans) {
		let len = plans.length;
		const update = document.getElementsByClassName('OrderUpdate')[0];
		const trash = document.getElementsByClassName('ConstrucUpdateDeleteRow')[0];
		let tbody = document.getElementById("ConsLists")
		if(update.classList.contains('active')){
			tbody = document.getElementById('ConsUpdateLists')
		}
		if(tbody.children.length>0){
			let trLen = tbody.children.length
		for (var i = 0; i <trLen ; i++) {
			tbody.deleteRow(-1);
		}
		}
		if(len>0){
		for (var i = 0; i < len; i++) {
			let row = tbody.insertRow();
			row.className = plans[i].id;
			let cell = row.insertCell();
			cell.style.display = "none";
			cell.innerText = plans[i].id
			
			let cell1 = row.insertCell();
			cell1.style.display = "none";
			cell1.innerText = plans[i].teamId
			
			let cell2 = row.insertCell();
			cell2.style.width = "30%";
			cell2.innerText = plans[i].team
			
			let cell3 = row.insertCell();
			cell3.style.width = "30%";
			cell3.innerText = plans[i].place + " 지점"
			
			let cell4 = row.insertCell();
			cell4.style.width = "40%";	
			let data = plans[i].plan
			cell4.innerText = data[0] + "-" + data[1] + "-" + data[2];
			if(update.classList.contains('active')){
				cell4.style.width = "30%";
				let cell5 = row.insertCell();
				cell5.style.width = "10%";
				let deleteIco = trash.cloneNode(true);
				deleteIco.addEventListener('click',function(){
				tbody.deleteRow(this.parentNode.parentNode.rowIndex);
				let tr = this.parentNode.parentNode;
				let id = tr.children[0].innerText;
				let del = confirm("삭제 하시겠습니까?")
				if(del){
				axios.get('deleteConstructionPlan',{params:{
					id:id
				}})
				.then(function(res){
					console.log(res.data)
					if(res.data){
						alert("삭제 되었습니다.")
					}
				})
				.catch(function(e){
					console.log(e)
					alert("오류")
					return;
				})
				}
				})
			cell5.appendChild(deleteIco);
			}
			

		}
		}

	}
	drawItems(urls) {
		const indiBox = document.getElementById("indi");
		const indiBoxItems = document.getElementById("indi-Items");
		if (indiBox.children.length > 1) {
			while (indiBox.children.length > 1) {
				indiBox.lastChild.remove();
			}
			while (indiBoxItems.children.length > 1) {
				indiBoxItems.lastChild.remove();
			}
		}
		if (indiBox.children.length === 1) {

			[...urls].forEach(function(url, i) {
				if (i === 0) {
					indiBoxItems.children[0].children[0].src = "imageView?imagename=" + encodeURI(url);
				} else {
					let indiCator = document.getElementsByClassName("template-indi")[0].cloneNode(true);
					let item = document.getElementsByClassName("indi-item")[0].cloneNode(true);
					let indis = indiCator;
					let items = item;
					indis.classList.remove('active')
					indis.setAttribute("data-slide-to", i);
					items.classList.remove('active')
					items.children[0].src = "imageView?imagename=" + encodeURI(url);
					indiBox.appendChild(indis);
					indiBoxItems.appendChild(items);
				}
			})

		}
	}
	drawProducts(products) {
		const tr = document.getElementById("OrderInfoProducts").children;
		[...products].forEach(function(data, i) {
			let td = tr[i].children;
			td[0].innerText = data.id;
			td[1].innerText = data.code;
			td[2].innerText = data.name;
			td[3].innerText = data.cnt;
			td[4].innerText = data.Ep + "원";
			td[5].innerText = data.Total + "원";
		})
	}
	detailSample() {
		const indiBoxItems = document.getElementById("indi-Items");
		document.getElementById("detailSampleImg").addEventListener('click', function() {
			$('#myModal').modal('show');
			for (var i = 0; i < indiBoxItems.children.length; i++) {
				console.log(indiBoxItems.children[i].classList.contains('active') + ": " + i)
				if (indiBoxItems.children[i].classList.contains('active')) {
					document.getElementById("detailView").src = indiBoxItems.children[i].children[0].src;
					break;
				}
			}

		})
	}
	changeRightPageDependingOnState(state, title, id, company_id) {
		

		switch (state) {
			case "수주 작성 완료": {
				$(".msform:eq(0) fieldset").eq(0).hide();
				$("#orderInfo-step li").eq(1).addClass("active");
				document.getElementById("forLatLngCompany").value = company_id.substring(company_id.indexOf("-") + 1, company_id.length);
				/*	$("#order-process li").eq(1).addClass("active");*/
				$(".msform:eq(0) fieldset").eq(1).show();
				document.getElementById('suju_id').value = id;
				document.getElementsByClassName('sujuTitle')[1].value = title;
				document.getElementsByClassName("previous")[0].disabled = true;
				break;
			}
			case "시공 계획 작성 완료": {
				$("#order-process li").eq(1).addClass("active");
				$(".msform:eq(0) fieldset").eq(0).hide();
				$("#orderInfo-step li").eq(1).addClass("active");
				document.getElementById("forLatLngCompany").value = company_id;
				/*	$("#order-process li").eq(1).addClass("active");*/
				$(".msform:eq(0) fieldset").eq(1).show();
				document.getElementById('suju_id').value = id;
				document.getElementsByClassName('sujuTitle')[1].value = title;
				document.getElementsByClassName("previous")[0].disabled = true;
				break;
			}

		}
	}
	constructionPlanRegi() {
		//const regibody = document.getElementById("registerConsLocation");
		const tbody = document.getElementById("ConsLists")
		document.getElementById("constructionPlanRegiButton").addEventListener('click', function() {

			let form = document.getElementById('construction-Plan');
			let datas = new FormData(form);
			let suju = document.getElementById("suju_id").value;
			let planDate = document.getElementsByName("planConstructionDate")[0].value;
			let list_check = document.getElementById("constructionRegiLocaitonLists").children;

			if (!!suju === false) {
				alert("수주가 선택 되지 않았습니다.")
				return;
			}
			else if (!!planDate === false) {
				alert("시공 예상 날짜가 입력되지 않았습니다.")
				return;
			} else if (list_check.length === 0) {
				alert("시공 장소가 입력되지 않았습니다.")
				return;
			}

			if (list_check.length > 0) {
				[...list_check].forEach(function(tr) {
					datas.append("latlng", tr.className);
				})
			}
			for (var i = 0; i <= list_check.length; i++) {
				document.getElementById("constructionRegiLocaitonLists").deleteRow(-1)
			}

			axios.post('createConstructionPlan', datas)
				.then(function(res) {
					console.log(res.data)

					let plans = res.data;

					let row = tbody.insertRow();
					row.className = plans.id;
					let cell = row.insertCell();
					cell.style.display = "none";
					cell.innerText = plans.id
					let cell1 = row.insertCell();
					cell1.style.width = "30%";
					cell1.innerText = plans.team
					let cell2 = row.insertCell();
					cell2.style.width = "30%";
					cell2.innerText = plans.place + " 지점"
					let cell3 = row.insertCell();
					cell3.style.width = "40%";
					let data = plans.plan
					cell3.innerText = data[0] + "-" + data[1] + "-" + data[2];



				})
				.catch(function(e) { console.log(e) })
		})

	}

	loadConstructionDivisionList() {
		axios.get('getConstructionDivisionList').then(function(res) {
			let data = res.data;
			const evt = new OrderInfo;
			const select = document.getElementById('contructionTeam');
			const selectForUpdate = document.getElementById('contructionTeamForUpdate');
			evt.drawDivisionOption(select, data)
			evt.drawDivisionOption(selectForUpdate, data)

		})
	}
	orderCreateClick() {
		document.getElementsByClassName('OrderCreate')[0].addEventListener('click', function() {
			if (document.getElementsByClassName('OrderCreate')[0].classList.contains('active')) {
				location.reload();
			}
		})
	}
	updateFieldSetEvt(title, products) {
		const sujuTitle = document.getElementsByClassName('sujuTitle')[2];
		const productsTable = document.getElementById("OrderUpdateLists");
		const trashIcon = document.getElementsByClassName("orderUpdateDeleteRow")[0];
		//수주명 가져오기
		sujuTitle.value = title;

		let len = products.length;
		
		let tr = productsTable.children;
		if (tr.length != 0) {
			for (let i = 0; i <= tr.length; i++) {
				productsTable.deleteRow(-1);
			}
		}

		for (let i = 0; i < len; i++) {
			let row = productsTable.insertRow();
			let idCell = row.insertCell();
			idCell.style.display = "none";
			idCell.innerText = products[i].id;

			let productCell = row.insertCell();
			productCell.style.width = "40%";
			productCell.innerText = products[i].name;

			let orderCountCell = row.insertCell();
			orderCountCell.style.width = "27%";
			orderCountCell.innerText = products[i].cnt;

			let priceCell = row.insertCell();
			priceCell.style.width = "30%";
			priceCell.innerText = products[i].Total;

			let trashCell = row.insertCell();
			let icon = trashIcon.cloneNode(true);
			icon.addEventListener('click', function() {
				productsTable.deleteRow(this.parentNode.parentNode.rowIndex);
			})
			trashCell.appendChild(icon);

		}



	}
	updateTrForUpdate() {
		const tbody = document.getElementById("OrderUpdateLists");
		const inputs = document.getElementsByClassName("orderedProduct-u");
		let tr = tbody.children;
		if (tr.length > 0) {
			[...tr].forEach(function(tr) {
				tr.addEventListener('click', function() {
					let id = this.children[0].innerText;
					let cnt = this.children[2].innerText;
					inputs[0].value = id;
					inputs[1].value = cnt;

				})
			})
		}



	}
	getTrToInput(tr, products, urls, urlsID) {
		const inputs = document.getElementsByClassName("sujuForUpdate");
		let trash = document.getElementsByClassName("orderUpdateDeleteRow")[0]
		let id = tr.children[0].innerText;
		let companyOri = tr.children[4].className;
		let company_id = companyOri.substring(companyOri.indexOf("-") + 1);
		let date1 = tr.children[2].innerText;
		let date2 = tr.children[3].innerText;
		inputs[0].value = id;
		inputs[1].value = company_id;
		inputs[2].value = date1;
		inputs[3].value = date2;

		if (products.length != 0) {
			let Total = 0;
			[...products].forEach(function(data) {
				Total += data.Total;

			})
			document.getElementById("TotalPriceForUpdate").value = Total;
		}
		const tbody = document.getElementById("SampleDesignLists");
		for (let i = 0; i <= urls.length; i++) {
			tbody.deleteRow(-1);
		}
		if (urls.length != 0) {

			[...urls].forEach(function(data, i) {
				let row = tbody.insertRow();
				let cell = row.insertCell();
				cell.style.display = "none";
				cell.innerText = urlsID[i];
				let cell1 = row.insertCell();
				cell1.style.width = "5%";
				cell1.innerText = i + 1;
				let cell2 = row.insertCell();
				cell2.style.width = "80%";
				cell2.innerText = data.substring(data.indexOf("]") + 1, data.length);
				let icon = trash.cloneNode(true);
				trash.classList.remove("orderUpdateDeleteRow");;
				let cell3 = row.insertCell();
				cell3.style.width = "10%";
				icon.addEventListener('click', function() {
					tbody.deleteRow(this.parentNode.parentNode.rowIndex);
				})
				cell3.appendChild(icon);

			})
		}


	}

	addUpdateOrderProductsButton() {
		const trash = document.getElementsByClassName("orderUpdateDeleteRow")[0];
		const productsInputs = document.getElementsByClassName("orderedProduct-u")
		const tbody = document.getElementById("OrderUpdateLists");


		trash.parentNode.addEventListener('click', function() {
			let tr = tbody.children;
			if (tr.length > 0) {
				[...tr].forEach(function() {
					tbody.deleteRow(-1);
				})
			}

		})
		document.getElementById("addOrderProductsUpdate").addEventListener('click', function() {
			let tr = tbody.children;
			if (parseInt(productsInputs[1].value) === 0) {
				alert("수량이 입력되지 않았습니다.")
				return;
			}
			let value = productsInputs[0].value;

			axios.get('SelectProduct', {
				params: {
					id: value
				}
			}).then(function(res) {
				let state = false;
				console.log(res.data)
				for (let i = 0; i < tr.length; i++) {

					if (parseInt(tr[i].children[0].innerText) === parseInt(value)) {

						let cnt = parseInt(tr[i].children[2].innerText);
						if (cnt != productsInputs[1].value) {
							tr[i].children[2].innerText = productsInputs[1].value;
							tr[i].children[3].innerText = productsInputs[1].value * parseInt(res.data.defaultSalePrice);
						} else {
							alert("수량이 동일합니다.")
							return;
						}
						state = true;
						break;
					}
				}
				if (state === false) {
					let row = tbody.insertRow();
					row.className = productsInputs[0].value;
					let text = productsInputs[0].options[productsInputs[0].selectedIndex].text
					let cell = row.insertCell();
					cell.style.width = "40%"
					cell.innerText = text;
					let cell2 = row.insertCell();
					cell2.style.width = "27%"
					cell2.innerText = parseInt(productsInputs[1].value);
					let cell3 = row.insertCell();
					cell3.style.width = "30%"
					cell3.innerText = parseInt(res.data.defaultSalePrice) * parseInt(productsInputs[1].value);
					let del = row.insertCell();
					del.style.width = "3%"
					let delCon = trash.cloneNode(true);

					delCon.addEventListener('click', function() {
						tbody.deleteRow(this.parentNode.parentNode.rowIndex);
						let total = parseInt(document.getElementById("TotalPriceForUpdate").value);
						total -= parseInt(this.parentNode.parentNode.children[3].innerText)
						document.getElementById("TotalPriceForUpdate").value = total;
					})
					del.appendChild(delCon);
				}
			}).catch(function(e) {
				console.log(e);
				alert("에러")
				return;
			}).finally(function() {
				let Total = 0;

				[...tr].forEach(function(tr) {
					Total += parseInt(tr.children[3].innerText);
				})
				document.getElementById("TotalPriceForUpdate").value = Total;

			})


		})

	}
	sujuUpdateEvt() {
		const updateButton = document.getElementById("suju-update-button")
		const tbody = document.getElementById("OrderUpdateLists");
		const urlTbody = document.getElementById("SampleDesignLists");

		updateButton.addEventListener('click', function() {
			let state = confirm("수정 하시겠습니다까?")
			if(state === false)return;
			
			let form = document.getElementById("sujuUpdate")
			let data = new FormData(form);
			for (let key of data.keys()) {
				if (data.get(key).length === 0) {
					alert("빈 항목이 존재합니다.")
					return;
				}
			}

			let tr = tbody.children;
			let urlTr = urlTbody.children;
			// 제품 form 
			let products = [];
			for (let i = 0; i < tr.length; i++) {
				let td = tr[i].children;
				let product = {
					product_id:	parseInt(td[0].innerText), 
					cnt:parseInt(td[2].innerText)
				};
				products.push(product);
				
			}
			data.append("products", JSON.stringify(products));
			for (let j = 0; j < urlTr.length; j++) {
				let td = urlTr[j].children[0];
				data.append("urls", parseInt(td.innerText));
			}
			if (tr.length != 0 && urlTr.length != 0) {
				const csrf = document.getElementsByName("_csrf")[0].value;
				axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
				axios.post("updateRecivedOrderInfo", data)
					.then(function(res) {
						console.log(res.data);
						if (res.data) {
							location.reload();
						}
					})
					.catch(function(e) {
						location.reload();
						console.log(e)
					})
					.finally(function() {
						$(".msform:eq(1) fieldset").eq(0).hide();
						$("#orderInfo-update li").eq(1).addClass("active");
						//document.getElementById("forLatLngCompany").value = company_id;
						/*	$("#order-process li").eq(1).addClass("active");*/
						$(".msform:eq(1) fieldset").eq(1).show();
						//document.getElementById('suju_id').value = id;
						//document.getElementsByClassName('sujuTitle')[1].value = title;
						//document.getElementsByClassName("previous")[0].disabled = true;
					})
			}
			/*for(let key of data.keys()){
				console.log(key+": "+ data.get(key));
			}*/

		})
	}
	deleteSuju() {
		document.getElementById("sujuDelete").addEventListener('click', function() {
			let id = document.getElementsByClassName("sujuForUpdate")[0].value;
			if (!!id) {
				let state = confirm("정말 삭제하시겠습니까?")
				if (state) {
					let params = { "id": id }
					axios.get('deleteRecivedOrderInfo', { params })
						.then(function(res) {
							console.log(res.data)
							if (res.data) {
								location.reload();
							}
						})
						.catch(function(e) {
							console.log(e)
						})
				}
			} else {
				alert("선택된 수주가 없습니다.")
			}

		})
	}
	constructionPlanTrToTrForUpdate(){
		const tbody = document.getElementById('ConsUpdateLists')
		const locationTbody = document.getElementById('constructionUpdateLocaitonLists')
		const division = document.getElementById('contructionTeamForUpdate');
		const dataInput = document.getElementById("planConstructionDateForupdate");
		let tr = tbody.children;
		for(let i =0 ; i<tr.length;i++){
			tr[i].addEventListener('click',function(){
				let len = locationTbody.rows.length
				console.log(len)
				for(let i =0 ;i<=len;i++){
					locationTbody.deleteRow(-1)
				}
				let td =this.children;
				
				let id = td[0].innerText;
				if(!!id === false){
					return;
				}
				document.getElementById('constructionPlan_Id').value = id;
				let team = td[1].innerText;
				let planDate = td[4].innerText;
				division.value = team;
				dataInput.value = planDate;
				
				axios.get('selectConstructionPlanLocationList',{params:{id:id}})
				.then(function(res){
					console.log(res.data)
					let data = res.data;
					
					
					[...data].forEach(function(data){
					let trash = document.getElementsByClassName('ConstrucUpdateDeleteRowForLoction')[0].cloneNode(true);
					let id = data.Lat_id
					let consLocationID = data.id;
					let title = data.title
					let address =data.address
					let detail = data.detail
					
					let row = locationTbody.insertRow();
					let cell = row.insertCell();
					cell.style.display = "none";
					cell.innerText = id;
					
					let cells = row.insertCell();
					cells.style.display = "none";
					cells.innerText = consLocationID;
					
					let cell1 = row.insertCell();
					cell1.style.width = "19%";
					cell1.innerText = title;
					
					let cell2 = row.insertCell();
					cell2.style.width = "58%";
					cell2.innerText = address;
					
					let cell3 = row.insertCell();
					cell3.style.width = "20%";
					cell3.innerText = detail;
					
					let cell4 = row.insertCell();
					cell4.style.width = "3%";
					cell4.appendChild(trash);
					trash.addEventListener('click',function(){
						locationTbody.deleteRow(this.parentNode.parentNode.rowIndex);
					})
					})
				})
				.catch(function(e){
					console.log(e)
				})
			})
		}
	}
	eraseTable(tbody){
		let tr = tbody.children;
		for(let i =0; i<=tr.length;i++){
			tbody.deleteRow(-1);
		}
	}
	constructionPlanUpdateConfirmEvt(){
		const button = document.getElementById("constructionPlanUpdate-Confirm");
		const tbody = document.getElementById("constructionUpdateLocaitonLists");
		button.addEventListener('click',function(){
				const division = document.getElementById('contructionTeamForUpdate');
				const dataInput = document.getElementById("planConstructionDateForupdate");
				let plan_id = document.getElementById('constructionPlan_Id').value;
				if(!!plan_id === false){
					alert("입력되지 않은 항목이 있습니다.")
					return;
				}
				if(!!dataInput.value === false){
					alert("입력되지 않은 항복이 있습니다.")
					return;
				}
				let form = new FormData();
				form.append("id",plan_id);
				form.append("division",division.value);
				form.append("date",dataInput.value);
				
				let tr = tbody.children;
				console.log(plan_id);
				[...tr].forEach(function(el){
					let latlng_id = parseInt(el.children[0].innerText);
					let planLocation_id = parseInt(el.children[1].innerText);
					//console.log(el.children[0].innerText+" : "+el.children[1].innerText);
					if(planLocation_id===0){
						form.append("addLocation",latlng_id);
					}else{
						form.append("consLocation",planLocation_id);	
					}
				})
				const csrf = document.getElementsByName("_csrf")[0].value;
				axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
				axios.post('updateConstructionPlan',form)
				.then(function(res){
					console.log(res.data)
					if(res.data){
						location.reload();
					}
				})
				
		})
		
	}
	updateCheckButton(){
		const update = document.getElementsByClassName("OrderUpdate")[0];
		update.addEventListener("click",function(){
			alert("수정 항목을 선택해 주세요")
		})
	}

}

