<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<link href="./css/OrderHistory.css" rel="stylesheet">
<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">발주 관리</span>
		<div class="input-group search-group float-right" style="display: none;">
			<div class="input-group-prepend">
				<select class="custom-select search-select"
					id="OrderHistoryInfoSearch"></select>
			</div>

			<input type="search" class="form-control search-control"
				id="OrderHistorySearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class="  mt-0 table-panel col-2 border border-scondary"
				style="padding: 1px 1px 1px 1px;">
				<div>
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover border-scondary doc"
						id="OrderHistoryTable">
						<thead id="OrderHistoryColName">
							<tr>
								<!-- <th style="width: 9.97%;">LOTNO</th> -->
								<th style="width: 60%;">문서 번호</th>
								<th style="width: 40%;">문서 제목</th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="doc-scroll">
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover doc">
						<tbody id="OrderHistoryBody" class="p0">
							<c:forEach var="Docs" items="${Docs}">
								<tr>

									<td style="width: 60%;">${Docs.getDocNumber()}</td>
									<td style="width: 40%;">${Docs.getTitle()}</td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>
			<div class="mt-0 table-panel col-7 border border-scondary "
				style="padding: 1px 1px 1px 1px;">
				<div>
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover "
						id="OrderHistoryTable">
						<thead id="OrderHistoryColName">
							<tr>
								<!-- <th style="width: 9.97%;">LOTNO</th> -->
								<th style="width: 8%;">제품유형</th>
								<th style="width: 17%;">제품</th>
								<th style="width: 5%;">주문량</th>
								<th style="width: 10%;">거래처</th>
								<th style="display:none;">매입가</th>
								<th style="width: 10%;">주문날짜</th>
								<th style="width: 10%;">도착예정날짜</th>
								<th style="width: 9.97%;">도착날짜</th>
								<th style="width: 9.97%;">담당자</th>
								<th style="width: 10.03%;">입고 여부</th>

							</tr>
						</thead>
					</table>
				</div>
				<div class="table-scroll">
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover">
						<tbody id="OrderHistoryContents">
							<c:forEach begin="0" end="25" step="1">
								<tr>
									<td style="display: none;"></td>
									<td style="width: 8%;">-</td>
									<td style="width: 17%;">-</td>
									<td style="width: 5%;">-</td>
									<td style="width: 10%;">-</td>
									<td style="display:none;">-</td>
									<td style="width: 10%;">-</td>
									<td style="width: 10%;">-</td>
									<td style="width: 10%;">-</td>
									<td style="width: 10%;">-</td>
									<td style="width: 10%;">-</td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a class="nav-link OrderCreate active "
							data-toggle="tab" href="#Orderhistory-register">등록</a></li>
						<li class="nav-item"><a class="nav-link OrderUpdate"
							data-toggle="tab" href="#Orderhistory-update">수정</a></li>
						<li class="nav-item"><a class="nav-link OrderDelete"
							data-toggle="tab" href="#Orderhistory-delete">삭제</a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="Orderhistory-register">
						<s:csrfInput />
						<div class="card border border-secondary mt-3">
							<div class="card">
								<div class="card-header" style="padding: 1px 1px 1px 1px;">
									<div class="input-group Order-group">
										<div class="input-group-prepend"
											style="padding: 0px 0px 0px 0px; height: 1.5rem;">
											<span class="input-group-text Order-group-text"
												style="padding: 0px 0.1875rem 0px 0.1875rem;">문서 제목</span>
										</div>
										<input type="text" class="form-control Doctitle"
											style="height: 1.5rem;" name="DocTitle" placeholder="Title">
									</div>
								</div>
								<div class="card-body regi-body">
									<table class="table mb-0 table-hover table-condensed">
										<thead
											style="padding-top: 0px; padding-bottom: 0px; font-size: 0.5rem;">
											<tr>
												<!-- <th style="width: 9.97%;">LOTNO</th> -->
												<th style="width: 22%;">유형</th>
												<th style="width: 25%;">제품</th>
												<th style="width: 25%;">주문량</th>
												<th style="display:none;">매입가</th>
												<th style="width: 3%; padding: 0 0 0 0;"><i
													class='bx bxs-trash btn deleteRow'></i></th>
											</tr>
										</thead>
									</table>
									<div style="height: 10rem; overflow: scroll;">
										<table
											class="table table-condensed table-striped table-bordered mb-0 table-hover">
											<tbody class="p0" id="OrderLists">
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="card-body regi-input-body row no-gutter"
								style="margin: 0px 0px 0px 0px">
								<div class="input-group Order-group col-md-12 ">
									<div class="input-group-prepend"
										style="padding: 0px 0px 0px 0px; height: 1.5rem;">
										<label class="input-group-text Order-group-text"
											style="padding: 0px 0.1875rem 0px 0.1875rem;">제품 유형</label>
									</div>
									<select class="custom-select Orderhistory " name="isMaterial">
										<option value="true" selected="selected">원/부자재</option>
										<option value="false" >완제품</option>
									</select>
								</div>

								<div class="input-group Order-group col-md-12 mt-2">
									<div class="input-group-prepend"
										style="padding: 0px 0px 0px 0px; height: 1.5rem;">
										<label class="input-group-text Order-group-text"
											style="padding: 0px 0.1875rem 0px 0.1875rem;">제품</label>
									</div>
									<select
										class="custom-select Orderhistory-Select-c Orderhistory-c Orderhistory "
										name="product_id">

									</select>
								</div>
								<div class="input-group Order-group col-md-12 mt-2">
									<div class="input-group-prepend"
										style="padding: 0px 0px 0px 0px; height: 1.5rem;">
										<span class="input-group-text Order-group-text"
											style="padding: 0px 0.1875rem 0px 0.1875rem;">주문량</span>
									</div>
									<input type="number" class="form-control Orderhistory" min="0"
										style="height: 1.5rem;" name="OrderCount" placeholder="0"
										value=0>
								</div>

								<div class="input-group Order-group col-md-12 mt-2" style="display: none;">
									<div class="input-group-prepend" >
										<span class="input-group-text Order-group-text"
											style="padding: 0px 0.1875rem 0px 0.1875rem;">매입가</span>
									</div>
									<input type="number" class="form-control Orderhistory"
										style="display:none;" name="price" placeholder="0" min="0"
										step="10" value="0">
								</div>
								<div class="input-group Order-group col-md-12 mt-2">
									<div class="input-group-prepend"
										style="padding: 0 0 0 0; height: 1.5rem;">
										<label class="input-group-text Order-group-text">거래처</label>
									</div>
									<select
										class="custom-select Orderhistory-Select-c Orderhistory-c Orderhistory"
										name="company.id">
										<option value="0" selected="selected">없음</option>
									</select>
								</div>

								<div class="input-group Order-group col-md-12 mt-2">
									<div class="input-group-prepend"
										style="padding: 0 0 0 0; height: 1.5rem;">
										<span class="input-group-text Order-group-text">주문일</span>
									</div>
									<input type="date" class="form-control Orderhistory"
										style="height: 1.5rem;" name="orderDate">
								</div>
								<div class="input-group Order-group col-md-12 mt-2">
									<div class="input-group-prepend"
										style="padding: 0 0 0 0; height: 1.5rem;">
										<span class="input-group-text Order-group-text">도착 예정일</span>
									</div>
									<input type="date" class="form-control Orderhistory"
										style="height: 1.5rem;" name="plannedArriveDate">
								</div>
								<div class="input-group Order-group col-md-12 mt-3">
									<button type="button" class="btn" id="addOrder"
										style="padding: 0 1 0 1; width: 100%;">추가</button>
								</div>
							</div>

							<div class="card-footer" style="padding: 1px 1px 1px 1px;">

								<button type="button"
									class="btn btn-block btn-secondary float-right"
									style="padding: 0 1 0 1;" id="orderRegister">등록</button>
							</div>
						</div>


					</div>
					<div class="tab-pane fade " id="Orderhistory-update">
						<div class="card border border-secondary mt-3">
							<div class="card">
								<div class="card-header" style="padding: 1px 1px 1px 1px;">
									<div class="input-group Order-group">
										<div class="input-group-prepend"
											style="padding: 0px 0px 0px 0px; height: 1.5rem;">
											<span class="input-group-text Order-group-text"
												style="padding: 0px 0.1875rem 0px 0.1875rem;">문서 제목</span>
										</div>
										<input type="text" class="form-control UpdateDoctitle"
											style="height: 1.5rem;" name="DocTitle" placeholder="Title">
									</div>
								</div>
								<div class="card-body regi-body">
									<table class="table mb-0 table-hover table-condensed">
										<thead
											style="padding-top: 0px; padding-bottom: 0px; font-size: 0.5rem;">
											<tr>
												<!-- <th style="width: 9.97%;">LOTNO</th> -->
												<th style="width: 22%;">유형</th>
												<th style="width: 25%;">제품</th>
												<th style="width: 25%;">주문량</th>
												<th style="display:none;">매입가</th>
												<th style="width: 3%; padding: 0 0 0 0;"><i
													class='bx bxs-trash btn deleteRow'></i></th>
											</tr>
										</thead>
									</table>
									<div style="height: 10rem; overflow: scroll;">
										<table
											class="table table-condensed table-striped table-bordered mb-0 table-hover">
											<tbody class="p0" id="update-OrderLists">
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="card-body regi-input-body row no-gutter"
								style="margin: 0px 0px 0px 0px">
								<input type="hidden" id="updateDocID" name="id">
								<div class="input-group Order-group col-md-12">
									<div class="input-group-prepend"
										style="padding: 0px 0px 0px 0px; height: 1.5rem;">
										<label class="input-group-text Order-group-text"
											style="padding: 0px 0.1875rem 0px 0.1875rem;">제품 유형</label>
									</div>
									<select class="custom-select Orderhistory-u " name="isMaterial">
										<option value="true" selected="selected">원/부자재</option>
										<option value="false" >완제품</option>
									</select>
								</div>

								<div class="input-group Order-group col-md-12 mt-2">
									<div class="input-group-prepend"
										style="padding: 0px 0px 0px 0px; height: 1.5rem;">
										<label class="input-group-text Order-group-text"
											style="padding: 0px 0.1875rem 0px 0.1875rem;">제품</label>
									</div>
									<select
										class="custom-select Orderhistory-Select-u Orderhistory-u  "
										name="product_id">

									</select>
								</div>
								<div class="input-group Order-group col-md-12 mt-2">
									<div class="input-group-prepend"
										style="padding: 0px 0px 0px 0px; height: 1.5rem;">
										<span class="input-group-text Order-group-text"
											style="padding: 0px 0.1875rem 0px 0.1875rem;">주문량</span>
									</div>
									<input type="number" class="form-control Orderhistory-u"
										min="0" style="height: 1.5rem;" name="OrderCount"
										placeholder="0" value=0>
								</div>

								<div class="input-group Order-group col-md-12 mt-2" style="display: none;">
									<div class="input-group-prepend"
										style="height: 1.5rem;">
										<span class="input-group-text Order-group-text"
											style="padding: 0px 0.1875rem 0px 0.1875rem;">매입가</span>
									</div>
									<input type="number" class="form-control Orderhistory-u"
										style="display: none;" name="price" placeholder="0" min="0"
										step="10" value="0">
								</div>
								<div class="input-group Order-group col-md-12 mt-2">
									<div class="input-group-prepend"
										style="padding: 0 0 0 0; height: 1.5rem;">
										<label class="input-group-text Order-group-text">거래처</label>
									</div>
									<select
										class="custom-select Orderhistory-Select-u Orderhistory-u"
										name="company.id">
										<option value="0" selected="selected">없음</option>
									</select>
								</div>

								<div class="input-group Order-group col-md-12 mt-2">
									<div class="input-group-prepend"
										style="padding: 0 0 0 0; height: 1.5rem;">
										<span class="input-group-text Order-group-text">주문일</span>
									</div>
									<input type="date" class="form-control Orderhistory-u"
										style="height: 1.5rem;" name="orderDate">
								</div>
								<div class="input-group Order-group col-md-12 mt-2">
									<div class="input-group-prepend"
										style="padding: 0 0 0 0; height: 1.5rem;">
										<span class="input-group-text Order-group-text">도착 예정일</span>
									</div>
									<input type="date" class="form-control Orderhistory-u"
										style="height: 1.5rem;" name="plannedArriveDate">
								</div>
								<div class="input-group Order-group col-md-12 mt-3" >

									<button type="button" class="btn btn-outline-secondary"
										id="update-addOrder" style="padding: 0 1 0 1; width: 100%;">수정
										내용 반영</button>
									<button type="button" class="btn btn-outline-secondary"
										id="update-createOrder" style="display: none;">제품
										추가</button>

								</div>
							</div>

							<div class="card-footer" style="padding: 1px 1px 1px 1px;">

								<button type="button"
									class="btn btn-block btn-secondary float-right"
									style="padding: 0 1 0 1;" id="orders-update">수정</button>
							</div>
						</div>

					</div>
					<div class="tab-pane fade " id="Orderhistory-delete">
						<div class=" card border border-secondary mb-2 ">
							<div class="card-body" style="padding: 0px 0px 0px 0px">
								<table class="table mb-0 table-hover table-condensed">
									<thead
										style="padding-top: 0px; padding-bottom: 0px; font-size: 0.5rem;">
										<tr>
											<!-- <th style="width: 9.97%;">LOTNO</th> -->
											<th style="width: 50%;">문서 번호</th>
											<th style="width: 47%;">문서 제목</th>
											<th style="width: 3%; padding: 0 0 0 0;"><i
												class='bx bxs-trash btn deleteRow'></i></th>

										</tr>
									</thead>
								</table>
								<div style="height: 10rem; overflow: scroll;">
									<table
										class="table table-condensed table-striped table-bordered mb-0 table-hover">
										<tbody class="p0" id="DocDeleteLists">
										</tbody>
									</table>
								</div>
							</div>
							<div class="card-footer" style="padding: 1px 1px 1px 1px;">

								<button type="button"
									class="btn btn-block btn-secondary float-right"
									style="padding: 0 1 0 1;" id="document-delete">발주 삭제</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>