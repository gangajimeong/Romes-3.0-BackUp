<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>
<div class="container h-100">
	<div class="row align-items-center h-100">
		<div class="col-md-6 col-lg-6  mx-auto">
			<div class="card border-0" style="margin-top: 23.872679045092838vh;">
				<div class="card-header login-header row no-gutters bg-white">
					<div class="col-md-3">
						<img class="LoginLogo" src="img/mainLogo.png"
							style="height: 10.610079575596817vh;">
					</div>
					<div class="col-md-9">
						<b style="font-size: 50px;">ROMES 3.0</b>
					</div>
				</div>

				<div class="card-body  border-dark loginBgd">
					<form action="<c:url value='/loginAsk' />" method="post"
						class="mb-0" id="loginForm">

						<div class="form-group mt-2">
							<input type="text" name="loginid" class="form-control loginData"
								placeholder="아이디">
						</div>
						<div class="form-group mt-3">
							<input type="password" name="loginpw"
								class="form-control loginData" placeholder="비밀번호">
						</div>
						<div class="form-action ">
							<button type="button" class="btn btn-login btn-block  "
								id="login_button">
								<b>로그인</b>
							</button>
						</div>
						<s:csrfInput />
						<span>${requestScope.loginFailMsg}</span>
					</form>
				</div>
				<div class="card-footer login-footer bg-white ">
					<div class="float-right" style="padding-right: 0px;">
						<a href="<c:url value='/signup' />" style="color: black;"><b>회원가입</b></a>
						&nbsp;&nbsp; <a href="<c:url value='/findUserInfo' />"
							style="color: black;"><b>아이디|비밀번호 찾기</b> </a>&nbsp;&nbsp;
					</div>
				</div>

			</div>
		</div>

	</div>
</div>




