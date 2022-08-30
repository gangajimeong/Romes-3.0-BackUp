<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">생산계획</span>
		<div class="input-group search-group float-right  ">
			<div class="input-group-prepend">
				<select class="custom-select search-select"
					id="ProductionPlanInfoSearch"></select>
			</div>

			<input type="search" class="form-control search-control"
				id="ProductionPlanInputSearch">
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
					<b class="card-title">생산 계획 목록</b>
				</div>
				<div class="card-body" style="padding: 0px 0px 0px 0px;">
					<span style="display: none" id="production-data">${data}</span>
					<div>
						<table
							class="table table-condensed table-striped table-bordered mb-0 table-hover "
							id="ProductionPlansTable">
							<thead id="ProductionPlanColName">
								<tr>
									<th width="5%">No.</th>
									<th width="20%">수주명</th>
									<th width="20%">청구지</th>
									<th width="10%" style="display: none;">출고지</th>
									<th width="15%">매장명</th>
									<th width="15%">품목</th>
									<th width="10%">긴급</th>
									<th width="5%"><b><i class='bx bx-search-alt-2'
											style="font-size: 20px;"></i></b></th>

								</tr>
							</thead>
						</table>
					</div>
					<div
						style="height: 80.31746031746032vh; overflow: auto; padding: 0 0 0 0;">
						<table
							class="table table-condensed table-striped table-bordered mb-0 table-hover">
							<tbody id="ProductionPlanContents">
								<c:forEach items="${plans}" var="item" varStatus="status">
									<tr>
										<td style="display: none">${item.id}</td>
										<td width="5%">${status.index+1}</td>
										<td width="20%">${item.title}</td>
										<td width="20%">${item.company}</td>
										<td style="display: none">
											<%-- ${item.address} --%>-
										</td>
										<td width="15%">${item.branch}</td>
										<td width="15%">${item.product}</td>
										<td style="display: none">${item.user}</td>
										<td style="display: none">${item.workingLine}</td>
										<td style="display: none">${item.coating}</td>
										<td style="display: none">${item.backprint}</td>
										<td style="display: none">${item.size}</td>
										<td style="display: none">${item.plancount}</td>
										<td style="display: none">${item.startTime}</td>
										<td style="display: none">${item.url}</td>
										<td style="display: none">${item.remark}</td>

										<td width="10%"><c:choose>
												<c:when test="${item.emergency eq false}">-</c:when>
												<c:when test="${item.emergency eq true}">
													<i class='bx bxs-bell-ring' style="color: red;"></i>
												</c:when>
											</c:choose></td>
										<td width="5%"><button type="button"
												class="btn btn-info detailForProductionPlanBtn">
												<i class='bx bx-search-alt-2'></i>
											</button></td>
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
							class="nav-link ProductionPlanCreate active " data-toggle="tab"
							href="#ProductionPlan-register"><b>시안확인</b></a></li>
						<!-- 						<li class="nav-item"><a class="nav-link ProductionPlanUpdate" -->
						<!-- 							data-toggle="tab" href="#ProductionPlan-update-delete"><b>수정 -->
						<!-- 									/ 삭제</b></a></li> -->
					</ul>
				</div>

				<div class=" card-body tab-content"
					style="padding: 0px 0.188rem 0.188rem 0.188rem;">
					<div class="tab-pane fade show active" id="ProductionPlan-register">
						<!-- 						<div class="row no-gutters mt-2 mb-0" -->
						<!-- 							style="padding: 1px 1px 1px 1px;"> -->
						<!-- 							<div class="col-md-12"> -->
						<!-- 								<ol class="stepBar step2 " id="ProductionPlan-step"> -->
						<!-- 									<li class="step active"><b>수주 정보 선택</b></li> -->
						<!-- 									<li class="step "><b>생산지시 및 출하 정보 입력</b></li> -->

						<!-- 								</ol> -->
						<!-- 							</div> -->
						<!-- 						</div> -->
						<!-- progressbar -->
						<div class="msform" id="productionPlan-Field">
							<fieldset>

								<div class=" row no-gutters mt-1"
									style="padding: 1px 1px 1px 1px;">

									<div class="card col-md-12" style="display: none;">
										<div class="card-header"
											style="font-size: 0.7rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
											<b class="card-title">수주 정보 선택</b>
										</div>
										<div class="card-body " style="padding: 20px 5px 20px 5px;">
											<div class="input-group  ">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text ">수주</label>
												</div>
												<select class="custom-select" name="company.id"
													id="production-recievedInfo">
													<option value=-1>수주 선택</option>

													<c:forEach items="${data}" var="item">
														<option value="${item.id}">${item.title}</option>
													</c:forEach>
												</select>
											</div>
											<div class="input-group mt-2 ">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text ">품목</label>
												</div>
												<select class="custom-select" name="company.id"
													id="production-receivedProduct">
													<option value=-1>품목 선택</option>
												</select>
											</div>
											<div class="input-group  mt-2">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text ">청구지</label>
												</div>
												<input type="text" class="form-control" disabled="disabled"
													style="height: 3.183023872679045vh;"
													id="production-orderCompany">
											</div>
											<div class="input-group mt-2">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text ">규격</label>
												</div>
												<input type="text" class="form-control" disabled="disabled"
													style="height: 3.183023872679045vh;"
													id="production-productSpec">
											</div>
										</div>
									</div>

									<div class="col-md-12">
										<div class="card " style="height: 40vh;">
											<div class="card-header"
												style="font-size: 0.7rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
												<b class="card-title">확정 시안</b>
											</div>
											<div class="card-body" style="padding: 5px 5px 5px 5px;">
												<div id="production-Indicators" class="carousel slide"
													data-bs-interval="false">
													<ol class="carousel-indicators" id="production-Indi">
														<li data-target="#constructionIndicators"
															data-slide-to="0" class="production-template-indi active"></li>
													</ol>
													<div class="carousel-inner" id="production-indi-Items"
														style="height: 33vh;">
														<div class="carousel-item active production-indi-item">
															<img class="d-block w-100 h-100" src="img/RomesBack.png"
																alt="First slide" id="Plan_image"> <img
																class="d-block w-100 h-100" src="" alt="First slide"
																id="Plan_no_image" style="display: none;">
															<div class="carousel-caption d-none d-lg-block"></div>
														</div>
													</div>
													<%-- <a class="carousel-control-prev rgba-black-strong"
														href="#constructionIndicators" role="button"
														data-slide="prev"> <span
														class="carousel-control-prev-icon" aria-hidden="true"></span>
														<span class="sr-only">Previous</span>
													</a> <a class="carousel-control-next"
														href="#constructionIndicators" role="button"
														data-slide="next"> <span
														class="carousel-control-next-icon" aria-hidden="true"></span>
														<span class="sr-only">Next</span>
													</a> --%>
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-12 mt-4" style="display: none;">
										<button class="btn btn-secondary btn-block mt-1" type="button"
											id="select-suju">
											<b>선택 완료</b>
										</button>
									</div>
								</div>

							</fieldset>
							<fieldset style="display: none;">
								<div class=" row no-gutters" style="padding: 1px 1px 1px 1px;">
									<div class="card col-md-12 ">
										<div class="card-header"
											style="font-size: 0.2rem; padding: 0.31746031746031744vh 0.145032632342277vw 0.31746031746031744vh 0.145032632342277vw; background-color: #5E7DC0">
											<b class="card-title">작업 지시 정보</b>
										</div>
										<div class="card-body" style="padding: 1px 1px 1px 1px;">
											<div class="input-group col-md-12 mt-2">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text ">긴급</label>
												</div>
												<select class="custom-select" id="production-isEmergency">
													<option value="false" selected="selected">아니오</option>
													<option value="true">예</option>
												</select>
											</div>
											<div class="input-group col-md-12 mt-2">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text ">출력기</label>
												</div>
												<%-- <select class="custom-select" id="production-workingLine">
													<option value=-1>출력기 선택</option>
													<c:forEach items="${line}" var="item">
														<option value="${item.id}">${item.line}</option>
													</c:forEach>
												</select> --%>
											</div>
											<div class="input-group  col-md-12 mt-2">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text ">수량</label>
												</div>
												<input type="number" class="form-control"
													style="height: 3.183023872679045vh;" id="production-count">
											</div>

											<div class="input-group  col-md-12 mt-2">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text ">코팅</label>
												</div>
												<select class="custom-select" id="production-isCoating">
													<option value="true" selected="selected">사용</option>
													<option value="false" selected="selected">미사용</option>
												</select>
											</div>
											<div class="input-group  col-md-12 mt-2">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text ">배면출력</label>
												</div>
												<select class="custom-select" id="production-isBackPring">
													<option value="true" selected="selected">예</option>
													<option value="false" selected="selected">아니오</option>
												</select>
											</div>
											<div class="input-group  col-md-12 mt-2">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text ">시작시간</label>
												</div>
												<input type="datetime-local" class="form-control"
													style="height: 3.183023872679045vh;"
													id="production-startTime">
											</div>
											<div class="input-group  col-md-12 mt-2 mb-2">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text ">완료시간</label>
												</div>
												<input type="datetime-local" class="form-control"
													style="height: 3.183023872679045vh;"
													id="production-endTime">
											</div>

										</div>
									</div>
									<div class="card col-md-12 mt-3">
										<div class="card-header"
											style="font-size: 0.2rem; padding: 0.31746031746031744vh 0.145032632342277vw 0.31746031746031744vh 0.145032632342277vw; background-color: #5E7DC0">
											<b class="card-title">출하 정보 입력</b>
										</div>
										<div class="card-body" style="padding: 1px 1px 1px 1px;">
											<div class="input-group  col-md-12 mt-2">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text -text">출고날짜</label>
												</div>
												<input type="date" class="form-control"
													style="height: 3.183023872679045vh;"
													id="production-releaseDay">
											</div>
											<div class="input-group  col-md-12 mt-2">
												<div class="input-group-prepend"
													style="padding: 0 0 0 0; height: 3.183023872679045vh;">
													<label class="input-group-text -text">출고지</label>
												</div>
												<select class="custom-select" id="production-direction">
													<option value=-1>출고지 선택</option>
												</select>
											</div>
											<div class="col-md-12 mt-2 mb-2">
												<div class="card ">
													<div class="card-header"
														style="font-size: 0.2rem; padding: 0.31746031746031744vh 0.145032632342277vw 0.31746031746031744vh 0.145032632342277vw; background-color: #5E7DC0">
														<b class="card-title">특이 사항</b>
													</div>
													<div class="card-body" style="padding: 3px 3px 3px 3px;">
														<textarea class="form-control" id="production-remark"
															style="height: 17vh; font-size: 10px;"></textarea>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-12 mt-3" style="padding: 0px 0px 0px 0px;">
									<div class="btn-group btn-block" role="group">
										<button type="button"
											class="btn btn-secondary previousProductionPlan"
											style="width: 100%;">
											<b>이전</b>
										</button>
										<button type="button" class="btn btn-primary"
											id="production-submit" style="width: 100%;">
											<b>등록</b>
										</button>
									</div>
								</div>
							</fieldset>
						</div>
					</div>
					<!--update FieldSet  -->
					<!-- 					<div class="tab-pane fade" id="ProductionPlan-update-delete"> -->
					<!-- 						progressbar -->
					<!-- 						<div class="row no-gutters mt-2 mb-0" -->
					<!-- 							style="padding: 1px 1px 1px 1px;"> -->
					<!-- 							<div class="col-md-12"> -->
					<!-- 								<ol class="stepBar step2 " id="ProductionPlan-update-step"> -->
					<!-- 									<li class="step active"><b>수주 정보 변경</b></li> -->
					<!-- 									<li class="step "><b>생산지시 및 출하 정보 수정</b></li> -->

					<!-- 								</ol> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 						<form id = "productionFormForUpdate"> -->
					<!-- 							<div class="msform" id="productionPlan-update-Field"> -->
					<!-- 								<fieldset> -->

					<!-- 									<div class=" row no-gutters mt-1" -->
					<!-- 										style="padding: 1px 1px 1px 1px;"> -->

					<!-- 										<div class="card col-md-12"> -->
					<!-- 											<div class="card-header" -->
					<!-- 												style="font-size: 0.7rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0"> -->
					<!-- 												<b class="card-title">수주 정보 선택</b> -->
					<!-- 											</div> -->
					<!-- 											<div class="card-body " style="padding: 20px 5px 20px 5px;"> -->
					<%-- 												<s:csrfInput/> --%>
					<!-- 												<input type="hidden" id="ProductionPlanIdForUpdate" -->
					<!-- 													name="productionPlan_id"> -->
					<!-- 												<div class="input-group  "> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text ">수주</label> -->
					<!-- 													</div> -->
					<%-- 													<select class="custom-select Production-update-conrol" --%>
					<%-- 														name="orderInfoId" > --%>
					<!-- 														<option value=-1 >수주 선택</option> -->

					<%-- 														<c:forEach items="${data}" var="item"> --%>
					<%-- 															<option value="${item.id}">${item.title}</option> --%>
					<%-- 														</c:forEach> --%>
					<%-- 													</select> --%>
					<!-- 												</div> -->

					<!-- 												<div class="input-group mt-2 "> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text ">품목</label> -->
					<!-- 													</div> -->
					<%-- 													<select class="custom-select Production-update-conrol" --%>
					<%-- 														style="color: black;" disabled="disabled"> --%>
					<!-- 														<option value=-1>품목 선택</option> -->
					<%-- 													</select> --%>
					<!-- 												</div> -->
					<!-- 												<div class="input-group  mt-2"> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text ">청구지</label> -->
					<!-- 													</div> -->
					<!-- 													<input type="text" -->
					<!-- 														class="form-control Production-update-conrol" -->
					<!-- 														disabled="disabled" style="height: 3.183023872679045vh;"> -->
					<!-- 												</div> -->
					<!-- 												<div class="input-group mt-2"> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text ">규격</label> -->
					<!-- 													</div> -->
					<!-- 													<input type="text" -->
					<!-- 														class="form-control Production-update-conrol" -->
					<!-- 														disabled="disabled" style="height: 3.183023872679045vh;"> -->
					<!-- 												</div> -->
					<!-- 											</div> -->
					<!-- 										</div> -->
					<!-- 										<div class="col-md-12 mt-3"> -->
					<!-- 											<div class="card " style="height: 40vh;"> -->
					<!-- 												<div class="card-header" -->
					<!-- 													style="font-size: 0.7rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0"> -->
					<!-- 													<b class="card-title">확정 시안</b> -->
					<!-- 												</div> -->
					<!-- 												<div class="card-body" style="padding: 5px 5px 5px 5px;"> -->
					<!-- 													<div id="production-Indicators" class="carousel slide" -->
					<!-- 														data-bs-interval="false"> -->
					<!-- 														<ol class="carousel-indicators" -->
					<!-- 															id="production-update-Indi"> -->
					<!-- 															<li data-target="#constructionIndicators" -->
					<!-- 																data-slide-to="0" -->
					<!-- 																class="production-update-template-indi active"></li> -->
					<!-- 														</ol> -->
					<!-- 														<div class="carousel-inner" -->
					<!-- 															id="production-update-indi-Items" style="height: 33vh;"> -->
					<!-- 															<div -->
					<!-- 																class="carousel-item active production-update-indi-item"> -->
					<!-- 																<img class="d-block w-100 h-100" src="img/RomesBack.png" -->
					<!-- 																	alt="First slide"> -->
					<!-- 																<div class="carousel-caption d-none d-lg-block"></div> -->
					<!-- 															</div> -->
					<!-- 														</div> -->
					<!-- 														<a class="carousel-control-prev rgba-black-strong" -->
					<!-- 															href="#constructionIndicators" role="button" -->
					<%-- 															data-slide="prev"> <span --%>
					<%-- 															class="carousel-control-prev-icon" aria-hidden="true"></span> --%>
					<%-- 															<span class="sr-only">Previous</span> --%>
					<!-- 														</a> <a class="carousel-control-next" -->
					<!-- 															href="#constructionIndicators" role="button" -->
					<%-- 															data-slide="next"> <span --%>
					<%-- 															class="carousel-control-next-icon" aria-hidden="true"></span> --%>
					<%-- 															<span class="sr-only">Next</span> --%>
					<!-- 														</a> -->
					<!-- 													</div> -->
					<!-- 												</div> -->
					<!-- 											</div> -->
					<!-- 										</div> -->
					<!-- 										<div class="col-md-12 mt-4"> -->
					<!-- 											<button class="btn btn-secondary btn-block mt-1" -->
					<!-- 												type="button" id="select-update-suju"> -->
					<!-- 												<b>선택 완료</b> -->
					<!-- 											</button> -->
					<!-- 										</div> -->
					<!-- 									</div> -->

					<!-- 								</fieldset> -->
					<!-- 								<fieldset> -->
					<!-- 									<div class=" row no-gutters" style="padding: 1px 1px 1px 1px;"> -->
					<!-- 										<div class="card col-md-12 "> -->
					<!-- 											<div class="card-header" -->
					<!-- 												style="font-size: 0.2rem; padding: 0.31746031746031744vh 0.145032632342277vw 0.31746031746031744vh 0.145032632342277vw; background-color: #5E7DC0"> -->
					<!-- 												<b class="card-title">작업 지시 정보</b> -->
					<!-- 											</div> -->
					<!-- 											<div class="card-body" style="padding: 1px 1px 1px 1px;"> -->
					<!-- 												<div class="input-group col-md-12 mt-2"> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text ">긴급</label> -->
					<!-- 													</div> -->
					<%-- 													<select class="custom-select Production-update-conrol" --%>
					<%-- 														name="isEmergency"> --%>
					<!-- 														<option value="false" selected="selected">아니오</option> -->
					<!-- 														<option value="true">예</option> -->
					<%-- 													</select> --%>
					<!-- 												</div> -->
					<!-- 												<div class="input-group col-md-12 mt-2"> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text ">출력기</label> -->
					<!-- 													</div> -->
					<%-- 													<select class="custom-select Production-update-conrol" --%>
					<%-- 														name="backprint"> --%>
					<!-- 														<option value=-1>출력기 선택</option> -->
					<%-- 														<c:forEach items="${line}" var="item"> --%>
					<%-- 															<option value="${item.id}">${item.line}</option> --%>
					<%-- 														</c:forEach> --%>
					<%-- 													</select> --%>
					<!-- 												</div> -->
					<!-- 												<div class="input-group  col-md-12 mt-2"> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text ">수량</label> -->
					<!-- 													</div> -->
					<!-- 													<input type="number" -->
					<!-- 														class="form-control Production-update-conrol" name="count" -->
					<!-- 														style="height: 3.183023872679045vh;"> -->
					<!-- 												</div> -->

					<!-- 												<div class="input-group  col-md-12 mt-2"> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text ">코팅</label> -->
					<!-- 													</div> -->
					<%-- 													<select class="custom-select Production-update-conrol" --%>
					<%-- 														name="coating"> --%>
					<!-- 														<option value="true">사용</option> -->
					<!-- 														<option value="false">미사용</option> -->
					<%-- 													</select> --%>
					<!-- 												</div> -->
					<!-- 												<div class="input-group  col-md-12 mt-2"> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text ">배면출력</label> -->
					<!-- 													</div> -->
					<%-- 													<select class="custom-select Production-update-conrol" --%>
					<%-- 														name="backprint"> --%>
					<!-- 														<option value="true">예</option> -->
					<!-- 														<option value="false">아니오</option> -->
					<%-- 													</select> --%>
					<!-- 												</div> -->
					<!-- 												<div class="input-group  col-md-12 mt-2"> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text ">시작시간</label> -->
					<!-- 													</div> -->
					<!-- 													<input type="datetime-local" -->
					<!-- 														class="form-control Production-update-conrol" -->
					<!-- 														style="height: 3.183023872679045vh;" name="startTime"> -->
					<!-- 												</div> -->
					<!-- 												<div class="input-group  col-md-12 mt-2 mb-2"> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text ">완료시간</label> -->
					<!-- 													</div> -->
					<!-- 													<input type="datetime-local" -->
					<!-- 														class="form-control Production-update-conrol" -->
					<!-- 														name="endTime" style="height: 3.183023872679045vh;"> -->
					<!-- 												</div> -->

					<!-- 											</div> -->
					<!-- 										</div> -->
					<!-- 										<div class="card col-md-12 mt-3"> -->
					<!-- 											<div class="card-header" -->
					<!-- 												style="font-size: 0.2rem; padding: 0.31746031746031744vh 0.145032632342277vw 0.31746031746031744vh 0.145032632342277vw; background-color: #5E7DC0"> -->
					<!-- 												<b class="card-title">출하 정보 입력</b> -->
					<!-- 											</div> -->
					<!-- 											<div class="card-body" style="padding: 1px 1px 1px 1px;"> -->
					<!-- 												<div class="input-group  col-md-12 mt-2"> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text -text">출고날짜</label> -->
					<!-- 													</div> -->
					<!-- 													<input type="date" -->
					<!-- 														class="form-control Production-update-conrol" -->
					<!-- 														name="releaseDay" style="height: 3.183023872679045vh;" -->
					<!-- 														id="production-releaseDay"> -->
					<!-- 												</div> -->
					<!-- 												<div class="input-group  col-md-12 mt-2"> -->
					<!-- 													<div class="input-group-prepend" -->
					<!-- 														style="padding: 0 0 0 0; height: 3.183023872679045vh;"> -->
					<!-- 														<label class="input-group-text -text">출고지</label> -->
					<!-- 													</div> -->
					<%-- 													<select class="custom-select Production-update-conrol" --%>
					<%-- 														id="production-direction" name="direction"> --%>
					<!-- 														<option value=-1>출고지 선택</option> -->
					<%-- 													</select> --%>
					<!-- 												</div> -->
					<!-- 												<div class="col-md-12 mt-2 mb-2"> -->
					<!-- 													<div class="card "> -->
					<!-- 														<div class="card-header" -->
					<!-- 															style="font-size: 0.2rem; padding: 0.31746031746031744vh 0.145032632342277vw 0.31746031746031744vh 0.145032632342277vw; background-color: #5E7DC0"> -->
					<!-- 															<b class="card-title">특이 사항</b> -->
					<!-- 														</div> -->
					<!-- 														<div class="card-body" style="padding: 3px 3px 3px 3px;"> -->
					<!-- 															<textarea class="form-control Production-update-conrol" -->
					<!-- 																name="remark" style="height: 17vh; font-size: 10px;"></textarea> -->
					<!-- 														</div> -->
					<!-- 													</div> -->
					<!-- 												</div> -->
					<!-- 											</div> -->
					<!-- 										</div> -->
					<!-- 									</div> -->
					<!-- 									<div class="col-md-12 mt-3" style="padding: 0px 0px 0px 0px;"> -->
					<!-- 										<div class="row"> -->
					<!-- 											<div class="col-md-2"> -->
					<!-- 												<button type="button" -->
					<!-- 													class="btn btn-secondary previousProductionPlan"> -->
					<!-- 													<b>이전</b> -->
					<!-- 												</button> -->
					<!-- 											</div> -->
					<!-- 											<div class="btn-group btn-block col-md-10" role="group"> -->
					<!-- 												<button type="button" class="btn btn-secondary " -->
					<!-- 													id="finallyProductionPlanUpdate" style="width: 100%;"> -->
					<!-- 													<b>수정</b> -->
					<!-- 												</button> -->
					<!-- 												<button type="button" class="btn btn-primary" -->
					<!-- 													id="finallyProductionPlanDelete" style="width: 100%;"> -->
					<!-- 													<b>생산 계획 삭제</b> -->
					<!-- 												</button> -->
					<!-- 											</div> -->
					<!-- 										</div> -->

					<!-- 									</div> -->
					<!-- 								</fieldset> -->

					<!-- 							</div> -->
					<!-- 						</form> -->


					<!-- 					</div> -->
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="DetailForProductionPlan" tabindex="-1"
		role="dialog" aria-labelledby="DetailForProductionPlanTitle"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg"
			role="document">
			<div class="modal-content"
				style="background-color: #5E7DC0; padding: 0px 0px 0px 0px">
				<div class="modal-header" style="padding: 5px 20px 5px 20px">
					<b class="modal-title" id="DetailTitle">생산 계획 상세보기</b>
					<button type="button" class="close" data-dismiss="modal"
						style="margin: 5px 0px 0px 0px; padding: 0px 0px 0px 0px"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body"
					style="background-color: white; padding: 5px 5px 5px 5px">
					<div class="row no-gutters">
						<div class="col-md-12">
							<div class="row no-gutters">
								<div class="card col-md-10" style="padding: 5px 5px 5px 5px">
									<div class="card-body" style="padding: 2px 2px 2px 2px;">
										<table class="mb-0 table table-borderless"
											style="padding: 0px 0px 0px 0px; border-color: white; color: black">
											<tr>
												<td style="display: none;" class="selectProductionPlanData"></td>
												<th
													style="padding: 0px 0px 0px 0px; width: 10%; text-align: left;">제품:
												</th>
												<td
													style="padding: 0px 0px 0px 0px; width: 60%; text-align: center;"
													class="selectProductionPlanData">test</td>
												<th
													style="padding: 0px 0px 0px 0px; width: 10%; text-align: left;">담당자:
												</th>
												<td
													style="padding: 0px 0px 0px 0px; width: 20%; text-align: center;"
													class="selectProductionPlanData">-</td>
											</tr>
										</table>
									</div>

								</div>

								<div class="card col-md-1 ml-auto"
									style="padding: 0px 0px 0px 0px">
									<div class="card-body"
										style="padding: 0px 0px 0px 0px; color: black">
										<button type="button" id="emergency-alarm"
											style="border: none; width: 100%; border-radius: 3px; display: flex; flex-direction: column; flex-wrap: nowrap; align-items: center; background-color: inherit;">
											<i class='bx bxs-bell-ring'
												style="color: black; padding: 5px 5px 5px 5px;"
												id="bell-icon"></i> <b id="emergency-alarm-text" style="">-</b>
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row ">
						<div class="col-md-6 mt-2" style="padding-right: 0px;">
							<table class="table table-striped table-bordered mb-0"
								style="font-size: 13px;">
								<tbody>

									<tr>
										<th width="20%">청구지</th>
										<td width="80%" style="vertical-align: middle;"
											class="selectProductionPlanData">-</td>
									</tr>
									<tr>
										<th width="20%">출고지</th>
										<td width="80%" style="vertical-align: middle;"
											class="selectProductionPlanData">-</td>
									</tr>
									<tr>
										<th width="20%">매장명</th>
										<td width="80%" style="vertical-align: middle;"
											class="selectProductionPlanData">-</td>
									</tr>
									<tr>
										<th width="20%">수량</th>
										<td width="80%" style="vertical-align: middle;"
											class="selectProductionPlanData">-</td>
									</tr>

								</tbody>
							</table>

						</div>

						<div class="col-md-6 mt-2">


							<table class="table table-striped table-bordered mb-0"
								style="font-size: 13px;">
								<tbody>
									<tr>
										<s:csrfInput />
										<th width="20%">출력기</th>
										<td width="80%" style="vertical-align: middle;"><select
											class="custom-select selectProductionPlanData"
											id="production-workingLine" style="height: 2rem;">
												<option value=0 selected>출력기 선택</option>
												<c:forEach items="${line}" var="item">
													<option value="${item.id}">${item.line}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th width="20%">코팅</th>
										<td width="80%" style="vertical-align: middle;"
											class="selectProductionPlanData">-</td>
									</tr>
									<tr>
										<th width="20%">배면</th>
										<td width="80%" style="vertical-align: middle;"
											class="selectProductionPlanData">-</td>
									</tr>
									<tr>
										<th width="20%">규격</th>
										<td width="80%" style="vertical-align: middle;"
											class="selectProductionPlanData">-</td>
									</tr>

								</tbody>
							</table>

						</div>
					</div>
					<button class="mt-3 mb-2 productionplanSubmit">생산완료</button>
					<div class="row" style="display: none;">
						<div class="col-md-6 mt-2 " style="padding-right: 0px;">
							<div class="card" style="padding: 5px 5px 5px 5px">
								<div class="card-header"
									style="font-size: 0.7rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
									<b class="card-title">특이 사항</b>
								</div>
								<div class="card-body" style="padding: 0px 0px 0px 0px">
									<div class="form-group mb-0">
										<textarea class="form-control selectProductionPlanData"
											style="border-radius: 0px 0px 0px 0px;"
											id="detailTextAreaForPlan" readonly="readonly"></textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-6 mt-2">
							<div class="card" style="padding: 5px 5px 5px 5px">
								<div class="card-header"
									style="font-size: 0.7rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
									<b class="card-title">예상 시간</b>
								</div>
								<div class="card-body" style="padding: 0px 0px 0px 0px">
									<div class="input-group  mt-1">
										<div class="input-group-prepend"
											style="padding: 0 0 0 0; height: 3.183023872679045vh;">
											<label class="input-group-text -text">시작 시간</label>
										</div>
										<input type="datetime-local"
											class="form-control selectProductionPlanData"
											style="height: 3.183023872679045vh;">
									</div>
									<div class="input-group  mt-2">
										<div class="input-group-prepend"
											style="padding: 0 0 0 0; height: 3.183023872679045vh;">
											<label class="input-group-text -text">종료 시간</label>
										</div>
										<input type="datetime-local"
											class="form-control selectProductionPlanData"
											style="height: 3.183023872679045vh;">
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
