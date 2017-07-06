<%@include file="/WEB-INF/views/header.jsp"%>

<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="library.add" bundle="${rb}" />
		</h3>
	</div>

	<div class="row-fluid">
		<div class="col-sm-6 col-sm-offset-3 ">
			<form action="./addBook" method="POST" role="form">

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
					<label for="isbn">ISBN</label> <input type="text"
						class="form-control" id="ISBN" name="isbn" placeholder="ISBN"
						value="<c:out value="${requestScope.book.getIsbn()}" />" />
				</div>

				<div class="form-group">
					<label for="title"><fmt:message key="library.title"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="title" name="title"
						placeholder="<fmt:message key="library.title" bundle="${rb}"/>"
						value="<c:out value="${requestScope.book.getTitle()}" />" />
				</div>
				<div class="form-group">
					<label for="publisher"><fmt:message key="library.publisher"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="publisher" name="publisher"
						placeholder="<fmt:message key="library.publisher" bundle="${rb}"/>"
						value="<c:out value="${requestScope.book.getPublisher()}" />" />
				</div>

				<div class="form-group">
					<label for="availability"><fmt:message
							key="library.availability" bundle="${rb}" /></label><br /> <select
						name="availability" class="form-control">
						<c:forEach items="${requestScope.availabilities}"
							var="availability">
							<option value="${availability.getValue()}"><fmt:message
									key="${availability.getLocalizedValue()}" bundle="${rb}" /></option>
						</c:forEach>
					</select>
				</div>
				
				<div class="form-group">
					<label for="authors"><fmt:message
							key="library.authors" bundle="${rb}" /></label><br /> <select
						name="authors" class="form-control" multiple>
						<c:forEach items="${requestScope.authors}"
							var="author">
							<option value="${author.getId()}">
									${author.getName()} ${author.getSurname()} </option>
						</c:forEach>
					</select>
				</div>

				<button type="submit" class="btn btn-default" id="submitButton">
					<fmt:message key="library.add" bundle="${rb}" />
				</button>
			</form>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>