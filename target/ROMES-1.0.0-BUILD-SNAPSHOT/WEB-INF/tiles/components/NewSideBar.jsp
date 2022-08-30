<!-- Boxicons CDN Link -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>


<div class="sidebar close">
	<div class="logo-details">
		<img class="RLogo" src="img/mainLogo.png"> <span
			class="logo_name">ROMES 3.0</span>
	</div>
	<ul class="nav-links">
		<li>

			<div class="i-link">
				<i class='bx bx-collection'></i> <span class="link_name">기준
					정보</span> <i class='bx bxs-chevron-down arrow'></i>
			</div>
			<ul class="sub-menu">
				<li><a class="link_name" href="#">기준정보</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">공통코드</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">부서관리</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">거래처관리</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">시공사관리</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">브랜드관리</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">생산라인관리</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">원/부자재관리</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">로케이션마스터</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">공정마스터</a></li>				
				<li><a href="<c:url value="/main" />" class="tabAdd">불량코드</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">제품정보</a></li>
			</ul>
		</li>
		<li>
			<div class="i-link">
				<i class='bx bx-book-alt'></i> <span class="link_name">생산관리</span>
				<i class='bx bxs-chevron-down arrow'></i>
			</div>
			<ul class="sub-menu">
				<li><a class="link_name" href="#">생산관리</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">수주/작업지시</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">생산계획</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">시공지시</a></li>
				<li><a class="tabAdd" href="<c:url value="/main" />">작업보고서</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">시뮬레이션</a></li>
				<li><a class="tabAdd" href="<c:url value="/main" />">설비별불량</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">CAPA</a></li>
				<li><a class="tabAdd" href="<c:url value="/main" />">KPI</a></li>
			</ul>
		</li>

		<li><div class="i-link">
				<i class='bx bx-pie-chart-alt-2'></i> <span class="link_name">원자재재고</span> <i class='bx bxs-chevron-down arrow'></i>
			</div>

			<ul class="sub-menu">
				<li><a class="link_name" href="#">원자재재고</a></li>
				<li><a href="<c:url value="/main"/>" class="tabAdd">입고/출고</a></li>
				<li><a href="<c:url value="/main"/>" class="tabAdd">재고현황</a></li>
				<%-- <li><a href="<c:url value="/LocationMaster" />">적정 재고</a></li> --%>
				<li><a href="<c:url value="/main"/>" class="tabAdd">발주관리</a></li>
			</ul></li>


		<li>
			<div class="i-link">
				<i class='bx bx-line-chart'></i> <span class="link_name">제품출하/시공</span> <i class='bx bxs-chevron-down arrow'></i>
			</div>
			<ul class="sub-menu">
				<li><a class="link_name" href="#">제품출하/시공</a></li>
				<li><a href="<c:url value="/main"/>" class="tabAdd">출하실적</a></li>
				<li><a href="<c:url value="/main"/>" class="tabAdd">시공실적</a></li>
				<li><a href="<c:url value="/main"/>" class="tabAdd">제품/시공불량내역</a></li>
			</ul>
		</li>
		<li>
			<div class="i-link">

				<i class='bx bx-laptop'></i> <span class="link_name">모니터링</span> <i
					class='bx bxs-chevron-down arrow'></i>

			</div>
			<ul class="sub-menu">
				<li><a class="link_name" href="#">모니터링</a></li>
				<li><a class="tabAdd" href="<c:url value="/work" />">작업지시모니터링</a></li>
			</ul>
		</li>
		<li><div class="i-link">
				<i class='bx bx-compass'></i> <span class="link_name">시스템관리</span>
				<i class='bx bxs-chevron-down arrow'></i>
			</div>

			<ul class="sub-menu">
				<li><a class="link_name" href="#">시스템관리</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">사용자관리</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">사용자정보관리</a></li>
				<li><a href="<c:url value="/main" />" class="tabAdd">로그기록</a></li>
				<li><a class="tabAdd" href="<c:url value="/main" />">시스템정보관리</a></li>
			</ul></li>
		<li><div class="i-link">
				<i class='bx bx-cog'></i> <span class="link_name">설정</span>
			</div>

			<ul class="sub-menu blank">
				<li><a class="tabAdd" href="<c:url value="/main" />">설정</a></li>
			</ul></li>
	</ul>
</div>





