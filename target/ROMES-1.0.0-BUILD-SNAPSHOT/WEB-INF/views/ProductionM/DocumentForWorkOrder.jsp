<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card" id="main" style="border-bottom: none;">
	<div class="card-header card-headerMain">
		<div class="container-fluid">
			<div class="row" style="padding: 0px 0px 0px 0px;">
				<div class="col-md-2 col-sm-2 col-lg-2"
					style="padding: 0px 0px 0px 0px; margin: auto;">
					<span class="badge badge-dark page-titles">디자인 관리</span>
				</div>
				<div class="col-md-10 col-sm-10 col-lg-10 ml-auto"
					style="padding: 0px 0px 0px 0px;">

					<div class="float-right">
						<button class="btn btn-secondary border border-light"
							id="mymyList">
							<b>협의 요청 문서 목록 보기</b>
						</button>
					</div>

				</div>
			</div>
		</div>
	</div>
	<div class="card-body  mainPanel border border-0">
		<div class="row no-gutters">
			<div class="col-md-9">
				<div class="row">
					<div class="col-md-12">
						<div class="card  mt-0 table-panel rounded-0"
							style="padding: 1px 1px 1px 1px;">
							<div class="card-header"
								style="font-size: 0.2rem; padding: 4px 4px 4px 4px; background-color: #5E7DC0">
								<div class="container-fluid">
									<div class="row" style="padding: 0px 0px 0px 0px;">
										<div class="col-md-2 col-sm-2 col-lg-2"
											style="padding: 0px 0px 0px 0px; margin: auto;">
											<span class="badge badge-dark page-titles">디자인 시안 목록</span>
										</div>
										<div class="col-md-10 col-sm-10 col-lg-10 ml-auto"
											style="padding: 0px 0px 0px 0px;">
											<div class="input-group search-group float-right  ">
												<div class="input-group-prepend">
													<select class="custom-select search-select"
														id="DocumentForWorkOrderSearch"></select>
												</div>

												<input type="search" class="form-control search-control"
													id="DocumentForWorkOrderInputSearch">
												<div class="input-group-append search-append">
													<span class="input-group-text search"><i
														class='bx bx-search bx-sm'></i></span>
												</div>
											</div>
										</div>
									</div>
								</div>

							</div>
							<div class="card-body" style="padding: 0px 0px 0px 0px;">
								<table
									class="table table-striped table-bordered table-hover custom-table mb-0"
									id="DocumentForWorkOrderTable">
									<thead id="DocumentForWorkOrderColName">
										<tr>
											<!-- <th style="width: 9.97%;">LOTNO</th> -->

											<th style="width: 20%;">제목</th>
											<th style="width: 18%;">수주</th>
											<th style="width: 18%;">품목</th>
											<th style="width: 6%;">작성자</th>
											<th style="width: 10%;">등록 날짜</th>
											<th style="width: 10%;">수정 날짜</th>
											<th style="width: 5%;">시안</th>
											<th style="width: 10%;">진행도</th>

										</tr>
									</thead>
									<tbody id="DocumentForWorkOrderContents"
										style="height: 29.31746031746032vh;">
										<c:forEach var="ds" items="${makingList}">
											<tr>
												<td style="display: none;">${ds.id}</td>
												<td style="width: 20%;">${ds.title}</td>
												<td style="width: 18%;">${ds.info_title}</td>
												<td style="width: 18%;">${ds.product}</td>
												<td style="width: 6%;">${ds.writer}</td>
												<td style="width: 10%;">${ds.writtenDate}</td>
												<td style="width: 10%;">${ds.updateDate}</td>
												<td style="width: 5%;"><span hidden="true">${ds.url}</span>
													<button class="btn btn-outline-info previewForDoc">
														<i class='bx bx-file-find'></i>
													</button></td>
												<td style="width: 10%;">${ds.state}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>

						</div>
					</div>
					<div class="col-md-12">
						<div class="card mt-1 table-panel rounded-0"
							style="padding: 1px 1px 1px 1px;">
							<div class="card-header"
								style="font-size: 0.2rem; padding: 4px 4px 4px 4px; background-color: #5E7DC0">
								<div class="container-fluid">
									<div class="row" style="padding: 0px 0px 0px 0px;">
										<div class="col-md-2 col-sm-2 col-lg-2"
											style="padding: 0px 0px 0px 0px; margin: auto;">
											<span class="badge badge-dark page-titles">결제 대기 목록</span>
										</div>
										<div class="col-md-10 col-sm-10 col-lg-10"
											style="padding: 0px 0px 0px 0px;">
											<div class="input-group search-group float-right  ">
												<div class="input-group-prepend">
													<select class="custom-select search-select"
														id="DocumentForWorkOrderSearch2"></select>
												</div>

												<input type="search" class="form-control search-control"
													id="DocumentForWorkOrderInputSearch2">
												<div class="input-group-append search-append">
													<span class="input-group-text search"><i
														class='bx bx-search bx-sm'></i></span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="card-body" style="padding: 0px 0px 0px 0px;">
								<table
									class="table table-striped table-bordered mb-0 table-hover custom-table"
									id="DocumentForWorkOrderTable2">
									<thead id="DocumentForWorkOrder2ColName">
										<tr>
											<!-- <th style="width: 9.97%;">LOTNO</th> -->
											<th style="display: none; width: 5%;">제목</th>
											<th style="width: 20%;">제목</th>
											<th style="width: 20%;">수주</th>
											<th style="width: 20%;">품목</th>
											<th style="width: 20%;">등록 날짜</th>
											<th style="width: 5%;">시안</th>
											<th style="width: 10%;">진행도</th>

										</tr>
									</thead>
									<tbody id=" DocumentForWorkOrder2Contents"
										style="height: 40.01746031746032vh;">
										<c:forEach var="docs" items="${readyDocs}">
											<tr>
												<td style="display: none;">${docs.id}</td>
												<td style="width: 20%;">${docs.title}</td>
												<td style="width: 20%;">${docs.info_title}</td>
												<td style="width: 20%;">${docs.product}</td>
												<td style="width: 20%;">${docs.updateDate}</td>
												<td style="width: 5%;"><span hidden="true">${docs.url}</span>
													<button class="btn btn-outline-info preConFirmDoc">
														<i class='bx bx-file-find'></i>
													</button></td>
												<td style="width: 10%;">${docs.state}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="card regi-card col-md-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link DocumentForWorkOrderCreate active "
							data-toggle="tab" href="#DocumentForWorkOrder-register"><b>문서
									등록</b></a></li>
						<li class="nav-item"><a
							class="nav-link DocumentForWorkOrderUpdate" data-toggle="tab"
							href="#DocumentForWorkOrder-update"><b>문서 수정</b></a></li>
						<li class="nav-item"><a
							class="nav-link DocumentForWorkOrderDelete" data-toggle="tab"
							href="#DocumentForWorkOrder-Delete"><b>문서 삭제</b></a></li>
					</ul>
				</div>

				<div class=" card-body tab-content"
					style="padding: 0.188rem 0.188rem 0.188rem 0.188rem;">
					<div class="tab-pane fade show active"
						id="DocumentForWorkOrder-register">
						<div class="row no-gutters">
							<div class="col-md-12 " style="padding: 0px 0px 0px 0px;">
								<div class="card mt-2"
									style="margin: 0px 0px 0px 0px; padding: 0px 0px 0px 0px;">
									<div class="card-header"
										style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
										<b class="card-title">문서 정보 입력</b>
									</div>
									<div class="card-body" style="padding: 5px 5px 5px 5px;">
										<form id="register-Design">
											<s:csrfInput />
											<div class="row no-gutters">
												<div class="col-md-12">
													<div class="input-group   ">
														<div class="input-group-prepend"
															style="padding: 0px 0px 0px 0px; height: 3.183023872679045vh;">
															<span class="input-group-text "
																style="padding: 0px 0.1875rem 0px 0.1875rem;">문서명</span>
														</div>
														<input type="text" class="form-control "
															style="height: 3.183023872679045vh;" name="title"
															placeholder="Title">
													</div>
												</div>
												<div class="input-group  col-md-12 mt-2">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text ">수주</label>
													</div>
													<select class="custom-select selectOrderInfoForDoc"
														name="orderInfo_id">
														<option value="0" selected="selected">없음</option>
													</select>
												</div>
												<div class="input-group  col-md-12 mt-2">
													<div class="input-group-prepend"
														style="padding: 0 0 0 0; height: 3.183023872679045vh;">
														<label class="input-group-text ">제품</label>
													</div>
													<select
														class="custom-select selectOrderInfoToProductForDoc"
														name="product_id">
														<option value="0" selected="selected">없음</option>
													</select>
												</div>
												<div class="col-md-12">
													<div class="card mt-2 p-0">
														<div class="card-header"
															style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
															<b class="card-title">협의자 선택</b>
														</div>
														<div class="card-body p-0">
															<div class=" row no-gutters"
																style="padding: 0.2rem 0.2rem 0.2rem 0.2rem;">
																<div class="input-group col-md-12 col-sm-10 mt-2">
																	<div class="input-group-prepend"
																		style="padding: 0 0 0 0; height: 3.183023872679045vh;">
																		<label class="input-group-text "
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
																		<label class="input-group-text"
																			style="padding: 0 2 0 2;">직책</label>
																	</div>
																	<select class="custom-select selectPositionForApproval"
																		style="padding: 0 0 0 0; height: 3.183023872679045vh;">
																		<option value="0">없음</option>
																	</select>
																</div>
																<div class="input-group col-md-12 col-sm-12 mt-2">
																	<div class="input-group-prepend"
																		style="padding: 0 0 0 0; height: 3.183023872679045vh;">
																		<label class="input-group-text "
																			style="padding: 0 2 0 2;">협의자</label>
																	</div>
																	<select class="custom-select selectUsersForApproval"
																		style="padding: 0 0 0 0; height: 3.183023872679045vh;"
																		name="user_id">
																		<option value="0">없음</option>
																	</select>
																</div>
															</div>

														</div>

													</div>
												</div>

												<div class=" col-md-12 mt-2">
													<div class="card p-0">
														<div class="card-header"
															style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
															<b class="card-title">시안 등록</b>
														</div>


														<div class="card-body p-0">
															<div class=" row no-gutters"
																style="padding: 0.2rem 0.2rem 0.2rem 0.2rem;">
																<div class=" col-md-12">
																	<button type="button" class="btn btn-info btn-block"
																		id="uploadDesign" style="border-radius: 2px 2px 0 0;">
																		<b style="font-size: 0.5rem">디자인 시안 업로드&nbsp;</b> <i
																			class='bx bx-upload' style="font-size: 0.5rem"></i>
																	</button>
																	<input type="file" accept=".png,.jpg,.jpeg"
																		id="DesignUploadInput" multiple hidden="true">
																	<div class="border border-info mt-0 "
																		style="height: 3.283819628647215vh; overflow: auto; font-size: 0.1rem; text-align: center; padding: 0.2rem 0.2rem 0.2rem 0.2rem;">
																		<span id="designReport"
																			style="color: black; margin: auto"></span>
																	</div>
																</div>

															</div>

														</div>
													</div>

												</div>
												<div class="col-md-12">
													<div class="card mt-2 mb-4">
														<div class="card-header"
															style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
															<b class="card-title">특이 사항</b>
														</div>
														<div class="card-body" style="padding: 2px 2px 2px 2px;">
															<div class="form-group mb-0">
																<textarea class="form-control"
																	id="exampleFormControlTextarea1" rows="10"
																	style="height: 140px; font-size: 0.1rem;" name="remark"></textarea>
															</div>
														</div>

													</div>

												</div>
											</div>
										</form>
									</div>
								</div>
								<div class="card-footer border border-light mt-4"
									style="padding: 0px 0px 0px 0px;">
									<button type="button" class="btn btn-info btn-block"
										id="DesignDocRegi" style="padding: 0 1 0 1;">디자인 시안
										등록</button>

								</div>
							</div>
						</div>


					</div>
					<!--문서 수정  -->
					<div class="tab-pane fade" id="DocumentForWorkOrder-Update">
						<div class="container-fluid" style="padding: 0 0 0 0;">
							<div class="row no-gutters">
								<div class="col-md-12">
									<div class="card mt-2"
										style="margin: 0px 0px 0px 0px; padding: 0px 0px 0px 0px;">
										<div class="card-header"
											style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
											<b class="card-title">문서 정보 수정</b>
										</div>
										<div class="card-body" style="padding: 5px 5px 5px 5px;">
											<form id="updateDocumentForm">
												<div class="row no-gutters">
													<s:csrfInput />
													<input type="hidden" class="updateForDoc" name="id">
													<div class="col-md-12">
														<div class="input-group">
															<div class="input-group-prepend"
																style="padding: 0px 0px 0px 0px; height: 3.183023872679045vh;">
																<span class="input-group-text "
																	style="padding: 0px 0.1875rem 0px 0.1875rem;">문서명</span>
															</div>
															<input type="text" class="form-control updateForDoc"
																style="height: 3.183023872679045vh;" name="title"
																placeholder="Title">
														</div>
													</div>
													<div class="input-group  col-md-12 mt-2">
														<div class="input-group-prepend"
															style="padding: 0 0 0 0; height: 3.183023872679045vh;">
															<label class="input-group-text ">수주</label>
														</div>
														<select
															class="custom-select updateForDoc selectOrderInfoForDoc"
															name="orderInfo_id">
															<option value="0" selected="selected">없음</option>
														</select>
													</div>
													<div class="input-group  col-md-12 mt-2">
														<div class="input-group-prepend"
															style="padding: 0 0 0 0; height: 3.183023872679045vh;">
															<label class="input-group-text ">제품</label>
														</div>
														<select
															class="custom-select updateForDoc selectOrderInfoToProductForDoc"
															name="product_id">
															<option value="0" selected="selected">없음</option>
														</select>
													</div>
													<div class="col-md-12">
														<div class="card mt-2 p-0">
															<div class="card-header"
																style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
																<b class="card-title">협의자 선택</b>
															</div>
															<div class="card-body p-0">
																<div class=" row no-gutters"
																	style="padding: 0.2rem 0.2rem 0.2rem 0.2rem;">
																	<div class="input-group col-md-12 col-sm-10 mt-2">
																		<div class="input-group-prepend"
																			style="padding: 0 0 0 0; height: 3.183023872679045vh;">
																			<label class="input-group-text "
																				style="padding: 0 2 0 2;">부서</label>
																		</div>
																		<select
																			class="custom-select selectDivisionForApproval"
																			style="padding: 0 0 0 0; height: 3.183023872679045vh;">
																			<option value="0">없음</option>
																		</select>
																	</div>
																	<div class="input-group col-md-12 col-sm-12 mt-2">
																		<div class="input-group-prepend"
																			style="padding: 0 0 0 0; height: 3.183023872679045vh;">
																			<label class="input-group-text"
																				style="padding: 0 2 0 2;">직책</label>
																		</div>
																		<select
																			class="custom-select selectPositionForApproval"
																			style="padding: 0 0 0 0; height: 3.183023872679045vh;">
																			<option value="0">없음</option>
																		</select>
																	</div>
																	<div class="input-group col-md-12 col-sm-12 mt-2">
																		<div class="input-group-prepend"
																			style="padding: 0 0 0 0; height: 3.183023872679045vh;">
																			<label class="input-group-text "
																				style="padding: 0 2 0 2;">결제자</label>
																		</div>
																		<select
																			class="custom-select selectUsersForApproval updateForDoc"
																			style="padding: 0 0 0 0; height: 3.183023872679045vh;"
																			name="user_id">
																			<option value="0">없음</option>
																		</select>
																	</div>
																</div>

															</div>

														</div>
													</div>

													<div class=" col-md-12 mb-2">
														<div class="card mt-2 p-0">
															<div class="card-header"
																style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
																<b class="card-title">시안 수정</b>
															</div>


															<div class="card-body p-0">
																<div class=" row no-gutters"
																	style="padding: 0.2rem 0.2rem 0.2rem 0.2rem;">
																	<div class=" col-md-12">
																		<button type="button" class="btn btn-info btn-block"
																			id="uploadDesignForUpdate"
																			style="border-radius: 2px 2px 0 0;">
																			<b style="font-size: 0.5rem">디자인 수정본 업로드&nbsp;</b> <i
																				class='bx bx-upload' style="font-size: 0.5rem"></i>
																		</button>
																		<input type="file" accept=".png,.jpg,.jpeg"
																			id="DesignUploadInputForUpdate" multiple
																			hidden="true">
																		<div class="border border-info mt-0 "
																			style="height: 3.283819628647215vh; overflow: auto; font-size: 0.1rem; text-align: center; padding: 0.2rem 0.2rem 0.2rem 0.2rem;">
																			<span id="designReportForUpdate"
																				style="color: black; margin: auto"></span>
																		</div>
																	</div>
																</div>

															</div>
														</div>

													</div>
													<div class="col-md-12  ">
														<div class="card mt-2">
															<div class="card-header"
																style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
																<b class="card-title">특이 사항</b>
															</div>
															<div class="card-body" style="padding: 2px 2px 2px 2px;">
																<div class="form-group mb-0">
																	<textarea class="form-control updateForDoc" rows="5"
																		name="remark"></textarea>
																</div>
															</div>

														</div>

													</div>

												</div>

											</form>
										</div>
										<div class="card-footer" style="padding: 5px 3px 5px 3px;">
											<div class="button-group">
												<button type="button" class="btn btn-block suju-button"
													id="updateForDocConfirm" style="padding: 0 1 0 1;">수정</button>
											</div>
										</div>
									</div>
									<div class="card-footer border border-light mt-2"
										style="padding: 0px 0px 0px 0px;">

										<div class="button-group">
											<button type="button" class="btn btn-block suju-button"
												id="customer-Confirm-request" style="padding: 0 1 0 1;">고객
												승인 요청</button>
										</div>
										<div class="progress" style="height: 3vh; display: none;"
											id="loading-button">
											<div
												class="progress-bar progress-bar-striped progress-bar-animated"
												role="progressbar" aria-valuenow="100" aria-valuemin="0"
												aria-valuemax="100" style="width: 100%;">
												<b>loading...</b>
											</div>
										</div>
									</div>
								</div>
							</div>

						</div>
					</div>
					<!--문서 삭제  -->
					<div class="tab-pane fade" id="DocumentForWorkOrder-Delete">
						<div class="row no-gutters">
							<div class="col-md-12 " style="padding: 0px 0px 0px 0px;">
								<div class="card mt-2"
									style="margin: 0px 0px 0px 0px; padding: 0px 0px 0px 0px;">
									<div class="card-header"
										style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
										<b class="card-title">문서 삭제 목록</b>
									</div>
									<div class="card-body" style="padding: 0px 0px 0px 0px;">
										<table class="table  table-hover custom-table mb-0"
											style="font-size: 8px;">
											<thead>
												<tr>
													<!-- <th style="width: 9.97%;">LOTNO</th> -->

													<th style="width: 25%;">제목</th>
													<th style="width: 23%;">작성자</th>
													<th style="width: 25%;">등록 날짜</th>
													<th style="width: 25%;">수정 날짜</th>
													<th
														style="width: 2%; padding: 3px; vertical-align: middle;"><i
														class='bx bxs-trash' id="trashForDoc"></i></th>


												</tr>
											</thead>
											<tbody style="height: 29.31746031746032vh;"
												id="deleteForDocument">

											</tbody>
										</table>
									</div>
									<div class="card-footer" style="padding: 5px 3px 5px 3px;">
										<div class="button-group">
											<button type="button" class="btn btn-block suju-button"
												id="deletForDocumentButton" style="padding: 0 1 0 1;">삭제</button>
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
	<div class="modal fade DesignPreview" tabindex="-1" role="dialog"
		aria-hidden="true" style="z-index: 1060;">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="card" style="background: #5E7DC0">
					<div class="card-header" style="padding: 2px 2px 2px 2px;">
						<div class="row no-gutters">
							<div class="col-md-4">
								<b class="card-title">디자인 시안</b>
							</div>
							<div class="col-md-4 ml-auto">
								<div class="float-right">
									<input type="hidden" id="docIdForModal">
									<button
										class="btn btn-secondary excelForDesign border border-light">
										<b>Excel</b>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="card-body"
						style="padding: 2px 2px 2px 2px; background: white;">

						<img class="img-responsive" id="designImgforDoc" src=""
							style="width: 100%; height: 100%;">
					</div>
					<div class="card-footer" style="padding: 2px 2px 2px 2px;">
						<button type="button"
							class="btn btn-secondary float-right border border-light"
							data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade myList" tabindex="-2" role="dialog"
		style="z-index: 1041;" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="card" style="background: #5E7DC0">
					<div class="card-header" style="padding: 2px 2px 2px 2px;">
						<div class="row no-gutters">
							<div class="col-md-4">
								<b class="card-title">협조 요청 디자인 목록</b>
							</div>
						</div>
					</div>
					<div class="card-body"
						style="padding: 2px 2px 2px 2px; background: white;">

						<table
							class="table table-striped table-bordered table-hover custom-table mb-0"
							style="font-size: 10px;">
							<thead>
								<tr>
									<!-- <th style="width: 9.97%;">LOTNO</th> -->

									<th style="width: 15%;">제목</th>
									<th style="width: 16%;">수주</th>
									<th style="width: 14%;">품목</th>
									<th style="width: 10%;">작성자</th>
									<th style="width: 13%;">등록 날짜</th>
									<th style="width: 13%;">수정 날짜</th>
									<th style="width: 6%;">시안</th>
									<th style="width: 10%;">진행도</th>

								</tr>
							</thead>
							<tbody style="height: 29.31746031746032vh;">
								<c:forEach var="ds" items="${mylist}">
									<tr>
										<td style="display: none;">${ds.id}</td>
										<td style="width: 15%;">${ds.title}</td>
										<td style="width: 16%;">${ds.info_title}</td>
										<td style="width: 14%;">${ds.product}</td>
										<td style="width: 10%;">${ds.writer}</td>
										<td style="width: 13%;">${ds.writtenDate}</td>
										<td style="width: 13%;">${ds.updateDate}</td>
										<td style="width: 6%; padding: 0px;"><span hidden="true">${ds.url}</span>
											<button class="btn btn-outline-info myDetail">
												<i class='bx bx-file-find'></i>
											</button></td>
										<td style="width: 10%;">${ds.state}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="card-footer" style="padding: 2px 2px 2px 2px;">
						<button type="button"
							class="btn btn-secondary float-right border border-light"
							data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>