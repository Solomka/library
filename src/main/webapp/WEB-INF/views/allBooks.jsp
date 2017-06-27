<%@include file="header.jsp"%>

<div class="row-fluid">
	<div class="span12" align="center">

		<h2><fmt:message key="library.allBooks" bundle="${rb}"/></h2>

		<br> <br> <br>

		<table>
			<thead>
				<tr>

					<th>#</th>
					<th>ISBN</th>
					<th>Title</th>
					<th>Publisher</th>
					<th>Imprint date</th>
					<th>Availability</th>
					<th>Action</th>

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
						<td>Action</td>


					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@include file="footer.jsp"%>
