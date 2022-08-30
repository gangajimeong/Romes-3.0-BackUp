<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<div class="card mr-0 workorderpage" id="main">

	<div class="card mb-3" >
		<div class="card-header card-headerMain border-secondary"
			style="padding: 0.5rem;">
			<span class="badge badge-dark panel-heading" style="font-size: 3vh;">작업지시
				모니터링</span>
		</div>
		<div class="panel-body setHeight" >
			<table class="table table-bordered mt-2 tableArea">
			<s:csrfInput />
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
				<tbody id="monitor-tbody">
				
					<tr style="display: none" id="monitor-origin">
						<td style="display: none;" class="monitor-id">${i.id}</td>
						<td class="workOrder-tableInfo workOrderTable monitor-idx"
							data-toggle="modal" data-target="#workOrder-modalInfo"
							data-id="${status.index}">${status.index + 1}</td>
						<td class="monitor-user"></td>
						<td class="monitor-company"></td>
						<td class="monitor-branch"></td>
						<td class="monitor-printer"></td>
						<td class="monitor-product"></td>
						<td class="monitor-coating"></td>
						<td class="monitor-back">}</td>
						<td class="monitor-size"></td>
						<td class="monitor-count"></td>
						<td class="monitor-remark"></td>
						<td style="display: none;" class="monitor-manufacture"></td>
						<td style="display: none;" class="monitor-url"></td>
						<td class="monitor-emergency"><c:if
								test="${i.emergency == true}">
								<i class='bx bxs-bell-ring'
									style="color: red; padding: 5px 5px 5px 5px;" id="bell-icon"></i>
							</c:if></td>
						<td>
							<button style="display: none;" 
								type="button" class="btn btn-secondary editBtn submit-button"
								title="${i.id}">완료</button>
						</td>
						<c:forEach var="i" items="${data}" varStatus="status">
							<tr>
								<td style="display: none;" id="id-${status.index}">${i.id}</td>
								<td class="workOrder-tableModal workOrderTable"
									data-toggle="modal" data-target="#workOrder-modalInfo"
									data-id="${status.index}">${status.index + 1}</td>
								<td id="user-${status.index}">${i.user}</td>
								<td id="company-${status.index}">${i.company}</td>
								<td id="branch-${status.index}">${i.branch}</td>
								<td id="printer-${status.index}">${i.printer}</td>
								<td id="product-${status.index}">${i.product}</td>
								<td id="coating-${status.index}">${i.coating}</td>
								<td id="back-${status.index}">${i.back}</td>
								<td id="size-${status.index}">${i.size}</td>
								<td id="count-${status.index}">${i.count}</td>
								<td id="remark-${status.index}">${i.remark}</td>
								<td id="emergency-${status.index}"><c:if
										test="${i.emergency == true}">
										<i class='bx bxs-bell-ring'
											style="color: red; padding: 5px 5px 5px 5px;" id="bell-icon"></i>
									</c:if></td>
								<td style="display: none;" id="manufacture-${status.index}">${i.manufacture}</td>
								<td style="display: none;" id="url-${status.index}">${i.url}</td>
								<td>
									<%-- <button id="sumitButton-${status.index}"
										type="button" class="btn btn-secondary editBtn complete-Work"
										data-id="${status.index}" data-toggle="modal" data-target="#workOrder-modal">완료</button> --%>
									<button
										type="button" class="btn btn-secondary editBtn complete-Work"
										title="${i.id}">완료</button>
								</td>
							</tr>
						</c:forEach>
				</tbody>
			</table>
		</div>

	
	</div>
	<div class="card mb-3" style="position: fixed; bottom: 0; width: 100%; height: 15rem;">
		<div class="card-header card-headerMain border-secondary"
			style="padding: 0.5rem;">
			<span class="badge badge-dark panel-heading" style="font-size: 3vh;">작업완료목록</span>
		</div>
		<div class="panel-body" style="overflow-x: hidden; overflow-y: scroll;">
			<table class="table table-bordered mt-2 tableArea">
				<s:csrfInput />
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
				</tr>
				<tbody id="com-monitor-tbody">
				
					<tr style="display: none" id="com-monitor-origin">
						<td style="display: none;" class="com-monitor-id">${i.id}</td>
						<td class="com-monitor-idx">${status.index + 1}</td>
						<td class="com-monitor-user"></td>
						<td class="com-monitor-company"></td>
						<td class="com-monitor-branch"></td>
						<td class="com-monitor-printer"></td>
						<td class="com-monitor-product"></td>
						<td class="com-monitor-coating"></td>
						<td class="com-monitor-back"></td>
						<td class="com-monitor-size"></td>
						<td class="com-monitor-count"></td>
						<td class="com-monitor-remark"></td>
						<td style="display: none;" class="com-monitor-manufacture"></td>
						<td style="display: none;" class="com-monitor-url">${i.url}</td>
						
						<c:forEach var="i" items="${complete}" varStatus="status">
							<tr>
								<td style="display: none;" id="com-id-${status.index}">${i.id}</td>
								<td class="com-workOrderTable">${status.index + 1}</td>
								<td id="com-user-${status.index}">${i.user}</td>
								<td id="com-company-${status.index}">${i.company}</td>
								<td id="com-branch-${status.index}">${i.branch}</td>
								<td id="com-printer-${status.index}">${i.printer}</td>
								<td id="com-product-${status.index}">${i.product}</td>
								<td id="com-coating-${status.index}">${i.coating}</td>
								<td id="com-back-${status.index}">${i.back}</td>
								<td id="com-size-${status.index}">${i.size}</td>
								<td id="com-count-${status.index}">${i.count}</td>
								<td id="com-remark-${status.index}">${i.remark}</td>
								<td style="display: none;" id="com-manufacture-${status.index}">${i.manufacture}</td>
								<td style="display: none;" id="com-url-${status.index}">${i.url}</td>
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
						<button type="button" class="btn btn-block suju-button" id=""
							style="padding: 0 1; background-color: #5a6268;">생산완료</button>
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
		aria-hidden="true" style="overflow: hidden;">
		<div class="modal-dialog modal-dialog-centered modal-lg"
			role="document" style="max-width: 1300px;">
			<div class="modal-content card" id="workOrderModal-info">
				<div class="card-header" style="background-color: #5E7DC0">
					<b class="card-title">작업상세</b>
				</div>
				<div class="card-body">
					<div class="input-group-prepend" style="height: 3.2vh;">
						<label class="input-group-text Order-group-text">작업 시안</label>
					</div>
					<img style="height: 65vh; border: 1px solid #ced4da;"
						src="image?imagename=no_image.png" id="workInfo-img" class="imgfile">
				</div>
				<div class="card-body" style="padding-top: 0;">
					<div class="input-group-prepend" style="height: 3.2vh;">
						<label class="input-group-text Order-group-text">가공방법</label>
					</div>
					<textarea id="workInfo-info" class="form-control"
						style="height: 10vh;" disabled></textarea>
				</div>
			</div>
		</div>
	</div>
</div>

