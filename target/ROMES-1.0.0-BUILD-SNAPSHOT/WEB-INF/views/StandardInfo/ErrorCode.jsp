<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main" >

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">불량 코드 관리</span>
		<div class="input-group search-group float-right  ">
			<div class="input-group-prepend">
				<select class="custom-select search-select" id="ErrorCodeInfoSearch"></select>
			</div>

			<input type="text" class="form-control search-control"
				id="ErrorCodeSearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9">
				<table id="ErrorCodeTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="ErrorCodeColName">
						<tr>

							<th style="width: 2%; display: none;" class="ErrorCodeChkRow">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										name="ErrorCodeChkAll">
								</div>
							</th>

							<th style="width: 18%">불량 코드</th>
							<th style="width: 20%">불량 유형</th>
							<th style="width: 40%">조치 내용</th>
							<th style="width: 20%">등록 날짜</th>
						</tr>
					</thead>
					<tbody id="ErrorCodeBody">
						<c:forEach var="ErrorCodes" items="${ErrorCodes}" varStatus="status">
							<tr class="${ErrorCodes.getId()}">
								<td class="ErrorCodeChkRow" style="display: none;">
									<div class="form-check">
										<input class="form-check-input position-static"
											type="checkbox" name="ErrorCodeChk"
											value="${ErrorCodes.getId()}">
									</div>
								</td>

								<td style="display: none;">${ErrorCodes.getId()}</td>
								<td>${ErrorCodes.getErrorCode()}</td>
								<td>${ErrorCodes.getErrorType()}</td>
								<td>${ErrorCodes.getErrorMeasure()}</td>
								<td>${ErrorCodes.getCreateDate()}</td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link ErrorCodeCreate active " data-toggle="tab"
							href="#ErrorCode-register">등록</a></li>
						<li class="nav-item"><a class="nav-link ErrorCodeUpdate"
							data-toggle="tab" href="#ErrorCode-update">수정</a></li>
						<li class="nav-item"><a class="nav-link ErrorCodeDelete"
							data-toggle="tab" href="#ErrorCode-delete">삭제</a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="ErrorCode-register">
						<form action="createErrorCode" method="post">
							<div class="card border border-secondary mt-3">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">불량 코드</span>
										</div>
										<input type="text" class="form-control ErrorCode-c regi"
											name="errorCode" placeholder="불량 코드">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">불량 유형</span>
										</div>
										<input type="text" class="form-control ErrorCode-c regi"
											name="errorType" placeholder="불량 유형">
									</div>
									<div class="input-group regi-group">
										
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-textArea">조치 내용</span>
											</div>
											<textarea class="form-control ErrorCode-c regi" name="errorMeasure" placeholder="조치 내용"></textarea>
										
									</div>
								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1 " type="submit">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade" id="ErrorCode-update">
						<div class=" card border border-secondary mb-2">
							<table
								class="table table-bordered ErrorCode-update-list-table mb-0 table-hover regi-update sub-table">
								<thead>
									<tr>
										<th style="width: 20%">불량 코드</th>
										<th style="width: 20%">불량 유형</th>
										<th style="width: 40%">조치 내용</th>
										<th style="width: 20%">등록 날짜</th>
									</tr>
								</thead>
								<tbody class="ErrorCode-update-list">
									<tr style="display: none;">
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="card border border-secondary mt-3">
							<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">불량 코드</span>
										</div>
										<input type="text" class="form-control ErrorCode-u regi"
											name="errorCode" placeholder="불량 코드">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">불량 유형</span>
										</div>
										<input type="text" class="form-control ErrorCode-u regi"
											name="errorType" placeholder="불량 유형">
									</div>
									<div class="input-group regi-group">
										
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-textArea">조치 내용</span>
											</div>
											<textarea class="form-control ErrorCode-u regi" name="errorMeasure" placeholder="조치 내용"></textarea>
									</div>
								</div>
							<div class="card-footer">
								<button class="btn regi-btn mt-1 mb-1" id="update-ErrorCode-btn"
									type="button">수정</button>
							</div>
						</div>

					</div>
					<div class="tab-pane fade show" id="ErrorCode-delete">
						<div class=" card border border-secondary mb-2 ">
							<table class="table table-bordered mb-0 regi-delete sub-table">
								<thead>
									<tr>
										<th style="width: 20%">불량 코드</th>
										<th style="width: 20%">불량 유형</th>
										<th style="width: 30%">조치 내용</th>
										<th style="width: 19%">등록 날짜</th>
										<th style="width: 11%">삭제</th>
									</tr>
								</thead>
								<tbody class="ErrorCode-delete-list">
									<tr style="display: none;">
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
										<td>-</td>
									</tr>

								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>