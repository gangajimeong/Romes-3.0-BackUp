<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<!-- 부서관리코드참조 -->
<div class="card mr-0" id="main">

	<div class="card-header card-headerMain border-secondary">
		<span class="badge badge-dark page-title">작업보고서</span>
		<div class="input-group search-group float-right  "></div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-7">
				<table
					class="table table-condensed table-striped table-bordered mb-0 table-hover "
					style="table-layout: fixed;">
					<thead id="WorkReportColName">
						<tr>
							<th style="width: 2%; display: none;" class="ChkRow">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										name="ChkAll">
								</div>
							</th>
							<th style="width: 5%">No</th>
							<th style="width: 15%">작성자</th>
							<th style="width: 15%">타입</th>
							<th style="width: 25%">제목</th>
							<th style="width: 40%">보고내용</th>

						</tr>
					</thead>
					<tbody id="WorkReportBody">
						<c:forEach var="Report" items="${Reports}" varStatus="status">
							<tr>
								<td style="display: none;">
									<div class="form-check">
										<input class="form-check-input position-static"
											type="checkbox" name="Chk">
									</div>
								</td>

								<td style="display: none;">${Report.id}</td>
								<td>${status.count}</td>
								<td>${Report.name}</td>
								<td>${Report.type}</td>
								<td>${Report.title}</td>
								<td
									style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${Report.report}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-5">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link WorkReportCreate active " data-toggle="tab"
							href="#WorkReport-register">등록</a></li>
						<li class="nav-item"><a class="nav-link WorkReportUpdate"
							data-toggle="tab" href="#WorkReport-update">수정/삭제</a></li>
						<li class="nav-item"><a class="nav-link WorkReportShow"
							data-toggle="tab" href="#WorkReport-showlist">조회</a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="WorkReport-register">
						<form action="createReportWork" method="post">
							<div class="card border border-secondary mt-3">
								<div class="card-body regi-input-body">
									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">타입</label>
										</div>
										<select class="form-control WorkReport-c regi" name="repoType"
											style="padding: 0;">
											<option value="일간보고">일간보고</option>
											<option value="주간보고">주간보고</option>
											<option value="월간보고">월간보고</option>
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">제목</span>
										</div>
										<input type="text" class="form-control WorkReport-c regi"
											name="title">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text" style="border-radius: 3px;">보고내용</span>
										</div>
									</div>
									<textarea rows="15" cols="10"
										class="form-control WorkReport-c regi" name="Report"></textarea>
								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1 " type="submit">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade" id="WorkReport-update">
						<div class="row no-gutters">
							<div class="col-md-12 col-sm-12 col-lg-12">
								<div class="float-right">
									<button type="button"
										class="btn btn-secondary border border-light"
										style="padding: 7px 14px;"
										id="deleteWorkReport">
										<b class="card-title">삭제</b>
									</button>
								</div>
							</div>
						</div>
						<form id="update-WorkReport-list">
							<div class="card border border-secondary mt-1">
								<div class="card-body regi-input-body">

									<s:csrfInput />
									<input type="hidden" class="WorkReport-u" name="id">
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<label class="input-group-text regi-group-text">타입</label>
										</div>
										<select class="form-control WorkReport-u regi" name="repoType"
											style="padding: 0;">
											<option value="일간보고">일간보고</option>
											<option value="주간보고">주간보고</option>
											<option value="월간보고">월간보고</option>
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">제목</span>
										</div>
										<input type="text" class="form-control WorkReport-u regi"
											name="title">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text" style="border-radius: 3px;">보고내용</span>
										</div>
									</div>
									<textarea rows="15" cols="10"
										class="form-control WorkReport-u regi" name="Report"></textarea>

								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1"
										id="update-WorkReport-btn" type="button">수정</button>
								</div>
							</div>
						</form>
					</div>
					<div class="tab-pane fade" id="WorkReport-showlist">
						<div class="card border border-secondary mt-3">
							<div class="card-body regi-input-body">

								<s:csrfInput />
								<input type="hidden" class="WorkReport-s" name="id">
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<label class="input-group-text regi-group-text">타입</label>
									</div>
									<select class="form-control WorkReport-s regi" name="repoType"
										style="padding: 0;" disabled>
										<option>일간보고</option>
										<option>주간보고</option>
										<option>월간보고</option>
									</select>
								</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text">작성일</span>
									</div>
									<input type="date" class="form-control WorkReport-s regi"
										name="time" disabled style="border-radius: 0 3px 3px 0;">

									<div class="input-group-prepend ml-2" >
										<span class="input-group-text regi-group-text" style="border-radius: 3px;">작성자</span>
									</div>
									<input type="text" class="form-control WorkReport-s regi" name="user_id" disabled>
								</div>
								<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">제목</span>
										</div>
										<input type="text" class="form-control WorkReport-s regi" name="title" disabled>
									</div>
								<div class="input-group regi-group">
									<div class="input-group-prepend">
										<span class="input-group-text regi-group-text" style="border-radius: 3px;">보고내용</span>
									</div>
								</div>
								<textarea rows="15" cols="10" class="form-control WorkReport-s regi" name="Report" disabled></textarea>

							</div>

						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>