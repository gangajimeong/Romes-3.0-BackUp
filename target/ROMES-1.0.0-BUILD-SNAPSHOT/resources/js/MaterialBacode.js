

export default class MaterialBacode {
	printTrEvt() {
		const tbody = document.getElementById('printerLists');
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
		document.getElementById("exePrint").addEventListener('click', function() {
			let tr = tbody.children;
			let id = tr[0].children[0].innerText
			if (!!id === false) return;
			let name = tr[0].children[2].innerText
			let ret = confirm("출력 하시겠습니까?")
			console.log("id =" + id);
			console.log("printName =" + name);
			if (ret) {
				axios.get('printMaterial', { params: { id: id, printName: name } })
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
		document.getElementById("SelectPrinter").addEventListener('click', function() {
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
	
	
	BarcodeModal() {
		const btns = document.getElementsByClassName('getPrinter');

		[...btns].forEach(function(btn) {

			const tbody = document.getElementById('printerLists');
			btn.addEventListener('click', function(e) {
				e.stopPropagation();
				let id = this.parentNode.parentNode.children[0].innerText;
				$('.BarcodePrint').modal('show')
				let tr = tbody.children;
				[...tr].forEach(function(ele) {
					ele.children[0].innerText = id;
				})
				console.log(id);
			})
		})
	}
}

