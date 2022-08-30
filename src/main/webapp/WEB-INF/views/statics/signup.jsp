<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<div class="container-fluid h-100 openSignup"
	style="background: #5E7DC0;">
	<div class="row align-items-center h-100">
		<div class="col-md-6 col-lg-6  mx-auto">
			<div class="card  border-white mt-3">

				<div class="card-header login-header row no-gutters bg-white "
					style="padding: 2px 10px 2px 10px">
					<div class="col-md-4">
						<img class="LoginLogo" src="img/mainLogo.png"
							style="height: 8.610079575596817vh;">
					</div>
					<div class="col-md-6">
						<b style="font-size: 40px;">ROMES 3.0</b>
					</div>
					<div class="col-md-2" style="padding-top: 2.6525198938992043vh;">
						<b style="font-size: 2.6525198938992043vh;">회원가입</b>
					</div>
				</div>


				<div class="card-body  border-dark loginBgd"
					style="padding-top: 5px;">

					<c:url var="signUpPath" value="/signup" />
					<f:form modelAttribute="user" action="${ signUpPath }"
						method="post" id="signupForm">

						<div class="form-group"
							style="margin-bottom: 1.9893899204244032vh">
							<label><b>아이디</b></label>
							<div class="row">
								<div class="col-md-10 col-sm-10 col-xl-10">
									<f:input path="LoginId" cssClass="form-control Join-input"
										id="loginIdCheckInput" placeholder="아이디" />
									<f:errors path="LoginId" element="div"
										cssClass="alert text-danger a-font" />
								</div>
								<div class="col-md-2 col-sm-2 col-xl-2"
									style="padding: 0 0 0 0;">
									<button type="button" class="btn btn-secondary ml-1"
										id="double_check">
										<b>중복 확인</b>
									</button>
								</div>
							</div>
						</div>


						<div class="form-group"
							style="margin-bottom: 1.9893899204244032vh">
							<label><b>비밀번호</b></label>
							<f:password path="LoginPw" cssClass="form-control Join-input"
								id="password" placeholder="비밀번호" />
							<f:errors path="LoginPw" element="div"
								cssClass="alert text-danger a-font" />
							<p id="pwdError" Class="alert text-danger a-font"
								style="display: none; padding: 5px 0px 0px 0px"></p>
						</div>
						<div class="form-group"
							style="margin-bottom: 1.9893899204244032vh">
							<label><b>이름</b></label>
							<f:input path="name" cssClass="form-control Join-input"
								placeholder="이름" />
							<f:errors path="name" element="div"
								cssClass="alert text-danger a-font" />
						</div>

						<div class="form-group"
							style="margin-bottom: 1.9893899204244032vh">
							<label><b>직책</b></label>
							<f:input path="position" cssClass="form-control Join-input"
								placeholder="직책" />
							<f:errors path="position" element="div"
								cssClass="alert text-danger a-font" />
						</div>
						<div class="form-group"
							style="margin-bottom: 1.9893899204244032vh">
							<label><b>번호</b></label>
							<f:input path="tel" cssClass="form-control Join-input"
								placeholder="010-0000-0000" />
							<f:errors path="tel" element="div"
								cssClass="alert text-danger a-font" />
						</div>
						<%-- <div class="form-group"
							style="margin-bottom: 1.9893899204244032vh">
							<label><b>부서</b></label>
							<f:input path="division.division"
								cssClass="form-control Join-input" placeholder="부서" />
							<f:errors path="division.division" element="div"
								cssClass="alert text-danger a-font" />
						</div> --%>
						<div class="form-group"
							style="margin-bottom: 1.9893899204244032vh">
							<label><b>부서</b></label>
							<f:select path="division.id"
								cssClass="form-control Join-input"
								style="padding:0;">
								<c:forEach var="item" items="${division}" varStatus="i">
									<f:option value="${item.id}">${item.division}</f:option>
								</c:forEach>
							</f:select>
						</div>

						<%-- <div class="form-group"
							style="margin-bottom: 1.9893899204244032vh">
							<label><b>팀</b></label>
							<f:input path="division.team" cssClass="form-control Join-input"
								placeholder="팀" />
							<f:errors path="division.team" element="div"
								cssClass="alert text-danger a-font" />
						</div> --%>
						<div class="form-group"
							style="margin-bottom: 1.9893899204244032vh">
							<label><b>이메일</b></label>
							<f:input path="email" cssClass="form-control Join-input"
								placeholder="이메일" />
							<f:errors path="email" element="div"
								cssClass="alert text-danger a-font" />
						</div>

						<div class="form-action">
							<button type="button" class="btn btn-secondary btn-lg btn-block"
								id="confirmSingUp" disabled="disabled">회원 가입</button>
						</div>



					</f:form>

				</div>

			</div>
		</div>
	</div>
</div>

