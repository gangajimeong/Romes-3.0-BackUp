<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">
	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">브랜드 관리</span>
		
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div
				class=" card regi-card  mt-0 table-panel col-md-7 col-sm-7 col-lg-7">
				<div class="row no-gutters">
					<div class="col-md-12">
						<div class="card" id="BrandCard">
							<div class="card-header"
								style="font-size: 0.2rem; padding: 4px 4px 4px 4px; background-color: #5E7DC0">
								<div class="container-fluid">
									<div class="row" style="padding: 0px 0px 0px 0px;">
										<div class="col-md-2 col-sm-2 col-lg-2"
											style="padding: 0px 0px 0px 0px; margin: auto;">
											<span class="badge badge-dark page-titles">브랜드 목록</span>
										</div>
										<div class="col-md-10 col-sm-10 col-lg-10 ml-auto"
											style="padding: 0px 0px 0px 0px;">
											<div class="input-group search-group float-right" style="width:50%;">
												<label class="input-group-text search"
													style="height: 3.183023872679045vh; margin-right: 10px;">
													<input type="file" id="brandExcel" accept=".xlsx" style="display: none;">Excel Import
												</label>
												<div class="input-group-prepend">
													<select class="custom-select search-select"
														id="BrandManagementInfoSearch"></select>
												</div>

												<input type="search" class="form-control search-control"
													id="BrandSearch">
												<div class="input-group-append search-append">
													<span class="input-group-text search"><i
														class='bx bx-search bx-sm'></i></span>
												</div>
											</div>
										</div>
									</div>
								</div>

							</div>
							<div class="card-body p-0 h-100">
								<table id="BrandManagementTable"
									class="table table-condensed table-bordered mb-0 table-hover custom-table">
									<thead id="BrandManagementColName">
										<tr>
											<th style="width: 3%;">No</th>
											<th style="width: 25%;">브랜드명</th>
											<th style="width: 25%;">거래처명</th>
											<th style="width: 20%;">담당자명</th>
											<th style="width: 27%;">Tel</th>

										</tr>
									</thead>
									<tbody id="BrandBody" style="height: 35.5vh;">
										<c:forEach var="Brand" items="${BrandList}" varStatus="status">
											<tr id="Brand-${Brand.id}">
												<td style="width: 3%;">${status.count}</td>
												<td style="display: none;">${Brand.id}</td>
												<td style="width: 25%;">${Brand.brand}</td>
												<td style="width: 25%;">${Brand.company}</td>
												<td style="width: 20%;">${Brand.ceo}</td>
												<td style="width: 27%;">${Brand.tel}</td>


											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="card " id="BranchCard">
							<div class="card-header"
								style="font-size: 0.2rem; padding: 4px 4px 4px 4px; background-color: #5E7DC0">
								<div class="container-fluid">
									<div class="row" style="padding: 0px 0px 0px 0px;">
										<div class="col-md-2 col-sm-2 col-lg-2"
											style="padding: 0px 0px 0px 0px; margin: auto;">
											<span class="badge badge-dark page-titles"><span
												id="brandListName"></span>&nbsp;매장 목록</span>
										</div>
										<div class="col-md-10 col-sm-10 col-lg-10 ml-auto"
											style="padding: 0px 0px 0px 0px;">
											<div class="input-group search-group float-right" style="width:50%;">
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
							<div class="card-body p-0 h-100">
								<table id="BranchManagementTable"
									class="table table-condensed table-striped table-bordered mb-0 table-hover custom-table">
									<thead id="BrandManagement2ColName" class="BranchManagementColName">
										<tr>
											<th style="width: 3%;">No</th>
											<th style="width: 25%;">매장명</th>
											<th style="width: 15%;">시공사</th>
											<th style="width: 57%;">장소</th>
										</tr>
									</thead>
									<tbody id="BranchBody" style="height: 35.5vh;">

									</tbody>
								</table>
							</div>
						</div>

					</div>

				</div>
			</div>
			<div class="card col-md-5 col-lg-5 col-sm-5">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a class="nav-link BrandCreate active "
							data-toggle="tab" href="#Brand-register"><b>등록</b></a></li>
						<li class="nav-item"><a class="nav-link BrandUpdate"
							data-toggle="tab" href="#Brand-update"><b>수정 / 삭제</b></a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content p-0">
					<div class="tab-pane fade show active " id="Brand-register">

						<div class="card p-0">
							<div class="card-header"
								style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
								<b class="card-title">브랜드 등록</b>
							</div>

							<div class="card-body regi-input-body" style="padding: 3px;">
								<form id="createBrandManagement">
									<s:csrfInput />

									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">브랜드명</span>
										</div>
										<input type="text" class="form-control Brand-c regi"
											name="companyName" placeholder="브랜드명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">담당자명</span>
										</div>
										<input type="text" class="form-control Brand-c regi"
											name="CEO_Name" placeholder="담당자명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">TEL</span>
										</div>
										<input type="tel" class="form-control Brand-c regi"
											name="phone" placeholder="010-1234-1234"
											pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">거래처</label>
										</div>
										<select class="custom-select Brand-c regi" name="company.id">
											<c:forEach var="item" items="${companys}">
												<option value="${item.id}" selected="selected">${item.name}</option>
											</c:forEach>
										</select>
									</div>

									<div class="input-group regi-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">비고</span>
										</div>
										<input type="text" class="form-control Brand-c regi"
											name="remarks" placeholder="비고">
									</div>
									<div class="border p-1">
										<button class="btn btn-secondary regi-btn mt-2 mb-2"
											type="button" id="NewBrand-register">등록</button>
									</div>
								</form>
							</div>
							<div class="card-body mb-0" style="padding: 3px; display:none;"
								id="Branch-register">
								<div class="card p-0 border borderless mt-2">
									<div class="card-header"
										style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
										<b class="card-title">&nbsp;<span class="BrandName"></span>&nbsp;매장
											등록
										</b>
									</div>
									<div class="card-body regi-input-body p-0">

										<table
											class="table table-bordered table-striped mb-0 custom-table">
											<thead
												style="padding-top: 0px; padding-bottom: 0px; font-size: 0.5rem;">
												<tr>
													<!-- <th style="width: 9.97%;">LOTNO</th> -->
													<th style="width: 20%;">매장명</th>
													<th style="width: 20%;">시공사</th>
													<th style="width: 55%;">주소</th>
													<th style="width: 5%; vertical-align: middle"><i
														class='bx bxs-trash' id="trashForStore"
														style="color: gray;"></i></th>
												</tr>
											</thead>
											<tbody class="p0" id="StoreLists"
												style="height: 13.854111405835544vh;">
											</tbody>
										</table>
										<form id="storeLocation" class="p-0">
											<div class="row no-gutters" style="padding: 5px;">
												<input type="hidden" name=brand_id id="brandId">
												<div class="col-md-12 col-sm-12 col-lg-12">
													<div class="input-group regi-group">
														<div class="input-group-prepend">
															<span class="input-group-text  regi-group-text">매장명</span>
														</div>
														<input type="text" class="form-control Store-c regi"
															name="title" placeholder="매장명" disabled="disabled">
													</div>
												</div>
												<div class="col-md-12 col-sm-12 col-lg-12">
													<div class="input-group regi-group">
														<div class="input-group-prepend">
															<label class="input-group-text regi-group-text">시공사</label>
														</div>
														<select class="custom-select Store-c regi"
															name="company.id">
															<c:forEach var="construction" items="${Construction}">
																<option value="${construction.id}">${construction.name}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="col-md-12 col-sm-12 col-lg-12">
													<div class="input-group regi-group">
														<div class="input-group-prepend">
															<span class="input-group-text  regi-group-text">주소</span>
														</div>
														<input type="text"
															class="form-control Store-c regi addressSearch-branch"
															value="주소 입력" readonly="readonly">
													</div>
												</div>
												<div class="col-md-12 col-sm-12 col-lg-12">
													<div class="input-group regi-group mb-2">
														<div class="input-group-prepend">
															<span class="input-group-text  regi-group-text">상세
																주소</span>
														</div>
														<input type="text"
															class="form-control Store-c regi detailAddress-branch"
															name="address" placeholder="상세 주소" disabled="disabled">
													</div>
												</div>
												<div class="col-md-12 col-sm-12 col-lg-12">
													<button class="btn btn-block btn-info" id="addStore"
														type="button">추가</button>
												</div>
											</div>
										</form>
									</div>
									<div class="card-footer p-1">
										<button class="btn btn-secondary regi-btn mt-1 mb-1"
											type="button" id="Branch-register-btn" disabled="disabled">등록</button>
									</div>
								</div>


							</div>

						</div>


					</div>
					<div class="tab-pane fade" id="Brand-update">
						<div class="card mt-1 p-0">
							<div class="card-header"
								style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
								<div class="row no-gutters">
									<div class="col-md-3 col-sm-3 col-lg-3">
										<b class="card-title"><b class="card-title">브랜드 수정/삭제</b></b>
									</div>
									<div class="col-md-9 col-sm-9 col-lg-9">
										<div class="float-right">
											<button type="button" id="delete-brand"
												class="btn btn-secondary border border-light"
												style="padding-top: 2px; padding-bottom: 2px;">
												<b class="card-title">브랜드 삭제</b>
											</button>
										</div>
									</div>
								</div>
							</div>

							<div class="card-body regi-input-body" style="padding: 3px;">
								<form id="updateBrandManagement">
									<s:csrfInput />
									<input type="hidden" class="Brand-u" name="id">
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">브랜드명</span>
										</div>
										<input type="text" class="form-control Brand-u regi"
											name="companyName" placeholder="브랜드명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">담당자명</span>
										</div>
										<input type="text" class="form-control Brand-u regi"
											name="CEO_Name" placeholder="담당자명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">TEL</span>
										</div>
										<input type="tel" class="form-control Brand-u regi"
											name="phone" placeholder="TEL">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">거래처</label>
										</div>
										<select class="custom-select Brand-u regi" name="company.id">
											<c:forEach var="item" items="${companys}">
												<option value="${item.id}" selected="selected">${item.name}</option>
											</c:forEach>
										</select>
									</div>

									<div class="input-group regi-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">비고</span>
										</div>
										<input type="text" class="form-control Brand-u regi"
											name="remarks" placeholder="비고">
									</div>
									<div class="border p-1">
										<button class="btn btn-secondary regi-btn mt-2 mb-2"
											id=BrandUpdateButton type="button">수정</button>
									</div>
								</form>
							</div>
							<div class="card-body regi-input-body " style="padding: 3px;">
								<div class="card p-0 border borderless mt-2">
									<div class="card-header"
										style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
										<b class="card-title">매장 추가/수정</b>
									</div>
									<div class="card-body regi-input-body p-0">

										<table
											class="table table-bordered table-striped mb-0 custom-table">
											<thead
												style="padding-top: 0px; padding-bottom: 0px; font-size: 0.5rem;">
												<tr>
													<!-- <th style="width: 9.97%;">LOTNO</th> -->
													<th style="width: 20%;">매장명</th>
													<th style="width: 20%;">시공사</th>
													<th style="width: 55%;">주소</th>
													<th style="width: 5%; vertical-align: middle"><i
														class='bx bxs-trash' id="trashForStoreForUpdate"
														style="color: gray;"></i></th>
												</tr>
											</thead>
											<tbody class="p0" id="StoreListsForUpdate"
												style="height: 13.854111405835544vh;">
											</tbody>
										</table>
										<form id="storeLocationForUpdate" class="p-0">
											<div class="row no-gutters" style="padding: 5px;">
												<input type="hidden" id="brand_idForUpdate"> <input
													type="hidden" class=" Store-u">
												<div class="col-md-12 col-sm-12 col-lg-12">
													<div class="input-group regi-group">
														<div class="input-group-prepend">
															<span class="input-group-text  regi-group-text">매장명</span>
														</div>
														<input type="text" class="form-control Store-u regi"
															placeholder="매장명">
													</div>
												</div>
												<div class="col-md-12 col-sm-12 col-lg-12">
													<div class="input-group regi-group">
														<div class="input-group-prepend">
															<label class="input-group-text regi-group-text">시공사</label>
														</div>
														<select class="custom-select Store-u regi"
															name="company.id">
															<c:forEach var="construction" items="${Construction}">
																<option value="${construction.id}">${construction.name}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="col-md-12 col-sm-12 col-lg-12">
													<div class="input-group regi-group">
														<div class="input-group-prepend">
															<span class="input-group-text  regi-group-text">주소</span>
														</div>
														<input type="text"
															class="form-control Store-u regi addressSearch-branch"
															placeholder="주소">
													</div>
												</div>
												<div class="col-md-12 col-sm-12 col-lg-12">
													<div class="input-group regi-group mb-2">
														<div class="input-group-prepend">
															<span class="input-group-text  regi-group-text">상세
																주소</span>
														</div>
														<input type="text"
															class="form-control Store-u regi detailAddress-branch"
															placeholder="상세 주소">
													</div>
												</div>
												<div class="col-md-12 col-sm-12 col-lg-12">
													<div class="btn-group btn-block" role="group">
														<button class="btn  btn-info w-100" id="TrStoreForUpdate"
															type="button">반영</button>
														<button class="btn w-100 btn-info" id="addStoreForUpdate"
															type="button">추가</button>
													</div>

												</div>
											</div>
										</form>
									</div>
									<div class="card-footer p-1">
										<button class="btn btn-secondary regi-btn mt-1 mb-1"
											id=StoreUpdateButton type="button">수정</button>
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


