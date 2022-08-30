/**
 * 
 */
/**
* 
*/
export default class BrandManagement {

	Buttonevt() {
		/*$("#BrandManagementTable").ready(function(e) {alert("매장을 등록 하시려면 브랜드 목록을 클릭하세요") })*/
		const tbody = document.getElementById("BrandBody")
		let trs = tbody.children;
		[...trs].forEach(function(tr) {
			tr.addEventListener('click', MainTrEvt)
		})
		const updates = document.getElementsByClassName('BrandUpdate')[0];
		const createBtn = document.getElementById("NewBrand-register");
		const addBranchBtn = document.getElementById("addStore");
		const branchBtn = document.getElementById("Branch-register-btn");
		const trStoreUpdateBtn = document.getElementById("TrStoreForUpdate");
		const addStoreForUpdateBtn = document.getElementById("addStoreForUpdate");
		const inputs = document.getElementsByClassName("addressSearch-branch")
		const detailAddress = document.getElementsByClassName('detailAddress-branch');
		
		const BrandUpdateButton = document.getElementById("BrandUpdateButton");
		const StoreUpdateButton = document.getElementById("StoreUpdateButton");
		const deletebrand = document.getElementById("delete-brand");
		
		updates.addEventListener('click',function(){
			alert("브랜드 목록에서 브랜드를 선택해 주세요. ")
		})
		inputs[0].addEventListener('click', getAddress);
		detailAddress[0].addEventListener('focusout', plusDetailAddr)
		inputs[1].addEventListener('click', getAddress);
		detailAddress[1].addEventListener('focusout', plusDetailAddr1)
		
		createBtn.addEventListener('click', create);
		branchBtn.addEventListener('click', createBranch)
		addBranchBtn.addEventListener('click', addBranch);
		trStoreUpdateBtn.addEventListener('click',trUpdater);
		addStoreForUpdateBtn.addEventListener('click',addStoreForUpdate)
		BrandUpdateButton.addEventListener('click',update);
		StoreUpdateButton.addEventListener('click',updateBranch)
		deletebrand.addEventListener('click',deleteAxios);
		
		//excelImport
		const ExcelImportBtn = document.getElementById("brandExcel");
		ExcelImportBtn.addEventListener('change', readExcel);
	}
}
function addStoreForUpdate(){
	const inputs = document.getElementsByClassName("Store-u");
	const tbody = document.getElementById("StoreListsForUpdate");
	let mainTr = tbody.children;
	
	for(let i=0;i<mainTr.length;i++){
		let existName = mainTr[i].children[1].innerText;
		if(existName.replace(" ","")===inputs[1].value.replace(" ","")){
			alert("동일한 매장명이 있습니다.")
			return;
		}
	}
	
	let data = {}
	data.id = 0;
	data.title = inputs[1].value;
	for(let i=0; i<inputs[2].options.length;i++){
		if(inputs[2].options[i].value === inputs[2].value){
			data.company = inputs[2].options[i].text;
		}
	}
	data.company_id = inputs[2].value;
	data.addr = inputs[3].value;
	data.detail = inputs[4].value;
	createTrForUpdateStore(data);
	for(let i=0;i<inputs.length;i++){
		inputs[i].value="";
	}
	console.log(data);
}
function trUpdater(){
	const inputs = document.getElementsByClassName("Store-u");
	const tbody = document.getElementById("StoreListsForUpdate")
	let id = inputs[0].value;
	let company_id = inputs[2].value;
	let mainTr = tbody.children;
	let tr = document.getElementsByClassName("store-"+id)[0];
	let name = inputs[1].value;
	
	for(let i=0;i<mainTr.length;i++){
		let existName = mainTr[i].children[1].innerText;
		let existId = mainTr[i].children[0].innerText;
		
		if(existName.replace(" ","")===name.replace(" ","")&& existId!=id){
			alert("동일한 매장명이 있습니다.")
			return;
		}
	}
	let state = false;
	for(let i=0;i<mainTr.length;i++){
		let existId = mainTr[i].children[0].innerText;
		if(existId===id&&id!=0){
			state = true;
		}
	}
	if(state===false){alert("등록된 매장이 아닙니다."); return;}
	if(!!tr){
	let td = tr.children;
	td[1].innerText = inputs[1].value;
	
	for(let i=0; i<inputs[2].options.length;i++){
		if(inputs[2].options[i].value === company_id){
			td[2].innerText = inputs[2].options[i].text;
		}
	}
	td[2].className = company_id;
	td[3].innerText = inputs[3].value;
	td[4].innerText = inputs[4].value;
	}
	for(let i =0; i<inputs.length;i++){
		inputs[i].value = "";
	}
}

