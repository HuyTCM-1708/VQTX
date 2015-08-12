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
<title>Insert title here</title>
</head>
<body>
	<!-- Menu -->
	<table>
		<tr>
			<th style="background-color: blue;">
				<c:url var="team_home" value="teamPage"></c:url>
				<a href="${team_home}">Trang chủ</a>
			</th>
			<th>
				<c:url var="team_score" value="teamScore"></c:url>
				<a href="${team_score}">Bảng điểm đội</a>
			</th>
			<th>
				<c:url var="finalScore" value="finalScore"></c:url>
				<a href="${finalScore}">Bảng tổng sắp</a>
			</th>
		</tr>
	</table>
	<c:if test="${not empty msg}">
		<span><font color="red">${msg}</font></span>
	</c:if>
	<c:if test="${not empty LISTSCORES}">
		<table border="1">
			<tr>
				<th>Trạm</th>
				<th>Điểm mật thư</th>
				<th>Thử thách 1</th>
				<th>Thử thách 2</th>
				<th>Thử thách 3</th>
				<th>Điểm cộng</th>
				<th>Điểm trừ</th>
				<th>Điểm tổng</th>
			</tr>
			<c:forEach var="score" items="${LISTSCORES}">
				<tr>
					<td>${score.scorePK.stationCode}</td>
					<td>${score.cryptogramScore}</td>
					<td>${score.score1}</td>
					<td>${score.score2}</td>
					<td>${score.score3}</td>
					<td>${score.bonus}</td>
					<td>${score.penalty}</td>
					<td>${score.sumScore}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>