<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Home</title>
</head>
<body>
	<p class="logout">
		<a href="${pageContext.request.contextPath}/logout">Logout</a>
	</p>
	
	<p>
		<a href="${pageContext.request.contextPath}/loan/history">Loan History</a>
	</p>
	
	<form:form id="register-form" method="post"
		action="${pageContext.request.contextPath}/loan/request"
		modelAttribute="loanModel">
		<center>
			<table border="1" width="30%" cellpadding="5">
				<thead>
					<tr>
						<th colspan="2">Request Loan Here</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Amount</td>
						<td><form:input type="text" path="loanAmount" value="" /></td>
						<%-- 						<c:set var="loanAmountError"> --%>
						<%-- 							<form:errors path='loanAmount' /> --%>
						<%-- 						</c:set> --%>
						<%-- 						<c:if test="${not empty loanAmountError}"> --%>
						<%-- 							<td class="error"><form:errors path="loanAmount" /></td> --%>
						<%-- 						</c:if> --%>
					</tr>
					<tr>
						<td>Expiration Date (yyyy-mm-dd)</td>
						<td><form:input type="text" path="expirationDate" value="" /></td>
						<c:set var="expirationDateError">
							<form:errors path='expirationDate' />
						</c:set>
						<c:if test="${not empty expirationDateError}">
							<td class="error"><form:errors path="expirationDate" /></td>
						</c:if>

					</tr>

					<spring:hasBindErrors name="loanModel">
						<h2>Loan Rejected!</h2>

						<c:forEach var="error" items="${errors.allErrors}">
							<c:if test="${error.field eq 'loan' }">
								<tr>
									<td class="error">${error.defaultMessage}</td>
								</tr>
							</c:if>
						</c:forEach>


					</spring:hasBindErrors>

					<tr>
						<td><input id="submitBtn" type="submit" value="Submit" /></td>
						<td><input type="reset" value="Reset" /></td>
					</tr>
				</tbody>
			</table>
		</center>
	</form:form>
</body>
</html>