function deleteAxios() {
	const updates = document.getElementsByClassName('BrandUpdate')[0];
	if(updates.classList.contains("active")){
	let id = document.getElementsByClassName("Brand-u")[0].value;
	if(!!id===false){alert("브랜드 목록에서 브랜드를 선택해주세요");return;}
	let ret = confirm("브랜드를 삭제 하시겠습니까?");
	if(ret === false)return;
	axios.get('deleteBrand', { params: { id: id } })
		.then(function(res) {
			if (res.data) {
				location.reload();
			} else {
				alert("오류")
			}

		}).catch(function(e) {
			console.log(e)
		})
	}
}



function getAddress() {

	console.log("입력")
	let address = this;

	new daum.Postcode({

		oncomplete: function(data) {
			address.value = data.roadAddress;

		}
	}).open();
}
function plusDetailAddr() {
	const inputs = document.getElementsByClassName("addressSearch-branch")
	const details = document.getElementsByClassName('detailAddress-branch')
	let addr = inputs[0].value;
	if (addr == "주소 입력") return;
	let detail = details[0].value;
	details[0].value = addr + " " + detail;

}
function plusDetailAddr1() {
	const inputs = document.getElementsByClassName("addressSearch-branch")
	const details = document.getElementsByClassName('detailAddress-branch')
	let addr = inputs[1].value;
	if (addr == "주소 입력") return;
	let detail = details[1].value;
	details[1].value = addr + " " + detail;

}

