<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">완제품관리</span>
		<div class="input-group search-group float-right  ">
			<label class="input-group-text search" style="height: 3.183023872679045vh; margin-right: 10px;">
				<input type="file" id="ProductExcel" accept=".xlsx" style="display: none;">Excel Import
			</label>
			<div class="input-group-prepend">
				<select class="custom-select search-select"
					id="productMasterInfoSearch"></select>
			</div>

			<input type="text" class="form-control search-control"
				id="ProductSearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9" style="height: 89vh;">
				<table id="ProductTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="productMasterColName">
						<tr>

							<th style="width: 2%; display: none;" class="ProductChkRow">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										name="ProductChkAll">
								</div>
							</th>

							<th style="width: 24%">제품</th>
							<th style="width: 18%">제품 코드</th>
							<th style="width: 18%">규격</th>
							<th style="width: 18%">판매가</th>
							<!-- <th style="width: 12%">단위 수량</th> -->
							<!-- <th style="width: 12%">적정 수량</th> -->
							<th style="width: 18%">비고</th>
						</tr>
					</thead>
					<tbody id="ProductBody">
						<c:forEach var="products" items="${Products}" varStatus="status">
							<tr class="${products.getId()}">
								<td class="ProductChkRow" style="display: none;">
									<div class="form-check">
										<input class="form-check-input position-static"
											type="checkbox" name="ProductChk" value="${products.getId()}">
									</div>
								</td>

								<td style="display: none;">${products.getId()}</td>
								<td>${products.getName()}</td>
								<td>${products.getProductCode()}</td>
								<td>${products.getSize()}</td>
								<td>${products.getPrice()}</td>
								<td>${products.getRemark()}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link ProductCreate active " data-toggle="tab"
							href="#Product-register">등록</a></li>
						<li class="nav-item"><a class="nav-link ProductUpdate"
							data-toggle="tab" href="#Product-update">수정</a></li>
						<li class="nav-item"><a class="nav-link ProductDelete"
							data-toggle="tab" href="#Product-delete">삭제</a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="Product-register">
						<form  id = "createProductForm" action = "createProduct" method="post">
							<div class="card border border-secondary mt-3">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">제품 이름</span>
										</div>
										<input type="text" class="form-control Product-c regi"
											name="name" placeholder="제품 이름">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">제품 코드</span>
										</div>
										<input type="text" class="form-control Product-c regi"
											name="productCode" placeholder="제품 코드">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">규격</span>
										</div>
										<input type="text" class="form-control Product-c regi"
											name="size" placeholder="규격">
									</div>
									<%-- <div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">제품 그룹</label>
										</div>
										<select class="custom-select Product-Select-c regi"
											name="productGroup.id">

										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">제품 특성</label>
										</div>
										<select class="custom-select Product-Select-c regi"
											name=" productFeature.id">

										</select>
									</div> --%>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">판매가</span>
										</div>
										<input type="text" class="form-control Product-c regi"
											name="price" placeholder="판매가">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">비고</span>
										</div>
										<input type="text" class="form-control Product-c regi"
											name="remark" placeholder="비고">
									</div>
									<%-- <div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">제품 위치</label>
										</div>
										<select class="custom-select Product-Select-c regi"
											name=" defaultLocation.id">
											<option value = "0">정보 없음</option>

										</select>
									</div> --%>
