<%@include file="header.jsp"%>

<div class="container-fluid" align="center">
	<div class="row-fluid pg-title">
		<h2>
			<fmt:message key="library.authors" bundle="${rb}" />
		</h2>
	</div>
	<div class="row-fluid">

		<table class="table table-bordered">
			<thead>
				<tr>

					<th>#</th>
					<th><fmt:message key="library.name" bundle="${rb}" /></th>
					<th><fmt:message key="library.surname" bundle="${rb}" /></th>
					<th><fmt:message key="library.country" bundle="${rb}" /></th>

				</tr>
			</thead>

			<tbody>
				<c:forEach items="${bookAuthors}" var="bookAuthor"
					varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${bookAuthor.getName()}</td>
						<td>${bookAuthor.getSurname()}</td>
						<td>${bookAuthor.getCountry()}</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@include file="footer.jsp"%>
