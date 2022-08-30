/**
 * 
 */
export class chkModule{
	chkPW(pw) {

		var num = pw.search(/[0-9]/g);
		var eng = pw.search(/[a-z]/ig);
		var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

		if (pw.length < 8 || pw.length > 20) {
			$('#pwdError').show();
			$('#pwdError').text("8자리 ~ 20자리 이내로 입력해주세요.");
			return false;
		} else if (pw.search(/\s/) != -1) {
			$('#pwdError').show();
			$('#pwdError').text("비밀번호는 공백 없이 입력해주세요.");
			return false;
		} else if (num < 0 || eng < 0 || spe < 0) {
			$('#pwdError').show();
			$('#pwdError').text("영문,숫자, 특수문자를 혼합하여 입력해주세요.");
			return false;
		} else {
			$('#pwdError').empty();
			return true;
		}

	}
	sessionClear() {
		const url = new URL(window.location.href);
		const urlParam = url.searchParams;
		var value = urlParam.get("logout")

		if (value == "true") {
			sessionStorage.clear();

		}

	}
}
