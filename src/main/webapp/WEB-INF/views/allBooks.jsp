<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<c:if test="${user.getRole().getValue() eq 'librarian' }">
				<button type="button" class="btn btn-default"
					onclick="location.href='./librarian/addBook';">
					<fmt:message key="library.add" bundle="${rb}" />
				</button>
			</c:if>
			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#searchByTitle">
				<fmt:message key="library.book.searchByTitle" bundle="${rb}" />
			</button>
			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#searchByAuthor">
				<fmt:message key="library.book.searchByAuthor" bundle="${rb}" />
			</button>
		</div>
	</div>

	<!-- modal -->
	<div class="modal fade" id="searchByTitle" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<fmt:message key="library.book.searchByTitle" bundle="${rb}" />
					</h4>
				</div>
				<div class="modal-body">
					<form
						action="${pageContext.request.contextPath}/controller/books/title"
						method="POST" role="form">

						<div class="form-group">
							<label for="title"><fmt:message key="library.title"
									bundle="${rb}" /></label> <input type="text" class="form-control"
								id="title" name="title" />
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

	<div class="modal fade" id="searchByAuthor" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<fmt:message key="library.book.searchByAuthor" bundle="${rb}" />
					</h4>
				</div>
				<div class="modal-body">
					<form
						action="${pageContext.request.contextPath}/controller/books/author"
						method="POST" role="form">

						<div class="form-group">
							<label for="author"><fmt:message key="library.author"
									bundle="${rb}" /></label> <input type="text" class="form-control"
								id="author" name="author" />
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
			<fmt:message key="library.books" bundle="${rb}" />
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
					<th>ISBN</th>
					<th><fmt:message key="library.title" bundle="${rb}" /></th>
					<th><fmt:message key="library.authors" bundle="${rb}" /></th>
					<th><fmt:message key="library.publisher" bundle="${rb}" /></th>
					<th><fmt:message key="library.availability" bundle="${rb}" /></th>
					<th>***</th>
				</tr>
			</thead>
			<tbody>
			<%-- pagination limit and offset --%>
				<c:set var="limit" value="${limit}" />
				<c:set var="offset" value="${(currentPage-1)*limit}" />

				<c:forEach items="${books}" var="book" varStatus="status">
					<tr>
						<c:choose>
							<c:when test="${not empty currentPage}">
								<td>${offset + (status.index + 1)}</td>
							</c:when>
							<c:otherwise>
								<td>${status.index + 1}</td>
							</c:otherwise>
						</c:choose>
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

	<%--show pagination --%>
	<c:if test="${not empty currentPage}">
		<div class="row">
			<nav aria-label="...">
				<ul class="pager">
					<!-- display Previous link except for the 1st page -->
					<c:choose>
						<c:when test="${currentPage != 1}">
							<li><a href="./books?page=${currentPage - 1}"><span
									aria-hidden="true">&larr;</span> <fmt:message
										key="library.pagination.previous" bundle="${rb}" /></a></li>
						</c:when>
						<c:otherwise>
							<li class="disabled"><a href="javascript:void(0)"><span aria-hidden="true">&larr;</span>
									<fmt:message key="library.pagination.previous" bundle="${rb}" /></a></li>
						</c:otherwise>
					</c:choose>
					<!-- 	display Next link except for the last page -->
					<c:choose>
						<c:when test="${currentPage lt numberOfPages}">
							<li><a href="./books?page=${currentPage + 1}"><fmt:message
										key="library.pagination.next" bundle="${rb}" /><span
									aria-hidden="true">&rarr;</span></a></li>
						</c:when>
						<c:otherwise>
							<li class="disabled"><a href="javascript:void(0)"><fmt:message
										key="library.pagination.next" bundle="${rb}" /><span
									aria-hidden="true">&rarr;</span></a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</nav>
		</div>
	</c:if>
</div>

<%@include file="footer.jsp"%>
