export default class WorkReport {
	WorkReport() {
		
		const updateBtn = document.getElementById("update-WorkReport-btn");
		updateBtn.addEventListener('click', update);

		// 메인 테이블 행 이벤트
		const tbody = document.getElementById("WorkReportBody");
		let tr = tbody.children;
		[...tr].forEach((ele) => {
			ele.addEventListener('click', MainTrEvt);
		})

		const deleteBtn = document.getElementById("deleteWorkReport");
		deleteBtn.addEventListener('click', remove);

	}
}


function MainTrEvt() {
	const updateTab = document.getElementsByClassName("WorkReportUpdate")[0];
	const showTab = document.getElementsByClassName("WorkReportShow")[0];
	const inputs = document.getElementsByClassName("WorkReport-u");
	const showinput = document.getElementsByClassName("WorkReport-s");
	
	let tr = this;
	let id = tr.children[1].innerText;
	console.log(id);

	if (updateTab.classList.contains("active")) {
		for (let i = 0; i < inputs.length; i++) {
			inputs[i].value = "";
		}
		axios.get('selectWorkReportById', { params: { id: id } })
			.then(function(res) {
				let data = res.data
				console.log(data);
				if (data[0] === false) {
					alert("error")
					return;
				}
				for (let i = 0; i < data.length; i++) {
						inputs[i].value = data[i];
				}

			})
			.catch(function(e) {
				console.log(e);
				alert("오류: 관리자 문의")
			})
	} else if(showTab.classList.contains("active")){
		for (let i = 0; i < showinput.length; i++) {
			showinput[i].value = "";
		}
		axios.get('selectWorkReportByIdForShow', { params: { id: id } })
			.then(function(res) {
				let data = res.data
				console.log(data);
				if (data[0] === false) {
					alert("error")
					return;
				}
				for (let i = 0; i < data.length; i++) {
					showinput[i].value = data[i];
				}
			})
			.catch(function(e) {
				console.log(e);
				alert("오류: 관리자 문의")
			})
	}
}

function update() {
	const form = document.getElementById("update-WorkReport-list");
	let data = new FormData(form);
	console.log(data);
	axios.post("updateWorkReport", data)
		.then(function(res) {
			let ret = res.data;
			if (ret) {
				location.reload();
			} else {
				alert("등록 실패")
			}
		})
}

function remove() {
	const reportID = document.getElementsByClassName("WorkReport-u")[0].value;
	console.log(reportID);
	axios.get("deleteWorkReport",{ params: { id: reportID } })
		.then(function(res){
			let ret= res.data;
			if(ret){
				alert("삭제 되었습니다.");
				location.reload();
			} else{
				alert("등록 실패");
			}
		})
}