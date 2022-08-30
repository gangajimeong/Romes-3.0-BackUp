<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card  border-secondary mr-0" id="main">
	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">시공 실적</span>

	</div>

	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class="mt-0 table-panel col-7 border border-scondary "
				style="padding: 1px 1px 1px 1px;">
				<div>
					<div class="input-group search-group float-right w-100 "
						style="padding-bottom: 10px">

						<div class="input-group Order-group col-md-12 mt-2">

							<select class="custom-select search-select w-20"
								style="height: 3.183023872679045vh;"
								id="constructionPerformanceSearch">
								<option value="0-2">수주명</option>
								<option value="0-4">브랜드</option>
								<option value="1-2">시공사</option>
								<option value="1-4">시공위치</option>
							</select> <input type="text" class="form-control search-control"
								style="height: 3.183023872679045vh; margin-right: 2%"
								id="ConstructionPerformanceSearchText"> <input
								type="date" class="form-control"
								style="height: 3.183023872679045vh;" id="constPreDate">
							<span style="color: black; padding: 0px 10px 0px 10px"> ~
							</span> <input type="date" class="form-control"
								style="height: 3.183023872679045vh;" id="constLastDate">
						</div>


					</div>
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover "
						id="ConstructionPerformanceTable">
						<thead id="ConstructionPerformanceColName">
							<tr>
								<!-- <th style="width: 9.97%;">LOTNO</th> -->
								<th style="width: 5%;">No</th>
								<th style="width: 19%;">수주명</th>
								<th style="width: 19%;">수주일</th>
								<th style="width: 19%;">브랜드</th>
								<th style="width: 19%;">담당자</th>
								<th style="width: 19%;">상태</th>
							</tr>
						</thead>
					</table>
				</div>
				<div style="overflow: auto; height: 78vh">
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover">
						<tbody id="ConstructionContents">
							<c:forEach items="${lists}" var="item" varStatus="status">
								<tr>
									<td style="display: none;">${item.id}</td>
									<td style="width: 5%;">${status.index+1}</td>
									<td style="width: 19%;">${item.title}</td>
									<td style="width: 19%;">${item.date}</td>
									<td style="width: 19%;">${item.company}</td>
									<td style="width: 19%;">${item.user}</td>
									<td style="width: 19%;">${item.state}</td>
								</tr>
								<tr class="construcionContent mb-0">
									<td colspan="12" style="padding: 0px">
										<div class="collapse hiddenRow border border-info"
											id="${item.id}">
											<table class="table table-striped mb-0">
												<thead>
													<tr>
														<!-- <th style="width: 9.97%;">LOTNO</th> -->
														<th style="width: 20%;">시공사</th>
														<th style="width: 20%;">브랜드</th>
														<th style="width: 20%;">시공 위치</th>
														<th style="width: 20%;">시공자</th>
														<th style="width: 20%;">시공날짜</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${item.result}" var="h" varStatus="s">
														<tr>
															<td style="display: none">${h.id}</td>
															<td style="display: none">${h.url}</td>
															<td>${h.company}</td>
															<td>${h.brand}</td>
															<td>${h.branch}</td>
															<td>${h.user}</td>
															<td>${h.date}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>
			<div class="mt-0 table-panel col-md-5 border border-scondary "
				style="padding: 1px 1px 1px 1px;">
				<div class="row no-gutters">

					<div class="col-md-12 mt-1">

						<div class="card">
							<div class="card-header"
								style="padding: 2px 2px 2px 2px; background-color: #5E7DC0">
								<b class="card-title" style="font-size: 0.7rem;">시공 사진</b>
								<div class="float-right">
									<button class="btn btn-secondary"
										style="padding-top: 0px; padding-bottom: 0px;"
										id="detailConstructionImg">자세히 보기</button>
								</div>
							</div>
							<div class="card-body" style="padding: 1px 1px 1px 1px;">
								<div id="constructionIndicators" class="carousel slide"
									data-bs-interval="false">
									<ol class="carousel-indicators" id="constPerformIndi">
										<li data-target="#constructionIndicators" data-slide-to="0"
											class="template-indi active"></li>
									</ol>
									<div class="carousel-inner" style="height: 78vh;"
										id="constPerform-indi-Items">
										<div class="carousel-item active indi-item">
											<img class="d-block w-100 h-100"
												src="image?imagename=no_image.png" alt="First slide">
											<div class="carousel-caption d-none d-lg-block"></div>
										</div>
									</div>
									<a class="carousel-control-prev rgba-black-strong"
										href="#constructionIndicators" role="button" data-slide="prev">
										<span class="carousel-control-prev-icon" aria-hidden="true"></span>
										<span class="sr-only">Previous</span>
									</a> <a class="carousel-control-next"
										href="#constructionIndicators" role="button" data-slide="next">
										<span class="carousel-control-next-icon" aria-hidden="true"></span>
										<span class="sr-only">Next</span>
									</a>


								</div>
							</div>
						</div>
					</div>
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered modal-lg"
							role="document">
							<div class="modal-content card">

								<div class="card-header"
									style="font-size: 0.8rem; padding: 2px 2px 2px 2px; background: #5E7DC0">
									<b class="card-title">상세보기</b>
								</div>
								<div class="card-body" style="padding: 0 0 0 0;">
									<img src="" class="img-fluid img-thumbnail" alt="..."
										id="detailView">
								</div>
								<div class="card-footer row no-gutters "
									style="padding: 0.5rem 0.5rem 0.5rem 0.5rem;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>