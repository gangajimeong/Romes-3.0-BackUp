<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">


	<div class="card-header card-headerMain  border-secondary">
		<div class="row no-gutters">
			<span class="badge badge-dark page-title">출하 실적</span>
		</div>
	</div>
	
	<div class="input-group Order-group col-md-12 mt-2 mb-2">
		<select class="custom-select col-md-3" style=""
			id="ShippingResultInfoSearch">
			<option value=2>제품명</option>
			<option value=3>처리자</option>
			<option value=5>청구지</option>
			<option value=6>출고지</option>
		</select> 
		<input type="text" class="form-control search-control"
			style="margin-right: 2%" id="ShippingResultSearch">
		
		<input type="date" class="form-control" style="height: 3.183023872679045vh;" id="ShippingResult-PreDate">
		<span style="color: black;padding: 0px 10px 0px 10px" > ~  </span> 
		<input type="date" class="form-control" style="height: 3.183023872679045vh;" id="ShippingResult-LastDate">
		<span class="input-group-text search"><i
			class='bx bx-search bx-sm'></i></span>
	</div>

	<div class="card-body  border-secondary mainPanel">
					<table class="table table-striped table-bordered mb-0 table-hover">
						<thead id="ShippingResultColName">
							<tr>
								<th width="5%">No.</th>
								<th width="15%">제품명</th>
								<th width="15%">처리자</th>
								<th width="15%">수량</th>
								<th width="15%">청구지</th>
								<th width="15%">출고지</th>
								<th width="15%">출하일</th>
							</tr>
						</thead>
					</table>
					<div style="overflow: auto; height: 70vh">
						<table
							class="table table-condensed table-striped table-bordered mb-0 table-hover">
							<tbody id="ShippingResultContents">
							<c:forEach items="${SR}" var = "item" varStatus="status">
								<tr>
									<td style="display: none;">${item.id}</td>
									<td width="5%">${status.count}</td>
									<td width="15%">${item.product}</td>
									<td width="15%">${item.user}</td>
									<td width="15%">${item.count}</td>
									<td width="15%">${item.brand}</td>
									<td width="15%">${item.branch}</td>
									<td width="15%">${item.date}</td>
								</tr>
								</c:forEach>
								
<%--								<c:forEach items="${data}" var="item" varStatus="status">

									<tr style="background-color: white">
										<td style="display: none">${item.id}</td>
										<td width="7%">${status.index+1}</td>
										<td width="8%">${item.user}</td>
										<td width="8%">${item.company}</td>
										<td width="8%">${item.releaseDay}</td>
										<td width="8%">${item.direction}</td>
										<td width="8%">${item.workingLine}</td>
										<td width="8%">${item.product}</td>
										<td width="7%">${item.isCoating}</td>
										<td width="7%">${item.isBack}</td>
										<td width="8%">${item.size}</td>
										<td width="8%">${item.ordercount}/${item.makecount}</td>
										<td width="8%">${item.remark}</td>
										<td width="7%"><c:if test="${empty item.history}">
												<button type="button" data-toggle="modal"
													data-target="#shipping-modal" class="btn btn-secondary"
													data-id="${item.id}">수정</button>
											</c:if></td>
									</tr>

									<tr class="dataContent mb-0">
										<td colspan="12" style="padding: 0px">
											<div class="collapse hiddenRow border border-info"
												id="${item.id}">
												<table class="table table-striped mb-0">
													<thead>
														<tr class="info">
															<th>일시</th>
															<th>수량</th>
															<th>특이 사항</th>
															<th></th>
														</tr>
													</thead>

													<tbody>
														<c:forEach items="${item.history}" var="h" varStatus="s">
															<tr>
																<td style="display: none">${h.id}</td>
																<td>${h.generateDay}</td>
																<td>${h.ordercount}/${h.makecount}</td>
																<td>${h.remark}</td>
																<td><c:if test="${s.last}">
																		<button type="button" data-toggle="modal"
																			data-target="#shipping-modal"
																			class="btn btn-secondary" data-id="${h.id}">수정</button>
																	</c:if></td>
															</tr>
														</c:forEach>



													</tbody>
												</table>

											</div>
										</td>
									</tr>
								</c:forEach>
--%>
							</tbody>
						</table>
					</div>
				</div>
			</div>
	<%-- <div class="modal fade" id="shipping-modal" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg"
			role="document">
			<div class="modal-content card">
				<div class="card-header" style="background-color: #5E7DC0">
					<b class="card-title">변경 이력 등록</b>
				</div>
				<div class="card-body" style="padding: 1px 1px 1px 1px;">
					<div class="input-group Order-group col-md-12 mt-2">
						<div class="input-group-prepend"
							style="padding: 0 0 0 0; height: 3.183023872679045vh;">
							<label class="input-group-text Order-group-text">작업 재지시</label>
						</div>
						<select class="custom-select" name="company.id"
							id="shipping-remake">
							<option value=true>예</option>
							<option value=false>아니오</option>
						</select>
					</div>
					<div class="card-body">
						<div class="input-group-prepend"
							style="height: 3.183023872679045vh;">
							<label class="input-group-text Order-group-text">특이 사항</label>
						</div>
						<textarea id="shipping-remark" class="form-control"
							style="height: 30vh;"></textarea>
					</div>
				</div>

				<div class="card-footer row no-gutters "
					style="padding: 0.5rem 0.5rem 0.5rem 0.5rem;">
					<div class="button-group col-md-12">
						<button type="button" class="btn btn-block suju-button"
							id="shipping-submit" style="padding: 0 1 0 1;">
							등록
							<div class="spinner-border" role="status" style="display: none;"
								id="shipping-loading">
								<span class="sr-only">Loading...</span>
							</div>
						</button>
						<button type="button" class="btn btn-block suju-button"
							id="production-cancel" style="padding: 0 1 0 1;">취소</button>
					</div>
				</div>
			</div>
		</div>
	</div> --%>

