<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- LOCALE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<body>
	<h2>All Books</h2>

	<br>
	<br>
	<br>

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
</body>
</html>
