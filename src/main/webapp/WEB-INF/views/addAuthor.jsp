<%@include file="/WEB-INF/views/header.jsp"%>

<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="library.add" bundle="${rb}" />
		</h3>
	</div>

	<div class="row-fluid">
		<div class="col-sm-6 col-sm-offset-3 ">
			<form action="./addAuthor" method="POST" role="form">

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
					<label for="name"><fmt:message key="library.name"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="name" name="name"
						placeholder="<fmt:message key="library.name"
							bundle="${rb}" />"
						value="<c:out value="${requestScope.author.getName()}" />" />
				</div>

				<div class="form-group">
					<label for="surname"><fmt:message key="library.surname"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="surname" name="surname"
						placeholder="<fmt:message key="library.surname" bundle="${rb}"/>"
						value="<c:out value="${requestScope.author.getSurname()}" />" />
				</div>
				<div class="form-group">
					<label for="country"><fmt:message key="library.country"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="country" name="country"
						placeholder="<fmt:message key="library.country" bundle="${rb}"/>"
						value="<c:out value="${requestScope.author.getCountry()}" />" />
				</div>

				<button type="submit" class="btn btn-default" id="submitButton">
					<fmt:message key="library.add" bundle="${rb}" />
				</button>
			</form>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>