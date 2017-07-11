<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<c:if test="${user.getRole().getValue() eq 'librarian' }">
				<button type="button" class="btn btn-default" data-toggle="modal"
					data-target="#addBookInstance">
					<fmt:message key="library.add" bundle="${rb}" />
				</button>
			</c:if>
		</div>
	</div>

	<!-- modal -->

	<div class="modal fade" id="addBookInstance" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<fmt:message key="library.add" bundle="${rb}" />
					</h4>
				</div>
				<div class="modal-body">
					<form action="./addBookInstance" method="POST" role="form">

						<div class="form-group">
							<label for="inventoryNumber"><fmt:message
									key="library.inventoryNumber" bundle="${rb}" /></label> <input
								type="text" class="form-control" id="inventoryNumber"
								name="inventoryNumber" /> <input type="hidden" name="id_book"
								value="${param.id_book}">
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-default" id="submitButton">
								<fmt:message key="library.add" bundle="${rb}" />
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="row-fluid pg-title" align="center">
		<h2>
			<fmt:message key="library.instances" bundle="${rb}" />
		</h2>
	</div>

	<div class="row-fluid" align="center">
		<c:if test="${not empty param.success}">
			<div class="alert alert-success">
				<fmt:message key="${param.success}" bundle="${rb}" />
			</div>
		</c:if>
	</div>
	<div class="row-fluid" align="center">
		<c:if test="${not empty param.error}">
			<div class="alert alert-danger">
				<fmt:message key="${param.error}" bundle="${rb}" />
			</div>
		</c:if>
	</div>

	<div class="row-fluid overflow-hidden">
		<div class="book-descr">
			<strong><fmt:message key="library.book.book" bundle="${rb}" /></strong>
		</div>
		<div class="book-descr">
			<fmt:message key="library.title" bundle="${rb}" />
			: <br />
			<fmt:message key="library.authors" bundle="${rb}" />
			:
		</div>
		<div class="float-left">
			${book.getTitle()}<br />
			<c:forEach items="${book.getAuthors()}" var="author">
						${author.getName()} ${author.getSurname()}&nbsp;
			</c:forEach>
			<br />
		</div>
	</div>

	<div class="row-fluid top-margin" align="center">

		<table class="table table-bordered">
			<thead>
				<tr>
					<th>#</th>
					<th><fmt:message key="library.inventoryNumber" bundle="${rb}" /></th>
					<th><fmt:message key="library.status" bundle="${rb}" /></th>
					<c:if test="${user.getRole().getValue() eq 'reader' }">
						<th>***</th>
					</c:if>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${book.getBookInstances()}" var="bookInstance"
					varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${bookInstance.getInventoryNumber()}</td>
						<td><c:if test="${not empty bookInstance.getStatus()}">
								<fmt:message
									key="${bookInstance.getStatus().getLocalizedValue()}"
									bundle="${rb}" />
							</c:if></td>
						<c:if test="${user.getRole().getValue() eq 'reader' }">
							<td><a
								href="./reader/createOrder?id_book_instance=${bookInstance.getId()}"><fmt:message
										key="library.createOrder" bundle="${rb}" /></a></td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@include file="footer.jsp"%>
