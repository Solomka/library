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
	<div class="row-fluid" align="center">
		<c:if test="${not empty param.error}">
			<div class="alert alert-danger">
				<fmt:message key="${param.error}" bundle="${rb}" />
			</div>
		</c:if>
	</div>
	
	<div class="row-fluid top-margin" align="center">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>#</th>
					<c:if test="${user.getRole().getValue() eq 'librarian' }">
					<th><fmt:message key="library.readerCardNumber" bundle="${rb}" /></th>
					</c:if>
					<th><fmt:message key="library.inventoryNumber" bundle="${rb}" /></th>
					<th><fmt:message key="library.authors" bundle="${rb}" /></th>
					<th><fmt:message key="library.publisher" bundle="${rb}" /></th>
					<th><fmt:message key="library.availability" bundle="${rb}" /></th>
					<th>***</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${books}" var="book" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${book.getIsbn()}</td>
						<td>${book.getTitle()}</td>
						<td><c:forEach items="${book.getAuthors()}" var="author">
						${author.getName()} ${author.getSurname()}<br />
							</c:forEach></td>
						<td>${book.publisher}</td>
						<td><fmt:message
								key="${book.availability.getLocalizedValue()}" bundle="${rb}" /></td>
						<td><a href="./bookInstances?id_book=${book.getId()}"><fmt:message
									key="library.instances" bundle="${rb}" /></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</div>

<%@include file="footer.jsp"%>
