<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<button type="button" class="btn btn-default"
				onclick="location.href='./addReader';">
				<fmt:message key="library.add" bundle="${rb}" />
			</button>
		</div>
	</div>

	<div class="row-fluid" align="center">
		<h2>
			<fmt:message key="library.readers" bundle="${rb}" />
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
					<th><fmt:message key="library.readerCardNumber" bundle="${rb}" /></th>
					<th><fmt:message key="library.surname" bundle="${rb}" /></th>
					<th><fmt:message key="library.name" bundle="${rb}" /></th>
					<th><fmt:message key="library.patronymic" bundle="${rb}" /></th>
					<th><fmt:message key="library.email" bundle="${rb}" /></th>
					<th><fmt:message key="library.phone" bundle="${rb}" /></th>
					<th><fmt:message key="library.address" bundle="${rb}" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${readers}" var="reader" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${reader.getReaderCardNumber()}</td>
						<td>${reader.getSurname()}</td>
						<td>${reader.getName()}</td>
						<td>${reader.getPatronymic()}</td>
						<td>${reader.getEmail()}</td>
						<td>${reader.getPhone()}</td>
						<td>${reader.getAddress()}</td>						
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@include file="footer.jsp"%>
