<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">

	<div class="card-header card-headerMain border-secondary">
		<span class="badge badge-dark page-title">부서 관리</span>
		<div class="input-group search-group float-right  ">
			<div class="input-group-prepend">
				<select class="custom-select search-select" id="DivisionInfoSearch"></select>
			</div>

			<input type="text" class="form-control search-control"
				id="DivisionSearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9">
				<table id="DivisionTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="DivisionColName">
						<tr>

							<th style="width: 2%; display: none;" class="DivisionChkRow">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										name="DivisionChkAll">
								</div>
							</th>

							<th style="width: 38%">부서명</th>
							<th style="width: 60%">업무</th>

						</tr>
					</thead>
					<tbody id="DivisionBody">
						<c:forEach var="Divisions" items="${Divisions}" varStatus="status">
							<tr class="${Divisions.getId()}">
								<td class="DivisionChkRow" style="display: none;">
									<div class="form-check">
										<input class="form-check-input position-static"
											type="checkbox" name="DivisionChk"
											value="${Divisions.getId()}">
									</div>
								</td>

								<td style="display: none;">${Divisions.getId()}</td>
								<td>${Divisions.getDivision()}</td>
								<%-- <td>${Divisions.getTeam()}</td> --%>
								<td>${Divisions.getWorkInfo()}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link DivisionCreate active " data-toggle="tab"
							href="#Division-register">등록</a></li>
						<li class="nav-item"><a class="nav-link DivisionUpdate"
							data-toggle="tab" href="#Division-update">수정</a></li>
						<li class="nav-item"><a class="nav-link DivisionDelete"
							data-toggle="tab" href="#Division-delete">삭제</a></li>
						<li class="nav-item"><a class="nav-link DivisionEmployee"
							data-toggle="tab" href="#Division-Employee-select">소속 직원</a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="Division-register">
						<form action="createDivision" method="post">
							<div class="card border border-secondary mt-3">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">부서명</span>
										</div>
										<input type="text" class="form-control Division-c regi"
											name="division" placeholder="부서명" id = "division-text">
									</div>
									<%-- 
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">팀명</span>
										</div>
										<input type="text" class="form-control Division-c regi"
											name="team" placeholder="팀명">
									</div>
									--%>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">업무</span>
										</div>
										<input type="text" class="form-control Division-c regi"
											name="WorkInfo" placeholder="업무">
									</div>
								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1 " type="submit">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade" id="Division-update">
						<div class=" card border border-secondary mb-2">
							<table
								class="table table-bordered Division-update-list-table mb-0 table-hover regi-update sub-table">
								<thead>
									<tr>
										<th style="width: 45%">부서명</th>
										<th style="width: 55%">업무</th>
										<%-- 
										<th style="width: 55%">업무</th>
										--%>
									</tr>
								</thead>
								<tbody class="Division-update-list">
									<tr style="display: none;">
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="card border border-secondary mt-3">
							<div class="card-body regi-input-body">

								<s:csrfInput />
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">부서명</span>
									</div>
									<input type="text" class="form-control Division-u regi"
										name="division" placeholder="부서명">
								</div>
								<%-- 
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">팀명m</span>
									</div>
									<input type="text" class="form-control Division-u regi"
										name="team" placeholder="팀명">
								</div>
								--%>
								<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">업무</span>
										</div>
										<input type="text" class="form-control Division-u regi"
											name="workInfo" placeholder="업무">
									</div>
							</div>
							<div class="card-footer">
								<button class="btn regi-btn mt-1 mb-1" id="update-Division-btn"
									type="button">등록</button>
							</div>
						</div>

					</div>
					<div class="tab-pane fade show" id="Division-delete">
						<div class=" card border border-secondary mb-2 ">
							<table class="table table-bordered mb-0 regi-delete sub-table">
								<thead>
									<tr>
										<th style="width: 40%">부서명</th>
										<th style="width: 30%">업무</th>
										<th style="width: 29%">삭제</th>
<!-- 										<th style="width: 1%"><i -->
<!-- 											class='bx bxs-trash btn Div-delete-checkBox'></i></th> -->
									</tr>
								</thead>
								<tbody class="Division-delete-list">
									<tr style="display: none;">
										<td>-</td>
										<td>-</td>
										<td>-</td>
									</tr>

								</tbody>
							</table>
						</div>
					</div>
					<div class="tab-pane fade" id="Division-Employee-select">
						<div class=" card border border-secondary mb-2 ">
							<table class="table table-bordered mb-0 regi-delete sub-table">
								<thead>
									<tr>
										<th style="width: 30%">직원명</th>
										<th style="width: 30%">직책</th>
										<th style="width: 40%">번호</th>
									</tr>
								</thead>
								<tbody class="Division-employee-list">
										
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>