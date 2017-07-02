<%@include file="WEB-INF/views/header.jsp"%>
<div class="container-fluid" align="center">

	<div class="row-fluid">

		<c:if test="${not empty param.general_error}">
			<div class="alert alert-danger">
				<fmt:message key="${param.general_error}" bundle="${rb}" />
			</div>
		</c:if>
	</div>

	<div class="row-fluid">
		<h2>
			<fmt:message key="library.greeting" bundle="${rb}" />
		</h2>
	</div>
</div>
<%@include file="WEB-INF/views/footer.jsp"%>
