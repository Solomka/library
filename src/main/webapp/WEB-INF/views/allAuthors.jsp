<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<button type="button" class="btn btn-default"
				onclick="location.href='./addAuthor';">
				<fmt:message key="library.add" bundle="${rb}" />
			</button>
		</div>
	</div>

	<div class="row-fluid" align="center">
		<h2>
			<fmt:message key="library.authors" bundle="${rb}" />
		</h2>
	</div>

	<div class="row-fluid" align="center">
		<c:if test="${not empty param.success}">
			<div class="alert alert-success">
				<fmt:message key="${param.success}" bundle="${rb}" />
			</div>
		</c:if>
	</div>
	<div class="row-fluid top-margin" align="center">
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
				<c:forEach items="${authors}" var="author" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${author.getName()}</td>
						<td>${author.getSurname()}</td>
						<td>${author.getCountry()}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@include file="footer.jsp"%>
