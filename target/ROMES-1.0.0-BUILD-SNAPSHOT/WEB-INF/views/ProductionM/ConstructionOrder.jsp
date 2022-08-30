<%@ page language="java" contentType="text/html; charset= UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0" id="main">
	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">시공 지시 관리</span>

	</div>
	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">

			<div class="col-md-12 col-sm-12 col-lg-12">
				<div class=card>
					<div class="card-header"
						style="font-size: 0.2rem; padding: 4px 4px 4px 4px; background-color: #5E7DC0">
						<div class="container-fluid">
							<div class="row" style="padding: 0px 0px 0px 0px;">
								<div class="col-md-2 col-sm-2 col-lg-2"
									style="padding: 0px 0px 0px 0px; margin: auto;">
									<span class="badge badge-dark page-titles">시공 지시 대기목록</span>
								</div>
								<div class="col-md-10 col-sm-10 col-lg-10 ml-auto"
									style="padding: 0px 0px 0px 0px;">
									<div class="input-group search-group float-right  ">
										<div class="input-group-prepend">
											<select class="custom-select search-select"
												id="ConstructionOrderInfoSearch"></select>
										</div>

										<input type="search" class="form-control search-control"
											id="ConstructionOrderInputSearch">
										<div class="input-group-append search-append">
											<span class="input-group-text search"><i
												class='bx bx-search bx-sm'></i></span>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
					<div class="card-body p-0">
						<table id="BrandManagementTable"
							class="table table-condensed table-striped table-bordered mb-0 table-hover custom-table">
							<thead id="ConstructionOrderColName">
								<tr>
									<th style="width: 3%;">No</th>
									<th style="width: 20%;">매장명</th>
									<th style="width: 17%;">품목</th>
									<th style="width: 20%;">수주일</th>
									<th style="width: 20%;">시공사</th>
									<th style="display: none;">진행현황</th>


								</tr>
							</thead>
							<tbody style="height: 34.5vh;" id="ConstructionOrderContents">
								<c:forEach varStatus="status" items="${Lists}" var="order">
									<tr class="modalClick">
										<td style="display: none;">${order.id}</td>
										<td style="width: 3%;">${status.index+1}</td>
										<td style="width: 20%;">${order.branch}</td>
										<td style="width: 17%;">${order.product}</td>
										<td style="width: 20%;">${order.date}</td>
										<td style="width: 20%;">${order.company}</td>
										<td style="display: none;">${order.state}</td>
										<td style="display: none;">${order.order}</td>
										<td style="display: none;">${order.brand}</td>
										<td style="display: none;">${order.design}</td>
										<td style="display: none;">${order.cId}</td>
										
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12 col-lg-12 mt-2">
				<div class=card>
					<div class="card-header"
						style="font-size: 0.2rem; padding: 4px 4px 4px 4px; background-color: #5E7DC0">
						<div class="container-fluid">
							<div class="row" style="padding: 0px 0px 0px 0px;">
								<div class="col-md-2 col-sm-2 col-lg-2"
									style="padding: 0px 0px 0px 0px; margin: auto;">
									<span class="badge badge-dark page-titles">시공 지시 목록</span>
								</div>
								<div class="col-md-10 col-sm-10 col-lg-10 ml-auto"
									style="padding: 0px 0px 0px 0px;">
									<div class="input-group search-group float-right  ">
										<div class="input-group-prepend">
											<select class="custom-select search-select"
												id="ConstructionOrder2InfoSearch"></select>
										</div>

										<input type="search" class="form-control search-control"
											id="ConstructionOrderInputSearch2">
										<div class="input-group-append search-append">
											<span class="input-group-text search"><i
												class='bx bx-search bx-sm'></i></span>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
					<div class="card-body p-0">
						<table id="BrandManagementTable"
							class="table table-condensed table-striped table-bordered mb-0 table-hover custom-table">
							<thead id="ConstructionOrder2ColName">
								<tr>
									<th style="width: 3%;">No</th>
									<th style="width: 20%;">수주명</th>
									<th style="width: 20%;">브랜드</th>
									<th style="width: 20%;">매장명</th>
									<th style="width: 27%;">수주일</th>
								</tr>
							</thead>
							<tbody style="height: 35.5vh;" id="ConstructionOrderContents2">
								<c:forEach varStatus="status" items="${finList}" var="fin">
									<tr class = "coList">
										<td style="display: none;">${fin.id}</td>
										<td style="width: 3%;">${status.index+1}</td>
										<td style="width: 20%;">${fin.title}</td>
										<td style="width: 20%;">${fin.brand}</td>
										<td style="width: 20%;">${fin.branch}</td>
										<td style="width: 27%;">${fin.date}</td>
										<td style="display: none;">${fin.company}</td>
										<td style="display: none;">${fin.phone}</td>
										<td style="display: none;">${fin.user}</td>
										<td style="display: none;">${fin.position}</td>
										<td style="display: none;">${fin.product}</td>
										<td style="display:none;">${fin.design}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="constModal"></div>
	<div class="modalContents">
		<input type="hidden" id="ConstructionInfo">
		<table class="tableCont sec1">
			<tr>
				<th class="contTitle" colspan="9">시공 상세 내용</th>
				<span class="closeBtn" aria-hidden="true">&times;</span>
			</tr>
			<tr class="contRow">
				<td style="display:none;" id = constructionOrder-detail-id></td>
				<td class="imgSection" colspan="6" rowspan="5">
					<img style ="width:95%; height:95%;" src="image?imagename=no_image.png" id="construction-sample-image" class="imgfile">
				</td>
				<th class="contName">수주명</th>
				<td class="content" colspan="2" id="title"></td>
			</tr>
			<tr class="contRow">
				<th class="contName">브랜드</th>
				<td class="content" colspan="2" id="brand"></td>
			</tr>
			<tr class="contRow">
				<th class="contName">매장명</th>
				<td class="content" colspan="2" id="branch"></td>				
			</tr>
			<tr class="contRow">
				<th class="contName">품목</th>
				<td class="content" colspan="2" id="product"></td>
			</tr>
			<tr class="contRow">
				<th class="contName">수주일</th>
				<td class="content" colspan="2" id="Construction_orderDate"></td>
			</tr>
			<tr class="contRow">
				<th class="contName" colspan="2">시공사</th>
				<td class="content" colspan="5" >
					<select class="custom-select" style="height:70%;font-size:14px;" id="constructionOrder_company">
	 						<c:forEach varStatus="status" items="${company}" var="com">
	 							<option value="${com.id}">${com.company}</option>
	 						</c:forEach> 
	 				</select>
 				</td>
 				<td colspan="2">
 					<button type = "button" class="changeBtn construction-order-btn">시공지시 등록</button>
 				</td>
			</tr>
			
		</table>
	</div>
	<div class="modal fade" id="constOrder-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document" style="min-width: 70%;">
				<div class="modal-content card" style="height: 70vh;">
					<div class="card-header" style="background-color: #5E7DC0">
						<b class="card-title" style="font-size: 25px;">시공내용</b>
					</div>
					<div class="card-body">
						<table class="tableCont sec1">
			
							<tr class="contRow">
								<td style="display:none;" id = constructionOrder-detail-id></td>
								<td class="imgSection" colspan="6" rowspan="6">
									<img style ="width:95%; height:95%;" src="image?imagename=no_image.png" id="conList-sample-image" class="imgfile">
								</td>
								<th class="contName">수주명</th>
								<td id="conList-title" colspan="2" style="padding-left: 10px;"></td>
							</tr>
							<tr class="contRow">
								<th class="contName">브랜드</th>
								<td id="conList-brand" colspan="2" style="padding-left: 10px;"></td>
							</tr>
							<tr class="contRow">
								<th class="contName">매장명</th>
								<td id="conList-branch" colspan="2" style="padding-left: 10px;"></td>				
							</tr>
							<tr class="contRow">
								<th class="contName">품목</th>
								<td id="conList-product" colspan="2" style="padding-left: 10px;"></td>
							</tr>
							<tr class="contRow">
								<th class="contName">수주일</th>
								<td id="conList-orderDate" colspan="2" style="padding-left: 10px;"></td>
							</tr>
							<tr class="contRow">
								<th class="contName">시공사</th>
								<td id="conList-company" colspan="2" style="padding-left: 10px;"></td>
							</tr>
							
						</table>
					</div>
					
				</div>
			</div>
		</div>
</div>
