/**
 * 
 */



document.addEventListener("DOMContentLoaded", function(event) {

/*
	const completeBtn = document.getElementsByClassName('complete-Work');
	[...completeBtn].forEach((btn) => {
		btn.addEventListener('click', completeWork)
	})


	const workOrderTable = document.getElementsByClassName('workOrderTable');
	[...workOrderTable].forEach((btn) => {
		btn.addEventListener('click', workInfo)
	})


	PE();*/
});

function connectSse(){
	let source = new EventSource('/monitor/sse');
	source.addEventListener('WorkOrder',function(event){
		let datas = event.data;
		console.log(datas);
		clear(datas);
	})
}

function workInfo() {
	const info = document.getElementById('workInfo-info');
	$('#workOrder-modalInfo').modal('show');

	let tr = this.parentNode;
	let td = tr.children;
	let id = td[0].innerText;

	info.innerText = td[13].innerText;
}

function PE() {
	//console.log("PE start")
	axios({
		method: "get",
		url: "/Monitor/ProductandError",
	}).then(response => {
		const PEResult = response.data;
		//console.log(PEResult)
		//console.log(PEResult.length)
		//console.log(PEResult[PEResult.length-1].length)
		clear(PEResult)
	}).catch(function(error) {
		console.log(error);
	}).finally(() => {

	})
	//console.log("Data end")
}

function output(PEResult) {
	var PEResult

	for (var i = 0; i < PEResult.length; i++) {

		document.getElementById("id-" + i).innerHTML = PEResult[i].id;
		document.getElementById("company-" + i).innerHTML = PEResult[i].company;
		document.getElementById("branch-" + i).innerHTML = PEResult[i].branch;
		document.getElementById("printer-" + i).innerHTML = PEResult[i].printer;
		document.getElementById("product-" + i).innerHTML = PEResult[i].product;
		document.getElementById("coating-" + i).innerHTML = PEResult[i].coating;
		document.getElementById("back-" + i).innerHTML = PEResult[i].back;
		document.getElementById("size-" + i).innerHTML = PEResult[i].size;
		document.getElementById("count-" + i).innerHTML =  PEResult[i].count;
		document.getElementById("remark-" + i).innerHTML = PEResult[i].remark;
		document.getElementById("manufacture-" + i).innerHTML = PEResult[i].manufacture;
		document.getElementById("sumitButton-" + i).style.display = "";


	}

	setTimeout(function() {
			PE()
		}, 5000);


}

function clear(PEResult) {
	
	for (var i = 0; i < 11; i++) {

		document.getElementById("id-" + i).innerHTML = "";
		document.getElementById("company-" + i).innerHTML = "";
		document.getElementById("branch-" + i).innerHTML = "";
		document.getElementById("printer-" + i).innerHTML = "";
		document.getElementById("product-" + i).innerHTML = "";
		document.getElementById("coating-" + i).innerHTML = "";
		document.getElementById("back-" + i).innerHTML = "";
		document.getElementById("size-" + i).innerHTML = "";
		document.getElementById("count-" + i).innerHTML = "";
		document.getElementById("remark-" + i).innerHTML = "";
		document.getElementById("manufacture-" + i).innerHTML = "";
		document.getElementById("sumitButton-" + i).style.display = "none";
		
	}
	output(PEResult)
}

function completeWork(){
	axios({
		method: "post",
		url: "/WorkOrder",
		data:{
			id:512
		}
	}).then(response => {
		let data = response.data;
		if(data.result == 0){
			alert("성공");
		}
	}).catch(function(error) {
		console.log(error);
	})
}