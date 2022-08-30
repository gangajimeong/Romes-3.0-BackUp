<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">원/부자재 관리</span>
		<div class="input-group search-group float-right  ">
			<label class="input-group-text search" style="height: 3.183023872679045vh; margin-right: 10px;">
				<input type="file" id="MaterialExcel" accept=".xlsx" style="display: none;">Excel Import
			</label>
			<div class="input-group-prepend">
				<select class="custom-select search-select"
					id="MaterialMasterInfoSearch"></select>
			</div>

			<input type="text" class="form-control search-control"
				id="MaterialSearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9" style="height: 89vh;">
				<table id="MaterialTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="MaterialMasterColName">
						<tr>
							<th style="width: 17%">제품</th>
							<th style="width: 12%">제품 코드</th>
							<th style="width: 12%">규격</th>
							<!-- <th style="width: 12%">매입단가</th> -->
							<th style="width: 10%">단위 수량</th>
							<th style="width: 10%">적정 수량</th>
							<th style="width: 10%">재고 수량</th>
							<th style="width: 7%; padding: 0px; display: none;"><i
								class='bx bx-barcode' style="font-size: 20px;"></i></th>
						</tr>
					</thead>
					<tbody id="MaterialBody">
						<c:forEach var="Materials" items="${Materials}" varStatus="status">
							<tr class="${Materials.getId()}">
								<td style="display: none;">${Materials.getId()}</td>
								<td>${Materials.getName()}</td>
								<td>${Materials.getProductCode()}</td>
								<td>${Materials.getStandard()}</td>
								<%-- <td>${Materials.getDefaultSalePrice()}</td> --%>
								<td>${Materials.getUnitCount()}</td>
								<td>${Materials.getProperQuentity()}</td>
								<td>${Materials.getStockCount()}</td>
								<td style="width: 7%; padding: 0px; display: none;"><button
										class="btn btn-secondary p-0 getPrinter">
										<i class='bx bx-barcode' style="font-size: 20px;"></i>
									</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link MaterialCreate active " data-toggle="tab"
							href="#Material-register">등록</a></li>
						<li class="nav-item"><a class="nav-link MaterialUpdate"
							data-toggle="tab" href="#Material-update">수정/삭제</a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="Material-register">
						<form action="createMaterial" method="post">
							<div class="card border border-secondary mt-3">
								<div class="card-body regi-input-body">
									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">제품 이름</span>
										</div>
										<input type="text" class="form-control Material-c regi"
											name="name" placeholder="제품 이름">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">제품 코드</span>
										</div>
										<input type="text" class="form-control Material-c regi"
											name="productCode" placeholder="제품 코드">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">규격</span>
										</div>
										<input type="text" class="form-control Material-c regi"
											name="standard" placeholder="규격">
									</div>
									<%-- <div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">매입단가</span>
										</div>
										<input type="text" class="form-control Material-c regi"
											name="defaultSalePrice" placeholder="매입단가">
									</div> --%>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">단위 수량</span>
										</div>
										<input type="number" class="form-control Material-c regi"
											name="unitCount" placeholder="단위 수량">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">적정 수량</span>
										</div>
										<input type="number" class="form-control Material-c regi"
											name="properQuentity" placeholder="적정 수량">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">재고 수량</span>
										</div>
										<input type="number" class="form-control Material-c regi"
											name="stockCount" placeholder="재고 수량">
									</div>
								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1 " type="submit">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade" id="Material-update">
						<div class="card-header"
							style="font-size: 0.8rem; padding: 2px 2px 2px 2px;">
							<div class="row no-gutters">
								<div class="col-md-12 col-sm-12 col-lg-12">
									<div class="float-right">
										<button type="button"
											class="btn btn-secondary border border-light"
											style="padding-top: 2px; padding-bottom: 2px;"
											id="deleteMaterial">
											<b class="card-title">삭제</b>
										</button>
									</div>
								</div>
							</div>
						</div>
						<div class="card border border-secondary mt-3">
							<div class="card-body regi-input-body">
								<form id="update-Material">
									<s:csrfInput />
									<input type="hidden" class="Material-u" name="id">
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">제품 이름</span>
										</div>
										<input type="text" class="form-control Material-u regi"
											name="name" placeholder="제품 이름">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">제품 코드</span>
										</div>
										<input type="text" class="form-control Material-u regi"
											name="productCode" placeholder="제품 코드">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">규격</span>
										</div>
										<input type="text" class="form-control Material-u regi"
											name="standard" placeholder="규격">
									</div>
									<%-- <div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">매입단가</span>
										</div>
										<input type="text" class="form-control Material-u regi"
											name="defaultSalePrice" placeholder="매입단가">
									</div> --%>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">단위 수량</span>
										</div>
										<input type="number" class="form-control Material-u regi"
											name="unitCount" placeholder="단위수량">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">적정 수량</span>
										</div>
										<input type="number" class="form-control Material-u regi"
											name="properQuentity" placeholder="적정 수량">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">재고 수량</span>
										</div>
										<input type="number" class="form-control Material-u regi"
											name="stockCount" placeholder="재고 수량">
									</div>
								</form>
							</div>
							<div class="card-footer">
								<button class="btn regi-btn mt-1 mb-1" id="update-Material-btn"
									type="button">수정</button>
							</div>
						</div>
					</div>
					<div class="modal fade BarcodePrint" tabindex="-2" role="dialog"
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
													id="SelectPrinter">다시 선택</button>
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
												id="printerLists">
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
											id="exePrint">출력</button>
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