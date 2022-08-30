export default class ProcessMaster{
	updateEvt(){
		const updateBtn = document.getElementById("update-ProcessMaster-btn");
		const deleteBtn = document.getElementById("deleteProcess");
		updateBtn.addEventListener('click', update);
		deleteBtn.addEventListener('click', deleteProcess);

		// 메인 테이블 행 이벤트
		const tbody = document.getElementById("ProcessMasterBody");
		let tr = tbody.children;
		[...tr].forEach((ele) => {
			ele.addEventListener('click', MainTrEvt);
		})
	}
};


function MainTrEvt() {
	const updateTab = document.getElementsByClassName("ProcessUpdate")[0];
	const inputs = document.getElementsByClassName("ProcessMaster-u");
	let tr = this;
	let id = tr.children[1].innerText;

	if (updateTab.classList.contains("active")) {
		for (let i = 0; i < inputs.length; i++) {
			inputs[i].value = "";
		}
		axios.get('selectProcessById', { params: { id: id } })
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
	const form = document.getElementById("update-ProcessMaster-list");
	let data = new FormData(form);
	console.log(data);
	axios.post("updateProcess", data)
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}

function deleteProcess() {
	let id = document.getElementsByClassName("ProcessMaster-u")[0].value;
	if (!!id === false) {
		alert("삭제할 정보를 클릭하세요");
		return;
	}
	let state = confirm("삭제 하시겠습니까?");
	if (state === false) return;

	axios.get("deleteProcess", { params: { id: id } })
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}