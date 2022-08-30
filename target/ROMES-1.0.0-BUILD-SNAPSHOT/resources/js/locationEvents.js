/**
 * 
 */

export class EvtToLocation {

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
	DynamicOption(SelectBoxElement, ret) {
		const selectBox = SelectBoxElement;
		const frag = document.createDocumentFragment();
		if (ret.length != 0) {
			selectBox.children[0].remove();
			ret.forEach(function(data, i) {
				const newOption = document.createElement('option');
				if (i == 0) {
					newOption.selected = true;
				}
				newOption.value = data.id;
				newOption.innerText = data.value;
				frag.append(newOption);
			})
			selectBox.appendChild(frag);
		}


	}
	addLocationOption(SelectBoxElement, ret) {
		const selectBox = SelectBoxElement;
		console.log(selectBox)
		const frag = document.createDocumentFragment();
		if (ret.length != 0) {
			selectBox.children[0].remove();
			ret.forEach(function(data, i) {
				const newOption = document.createElement('option');
				if (i === 0) {
					let firstOpt = document.createElement('option');
					firstOpt.value = 0;
					firstOpt.innerText = "없음"
					newOption.selected = true;
					frag.append(firstOpt);
				}
				newOption.value = data.id;
				newOption.innerText = data.name;
				frag.append(newOption);


			})
			selectBox.appendChild(frag);
		}


	}


	MainEvtToTr() {
		const tbody = document.getElementById("LocationBody");
		const tr = tbody.children;
		
		[...tr].forEach(function(element) {
			element.addEventListener('click', function() {
				SubEvtToTr(this);
			})
		})

	}

	drawImg(url) {
		const img = document.getElementById("view")
		const noImage = document.getElementById("no");
		if (url.indexOf("no_image") < 0) {
			noImage.style.display = "none";
			img.style.display = "";
			img.src = "image?imagename=" + url

		} else {
			noImage.style.display = "";
			img.style.display = "none";
			img.src = "img/no_image.png"
		}
	}


	drawLocationOption() {
		const evt = new EvtToLocation();
		const LocationBody = document.getElementById("LocationBody");
		const tr = LocationBody.children;
		const selectBox = document.getElementsByClassName("Location-Select-c");
		const updateBox = document.getElementsByClassName('Location-Select-u');
		const typeBox = selectBox[0];
		const locationBox = selectBox[1];
		const updateTypeBox = updateBox[0];
		const updatelocationbox = updateBox[1];

		const csrf = document.getElementsByName("_csrf")[0].value;
		if (typeBox.children.length < 2) {
			axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
			axios.post("getLocationTypeCode")
				.then(function(response) {
					const ret = response.data;
					evt.DynamicOption(typeBox, ret)
					evt.DynamicOption(updateTypeBox, ret)
					console.log(ret);
				}).catch(function(e) {
					console.log(e);

				})
		}



		if (tr.length != 0) {
			const ret = [];

			[...tr].forEach(function(col, i) {
				let td = col.children;
				let obj = {};
				obj.id = td[0].innerText;
				obj.name = td[1].innerText;
				ret[i] = obj;
			})
			console.log(ret)
			evt.addLocationOption(locationBox, ret);
			evt.addLocationOption(updatelocationbox, ret);

		}
	}
	AllClearNaviData() {
		const active = document.getElementsByClassName("cusBread")[0].children;
		active.setAttribute("class", '')
		active.children
		for (let i = 0; i < active.length; i++) {

		}
	}

