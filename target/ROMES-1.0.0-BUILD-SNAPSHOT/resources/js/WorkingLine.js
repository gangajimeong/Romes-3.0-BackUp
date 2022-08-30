 import * as RESULT from './ResultCodes.js';

export default class WorkingLine{
	UDEvt(){
		const updateBtn = document.getElementById("update-WorkingLine-btn");
		const deleteBtn = document.getElementById("deleteWorkingLine");
		updateBtn.addEventListener('click', update);
		deleteBtn.addEventListener('click', del);

		// 메인 테이블 행 이벤트
		const tbody = document.getElementById("WorkingLineBody");
		let tr = tbody.children;
		[...tr].forEach((ele) => {
			ele.addEventListener('click', MainTrEvt);
		})
	}
};

function MainTrEvt() {
	const updateTab = document.getElementsByClassName("WorkingLineUpdate")[0];
	const inputs = document.getElementsByClassName("WorkingLine-u");
	let tr = this;
	let id = tr.children[1].innerText;

	if (updateTab.classList.contains("active")) {
		for (let i = 0; i < inputs.length; i++) {
			inputs[i].value = "";
		}
		axios.get('selectWLById', { params: { id: id } })
			.then(function(res) {
				let data = res.data
//				console.log(data);
				if (data[0] === false) {
					alert("error")
					return;
				}
//				console.log(inputs[0])
				for (let i = 0; i < data.length-1; i++) {
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
	const form = document.getElementById("update-workingline");
	let data = new FormData(form);
	
	axios.post("updateWL", data)
		.then(function(res) {
			let ret = res.data;
			console.log(ret);
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}

function del() {
	let id = document.getElementsByClassName("WorkingLine-u")[0].value;
	if (!!id === false) {
		alert("삭제할 정보를 클릭하세요");
		return;
	}
	let state = confirm("삭제 하시겠습니까?");
	if (state === false) return;

	axios.get("deleteWL", { params: { id: id } })
		.then(function(res) {
			let ret = res.data.result;
			console.log(ret);
			if (ret == RESULT.SUCCESS) {
				alert("삭제");
				location.reload();
			} else if (ret == RESULT.AUTHORITY_ERROR) {
				alert("권한이 없습니다.");
			} else {
				alert("등록실패");
			}
		})
}