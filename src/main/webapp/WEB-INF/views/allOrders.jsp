<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<c:if test="${user.getRole().getValue() eq 'librarian' }">
				<button type="button" class="btn btn-default" data-toggle="modal"
					data-target="#searchByReaderCardNumber">
					<fmt:message key="library.order.searchByReaderCardNumber"
						bundle="${rb}" />
				</button>
				<button type="button" class="btn btn-default"
					onclick="location.href='${pageContext.request.contextPath}/controller/librarian/orders/unfulfilled';">
					<fmt:message key="library.order.unfulfilled" bundle="${rb}" />
				</button>
				<button type="button" class="btn btn-default"
					onclick="location.href='${pageContext.request.contextPath}/controller/librarian/orders/outstanding';">
					<fmt:message key="library.order.outstanding" bundle="${rb}" />
				</button>
				<button type="button" class="btn btn-default"
					onclick="location.href='${pageContext.request.contextPath}/controller/librarian/orders/toReadingRoom';">
					<fmt:message key="library.order.toReadingRoom" bundle="${rb}" />
				</button>				
			</c:if>
		</div>
	</div>

	<!-- modal -->
	<div class="modal fade" id="searchByReaderCardNumber" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<fmt:message key="library.order.searchByReaderCardNumber"
							bundle="${rb}" />
					</h4>
				</div>
				<div class="modal-body">
					<form
						action="${pageContext.request.contextPath}/controller/librarian/orders/readerCardNumber"
						method="GET" role="form">
						<div class="form-group">
							<label for="readerCardNumber"><fmt:message
									key="library.readerCardNumber" bundle="${rb}" /></label> <input
								type="text" class="form-control" id="readerCardNumber"
								name="readerCardNumber" />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-default" id="submitButton">
								<fmt:message key="library.search" bundle="${rb}" />
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

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
						<th><fmt:message key="library.readerCardNumber"
								bundle="${rb}" /></th>
					</c:if>
					<th><fmt:message key="library.inventoryNumber" bundle="${rb}" /></th>
					<th><fmt:message key="library.orderCreationDate"
							bundle="${rb}" /></th>
					<th><fmt:message key="library.orderFulfilmentDate"
							bundle="${rb}" /></th>
					<c:if test="${user.getRole().getValue() eq 'librarian' }">
						<th><fmt:message key="library.orderIssuanceDate"
								bundle="${rb}" /></th>
					</c:if>
					<th><fmt:message key="library.orderReturnDate" bundle="${rb}" /></th>
					<c:if test="${user.getRole().getValue() eq 'librarian' }">
						<th><fmt:message key="library.orderActualReturnDate"
								bundle="${rb}" /></th>
						<th>***</th>
					</c:if>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orders}" var="order" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<c:if test="${user.getRole().getValue() eq 'librarian' }">
							<td><a
								href="${pageContext.request.contextPath}/controller/librarian/reader?id_reader=${order.getReader().getId()}">${order.getReader().getReaderCardNumber()}</a></td>
						</c:if>
						<td><a
							href="${pageContext.request.contextPath}/controller/book?id_book_instance=${order.getBookInstance().getId()}">
								${order.getBookInstance().getInventoryNumber()}</a></td>
						<td>${order.getCreationDate()}</td>
						<td>${order.getFulfilmentDate()}</td>
						<c:if test="${user.getRole().getValue() eq 'librarian' }">
							<td>${order.getPickUpDate()}</td>
						</c:if>
						<td>${order.getReturnDate()}</td>
						<c:if test="${user.getRole().getValue() eq 'librarian' }">
							<td>${order.getActualReturnDate()}</td>

							<td><c:choose>
									<c:when test="${not empty back_to_reading_room }">
										<a
											href="${pageContext.request.contextPath}/controller/librarian/orders/return?id_order=${order.getId()}"><fmt:message
												key="library.backToReadingRoom" bundle="${rb}" /></a>
									</c:when>
									<c:otherwise>
										<a
											href="${pageContext.request.contextPath}/controller/librarian/orders/fulfil?id_order=${order.getId()}"><fmt:message
												key="library.fulfilOrder" bundle="${rb}" /></a>
										<br />
										<a
											href="${pageContext.request.contextPath}/controller/librarian/orders/issue?id_order=${order.getId()}"><fmt:message
												key="library.issueOrder" bundle="${rb}" /></a>
										<br />
										<a
											href="${pageContext.request.contextPath}/controller/librarian/orders/return?id_order=${order.getId()}"><fmt:message
												key="library.returnOrder" bundle="${rb}" /></a>
									</c:otherwise>
								</c:choose></td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</div>

<%@include file="footer.jsp"%>
