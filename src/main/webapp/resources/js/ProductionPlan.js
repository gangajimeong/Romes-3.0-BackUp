import * as RESULT from './ResultCodes.js';
export default class ProductionPlan {
	setInfoEvt() {

		var orderInfoSelect = document.getElementById('production-recievedInfo');
		var orderProductSelect = document.getElementById('production-receivedProduct');
		var productSize = document.getElementById('production-productSpec');
		var workCount = document.getElementById('production-count');
		var locations = document.getElementById('production-direction');
		var company = document.getElementById('production-orderCompany');
		var isCoating = document.getElementById('production-isCoating');
		var isEmergency = document.getElementById('production-isEmergency');
		var workingLine = document.getElementById('production-workingLine');
		var isBack = document.getElementById('production-isBackPring');
		var startTime = document.getElementById('production-startTime');
		var endTime = document.getElementById('production-endTime');
		var releaseDate = document.getElementById('production-releaseDay');
		var save = document.getElementById('production-submit');
		var remark = document.getElementById('production-remark');
		save.addEventListener('click', function() {
			if (getSelectedValue(orderInfoSelect) == -1) {
				alert('수주 정보를 선택해 주세요.');
			} else if (getSelectedValue(orderProductSelect) == -1) {
				alert('품목 정보를 선택해 주세요.');
			} else if (getSelectedValue(workingLine) == -1) {
				alert('출력기를 선택해 주세요.');
			} else if (workCount.value == '') {
				alert('수량을 입력해 주세요.');
			} else if (startTime.value == '') {
				alert('시작시간을 입력해 주세요.');
			} else if (endTime.value == '') {
				alert('완료 시간을 입력해 주세요.')
			} else if (releaseDate.value == '') {
				alert('출하 날짜를 입력해 주세요.')
			} else if (getSelectedValue(locations) == -1) {
				alert('출고지를 선택해 주세요.')
			} else {

				var data = {
					userId: 0,
					orderId: getSelectedValue(orderInfoSelect),
					productId: JSON.parse(getSelectedValue(orderProductSelect)).id,
					count: workCount.value,
					lineId: getSelectedValue(workingLine),
					isCoating: getSelectedValue(isCoating),
					isBackPrint: getSelectedValue(isBack),
					isEmergency: getSelectedValue(isEmergency),
					startTime: startTime.value.replace("T", " "),
					endTime: endTime.value.replace("T", " "),
					releaseDate: releaseDate.value,
					direction: getSelectedValue(locations),
					remark: remark.value
				};
				console.log(data);
				axios.get('makeProductionPlan', { params: data }).then(function(response) {
					var resultcode = response.data.result;
					if (resultcode == RESULT.SUCCESS) {
						alert('저장되었습니다.')
						window.location.reload();
					}
				}).catch(function(error) {
					alert(error);
				})
			}

		});

		document.getElementById("select-suju").addEventListener('click', function() {
			if (parseInt(orderInfoSelect.value) === -1) {
				alert("선택된 수주가 없습니다.")
				return;
			}
			if (parseInt(orderProductSelect.value) === -1) {
				alert("선택된 품목이 없습니다.")
				return;
			} else {
				stepNextEvtIdx(this);
			}
		})

		orderInfoSelect.addEventListener('change', function() {
			var id = getSelectedValue(this);
			var orderInfo;
			var data = JSON.parse(document.getElementById('production-data').innerText);
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == id) {
					orderInfo = data[i];
					break;
				}
			}

			removeAllChild(orderProductSelect, false);
			removeAllChild(locations, false);
			company.value = orderInfo.company;
			for (var i = 0; i < orderInfo.products.length; i++) {
				var selection = orderProductSelect.children[0].cloneNode(false);
				console.log(orderInfo.products[i])

				selection.value = JSON.stringify(orderInfo.products[i]);
				selection.innerText = orderInfo.products[i].product;
				orderProductSelect.appendChild(selection);
			}
			for (var i = 0; i < orderInfo.locations.length; i++) {
				var selection = locations.children[0].cloneNode(false);
				selection.value = orderInfo.locations[i].id;
				selection.innerText = orderInfo.locations[i].title;
				locations.appendChild(selection);
			}
			changeImage(orderInfo.urls);
		});

		orderProductSelect.addEventListener('change', function() {
			var item = getSelectedValue(orderProductSelect);
			if (item == "-1") {
				productSize.value = "품목을 선택해 주세요";
				workCount.value = "0";

			} else {

				var productInfo = JSON.parse(item);
				productSize.value = productInfo.size;
				workCount.value = productInfo.count;
			}
		});

		const detailBtn = document.getElementsByClassName("detailForProductionPlanBtn");
		[...detailBtn].forEach((btn) => {
			btn.addEventListener('click', DetailForProductionPlanEvt);
		})

		const productionplanSubmit = document.getElementsByClassName("productionplanSubmit");
		[...productionplanSubmit].forEach((btn) => {
			btn.addEventListener('click', completeProduction);
		})

		const emergencyAlarm = document.getElementById('emergency-alarm');
		emergencyAlarm.addEventListener('click', function() {
			switchEmergency();
		})

		const Printer = document.getElementsByClassName('selectProductionPlanData')[7];
		Printer.addEventListener('change', function() {
			selectPrinter();
		});
		
		const tbody = document.getElementById('ProductionPlanContents');
		let tr = tbody.children;
		[...tr].forEach((ele) => {
			ele.addEventListener('click', TrEvt)
		})
		

	}
	stepEvtForm() {

		$(".nextProductionPlan").on('click', stepNextEvt);

		$(".previousProductionPlan").on('click', stepPreiousEvt);

	}
	updateTabClick() {
		const evt = new ProductionPlan
		evt.finallyProductionPlanUpdate();
		evt.finallyProductionPlanDelete();
		document.getElementsByClassName("ProductionPlanUpdate")[0].addEventListener('click', function() {
			const inputs = document.getElementsByClassName("Production-update-conrol")
			document.getElementById("select-update-suju").addEventListener('click', function() {
				if (parseInt(inputs[0].value) === -1) {
					alert("선택된 수주가 없습니다.")
					return;
				}
				if (parseInt(inputs[1].value) === -1) {
					alert("선택된 품목이 없습니다.")
					return;
				} else {
					stepNextEvtIdx(this);
				}
			})

			inputs[0].addEventListener('change', function() {

				if (parseInt(inputs[0].value) === -1) return;
				var id = getSelectedValue(this);
				var orderInfo;
				var data = JSON.parse(document.getElementById('production-data').innerText);

				for (var i = 0; i < data.length; i++) {
					if (data[i].id == id) {
						orderInfo = data[i];
						break;
					}
				}

				removeAllChild(inputs[1], false);
				removeAllChild(inputs[12], false);
				inputs[2].value = data[0].company;
				for (var i = 0; i < orderInfo.products.length; i++) {
					var selection = inputs[1].children[0].cloneNode(false);
					console.log(orderInfo.products[i])

					selection.value = JSON.stringify(orderInfo.products[i]);
					selection.innerText = orderInfo.products[i].product;
					inputs[1].appendChild(selection);
				}
				for (var i = 0; i < orderInfo.locations.length; i++) {
					var selection = inputs[12].children[0].cloneNode(false);
					selection.value = orderInfo.locations[i].id;
					selection.innerText = orderInfo.locations[i].title;
					inputs[12].appendChild(selection);
				}
				changeImage(orderInfo.urls);
			});

			inputs[1].addEventListener('change', function() {
				if (parseInt(inputs[1].value) === -1) return;
				var item = getSelectedValue(this);
				if (item == "-1") {
					inputs[3].value = "품목을 선택해 주세요";
					workCount.value = "0";

				} else {

					var productInfo = JSON.parse(item);
					inputs[3].value = productInfo.size;
					inputs[6].value = productInfo.count;
				}
			});
		})
	}
	TrToInput() {

		const tbody = document.getElementById("ProductionPlanContents")
		const inputs = document.getElementsByClassName("Production-update-conrol");
		let tr = tbody.children;
		for (let i = 0; i < tr.length; i++) {
			tr[i].addEventListener('click', function() {

				if (document.getElementsByClassName("ProductionPlanUpdate")[0].classList.contains('active')) {
					if (parseInt(this.children[0].innerText) > 0) {
						let id = this.children[0].innerText;
						document.getElementById("ProductionPlanIdForUpdate").value = id;
						axios.get('selectAboutProductionPlan', { params: { id: id } })
							.then(function(res) {

								let data = res.data;
								console.log(data)
								let datas = [data.orderInfoId, data.product, data.company,
								data.size, data.isEmergency, data.workingLine,
								data.count, data.coating != "미사용" ? true : false, data.backprint != "아니오" ? true : false,
								data.startTime.replace(" ", "T"), data.endTime.replace(" ", "T"), data.releaseDay,
								data.direction, data.remark]

								getSelectDataForUpdate(inputs, datas[0]);

								for (let i = 0; i < inputs.length; i++) {

									if (i === 11 || i === 12) {
										if (i === 11) {
											if (datas[i] === "No Info") {
												inputs[i].value = "";
											} else {
												inputs[i].value = datas[11].replace(" ", "T")
											}
										} else if (i === 11) {
											if (datas[i] === "No Info") {
												inputs[i].value = -1;
											} else {
												inputs[i].value = datas[i];
											}
										}
									} else {
										inputs[i].value = datas[i];
									}

								}

							})
					}

				} else return;
			})
		}
	}


	finallyProductionPlanUpdate() {
		const orderInfoInput = document.getElementsByClassName("Production-update-conrol")[0];
		document.getElementById("finallyProductionPlanUpdate").addEventListener('click', function() {
			let form = document.getElementById("productionFormForUpdate");
			orderInfoInput.disabled = false;
			let datas = new FormData(form);
			for (let data of datas.keys()) {
				if (!!datas.get(data) === false || parseInt(datas.get(data)) === -1) {
					orderInfoInput.disabled = true;
					alert("빈 항목이 있습니다.")
					return;
				}
			}
			orderInfoInput.disabled = true;
			axios.post('updateProductionPlan', datas)
				.then(function(res) {
					console.log(res.data);
				})
		});
	}
	finallyProductionPlanDelete() {

		document.getElementById("finallyProductionPlanDelete").addEventListener('click', function() {
			let id = document.getElementById("ProductionPlanIdForUpdate").value;
			if (!!id) {
				axios.get('deleteProductionPlan', { params: { id: id } })
					.then(function(res) {
						console.log(res.data);
					}).catch(function(e) {
						conole.log(e)
					})
			} else {
				alert("선택된 계획이 없습니다.");
				return;
			}
		});
	}
}

