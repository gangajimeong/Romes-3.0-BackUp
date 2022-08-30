<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">시뮬레이션</span>
	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters" style="padding: 1px 1px 1px 1px;">
			<div class="col-md-6 col-sm-12 col-lg-6">
				<div class="row no-gutters" style="padding: 1px 1px 1px 1px;">
					<div class="col-md-12 col-sm-12 col-lg-12">
						<div class="card mt-0"
							style="padding: 1px 1px 1px 1px; border-radius: 0px 0px 0px 0px; height: 100%;">
							<div class="card-header"
								style="font-size: 0.2rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
								<div class="container-fluid">
									<div class="row" style="padding: 0px 0px 0px 0px;">
										<div class="col-md-2 col-sm-2 col-lg-2"
											style="padding: 0px 0px 0px 0px; margin: auto;">
											<span class="badge badge-dark page-titles float-left">원자재 재고</span>
										</div>
										<div class="col-md-10 col-sm-10 col-lg-10 "
											style="padding: 0px 0px 0px 0px;">
										</div>
									</div>
								</div>
							</div>
							<div class="card-body" style="padding: 0px 0px 0px 0px;">
								<div>
									<table
										class="table table-condensed table-striped table-bordered mb-0 table-hover custom-table"
										id="">
										<thead id="">
											<tr>
												<th style="width: 10%;">No</th>
												<th style="width: 40%;">제품명</th>
												<th style="width: 30%;">규격</th>
												<th style="width: 20%;">재고량</th>

											</tr>
										</thead>
										<tbody id=""
											style="height: 30.31746031746032vh;">
											<c:forEach var="material" items="${material}" varStatus="status">
												<tr>
													<td style="display: none;">${material.id}</td>
													<td style="width: 10%;">${status.count}</td>
													<td style="width: 40%;">${material.name}</td>
													<td style="width: 30%;">${material.size}</td>
													<td style="width: 20%;">${material.count}</td>

												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12 col-lg-12 mt-2">
						<div class="card "
							style="padding: 1px 1px 1px 1px; border-radius: 0px 0px 0px 0px; height: 100%;">
							<div class="card-header"
								style="font-size: 0.2rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
								<div class="container-fluid">
									<div class="row" style="padding: 0px 0px 0px 0px;">
										<div class="col-md-2 col-sm-2 col-lg-2"
											style="padding: 0px 0px 0px 0px; margin: auto;">
											<span class="badge badge-dark page-titles">생산계획정보</span>
										</div>
										<div class="col-md-10 col-sm-10 col-lg-10 "
											style="padding: 0px 0px 0px 0px;">
										</div>
									</div>
								</div>
							</div>
							<div class="card-body" style="padding: 0px 0px 0px 0px;">
								<div>
									<table
										class="table table-condensed table-striped table-bordered mb-0 table-hover custom-table"
										id="">
										<thead id="">
											<tr>
												<th style="width: 10%;">No</th>
												<th style="width: 30%;">생산라인</th>
												<th style="width: 40%;">제품정보</th>
												<th style="width: 20%;">생산수량</th>

											</tr>
										</thead>
										<tbody id="OrderHistoryContents"
											style="height: 40.31746031746032vh;">
											<c:forEach var="lines" items="${lines}" varStatus="status">
												<tr>
													<td style="display: none;">${lines.id}</td>
													<td style="width: 10%;">${status.count}</td>
													<td style="width: 30%;">${lines.line}</td>
													<td style="width: 40%;">${lines.name}</td>
													<td style="width: 20%;">${lines.count}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="card regi-card col-md-6">
				<div class="card "
					style="padding: 1px 1px 1px 1px; border-radius: 0px 0px 0px 0px; height: 100%;">
					<div class="card-header"
						style="font-size: 0.2rem; padding: 2px 2px 2px 2px; background-color: #5E7DC0">
						<div class="container-fluid">
							<div class="row" style="padding: 0px 0px 0px 0px;">
								<div class="col-md-2 col-sm-2 col-lg-2"
									style="padding: 0px 0px 0px 0px; margin: auto;">
									<span class="badge badge-dark page-titles">시뮬레이션</span>
								</div>
								<div class="col-md-10 col-sm-10 col-lg-10 "
									style="padding: 0px 0px 0px 0px;">
								</div>
							</div>
						</div>
					</div>
					<div class="card-body" style="padding: 0px 0px 0px 0px;">
						<div>
							<table
								class="table table-condensed table-striped table-bordered mb-0 table-hover custom-table"
								id="">
								<thead id="">
									<tr>
										<th style="width: 10%;">No</th>
										<th style="width: 20%;">생산라인</th>
										<th style="width: 20%;">작업제품</th>
										<th style="width: 20%;">생산수량</th>
										<th style="width: 20%;">자재소요량</th>

									</tr>
								</thead>
								<tbody id="">
									<c:forEach var="simul" items="${simul}" varStatus="status">
										<tr>
											<td style="display: none;">${simul.id}</td>
											<td style="width: 10%;">${status.count}</td>
											<td style="width: 20%;">${simul.line}</td>
											<td style="width: 20%;">${simul.name}</td>
											<td style="width: 20%;">${simul.count}</td>
											<td style="width: 20%;">${simul.mr}</td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>