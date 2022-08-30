<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">KPI</span>
		<div class="input-group search-group float-right  " style="width:55%">
		<span class="input-group-text search" id="kpiExcelExport">Excel Export</span>
			<select class="custom-select search-select w-20"
				style="height: 3.183023872679045vh;" id="kpiLineSearch">
				<option value="0" selected="selected">전체 출력기</option>
				<c:forEach items="${printers}" var="item">
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
			</select> 
			<input type="date" class="form-control"
				style="height: 3.183023872679045vh;" id="kpiPreDate"> 
				<span style="color: black; padding: 0px 10px 0px 10px"> ~ </span> 
				<input
				type="date" class="form-control"
				style="height: 3.183023872679045vh;" id="kpiLastDate"> 
				<span
				class="input-group-text search" id="kpiSearch"><i
				class='bx bx-search bx-sm'></i></span>
		</div>
	</div>
	<div>
		<div class="input-group search-group float-right smooth-scroll"
			style="padding-left: 0px; padding-right: 0px;; width: 80%">
			<div class="input-group-prepend">
				<span class="input-group-text regi-group-text">전체 작업지시 건수</span>
			</div>
			<input type="text" class="form-control register-control regi"
				style="padding-left: 0px; padding-right: 0px; width: 15%; text-align: center;"
				id="kpiTotalcount" placeholder="전체 작업지시 건수" readonly>
			<div class="input-group-prepend">
				<span class="input-group-text regi-group-text">작업지시 수정 횟수</span>
			</div>
			<input type="text" class="form-control register-control regi"
				style="padding-left: 0px; padding-right: 0px; width: 15%; text-align: center;"
				id="kpiErrorcount" placeholder="전체 작업지시 건수" readonly>
			<div class="input-group-prepend">
				<span class="input-group-text regi-group-text">작업지시 수정률</span>
			</div>
			<input type="text" class="form-control register-control regi"
				style="padding-left: 0px; padding-right: 0px; width: 15%; text-align: center;"
				id="kpiAverage" placeholder="수정 백분율" readonly>
			<div class="input-group-prepend">
				<span class="input-group-text regi-group-text">평균 리드타임(일)</span>
			</div>
			<input type="text" class="form-control register-control regi"
				style="padding-left: 0px; padding-right: 0px; width: 15%; text-align: center;"
				id="kpiAverageLead" placeholder="평균 리드타임" readonly>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel ">
		<div class="row no-gutters" style="padding: 1px 1px 1px 1px;">
			<div class="card col-md-4" id="error_chart">
				<div class="card-header"
					style="font-size: 0.2rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
					<b class="card-title">오류별 작업지시 수정 발생 건수</b>
				</div>
				<div class="card-body p-0">
					<div id="error_linechart_area"></div>
					<div id="error_ctrl_area"></div>
				</div>
				<div class="card-header"
					style="font-size: 0.2rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
					<b class="card-title">출력기별 작업지시 수정 발생 건수</b>
				</div>
				<div class="card-body p-0">
					<div id="printer_linechart_area"></div>
					<div id="printer_ctrl_area"></div>
				</div>
			</div>
			<div class="card col-md-8" id="kpi_chart">
				<div class="card-header"
					style="font-size: 0.2rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
					<b class="card-title">완료 작업지시 목록</b>
				</div>
				<div class="card-body" style="padding: 0px 0px 0px 0px;">
					<div>
						<table
							class="table table-condensed table-striped table-bordered mb-0 table-hover "
							id="kpiTable">
							<thead id="kpiInfoName">
								<tr>
									<th width="5%">No.</th>
									<th width="15%">Title</th>
									<th width="10%">브랜드</th>
									<th width="10%">지점</th>
									<th width="10%">품목</th>
									<th width="10%">출력기</th>
									<th width="10%">수정횟수</th>
									<th width="10%">수주일</th>
									<th width="10%">완료일</th>
									<th width="10%">리드타임(일)</th>
								</tr>
							</thead>
						</table>
					</div>
					<div
						style="height: 98vh; overflow: auto; padding: 0 0 0 0;">
						<table
							class="table table-condensed table-striped table-bordered mb-0 table-hover">
							<tbody id="kpiContents">

							</tbody>
						</table>
					</div>
					<div id="testArea">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
