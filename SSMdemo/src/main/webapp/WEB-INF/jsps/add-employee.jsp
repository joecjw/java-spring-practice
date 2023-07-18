<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>New Employee Page</title>
</head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<body>
	<div class="container">
		<h1>Employee Details</h1>
		<hr />

		<s:form action="addEmployeeProcess" modelAttribute="employee">

			<input id="hidden-txt" type="hidden" value="${employee.hobbies}">

			<s:hidden path="id" />
		
					<div class="form-group">
				<s:input path="fullname" class="form-control"
				placeholder="Enter Full Name" />
			</div>

			<div class="form-group">
				<s:input path="email" placeholder="Enter Email" class="form-control" />
			</div>
			
			<div class="form-group">
				<div class="form-check-inline">
					<label class="form-check-label">
						<s:radiobutton path="gender" value="Male" class="form-check-input"/>Male
					</label>
				</div>
			
				<div class="form-check-inline">
					<label class="form-check-label">
						<s:radiobutton path="gender" value="Female" class="form-check-input"/>Female
					</label>
				</div>
			</div>

			<div class="form-group">
				<div class="form-check-inline">
					<label class="form-check-label"> <s:checkbox path="hobbies"
							value="Sports" class="form-check-input" />Sports
					</label>
				</div>

				<div class="form-check-inline">
					<label class="form-check-label"> <s:checkbox path="hobbies"
							value="Browsing" class="form-check-input" />Browsing
					</label>
				</div>

				<div class="form-check-inline">
					<label class="form-check-label"> <s:checkbox path="hobbies"
							value="Running" class="form-check-input" />Running
					</label>
				</div>

				<div class="form-check-inline">
					<label class="form-check-label"> <s:checkbox path="hobbies"
							value="Swimming" class="form-check-input" />Swimming
					</label>
				</div>
				<div class="form-check-inline">
					<label class="form-check-label"> <s:checkbox path="hobbies"
							value="Reading" class="form-check-input" />Reading
					</label>
				</div>
				<div class="form-check-inline">
					<label class="form-check-label"> <s:checkbox path="hobbies"
							value="Cycling" class="form-check-input" />Cycling
					</label>
				</div>
			</div>

			<div class="form-group">
				<s:select path="country" class="form-control">
					<s:option value="0">--Select--</s:option>
					<s:option value="India">India</s:option>
					<s:option value="Australia">Australia</s:option>
					<s:option value="Japan">Japan</s:option>
					<s:option value="America">America</s:option>
					<s:option value="South Africa">South Africa</s:option>
					<s:option value="Sri Lanka">Sri Lanka</s:option>
					<s:option value="China">China</s:option>
				</s:select>
			</div>

			<div class="form-group">
				<s:textarea path="address" placeholder="Enter Address"
				class="form-control" />
			</div>
		
			<input type="submit" value="Save" class="btn btn-info" />&nbsp;

		</s:form>
		<hr />
		<button onclick="window.location.href='.html' "class="btn btn-info">Back to
			Employee List</button>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var hobbies = $("#hidden-txt").val().split(",");
			var $checkboxes = $("input[type=checkbox]");
			$checkboxes.each(function(idx, element) {
				if (hobbies.indexOf(element.value) != -1) {
					element.setAttribute("checked", "checked");
					$("#hobbies").val("");
				} else {
					element.removeAttribute("checked");
				}
			});
		});
	</script>
</body>
</html>