export default class UserInfo {
	userInfo() {
		let classification = 0;
		let state = false;
		let compare = false;
		findDivision();

		const currentPW = document.getElementById('current-password');
		currentPW.addEventListener('change', function() {
			let data = new FormData();
			if (currentPW.value != "") {
				data.append("data", currentPW.value);

				let csrf = document.getElementsByName("_csrf")[0].value;
				axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
				axios.post('checkCurrentPW', data)
					.then((res) => {
						if (res.data == true) {
							state = true;
							console.log(true);
						} else {
							state = false;
							console.log(false);
						}
					})
			}
		})

		const newPW = document.getElementById('new-password');
		newPW.addEventListener('change', function() {
			compare = false;
		})

		const modifyConfirm = document.getElementById('modify-confirm');
		modifyConfirm.addEventListener('click', function() { updateInfo(state, compare, classification) })

		const checkPW = document.getElementById('check-password');
		const position = document.getElementById('info-position');
		const phone = document.getElementById('info-phone');
		const division = document.getElementById('info-division');


		const email = document.getElementById('info-email');


		const modifyBtn = document.getElementById('modify-info');
		modifyBtn.addEventListener('click', function() {
			let modi = confirm("정보를 수정하겠습니까?");
			if (modi == true) {
				currentPW.disabled = false;
				newPW.disabled = true;
				checkPW.disabled = true;
				position.disabled = false;
				phone.disabled = false;
				division.disabled = false;
				email.disabled = false;
				modifyConfirm.disabled = false;
				classification = 2;
			}
		});

		const modifyPWBtn = document.getElementById('modify-password');
		modifyPWBtn.addEventListener('click', function() {
			let modi = confirm("비밀번호를 변경하겠습니까?");
			if (modi == true) {
				currentPW.disabled = false;
				newPW.disabled = false;
				checkPW.disabled = false;
				position.disabled = true;
				phone.disabled = true;
				division.disabled = true;
				email.disabled = true;
				modifyConfirm.disabled = false;
				classification = 1;
			}
		});

		const comparePW = document.getElementById('check-password');
		comparePW.addEventListener('change', function() {
			if (comparePW.value == newPW.value) {
				compare = true;
			} else {
				compare = false;
			}
		})

	}

}


function updateInfo(state, compare, classification) {
	console.log(classification, state, compare);

	switch (classification) {
		case 1:
			switch (state) {
				case true:
					switch (compare) {
						case true: modifyPW(); break;
						case false: alert("비밀번호를 확인해주세요"); break;
					} break;
				case false: alert("비밀먼호가 틀립니다"); break;
			} break;
		case 2:
			switch (state) {
				case true: modifyInfo(); break;
				case false: alert("비밀번호를 확인해주세요"); break;
			} break;

	}

}


function modifyPW() {
	const newPassword = document.getElementById('new-password').value;
	console.log(newPassword);
	let data = new FormData();
	data.append('newPassword', newPassword);
	if (confirm("수정하시겠습니까?")) {
		let csrf = document.getElementsByName("_csrf")[0].value;
		axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
		axios.post('modifyPassword', data)
			.then(res => {
				if (res.data == true) {
					console.log(res.data);
					alert("수정 완료");
					location.reload();
				} else {
					alert("오류");
					location.reload();
				}
			})
	}
}

function modifyInfo() {
	const form = document.getElementById('modify-info-form');
	let data = new FormData(form);
	for (let key of data.keys()) {
		if (!!data.get(key) === false) {
			alert("선택하지 않았거나 빈 항목이 있습니다.")
			return;
		}
	}
	if (confirm("수정하시겠습니까?")) {
		let csrf = document.getElementsByName("_csrf")[0].value;
		axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
		axios.post('modifyUserInfo', data)
			.then(res => {
				if (res.data == true) {
					console.log(res.data);
					alert("수정 완료")
					location.reload();
				} else {
					alert("오류");
					location.reload();
				}
			})
	}
}

function findDivision() {
	let data;

	axios.get('infoFindDivision')
		.then(res => {
			data = res.data;
			$("#info-division").val(data).prop("seleted",true);
		})


}



