<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">공통 코드</span>
		<div class="input-group search-group float-right  ">
			<div class="input-group-prepend">
				<select class="custom-select search-select"
					id="commonCodeInfoSearch"></select>
			</div>

			<input type="text" class="form-control search-control"
				id="CommonCodeSearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9">
				<table id="commonCodeTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="commonCodeColName">
						<tr>

							<th style="width: 2%; display: none;" class="CommonCodeChkRow">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										name="CommonCodeChkAll">
								</div>
							</th>

							<th style="width: 25%">특성코드</th>
							<th style="width: 25%">분류</th>
							<th style="width: 20%">값(value)</th>
							<th style="width: 25%">칼럼코드</th>
						</tr>
					</thead>
					<tbody id="commonBody">
						<c:forEach var="commonCodes" items="${commonCodes}"
							varStatus="status">
							<tr>
								<td class="CommonCodeChkRow" style="display: none;">
									<div class="form-check">
										<input class="form-check-input position-static"
											type="checkbox" name="CommonCodeChk"
											value="${commonCodes.getId()}">
									</div>
								</td>

								<td style="display: none;">${commonCodes.getId()}</td>
								<td class="CodeId">${commonCodes.getCodeId()}</td>
								<td class="classification">${commonCodes.getClassification()}</td>
								<td>${commonCodes.getValue()}</td>
								<td>${commonCodes.getColumnCode()}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link CommonCodeCreate active " data-toggle="tab"
							href="#CommonCode-register">등록</a></li>
						<li class="nav-item"><a class="nav-link CommonCodeUpdate"
							data-toggle="tab" href="#CommonCode-update">수정</a></li>
						<li class="nav-item"><a class="nav-link CommonCodeDelete"
							data-toggle="tab" href="#CommonCode-delete">삭제</a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="CommonCode-register">
						<form action="createCommonCode" method="post">
							<div class="card border border-secondary mt-3">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">특성코드</span>
										</div>
										<input type="text" class="form-control register-control regi "
											name="CodeId" placeholder="특성 코드" readonly>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">분류</span>
										</div>
										<select
											class="custom-select register-control regi regi-select"
											name="classification">
											<option selected="selected">선택</option>
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">값</span>
										</div>
										<input type="text" class="form-control register-control regi"
											name="value" placeholder="값">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">칼럼코드</span>
										</div>
										<input type="text" class="form-control register-control regi"
											name="columnCode" placeholder="칼럼코드" readonly>
									</div>


								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1" type="submit">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade show" id="CommonCode-update">
						<div class=" card border border-secondary mb-2">
							<table
								class="table table-bordered CommonCode-update-list-table mb-0 table-hover regi-update">
								<thead>
									<tr>
										<th style="width: 25%">특성코드</th>
										<th style="width: 25%">분류</th>
										<th style="width: 20%">값(value)</th>
										<th style="width: 25%">칼럼코드</th>
									</tr>
								</thead>
								<tbody class="CommonCode-update-list">
									<tr style="display: none;">
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<form action="updateCommonCode" method="post">
							<div class="card border border-secondary">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">특성코드</span>
										</div>
										<input type="text" class="form-control register-u regi"
											name="CodeId" placeholder="특성 코드" readonly="readonly">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">분류</span>
										</div>
										<select class="custom-select regi-u-select register-u regi"
											name="classification">
											<option>선택</option>
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">값</span>
										</div>
										<input type="text" class="form-control register-u regi"
											name="value" placeholder="값">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">칼럼코드</span>
										</div>
										<input type="text" class="form-control register-u regi"
											name="columnCode" placeholder="칼럼코드" readonly="readonly">
									</div>

								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1" type="submit">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade show" id="CommonCode-delete">
						<div class=" card border border-secondary mb-2 ">
							<table class="table table-bordered mb-0 regi-delete">
								<thead>
									<tr>
										<th style="width: 25%">특성코드</th>
										<th style="width: 25%">분류</th>
										<th style="width: 20%">값(value)</th>
										<th style="width: 22%">칼럼코드</th>
										<th style="width: 3%"><i
											class='bx bxs-trash btn delete-checkBox'></i></th>
									</tr>
								</thead>
								<tbody class="CommonCode-delete-list">
									<tr style="display: none;">
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
									</tr>

								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>