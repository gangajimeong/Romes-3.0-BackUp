<%@ page pageEncoding="utf-8" session="false"%>
<div class="card  border-secondary mr-0" id ="main">

	<div class="card-header card-headerMain  border-secondary">
		<div class="row no-gutters">
			<div class="col-3">
				<h2>
					<span class="badge badge-dark mt-2 ml-2">매입품 관리</span>
				</h2>
			</div>
			<div class="col-9">

				<div class="input-group float-right mt-2 mr-1">
					<div class="input-group-prepend">
						<select class="custom-select">
							<option selected>Choose...</option>
							<option value="1">매입품</option>
							<option value="2">Info</option>
							<option value="3">거래처</option>
							<option value="3">단가</option>
							
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
				<button class="btn btn-secondary">수정</button>
			</div>
		</div>
		<div class="card mt-1 border-secondary mt-1 tablePanel">
			<div class="card-body  border-dark tableSetting">
				<table class="table table-striped table-bordered mb-0 table-hover">
					<thead>
						<tr>
							<th>매입품</th>
							<th>Info</th>
							<th>거래처</th>
							<th>Info</th>
							<th>단가</th>
						</tr>
					</thead>
					<tbody >
					<%for(int i =0; i<30;i++){  %>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<%} %>
					</tbody>
				</table>
			</div>
		</div>
	</div>

</div>

