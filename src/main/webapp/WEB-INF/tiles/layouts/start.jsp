<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="./css/login.css" rel="stylesheet">
<link href="./css/workorder.css" rel="stylesheet">
<c:url var="bootstrap" value="./css/bootstrap.css" />
<link href="${ bootstrap }" rel="stylesheet">
<link rel="shortcut icon" href="#">
<link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
<title>ROMES 3.0</title>
</head>
<body>



	<tiles:insertAttribute name="content" />

	
	<script src="js/jquery-3.6.0.min.js"></script>
	<script src="https://unpkg.com/axios@0.26.1/dist/axios.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></Script>
	<script type="module" src="js/login.js"></script>
	<script type="module" src="js/work.js"></script>
	
</body>
</html>