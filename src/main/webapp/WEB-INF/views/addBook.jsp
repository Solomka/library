<%@include file="/WEB-INF/views/header.jsp"%>

<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="library.addBook" bundle="${rb}" />
		</h3>
	</div>

	<div class="row-fluid">
		<div class="col-sm-6 col-sm-offset-3 ">
			<form action="./addBook" method="POST" role="form">

				<c:if test="${not empty requestScope.errors}">
					<div class="alert alert-danger">
						<c:forEach items="${requestScope.errors}" var="error">
							<fmt:message key="${error}" bundle="${rb}" />
							<br>
						</c:forEach>
					</div>
				</c:if>
				
				<div class="form-group">
					<label for="isbn">ISBN</label> <input type="text" class="form-control"
						id="ISBN" name="isbn"
						placeholder="ISBN"
						 />
				</div>

				<div class="form-group">
					<label for="title"><fmt:message key="library.title"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="title" name="title"
						placeholder="<fmt:message key="library.title" bundle="${rb}"/>"
						 />
				</div>
				<div class="form-group">
					<label for="publisher"><fmt:message key="library.publisher"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="publisher" name="publisher"
						placeholder="<fmt:message key="library.publisher" bundle="${rb}"/>"
						 />
				</div>
				<div class="form-group">
					<label for="imprintDate"><fmt:message key="library.imprintDate"
							bundle="${rb}" /></label> <input type="date" class="form-control"
						id="imprintDate" name="imprintDate"
						placeholder="yy-mm-dd" />
				</div>
				
				
				<button type="submit" class="btn btn-default" id="submitButton">
					<fmt:message key="library.add" bundle="${rb}" />
				</button>
			</form>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>