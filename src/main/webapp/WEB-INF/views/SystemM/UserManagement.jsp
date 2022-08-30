<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<style>
a {
	color: white;
}

.btn-light {
	background-color: #5E7DC0 !important;
}

</style>
<div class="card  border-secondary mr-0" id="main">
	<div class="card-header card-headerMain">
		<div class="row no-gutters" style="padding: 0px 0px 0px 0px;">
			<div class="col-md-2 col-sm-2 col-lg-2"
				style="padding: 0px 0px 0px 0px; margin: auto;">
				<span class="badge badge-dark page-titles">사용자 관리</span>
			</div>
			<div class="col-md-10 col-sm-10 col-lg-10"
				style="padding: 0px 0px 0px 0px;">
				<div class="input-group search-group float-right  ">
					<div class="input-group-prepend">
						<select class="custom-select search-select " id="UserInfoSearch"></select>
					</div>

					<input type="search" class="form-control search-control"
						id="UserSearch">
					<div class="input-group-append search-append">
						<span class="input-group-text search"><i
							class='bx bx-search bx-sm'></i></span>
					</div>
				</div>
			</div>

		</div>
	</div>
	<div class="card-body " style="padding: 0px 0px 0px 0px;">
		<div class="row no-gutters">
			<div class="col-md-12 col-sm-12 col-lg-12">
				<div>
					<table class="table mb-0 ">
						<thead id="UserManagementColName">
							<tr>
								<th width="10%" class="p-0">ID</th>
								<th width="10%" class="p-0">이름</th>
								<th width="8%" class="p-0">부서</th>
								<th width="5%" class="p-0">직책</th>
								<th width="15%" class="p-0">이메일</th>
								<th width="14%" class="p-0">tel</th>
								<!-- <th width="13%" class="p-0">문서 권한</th> -->
								<th width="13%" class="p-0">Web 권한</th>
								<th width="9%" class="p-0">시공사 권한</th>
								<th width="3%" class="p-0"><i class='bx bx-trash p-0 mt-0'
									style="font-size: 2vh;"></i></th>
							</tr>
						</thead>
					</table>
				</div>
				<div style="height: 642px; overflow: auto;">
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover "
						id="UserTable">
						<tbody id="UserBody">
							<c:forEach var="user" items="${users}" varStatus="status">
								<tr>
									<td style="display: none;">${user.getId()}</td>
									<td width="10%"  class="p-0">${user.getLoginId()}</td>
									<td width="10%"  class="p-0">${user.getName()}</td>
									<td width="8%"  class="p-0">${user.getDivision().getDivision()}</td>
									<td width="5%"  class="p-0">${user.getPosition()}</td>
									<td width="15%"  class="p-0">${user.getEmail()}</td>
									<td width="14%"  class="p-0">${user.getTel()}</td>
									<c:url var="changeRoleUrl" value="/admins/role/${user.getId()}"/>
									<c:choose>

										<c:when test="${ user.hasRole('NOTUSER')}">
											<td width="13%"  class="p-0"><a href="${ changeRoleUrl }/user"
												class="btn <c:choose><c:when test="${ user.hasRole('USER') }">btn-secondary</c:when><c:otherwise>btn-outline-secondary</c:otherwise></c:choose>"
												style="width: 60%;">회원 가입 승인</a></td>
											<td>-</td>
										</c:when>
										<c:otherwise>
											<%-- <td width="13%"><div class="btn-group">
													<a href="${ changeRoleUrl }/D_Writer"
														class="btn <c:choose><c:when test="${ user.hasRole('D_Writer') }">btn-secondary</c:when><c:otherwise>btn-outline-secondary</c:otherwise></c:choose>">작성자</a>
													<a href="${ changeRoleUrl }/D_collaborator"
														class="btn <c:choose><c:when test="${ user.hasRole('D_collaborator') }">btn-secondary</c:when><c:otherwise>btn-outline-secondary</c:otherwise></c:choose>">협의자</a>
													<a href="${ changeRoleUrl }/D_customer"
														class="btn <c:choose><c:when test="${ user.hasRole('D_customer') }">btn-secondary</c:when><c:otherwise>btn-outline-secondary</c:otherwise></c:choose>">클라이언트</a>
												</div></td> --%>
											<td width="13%"  class="p-0"><div class="btn-group">
													<a href="${ changeRoleUrl }/admin"
														class="btn <c:choose><c:when test="${ user.hasRole('ADMIN') }">btn-secondary</c:when><c:otherwise>btn-outline-secondary</c:otherwise></c:choose>">관리자</a>
													<a href="${ changeRoleUrl }/manager"
														class="btn <c:choose><c:when test="${ user.hasRole('MANAGER') }">btn-secondary</c:when><c:otherwise>btn-outline-secondary</c:otherwise></c:choose>">매니저</a>
													<a href="${ changeRoleUrl }/user"
														class="btn <c:choose><c:when test="${ user.hasRole('USER') }">btn-secondary</c:when><c:otherwise>btn-outline-secondary</c:otherwise></c:choose>">사용자</a>
												</div></td>
											<td width="9%"  class="p-0">
												<div class="btn-group">
													<a href="${ changeRoleUrl }/ctt_admin"
														class="btn <c:choose><c:when test="${ user.hasRole('CTT_ADMIN') }">btn-info</c:when><c:otherwise>btn-outline-info</c:otherwise></c:choose>">관리자</a>
													<a href="${ changeRoleUrl }/ctt_user"
														class="btn <c:choose><c:when test="${ user.hasRole('CTT_USER') }">btn-info</c:when><c:otherwise>btn-outline-info</c:otherwise></c:choose>">사용자</a>
												</div>
											</td>
										</c:otherwise>
									</c:choose>
									<td width="3%"  class="p-0" style = "vertical-align: middle;"><a style="color: gray; "
										href="UserManagement/userInfoDelete/${ user.id }"
										class="btn p-0" ><i class='bx bx-trash '
											style="font-size: 2vh; "></i></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

		</div>
	</div>
</div>