function MainTrEvt() {
	
	const updates = document.getElementsByClassName('BrandUpdate')[0];
	
	
	const inputs = document.getElementsByClassName('Brand-u')
	const tbody = document.getElementById("BranchBody");
	const storeTbody = document.getElementById("StoreListsForUpdate");
	let id = this.children[1].innerText;
	let name = this.children[2].innerText;
	
	axios.get('selectBranch', { params: { id: id } })
		.then(function(res) {
			let ret = res.data;
			if (ret[0] == "No Info") { 
				alert("등록된 매장이 없습니다."); 
				clearTable(tbody);
				writeBrand(name);
				return; 
			}
			clearTable(tbody);
			writeBrand(name);
			for (let i = 0; i < ret.length; i++) {
				createMainBranchTr(ret[i]);
			}
		})
		.catch(function(e) { console.log(e); alert("error") })
	
	if(updates.classList.contains('active')){
		clearTable(storeTbody); 
		axios.get('SelectBrandById', { params: { id: id } })
			.then(function(res) {
				let ret = res.data;
				console.log(ret)
				document.getElementById("brand_idForUpdate").value = ret[0];
				console.log(document.getElementById("brand_idForUpdate").value)
				for (let i = 0; i < 6; i++) {
					inputs[i].value = ret[i];
				}
				let branchs = ret[6];
				[...branchs].forEach(function(branch){
					createTrForUpdateStore(branch);
				})
			}).catch(function(e) {
				console.log(e)
				alert("오류")
				return;
			})
		
	}

}
function writeBrand(name) {

	const brand = document.getElementById("brandListName");
	brand.innerText = " ";
	brand.innerText = name;

}
// 등록/수정/삭제
function create() {
	const branch = document.getElementById("Branch-register");
	const inputs = document.getElementsByClassName("Store-c");
	const branchBtn = document.getElementById("Branch-register-btn");
	const form = document.getElementById("createBrandManagement");
	let data = new FormData(form);
	for (let key of data.keys()) {
		if (!!data.get(key) === false && key != "remarks") {
			alert("빈 항목이 있습니다.")
			return;
		};
	}

	axios.post("createBrand", data)
		.then(function(res) {
			let data = res.data;
			createBrandTr(data);
			location.reload();
//			let ret = confirm("매장을 입력하시겠습니까?")
//			if (ret) {
//				document.getElementById("brandId").value = data.id;
//				branch.style.opacity = "1";
//				[...inputs].forEach(function(input) {
//					input.disabled = false;
//				})
//				branchBtn.disabled = false;
//			} else {
//				location.reload();
//			}
		}).catch(function(e) {
			console.log(e);
			alert("오류")
		})
}
//업데이트 마무리
function update() {
	const form = document.getElementById("updateBrandManagement");
	let data = new FormData(form);
	for (let key of data.keys()) {
		if (!!data.get(key) === false && key != "remarks") {
			alert("빈 항목이 있습니다.")
			return;
		};
	}

	axios.post("updateBrand", data)
		.then(function(res) {
			let data = res.data;
			console.log(data)
			let row = findRowAndUpdate(data.id)
		    let td = row.children;
		    td[1].innerText = data.id
		    td[2].innerText = data.companyName;
		    td[3].innerText = data.company;
		    td[4].innerText = data.CEO_Name;
		    td[5].innerText = data.phone;
		    alert("수정되었습니다.")
		    location.reload();
		}).catch(function(e) {
			console.log(e);
			alert("오류")
		})
}
function updateBranch(){
	const tbody = document.getElementById("StoreListsForUpdate");
	let data = new FormData();
	let id = document.getElementById("brand_idForUpdate").value;
	if (!!id === false) return;
	let tr = tbody.children;
	if (tr.length === 0) {
		alert("입력된 매장이 없습니다.")
		return;
	}
	//if
	if(tr.length === 1){
		let obj = {};
		let td = tr[0].children;
		let cons_id = td[2].className;
		cons_id = cons_id.substring(cons_id.indexOf("-")+1,cons_id.length);

		obj.id = td[0].innerText;
		obj.name = td[1].innerText;
		obj.cons = cons_id;
		obj.address = td[3].innerText;
		obj.detail = td[4].innerText;
		data.append("tr",0);
		data.append("data", JSON.stringify(obj));
	}else{
	for (let i = 0; i < tr.length; i++) {
		let obj = {};
		let td = tr[i].children;
		let cons_id = td[2].className;
		cons_id = cons_id.substring(cons_id.indexOf("-")+1,cons_id.length);

		obj.id = td[0].innerText;
		obj.name = td[1].innerText;
		obj.cons = cons_id;
		obj.address = td[3].innerText;
		obj.detail = td[4].innerText;
		data.append("tr",1);
		data.append("data", JSON.stringify(obj));
	}
	console.log(data);
	}
	data.append("brand", id)
	let csrf = document.getElementsByName("_csrf")[0].value;
	axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
	axios.post('updateBranch', data).then(function(res) {
//		if (res.data) {
//			alert("수정 되었습니다.")
//			location.reload();
//		}
		alert("수정되었습니다.")
	    location.reload();
	});
}
function addBranch() {
	const inputs = document.getElementsByClassName("Store-c")
	let branch = inputs[0].value;
	if (!!branch === false) { alert("매장명이 입력되지 않았습니다."); return; }
	let address = inputs[3].value;
	if (!!address === false) { address = false; }
	if (!!branch === false) return;
	let data = {};
	data.name = branch;
	data.address = address;
	createBranchTr(data);
	InputsErase("Store");
}
function createBranch() {
	const tbody = document.getElementById("StoreLists")
	let data = new FormData();
	let id = document.getElementById("brandId").value;
	if (!!id === false) return;
	let tr = tbody.children;
	if (tr.length === 0) {
		alert("입력된 매장이 없습니다.")
		return;
	}
	for (let i = 0; i < tr.length; i++) {
		let obj = {};
		let td = tr[i].children;

		obj.name = td[0].innerText;
		obj.cons = td[1].innerText
		obj.address = td[2].innerText;

		data.append("data", JSON.stringify(obj));
	}
	data.append("brand", id)
	let csrf = document.getElementsByName("_csrf")[0].value;
	axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
	axios.post('createBranch', data).then(function(res) {
		if (res.data) {
			alert("등록 되었습니다.")
			location.reload();
		}
	});
}
function clearTable(tbody) {
	let tr = tbody.children;
	let len = tr.length;
	for (let i = 0; i < len; i++) {
		tbody.deleteRow(-1);
	}
}
function createMainBranchTr(data) {
	const tbody = document.getElementById("BranchBody");
	let len = tbody.children.length;
	let row = tbody.insertRow();
	
	let id = row.insertCell();
	id.style.display = "none";
	id.innerText = data.id;

	let no = row.insertCell();
	no.style.width = "3%";
	no.innerText = len + 1;

	let branch = row.insertCell();
	branch.style.width = "25%";
	branch.innerText = data.title;

	let cons = row.insertCell();
	cons.style.width = "15%";
	cons.innerText = data.cons;

	let addr = row.insertCell();
	addr.style.width = "57%";
	if (!!data.addr === false) addr.innerText = "정보 없음"
	else addr.innerText = data.addr;
}
function createBranchTr(data) {
	if (!!data === false) return;
	let state = checkExistStoreRow(data.name);
	if (state) { alert("이미 추가된 항목입니다."); return; }


	const tbody = document.getElementById("StoreLists");
	const ico = document.getElementById("trashForStore");

	let row = tbody.insertRow();
	let name = row.insertCell();
	name.style.width = "20%";
	if (!!data.name) name.innerText = data.name;

	let cons = row.insertCell();
	cons.style.width = "20%";
	if (!!data.cons) cons.innerText = data.cons;

	let address = row.insertCell();
	address.style.width = "55%";
	if (!!data.address) address.innerText = data.address;

	let trash = row.insertCell();

	let btn = document.createElement("button");
	btn.className = "btn btn-outline-secondary p-0"

	let icon = ico.cloneNode();
	btn.appendChild(icon);
	trash.appendChild(btn);

	btn.addEventListener("click", deleteTargetRow);

}