	drawNavi(id, name) {
		const navi = document.getElementsByClassName("cusBread")[0];
		let next = 0;

		for (let i = 0; i < navi.children.length; i++) {
			if (!navi.children[i].classList.contains('active')) { next = i; break; };
		}
		if (navi.children[next + 1].classList.contains('active')) {
			for (let i = navi.children.length; i > 1; i--) {
				if (next === i) break;
				navi.children[next].setAttribute('class', '');
			}
		}
		navi.children[next].setAttribute('class', 'active');
		navi.children[next].children[0].setAttribute("class", id);
		navi.children[next].children[0].innerText = name

	}
	getSubData(id, tr) {
		const form = new FormData();
		const evt = new EvtToLocation();
		const tbody = document.getElementById('printListContents');

		form.append("id", id);
		axios.post("getTopAndSubLocationLists", form)
			.then(function(response) {
				const ret = response.data;
				console.log(ret.Subs.length)
				console.log(ret)

				if (tr[0].children[0].innerText != '-' && ret.Subs.length != 0) {
					for (let i = 0; i < tr.length; i++) {
						tr[i].children[0].innerText = "-";
						tr[i].children[1].innerText = "-";
						tr[i].children[2].innerText = "-";
						tr[i].children[3].innerText = "-";
					}
				}

				ret.Subs.forEach(function(data, idx) {
					let td = tr[idx].children;
					td[0].innerText = data.id;
					td[1].innerText = data.name;
					td[2].innerText = data.type;
					td[3].innerText = data.img;
					td[4].innerText = "";
					let button = document.createElement('button');
					button.className = "btn btn-secondary p-0"

					let ico = document.getElementById("oriBarcode").cloneNode();
					button.appendChild(ico);

					td[4].appendChild(button);
					tr[idx].addEventListener('click', function(e) {
						//evt.drawNavi(this.children[0].innerText,this.children[1].innerText)
						evt.getSubData(this.children[0].innerText, tr);
						evt.drawImg(this.children[3].innerText);
						SubEvtToTr(this)

					})
					ico.addEventListener('click', function(e) {
						e.stopPropagation();
						let id = button.parentNode.parentNode.children[0].innerText;
						console.log(id);
						$('.GoPrint').modal('show')
						let tr = tbody.children;
						[...tr].forEach(function(ele) {
							ele.children[0].innerText = id;
						})
					})


				})
			}).catch(function(e) {
				console.log(e);
			})
	}


	drawSubDataFromMain(td) {
		const tr = document.getElementById("Sub-LocationBody").children;
		const evt = new EvtToLocation();
		const id = td[0].innerText;

		evt.getSubData(id, tr);

	}
	deleteEvt() {
		document.getElementById("delete-location-button").addEventListener('click', function() {
			const tr = document.getElementById("Delete-LocationBody").children;
			const data = [];
			for (let i = 0; i < tr.length; i++) {
				if (tr[i].children[0].innerText === "-") break;
				data[i] = tr[i].children[0].innerText;
			}
			const params = {
				"id": data.join(",")
			}

			axios.get("LocationDelete", { params })
				.then(function(res) {
					const evt = new EvtToLocation();
					evt.earseSub();
					evt.eraseDelete();

				})
				.catch(function(e) {
					console.log(e);
				})
		})

	}


