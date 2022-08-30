export default class EquipmentDefect{
	UDevt(){
		const updateBtn = document.getElementById("update-equipmentDefect-btn");
		const deleteBtn = document.getElementById("deleteEquipmentDefect");
		updateBtn.addEventListener('click', update);
		deleteBtn.addEventListener('click', del);

		// 메인 테이블 행 이벤트
		const tbody = document.getElementById("EquipmentDefectContents");
		let tr = tbody.children;
		[...tr].forEach((ele) => {
			ele.addEventListener('click', MainTrEvt);
		})
	}
};

function MainTrEvt() {
	const updateTab = document.getElementsByClassName("equipmentDefectUpdate")[0];
	const inputs = document.getElementsByClassName("equipmentDefect-u");
	let tr = this;
	let id = tr.children[0].innerText;

	if (updateTab.classList.contains("active")) {
		for (let i = 0; i < inputs.length; i++) {
			inputs[i].value = "";
		}
		
		axios.get('selectEDById', { params: { id: id } })
			.then(function(res) {
				let data = res.data
				console.log(data);
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
	const form = document.getElementById("update-equipmentDefect");
	console.log(form)
	let data = new FormData(form);
	console.log(data.values);
	axios.post("updateED", data)
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
	let id = document.getElementsByClassName("equipmentDefect-u")[0].value;
	if (!!id === false) {
		alert("삭제할 정보를 클릭하세요");
		return;
	}
	let state = confirm("삭제 하시겠습니까?");
	if (state === false) return;

	axios.get("deleteED", { params: { id: id } })
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}