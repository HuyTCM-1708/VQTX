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
<title>Đội chơi</title>
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
			<th style="background-color: green;">
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
	<!-- Content -->
	<h5>Xin chào, ${USER.username}</h5>
	<c:if test="${empty SCORE}">
		<form action="submitCryptogramCode">
			<table>
				<tr>
					<th>Nhập mã mật thư:</th>
					<c:if test="${not empty msg}">
						<span><font color="red">${msg}</font></span>
					</c:if>
					<td><input type="text" name="cryptogramCode"></td>
					<td><button>Gửi</button></td>
				</tr>
			</table>
		</form>
	</c:if>
	<c:if test="${not empty SCORE}">
		<c:if test="${not SCORE.processing}">
		<c:if test="${not empty CURRSTATION}">
			<span><font color="blue">Hướng dẫn đường đi: ${CURRSTATION.place}</font></span>
		</c:if>
			<form action="enrollStation" method="post">
				<input type="text" name="enrollCode" placeholder="Mã nhập trạm" />
				<input type="hidden" name="stationCode"
					value="${SCORE.scorePK.stationCode}" />
				<button>Xác nhận</button>
			</form>
			<br />
			<c:if test="${SCORE.numOfHint < 3}">
				<form action="getHint" onsubmit="return confirm('Nhận gợi ý sẽ bị mất điểm.\n Bạn vẫn muốn tiếp tục?');">
					<input type="hidden" name="stationCode"
						value="${SCORE.scorePK.stationCode}" /> <input type="hidden"
						name="numOfHint" value="${SCORE.numOfHint}" /> <input
						type="submit" value="Nhận gợi ý ${SCORE.numOfHint + 1}"/>
				</form>
			</c:if>
			<c:if test="${not empty HINT}">
				<p id="hint">${HINT}</p>
			</c:if>
		</c:if>
		<c:if test="${SCORE.processing}">
		<c:if test="${not empty CURRSTATION}">
			<span><font color="blue">Chào mừng bạn đến trạm: ${CURRSTATION.stationName}</font></span>
		</c:if>
			<h1>Đang thực hiện thử thách...</h1>
			<form action="overStation" method="post">
				Mã xuất trạm: <input type ="text" name="overCode" placeholder="Mã xuất trạm" />
				<input type="hidden" name="stationCode"
					value="${SCORE.scorePK.stationCode}" />
				<button>Xác nhận</button>
			</form>
		</c:if>
	</c:if>
</body>
</html>