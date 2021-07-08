<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>International Patients Management System</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Pinyon+Script&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/style-table.css">
<link rel="stylesheet" href="/css/style-admin.css">
</head>
<body>
	<div class="main-container-register">
		<%@ include file="fragments/header.jsp"%>
		<div class="section grid">
			<%@ include file="admin-fragments/admin-sidebar.jsp"%>
			<div class="content list-container">
				<h1>Add / Update Specialist</h1>
				<div class="container">
					<form:form action="/portal/addSpecialist" method="POST"
						modelAttribute="specialistDetail">
						<div class="form-group">
							<form:label path="specialistId">Id:</form:label>
							<form:input path="specialistId" class="form-control" id="specialistId"
								type="number" required="required" />
						</div>
						<div class="form-group">
							<form:label path="name">Name:</form:label>
							<form:input path="name" name="name" class="form-control" id="name"
								type="text" required="required" 
								data-error="Please enter a valid Name." />
							<form:errors path="name" cssStyle="color:red"></form:errors>
						</div>
						<div class="form-group">
							<form:label path="areaOfExpertise">Select area of Expertise:</form:label>
							<form:select path="areaOfExpertise" name="areaOfExpertise" class="form-control" id="ailment"
								items="${ailmentList}" required="required" />
							<form:errors path="areaOfExpertise" cssStyle="color:red"></form:errors>
						</div>
						<div class="form-group">
							<form:label path="experienceInYears">Experience in years:</form:label>
							<form:input path="experienceInYears" name="experienceInYears" class="form-control" id="experienceInYears"
								type="number" required="required" />
							<form:errors path="experienceInYears" cssStyle="color:red"></form:errors>
						</div>
						<div class="form-group">
							<form:label path="contactNumber">Contact Number:</form:label>
							<form:input path="contactNumber" name="contactNumber" class="form-control" id="contactNumber"
								type="number" pattern="/(7|8|9)\d{9}/" required="required" />
							<form:errors path="contactNumber" cssStyle="color:red"></form:errors>
						</div>
						<form:button class="btn">Add / Update Specialist</form:button>
					</form:form>
				</div>
 				
			</div>
		</div>
		<%@ include file="fragments/footer.jsp"%>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="/js/script.js"></script>
</body>
</html>