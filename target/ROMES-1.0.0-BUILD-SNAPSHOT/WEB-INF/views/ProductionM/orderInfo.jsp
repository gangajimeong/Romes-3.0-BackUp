<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">수주 / 작업 지시</span>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters" style="padding: 1px 1px 1px 1px;">
			<div class="col-md-8 col-sm-12 col-lg-8">
				<div class="row no-gutters" style="padding: 1px 1px 1px 1px;">
					<div class="col-md-12 col-sm-12 col-lg-12">
						<div class="card mt-0"
							style="padding: 1px 1px 1px 1px; border-radius: 0px 0px 0px 0px; height: 100%;">
							<div class="card-header"
								style="font-size: 0.2rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
								<div class="container-fluid">
									<div class="row" style="padding: 0px 0px 0px 0px;">
										<div class="col-md-1 col-sm-1 col-lg-1"
											style="padding: 0px 0px 0px 0px; margin: auto;">
											<span class="badge badge-dark page-titles">수주 목록</span>
										</div>

										<div class="col-md-11 col-sm-11 col-lg-11 "
											style="padding: 0px 0px 0px 0px;">

											<div class="row no-gutters"
												style="justify-content: flex-end;">
												<div class="col-md-2 ml-auto">
													<div class="input-group-append search-append float-right">
														<label class="input-group-text search"
															style="height: 3.183023872679045vh; margin-right: 10px;">
															<input type="file" id="receive_order_excelImport"
															accept=".xlsx" style="display: none;">Excel
															Import
														</label>
													</div>
												</div>
												<div class="">
													<div class="input-group float-right"
														style="flex-wrap: nowrap;">
														<div class="input-group-prepend "
															style="padding: 0px 0px 0px 0px; height: 3.183023872679045vh;">
															<span class="input-group-text Order-group-text"
																style="padding: 0px 5px 0px 5px">날짜</span>
														</div>
														<div style="display: flex;">
															<input type="date" class="form-control"
																style="height: 3.183023872679045vh;"
																id="orderinfoPreDate"> <span
																style="color: black; padding: 0px 10px 0px 10px; font-size: 16px; font-weight: bold;">
																~ </span> <input type="date" class="form-control"
																style="height: 3.183023872679045vh;"
																id="orderinfoLastDate">

															<div class="input-group-append search-append"
																id="dateSearch">
																<span class="input-group-text search"><i
																	class='bx bx-search bx-sm'></i></span>
															</div>
														</div>
													</div>
												</div>
												<div class="col-md-3 ml-2">
													<div class="input-group  float-right ">
														<div class="input-group-prepend">
															<select class="custom-select search-select"
																id="OrderInfoSearch"></select>
														</div>

														<input type="search" class="form-control search-control"
															id="OrderInfoInputSearch">
														<div class="input-group-append search-append">
															<span class="input-group-text search"><i
																class='bx bx-search bx-sm'></i></span>
														</div>
													</div>
												</div>

											</div>



										</div>
									</div>
								</div>
							</div>
							<div class="card-body" style="padding: 0px 0px 0px 0px;">
								<div>
									<table
										class="table table-condensed table-striped table-bordered mb-0 table-hover custom-table"
										id="OrderHistoryTable">
										<thead id="OrderInfoColName">
											<tr>
												<!-- <th style="width: 9.97%;">LOTNO</th> -->
												<th style="width: 35%;">수주명</th>
												<th style="width: 15%;">수주일</th>
												<th style="width: 15%;">청구지</th>
												<th style="width: 10%;">작성자</th>
												<th style="width: 15%;">작성일</th>
												<th style="width: 10%;">작업 지시</th>

											</tr>
										</thead>
										<tbody id="OrderHistoryContents"
											style="height: 30.31746031746032vh;">
											<c:forEach var="info" items="${lists}" varStatus="state">
												<tr>
													<td style="display: none;">${info.id}</td>
													<td style="width: 35%;">${info.title}</td>
													<td style="width: 15%;" id="orderDate">${info.receivedOrderDate}</td>
													<td style="width: 15%;">${info.company}</td>
													<td style="width: 10%;">${info.writter}</td>
													<td style="width: 15%;">${info.writtenDate}</td>
													<td style="width: 10%; vertical-align: middle;"><button
															class="btn btn-outline-info workOrder"
															style="padding-top: 0px; padding-bottom: 0px;">작업
															지시 작성</button></td>
													<td style="display: none;">${info.brand}</td>
													<td style="display: none;">${info.bId}</td>

												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<!-- 어드민 접근 권한 필요  -->
					<%-- <s:authorize access="hasRole('ROLE_ADMIN')"> --%>
					<div class="col-md-12 col-sm-12 col-lg-12 mt-2">
						<div class="card "
							style="padding: 1px 1px 1px 1px; border-radius: 0px 0px 0px 0px; height: 100%;">
							<div class="card-header"
								style="font-size: 0.2rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
								<div class="container-fluid">
									<div class="row" style="padding: 0px 0px 0px 0px;">
										<div class="col-md-2 col-sm-2 col-lg-2"
											style="padding: 0px 0px 0px 0px; margin: auto;">
											<span class="badge badge-dark page-titles">작업지시목록</span>
										</div>
										<div class="col-md-10 col-sm-10 col-lg-10 "
											style="padding: 0px 0px 0px 0px;">
											<div class="row no-gutters"
												style="justify-content: flex-end;">
												<%-- <div class="">
													<div class="input-group float-right" style="flex-wrap:nowrap;">
														<div class="input-group-prepend "
															style="padding: 0px 0px 0px 0px; height: 3.183023872679045vh;">
															<span class="input-group-text Order-group-text"
																style="padding: 0px 5px 0px 5px">날짜</span>
														</div>
														<div style="display: flex;">
															<input type="date" class="form-control"
																	style="height: 3.183023872679045vh;" id="orderinfoPreDate"> 
															<span style="color: black; padding: 0px 10px 0px 10px"> ~ </span> 
															<input type="date" class="form-control"
																	style="height: 3.183023872679045vh;" id="orderinfoLastDate"> 
															<div class="input-group-append search-append">
																<span class="input-group-text search"><i
																	class='bx bx-search bx-sm'></i></span>
															</div>
														</div>
													</div>
												</div> --%>
												<div class="col-md-5 ml-5">
													<div class="input-group  float-right ">
														<div class="input-group-prepend">
															<select class="custom-select search-select"
																id="OrderInfoSearch2"></select>
														</div>

														<input type="search" class="form-control search-control"
															id="OrderInfoInputSearch2">
														<div class="input-group-append search-append">
															<span class="input-group-text search"><i
																class='bx bx-search bx-sm'></i></span>
														</div>
													</div>
												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="card-body" style="padding: 0px 0px 0px 0px;">
								<div>
									<table
										class="table table-condensed table-striped table-bordered mb-0 table-hover custom-table"
										id="OrderHistoryTable">
										<thead id="OrderInfo2ColName">
											<tr>
												<!-- <th style="width: 9.97%;">LOTNO</th> -->
												<th style="width: 43%;">매장명</th>
												<th style="width: 20%;">품목</th>
												<th style="width: 15%;">규격</th>
												<th style="width: 15%;">수량</th>
												<th style="width: 3%; vertical-align: middle;"><i
													class='bx bxs-trash'></i></th>

											</tr>
										</thead>
										<tbody id="OrderHistoryContents2"
											style="height: 40.31746031746032vh;">
											<c:forEach var="info" items="${datas}" varStatus="state">
												<tr>
													<td style="display: none;">${info.id}</td>
													<td style="width: 43%;">${info.locate}</td>
													<td style="width: 20%;">${info.product}</td>
													<td style="width: 15%;">${info.size}</td>
													<td style="width: 15%;">${info.count}</td>
													<!-- <td
														style="width: 5%; vertical-align: middle; padding: 0px;"><button
															class="btn btn-light btn-outline-secondary draftBtn"
															style="padding-top: 0px; padding-bottom: 0px;">
															<i class='bx bx-edit-alt' style="font-size: 18px;"></i>
														</button></td> -->
													<td style="display: none;">${info.url}</td>
													<td style="width: 3%; vertical-align: middle;"><button
															class="btn btn-light btn-outline-secondary p-0 delete_work_order">
															<i class='bx bxs-trash' style="font-size: 18px;"></i>
														</button></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<%-- </s:authorize> --%>
				</div>
			</div>
			<div class="card regi-card col-md-4">
				<div class="row no-gutters" style="padding: 1px 1px 1px 1px;">
					<div class="col-md-12 col-sm-12 col-lg-12">
						<div class="card-header register">
							<ul class="nav nav-tabs card-header-tabs regi-ul">
								<li class="nav-item"><a
									class="nav-link OrderCreate active " data-toggle="tab"
									href="#Order-register"><b>등록</b></a></li>
								<li class="nav-item"><a class="nav-link OrderUpdate"
									data-toggle="tab" href="#Order-update"><b>수정 / 삭제</b></a></li>
							</ul>
						</div>

						<div class=" card-body tab-content"
							style="padding: 0.188rem 0.188rem 0.188rem 0.188rem;">
							<div class="tab-pane fade show active" id="Order-register">


								<div class="card row mt-0"
									style="margin: 0px 0px 0px 0px; padding: 0px 0px 0px 0px;">
									<div class="card-header"
										style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
										<b class="card-title">수주 기본 정보 입력</b>
									</div>

									<div class="card-body " style="padding: 5px;">
										<form id="createOrderInfoForm" class="p-0 ">
											<div class="row no-gutters ">

												<s:csrfInput />
												<div class="input-group Order-group col-md-12 mt-1  ">
													<div class="input-group-prepend "
														style="padding: 0px 0px 0px 0px; height: 3.183023872679045vh;">
														<span class="input-group-text Order-group-text"
															style="padding: 0px 0.1875rem 0px 0.1875rem;">수주명</span>
													</div>
													<input type="text" class="form-control"
														style="height: 3.183023872679045vh;" name="title"
														placeholder="Title">
												</div>
												<div class="input-group Order-group col-md-12 mt-2 ">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<span class="input-group-text Order-group-text">수주일</span>
													</div>
													<input type="date" class="form-control receivedOrderDate"
														style="height: 3.183023872679045vh;">
												</div>
												<div class="input-group Order-group col-md-12 mt-2 mb-1">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text Order-group-text">청구지</label>
													</div>
													<select class="custom-select" name="orderCompany.id"
														id="order_select_company">
														<option value=0 selected>청구지를 선택하세요</option>
														<c:forEach var="company" items="${companys}">
															<option value="${company.id}">${company.name}</option>
														</c:forEach>
													</select>
												</div>
												<div class="input-group Order-group col-md-12 mt-2">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text Order-group-text">브랜드</label>
													</div>
													<select class="custom-select" name="brand.id"
														id="order_select_brand" disabled>
														<option value=0>브랜드를 선택하세요</option>
													</select>
												</div>
											</div>
										</form>
									</div>
									<div class="card-footer" style="padding: 2px 2px 2px 2px;">
										<div class="button-group p-0">
											<button type="button" class="btn btn-block suju-button"
												style="padding: 0 1 0 1;" id="createOrderInfo">등록</button>
										</div>
									</div>
								</div>
							</div>
							<!--update FieldSet  -->
							<div class="tab-pane fade" id="Order-Update">
								<div class="card mt-0"
									style="margin: 0px 0px 0px 0px; padding: 0px 0px 0px 0px;">
									<div class="card-header "
										style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
										<div class="row no-gutters ">
											<div class="col-md-5 col-sm-5 col-lg-5 ">
												<b class="card-title">수주 기본 정보 수정/삭제</b>
											</div>
											<div class="col-md-7 col-sm-7 col-lg-7 ">
												<div class="float-right">
													<button type="button"
														class="btn btn-secondary border border-light"
														style="padding-top: 2px; padding-bottom: 2px;"
														id="deleteOrderInfo">
														<b class="card-title">수주 정보 삭제</b>
													</button>
												</div>
											</div>
										</div>

									</div>
									<div class="card-body" style="padding: 5px;">
										<form id="updateOrderInfoForm" class="p-0">
											<div class="row no-gutters">
												<s:csrfInput />
												<input type="hidden" class="info-u" name="id">
												<div class="input-group Order-group col-md-12 mt-1">
													<div class="input-group-prepend "
														style="padding: 0px 0px 0px 0px; height: 3.183023872679045vh;">
														<span class="input-group-text Order-group-text"
															style="padding: 0px 0.1875rem 0px 0.1875rem;">수주명</span>
													</div>
													<input type="text" class="form-control info-u"
														style="height: 3.183023872679045vh;" name="title"
														placeholder="Title">
												</div>
												<div class="input-group Order-group col-md-12 mt-2 ">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<span class="input-group-text Order-group-text">수주일</span>
													</div>
													<input type="date"
														class="form-control receivedOrderDate info-u"
														style="height: 3.183023872679045vh;">
												</div>
												<div class="input-group Order-group col-md-12 mt-2 mb-1">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text Order-group-text">청구지</label>
													</div>
													<select class="custom-select info-u" name="orderCompany.id"
														id="order_update_company">
														<option value=0 selected>청구지를 선택하세요</option>
														<c:forEach var="company" items="${companys}">
															<option value="${company.id}">${company.name}</option>
														</c:forEach>
													</select>
												</div>
												<div class="input-group Order-group col-md-12 mt-2">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text Order-group-text">브랜드</label>
													</div>
													<select class="custom-select info-u" name="brand.id"
														id="order_update_brand">
															<option value=0>브랜드를 선택하세요</option>
													</select>
												</div>
											</div>

										</form>
									</div>
									<div class="footer mt-1" style="padding: 1px 3px 1px 3px;">
										<div class="button-group">
											<button type="button" class="btn btn-block btn-secondary"
												id="updateOrderInfo" style="padding: 0 1 0 1;">수정</button>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12 col-lg-12 mt-2 draftTab"
						style="padding: 0.188rem 0.188rem 0.188rem 0.188rem;">
						<div class="card-header register">
							<ul class="nav nav-tabs card-header-tabs regi-ul">
								<li class="nav-item"><a
									class="nav-link DesignCreate active " data-toggle="tab"
									href="#Design-register"><b>시안등록</b></a></li>
							</ul>
						</div>

						<div class=" card-body regi-body tab-content">
							<div class="tab-pane fade show active" id="Design-register">
								<div class="card border">
									<div class="card-header border-secondary img-header">
										<img class="card-img-top" src="img/no_image.png"
											alt="Card image cap" id="noImage"
											style="max-height: 5rem; width: 5rem; margin-top: 3rem;">
										<img class="card-img-top" src="" alt="Card image cap"
											id="sampleImage"
											style="width: 100%; height: 100%; object-fit: cover; display: none;">
									</div>
									<form
										action="DesignRegist?${_csrf.parameterName}=${_csrf.token}"
										method="post" enctype="multipart/form-data">
										<input type="hidden" class="Design-c" name="inputId" value="">
										<div class="card-body regi-input-body">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text regi-group-text">매장명</span>
												</div>
												<input type="text" class="form-control Design-c regi"
													name="BrandName" placeholder="매장명" disabled>
											</div>
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text regi-group-text">품목</span>
												</div>
												<input type="text" class="form-control Design-c regi"
													name="product" disabled>
											</div>
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text regi-group-text">업로드</span>
												</div>
												<div class="custom-file">
													<input type="file"
														class="custom-file-input file-regi-input Design-c"
														id="sample-regi" accept="image/*" name="img"> <label
														class="custom-file-label file-regi-input sample-select-name"
														for="sample-regi">파일 선택</label>
												</div>
											</div>
										</div>
										<div class="card-footer regi-group">
											<button class="btn regi-btn mt-1 mb-1 " type="submit">등록</button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="modal fade modal-right modal-center" id="myModal"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true" style="font-size: 10px;">
		<div class="modal-dialog modal-fullsize modal-center " role="document">
			<div class="modal-content modal-fullsize" style="background: #5E7DC0">
				<div class="card" style="background: #5E7DC0; border-radius: 0px;">
					<div class="card-header"
						style="font-size: 1.5rem; padding: 5px 5px 5px 5px;">
						<b class="card-title">작업지시서 작성</b>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="card-body" style="padding: 0 0 0 0; background: white;">
						<div class="card">
							<div class="card-body p-0">
								<div class="row no-gutters mt-2 ">
									<div class="col-md-12 col-sm-12 col-lg-12 mt-2">
										<div class="row no-gutters border-bottom"
											style="padding: 3px 3px 3px 0px;">
											<div class="col-md-8 col-sm-8 col-lg-8 p-0">
												<div class="border rounded mt-auto">
													<input type="hidden" id="orderInfoIdForWorkOrder">
													<table class="table table-borderless  table-light mb-0 p-0">
														<tbody id="OrderDataTbody">
															<tr>
																<td style="display: none;" id="orderInfoID">-</td>
																<td width="10%"><b>수주명:</b></td>
																<td width="16.66666%" class="border-right"
																	id="orderName">-</td>
																<td width="10%"><b>청구지:</b></td>
																<td width="16.66666%" class="border-right"
																	id="orderCompany">-</td>
																<td width="10%"><b>작성자:</b></td>
																<td width="16.66666%" class="border-right" id="writter">-</td>
															</tr>
															<tr class="border-top">
																<td width="10%"><b>작성일:</b></td>
																<td width="16.66666%" class="border-right"><input
																	type="date" style="text-align: center;"
																	id="writterDate"></td>
																<td width="10%"><b>출고일:</b></td>
																<td width="16.66666%" class="border-right"><input
																	type="date" style="text-align: center;" id="shipDate"></td>
																<td width="10%"><b>시공일:</b></td>
																<td width="16.66666%"><input type="date"
																	style="text-align: center;" id="constructDate"></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>

											<div class="col-md-3 col-sm-3 col-lg-3 ml-auto">
												<div class="border rounded mt-auto">
													<table class="table table-borderless mb-0 p-0 table-light">
														<tbody>
															<tr class="border-bottom"
																style="background: #5E7DC0; color: white">
																<td class=" border-right">영업부</td>
																<td class="border-right">디자인팀</td>
																<td class=" border-right">출력실</td>
																<td class="">제작실</td>
															</tr>
															<tr style="background: white;">
																<td class="border-right">-</td>
																<td class="border-right">-</td>
																<td class=" border-right">-</td>
																<td>-</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-12 col-sm-12 col-lg-12 mt-2">
										<div class="float-right " style="padding: 5px;">
											<button type="button"
												class="btn suju-button btn-outline-secondary" id="addRowBtn"
												style="padding-top: 2px; padding-bottom: 2px; border-radius: 0px;">
												<b class="card-title">항목 추가</b>
											</button>
										</div>
									</div>
									<div class="col-md-12 col-sm-12 col-lg-12 mt-2">
										<table class="table  table-bordered mb-0  custom-table">
											<thead>
												<tr>
													<!-- <th style="width: 9.97%;">LOTNO</th> -->
													<th style="width: 10%;">브랜드</th>
													<th style="width: 10%;">매장명</th>
													<th style="width: 14%;">품목</th>
													<th style="width: 3%;">코팅</th>
													<th style="width: 3%;">배면</th>
													<th style="width: 12%;">규격</th>
													<th style="width: 5%;">수량</th>
													<th style="width: 19%;">가공방법</th>
													<th style="width: 7%;">컷</th>
													<th style="width: 6%;">위치</th>
													<th style="display: none;">샘플</th>
													<th style="width: 3%;">시공</th>
													<th style="width: 2.5%; vertical-align: middle;"><i
														class='bx bxs-trash'></i></th>
												</tr>
											</thead>
											<tbody style="height: 40.31746031746032vh;"
												id="workOrderTbody">

												<tr class="ori-row">

													<td style="width: 10%; vertical-align: middle;"><input
														type="text" style="text-align: center;"
														id="orderInfoBrandname" class="form-control" disabled></td>
													<td style="width: 10%;"><select class="custom-select"
														style="height: 2.4rem;" name="brand.id" id="second_select">
															<option value=0>매장을 선택하세요</option>
													</select></td>

													<td style="width: 14%;"><input type="text"
														class="form-control" placeholder="품목"
														style="text-align: center;"></td>
													<td style="width: 3%;"><input type="checkbox"
														class="form-control coating"></td>
													<td style="width: 3%;"><input type="checkbox"
														class="form-control rearView"></td>
													<td style="width: 12%;"><input type="text"
														class="form-control" placeholder="규격"
														style="text-align: center;"></td>
													<td style="width: 5%;"><input type="number"
														class="form-control p-0" placeholder="수량"
														style="text-align: center;"></td>
													<td style="width: 19%;"><input type="text"
														class="form-control" placeholder="가공 방법"
														style="text-align: center;"></td>
													<td style="width: 7%;"><input type="text"
														class="form-control" placeholder="컷"
														style="text-align: center;"></td>
													<td style="width: 6%;"><input type="text"
														class="form-control" placeholder="위치"
														style="text-align: center;"></td>
													<!-- <td
														style="width: 3%; padding: 0px; vertical-align: middle;"><button
															class="btn p-0">
															<i class='bx bxs-image-add p-0' style="font-size: 28px;"></i>
														</button></td> -->
													<td
														style="display: none; padding: 0px; vertical-align: middle;">
														<button class="btn p-0 imageButton">
															<i class='bx bxs-image-add p-0' style="font-size: 28px;">
																<input type="file" accpet="image/*" id="SampleImage"
																required multiple style="display: none;">
															</i>
														</button>
													</td>
													<td style="width: 3%;"><input type="checkbox"
														class="form-control construction"></td>
													<td style="width: 2%; vertical-align: middle;"><button
															class="btn btn-secondary p-0 deleteWorkOrder">
															<i class='bx bxs-trash'
																style="color: white; font-size: 28px;"></i>
														</button></td>
												</tr>

											</tbody>
										</table>
									</div>

								</div>
							</div>
						</div>
					</div>
					<div class="card-body row no-gutters p-0"
						style="padding: 0.5rem 0.5rem 0.5rem 0.5rem; background: white">
						<table class="table table-bordered">

							<tbody>
								<tr style="background-color: #DDE8FA">
									<td rowspan="4" style="vertical-align: middle"><b>특이
											사항</b></td>
									<td class="p0"><b>매장규격추가 유/무</b></td>
									<td class="p0"><b>재출력사유</b></td>
									<td class="p0"><b>출고예정지 및 특이사항</b></td>
								</tr>
								<tr>
									<td><input type="text" class="form-control"
										style="text-align: center;"></td>
									<td rowspan="2"><input type="text" class="form-control"
										placeholder="재출력 사유" style="text-align: center; height: 10vh"></td>
									<td rowspan="2"><input type="text" class="form-control "
										placeholder="출고예정지 및 특이사항"
										style="text-align: center; height: 10vh"></td>

								</tr>
								<tr>
									<td><div class="btn-group btn-block" role="group"
											aria-label="Basic example">
											<button type="button" class="btn btn-outline-secondary w-100">오픈</button>
											<button type="button" class="btn btn-outline-secondary w-100">리뉴얼</button>
											<button type="button" class="btn btn-outline-secondary w-100">추가</button>
										</div></td>


								</tr>
							</tbody>
						</table>

					</div>
					<div class="card-footer" style="background: white">
						<button type="button"
							class="btn btn-secondary btn-block rgWorkOrderBtn">작업
							지시서 등록</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>