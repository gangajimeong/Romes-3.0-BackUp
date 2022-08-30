<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">
	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">로케이션 마스터</span>
		<div class="input-group search-group float-right  ">
			<div class="input-group-prepend">
				<select class="custom-select search-select" id="LocationMasterInfoSearch"></select>
			</div>

			<input type="text" class="form-control search-control"
				id="LocationSearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 location-table-panel col-md-9">
				<div class="row no-gutters">
					<div class="col-md-6 left-area">
						<div class="card h-100 location-style">
							<div class="card-header">
								<span class="badge badge-dark">로케이션 목록</span>
							</div>
							<div class="card-body img-main-header img-frame">

								<img class="card-img-top" src="img/no_image.png"
									alt="Card image cap" id="no"
									style="max-height: 5rem; width: 5rem; margin-top: 7rem;">

								<img class="card-img-top" src="" alt="Card image cap" id="view"
									style="width: 100%; height: 100%; object-fit: cover; display: none;">

							</div>
							<div class="card-body">

								<table id="LocationTable"
									class="table mb-0 table-hover Top-location-table table-bordered custom-table">
									<thead id="LocationMasterColName">
										<tr>
											<th style="width: 50; padding: 0px;">장소명</th>
											<th style="width: 43%; padding: 0px;">타입</th>
											<th style="width: 7%; padding: 0px;"><i
												class='bx bx-barcode' style="font-size: 20px;"></i></th>

										</tr>
									</thead>
									<tbody id="LocationBody"
										style="height: 15rem; font-size: 0.1rem;">
										<c:forEach var="Locations" items="${Locations}"
											varStatus="status">
											<tr class="${Locations.getId()}" style="padding: 0px;">
												<td style="display: none;">${Locations.getId()}</td>
												<td style="width: 50%; padding: 0px;">${Locations.getName()}</td>
												<td style="width: 43%; padding: 0px;">${Locations.getLocationTypeCode().getValue()}</td>
												<td style="display: none; padding: 0px;">${Locations.getPath()}</td>
												<td style="width: 7%; padding: 0px;"><button
														class="btn btn-secondary p-0 getBarcode">
														<i class='bx bx-barcode' style="font-size: 20px;"></i>
													</button></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>

						</div>
					</div>
					<div class="col-md-6">
						<div class="card location-style">
							<div class="card-header" style="padding: 2px 2px 2px 2px">
								<ol class="stepBar step4" id="order-process">
									<li class="step active"><b>최상위</b></li>
									<li class="step "><b>하위</b></li>
									<li class="step"><b>하위</b></li>
									<li class="step"><b>하위</b></li>
								</ol>

							</div>
							<div class="card-body">

								<table 
									class="table table-striped table-bordered mb-0 table-hover sub-location custom-table">
									<thead >
										<tr>
											<th style="width: 50%">장소명</th>
											<th style="width: 43%">타입</th>
											<th style="width: 7%; padding: 0px;"><i
												class='bx bx-barcode' style="font-size: 20px;"
												id="oriBarcode"></i></th>
										</tr>
									</thead>
									<tbody id="Sub-LocationBody" style="height: 36.25rem;">
										<c:forEach var="i" begin="0" end="20">
											<tr>
												<td style="display: none;">-</td>
												<td style="width: 50%">-</td>
												<td style="width: 43%">-</td>
												<td style="display: none;">-</td>
												<td style="width: 7%; padding: 0px;"><button
														class="btn btn-secondary p-0">
														<i class='bx bx-barcode' style="font-size: 20px;"></i>
													</button></td>
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
							class="nav-link LocationCreate active " data-toggle="tab"
							href="#Location-register"><b>등록</b></a></li>
						<li class="nav-item"><a class="nav-link LocationUpdate"
							data-toggle="tab" href="#Location-update"><b>수정</b></a></li>
						<li class="nav-item"><a class="nav-link LocationDelete"
							data-toggle="tab" href="#Location-delete"><b>삭제</b></a></li>
						<!-- <li class="nav-item"><a class="nav-link LocationEmployee"
							data-toggle="tab" href="#Location-Employee-select"
							style="display: none">자재 위치</a></li> -->

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="Location-register">
						<div class="card border h-100">
							<div class="card-header border-secondary img-header">
								<img class="card-img-top" src="img/no_image.png"
									alt="Card image cap" id="preview"
									style="max-height: 5rem; width: 5rem; margin-top: 3rem;">
							</div>
							<form
								action="LocationCreate?${_csrf.parameterName}=${_csrf.token}"
								method="post" enctype="multipart/form-data">

								<div class="card-body regi-input-body">

									<div class="input-group regi-group">

										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">장소명</span>
										</div>
										<input type="text" class="form-control Location-c regi"
											name="name" placeholder="장소명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">장소 타입</label>
										</div>
										<select
											class="custom-select Location-Select-c Location-c regi"
											name="locationTypeCode.id">
											<option value="0" selected="selected">없음</option>
										</select>
									</div>

									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">상위 장소</label>

										</div>
										<select
											class="custom-select Location-Select-c Location-c regi"
											name="parentLocation.id">
											<option value="0" selected="selected">없음</option>
										</select>
									</div>
									<div class="input-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">업로드</span>
										</div>
										<div class="custom-file">
											<input type="file"
												class="custom-file-input file-regi-input Location-c"
												id="file-regi" accept="image/*" name="img"> <label
												class="custom-file-label file-regi-input file-select-name"
												for="file-regi">파일 선택</label>
										</div>
									</div>
								</div>
								<div class="card-footer regi-group">
									<button class="btn regi-btn mt-1 mb-1 " type="submit">등록</button>
								</div>
							</form>
						</div>


					</div>
					<div class="tab-pane fade" id="Location-update">
						<div class="card border">
							<div class="card-header border-secondary img-header">
								<img class="card-img-top" src="img/no_image.png"
									alt="Card image cap" id="preview-u"
									style="max-height: 5rem; width: 5rem; margin-top: 3rem;">
							</div>
							<div class="card-body regi-input-body">
								<form action="LocationUpdate?${_csrf.parameterName}=${_csrf.token}" method="post"
									enctype="multipart/form-data">
									<s:csrfInput />
									<input type="hidden" class="Location-u" name="id" value="">
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">장소명</span>
										</div>
										<input type="text" class="form-control Location-u regi"
											name="name" placeholder="장소명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">장소 타입</label>
										</div>
										<select class="custom-select Location-Select-u regi"
											name="locationTypeCode.id">
											<option value="0" selected="selected">없음</option>
										</select>
									</div>

									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">상위 장소</label>
										</div>
										<select class="custom-select Location-Select-u regi"
											name="parentLocation.id">
											<option value="0" selected="selected">없음</option>
										</select>
									</div>
									<div class="input-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">업로드</span>
										</div>
										<div class="custom-file">
											<input type="file" class="custom-file-input file-regi-input Location-u"
												id="file-regi-u" accept="image/*" name="imgs"> <label
												class="custom-file-label file-regi-input file-select-name-u"
												for="file-regi-u">파일 선택</label>
										</div>
									</div>

									<div class="card-footer regi-group">
										<button class="btn regi-btn mt-1 mb-1 " type="submit">등록</button>
									</div>
								</form>
							</div>
						</div>


					</div>
					<div class="tab-pane fade show" id="Location-delete">
						<div class=" card border border-secondary mb-2 ">
							<div class="card-body regi-body">

								<table
									class="table table-striped table-bordered mb-0 table-hover sub-location custom-table">
									<thead>
										<tr style="font-size: 1.3262599469496021vh;">
											<th style="width: 60%">장소명</th>
											<th style="width: 40%">타입</th>
										</tr>
									</thead>
									<tbody id="Delete-LocationBody" style="height: 40vh;">

									</tbody>
								</table>

							</div>
							<div class="card-footer">
								<button type="button" class="btn btn-secondary btn-block"
									id="delete-location-button">삭제</button>
							</div>

						</div>
					</div>
					<div class="tab-pane fade" id="Location-Employee-select">
						<div class=" card border border-secondary mb-2 ">
							<table
								class="table table-bordered mb-0 regi-delete sub-table custom-table">
								<thead>
									<tr>
										<th style="width: 30%">직원명</th>
										<th style="width: 30%">직책</th>
										<th style="width: 40%">번호</th>
									</tr>
								</thead>
								<tbody class="Location-employee-list" style="height: 40vh;">

								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade GoPrint" tabindex="-2" role="dialog"
		style="z-index: 1041;" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="card" style="background: #5E7DC0">
					<div class="card-header" style="padding: 2px 2px 2px 2px;">
						<div class="row no-gutters">
							<div class="col-md-4">
								<b class="card-title">라벨 프린터 선택</b>
							</div>
							<div class=" ml-auto">
								<button class="btn btn-secondary border border-light"
									id="reSelectPrinter">다시 선택</button>
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
									<th style="width: 5%;">No</th>
									<th style="width: 95%;">프린터</th>
								</tr>
							</thead>
							<tbody style="height: 29.31746031746032vh;"
								id="printListContents">
								<c:forEach var="print" items="${prints}" varStatus="no">
									<tr>
										<td style="display: none"></td>
										<td style="width: 5%;">${no.count}</td>
										<td style="width: 95%;">${print}</td>
									</tr>

								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="card-footer" style="padding: 2px 2px 2px 2px;">
						<button type="button"
							class="btn btn-secondary float-right border border-light"
							id="executePrint">출력</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>