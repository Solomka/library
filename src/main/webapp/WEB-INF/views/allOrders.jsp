<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="center">
		<h2>
			<fmt:message key="library.orders" bundle="${rb}" />
		</h2>
	</div>

	<div class="row-fluid" align="center">
		<c:if test="${not empty param.success}">
			<div class="alert alert-success">
				<fmt:message key="${param.success}" bundle="${rb}" />
			</div>
		</c:if>
	</div>

</div>

<%@include file="footer.jsp"%>