function removeAllChild(element, allChild) {
	var idx = 0;
	if (allChild)
		idx = -1;
	for (var i = element.children.length - 1; i > idx; i--) {
		element.removeChild(element.children[i]);
	}
}
function changeImage(imgUrlList) {
	const indiBox = document.getElementById("production-Indi");
	const indiBoxItems = document.getElementById("production-indi-Items");

	if (indiBox.children.length > 1) {
		while (indiBox.children.length > 1) {
			indiBox.lastChild.remove();
		}
		while (indiBoxItems.children.length > 1) {
			indiBoxItems.lastChild.remove();
		}
	}
	let firstIndi = document.getElementsByClassName("production-template-indi")[0];
	let firstItem = document.getElementsByClassName("production-indi-item")[0];
	firstIndi.classList.add('active');
	firstItem.classList.add('active');
	if (indiBox.children.length === 1) {
		indiBoxItems.children[0].children[0].src = "image?imagename=noImage.png";
		[...imgUrlList].forEach(function(url, i) {
			if (i === 0) {
				indiBoxItems.children[0].children[0].src = "image?imagename=" + encodeURI(url);
			} else {
				let indiCator = firstIndi.cloneNode(true);
				let item = firstItem.cloneNode(true);
				let indis = indiCator;
				let items = item;
				indis.classList.remove('active')
				indis.setAttribute("data-slide-to", i);
				items.classList.remove('active')
				items.children[0].src = "image?imagename=" + encodeURI(url);
				indiBox.appendChild(indis);
				indiBoxItems.appendChild(items);
			}
		})

	}

}
function getSelectedValue(select) {
	return select.options[select.selectedIndex].value;
}
function stepNextEvt() {

	var current_fs, next_fs;
	var opacity;
	var animating;

	if (animating) return false;
	animating = true;

	current_fs = $(this).parent().parent().parent();
	next_fs = $(this).parent().parent().parent().next();

	if (document.getElementsByClassName("ProductionPlanCreate")[0].classList.contains('active')) {
		$("#ProductionPlan-step li").eq($("#productionPlan-Field fieldset").index(next_fs)).addClass("active");
	} else {
		$("#ProductionPlan-update-step li").eq(1).addClass("active");
	}

	next_fs.show();

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
		easing: 'easeInOutBack'
	});
}
function stepNextEvtIdx(ele) {

	var current_fs, next_fs;
	var opacity;
	var animating;

	if (animating) return false;
	animating = true;

	current_fs = $(ele).parent().parent().parent();
	next_fs = $(ele).parent().parent().parent().next();

	if (document.getElementsByClassName("ProductionPlanCreate")[0].classList.contains('active')) {
		$("#ProductionPlan-step li").eq($("#productionPlan-Field fieldset").index(next_fs)).addClass("active");
	} else {
		$("ProductionPlan-update-step li").eq(1).addClass("active");
	}

	next_fs.show();

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
		easing: 'easeInOutBack'
	});
}
function stepPreiousEvt() {
	var current_fs, previous_fs; //fieldsets
	var opacity; //fieldset properties which we will animate
	var animating; //flag to prevent quick multi-click glitches
	if (animating) return false;
	animating = true;
	if (!document.getElementsByClassName("ProductionPlanCreate")[0].classList.contains('active')) {
		current_fs = $(this).parent().parent().parent().parent();
		previous_fs = $(this).parent().parent().parent().parent().prev();
	}
	else {
		current_fs = $(this).parent().parent().parent();
		previous_fs = $(this).parent().parent().parent().prev();
	}

	//de-activate current step on progressbar

	if (document.getElementsByClassName("ProductionPlanCreate")[0].classList.contains('active')) {
		$("#ProductionPlan-step li").eq($("#productionPlan-Field fieldset").index(current_fs)).removeClass("active");
	} else {
		$("#ProductionPlan-update-step li").eq(1).removeClass("active");
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
function getSelectDataForUpdate(inputs, id) {
	var id = id;
	var orderInfo;
	var data = JSON.parse(document.getElementById('production-data').innerText);

	for (var i = 0; i < data.length; i++) {

		if (data[i].id === id) {

			orderInfo = data[i];
			break;
		}
	}

	removeAllChild(inputs[1], false);
	removeAllChild(inputs[12], false);
	inputs[2].value = data[0].company;
	for (var i = 0; i < orderInfo.products.length; i++) {
		var selection = inputs[1].children[0].cloneNode(false);
		var value = JSON.stringify(orderInfo.products[i])
		value = JSON.parse(value);

		selection.value = parseInt(value.id);

		selection.innerText = orderInfo.products[i].product;
		inputs[1].appendChild(selection);
	}
	for (var i = 0; i < orderInfo.locations.length; i++) {
		var selection = inputs[12].children[0].cloneNode(false);
		selection.value = orderInfo.locations[i].id;
		selection.innerText = orderInfo.locations[i].title;
		inputs[12].appendChild(selection);
	}
	changeImage(orderInfo.urls);



}


function DetailForProductionPlanEvt() {
	const detail = document.getElementsByClassName('detailForProductionPlanBtn');
	const datas = document.getElementsByClassName('selectProductionPlanData');
	console.log(detail.length)
	$('#DetailForProductionPlan').modal('show');
	let tr = this.parentNode.parentNode
	let td = tr.children;
	let id = td[0].innerText;
	console.log(id);

	datas[0].innerText = id;
	//제품
	datas[1].innerText = td[6].innerText;
	//담당자
	datas[2].innerText = td[7].innerText;
	//청구지
	datas[3].innerText = td[3].innerText;
	//출고지
	//datas[3].innerText = td[3].innerText;
	//매장명
	datas[5].innerText = td[5].innerText;
	//수량
	datas[6].innerText = td[12].innerText;
	//출력기
	datas[7].value = td[8].innerText;

	datas[8].innerText = td[9].innerText;
	datas[9].innerText = (td[10].innerText === "예" ? "사용" : "미사용");
	//특이사항
	//datas[9].innerText = td[11].innerText;
	//예상시간
	//datas[10].innerText = td[15].innerText;

	let start = td[13].innerText.replace(" ", "T");
	let end = td[14].innerText.replace(" ", "T");

	datas[12].value = start;
	datas[13].value = end
	const emergencyText = document.getElementById("emergency-alarm-text");
	const bellIcon = document.getElementById("bell-icon");

	if (td[16].innerText != "-") {
		bellIcon.style.color = "red";
		emergencyText.innerText = "긴급"
	} else {
		bellIcon.style.color = "black";
		emergencyText.innerText = "-"
	}

}


function switchEmergency() {
	const datas = document.getElementsByClassName('selectProductionPlanData')[0];
	let id = datas.innerText;


	let data = new FormData();
	data.append("id", id);

	let csrf = document.getElementsByName("_csrf")[0].value;
	axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
	axios.post('switchEmergency', data)
		.then(function(res) {
			console.log(res.data);
			if (res.data == false) {
				alert("변경 실패");
			} else {
				alert("변경 완료");
				location.reload();
			}
		})
}

function selectPrinter() {
	let id = document.getElementsByClassName('selectProductionPlanData')[0].innerText;
	
	const datas = document.getElementsByClassName('selectProductionPlanData');
	let printer = datas[7].value
	
	let data = new FormData();
	data.append("id", id);
	data.append("printer", printer);

	let csrf = document.getElementsByName("_csrf")[0].value;
	axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
	axios.post('selectPrinter', data)
		.then(function(res) {
			console.log(res.data);
			if (res.data == false) {
				alert("변경 실패");
			} else {
				alert("변경 완료");
				location.reload();
			}
		})
}

function completeProduction() {
	const datas = document.getElementsByClassName('selectProductionPlanData')[0];
	let id = datas.innerText;
	
	let data = new FormData();
	data.append("workOrderId",id);
	let csrf = document.getElementsByName("_csrf")[0].value;
	axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
	axios.post('completeProduction',data)
		.then(function(res){
			if(res.data == false){
				alert("등록 실패");
			}else{
				alert("등록");
				location.reload();
			}
		})

}

function TrEvt(){
	let tr = this;
	let url = tr.children[14].innerText;
	drawImg(url)
}

function drawImg(url) {
	const img = document.getElementById("Plan_image")
	const noImage = document.getElementById("Plan_no_image");
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
