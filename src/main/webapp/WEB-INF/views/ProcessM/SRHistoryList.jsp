<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card  border-secondary mr-0" id="main">
	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">입고/출고</span>

	</div>

	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class="mt-0 table-panel col-12 border border-scondary "
				style="padding: 1px 1px 1px 1px;">
				<div>
					<div class="input-group search-group float-right w-100 " style="padding-bottom: 10px">

						<div class="input-group Order-group col-md-12 mt-2">
							<select class="custom-select search-select w-20" style="height: 3.183023872679045vh;margin:0px 10px 0px 10px"
								id="srhistory-type-select">
									<option value=0>전체</option>
									<option value=1>입고</option>
									<option value=2>출고</option>
							</select>
							<select class="custom-select search-select w-20" style="height: 3.183023872679045vh;"
								id="srhistory-select">
									<option value=3>제품명</option>
									<option value=4>위치</option>
									<option value=6>처리자</option>
							</select>
							<input type="text" class="form-control search-control" style="height: 3.183023872679045vh;margin-right: 2%"
								id="srhistory-searchText">
								
						 	<input type="date"
								class="form-control" style="height: 3.183023872679045vh;"
								id="srhistory-PreDate">
							<span style="color: black;padding: 0px 10px 0px 10px" > ~  </span> 
							<input type="date" class="form-control"
								style="height: 3.183023872679045vh;" id="srhistory-LastDate">
							<span class="input-group-text search"><i
								class='bx bx-search bx-sm'></i></span>
						</div>


					</div>
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover "
						id="srhistory-Table">
						<thead id="srhistory-ColName">
							<tr>
								<!-- <th style="width: 9.97%;">LOTNO</th> -->
								<th style="width: 5%;">No</th>
								<th style="width: 13%;">Type</th>
								<th style="width: 15%;">제품명</th>
								<th style="width: 13%;">위치</th>
								<th style="width: 13%;">개수</th>
								<th style="width: 13%;">처리자</th>
								<th style="width: 15%;">시간</th>
								<th style="width: 13%;">비고</th>
							</tr>
						</thead>
					</table>
				</div>
				<div style="overflow: auto; height: 78vh">
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover">
						<tbody id="srhistory-table-Contents">
							<c:forEach items="${lists}" var="item" varStatus="status">
								<tr >
									<td style="display: none;">${item.id}</td>
									<td style="width: 5%;">${status.index+1}</td>
									<td style="width: 13%;">${item.type}</td>
									<td style="width: 15%;">${item.productName}</td>
									<td style="width: 13%;">${item.location}</td>
									<td style="width: 13%;">${item.count}</td>
									<td style="width: 13%;">${item.user}</td>
									<td style="width: 15%;">${item.time}</td>
									<td style="width: 13%;">${item.remark}</td>
									<td style="display: none;">${item.lot}</td>							
												
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>
			
		</div>
	</div>
