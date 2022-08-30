<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card  border-secondary mr-0" id="main">
	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">모니터링</span>

	</div>
	<div class="card mb-3">
		<div class="panel-heading titleArea">작업지시 모니터링</div>
		<div class="panel-body">
			<table class="table table-bordered mt-2 tableArea">
				<tr class="tableHead">
					<th width="5%">No</th>
					<th>작성자</th>
					<th>청구지</th>
					<th>매장명</th>
					<th>출력기</th>
					<th>픔목</th>
					<th>코팅</th>
					<th>배면</th>
					<th>규격</th>
					<th>수량</th>
					<th>특이사항</th>
					<th width="5%">긴급</th>
					<th width="5%">완료</th>
				</tr>
				<tbody>
					<c:forEach var="i" begin="0" end="10" varStatus="status">
						<tr>
							<td style="display:none;" id="id-${i}"></td>
							<td class="workOrder-tableInfo workOrderTable">${status.index + 1}</td>
							<td id="user-${i}"></td>
							<td id="company-${i}"></td>
							<td id="branch-${i}"></td>
							<td id="printer-${i}"></td>
							<td id="product-${i}"></td>
							<td id="coating-${i}"></td>
							<td id="back-${i}"></td>
							<td id="size-${i}"></td>
							<td id="count-${i}"></td>
							<td id="remark-${i}"></td>
							<td id="emergency-${i}"><i class='bx bxs-bell-ring'
								style="color: red; padding: 5px 5px 5px 5px;" id="bell-icon"></i></td>
							<td style ="display:none;" id = "manufacture-${i}"></td>
							<td>
								<button style="display:none;"id="sumitButton-${i}" type="button"
									class="btn btn-secondary editBtn complete-Work" data-id="${i}">완료</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="modal fade" id="workOrder-modal" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg"
			role="document">
			<div class="modal-content card" id="workOrderModal-change">
				<div class="card-header" style="background-color: #5E7DC0">
					<b class="card-title">변경 이력 등록</b>
				</div>
				<div class="card-body" style="padding: 1px;">
					<div class="input-group Order-group col-md-12 mt-3">
						<div class="input-group-prepend"
							style="padding: 0; height: 3.2vh;">
							<label class="input-group-text Order-group-text">작업 재지시</label>
						</div>
						<select class="custom-select" name="company.id"
							id="workInfo-remake" style="height: 3.2vh; padding: 0 0 0 10px;">
							<option value=true>예</option>
							<option value=false>아니오</option>
						</select>
					</div>
					<div class="card-body">
						<div class="input-group-prepend" style="height: 3.2vh;">
							<label class="input-group-text Order-group-text">특이 사항</label>
						</div>
						<textarea id="workInfo-remark" class="form-control"
							style="height: 30vh;"></textarea>
					</div>
				</div>
				<div class="row no-gutters " style="padding: 0.5rem;">
					<div class="button-group col-md-12">
						<button type="button" class="btn btn-block suju-button"
							id="workOrder-submit" style="padding: 0 1;">
							등록
							<div class="spinner-border" role="status" style="display: none;"
								id="workInfo-loading">
								<span class="sr-only">Loading...</span>
							</div>
						</button>
						<button type="button" class="btn btn-block suju-button"
							id="production-cancel" style="padding: 0 1;">취소</button>
					</div>
				</div>

			</div>
		</div>
	</div>
	<div class="modal fade" id="workOrder-modalInfo" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg"
			role="document">
			<div class="modal-content card" id="workOrderModal-info">
				<div class="card-header" style="background-color: #5E7DC0">
					<b class="card-title">작업내용</b>
				</div>
				<div class="card-body">
					<div class="input-group-prepend" style="height: 3.2vh;">
						<label class="input-group-text Order-group-text">작업 시안</label>
					</div>
					<img style="height: 30vh; border: 1px solid #ced4da;"
						src="image?imagename=no_image.png" id="" class="imgfile">
				</div>
				<div class="card-body" style="padding-top: 0;">
					<div class="input-group-prepend" style="height: 3.2vh;">
						<label class="input-group-text Order-group-text">작업상세내역</label>
					</div>
					<textarea id="workInfo-info" class="form-control"
						style="height: 30vh;"></textarea>
				</div>
			</div>
		</div>
	</div>
</div>