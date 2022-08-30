<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card border-secondary mr-0" id="main">
	<div class="card-header card-headerMain  border-secondary">
		<span class="badge badge-dark page-title">설정</span>

	</div>

	<div class="card-body  border-secondary mainPanel">
		<div class="row no-gutters">
			<div class="mt-0 table-panel col-12 border border-scondary "
				style="padding: 1px 1px 1px 1px;">
				<div>
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover "
						id="srhistory-Table">
						<thead id="srhistory-ColName">
							<tr>
								<!-- <th style="width: 9.97%;">LOTNO</th> -->
								<th style="width: 5%;">No</th>
								<th style="width: 13%;">시스템명</th>
								<th style="width: 15%;">버전</th>
								<th style="width: 13%;">업데이트일</th>
								<th style="width: 13%;">비고</th>
							</tr>
						</thead>
					</table>
				</div>
				<div style="overflow: auto; height: 78vh">
					<table
						class="table table-condensed table-striped table-bordered mb-0 table-hover">
						<tbody id="srhistory-table-Contents">
								<tr >
									<td style="display: none;"></td>
									<td style="width: 5%;">1</td>
									<td style="width: 13%;">ROMES 3.0</td>
									<td style="width: 15%;">v1.0.00</td>
									<td style="width: 13%;">2022-04-20</td>
									<td style="width: 13%;">-</td>
												
								</tr>
						</tbody>
					</table>
				</div>

			</div>
			
		</div>
	</div>
</div>
