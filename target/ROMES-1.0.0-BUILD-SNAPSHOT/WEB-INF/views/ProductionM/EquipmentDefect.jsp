<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main" >

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">설비별 불량</span>
		<div class="input-group search-group float-right  ">
			<div class="input-group-prepend">
				<select class="custom-select search-select" id="EquipmentDefectInfoSearch"></select>
			</div>

			<input type="text" class="form-control search-control"
				id="EquipmentDefectSearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9">
				<table id="EquipmentDefectTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="EquipmentDefectColName">
						<tr>
							<th style="width: 5%">No</th>
							<th style="width: 20%">설비명</th>
							<th style="width: 20%">불량유형</th>
							<th style="width: 20%">처리자</th>
							<th style="width: 30%">등록시간</th>
						</tr>
					</thead>
					<tbody id="EquipmentDefectContents">
						<c:forEach var="ed" items="${equipmentDefects}" varStatus="status">
							<tr>
								<td style="display: none;">${ed.id}</td>
								<td>${status.count}</td>
								<td style="display: none;">${ed.lineId}</td>
								<td>${ed.line}</td>
								<td style="display: none;">${ed.errorId}</td>
								<td>${ed.errorType}</td>
								<td>${ed.user}</td>
								<td>${ed.time}</td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a class="nav-link equipmentDefectUpdate active"
							data-toggle="tab" href="#equipmentDefect-update">수정/삭제</a></li>
					</ul>
				</div>
				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="equipmentDefect-update">
							<div class="row no-gutters">
								<div class="col-md-12 col-sm-12 col-lg-12">
									<div class="float-right">
										<button type="button"
											class="btn btn-secondary border border-light"
											style="padding-top: 2px; padding-bottom: 2px;"
											id="deleteEquipmentDefect">
											<b class="card-title">삭제</b>
										</button>
									</div>
								</div>
							</div>
						<div class="card border border-secondary mt-1">
							<div class="card-body regi-input-body">
									<form id = "update-equipmentDefect">
										<s:csrfInput />
										<input type="hidden" class="equipmentDefect-u" name="id">
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-text">설비명</span>
											</div>
											<select class="form-control equipmentDefect-u regi"
												name="order.printer.id" style="padding:0;">
												<c:forEach var="item" items="${lines}">
													<option value="${item.id}">${item.line}</option>
												</c:forEach>
											</select>
										</div>
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-text">불량 유형</span>
											</div>
											<select class="form-control equipmentDefect-u regi"
												name="errorCode.id" style="padding:0;">
												<c:forEach var="item" items="${errorCode}">
													<option value="${item.id}">${item.type}</option>
												</c:forEach>
											</select>
										</div>
									</form>
								</div>
							<div class="card-footer">
								<button class="btn regi-btn mt-1 mb-1" id="update-equipmentDefect-btn"
									type="button">수정</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>