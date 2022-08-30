<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>


<div class="card align-middle ModalCard">
	<div class="card-header modal-header">
		<span class="badge badge-dark ModalBadge" id="createModalTitle">공통코드 등록</span>
	</div>
	<div class="card-body">
		<div class="card ModalCard">
			<div class="card-body ">
				<form action="createCommonCode" method="post" id="createForm">
					<s:csrfInput />

				</form>
			</div>
		</div>
	</div>
</div>
