<%@include file="header.jsp"%>

<c:if test="${user.getRole().getValue() eq 'librarian' }">
	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<button type="button" class="btn btn-default"
				onclick="location.href='addBook';">
				<fmt:message key="library.addBook" bundle="${rb}" />
			</button>
		</div>
	</div>
</c:if>

<div class="container-fluid" align="center">
	<div class="row-fluid pg-title">
		<h2>
			<fmt:message key="library.books" bundle="${rb}" />
		</h2>
	</div>


	<div class="row-fluid">

		<table class="table table-bordered">
			<thead>
				<tr>

					<th>#</th>
					<th>ISBN</th>
					<th><fmt:message key="library.title" bundle="${rb}" /></th>
					<th><fmt:message key="library.publisher" bundle="${rb}" /></th>
					<th><fmt:message key="library.imprintDate" bundle="${rb}" /></th>
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
						<td>${book.publisher}</td>
						<td>${book.imprintDate}</td>
						<td>${book.availability}</td>
						<td><a href="./instances?id_book=${book.getId()}"><fmt:message
									key="library.instances" bundle="${rb}" /></a> <br /> <a
							href="./bookAuthors?id_book=${book.getId()}"><fmt:message
									key="library.authors" bundle="${rb}" /></a><br /> <c:if
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
