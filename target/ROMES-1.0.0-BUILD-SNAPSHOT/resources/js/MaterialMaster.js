export default class MaterialMaster {
	UDevt() {
		const updateBtn = document.getElementById("update-Material-btn");
		const deleteBtn = document.getElementById("deleteMaterial");
		updateBtn.addEventListener('click', update);
		deleteBtn.addEventListener('click', del);

		// 메인 테이블 행 이벤트
		const tbody = document.getElementById("MaterialBody");
		let tr = tbody.children;
		[...tr].forEach((ele) => {
			ele.addEventListener('click', MainTrEvt);
		})
		
		//excelImport
		const ExcelImportBtn = document.getElementById("MaterialExcel");
		ExcelImportBtn.addEventListener('change', readExcel);
	}
};

function MainTrEvt() {
	const updateTab = document.getElementsByClassName("MaterialUpdate")[0];
	const inputs = document.getElementsByClassName("Material-u");
	let tr = this;
	let id = tr.children[0].innerText;
	console.log(id)

	if (updateTab.classList.contains("active")) {
		for (let i = 0; i < inputs.length; i++) {
			inputs[i].value = "";
		}
		axios.get('selectMaterialById', { params: { id: id } })
			.then(function(res) {
				let data = res.data
//				console.log(data);
				if (data[0] === false) {
					alert("error")
					return;
				}
//				console.log(inputs[0])
				for (let i = 0; i < data.length; i++) {
						inputs[i].value = data[i];
				}

			})
			.catch(function(e) {
				console.log(e);
				alert("오류: 관리자 문의")
			})
	}
}

function update() {
	const form = document.getElementById("update-Material");
	console.log(form)
	let data = new FormData(form);
	console.log(data);
	axios.post("updateMaterial", data)
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}

function del() {
	let id = document.getElementsByClassName("Material-u")[0].value;
	if (!!id === false) {
		alert("삭제할 정보를 클릭하세요");
		return;
	}
	let state = confirm("삭제 하시겠습니까?");
	if (state === false) return;

	axios.get("deleteMaterial", { params: { id: id } })
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
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
			let csrf = document.getElementsByName("_csrf")[0].value;
			axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
			axios.post("materialExcel", formData)
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