<%@include file="/WEB-INF/views/header.jsp"%>

<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="library.changePassword" bundle="${rb}" />
		</h3>
	</div>

	<div class="row-fluid">
		<div class="col-sm-6 col-sm-offset-3 ">
			<form action="./changePassword" method="POST" role="form">

				<c:if test="${not empty requestScope.errors}">
					<div class="alert alert-danger">
						<c:forEach items="${requestScope.errors}" var="error">
							<p>
								<fmt:message key="${error}" bundle="${rb}" />
							</p>
						</c:forEach>
					</div>
				</c:if>
				<div class="form-group">
					<label for="oldPassword"><fmt:message
							key="library.oldPassword" bundle="${rb}" /></label> <input
						type="password" class="form-control" id="oldPassword"
						name="oldPassword"
						placeholder="<fmt:message key="library.oldPassword" bundle="${rb}"/>" />
				</div>
				<div class="form-group">
					<label for="newPassword"><fmt:message
							key="library.newPassword" bundle="${rb}" /></label> <input
						type="password" class="form-control" id="newPassword"
						name="newPassword"
						placeholder="<fmt:message key="library.newPassword" bundle="${rb}"/>" />
				</div>
				<div class="form-group">
					<label for="confirmNewPassword"><fmt:message
							key="library.confirmNewPassword" bundle="${rb}" /></label> <input
						type="password" class="form-control" id="confirmNewPassword"
						name="confirmNewPassword"
						placeholder="<fmt:message key="library.confirmNewPassword" bundle="${rb}"/>" />
				</div>
				<button type="submit" class="btn btn-default" id="submitButton">
					<fmt:message key="library.change" bundle="${rb}" />
				</button>
			</form>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>