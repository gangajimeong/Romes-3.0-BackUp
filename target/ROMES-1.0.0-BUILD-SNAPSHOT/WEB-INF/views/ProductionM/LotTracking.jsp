<%@ page pageEncoding="utf-8" session="false"%>
<div class="card  border-secondary mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<div class="row no-gutters">
			<div class="col-12">
				<h2>
					<span class="badge badge-dark mt-2 ml-2">LOT 추적</span>
				</h2>
			</div>
			
		</div>
	</div>

	<div class="card-body  border-secondary mainPanel">

		<div class="card border-secondary">
			<div class="card-body btnPanel">
				<div class="input-group" style="width: 30%;">
					<div class="input-group-prepend">
						<span class="input-group-text">LOT 번호</span>
					</div>
					<input type="text" class="form-control">
					<div class="input-group-append ">
						<button class="btn btn-secondary ">조회</button>
					</div>

				</div>
			</div>
		</div>
		<div class="card mt-1 border-secondary tablePanel">
			<div class="card-body  border-dark OMtableSetting">
				<table
					class="table table-striped table-bordered table-condensed table-hover mb-0 x-scrollTable">
					<thead>
						<tr>
							<th>Lot 분류</th>
						</tr>

					</thead>
					<tbody>
						<%
						for (int i = 0; i < 29; i++) {
						%>
						<tr>
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

