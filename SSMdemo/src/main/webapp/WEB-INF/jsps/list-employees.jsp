<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Employee Members Page</title>
</head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<body>
	<div class="container">
		<h1>Employee Details</h1>
		<p align="right">
			<button
				onclick="window.location.href = 'showFormForAddEmployee.html'"
				class="btn btn-primary">Add Employee</button>
		</p>
		<hr />
		<table border="1" class="table table-striped table-bordered">
			<thead class="thead-dark">
				<tr>
					<th>Full Name</th>
					<th>Email</th>
					<th>Gender</th>
					<th>Hobbies</th>
					<th>Country</th>
					<th>Address</th>
					<th colspan="2">Actions</th>
				</tr>
			</thead>

			<c:forEach items="${listemployees}" var="e">

				<c:url var="deletelink" value="/deleteEmployee">
					<c:param name="employeeId" value=" ${e.id}"></c:param>
				</c:url>

				<c:url var="updatelink" value="/updateEmployeeProcessRedirect">
					<c:param name="employeeId" value=" ${e.id}"></c:param>
				</c:url>

				<tbody>
					<tr>
						<td>${e.fullname}</td>
						<td>${e.email}</td>
						<td>${e.gender}</td>
						<td>${e.hobbies}</td>
						<td>${e.country}</td>
						<td>${e.address}</td>
						<td><a href="${updatelink}">Update</a></td>
						<td><a href="${deletelink}"
							onclick="if(!(confirm('Are you sure to DELETE the record?'))) return false;">Delete</a></td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
</body>
</html>