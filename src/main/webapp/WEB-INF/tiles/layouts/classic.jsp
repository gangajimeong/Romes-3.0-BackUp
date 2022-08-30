<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:url var="bootstrap" value="./css/bootstrap.css" />
<link href="${ bootstrap }" rel="stylesheet">
<link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css'
	rel='stylesheet'>

<link href="./css/CollapseSidebar.css" rel="stylesheet">
<link href="./css/CommonInfo.css" rel="stylesheet">
<link href="./css/TopMenu.css" rel="stylesheet">
<link href="./css/TabStyle.css" rel="stylesheet">
<link href="./css/OrderInfo.css" rel="stylesheet">
<link href="./css/ConstModal.css" rel="stylesheet">
<link href="./css/workorder.css" rel="stylesheet">
<!-- jquery ui -->
<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.24/themes/smoothness/jquery-ui.css" />
	<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<!-- <link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"> -->


<!--script  -->
<script src="js/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"
	type="text/javascript"></script>
<!-- <Script src="js/jquery-ui.js"></script> -->
<script src="https://unpkg.com/axios@0.26.1/dist/axios.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<Script src="js/bootstrap.min.js"></Script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/exceljs/4.3.0/exceljs.min.js"
	integrity="sha512-UnrKxsCMN9hFk7M56t4I4ckB4N/2HHi0w/7+B/1JsXIX3DmyBcsGpT3/BsuZMZf+6mAr0vP81syWtfynHJ69JA=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<Script type="module" src="js/init.js"></Script>
<script type="module" src="js/TabInit.js"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://www.gstatic.com/charts/loader.js">
</script>
<script src="https://html2canvas.hertzen.com/dist/html2canvas.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.0/jspdf.umd.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.15.5/xlsx.full.min.js"></script>

</head>
<body>

	<div class="container-fluid " style="padding: 0 0 0 0;">
		<div class="row no-gutters h-100">
			<div class=" col-1" id="SideMenu">
				<tiles:insertAttribute name="SideBar" />
			</div>
			<div class=" col-11 page-content" id="mainContents"
				style="padding: 0px 0px 0px 0px;">
				<div class="row no-gutters">
					<tiles:insertAttribute name="TopMenu" />
					<tiles:insertAttribute name="content" />
				</div>
			</div>
		</div>
	</div>


</body>
</html>