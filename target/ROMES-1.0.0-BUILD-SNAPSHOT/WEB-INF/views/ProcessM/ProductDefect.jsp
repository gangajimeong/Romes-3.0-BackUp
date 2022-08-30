<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">제품불량내역</span>
		<div class="input-group search-group float-right">
			<div class="input-group-prepend">
				<select class="custom-select search-select"
					id="ProductDefectInfoSearch"></select>
			</div>

			<input type="text" class="form-control search-control"
				id="ProductDefectSearch">
			<div class="input-group-append search-append">
				<span class="input-group-text search"><i
					class='bx bx-search bx-sm'></i></span>
			</div>
		</div>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class=" card regi-card  mt-0 table-panel col-9">
				<table id="ProductDefectTable"
					class="table table-condensed table-striped table-bordered mb-0 table-hover ">
					<thead id="ProductDefectColName">
						<tr>
							<th style="width: 5%">No</th>
							<th style="width: 20%">불량품</th>
							<th style="width: 20%">불량내용</th>
							<th style="width: 30%">처리자</th>
							<th style="width: 20%">등록시간</th>
						</tr>
					</thead>
					<tbody id="ProductDefectContents">
						<c:forEach var="ProductDefect" items="${ProductDefect}"
							varStatus="status">
							<tr>
								<td style="display: none;">${ProductDefect.id}</td>
								<td>${status.count}</td>
								<td>${ProductDefect.product}</td>
								<td>${ProductDefect.info}</td>
								<td>${ProductDefect.user}</td>
								<td>${ProductDefect.time}</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card regi-card col-3">
				<div class="card-header register">
					<ul class="nav nav-tabs card-header-tabs regi-ul">
						<li class="nav-item"><a
							class="nav-link ProductDefectUpdate active" data-toggle="tab"
							href="#ProductDefect-update">수정/삭제</a></li>

					</ul>
				</div>

				<div class=" card-body regi-body tab-content">
					<div class="tab-pane fade show active" id="ProductDefect-update">
						<div style="font-size: 0.8rem; padding: 2px 2px 2px 2px;">
							<div class="row no-gutters">
								<div class="col-md-12 col-sm-12 col-lg-12">
									<div class="float-right">
										<button type="button"
											class="btn btn-secondary border border-light"
											style="padding-top: 2px; padding-bottom: 2px;"
											id="deleteProductDefect">
											<b class="card-title">삭제</b>
										</button>
									</div>
								</div>
							</div>
						</div>
						<form id="update-ProductDefect-list">
							<div class="card border border-secondary mt-1">
								<div class="card-body regi-input-body">

									<s:csrfInput />
									<input type="hidden" class="ProductDefect-u" name="id">
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">불량품</span>
										</div>
										<input type="text" class="form-control ProductDefect-u regi"
											disabled>
									</div>
									<div class="input-group regi-group">
										<div class="input-group-prepend">
											<span class="input-group-text regi-group-text">불량내용</span>
										</div>
										<select class="form-control ProductDefect-u regi"
											style="padding: 0;" name="errorCode.id">
											<c:forEach var="error" items="${errors}">
												<option value="${error.id}">${error.type}</option>
											</c:forEach>
										</select>
									</div>

								</div>
								<div class="card-footer">
									<button class="btn regi-btn mt-1 mb-1"
										id="update-ProductDefect-btn" type="button">수정</button>
								</div>
							</div>
						</form>

					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="mt-1">
		<div class="card-header card-headerMain  border-secondary">
			<span class="badge badge-dark page-title">시공불량내역</span>
			<div class="input-group search-group float-right">
				<div class="input-group-prepend">
					<select class="custom-select search-select"
						id="ProductDefectInfoSearch2"></select>
				</div>

				<input type="text" class="form-control search-control"
					id="ProductDefectSearch2">
				<div class="input-group-append search-append">
					<span class="input-group-text search"><i
						class='bx bx-search bx-sm'></i></span>
				</div>
			</div>
		</div>
		<div class="card-body  border-secondary mainPanel">
			<div class="row no-gutters">
				<div class=" card regi-card  mt-0 table-panel col-9">
					<table id="ProductDefectTable"
						class="table table-condensed table-striped table-bordered mb-0 table-hover ">
						<thead id="ProductDefect2ColName">
							<tr>
								<th style="width: 5%">No</th>
								<th style="width: 20%">브랜드명</th>
								<th style="width: 20%">시공위치</th>
								<th style="width: 20%">시공시간</th>
								<th style="width: 30%">불량정보</th>
							</tr>
						</thead>
						<tbody id="ProductDefectContents2">
							<c:forEach var="ConstructionDefect"
								items="${ConstructionDefects}" varStatus="status">
								<tr>
									<td style="display: none;">${ConstructionDefect.id}</td>
									<td>${status.count}</td>
									<td>${ConstructionDefect.brand}</td>
									<td>${ConstructionDefect.location}</td>
									<td>${ConstructionDefect.time}</td>
									<td
										style="display: block; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width: 350px;">
										${ConstructionDefect.remark}</td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="card regi-card col-3">
					<div class="card-header register">
						<ul class="nav nav-tabs card-header-tabs regi-ul">
							<li class="nav-item"><a
								class="nav-link ConstructionDefectUpdate active"
								data-toggle="tab" href="#ConstructionDefect-update">수정/삭제</a></li>

						</ul>
					</div>

					<div class=" card-body regi-body tab-content">
						<div class="tab-pane fade show active"
							id="ConstructionDefect-update">
							<div style="font-size: 0.8rem; padding: 2px 2px 2px 2px;">
								<div class="row no-gutters">
									<div class="col-md-12 col-sm-12 col-lg-12">
										<div class="float-right">
											<button type="button"
												class="btn btn-secondary border border-light"
												style="padding-top: 2px; padding-bottom: 2px;"
												id="deleteConstructionDefect">
												<b class="card-title">삭제</b>
											</button>
										</div>
									</div>
								</div>
							</div>
							<form id="update-ConstructionDefect-list">
								<div class="card border border-secondary mt-1">
									<div class="card-body regi-input-body">

										<s:csrfInput />
										<input type="hidden" class="ConstructionDefect-u" name="id">
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-text">브랜드명</span>
											</div>
											<input type="text"
												class="form-control ConstructionDefect-u regi"
												name="brand.companyname" disabled>
										</div>
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-text">시공위치</span>
											</div>
											<input type="text"
												class="form-control ConstructionDefect-u regi"
												name="location.location.title" disabled>
										</div>
										<div class="input-group regi-group">
											<div class="input-group-prepend">
												<span class="input-group-text regi-group-text"
													style="border-radius: 0;">불량정보</span>
											</div>
											<textarea rows="5" cols="50"
												class="mt-1 ConstructionDefect-u" name="remark"></textarea>
										</div>

									</div>
									<div class="card-footer">
										<button class="btn regi-btn mt-1 mb-1"
											id="update-ConstructionDefect-btn" type="button">수정</button>
									</div>
								</div>
							</form>

						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>