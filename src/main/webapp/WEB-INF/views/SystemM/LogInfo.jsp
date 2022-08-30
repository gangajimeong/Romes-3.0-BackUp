<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card  border-secondary mr-0" id="main">
	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">로그 기록</span>

	</div>

	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class="mt-0 table-panel col-12 border border-scondary "
				style="padding: 1px 1px 1px 1px;">
				<div>
					<div class="input-group search-group float-right  "
						style="width: 60%">
						<select class="custom-select search-select w-20"
							style="height: 3.183023872679045vh;" id="logUserSearch">
							<option value="0" selected="selected">전체 사용자</option>
							<c:forEach items="${users}" var="item">
								<option value="${item.id}">${item.name}</option>
							</c:forEach>
						</select> <select class="custom-select search-select w-20"
							style="height: 3.183023872679045vh;" id="logCategorySearch">
							<option value="0" selected="selected">전체 카테고리</option>
							<option value="Android">Android</option>
							<option value="Manager">Manager</option>
						</select> <input type="date" class="form-control"
							style="height: 3.183023872679045vh;" id="logPreDate"> <span
							style="color: black; padding: 0px 10px 0px 10px"> ~ </span> <input
							type="date" class="form-control"
							style="height: 3.183023872679045vh;" id="logLastDate"> <span
							class="input-group-text search" id="logSearch"><i
							class='bx bx-search bx-sm'></i></span>
					</div>
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover "
						id="log-Table">
						<thead id="log-ColName">
							<tr>
								<!-- <th style="width: 9.97%;">LOTNO</th> -->
								<th style="width: 5%;">No</th>
								<th style="width: 10%;">사용자</th>
								<th style="width: 10%;">ID</th>
								<th style="width: 10%;">부서</th>
								<th style="width: 15%;">Category</th>
								<th style="width: 35%;">행동</th>
								<th style="width: 15%;">시간</th>
							</tr>
						</thead>
					</table>
				</div>
				<div style="overflow: auto; height: 78vh">
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover">
						<tbody id="loghistory-table-Contents">
							<c:forEach var="log" items="${log}" varStatus="state">
								<tr>
								<td style="width: 5%;">${state.count}</td>
								<td style="width: 10%;">${log.name}</td>
								<td style="width: 10%;">${log.loginId}</td>
								<td style="width: 10%;">${log.division}</td>
								<td style="width: 15%;">${log.category }</td>
								<td style="width: 35%;">${log.action }</td>
								<td style="width: 15%;">${log.time }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>

		</div>
	</div>
</div>