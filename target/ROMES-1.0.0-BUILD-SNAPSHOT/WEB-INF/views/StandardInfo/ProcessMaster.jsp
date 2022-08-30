<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card border-secondary mr-0 processmaster" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<div class="row no-gutters">
			<div class="col-3">
				<h2>
					<span class="badge badge-dark mt-2 ml-2">공정 마스터</span>
				</h2>
			</div>
		</div>
	</div>

	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9">
				<table id="ProcessMasterTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="ProcessMasterColName">
						<tr>

							<th style="width: 2%; display: none;" class="ProcessChkRow">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										name="ProcessChkAll">
								</div>
							</th>

							<th style="width: 20%">code</th>
							<th style="width: 20%">공정명</th>
							<th style="width: 20%">전공정</th>
							<th style="width: 20%">후공정</th>
							<th style="width: 20%">세부공정</th>
						</tr>
					</thead>
					<tbody id="ProcessMasterBody">
						<c:forEach var="ProcessMasters" items="${ProcessMasters}">
							<tr>
								<td class="ProcessChkRow" style="display: none;">
									<div class="form-check">
										<input class="form-check-input position-static"
											type="checkbox" name="ProcessChk"
											value="${ProcessMasters.id}">
									</div>
								</td>

								<td style="display: none;">${ProcessMasters.id}</td>
								<td class="CodeId">${ProcessMasters.code}</td>
								<td>${ProcessMasters.name}</td>
								<td>${ProcessMasters.before}</td>
								<td>${ProcessMasters.after}</td>
								<td>${ProcessMasters.detail}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link ProcessCreate active " data-toggle="tab"
							href="#ProcessMaster-register">등록</a></li>
						<li class="nav-item"><a class="nav-link ProcessUpdate"
							data-toggle="tab" href="#ProcessMaster-update">수정/삭제</a></li>


					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="ProcessMaster-register">
						<form action="createProcessMaster" method="post">
							<div class="card border border-secondary mt-3">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">code</span>
										</div>
										<input type="text" class="form-control ProcessMaster-c regi "
											name="code" placeholder="코드번호">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">공정명</span>
										</div>
										<input type="text" class="form-control ProcessMaster-c regi"
											name="processName" placeholder="공정명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">전공정</span>
										</div>
										<select class="custom-select ProcessMaster-c regi regi-select"
											name="beforeProcess.id">
												<option value=0 selected="selected">없음</option>
											<c:forEach var="ProcessMasters" items="${ProcessMasters}"
												varStatus="num">
												<option value="${ProcessMasters.id}">${ProcessMasters.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">후공정</span>
										</div>
										<select class="custom-select ProcessMaster-c regi regi-select"
											name="afterProcess.id">
												<option value=0 selected="selected">없음</option>
											<c:forEach var="ProcessMasters" items="${ProcessMasters}"
												varStatus="num">
												<option value="${ProcessMasters.id}">${ProcessMasters.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">세부공정</span>
										</div>
										<input type="text" class="form-control ProcessMaster-c regi"
											name="detailProcess" placeholder="세부공정">
									</div>


								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1" type="submit">등록</button>
								</div>
							</div>
						</form>

					</div>
					<div class="tab-pane fade show" id="ProcessMaster-update">
						<div class="card-header"
							style="font-size: 0.8rem; padding: 2px 2px 2px 2px;">
							<div class="row no-gutters">
								<div class="col-md-12 col-sm-12 col-lg-12">
									<div class="float-right">
										<button type="button"
											class="btn btn-secondary border border-light"
											style="padding-top: 2px; padding-bottom: 2px;"
											id="deleteProcess">
											<b class="card-title">삭제</b>
										</button>
									</div>
								</div>
							</div>
						</div>
						<form id="update-ProcessMaster-list">
							<div class="card border border-secondary">

								<div class="card-body regi-input-body">

									<s:csrfInput />
									<input type="hidden" class="ProcessMaster-u" name="id">
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">code</span>
										</div>
										<input type="text" class="form-control ProcessMaster-u regi"
											name="code" placeholder="코드번호">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">공정명</span>
										</div>
										<input type="text" class="form-control ProcessMaster-u regi"
											name="processName" placeholder="공정명">
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">전공정</span>
										</div>
										<select
											class="custom-select regi-u-select ProcessMaster-u regi"
											name="beforeProcess.id">
												<option value=0 selected="selected">없음</option>
											<c:forEach var="ProcessMasters" items="${ProcessMasters}">
												<option value="${ProcessMasters.id}">${ProcessMasters.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">후공정</span>
										</div>
										<select
											class="custom-select regi-u-select ProcessMaster-u regi"
											name="afterProcess.id">
												<option value=0 selected="selected">없음</option>
											<c:forEach var="ProcessMasters" items="${ProcessMasters}">
												<option value="${ProcessMasters.id}">${ProcessMasters.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">세부공정</span>
										</div>
										<input type="text" class="form-control ProcessMaster-u regi"
											name="detailProcess" placeholder="세부공정">
									</div>

								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1"
										id="update-ProcessMaster-btn" type="submit">수정</button>
								</div>
							</div>
						</form>

					</div>
				</div>
			</div>
		</div>
	</div>

</div>

