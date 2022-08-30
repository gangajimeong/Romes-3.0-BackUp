<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page pageEncoding="utf-8"%>
<div class="jumbotron row no-gutters"
	style="padding: 10px 10px 10px 10px; background: #5E7DC0; color: white">
	<div class="col-md-1 ">
		<img class="LoginLogo float-right" src="img/mainLogo.png"
			style="height: 5.610079575596817vh;">
	</div>
	<div class="col-md-1 " style="padding-left: 0px;">
		<b class="float-left mt-2" style="font-size: 20px;">ROMES 3.0</b>
	</div>
	<div class="col-md-10" style="padding-left: 0px;">
		<b class="float-left" style="font-size: 30px;">아이디/비밀번호 찾기</b>
	</div>
</div>
<div class="container OpenFindInfo">
	<div class="card-deck">

		<div class="card">
			<div class="card-header" style="background: #5E7DC0; color: white">
				<b>아이디 찾기</b>
			</div>
			<div class="card-body">
				<form method="post" id="findIdData">
					<div class="form-group form-group-sm">
						<div class="form-group ">
							<label><b>이름</b></label> <input type="text"
								Class="form-control findInfo" placeholder="이름" name="name" />
							<p Class="findError alert text-danger a-font"
								style="display: none; padding: 5px 0px 0px 0px; color: red;"></p>
						</div>
						<div class="form-group">
							<label><b>Email</b></label> <input Class="form-control findInfo"
								type="email" placeholder="example@example.com" name="email" />
						</div>
						<div class="form-group">
							<label><b>번호</b></label> <input Class="form-control findInfo"
								type="tel" placeholder="000-0000-0000" name="phone" />
						</div>

						<div class="form-action">
							<s:csrfInput />
							<input type="button" class="btn btn-Secondary btn-lg btn-block "
								id="findID" value="확인">
						</div>
					</div>
				</form>


			</div>
		</div>



		<div class="card">
			<div class="card-header" style="background: #5E7DC0; color: white">
				<b>비밀번호 찾기</b>
			</div>
			<div class="card-body">
				<c:url var="signUpPath" value="/signup" />
				<form method="post" id="findPassData">
					<div class="form-group form-group-sm">
						<div class="form-group ">
							<label><b>이름</b></label> <input Class="form-control findPass"
								type="text" value="" placeholder="이름" name="name" />
						</div>
						<div class="form-group">
							<label><b>아이디</b></label> <input Class="form-control findPass"
								type="text" value="" placeholder="아이디" name="id" />
						</div>
						<div class="form-group">
							<label><b>Email</b></label> <input Class="form-control findPass"
								type="email" placeholder="example@example.com" name="email" />
						</div>
						<div class="form-group">
							<label><b>번호</b></label> <input Class="form-control findPass"
								type="tel" placeholder="000-0000-0000" name="phone" />
						</div>

						<div class="form-action">
							<s:csrfInput />
							<input type="button" class="btn btn-Secondary btn-lg btn-block"
								value="확인" id="findPassWord">
						</div>

					</div>
				</form>
			</div>

		</div>


	</div>

</div>
<!--Modal-->
<div class="modal fade" id="findModal" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">
					<b>비밀번호 변경</b>
				</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form id="changePasswordData">
					<s:csrfInput />
					<input type="hidden" id="user_id_id">
					<div class="form-group row">
						<label for="inputPassword" class="col-sm-3 col-form-label"
							style="padding-right: 0px;"><b>비밀번호:</b></label>
						<div class="col-sm-9">
							<input type="password" class="form-control" id="changePassword"
								placeholder="Password">
							<p id="pwdError" Class="alert text-danger a-font"
								style="display: none; padding: 5px 0px 0px 0px; margin-bottom: 0px;"></p>
						</div>
					</div>
					<div class="form-group row">
						<label for="CheckPassword" class="col-sm-3 col-form-label"
							style="padding-right: 0px;"><b>비밀번호 확인:</b></label>
						<div class="col-sm-9">
							<input type="password" class="form-control" id="CheckPassword"
								placeholder="Password" disabled="disabled">
							<p class="pwdError" Class="alert text-danger a-font"
								style="display: none; padding: 5px 0px 0px 0px; margin-bottom: 0px; font-size: 0.8vw;"></p>
						</div>
					</div>
				</form>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" id="changeConfirm"
					disabled="disabled">확인</button>
			</div>

		</div>
	</div>
</div>