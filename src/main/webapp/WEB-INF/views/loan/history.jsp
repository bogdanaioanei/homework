<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Loan History</title>
</head>
<body>
	<p class="logout">
		<a href="${pageContext.request.contextPath}/logout">Logout</a>
	</p>

	<p>
		<a href="${pageContext.request.contextPath}/">Home</a>
	</p>

	<center>
		<table border="1" width="100%" cellpadding="10">
			<thead>
				<tr>
					<th colspan="2">Loan History for
						${loanHistory.firstName}${loanHistory.lastName}</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td></td>
					<td>Amount</td>
					<td>Amount Paid</td>
					<td>Amount Left to Pay</td>
					<td>Expiration Date</td>
					<td>Initial Interest Rate</td>
					<td>Current Interest Rate</td>
				</tr>
				<c:forEach var="loan" items="${loanHistory.loans}">

					<form action="${pageContext.request.contextPath}/loan/extend"
						method="post">
						<tr>
							<td>Loan</td>
							<td>${loan.loanAmount}</td>
							<td>${loan.amountPaid}</td>
							<td>${loan.amountLeftToPay}</td>
							<td>${loan.expirationDate}</td>
							<td>${loan.initialInterestRate}</td>
							<td>${loan.currentInterestRate}</td>
							<input type="hidden" name="loanUID" value="${loan.loanUID}" />
							<td><input type="submit" value="Extend" /></td>
						</tr>
					</form>
					<c:if test="${not empty loan.loanExtensions}">
						<tr>
							<td>Loan extensions for loan</td>
							<td></td>
							<td>New Expiration Date</td>
							<td>Interest Rate Multiplier</td>
							<td>New Interest Rate</td>
						</tr>
						<c:forEach var="loanExtension" items="${loan.loanExtensions}">
							<tr>
								<td>Loan extension</td>
								<td></td>
								<td>${loanExtension.newExpirationDate}</td>
								<td>${loanExtension.interestRateMultiplier}</td>
								<td>${loanExtension.newInterestRate}</td>

							</tr>
						</c:forEach>
					</c:if>


				</c:forEach>

			</tbody>
		</table>
	</center>
</body>
</html>
