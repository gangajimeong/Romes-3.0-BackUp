export default class WorkOrder {

	connectSse() {
		console.log('try connect to sse');
		let source = new EventSource('/ROMES/monitor/sse');
		source.addEventListener("monitor", (event) => {
			let datas = JSON.parse(event.data);
			console.log(datas);
			clear(datas);
		});
		source.addEventListener("completeList", (event) => {
			let datas = JSON.parse(event.data);
			console.log(datas);
			bottomClear(datas);
		});
	}

	evt() {
		const complete = document.getElementsByClassName('complete-Work');
		[...complete].forEach((btn) => {
			btn.addEventListener('click', completeWork)
		})
	}
	
	setmodal(){
		const mModalBtn = document.getElementsByClassName('workOrder-tableModal');
		
		[...mModalBtn].forEach((btn) => {
			btn.addEventListener('click', workInfo);
		});
		
	}

}

function clear(PEResult) {

	let tbody = document.getElementById('monitor-tbody');
	for (let i = tbody.children.length - 1; i > 0; i--)
		tbody.removeChild(tbody.children[i]);
	output(PEResult)
	
}

function bottomClear(PEResult){
	let comTbody = document.getElementById('com-monitor-tbody');
	for (let i = comTbody.children.length - 1; i > 0; i--)
		comTbody.removeChild(comTbody.children[i]);
	completeOutput(PEResult)
}

function output(PEResult) {
	var PEResult
	let tbody = document.getElementById('monitor-tbody');
	for (var i = 0; i < PEResult.length; i++) {
		let copy = document.getElementById('monitor-origin').cloneNode(true);
		copy.getElementsByClassName("monitor-id")[0].innerText = PEResult[i].id;
		copy.getElementsByClassName("monitor-idx")[0].innerText = i+1;
		copy.getElementsByClassName("monitor-user")[0].innerText = PEResult[i].user;
		copy.getElementsByClassName("monitor-company")[0].innerText = PEResult[i].company;
		copy.getElementsByClassName("monitor-branch")[0].innerText = PEResult[i].branch;
		copy.getElementsByClassName("monitor-printer")[0].innerText = PEResult[i].printer;
		copy.getElementsByClassName("monitor-product")[0].innerText = PEResult[i].product;
		copy.getElementsByClassName("monitor-coating")[0].innerText = PEResult[i].coating;
		copy.getElementsByClassName("monitor-back")[0].innerText = PEResult[i].back;
		copy.getElementsByClassName("monitor-size")[0].innerText = PEResult[i].size;
		copy.getElementsByClassName("monitor-count")[0].innerText = PEResult[i].count;
		copy.getElementsByClassName("monitor-remark")[0].innerText = PEResult[i].remark;
		copy.getElementsByClassName("monitor-manufacture")[0].innerText = PEResult[i].manufacture;
		copy.getElementsByClassName("monitor-url")[0].innerText = PEResult[i].url;
		
//		copy.getElementsByClassName("monitor-emergency")[0].innerText = PEResult[i].emergency;
		
		
		if(PEResult[i].emergency == true){
			copy.getElementsByClassName("monitor-emergency")[0].innerHTML =
			'<i class="bx bxs-bell-ring" style = "color: red; padding: 5px 5px 5px 5px;" id = "bell-icon" ></i >'
		}

		copy.getElementsByClassName("submit-button")[0].style.display = "";
		copy.getElementsByClassName("submit-button")[0].title = PEResult[i].id;
		copy.style.display = "";
		tbody.appendChild(copy);

		const complete2 = document.getElementsByClassName('submit-button');
		[...complete2].forEach((btn) => {
			btn.addEventListener('click', completeWork);
		});
		
		const modalBtn = document.getElementsByClassName('workOrder-tableInfo');
		[...modalBtn].forEach((btn) => {
			btn.addEventListener('click', workInfo2);
		});
	}

}


function completeOutput(PEResult) {
	var PEResult
	let tbody = document.getElementById('com-monitor-tbody');
	for (var i = 0; i < PEResult.length; i++) {
		let copy = document.getElementById('com-monitor-origin').cloneNode(true);
		copy.getElementsByClassName("com-monitor-id")[0].innerText = PEResult[i].id;
		copy.getElementsByClassName("com-monitor-idx")[0].innerText = i+1;
		copy.getElementsByClassName("com-monitor-user")[0].innerText = PEResult[i].user;
		copy.getElementsByClassName("com-monitor-company")[0].innerText = PEResult[i].company;
		copy.getElementsByClassName("com-monitor-branch")[0].innerText = PEResult[i].branch;
		copy.getElementsByClassName("com-monitor-printer")[0].innerText = PEResult[i].printer;
		copy.getElementsByClassName("com-monitor-product")[0].innerText = PEResult[i].product;
		copy.getElementsByClassName("com-monitor-coating")[0].innerText = PEResult[i].coating;
		copy.getElementsByClassName("com-monitor-back")[0].innerText = PEResult[i].back;
		copy.getElementsByClassName("com-monitor-size")[0].innerText = PEResult[i].size;
		copy.getElementsByClassName("com-monitor-count")[0].innerText = PEResult[i].count;
		copy.getElementsByClassName("com-monitor-remark")[0].innerText = PEResult[i].remark;
		copy.getElementsByClassName("com-monitor-manufacture")[0].innerText = PEResult[i].manufacture;
		copy.getElementsByClassName("com-monitor-url")[0].innerText = PEResult[i].url;
		
		
		copy.style.display = "";
		tbody.appendChild(copy);

		
		const modalBtn = document.getElementsByClassName('workOrder-tableInfo');
		[...modalBtn].forEach((btn) => {
			btn.addEventListener('click', workInfo2);
		});
	}

}











function completeWork() {
	if(confirm('작업을 완료하시겠습니까?') == true){
	let id = this.title;
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
			}
		})
	}
}

function workInfo() {
	const info = document.getElementById('workInfo-info');
	const img = document.getElementById('workInfo-img');
	$('#workOrder-modalInfo').modal('show');

	let tr = this.parentNode;
	let td = tr.children;
	let id = td[0].innerText;
	
	info.innerText="";
	img.src = "image?imagename=no_image.png";

	info.innerText = td[13].innerText;
	img.src = "image?imagename=" + td[14].innerText;
}

function workInfo2() {
	const info = document.getElementById('workInfo-info');
	const img = document.getElementById('workInfo-img');
	$('#workOrder-modalInfo').modal('show');

	let tr = this.parentNode;
	let td = tr.children;
	let id = td[0].innerText;
	
	info.innerText="";
	img.src = "image?imagename=no_image.png";

	info.innerText = td[12].innerText;
	img.src = "image?imagename=" + td[13].innerText;
}