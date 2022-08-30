<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">생산 라인 관리</span>
		<div class="input-group search-group float-right  ">
			<div class="input-group-prepend">
				<select class="custom-select search-select"
					id="WorkingLineInfoSearch"></select>
			</div>

			<input type="text" class="form-control search-control"
				id="WorkingLineSearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9">
				<table id="WorkingLineTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="WorkingLineColName">
						<tr>
							<th style="width: 2%; display: none;" class="WorkingLineChkRow">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										name="WorkingLineChkAll">
								</div>
							</th>
							<th style="width: 15%">구분</th>
							<th style="width: 15%">라인명</th>
							<th style="width: 25%">비고</th>
							<th style="width: 15%">작업량</th>
							<th style="width: 15%">자재소요량</th>
							<th style="width: 15%">위치</th>

						</tr>
					</thead>
					<tbody id="WorkingLineBody">
						<c:forEach var="line" items="${line}"
							varStatus="status">
							<tr class="${line.id}">
								<td class="WorkingLineChkRow" style="display: none;">
									<div class="form-check">
										<input class="form-check-input position-static"
											type="checkbox" name="WorkingLineChk"
											value="${line.id}">
									</div>
								</td>

								<td style="display: none;">${line.id}</td>
								<td>${line.type}</td>
								<td>${line.line}</td>
								<td>${line.remark}</td>
								<td>${line.workload}</td>
								<td>${line.mRequirement}</td>
								<td style="display: none;">${line.locaId}</td>
								<td>${line.locate}</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link WorkingLineCreate active " data-toggle="tab"
							href="#WorkingLine-register">등록</a></li>
						<li class="nav-item"><a class="nav-link WorkingLineUpdate"
							data-toggle="tab" href="#WorkingLine-update">수정/삭제</a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="WorkingLine-register">
						<form action="createWL" method="post">
							<div class="card border border-secondary mt-3">
								<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">타입</span>
										</div>
										<select class="form-control WorkingLine-c regi" style="padding:0;" name="type">
											<option value="출력라인">출력라인</option>
											<option value="제작라인">제작라인</option>
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">라인명</span>
										</div>
										<input type="text" class="form-control WorkingLine-c regi"
											name="line" placeholder="라인명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">비고</span>
										</div>
										<input type="text" class="form-control WorkingLine-c regi"
											name="remarks" placeholder="비고">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">작업량</span>
										</div>
										<input type="number" class="form-control WorkingLine-c regi"
											name="workload" placeholder="시간당 작업량">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">자재소요량</span>
										</div>
										<input type="number" class="form-control WorkingLine-c regi"
											name="mRequirement" placeholder="자재소요량">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">위치</label>
										</div>
										<select class="custom-select WorkingLine-c regi"
											name="location.id">
											<c:forEach var="item" items="${location}">
												<option value="${item.id}" selected="selected">${item.name}</option>
											</c:forEach>
										</select>
									</div>
									
								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1 " type="submit">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade" id="WorkingLine-update">
						<div class="row no-gutters">
							<div class="col-md-12 col-sm-12 col-lg-12">
								<div class="float-right">
									<button type="button"
										class="btn btn-secondary border border-light"
										style="padding-top: 2px; padding-bottom: 2px;"
										id="deleteWorkingLine">
										<b class="card-title">삭제</b>
									</button>
								</div>
							</div>
						</div>
						<div class="card border border-secondary mt-1">
							<div class="card-body regi-input-body">
								<form id = "update-workingline">
									<s:csrfInput />
									<input type="hidden" class="WorkingLine-u" name="id">
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">타입</span>
										</div>
										<select class="form-control WorkingLine-u regi" style="padding:0;" name="type">
											<option value="출력라인">출력라인</option>
											<option value="제작라인">제작라인</option>
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">라인명</span>
										</div>
										<input type="text" class="form-control WorkingLine-u regi"
											name="line" placeholder="라인명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">비고</span>
										</div>
										<input type="text" class="form-control WorkingLine-u regi"
											name="remarks" placeholder="비고">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">작업량</span>
										</div>
										<input type="number" class="form-control WorkingLine-u regi"
											name="workload" placeholder="시간당 작업량">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">자재소요량</span>
										</div>
										<input type="number" class="form-control WorkingLine-u regi"
											name="mRequirement" placeholder="자재소요량">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">위치</label>
										</div>
										<select class="custom-select WorkingLine-Select-u WorkingLine-u regi" id = "workingline-update-location"
											name="location.id">
											<c:forEach var="item" items="${location}">
												<option value="${item.id}">${item.name}</option>
											</c:forEach>
										</select>
									</div>
									
								</form>
							</div>
							<div class="card-footer">
								<button class="btn regi-btn mt-1 mb-1"
									id="update-WorkingLine-btn" type="button">수정</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>