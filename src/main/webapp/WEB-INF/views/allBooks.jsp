<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<c:if test="${user.getRole().getValue() eq 'librarian' }">
				<button type="button" class="btn btn-default"
					onclick="location.href='librarian/addBook';">
					<fmt:message key="library.add" bundle="${rb}" />
				</button>
			</c:if>
			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#search">
				<fmt:message key="library.book.searchByTitle" bundle="${rb}" />
			</button>

			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#search">
				<fmt:message key="library.book.searchByAuthor" bundle="${rb}" />
			</button>

		</div>
	</div>

	<div class="row-fluid pg-title" align="center">
		<h2>
			<fmt:message key="library.books" bundle="${rb}" />
		</h2>
	</div>


	<div class="row-fluid" align="center">

		<table class="table table-bordered">
			<thead>
				<tr>

					<th>#</th>
					<th>ISBN</th>
					<th><fmt:message key="library.title" bundle="${rb}" /></th>
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
						<td>${book.isbn}</td>
						<td>${book.title}</td>
						<td><c:forEach items="${book.getAuthors()}" var="author">
						${author.getName()} ${author.getSurname()}<br />
							</c:forEach></td>
						<td>${book.publisher}</td>
						<td><fmt:message
								key="${book.availability.getLocalizedValue()}" bundle="${rb}" /></td>
						<td><a href="./bookInstances?id_book=${book.getId()}"><fmt:message
									key="library.instances" bundle="${rb}" /></a> <br/> <c:if
								test="${user.getRole().getValue() eq 'librarian' }">
								<a href="./deleteBook?id_book=${book.getId()}"><fmt:message
										key="library.delete" bundle="${rb}" /></a>
							</c:if></td>


					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@include file="footer.jsp"%>
