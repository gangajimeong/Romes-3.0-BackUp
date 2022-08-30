<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<header class="col-md-12 col-lg-12 topnav" id="topMenu">
<!-- 		<ul> -->
<!-- 			<li class = "ml-2"><a class="openbtn">&#9776; Menu</a></li> -->
<!-- 			<li><a href="#">파일</a> First Tier Drop Down -->
<!-- 				<ul> -->
<!-- 					<li><a href="#">저장</a></li> -->
<!-- 					<li><a href="#">다른 이름으로 저장</a></li> -->
<!-- 					<li><a href="#">데이터 백업</a></li> -->
<!-- 					<li><a href="#">데이터 복원</a></li> -->
<!-- 					<li><a class="many" href="#">ERP IF</a> -->
<!-- 						<ul> -->
<!-- 							<li><a href="#">수주 업로드</a></li> -->
<!-- 						</ul></li> -->
<!-- 					<li><a href="#">보고서 저장경로 변경</a></li> -->
<!-- 					<li class="divider"></li> -->
<!-- 					<li><a href="#">로그아웃</a></li> -->
<!-- 					<li><a href="#">시스템 종료</a></li> -->
<!-- 				</ul></li> -->
<!-- 			<li><a href="#">문서</a> First Tier Drop Down -->
<!-- 				<ul> -->
<!-- 					<li><a href="#">견적서 발송</a></li> -->
<!-- 					<li><a href="#">발주서 발송</a></li> -->
<!-- 					<li><a href="#">운송장 발송</a></li> -->
<!-- 				</ul></li> -->
<!-- 			<li><a href="#">보고서</a> First Tier Drop Down -->
<!-- 				<ul> -->
<!-- 					<li><a href="#">작업지시서</a></li> -->
<!-- 					<li><a href="#">출하예정목록</a></li> -->
<!-- 					<li><a href="#">작업 일보</a></li> -->
<!-- 					<li><a class="many" href="#">KPI 성과측정</a> -->
<!-- 						<ul> -->
<!-- 							<li><a href="#">시간당 생산량</a></li> -->
<!-- 							<li><a href="#">리드 타임</a></li> -->
<!-- 							<li><a href="#">공정 불량률</a></li> -->
<!-- 							<li><a href="#">KPI 전체 통계</a></li> -->
<!-- 						</ul></li> -->
<!-- 					<li><a class="many" href="#">실적 보고서</a> -->
<!-- 						<ul> -->
<!-- 							<li><a href="#">생산 실적</a></li> -->
<!-- 							<li><a href="#">출하 실적</a></li> -->
<!-- 							<li><a href="#">근태 실적</a></li> -->
<!-- 						</ul></li> -->
<!-- 					<li><a href="#">출고 현황</a></li> -->
<!-- 					<li><a href="#">프린트</a></li> -->
<!-- 				</ul></li> -->
<!-- 			<li><a href="#">도움말</a> First Tier Drop Down -->
<!-- 				<ul> -->
<!-- 					<li><a class="many" href="#">메뉴얼</a> -->
<!-- 						<ul> -->
<!-- 							<li><a href="#">사용자 메뉴얼</a></li> -->
<!-- 							<li><a href="#">관리자</a></li> -->

<!-- 						</ul></li> -->

<!-- 				</ul></li> -->


<!-- 		</ul> -->
	
		<sec:authorize access="isAnonymous()">
			<a class="btn login float-right" href="<c:url value='/'/>"
				role="button">로그인</a>
		</sec:authorize>
		<!-- 로그아웃 버튼 -->
		<sec:authorize access="isAuthenticated()">

			<sec:authentication property="principal" var="user" />
			<c:url var="logoutUrl" value="/logout" />
			<form class="logoutForm float-right" action="${logoutUrl}"
				method="post">
				<label for="UserID"
					style="color: white; font-size: 1.5vh; margin-bottom: 0;">${ user.username }님
					환영합니다.</label>
				<button id="UserID" type="submit" class="btn logout">로그아웃</button>
				<sec:csrfInput />
			</form>

		</sec:authorize>
	

</header>



