<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

    <%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card  border-secondary mr-0" id="main">

	<div class="card-header card-headerMain  border-secondary">
		<div class="row no-gutters ">
			<div class="col-4">
				<h2>
					<span class="badge badge-dark page-title">공통 코드</span>
				</h2>
			</div>
			<div class="col-8">

				<div class="input-group float-right  ">
					<div class="input-group-prepend">
						<select class="custom-select" id ="CommonInfoSearch">
							
							
						</select>
					</div>

					<input type="text" class="form-control" id = "CommonCodeSearch" >
					<div class="input-group-append">
						<span class="input-group-text search">검색</span>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<s:authorize access="hasRole('ADMIN')">
				<div class="col-12">
					<div class="card border-secondary ">

						<div class="card-body btnPanel">

							<button class="btn btn-secondary create" data-toggle="modal"
								data-target="#modal" role="button">등록</button>
						</div>

					</div>
				</div>
			</s:authorize>
		</div>
		<div class="row no-gutters">
			<div class="col-12">
				<div class="card mt-1 border-secondary mt-1 tablePanel">
					<div class="card-body  border-dark tableSetting">
						<table id="DocumentListTable"
							class="table table-condensed table-striped table-bordered mb-0 table-hover ">
							<thead id = "DocColName">
								<tr>
									<th>ID</th>
									<th>제목</th>
									<th>결제 서류</th>
									<th>작성 날짜</th>
									<th>승인 상태</th>
									<th>작성자</th>
									<s:authorize access="hasRole('ADMIN')">
										<th colspan="2">승인</th>
									</s:authorize>
								</tr>
							</thead>
							<tbody id = "commonBody">
								<c:forEach var="commonCodes" items="${commonCodes}"
									varStatus="status">
									<tr>
										<td style="display: none; ">${commonCodes.getId()}</td>
										<td class = "CodeId">${commonCodes.getCodeId()}</td>
										<td class = "classification">${commonCodes.getClassification()}</td>
										<td>${commonCodes.getValue()}</td>
										<td>${commonCodes.getColumnCode()}</td>
										<s:authorize access="hasRole('ADMIN')">
											<td style="width: 8%;" colspan="2">
												<div class="btn-group">
													<button class="btn btn-secondary update"
														data-toggle="modal" data-target="#modal" role="button">수정</button>
													<a href="deleteCommonCode/${commonCodes.getId()}"
														class="btn btn-dark delete" role="button">삭제</a>
												</div>
											</td>
										</s:authorize>
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





    