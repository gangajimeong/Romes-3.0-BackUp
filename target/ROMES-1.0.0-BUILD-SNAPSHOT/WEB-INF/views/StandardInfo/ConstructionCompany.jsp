<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">
	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">시공사 관리</span>
		<div class="input-group search-group float-right  ">
			<label class="input-group-text search"
				style="height: 3.183023872679045vh; margin-right: 10px;">
				<input type="file" id="constructionExcel" accept=".xlsx" style="display: none;">Excel Import
			</label>
			<div class="input-group-prepend">
				<select class="custom-select search-select"
					id="ConstructionCompanyInfoSearch"></select>
			</div>

			<input type="text" class="form-control search-control"
				id="ConstructionCompanySearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9">
				<table id="ConstructionCompanyTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="ConstructionCompanyColName">
						<tr>

							<th style="width: 2%; display: none;" class="constructionChkRow">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										name="constructionChkAll">
								</div>
							</th>
							<th style="width: 3%;">No</th>
							<th style="width: 20%;">시공사명</th>
							<th style="width: 15%;">담당자명</th>
							<th style="width: 15%;">Tel</th>
							<th style="width: 15%;">비고</th>
							<th style="width: 15%;">등록일자</th>
							
						</tr>
					</thead>
					<tbody id="constructionBody">
						<c:forEach var="company" items="${CompanyList}" varStatus="status">
							<tr>
								<td class="constructionChkRow" style="display: none; width: 2%;">
									<div class="form-check">
										<input class="form-check-input position-static"
											type="checkbox" name="constructionChk" value="${company.id}">
									</div>
								</td>
								<td style="width: 3%;">${status.count}</td>
								<td style="display: none;">${company.id}</td>
								<td style="width: 20%;">${company.name}</td>
								<td style="width: 15%;">${company.user}</td>
								<td style="width: 15%;">${company.phone}</td>
								<td style="width: 15%;"></td>
								<td style="width: 15%;">${company.date}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link constructionCreate active " data-toggle="tab"
							href="#construction-register">등록</a></li>
						<li class="nav-item"><a class="nav-link constructionUpdate"
							data-toggle="tab" href="#construction-update">수정</a></li>
						<li class="nav-item"><a class="nav-link constructionDelete"
							data-toggle="tab" href="#construction-delete">삭제</a></li>
						<li class="nav-item"><a class="nav-link constructionEmployee"
						data-toggle="tab" href="#construction-Employee-select">소속 직원</a></li>
					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active " id="construction-register">
						<form id="createConstruction" method="post">
							<div class="card border border-secondary mt-3">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">시공사명</span>
										</div>
										<input type="text" class="form-control construction-c regi"
											name="companyName" placeholder="시공사명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">비고</span>
										</div>
										<input type="text" class="form-control construction-c regi"
											name="remark" placeholder="비고">
									</div>
								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1" id = "create-construction-btn"type="button">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade" id="construction-update">
						<form id="update-construction-list" method="post">
							<div class="card border border-secondary mt-3">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<input type="hidden" name="id" class="construction-u">

									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">시공사명</span>
										</div>
										<input type="text" class="form-control construction-u regi"
											name="companyName" placeholder="시공사명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">비고</span>
										</div>
										<input type="text" class="form-control construction-u regi"
											name="remark" placeholder="비고">
									</div>
								</div>

								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1"
										id="update-construction-btn" type="button">수정</button>
								</div>
							</div>
						</form>
					</div>
					<div class="tab-pane fade" id="construction-delete">
						<div class=" card border border-secondary mb-2 ">
							<table class="table table-bordered mb-0 regi-delete">
								<thead>
									<tr>

										<th style="width: 30%;">시공사명</th>
										<th style=""><i
											class='bx bxs-trash btn con-delete-checkBox'></i></th>
									</tr>
								</thead>
								<tbody class="construction-delete-list" style="font-size: 5px;">

								</tbody>
							</table>
						</div>
					</div>
					<div class="tab-pane fade" id="construction-Employee-select">
						<div class=" card border border-secondary mb-2 ">
							<table class="table table-bordered mb-0 regi-delete sub-table">
								<thead>
									<tr>
										<th style="width: 30%">직원명</th>
										<th style="width: 30%">직책</th>
										<th style="width: 40%">번호</th>
									</tr>
								</thead>
								<tbody class="construction-employee-list">
										
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

