<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<c:if test="${user.getRole().getValue() eq 'librarian' }">
				<button type="button" class="btn btn-default"
					onclick="location.href='librarian/addBookInstance';">
					<fmt:message key="library.add" bundle="${rb}" />
				</button>
			</c:if>
		</div>
	</div>

	<div class="row-fluid pg-title" align="center">
		<h2>
			<fmt:message key="library.instances" bundle="${rb}" />
		</h2>
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
						${author.getName()} ${author.getSurname()} <br />
			</c:forEach>
			<br />
		</div>
	</div>

	<div class="row-fluid" align="center">

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
						<td><fmt:message
								key="${bookInstance.getStatus().getLocalizedValue()}"
								bundle="${rb}" /></td>
						<c:if test="${user.getRole().getValue() eq 'reader' }">
							<td><a
								href="./createOrder?id_book_instance=${bookInstance.getId()}"><fmt:message
										key="library.createOrder" bundle="${rb}" /></a></td>
						</c:if>


					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@include file="footer.jsp"%>
