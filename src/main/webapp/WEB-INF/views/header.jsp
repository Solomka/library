<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="udt" uri="customtags"%>
<fmt:setLocale
	value="${empty sessionScope.locale ? 'en_US' : sessionScope.locale}"
	scope="session" />
<fmt:setBundle basename="/i18n/messages" var="rb" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Library</title>


<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/styles.css" />" />

<!--  Bootstrap -->
<!-- 
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/bootstrap-3.3.7/css/bootstrap.min.css" />" />
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-3.2.1.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/bootstrap-3.3.7/js/bootstrap.min.js"/>"></script> -->

<!--  DataTables -->
<!-- <link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/DataTables/datatables.min.css"/>" />
<script type="text/javascript"
	src="<c:url value="/resources/DataTables/datatables.min.js"/>"></script> -->
	
<!-- <link rel="stylesheet"
	href="<c:url value="/resources/font-awesome-4.7.0/css/font-awesome.min.css" />"> -->

<!--  Bootstrap -->
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<!--  DataTables -->
<script
	src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.10/js/dataTables.bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.10/css/dataTables.bootstrap.min.css">

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
	


</head>

<body>
	<div class="container-fluid">

		<div class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Main</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="allBooks">Books</a></li>
				</ul>

				<ul class="nav navbar-nav navbar-right">

					<c:if test="${not empty user }">
						<!-- Custom tag  -->
						<li><p class="navbar-text">
								<udt:user-data user="${user}" />
							</p></li>
					</c:if>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#"><i class="fa fa-language"
							aria-hidden="true"></i><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="changeLocale?lang=EN">EN</a></li>
							<li><a href="changeLocale?lang=UA">UA</a></li>
							<li><a href="changeLocale?lang=RU">RU</a></li>

						</ul></li>
					<c:choose>
						<c:when test="${not empty user}">
							<li><a href="logout"><span
									class="glyphicon glyphicon-log-out"></span> Logout</a></li>
						</c:when>
						<c:otherwise>

							<li><a href="getLoginPage"><span
									class="glyphicon glyphicon-log-in"></span> Login</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>