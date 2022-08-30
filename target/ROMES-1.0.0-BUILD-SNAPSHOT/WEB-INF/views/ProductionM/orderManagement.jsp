<%@ page pageEncoding="utf-8" session="false"%>
<div class="card  border-secondary mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<div class="row no-gutters">
			<div class="col-3">
				<h2>
					<span class="badge badge-dark mt-2 ml-2">수주 관리</span>
				</h2>
			</div>
			<div class="col-9">

				<div class="input-group float-right mt-2 mr-1">
					<div class="input-group-prepend">
						<select class="custom-select">
							<option selected>Choose...</option>
							<option value="1">수주 번호</option>
							<option value="2">수주명</option>
							<option value="3">담당자</option>
							<option value="3">주문 업체</option>
							<option value="1">주문 날짜</option>
							<option value="2">완료 기한</option>
							<option value="3">계약 유형</option>
							<option value="3">진행 현황</option>
						</select>
					</div>
					<input type="text" class="form-control"
						aria-label="Text input with dropdown button">
					<div class="input-group-append">
						<button class="btn btn-secondary" type="button">검색</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="card-body  border-secondary mainPanel">

		<div class="card border-secondary">
			<div class="card-body btnPanel">
				<button class="btn btn-secondary ">등록</button>
				<button class="btn btn-secondary">견적서 발송</button>
				<button class="btn btn-secondary">Excel Export</button>
			</div>
		</div>
		<div class="card mt-1 border-secondary tablePanel">
			<div class="card-body  border-dark OMtableSetting">
				<table
					class="table table-striped table-bordered table-hover mb-0 ">
					<thead>
						<tr>
							<th rowspan="2">수주 번호</th>
							<th rowspan="2">Info</th>
							<th rowspan="2">수주명</th>
							<th rowspan="2">담당자</th>
							<th rowspan="2">주문 업체</th>
							<th rowspan="2">주문 날짜</th>
							<th rowspan="2">완료 기한</th>
							<th rowspan="2">계약 유형</th>
							<th colspan="7">진행 현황</th>
						</tr>
						<tr>
							<th>주문접수</th>
							<th>생산계획</th>
							<th>작업지시</th>
							<th>생산완료</th>
							<th>출하대기</th>
							<th>부분출하</th>
							<th>출하완료</th>

						</tr>
					</thead>
					<tbody>
						<%
						for (int i = 0; i < 29; i++) {
						%>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>

</div>

