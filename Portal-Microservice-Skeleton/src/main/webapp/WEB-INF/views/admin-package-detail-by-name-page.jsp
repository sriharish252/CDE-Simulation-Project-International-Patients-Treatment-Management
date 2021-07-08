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
				<h1>Find In-patient Services</h1>
				<div class="container">
					<form:form action="ipTreatmentPackageByName" method="GET"
						modelAttribute="formInputsGetByPackageName">
						<div class="form-group">
							<form:label path="ailment">Select Ailment:</form:label>
							<form:select path="ailment" class="form-control" id="ailment"
								items="${ailmentList}" required="required" />
						</div>
						<div class="form-group">
							<form:label path="packageName">Select Package Name:</form:label>
							<form:select path="packageName" class="form-control"
								id="packageName" items="${packageList}" required="required" />
						</div>
						<form:button class="btn">Search</form:button>
					</form:form>
				</div>
				<div class="container result-container center border">
					<h3>Search Result</h3>
						<c:if test="${fn:length(ipTreatmentPackageNames) eq 0}">
							<h4>No packages to show!</h4>
							<br/>
						</c:if>
						<c:if test="${fn:length(ipTreatmentPackageNames) gt 0}">
					 	<table class="table table-striped">
							<thead>
								<tr>
									<th>Ailment</th>
									<th>Package Name</th>
									<th>Test Details</th>
									<th>Cost</th>
									<th>Duration</th>
								</tr>
							</thead>
							<tbody>
							 	<c:forEach items="${ipTreatmentPackageNames}" var="package"
									varStatus="loop">
									<tr>
										<td>${package.getAilmentCategory()}</td>
										<td>${package.getPackageDetail().getTreatmentPackageName()}</td>
										<td>${package.getPackageDetail().getTestDetails()}</td>
										<td>${package.getPackageDetail().getCost()}</td>
										<td>${package.getPackageDetail().getTreatmentDuration()}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</c:if>
				</div>
			</div>
		</div>
		<%@ include file="fragments/footer.jsp"%>
	</div>
</body>
</html>