<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="width=device-width,height=90%,  user-scalable = no">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<script
	src="${pageContext.servletContext.contextPath}/resources/jquery-1.11.3.js"></script>
<title>Bảng tổng sắp</title>
<style>
table {
	border-collapse: collapse;
}

table, th {
	border: 1px solid black;
	padding: 10px 10px 10px;
}
</style>
</head>
<body>
	<!-- Menu -->
	<table>
		<tr>
			<th><c:url var="team_home"
					value="teamPage"></c:url> <a href="${team_home}">Trang chủ</a></th>
			<th><c:url var="team_score" value="teamScore"></c:url> <a
				href="${team_score}">Bảng điểm đội</a></th>
			<th style="background-color: green;"><c:url var="finalScore" value="finalScore"></c:url> <a
				href="${finalScore}">Bảng tổng sắp</a></th>
		</tr>
	</table>
	<!-- Content -->
	<c:if test="${not empty FINALSCORE}">
		<table border="1">
			<tr>
				<th>Đội</th>
				<th>Tổng điểm</th>
				<th>Thứ hạng</th>
			</tr>
			<c:forEach var="result" items="${FINALSCORE}" varStatus="counter">
				<tr>
					<td>${result.key}</td>
					<td>${result.value}</td>
					<td>${counter.count}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>