function createBrandTr(data) {
	let state = checkExistRow(data.id);
	if (state) return;
	const tbody = document.getElementById("BrandBody");
	let len = tbody.children.length;
	let row = tbody.insertRow();
	
	let idx = row.insertCell();
	idx.style.width = "3%";
	idx.innerText = len + 1;


	let id = row.insertCell();
	id.style.display = "none";
	if (!!data.id) { id.innerText = data.id }

	let name = row.insertCell();
	name.style.width = "25%";
	if (!!data.companyName) { name.innerText = data.companyName }

	let company = row.insertCell();
	company.style.width = "20%";
	if (!!data.company) { company.innerText = data.company }

	let ceo = row.insertCell();
	ceo.style.width = "25%";
	if (!!data.CEO_Name) ceo.innerText = data.CEO_Name;

	let phone = row.insertCell();
	phone.style.width = "27%";
	if (!!data.phone) phone.innerText = data.phone;

}
function createTrForUpdateStore(data){
	const tbody = document.getElementById("StoreListsForUpdate");
	const ico = document.getElementById("trashForStoreForUpdate");
	
	let row = tbody.insertRow();
	if(data.id ===0)row.style.background = "#B1B1B1"
	row.addEventListener('click',trToInput);
	row.className = "store-"+data.id;
	let id = row.insertCell();
	id.style.display = "none";
	if(data.id===0)id.innerText = data.id;
	if (!!data.id)id.innerText = data.id;
	
	let name = row.insertCell();
	name.style.width = "20%";
	if (!!data.title) name.innerText = data.title;

	let cons = row.insertCell();
	cons.style.width = "20%";
	if (!!data.company){
		cons.className = "cons-"+data.company_id
		cons.innerText = data.company;
	} 

	let address = row.insertCell();
	address.style.width = "55%";
	if (!!data.addr) address.innerText = data.addr;

	let detail = row.insertCell();
	detail.style.display = "none";
	if(!!data.detail) detail.innerText = data.detail;

	let trash = row.insertCell();

	let btn = document.createElement("button");
	btn.className = "btn btn-outline-secondary p-0"

	let icon = ico.cloneNode();
	btn.appendChild(icon);
	trash.appendChild(btn);

	btn.addEventListener("click", deleteTargetRowU);
}
function findRowAndUpdate(id){
	let row = document.getElementById("Brand-"+id);
	return row;
}
function checkExistRow(id) {
	if (!!id === false) return;
	const tbody = document.getElementById("BrandBody");
	let state = false;
	let tr = tbody.children;
	for (let i = 0; i < tr.length; i++) {
		let existId = tr[i].children[2].innerText;
		if (existId === id) {
			state = true;
			break;
		}
	}
	return state;
}
function checkExistStoreRow(newStore) {
	if (!!newStore === false) return;
	const tbody = document.getElementById("StoreLists");
	/*const mainTbody = document.getElementById("BranchBody");*/

	let state = false;
	let tr = tbody.children;
	for (let i = 0; i < tr.length; i++) {
		let existStore = tr[i].children[0].innerText;
		if (existStore.replace(" ", "") === newStore.replace(" ", "")) {
			state = true;
			break;
		}
	}
	/*let maintr = mainTbody.children;
	for(let i; i<maintr.length;i++ ){
		let exist = maintr[i].children[2].innerText;
		if(exist.replace(" ","") === newStore.replace(" ","")){
		state = true;
		break;	
		}
		
	}*/
	return state;
}
function trToInput(event){
	event.stopPropagation();
	const inputs = document.getElementsByClassName("Store-u");
	const len = inputs.length;
	let tr = this;
	let td = tr.children;
	for(let i=0; i<len;i++){
		inputs[i].value = td[i].innerText;
	}
	
}

