<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<style type="text/css">
table th, table td {
	width: 100px;
	padding: 5px;
	border: 1px solid #ccc;
}

.selected {
	background-color: #666;
	color: #fff;
}
</style>
<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">결제 정보 관리</span>
		<div class="input-group search-group float-right  ">
			<div class="input-group-prepend">
				<select class="custom-select search-select"
					id="ApprovalInfoInfoSearch"></select>
			</div>

			<input type="search" class="form-control search-control"
				id="ApprovalInfoInputSearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters" style="padding: 1px 1px 1px 1px;">
			<div class="card col-md-9 mt-0 table-panel"
				style="padding: 1px 1px 1px 1px; border-radius: 0px 0px 0px 0px;">
				<div class="card-header"
					style="font-size: 0.2rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
					<b class="card-title">결제 정보 목록</b>
				</div>
				<div class="card-body" style="padding: 0px 0px 0px 0px;">
					<div>
						<table
							class="table table-condensed table-striped table-bordered mb-0 table-hover "
							id="ApprovalInfoTable">
							<thead id="ApprovalInfoColName">
								<tr>
									<th width="5%">No.</th>
									<th width="10%">결제명</th>
									<th width="65%">결제 순서</th>
									<th width="10%">등록 날짜</th>
									<th width="10%">변경 날짜</th>
								</tr>
							</thead>
						</table>
					</div>
					<div
						style="height: 80.31746031746032vh; overflow: auto; padding: 0 0 0 0;">
						<table
							class="table table-condensed table-striped table-bordered mb-0 table-hover">
							<tbody id="ApprovalInfoContents">
								<c:forEach var="item" items="${orderInfos}" varStatus="status">
									<tr>
										<td style="display: none;">${item.id}</td>
										<td width="5%">${status.index+1}</td>
										<td width="10%">${item.title}</td>
										<td width="65%"><c:forEach var="user"
												items="${item.users}" varStatus="status">
												<c:choose>
													<c:when test="${fn:length(item.users)!=status.index+1}">
														<b>${user}</b>
														<i class='bx bxs-right-arrow'></i>
													</c:when>
													<c:otherwise>
														<b>${user}</b>
													</c:otherwise>
												</c:choose>
											</c:forEach></td>
										<td width="10%">${item.createDate}</td>
										<td width="10%">${item.updateDate}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<div class="card regi-card col-md-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link ApprovalInfoCreate active " data-toggle="tab"
							href="#ApprovalInfo-register"><b>등록</b></a></li>
						<li class="nav-item"><a class="nav-link ApprovalInfoUpdate"
							data-toggle="tab" href="#ApprovalInfo-update-delete"><b>수정/삭제</b></a></li>
					</ul>
				</div>

				<div class=" card-body tab-content"
					style="padding: 0px 0.188rem 0.188rem 0.188rem;">
					<div class="tab-pane fade show active" id="ApprovalInfo-register">
						<div class="container-fluid" style="padding: 0px 0px 0px 0px;">
							<div class="row">

								<div class="col-md-12 mt-2">
									<div class="input-group Order-group ">
										<div class="input-group-prepend"
											style="padding: 0px 0px 0px 0px; height: 3.183023872679045vh;">
											<span class="input-group-text Order-group-text"
												style="padding: 0px 0.1875rem 0px 0.1875rem;">결제명</span>
										</div>
										<input type="text" class="form-control sujuTitle"
											style="height: 3.183023872679045vh;" name="title"
											placeholder="Title">
									</div>



									<div class="card mt-2">
										<div class="card-header"
											style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
											<b class="card-title">결제 라인 순서 테이블</b>
										</div>
										<div class="card-body" style="padding: 0 0 0 0;">
											<table class="table mb-0">
												<thead class="p0" style="font-size: 0.5rem;">
													<tr>
														<th width="10%">No</th>
														<th width="30%">이름</th>
														<th width="30%">직책</th>
														<th width="30%">부서</th>
													</tr>
												</thead>
											</table>
											<div style="height: 14.854111405835544vh; overflow: auto;">
												<table
													class="table table-condensed table-striped table-bordered mb-0 table-hover">
													<tbody class="p0" id="ApprovalLists"
														style="font-size: 0.5rem;">
														<c:forEach begin="0" end="9" step="1" varStatus="status">
															<tr>
																<td width="10%">${status.index+1}</td>
																<td width="30%">t${status.index+1}</td>
																<td width="30%">-</td>
																<td width="30%">-</td>

															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>

									</div>
								</div>
								<div class="col-md-12">
									<div class="card mt-2 p-0">
										<div class="card-header"
											style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
											<b class="card-title">결제자 선택</b>
										</div>
										<div class="card-body p-0">
											<div class=" row no-gutters"
												style="padding: 0.2rem 0.2rem 0.2rem 0.2rem;">
												<div class="input-group col-md-12 col-sm-10 mt-2">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text Order-group-text"
															style="padding: 0 2 0 2;">부서</label>
													</div>
													<select class="custom-select selectDivisionForApproval"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<option value="0">없음</option>
													</select>
												</div>
												<div class="input-group col-md-12 col-sm-12 mt-2">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text" style="padding: 0 2 0 2;">직책</label>
													</div>
													<select class="custom-select selectPositionForApproval"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<option value="0">없음</option>
													</select>
												</div>
												<div class="input-group col-md-12 col-sm-12 mt-2">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text Order-group-text"
															style="padding: 0 2 0 2;">결제자</label>
													</div>
													<select class="custom-select selectUsersForApproval"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<option value="0">없음</option>
													</select>
												</div>
												<div class="col-md-12 col-sm-12 mt-2"
													style="padding: 0px 0px 0px 0px;">

													<button class="btn btn-info btn-block" type="button"
														style="height: 3.183023872679045vh; padding: 0px 0px 0px 0px;">
														<b style="padding: 0px 0px 0px 0px;">추가</b> <i
															class='bx bx-plus-medical'
															style="padding: 0px 0px 0px 0px;"></i>
													</button>

												</div>


											</div>

										</div>
										<div class="card-footer mt-2 p-1">
											<button type="button" class="btn btn-secondary btn-block">등록</button>
										</div>
									</div>


								</div>
							</div>

						</div>
					</div>

					<!--update FieldSet  -->
					<div class="tab-pane fade" id="ApprovalInfo-update-delete">
						<div class="container-fluid" style="padding: 0px 0px 0px 0px;">
							<div class="row">

								<div class="col-md-12 mt-2">
									<div class="input-group Order-group ">
										<div class="input-group-prepend"
											style="padding: 0px 0px 0px 0px; height: 3.183023872679045vh;">
											<span class="input-group-text Order-group-text"
												style="padding: 0px 0.1875rem 0px 0.1875rem;">결제명</span>
										</div>
										<input type="text" class="form-control sujuTitle"
											style="height: 3.183023872679045vh;" name="title"
											placeholder="Title">
									</div>



									<div class="card mt-2">
										<div class="card-header"
											style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
											<b class="card-title">결제 라인 순서 테이블</b>
										</div>
										<div class="card-body" style="padding: 0 0 0 0;">
											<table class="table mb-0">
												<thead class="p0" style="font-size: 0.5rem;">
													<tr>
														<th width="10%">No</th>
														<th width="30%">이름</th>
														<th width="30%">직책</th>
														<th width="30%">부서</th>
													</tr>
												</thead>
											</table>
											<div style="height: 14.854111405835544vh; overflow: auto;">
												<table
													class="table table-condensed table-striped table-bordered mb-0 table-hover">
													<tbody class="p0" id="ApprovalLists"
														style="font-size: 0.5rem;">
														<c:forEach begin="0" end="9" step="1" varStatus="status">
															<tr>
																<td width="10%">${status.index+1}</td>
																<td width="30%">-</td>
																<td width="30%">-</td>
																<td width="30%">-</td>

															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>

									</div>
								</div>
								<div class="col-md-12">
									<div class="card mt-2 p-0">
										<div class="card-header"
											style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
											<b class="card-title">결제자 선택</b>
										</div>
										<div class="card-body p-0">
											<div class=" row no-gutters"
												style="padding: 0.2rem 0.2rem 0.2rem 0.2rem;">
												<div class="input-group col-md-12 col-sm-10 mt-2">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text Order-group-text"
															style="padding: 0 2 0 2;">부서</label>
													</div>
													<select class="custom-select orderedProduct"
														id="productOption"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<option value="0" selected="selected">없음</option>
													</select>
												</div>
												<div class="input-group col-md-12 col-sm-12 mt-2">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text Order-group-text"
															style="padding: 0 2 0 2;">직책</label>
													</div>
													<select class="custom-select orderedProduct"
														id="productOption"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<option value="0" selected="selected">없음</option>
													</select>
												</div>
												<div class="input-group col-md-12 col-sm-12 mt-2">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text Order-group-text"
															style="padding: 0 2 0 2;">결제자</label>
													</div>
													<select class="custom-select orderedProduct"
														id="productOption"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<option value="0" selected="selected">없음</option>
													</select>
												</div>
												<div class="col-md-12 col-sm-12 mt-2"
													style="padding: 0px 0px 0px 0px;">

													<button class="btn btn-info btn-block" type="button"
														style="height: 3.183023872679045vh; padding: 0px 0px 0px 0px;">
														<b style="padding: 0px 0px 0px 0px;">추가</b> <i
															class='bx bx-plus-medical'
															style="padding: 0px 0px 0px 0px;"></i>
													</button>

												</div>


											</div>

										</div>

										<div class="card-footer mt-2 p-1">
											<div class="btn-group btn-block" role="group">
												<button type="button" class="btn btn-outline-secondary "
													style="width: 50%;">수정</button>
												<button type="button" class="btn btn-outline-secondary "
													style="width: 50%;">삭제</button>
											</div>
										</div>
									</div>


								</div>
							</div>

						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>

