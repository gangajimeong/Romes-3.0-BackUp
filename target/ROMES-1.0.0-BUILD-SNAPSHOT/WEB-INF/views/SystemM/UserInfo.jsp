<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card userinfo border-secondary mr-0" id="main">
	<div class="card-header card-headerMain">
		<div class="row no-gutters" style="padding: 0px 0px 0px 0px;">
			<div class="" style="padding: 0px 0px 0px 0px;">
				<span class="badge badge-dark page-titles">사용자 정보관리</span>
			</div>

		</div>
	</div>
	<div class="card-body " style="padding: 0px 0px 0px 0px;">
		<div class="row no-gutters">
			<div class="col-md-12 col-sm-12 col-lg-12"
				style="background-color: #f2f2f2;">
				<div class="col-md-6 col-lg-6  mx-auto">
					<div class="card  border-white mt-3 mb-3">
						<div class="card-body  border-dark">
							<button class="btn btn-secondary float-right" id="modify-info">정보
								수정</button>
							<button class="btn btn-secondary float-right"
								id="modify-password">비밀번호 변경</button>
							<div class="form-group"
								style="margin-bottom: 1.9893899204244032vh">
								<label><b>아이디</b></label> <input class="form-control Join-input"
									value="${info.loginId}" disabled />
							</div>
							<form id="modify-info-form" class="p-0 ">
								<div class="form-group"
									style="margin-bottom: 1.9893899204244032vh">
									<label><b>현재 비밀번호</b></label> <input type="password" disabled
										class="form-control Join-input" id="current-password"
										name="currentPassword" placeholder="현재 비밀번호" />
									<p id="pwdError" Class="alert text-danger a-font"
										style="display: none; padding: 5px 0px 0px 0px"></p>
								</div>
								<div class="form-group"
									style="margin-bottom: 1.9893899204244032vh">
									<label><b>새 비밀번호</b></label> <input type="password" disabled
										class="form-control Join-input" id="new-password"
										name="newPassword" placeholder="새 비밀번호" />
									<p id="pwdError" Class="alert text-danger a-font"
										style="display: none; padding: 5px 0px 0px 0px"></p>
								</div>
								<div class="form-group"
									style="margin-bottom: 1.9893899204244032vh">
									<label><b>비밀번호 확인</b></label> <input type="password" disabled
										class="form-control Join-input" id="check-password"
										placeholder="비밀번호 확인" />
								</div>
								<div class="form-group"
									style="margin-bottom: 1.9893899204244032vh">
									<label><b>직책</b></label> <input name="position"
										value="${info.position }" class="form-control Join-input"
										id="info-position" placeholder="직책" disabled />
								</div>
								<div class="form-group"
									style="margin-bottom: 1.9893899204244032vh">
									<label><b>번호</b></label> <input name="tel"
										value="${info.phone }" class="form-control Join-input"
										id="info-phone" placeholder="010-0000-0000" disabled />
								</div>
								<div class="form-group"
									style="margin-bottom: 1.9893899204244032vh">
									<label><b>부서</b></label> <select name="division.id" disabled
										class="form-control Join-input" id="info-division"
										style="padding: 0;">
										<c:forEach var="item" items="${division}" varStatus="i">
											<option value="${item.id}">${item.division}</option>
										</c:forEach>
									</select>
								</div>

								<div class="form-group"
									style="margin-bottom: 1.9893899204244032vh">
									<label><b>이메일</b></label> <input name="email" id="info-email"
										value="${info.email }" class="form-control Join-input"
										placeholder="이메일" disabled />
								</div>
							</form>
							<div class="form-action">
								<button type="button" class="btn btn-secondary btn-lg btn-block"
									disabled id="modify-confirm">수정</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>