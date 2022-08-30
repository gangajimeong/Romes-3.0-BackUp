export default class ConstructionOrder {
	modalToggle() {
		//시공지시관리 목록 모달창 toggle
		const waitList = document.querySelectorAll(".modalClick");
		[...waitList].forEach((btn) => {
			btn.addEventListener('click', detail);
			btn.addEventListener('click', onoff);
		})
		const modalbg = document.querySelector(".constModal");
		const modalcont = document.querySelector(".modalContents");
		const closebtn = document.querySelector(".closeBtn");

		modalbg.addEventListener('click', onoff);
		closebtn.addEventListener('click', onoff);

		function onoff() {
			modalbg.classList.toggle('on');
			modalcont.classList.toggle('on');
			closebtn.classList.toggle('on');
			constructionOrderBtn[0].style.display = "block";
		}
		
		const constructionOrderBtn = document.getElementsByClassName('construction-order-btn');
		[...constructionOrderBtn].forEach((btn) => {
			btn.addEventListener('click', ConstructionConfirm);
		})
		
		
		const modalOpen = document.getElementsByClassName("coList");
		[...modalOpen].forEach((btn) => {
			btn.addEventListener('click', coList);
		})
		
	}

};

function detail() {
	const title = document.getElementById("title");
	const product = document.getElementById("product");
	const brand = document.getElementById("brand");
	const branch = document.getElementById("branch");
	const orderDate = document.getElementById("Construction_orderDate");
	const image = document.getElementById("construction-sample-image");
	const detailId = document.getElementById('constructionOrder-detail-id');
	const cId = document.getElementById('constructionOrder_company');

	// 테이블 초기화
	detailId.innerText = "";
	title.innerText = "";
	product.innerText = "";
	brand.innerText = "";
	branch.innerText = "";
	orderDate.innerText = "";

	let tr = this;
	let td = tr.children;
	let id = td[0].innerText;
	//	IdInput.value = id;

	detailId.innerText = id;
	title.innerText = td[7].innerText;
	product.innerText = td[3].innerText;
	brand.innerText = td[8].innerText;
	branch.innerText = td[2].innerText;
	orderDate.innerText = td[4].innerText;

	image.src = "image?imagename=" + td[9].innerText;
	cId.value = td[10].innerText;
	
	console.log(id);

}

function coList(){
	const title = document.getElementById("conList-title");
	const product = document.getElementById("conList-product");
	const brand = document.getElementById("conList-brand");
	const branch = document.getElementById("conList-branch");
	const orderDate = document.getElementById("conList-orderDate");
	const image = document.getElementById("conList-sample-image");
	const company = document.getElementById("conList-company");
	
	title.innerText = "";
	product.innerText = "";
	brand.innerText = "";
	branch.innerText = "";
	orderDate.innerText = "";
	company.innerText = "";
	
	$('#constOrder-modal').modal('show');
	
	
	let td = this.children;
	console.log(this);
	
	
	title.innerText = td[2].innerText;
	product.innerText = td[10].innerText;
	brand.innerText = td[3].innerText;
	branch.innerText = td[4].innerText;
	orderDate.innerText = td[5].innerText;
	company.innerText = td[6].innerText;
	
	image.src = "image?imagename=" + td[11].innerText;
}

function ConstructionConfirm() {
	const Company = document.querySelector("#constructionOrder_company");
	const id = document.getElementById('constructionOrder-detail-id').innerText;
	let company = Company.value;

	let formData = new FormData();


	formData.append("id", id);
	formData.append("company", company)

	console.log(id);
	console.log(company);

	if (confirm("등록하시겠습니까?")) {
		let csrf = document.getElementsByName("_csrf")[0].value;
		axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
		axios.post('ConstructionConfirm', formData)
			.then(res => {
				let data = res.data;
				if (data != false) {
					alert("시공지시가 등록되었습니다.");
					location.reload();
				} else {
					alert("등록 실패");
					location.reload();
				}
			})
	}

}