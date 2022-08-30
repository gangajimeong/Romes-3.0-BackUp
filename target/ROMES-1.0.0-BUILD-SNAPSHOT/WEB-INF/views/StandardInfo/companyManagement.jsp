<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">
	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">거래처 관리</span>
		<div class="input-group search-group float-right  ">
			<label class="input-group-text search"
				style="height: 3.183023872679045vh; margin-right: 10px;">
				<input type="file" id="companyExcel" accept=".xlsx" style="display: none;">Excel Import
			</label>
			<div class="input-group-prepend">
				<select class="custom-select search-select"
					id="companyManagementInfoSearch"></select>
			</div>

			<input type="text" class="form-control search-control"
				id="companySearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9">
				<table id="companyManagementTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="companyManagementColName">
						<tr>

							<th style="width: 2%; display: none;" class="companyChkRow">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										name="companyChkAll">
								</div>
							</th>
							<th style="width: 3%;">No</th>
							<th style="width: 20%;">업체명</th>
							<th style="width: 15%;">대표명</th>
							<th style="width: 10%;">사업 번호</th>
							<th style="width: 30%;">Tel</th>
							<th style="width: 30%;">Fax</th>
						</tr>
					</thead>
					<tbody id="companyBody">
						<c:forEach var="company" items="${companyList}" varStatus="status">
							<tr id ="Company-${company.getId()}" >
								<td class="companyChkRow" style="display: none; width: 2%;">
									<div class="form-check">
										<input class="form-check-input position-static"
											type="checkbox" name="companyChk" value="${company.getId()}">
									</div>
								</td>

								<td style="width: 3%;">${status.count}</td>
								<td style="display: none;">${company.getId()}</td>

								<td style="width: 20%;">${company.companyName}</td>
								<td style="width: 15%;">${company.CEO_Name}</td>
								<td style="width: 10%;">${company.businessNumber}</td>
								<td style="width: 30%;">${company.phone}</td>
								<td style="width: 30%;">${company.fax}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link companyCreate active " data-toggle="tab"
							href="#company-register">등록</a></li>
						<li class="nav-item"><a class="nav-link companyUpdate"
							data-toggle="tab" href="#company-update">수정</a></li>
						<li class="nav-item"><a class="nav-link companyDelete"
							data-toggle="tab" href="#company-delete">삭제</a></li>
						<li class="nav-item"><a
							class="nav-link companyResponsibility" data-toggle="tab"
							href="#company-Responsibility">담당자 관리</a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active " id="company-register">
						<form action="createCompanyManagement" method="post">
							<div class="card border border-secondary mt-3">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">업체명</span>
										</div>
										<input type="text" class="form-control company-c regi"
											name="companyName" placeholder="업체명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">대표명</span>
										</div>
										<input type="text" class="form-control company-c regi"
											name="CEO_Name" placeholder="대표명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">사업 번호</span>
										</div>
										<input type="text" class="form-control company-c regi"
											name="businessNumber" placeholder="사업 번호">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">우편 번호</span>
										</div>
										<input type="text" class="form-control company-c regi postal-input" 
											name="postalCode" placeholder="우편 번호" readonly="readonly">
									</div>
									<div class="input-group regi-group ">
										<div class="input-group-prepend ">
											<span class="input-group-text  regi-group-text border border-info">주소</span>
										</div>
										<input type="text" class="form-control regi addressSearch border border-info"
											 placeholder="주소 입력 버튼" readonly="readonly" >
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">상세 주소</span>
										</div>
										<input type="text" class="form-control company-c regi detailAddress"
											name="realAddress" placeholder="상세 주소"  >
									</div>

									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">TEL</span>
										</div>
										<input type="tel" class="form-control company-c regi"
											name="phone" placeholder="TEL">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">FAX</span>
										</div>
										<input type="tel" class="form-control company-c regi"
											name="fax" placeholder="FAX">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">Email</span>
										</div>
										<input type="email" class="form-control company-c regi"
											name="email" placeholder="Email">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">비고</span>
										</div>
										<input type="text" class="form-control company-c regi"
											name="remarks" placeholder="비고">
									</div>
								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1" type="submit">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade" id="company-update">
						<form id="update-company-list" method="post">
							<div class="card border border-secondary mt-3">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<input type="hidden" name="id" class="company-u">

									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">업체명</span>
										</div>
										<input type="text" class="form-control company-u regi"
											name="companyName" placeholder="업체명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">대표명</span>
										</div>
										<input type="text" class="form-control company-u regi"
											name="CEO_Name" placeholder="대표명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">사업 번호</span>
										</div>
										<input type="text" class="form-control company-u regi"
											name="businessNumber" placeholder="사업 번호">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">우편 번호</span>
										</div>
										<input type="text" class="form-control company-u regi"
											name="postalCode" placeholder="우편 번호">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">주소</span>
										</div>
										<input type="text" class="form-control company-u regi"
											name="realAddress" placeholder="주소">
									</div>

									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">TEL</span>
										</div>
										<input type="text" class="form-control company-u regi"
											name="phone" placeholder="TEL">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">FAX</span>
										</div>
										<input type="text" class="form-control company-u regi"
											name="fax" placeholder="FAX">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">Email</span>
										</div>
										<input type="text" class="form-control company-u regi"
											name="email" placeholder="Email">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">비고</span>
										</div>
										<input type="text" class="form-control company-u regi"
											name="remarks" placeholder="비고">
									</div>
								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1" id="update-company-btnn"
										type="button">수정</button>
								</div>
							</div>
						</form>
					</div>
					<div class="tab-pane fade" id="company-delete">
						<div class=" card border border-secondary mb-2 ">
							<table class="table table-bordered mb-0 regi-delete">
								<thead>
									<tr>

										<th style="width: 30%;">업체명</th>
										<th style="width: 20%;">대표명</th>
										<th style="width: 45%;">사업 번호</th>
										<th style="width: 5%;"><i
											class='bx bxs-trash c-delete-checkBox'></i></th>
									</tr>
								</thead>
								<tbody class="company-delete-list" style="font-size: 5px;">

								</tbody>
							</table>
						</div>
					</div>
					<div class="tab-pane fade" id="company-Responsibility">
						<div class=" card border border-secondary mb-2">
							<table
								class="table table-bordered Responsibility-list-table mb-0 table-hover sub-table">
								<thead>
									<tr>
										<th style="display: none;" id="companyMini"></th>
										<th style="width: 10%;">담당자</th>
										<th style="width: 20%;">직책</th>
										<th style="width: 30%;">TEL</th>
										<th style="width: 35%;">Email</th>
										<th style="width: 5%;"><i class='bx bxs-trash'></i></th>
									</tr>
								</thead>
								<tbody class="company-M-list">
									
								</tbody>
							</table>
						</div>


						<div class="card-header register">
							<ul class="nav nav-tabs card-header-tabs regi-ul">
								<li class="nav-item regi-li"><a
									class="nav-link sub-company-create active " data-toggle="tab"
									href="#sub-company-register">추가</a></li>
								<li class="nav-item regi-li"><a
									class="nav-link sub-company-update" data-toggle="tab"
									href="#sub-company-UD">수정</a></li>
							</ul>
						</div>
						<div class="card-body regi-input-body tab-content">

							<div class="tab-pane fade show active" id="sub-company-register">

								<form id="company-regiManager">
									<div class="card-body regi-input-body">
										<s:csrfInput />

										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-text">담당자</span>
											</div>
											<input type="text"
												class="form-control company-m-control regi" name="name"
												placeholder="담당자">
										</div>
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-text">직책</span>
											</div>
											<input type="text"
												class="form-control company-m-control regi" name="position"
												placeholder="직책">
										</div>
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-text">Tel</span>
											</div>
											<input type="text"
												class="form-control company-m-control regi" name="phone"
												placeholder="번호">
										</div>
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text  regi-group-text">Email</span>
											</div>
											<input type="text"
												class="form-control company-m-control regi" name="email"
												placeholder="이메일">
										</div>
									</div>
									<div class="card-footer">
										<button class="btn regi-btn mt-1 mb-1" type="button"
											id="addManager">등록</button>
									</div>
								</form>

							</div>
							<div class="tab-pane fade" id="sub-company-UD">
								<form id = "update-manger-form">
									<div class="card-body regi-input-body">
										<s:csrfInput />
										<input type="hidden" class=" manager-U-control" name="id">
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-text">담당자</span>
											</div>
											<input type="text"
												class="form-control manager-U-control regi" name="name"
												placeholder="담당자">
										</div>
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-text">직책</span>
											</div>
											<input type="text"
												class="form-control  manager-U-control regi" name="position"
												placeholder="직책">
										</div>
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-text">Tel</span>
											</div>
											<input type="text"
												class="form-control  manager-U-control regi" name="phone"
												placeholder="번호">
										</div>
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text  regi-group-text">Email</span>
											</div>
											<input type="text"
												class="form-control  manager-U-control regi" name="email"
												placeholder="이메일">
										</div>
									</div>
									<div class="card-footer">
										<button class="btn regi-btn mt-1 mb-1"
											id="update-company-manager-btn" type="button">수정</button>
									</div>
								</form>



							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="company-Modal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content company-content">
				<div class="modal-header" style="color: black;">
					<span class="badge badge-dark page-title">거래처 관리 수정</span>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true" style="color: black;">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<%-- <form id="update-company-Manager" method="post">
						<div class="card border border-secondary mt-3">

							<div class="card-body regi-input-body">

								<s:csrfInput />
								<input type="hidden" name="id" class="company-u">

								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">업체명</span>
									</div>
									<input type="text" class="form-control company-u regi"
										name="companyName" placeholder="업체명">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">대표명</span>
									</div>
									<input type="text" class="form-control company-u regi"
										name="CEO_Name" placeholder="대표명">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text  regi-group-text">사업 번호</span>
									</div>
									<input type="text" class="form-control company-u regi"
										name="businessNumber" placeholder="사업 번호">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text  regi-group-text">우편 번호</span>
									</div>
									<input type="text" class="form-control company-u regi"
										name="postalCode" placeholder="우편 번호">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text  regi-group-text">주소</span>
									</div>
									<input type="text" class="form-control company-u regi"
										name="realAddress" placeholder="주소">
								</div>

								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text  regi-group-text">TEL</span>
									</div>
									<input type="text" class="form-control company-u regi"
										name="phone" placeholder="TEL">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text  regi-group-text">FAX</span>
									</div>
									<input type="text" class="form-control company-u regi"
										name="fax" placeholder="FAX">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text  regi-group-text">Email</span>
									</div>
									<input type="text" class="form-control company-u regi"
										name="email" placeholder="Email">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text  regi-group-text">비고</span>
									</div>
									<input type="text" class="form-control company-u regi"
										name="remarks" placeholder="비고">
								</div>
							</div>
							<div class="card-footer">
								<button class="btn regi-btn mt-1 mb-1" id=""
									type="submit">수정</button>
							</div>
						</div>
					</form> --%>
				</div>
			</div>
		</div>
	</div>
</div>
