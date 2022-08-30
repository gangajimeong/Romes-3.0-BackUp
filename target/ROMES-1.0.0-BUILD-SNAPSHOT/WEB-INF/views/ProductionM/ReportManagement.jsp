<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="URL" value="${pageContext.request.requestURL}" />
<div class="card  border-secondary mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<div class="row no-gutters">
			<div class="col-12">
				<h2>
					<span class="badge badge-dark mt-2 ml-2">보고서 관리</span>
				</h2>
			</div>

		</div>
	</div>

	<div class="card-body  border-secondary mainPanel">

		<div class="card border-secondary">
			<div class="card-body btnPanel">
				<div class="row">
					<div class="col-10">
						<div class="input-group" style="font-size: 1vw;">
							<div class="input-group-prepend">
								<span class="input-group-text" style="font-size: 1vw;">저장
									위치</span>

							</div>
							<input type="text" class="form-control">
							<div class="input-group-append">
								<button class="btn btn-secondary">변경</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="card mt-1 border-secondary tablePanel">
			<div class="card-body  border-dark " style="padding: 0.5vw;">
				<div class="card">
					<div class="card-header">
						<ul class="nav nav-tabs card-header-tabs" id="bologna-list"
							role="tablist" style="padding: 0vw; font-size: 0.9vw;">
							<li class="nav-item"><a class="nav-link active"
								href="#WorkOrder" role="tab" aria-controls="WorkOrder"
								aria-selected="true">작업지시서</a></li>
							<li class="nav-item"><a class="nav-link"
								href="#ShipmentSchedule" role="tab"
								aria-controls="ShipmentSchedule" aria-selected="false">출하예정목록</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="#jobReport"
								role="tab" aria-controls="jobReport" aria-selected="false">작업일보</a>
							</li>
							<li class="nav-item"><a class="nav-link"
								href="#productionDaily" role="tab"
								aria-controls="productionDaily" aria-selected="true">생산 일보</a></li>
							<li class="nav-item"><a class="nav-link"
								href="#productionPerHour" role="tab"
								aria-controls="productionPerHour" aria-selected="false">시간당
									생산량</a></li>
							<li class="nav-item"><a class="nav-link" href="#leadTime"
								role="tab" aria-controls="leadTime" aria-selected="false">리드타임</a>
							</li>
							<li class="nav-item"><a class="nav-link "
								href="#ProcessDefectRate" role="tab"
								aria-controls="ProcessDefectRate" aria-selected="true">공정
									불량률</a></li>
							<li class="nav-item"><a class="nav-link"
								href="#KPIoverallStatistics" role="tab"
								aria-controls="KPIoverallStatistics" aria-selected="false">KPI
									전체통계</a></li>
							<li class="nav-item"><a class="nav-link"
								href="#productionPerformance" role="tab"
								aria-controls="productionPerformance" aria-selected="false">생산
									실적</a></li>
							<li class="nav-item"><a class="nav-link "
								href="#shipmentPerformance" role="tab"
								aria-controls="shipmentPerformance" aria-selected="true">출하
									실적</a></li>
							<li class="nav-item"><a class="nav-link"
								href="#T&Aperformance" role="tab" aria-controls="T&Aperformance"
								aria-selected="false">근태 실적</a></li>
							<li class="nav-item"><a class="nav-link"
								href="#ShipmentStatus" role="tab" aria-controls="ShipmentStatus"
								aria-selected="false">출고 현황</a></li>
						</ul>
					</div>
					<div class="card-body" style="padding: 5;">
						<h6 class="card-title mt-2 " style="font-size: 1.5vw;">
							<span class="badge badge-dark  ">옵션 설정</span>
						</h6>

						<div class="tab-content">
							<div class="tab-pane active" id="WorkOrder" role="tabpanel">

								<div class="row">
									<div class="col-12">
										<div class="form-group">
											<label for="LineSelect"><b style="color: black;">생산
													라인</b></label> <select class="form-control" id="LineSelect"
												style="font-size: 0.9vw;">
												<option>1</option>
												<option>2</option>
												<option>3</option>
											</select>
										</div>

									</div>
									<div class="col-12">
										<div class="card" style="height: 38vh;">
											<div class="card-header" style="padding: 3;">
												<b style="color: black;">출력 칼럼 선택</b>
											</div>
											<div class="card-body" style="max-height: 20vh;">
												<table class="table table-condensed"
													style="text-align: left; background-color: white; border: none; font-size: 0.9vw; max-height: 10vh;">
													<tbody>
														<tr>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="No"> <label class="custom-control-label"
																		for="No">No.</label>
																</div>
															</td>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="WorkDate"> <label
																		class="custom-control-label" for="WorkDate">작업날짜</label>
																</div>
															</td>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="WorkingLine"> <label
																		class="custom-control-label" for="WorkingLine">생산라인</label>
																</div>
															</td>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="orderInfo"> <label
																		class="custom-control-label" for="orderInfo">수주
																		정보</label>
																</div>
															</td>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="Product"> <label
																		class="custom-control-label" for="Product">제품</label>
																</div>
															</td>
														</tr>
														<tr>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="LotNo"> <label
																		class="custom-control-label" for="LotNo">LotNo</label>
																</div>
															</td>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="Qty"> <label class="custom-control-label"
																		for="Qty">수량</label>
																</div>
															</td>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="OrderDate"> <label
																		class="custom-control-label" for="OrderDate">주문날짜</label>
																</div>
															</td>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="DeadLine"> <label
																		class="custom-control-label" for="DeadLine">완료
																		기한</label>
																</div>
															</td>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="startTime"> <label
																		class="custom-control-label" for="startTime">시작
																		시간</label>
																</div>
															</td>
														</tr>
														<tr>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="duration"> <label
																		class="custom-control-label" for="duration">소요
																		시간</label>
																</div>
															</td>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="finishedTime"> <label
																		class="custom-control-label" for="finishedTime">완료
																		시간</label>
																</div>
															</td>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="downTime"> <label
																		class="custom-control-label" for="downTime">비가동시간</label>
																</div>
															</td>

															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="process"> <label
																		class="custom-control-label" for="process">진행
																		현황</label>
																</div>
															</td>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="worker"> <label
																		class="custom-control-label" for="worker">작업자</label>
																</div>
															</td>

														</tr>
														<tr>
															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="remark"> <label
																		class="custom-control-label" for="remark">비고</label>
																</div>
															</td>

															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="Emergency"> <label
																		class="custom-control-label" for="Emergency">긴급</label>
																</div>
															</td>

															<td>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input"
																		id="All"> <label class="custom-control-label"
																		for="All">*</label>
																</div>
															</td>
															<td></td>
															<td></td>
														</tr>

													</tbody>
												</table>

											</div>

										</div>

									</div>
									<div class="col-12">
										<button class="btn btn-secondary float-right mt-1">보고서
											생성</button>
									</div>
								</div>

							</div>

							<div class="tab-pane" id="ShipmentSchedule" role="tabpanel"
								aria-labelledby="ShipmentSchedule">

								<div class="row">
									<div class="col-12">
										<div class="input-group mb-3">
											<div class="input-group-prepend">
												<label class="input-group-text" for="inputGroupSelect01"><b>생산라인</b></label>
											</div>
											<select class="custom-select" id="inputGroupSelect01">
												<option selected>전체 라인</option>
												<option value="1">One</option>
												<option value="2">Two</option>
												<option value="3">Three</option>
											</select>
										</div>

									</div>
									<div class="col-12">

										<div class="col-12">
											<button class="btn btn-secondary float-right mt-1">보고서
												생성</button>
										</div>

									</div>
								</div>


							</div>

							<div class="tab-pane" id="jobReport" role="tabpanel"
								aria-labelledby="jobReport" style="padding: 5;">
								<div class="card">
									<div class="card-body">
										<div class="row">
											<div class="col-12">
												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<label class="input-group-text" for="inputGroupSelect01"><b
															class="labelFontSize">조회구분:</b></label>
													</div>
													<select class="custom-select" id="inputGroupSelect01">
														<option selected>전체 라인</option>
														<option value="1">One</option>
														<option value="2">Two</option>
														<option value="3">Three</option>
													</select>
												</div>

											</div>
											<div class="col-12">
												<div class="input-group input-daterange  mb-3">
													<div class="input-group-prepend">
														<span class="input-group-text"><b
															class="labelFontSize">날짜:</b></span>
													</div>
													<input type="text" class="form-control datePicker"
														value="2021-00-00">
												</div>
											</div>
											<div class="col-12">
												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<label class="input-group-text" for="inputGroupSelect01"><b
															class="labelFontSize">작업자:</b></label>
													</div>
													<select class="custom-select " id="inputGroupSelect01">
														<option selected>선택</option>
														<option value="1">One</option>
														<option value="2">Two</option>
														<option value="3">Three</option>
													</select>
												</div>

											</div>


											<div class="col-12">
												<button class="btn btn-secondary float-right mt-1">보고서
													생성</button>
											</div>



										</div>
									</div>
								</div>


							</div>
							<div class="tab-pane " id="productionDaily" role="tabpanel">
								<div class="card">
									<div class="card-body">
										<div class="row">
											<div class="col-12">
												<div class="input-group input-daterange  mb-3">
													<div class="input-group-prepend">
														<span class="input-group-text"><b
															class="labelFontSize">대상 일자:</b></span>
													</div>
													<input type="text" class="form-control datePicker"
														value="2021-00-00">
												</div>
											</div>
											<div class="col-12">
												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<label class="input-group-text" for="inputGroupSelect01"><b
															class="labelFontSize">대상 설비:</b></label>
													</div>
													<select class="custom-select" id="inputGroupSelect01">
														<option selected>전체 라인</option>
														<option value="1">One</option>
														<option value="2">Two</option>
														<option value="3">Three</option>
													</select>
												</div>

											</div>

											<div class="col-12">
												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<label class="input-group-text" for="inputGroupSelect01"><b
															class="labelFontSize">특이 사항:</b></label>
													</div>
													<input type="text" class="form-control">
												</div>

											</div>
											<div class="col-12">
												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<label class="input-group-text" for="inputGroupSelect01"><b
															class="labelFontSize">이상 유무:</b></label>
													</div>
													<input type="text" class="form-control">
												</div>

											</div>
											<div class="col-12">
												<button class="btn btn-secondary float-right mt-1">보고서
													생성</button>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="tab-pane" id="ShipmentSchedule" role="tabpanel"
								aria-labelledby="history-tab">
								<p class="card-text">First settled around 1000 BCE and then
									founded as the Etruscan Felsina about 500 BCE, it was occupied
									by the Boii in the 4th century BCE and became a Roman colony
									and municipium with the name of Bononia in 196 BCE.</p>
								<a href="#" class="card-link text-danger">Read more</a>
							</div>

							<div class="tab-pane" id="productionPerHour" role="tabpanel"
								aria-labelledby="deals-tab">
								<p class="card-text">Immerse yourself in the colours, aromas
									and traditions of Emilia-Romagna with a holiday in Bologna, and
									discover the city's rich artistic heritage.</p>
								<a href="#" class="btn btn-danger btn-sm">Get Deals</a>
							</div>
							<div class="tab-pane " id="leadTime" role="tabpanel">
								<p class="card-text">It is the seventh most populous city in
									Italy, at the heart of a metropolitan area of about one million
									people.</p>
								<a href="#" class="card-link text-danger">Read more</a>
							</div>

							<div class="tab-pane" id="ProcessDefectRate" role="tabpanel"
								aria-labelledby="history-tab">
								<p class="card-text">First settled around 1000 BCE and then
									founded as the Etruscan Felsina about 500 BCE, it was occupied
									by the Boii in the 4th century BCE and became a Roman colony
									and municipium with the name of Bononia in 196 BCE.</p>
								<a href="#" class="card-link text-danger">Read more</a>
							</div>

							<div class="tab-pane" id="KPIoverallStatistics" role="tabpanel"
								aria-labelledby="deals-tab">
								<p class="card-text">Immerse yourself in the colours, aromas
									and traditions of Emilia-Romagna with a holiday in Bologna, and
									discover the city's rich artistic heritage.</p>
								<a href="#" class="btn btn-danger btn-sm">Get Deals</a>
							</div>
							<div class="tab-pane" id="productionPerformance" role="tabpanel"
								aria-labelledby="deals-tab">
								<p class="card-text">Immerse yourself in the colours, aromas
									and traditions of Emilia-Romagna with a holiday in Bologna, and
									discover the city's rich artistic heritage.</p>
								<a href="#" class="btn btn-danger btn-sm">Get Deals</a>
							</div>
							<div class="tab-pane" id="shipmentPerformance" role="tabpanel"
								aria-labelledby="deals-tab">
								<p class="card-text">Immerse yourself in the colours, aromas
									and traditions of Emilia-Romagna with a holiday in Bologna, and
									discover the city's rich artistic heritage.</p>
								<a href="#" class="btn btn-danger btn-sm">Get Deals</a>
							</div>
							<div class="tab-pane" id="T&Aperformance" role="tabpanel"
								aria-labelledby="deals-tab">
								<p class="card-text">Immerse yourself in the colours, aromas
									and traditions of Emilia-Romagna with a holiday in Bologna, and
									discover the city's rich artistic heritage.</p>
								<a href="#" class="btn btn-danger btn-sm">Get Deals</a>
							</div>
							<div class="tab-pane" id="ShipmentStatus" role="tabpanel"
								aria-labelledby="deals-tab">
								<p class="card-text">Immerse yourself in the colours, aromas
									and traditions of Emilia-Romagna with a holiday in Bologna, and
									discover the city's rich artistic heritage.</p>
								<a href="#" class="btn btn-danger btn-sm">Get Deals</a>
							</div>

						</div>
					</div>


				</div>
			</div>
		</div>
	</div>
</div>