<%-- 									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">제품 타입</label>
										</div>
										<select class="custom-select Product-Select-c regi"
											name="unitType.id">
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">수량 타입</label>
										</div>
										<select class="custom-select Product-Select-c regi"
											name="quantitiesType.id">
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">수량</span>
										</div>
										<input type="number" class="form-control Product-c regi"
											name="unitCount" placeholder="수량">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">적정 수량</span>
										</div>
										<input type="number" class="form-control Product-c regi"
											name="properQuentity" placeholder="적정 수량">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">재고 수량</span>
										</div>
										<input type="number" class="form-control Product-c regi"
											name="stockCount" placeholder="재고 수량">
									</div> --%>
								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1 " type="submit" id = "createProductRegi">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade" id="Product-update">
						<div class=" card border border-secondary mb-2">
							<table
								class="table table-bordered Product-update-list-table mb-0 table-hover regi-update sub-table">
								<thead>
									<tr>
										<th style="width: 18%">제품</th>
										<th style="width: 12%">제품 코드</th>
										<th style="width: 12%">규격</th>
										<th style="width: 12%">판매가</th>
										<!-- <th style="width: 12%">단위 수량</th>
										<th style="width: 12%">적정 수량</th> -->
										<th style="width: 12%">비고</th>
									</tr>
								</thead>
								<tbody class="Product-update-list">
									<tr style="display: none;">
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="card border border-secondary mt-3">

							<div class="card-body regi-input-body">

								<s:csrfInput />
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">제품 이름</span>
									</div>
									<input type="text" class="form-control Product-u regi"
										name="name" placeholder="제품 이름">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">제품 코드</span>
									</div>
									<input type="text" class="form-control Product-u regi"
										name="productCode" placeholder="제품 코드">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">규격</span>
									</div>
									<input type="text" class="form-control Product-u regi"
										name="size" placeholder="규격">
								</div>
								<%-- <div class="input-group regi-group">
									<div class="input-group-prepend">
										<label class="input-group-text regi-group-text">제품 그룹</label>
									</div>
									<select class="custom-select Product-Select-u regi"
										name="productGroup">
										<option selected>데이터 없음</option>
									</select>
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<label class="input-group-text regi-group-text">제품 특성</label>
									</div>
									<select class="custom-select Product-Select-u regi"
										name=" productFeature">
										<option selected>데이터 없음</option>
									</select>
								</div> --%>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">판매가</span>
									</div>
									<input type="text" class="form-control Product-u regi"
										name="price" placeholder="판매가">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">비고</span>
									</div>
									<input type="text" class="form-control Product-u regi"
										name="remark" placeholder="비고">
								</div>
								<%-- <div class="input-group regi-group">
									<div class="input-group-prepend">
										<label class="input-group-text regi-group-text">제품 위치</label>
									</div>
									<select class="custom-select Product-Select-u regi"
										name=" defaultLocation">
										<option selected>데이터 없음</option>
									</select>
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<label class="input-group-text regi-group-text">제품 타입</label>
									</div>
									<select class="custom-select Product-Select-u regi"
										name="unitType">
										<option selected>데이터 없음</option>
									</select>
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<label class="input-group-text regi-group-text">수량 타입</label>
									</div>
									<select class="custom-select Product-Select-u regi"
										name="quantitiesType">
										<option selected>데이터 없음</option>
									</select>
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">수량</span>
									</div>
									<input type="number" class="form-control Product-u regi"
										name="unitCount" placeholder="판매가">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">적정 수량</span>
									</div>
									<input type="number" class="form-control Product-u regi"
										name="properQuentity" placeholder="적정 수량">
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">재고 수량</span>
									</div>
									<input type="number" class="form-control Product-u regi"
										name="stockCount" placeholder="재고 수량">
								</div> --%>
							</div>
							<div class="card-footer">
								<button class="btn regi-btn mt-1 mb-1" id="update-Product-btn"
									type="button">등록</button>
							</div>
						</div>

					</div>
					<div class="tab-pane fade show" id="Product-delete">
						<div class=" card border border-secondary mb-2 ">
							<table class="table table-bordered mb-0 regi-delete sub-table">
								<thead>
									<tr>
										<th style="width: 17%">제품</th>
										<th style="width: 12%">제품 코드</th>
										<th style="width: 12%">규격</th>
										<th style="width: 12%">판매가</th>
										<th style="width: 12%">비고</th>
										<th style="width: 1%"><i
											class='bx bxs-trash btn P-delete-checkBox'></i></th>
									</tr>
								</thead>
								<tbody class="Product-delete-list">
									<tr style="display: none;">
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
									</tr>

								</tbody>
							</table>
						</div>
					</div>
					<div class="modal fade" id="Product-Modal" tabindex="-1"
						role="dialog" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content Product-content">
								<div class="modal-header" style="color: black;">
									<span class="badge badge-dark" id="ProductName"></span>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true" style="color: black;">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<div class="card border border-secondary mt-3">

										<div class="card-body regi-input-body">

											<s:csrfInput />
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<span class="input-group-text regi-group-text">제품 이름</span>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name="name" placeholder="제품 이름">
											</div>
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<span class="input-group-text regi-group-text">규격</span>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name="standard" placeholder="규격">
											</div>
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<span class="input-group-text regi-group-text">제품 코드</span>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name="productCode" placeholder="제품 코드">
											</div>
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<label class="input-group-text regi-group-text">제품
														그룹</label>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name="productGroup.id">
											</div>
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<label class="input-group-text regi-group-text">제품
														특성</label>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name=" productFeature">

											</div>
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<span class="input-group-text regi-group-text">판매가</span>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name="defaultSalePrice" placeholder="판매가">
											</div>
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<label class="input-group-text regi-group-text">제품
														위치</label>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name=" defaultLocation.id">

											</div>
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<label class="input-group-text regi-group-text">제품
														타입</label>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name="unitType.id">
											</div>
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<label class="input-group-text regi-group-text">수량
														타입</label>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name="quantitiesType.id">
											</div>
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<span class="input-group-text regi-group-text">수량</span>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name="unitCount" placeholder="수량">
											</div>
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<span class="input-group-text regi-group-text">적정 수량</span>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name="properQuentity" placeholder="적정 수량">
											</div>
											<div class="input-group regi-group">
												<div class="input-group-prepend">
													<span class="input-group-text regi-group-text">재고 수량</span>
												</div>
												<input type="text" class="form-control Product-detail regi"
													name="stockCount" placeholder="재고 수량">
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
</div>