/**
 * 
 */
import ExcelFunctions from './ExcelFunction.js';
export default class ReceivedOrder {
	register() {
		const button = document.getElementById("register-suju-btn");
		const addButton = document.getElementById("add-ProductForSuju");
		const register = document.getElementById("register-suju-btn");
		button.addEventListener('click', register)
		addButton.addEventListener('click', addProduct)
		register.addEventListener('click',registerOrderInfo)
		
	}
	fileInputEvt() {
		const designButton = document.getElementById("uploadSamleImges");
		var fileData = document.getElementById("sampleUpload");
		designButton.addEventListener('click', function() { fileData.click() })
		fileData.addEventListener('change', fileListViewEvt)

	}
	setExcelImportEvt(){
		console.log('adjust event')
		var excelImport = document.getElementById('receive_order_excelImport');
		excelImport.addEventListener('change',function(){
			let tool = new ExcelFunctions;
			let workbook = tool.readExcel(this.value);
			let sheet = workbook.getWorkSheet(1);
			console.log(sheet.getRow(1).getCell(1).value);
		})
	}
}
function fileListViewEvt() {
	var fileData = document.getElementById("sampleUpload");
	const sampleList = document.getElementById("SampleList");
	const datas = new DataTransfer();
	if (fileData.files.length > 0) {
		for (var i = 0; i < fileData.files.length; i++) {
			datas.items.add(fileData.files[i]);
			var Row = sampleList.insertRow();
			Row.className = fileData.files[i].lastModified
			var num = Row.insertCell();
			var cell = Row.insertCell();
			var deleteCell = Row.insertCell();
			cell.innerText = fileData.files[i].name
			num.innerText = num.parentNode.rowIndex + 1;
			deleteCell.innerHTML = "&times;"
			deleteCell.addEventListener('click', function() {
				datas.items.remove(this.parentNode.rowIndex);
				fileData.files = datas.files;
				sampleList.deleteRow(this.parentNode.rowIndex);
				console.log(fileData.files);
			})

		}
	}
	console.log(fileData.files)
}
function register() {
	const form = document.getElementById("sujuData");


	let data = new FormData(form);
	for (let key of data.keys) {
		console.log(key + ":" + data.get(key));
	}

}
function addProduct() {
	let obj = [];
	const product = document.getElementsByClassName("orderedProduct-c");
	const tbody = document.getElementById("OrderProductLists");
	const fileInput = document.getElementById("sampleUpload");
	const form = document.getElementById("CreateOrderProduct");
	let trash = document.getElementById("trashForProduct").cloneNode();
	let row = tbody.insertRow();
	let data = new FormData(form);

	
	// 제품 데이터 생성
	for (var j = 0; j < fileInput.files.length; j++) {
		data.append('file', fileInput.files[j])
	}

	for (let key of data.keys()) {
		console.log(key + ":" + data.get(key));
		if (!!data.get(key) === false && key != "file") {
			alert("입력되지 않은 항목이 있습니다.")
			return;
		}
	}
	
	axios.post("createOrderProduct",data)
	.then(function(res){
		let ret = res.data;
		//
		
		let id = row.insertCell();
		id.innerText = ret.product;
		id.style.display = "none";
		
		for (let i = 0; i < product.length; i++) {
		if (i === 1 || i === 3) {
			let value = product[i].value;
			let td = row.insertCell();
			if (i === 3) value = parseInt(value);
			td.innerText = value;
			if (i === 1) td.style.width = "70%";
			if (i === 3) td.style.width = "25%"
		}
		if (i === product.length - 1) {
			let td = row.insertCell();
			let btn = document.createElement("button");
			btn.className = "btn btn-scondary p-0"
			btn.appendChild(trash);
			td.appendChild(btn);
			btn.addEventListener('click', deleteRow)
			td.style.width = "5%";
		}


	}
	}).catch(function(e){
		console.log(e)
		alert("오류")
	})
	
	
	
	
}
function checkTable(data){
	const tbody = document.getElementById("OrderProductLists");
	let tr = tbody.children;
	let state = false;
	[...tr].forEach(function(e){
		let id = e.children[0].innerText
		if(id == data)state = true;
	})
	return state;
}
function deleteRow() {
	const tbody = document.getElementById("OrderProductLists");
	let target = this.parentNode.parentNode;
	tbody.deleteRow(target.rowIdx);
}
function registerOrderInfo(){
	const products = document.getElementById("OrderProductLists");
	const form = document.getElementById("sujuData");
	let tr = products.children
	let data = new FormData(form)
	for (let key of data.keys()) {
		console.log(key + ":" + data.get(key));
	}
	let products_id = []
	for(let i=0; i< tr.length;i++){
		let id = tr[i].children[0].innerText;
		products_id[i] = id;
	}
	console.log(products_id)

}
