<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="lang" scope="session"
	value="${empty sessionScope.locale ? 'en_US' : sessionScope.locale}" />
<fmt:setLocale value="${lang}" scope="session" />
<fmt:setBundle basename="/i18n/exceptions" var="rb" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${lang}">
<title>Error Page</title>
<body>
	<div class="container-fluid" align="center">
		<div class="row-fluid">
			<div class="span12" align="center">
				<div class="alert alert-danger">
					<strong> ${pageContext.errorData.statusCode}</strong> <strong><fmt:message
							key="error.serverError" bundle="${rb}" /></strong>
					<fmt:message key="${pageContext.exception.message}" bundle="${rb}" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>