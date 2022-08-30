export default class DivisionManagement{
	trEvt(){
		const tr = document.getElementById("DivisionBody").children;
		const create = document.getElementsByClassName("DivisionCreate")[0];

		[...tr].forEach(function(tr) {
			tr.addEventListener('click', function() {
				if (create.classList.contains('active')) {
					let td = tr.children[2];
					document.getElementById("division-text").value = td.innerText;
				} else {
					return;
				}
			})
		})
	}
	
	setEmployee(){
		const tr = document.getElementById("DivisionBody").children;
		const employee = document.getElementsByClassName("DivisionEmployee")[0];
		const etd = document.querySelector(".Division-employee-list").childNodes[1];

		[...tr].forEach(function(tr) {
			tr.addEventListener('click', function() {
				if (employee.classList.contains('active')) {
					let td = tr.children[1];
					let divisionId = td.innerText;
					clearEmTable();
					
					axios.get("setEmployee", { params: { id: divisionId } })
					.then(function(res){
						let data = res.data
						if (data.length === 0) return;
						drawRow(data);
						
					})
				}
			})
		})
		
	}
	clearTable(){
		const checkCol = document.getElementsByClassName("DivisionChkRow")
					for (let i = 0; i < checkCol.length; i++) {
						checkCol[i].style.display = "none";
					}
	}
	
};

function clearEmTable() {
	const tbody = document.getElementsByClassName("Division-employee-list")[0];
	let tr = tbody.children;
	let len = tr.length;
	for (let i = 0; i < len; i++) {
		tbody.deleteRow(-1);
	}
}

function drawRow(ret) {
	console.log(ret)
	if (!!ret[0].NoInfo) {
		alert("소속 직원이 없습니다.")
		return;
	}
	let len = ret.length;
	const tbody = document.getElementsByClassName("Division-employee-list")[0];
	console.log(tbody)
	for (let i = 0; i < len; i++) {

		let row = tbody.insertRow();
		let cell = row.insertCell();
		cell.innerText = ret[i].name;

		let cell1 = row.insertCell();
		cell1.innerText = ret[i].position;

		let cell2 = row.insertCell();
		cell2.innerText = ret[i].tel;
		
	}
}