function InputsErase(target) {
	const Brand = document.getElementsByClassName("Brand-c");
	const Store = document.getElementsByClassName("Store-c");

	switch (target) {
		case "Brand": {
			[...Brand].forEach(function(e) {
				e.value = "";
			})
			break;
		};
		case "Store": {
			[...Store].forEach(function(e) {
				e.value = "";
			})
			break;
		}

	}
}


function deleteTargetRow() {
	const tbody = document.getElementById("StoreLists");
	let target = this.parentNode.parentNode;
	tbody.deleteRow(target.rowIdx);
}
function deleteTargetRowU(event) {
	event.stopPropagation()
	let state = confirm("정말 삭제 하시겠습니까?")
	if(state === false)return;
	const tbody = document.getElementById("StoreListsForUpdate");
	let target = this.parentNode.parentNode;
	let id = target.children[0].innerText;
	
	if(id>0){
		const mainTbody = document.getElementById("BranchBody");
		let brand = document.getElementById("brand_idForUpdate").value;
		axios.get("deleteBranch",{params:{id:id, brand:brand}})
		.then(function(res){ 
			console.log(res.data);
			
		})
		let idx = target.rowIndex
		tbody.deleteRow(idx-1);
		mainTbody.deleteRow(idx-1);
	}else{
		tbody.deleteRow(target.rowIndex-1);
	}
	
	
	
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
			//console.log(test)
			//console.log(formData.get("hi"))
			let csrf = document.getElementsByName("_csrf")[0].value;
			axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
			axios.post("brandExcel", formData)
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