	deleteTab() {
		const deletes = document.getElementsByClassName("LocationDelete")[0];
		const evt = new EvtToLocation;
		deletes.addEventListener('click', () => {
			evt.deleteEvt();


		})
	}
	earseSub() {
		const tbody = document.getElementById("Sub-LocationBody");
		const tr = tbody.children;
		[...tr].forEach(function(target, i) {
			let td = target.children
			for (let i = 0; i < td.length; i++)
				td[i].innerText = "-"

		})
	}
	eraseDelete() {
		const tbody = document.getElementById("Delete-LocationBody");
		const tr = tbody.children;
		[...tr].forEach(function(target, i) {
			let td = target.children
			for (let i = 0; i < td.length; i++)
				td[i].innerText = "-"
		})
		alert("삭제")
		location.reload()
	}
	printBtnEvt() {
		document.getElementById('executePrint').addEventListener('click')
	}
	printTrEvt() {
		const tbody = document.getElementById('printListContents');
		let tr = tbody.children;
		[...tr].forEach(function(el) {
			el.addEventListener('click', function() {

				let len = tr.length;
				let selectTr = this.cloneNode(true);
				for (let i = 0; i < len; i++) {
					tbody.deleteRow(-1);
				}
				tbody.appendChild(selectTr);

			})
		})
		document.getElementById("executePrint").addEventListener('click', function() {
			let tr = tbody.children;
			let id = tr[0].children[0].innerText
			if (!!id === false) return;
			let name = tr[0].children[2].innerText
			let ret = confirm("출력 하시겠습니까?")
			if (ret) {
				axios.get('printBarcode', { params: { id: id, printName: name } })
					.then(function(res) {
						let ret = res.data;
						if (ret.state === "출력 성공") {
							alert("출력 완료")
						}
						if (ret.state === "출력 실패") {
							alert("출력 실패")
							return;
						}

					}).catch(function() {
						console.log("오류")
					})
			} else {
				return;
			}
		})
		document.getElementById("reSelectPrinter").addEventListener('click', function() {
			let tr = tbody.children;
			let id = tr[0].children[0].innerText
			if (tr.length > 0) {
				let len = tr.length;
				for (let i = 0; i < len; i++) {
					tbody.deleteRow(-1);
				}
			}
			axios.get('getPrintList')
				.then(function(res) {
					let data = res.data;
					for (let i = 0; i < data.length; i++) {
						let newTr = tbody.insertRow();
						let idCell = newTr.insertCell();
						idCell.innerText = id;
						idCell.style.display = "none"

						let no = newTr.insertCell();
						no.innerText = i + 1;
						no.style.width = "5%";

						let print = newTr.insertCell();
						print.innerText = data[i];
						print.style.width = "95%";

						newTr.addEventListener('click', function() {

							let len = tr.length;
							let selectTr = this.cloneNode(true);
							for (let i = 0; i < len; i++) {
								tbody.deleteRow(-1);
							}
							tbody.appendChild(selectTr);

						})

					}
				})
		})
	}
	LocationMainEvent() {
		const evt = new EvtToLocation();
		const tbody = document.getElementById("LocationBody");
		const tr = tbody.children;
		const btns = document.getElementsByClassName('getBarcode');

		[...tr].forEach(function(target, i) {
			target.addEventListener('click', () => {
				evt.earseSub();
				const td = target.children;
				const fileName = td[3].innerText;
				//evt.drawNavi(td[0].innerText, td[1].innerText)
				evt.drawImg(fileName)
				evt.drawSubDataFromMain(td);
				console.log(fileName);

			})
		});
		[...btns].forEach(function(btn) {

			const tbody = document.getElementById('printListContents');
			btn.addEventListener('click', function(e) {
				e.stopPropagation();
				let id = this.parentNode.parentNode.children[0].innerText;
				console.log(id);
				console.log("here");
				$('.GoPrint').modal('show')
				let tr = tbody.children;
				[...tr].forEach(function(ele) {
					ele.children[0].innerText = id;
				})
			})
		})
	}

}
function SubEvtToTr(tr) {

	const tab = document.getElementsByClassName("LocationUpdate")[0];
	const deletTab = document.getElementsByClassName("LocationDelete")[0];
	const delTbody = document.getElementById("Delete-LocationBody")
	const input = document.getElementsByClassName('Location-u')
	const select = document.getElementsByClassName("Location-Select-u")
	let option = select[0].children
	let datas = [];
	for (let i = 0; i < option.length; i++) {
		let obj = { "id": option[i].value, "name": option[i].innerText }
		datas[i] = obj;
	}
	let value;


	if (tab.classList.contains("active")) {
		let td = tr.children;
		for (let i = 0; i < datas.length; i++) {
			if (datas[i].name === td[2].innerText) {
				value = datas[i].id
				break;
			}
		}

		input[0].value = td[0].innerText;
		input[1].value = td[1].innerText;
		select[0].value = value;
	}
	if (deletTab.classList.contains("active")) {

		let td = tr.children;
		let deltTr = delTbody.children
		let state= true;
		let len = delTbody.children.length;
		for(let i=0; i<len; i++){
			if(td[0].innerText === deltTr[i].children[0].innerText){
				state = false; 
				break;
			}
		}
		if(state === false)return;
		let ret = confirm("장소를 삭제 항목에 포함 시키겠습니까?")
		if(ret === false)return;
		let newTr = delTbody.insertRow();
		let idCell = newTr.insertCell();
		let placeCell = newTr.insertCell();
		let typeCell = newTr.insertCell();

		idCell.innerText = td[0].innerText;
		placeCell.innerText = td[1].innerText;
		typeCell.innerText = td[2].innerText;

		idCell.style.display = "none";
		placeCell.style.width = "60%"
		typeCell.style.width = "40%"
	}


}
