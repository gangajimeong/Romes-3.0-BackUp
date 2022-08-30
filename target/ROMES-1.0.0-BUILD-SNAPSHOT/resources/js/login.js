/**
 * 
 */

import { chkModule } from "./passwordChk.js";

document.addEventListener("DOMContentLoaded", function() {
	checkInputs();
});

function checkInputs() {

	const login = document.getElementsByClassName("loginData");
	const signup = document.getElementsByClassName("openSignup");
	const findInfo = document.getElementsByClassName("OpenFindInfo");
	
	if (login.length != 0) {
		document.getElementById("login_button").addEventListener('click', function() {
			if (!!login[0].value === false) {
				alert("아이디가 입력 되지 않았습니다.")
			} else if (!!login[1].value === false) {
				alert("비밀번호가 입력되지 않았습니다.")
			} else if (!!login[0].value && !!login[1].value) {
				document.getElementById('loginForm').submit();
			}
		})
	} else if (signup.length != 0) {
		 duplicatedIdCheck();
		document.getElementById('password').onkeyup = function() {

			var pw = this.value;
			const evt = new chkModule();
			evt.chkPW(pw)

			if ($('#password').val().trim() === "") {
				$('#pwdError').empty()
				$('#pwdError').hide();
			}


		}
	} else if (findInfo.length != 0) {
		const IdInputs = document.getElementsByClassName("findInfo");
		const passInputs = document.getElementsByClassName("findPass");
		const evt = new chkModule();
		IdInputs[0].addEventListener('keypress', function() {
			var pattern = /[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
			var check = /^[가-힣]+$/
			console.log(check.test(this.value))
			if (check.test(this.value) === false) {

				$(".findError").eq(0).show();
				$(".findError").eq(0).text("한글이 아닙니다.");
				this.value = this.value.replace(pattern, '');

			} else {
				$(".findError").eq(0).hide();
			}


		})
		document.getElementById("findID").addEventListener('click', function() {
			if (!!IdInputs[0].value === false) {
				alert("이름이 입력되지 않았습니다.")
			} else if (!!IdInputs[1].value === false) {
				alert("이메일이 입력되지 않았습니다.")
			} else if (!!IdInputs[2].value === false) {
				alert("힌트가 입력되지 않았습니다.")
			} else {

				let data = document.getElementById("findIdData")
				let formData = new FormData(data);
				axios.post("findUserId", formData).then(function(res) {
					let ret = confirm("아이디: " + res.data.loginid);
					if (ret) {
						location.href = "/ROMES";
					} else {
						location.reload();
					}

				})
			}
		})

		document.getElementById("findPassWord").addEventListener('click', function() {
			[...passInputs].forEach(function(data){
				console.log(data.value);
			})

			if (!!passInputs[0].value === false) {
				alert("이름이 입력되지 않았습니다.")
				return;
			} else if (!!passInputs[1].value === false) {
				alert("아이디가 입력되지 않았습니다.")
				return;
			} else if (!!passInputs[2].value === false) {
				alert("이메일이 입력되지 않았습니다.")
				return;
			} else {
				let data = document.getElementById("findPassData");
				let form = new FormData(data);

				axios.post('isUser', form).then(function(res) {
					console.log(res.data)
					if (res.data.result === 0) {
						$('#findModal').modal("show");
					
					document.getElementById("changePassword").addEventListener("keyup", function() {
						let pw = this.value;
						if (pw.length > 0){ evt.chkPW(pw)
						if(pw.length>7){document.getElementById("CheckPassword").disabled = false; }
						}else { $('#pwdError').empty(); }
					})
					document.getElementById("CheckPassword").addEventListener('keyup', function() {
						let match = document.getElementById("changePassword").value;
						let value = this.value;
						if (match === value) {
							$('.pwdError').eq(0).show();
							$('.pwdError').eq(0).text("확인").css('color', "green");
							document.getElementById("changeConfirm").disabled = false;
							document.getElementById("changeConfirm").addEventListener('click', function() {
								let pw = document.getElementById("CheckPassword").value;
								let data = new FormData();
								data.append("userid", res.data.id);
								data.append("pw",pw);
								let csrf = document.getElementsByName("_csrf")[0].value;
								axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf;
								axios.post("resetPassword" ,data).then(function(res){
									console.log(res.data)
									if(res.data.result ===0){
										location.href = "/ROMES";
									}else{
										alert("비밀 번호 변경 오류")
										return;
									}
								}).catch(function(e){
									console.log(e);
									location.reload();
								});
							})
						} else if (match != value) {
							document.getElementById("changeConfirm").disabled = true;
							if (value.length > 0) {
								$('.pwdError').eq(0).show();
								$('.pwdError').eq(0).text("비밀번호가 일치 하지 않습니다.").css('color', 'red');
							} else {
								$('.pwdError').eq(0).empty();
							}
						}
					})
					}else if(res.data.result === 3){
					    alert("회원가입 내용이 없음");
					    return;
					}else{
						alert("에러");
						return;
					}
					
				}).catch(function(e){
					console.log(e);
					alert("오류")
					location.reload();
				})
			}
		})

	}




}

function duplicatedIdCheck(){
	const loginIdInput = document.getElementById("loginIdCheckInput")
	const doubleCheckButtton = document.getElementById("double_check");
	const confirm = document.getElementById("confirmSingUp");
	const input = document.getElementsByClassName('Join-input');
	loginIdInput.addEventListener('focusout',function(){
		if(!!this.value===false){
			alert("아이디가 입력되지 않았습니다.")
		}
	})
	
	confirm.addEventListener('click',function(){
		let form = 	document.getElementById('signupForm')
		for(let i = 0; i<input.length;i++){
			if(!!document.getElementsByClassName('Join-input')[i].value === false){
				alert("빈 항목이 있습니다.")
				return;
			}
		}
		form.submit();
	})
	doubleCheckButtton.addEventListener('click',function(){
		let id = loginIdInput.value;
		if(id.length>0){
		axios.get('duplicationCheck',{params:{loginId:id}})
		.then(function(res){
			console.log(res.data)
			if(res.data.result === 0){
				
			alert("사용 할 수 있는 아이디 입니다.")
			document.getElementById("confirmSingUp").disabled = false;
			}
			if(res.data.result === 3){
				alert("이미 존재하는 아이디 입니다.")
				document.getElementById("confirmSingUp").disabled = true;
			}
		})
		}else{
			alert("입력된 아이디가 없습니다.")
			return;
		}
	})
}

