<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">CAPA</span>
		<div class="input-group search-group float-right" style="width:40%" >
			<span class="input-group-text search" id="capaExcelExport">Excel Export</span>
			<select class="custom-select search-select w-20"
				style="height: 3.183023872679045vh;"
				id="capaUserSearch">
				<option value="0" selected="selected">전체</option>
				<c:forEach items="${lines}" var="item" >
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
			</select> 
			<input type="date"
				class="form-control" style="height: 3.183023872679045vh;"
				id="capaPreDate">
			<span style="color: black; padding: 0px 10px 0px 10px"> ~ </span>
			<input type="date" class="form-control"
				style="height: 3.183023872679045vh;" id="capaLastDate">
			<span class="input-group-text search" id = "capaSearch"><i
								class='bx bx-search bx-sm'></i></span>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters" style="padding: 1px 1px 1px 1px;">
			<div class="card col-md-6 mt-0 table-panel"
				style="padding: 1px 1px 1px 1px; border-radius: 0px 0px 0px 0px;">
				<div class="card-header"
					style="font-size: 0.2rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
					<b class="card-title">완료 작업지시 목록</b>
				</div>

				<div class="card-body" style="padding: 0px 0px 0px 0px;">
					<span style="display: none" id="capa-data">${chartdata}</span>
					<div>
						<table
							class="table table-condensed table-striped table-bordered mb-0 table-hover "
							id="capaWorkInfoTable">
							<thead id="capaWorkInfoName">
								<tr>
									<th width="5%">No.</th>
									<th width="10%">Title</th>
									<th width="10%">이름</th>
									<th width="10%">브랜드</th>
									<th width="15%">지점</th>
									<th width="25%">품목</th>
									<th width="25%">완료일</th>
								</tr>
							</thead>
						</table>
					</div>
					<div
						style="height: 80.31746031746032vh; overflow: auto; padding: 0 0 0 0;">
						<table
							class="table table-condensed table-striped table-bordered mb-0 table-hover">
							<tbody id="capaWorkInfoContents">
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<div class="card col-md-6" id="Capa_chart">
				<div class="card-header"
					style="font-size: 0.2rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
					<b class="card-title">월별 실적 그래프</b>
				</div>
				<div class="card-body p-0">
					<div id="Capa_linechart_area"></div>
					<div id="Capa_ctrl_area"></div>
				</div>
			</div>
		</div>
	</div>
</div>
