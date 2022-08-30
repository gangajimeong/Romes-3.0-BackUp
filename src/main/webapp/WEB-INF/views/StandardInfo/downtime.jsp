<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">비가동 시간</span>
		<div class="input-group search-group float-right  ">
			<div class="input-group-prepend">
				<select class="custom-select search-select" id="DownTimeInfoSearch"></select>
			</div>

			<input type="search" class="form-control search-control"
				id="downTimeSearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9">
				<table id="downTimeTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="DownTimeColName">
						<tr>

							<th style="width: 2%; display: none;" class="DownTimeChkRow">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										name="DownTimeChkAll">
								</div>
							</th>

							<th style="width: 25%">Title</th>
							<th style="width: 25%">시작시간</th>
							<th style="width: 20%">종료시간</th>
							<th style="width: 25%">비고</th>
						</tr>
					</thead>
					<tbody id="commonBody">
						<c:forEach var="downTimes" items="${downTimes}"
							varStatus="status">
							<tr class = "${downTimes.getId()}">
								<td class="DownTimeChkRow" style="display: none;">
									<div class="form-check">
										<input class="form-check-input position-static"
											type="checkbox" name="DownTimeChk"
											value="${downTimes.getId()}">
									</div>
								</td>

								<td style="display: none;">${downTimes.getId()}</td>
								<td>${downTimes.getTitle()}</td>
								<td>${downTimes.getStartTimeToString()}</td>
								<td>${downTimes.getDownTimeToString()}</td>
								<td>${downTimes.getRemark()}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a class="nav-link DownTimeCreate active "
							data-toggle="tab" href="#DownTime-register">등록</a></li>
						<li class="nav-item"><a class="nav-link DownTimeUpdate"
							data-toggle="tab" href="#DownTime-update">수정</a></li>
						<li class="nav-item"><a class="nav-link DownTimeDelete"
							data-toggle="tab" href="#DownTime-delete">삭제</a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="DownTime-register">
						<form action="createDownTime" method="post">
							<div class="card border border-secondary mt-3">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">Title</span>
										</div>
										<input type="text" class="form-control DT-control regi"
											name="title" placeholder="Title">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">시작시간</span>
										</div>
										<input type="time" class="form-control DT-control regi"
											name="startTime" placeholder="시작시간" >
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">종료시간</span>
										</div>
										<input type="time" class="form-control DT-control regi"
											name="downTime" placeholder="종료시간">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">비고</span>
										</div>
										<input type="text" class="form-control DT-control regi"
											name="remark" placeholder="비고">
									</div>


								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1" type="submit">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade show" id="DownTime-update">
						<div class=" card border border-secondary mb-2">
							<table class="table table-bordered CommonCode-update-list-table mb-0 table-hover regi-update sub-table">
								<thead>
									<tr>
										<th style="width: 25%">Title</th>
										<th style="width: 25%">시작시간</th>
										<th style="width: 20%">종료시간</th>
										<th style="width: 25%">비고</th>
									</tr>
								</thead>
								<tbody class="DownTime-update-list">
									<tr style="display: none;">
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<form  method="get">
							<div class="card border border-secondary">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">Title</span>
										</div>
										<input type="text" class="form-control DT-u-control regi"
											name="title" placeholder="Title">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">시작시간</span>
										</div>
										<input type="time" class="form-control DT-u-control regi"
											name="startTime" placeholder="시작시간">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">종료시간</span>
										</div>
										<input type="time" class="form-control DT-u-control regi"
											name="downTime" placeholder="종료시간">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text  regi-group-text">비고</span>
										</div>
										<input type="text" class="form-control DT-u-control regi"
											name="remark" placeholder="비고">
									</div>


								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1 " type="button" id ="update-DownTime-btn">수정</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade show" id="DownTime-delete">
						<div class=" card border border-secondary mb-2 ">
							<table class="table table-bordered mb-0 regi-delete sub-table">
								<thead>
									<tr>
										<th style="width: 25%">Title</th>
										<th style="width: 25%">시작시간</th>
										<th style="width: 20%">종료시간</th>
										<th style="width: 22%">비고</th>
										<th style="width: 3%"><i class='bx bxs-trash btn DT-delete-checkBox'></i></th>
									</tr>
								</thead>
								<tbody class="DownTime-delete-list">
									<tr style="display: none;">
											<td>-</td>
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