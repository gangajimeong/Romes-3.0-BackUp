export default class ProductDefect{
	a(){
		const updateBtn = document.getElementById("update-ProductDefect-btn");
		const deleteBtn = document.getElementById("deleteProductDefect");
		updateBtn.addEventListener('click', update);
		deleteBtn.addEventListener('click', deletePD);
		const updateCDBtn = document.getElementById("update-ConstructionDefect-btn");
		const deleteCDBtn = document.getElementById("deleteConstructionDefect");
		updateCDBtn.addEventListener('click', updateCD);
		deleteCDBtn.addEventListener('click', deleteCD);

		// 메인 테이블 행 이벤트
		const tbody = document.getElementById("ProductDefectContents");
		let tr = tbody.children;
		[...tr].forEach((ele) => {
			ele.addEventListener('click', MainTrEvt);
		})
		
		const tbody2 = document.getElementById("ProductDefectContents2");
		let tr2 = tbody2.children;
		[...tr2].forEach((ele) => {
			ele.addEventListener('click', MainTrEvt2);
		})
	}
};

function MainTrEvt() {
	const updateTab = document.getElementsByClassName("ProductDefectUpdate")[0];
	const inputs = document.getElementsByClassName("ProductDefect-u");
	let tr = this;
	let id = tr.children[0].innerText;

	if (updateTab.classList.contains("active")) {
		for (let i = 0; i < inputs.length; i++) {
			inputs[i].value = "";
		}
		axios.get('selectPDById', { params: { id: id } })
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
function MainTrEvt2() {
	const updateTab = document.getElementsByClassName("ConstructionDefectUpdate")[0];
	const inputs = document.getElementsByClassName("ConstructionDefect-u");
	let tr = this;
	let id = tr.children[0].innerText;

	if (updateTab.classList.contains("active")) {
		for (let i = 0; i < inputs.length; i++) {
			inputs[i].value = "";
		}
		axios.get('selectCDById', { params: { id: id } })
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
	const form = document.getElementById("update-ProductDefect-list");
	let data = new FormData(form);
	console.log(form)
	console.log(data);
	axios.post("updatePD", data)
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}

function deletePD() {
	let id = document.getElementsByClassName("ProductDefect-u")[0].value;
	if (!!id === false) {
		alert("삭제할 정보를 클릭하세요");
		return;
	}
	let state = confirm("삭제 하시겠습니까?");
	if (state === false) return;

	axios.get("deletePD", { params: { id: id } })
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}

function updateCD() {
	const form = document.getElementById("update-ConstructionDefect-list");
	let data = new FormData(form);
	console.log(form)
	console.log(data);
	axios.post("updateCD", data)
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}

function deleteCD() {
	let id = document.getElementsByClassName("ConstructionDefect-u")[0].value;
	if (!!id === false) {
		alert("삭제할 정보를 클릭하세요");
		return;
	}
	let state = confirm("삭제 하시겠습니까?");
	if (state === false) return;

	axios.get("deleteCD", { params: { id: id } })
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}