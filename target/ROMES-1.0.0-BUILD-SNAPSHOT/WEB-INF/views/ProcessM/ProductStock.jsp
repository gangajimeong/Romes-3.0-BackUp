<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card  border-secondary mr-0" id="main">
	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">재고 현황</span>

	</div>

	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class="mt-0 table-panel col-12 border border-scondary "
				style="padding: 1px 1px 1px 1px;">
				<div>
					<div class="input-group search-group float-right w-100 "
						style="padding-bottom: 10px">

						<div class="input-group Order-group col-md-12 mt-2">
							<select class="custom-select search-select w-20"
								style="height: 3.183023872679045vh; margin: 0px 10px 0px 10px"
								id="productstock-type-select">
								<option value=0>전체</option>
								<option value=1>완제품</option>
								<option value=2>원부자재</option>
							</select> <select class="custom-select search-select w-20"
								style="height: 3.183023872679045vh;" id="productstock-select">
								<option value=3>제품명</option>
								<option value=-1>위치</option>

							</select> <input type="text" class="form-control search-control"
								style="height: 3.183023872679045vh; margin-right: 2%"
								id="productstock-searchText">
						</div>


					</div>
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover "
						id="srhistory-Table">
						<thead id="productstock-ColName">
							<tr>
								<!-- <th style="width: 9.97%;">LOTNO</th> -->
								<th style="width: 5%;">No</th>
								<th style="width: 20%;">Type</th>
								<th style="width: 20%;">제품명</th>
								<th style="width: 15%;">재고량</th>
								<th style="width: 15%;">적정 재고량</th>
								<th style="width: 15%;">여유 재고량</th>
							</tr>
						</thead>
					</table>
				</div>
				<div style="overflow: auto; height: 78vh">
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover">
						<tbody id="product-stock-table-Contents">
							<c:forEach items="${lists}" var="item" varStatus="status">
								<tr name="contents">
									<td style="display: none;">${item.id}</td>
									<td style="width: 5%;">${status.index+1}</td>
									<td style="width: 20%;">${item.type}</td>
									<td style="width: 20%;">${item.name}</td>
									<td style="width: 15%;">${item.quentity}</td>
									<td style="width: 15%;">${item.properquentity}</td>
									<td style="width: 15%;" class="spare">${item.spare}</td>
									<td style="display: none;">${item.url}</td>
									<td style="display: none;">${item.locations}</td>
								</tr>

<%-- 								<c:forEach begin="0" end="3" step="1" varStatus="status"> --%>
<%-- 									<tr name="${item.id}" style="display: none;"> --%>
<!-- 										<td></td> -->
<%-- 										<td colspan="2">test${status.index}:${item.id}</td> --%>
<!-- 										<td colspan="2">test</td> -->
<!-- 									</tr> -->
<%-- 								</c:forEach> --%>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>

		</div>
	</div>
</div>