<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Registration page</title>
</head>
<body>
	<form:form id="register-form" method="post"
		action="${pageContext.request.contextPath}/register"
		modelAttribute="registerModel">
		<center>
			<table border="1" width="30%" cellpadding="5">
				<thead>
					<tr>
						<th colspan="2">Enter Information Here</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>First Name</td>
						<td><form:input type="text" path="firstName" value="" /></td>
						<c:set var="firstNameError">
							<form:errors path='firstName' />
						</c:set>
						<c:if test="${not empty firstNameError}">
							<td class="error"><form:errors path="firstName" /></td>
						</c:if>
					</tr>
					<tr>
						<td>Last Name</td>
						<td><form:input type="text" path="lastName" value="" /></td>
						<c:set var="lastNameError">
							<form:errors path='lastName' />
						</c:set>
						<c:if test="${not empty lastNameError}">
							<td class="error"><form:errors path="lastName" /></td>
						</c:if>
					</tr>
					<tr>
						<td>Email</td>
						<td><form:input type="text" path="email" value="" /></td>
						<c:set var="emailError">
							<form:errors path='email' />
						</c:set>
						<c:if test="${not empty emailError}">
							<td class="error"><form:errors path="email" /></td>
						</c:if>
					</tr>
					<tr>
						<td>User Name</td>
						<td><form:input type="text" path="username" value="" /></td>
						<c:set var="usernameError">
							<form:errors path='username' />
						</c:set>
						<c:if test="${not empty usernameError}">
							<td class="error"><form:errors path="username" /></td>
						</c:if>
					</tr>
					<tr>
						<td>Password</td>
						<td><form:input type="password" path="password" value="" /></td>
						<c:set var="passwordError">
							<form:errors path='password' />
						</c:set>
						<c:if test="${not empty passwordError}">
							<td class="error"><form:errors path="password" /></td>
						</c:if>
					</tr>
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