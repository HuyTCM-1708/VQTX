<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VQTX-Quản lý</title>
<style type="text/css">
.col-4 {
	width: 33.3333%;
	float: left;
	position: relative;
	min-height: 1px;
	padding-right: 15px;
	padding-left: 15px;
}
</style>
</head>
<body>
	<div>
		<table>
			<!-- Add station -->
			<form action="addStation">
				<tr>
					<td>Tạo trạm</td>
					<td><input type="text" name="txtNoStation" value="1" /></td>
					<td><input type="submit" value="Thêm trạm" /></td>
					<td><c:url var="createChiefAcc" value="/generateChiefAcc"></c:url>
						<a href="${createChiefAcc}">Tạo tài khoản trạm trưởng</a></td>
				</tr>
			</form>
			<!-- Add group -->
			<form action="addTeam">
				<tr>
					<td>Tạo đội</td>
					<td><input type="text" name="txtNoTeam" value="1" /></td>
					<td><input type="submit" value="Thêm đội"></td>
					<td><c:url var="createTeamAcc" value="/generateTeamAcc"></c:url>
						<a href="${createTeamAcc}">Tạo tài khoản đội chơi</a></td>
				</tr>
			</form>
		</table>${USER.role}
		Username: ${USER.username}<br /> Password: ${USER.password}

		<table border="1">
			<tr>
				<td></td>
				<c:forEach var="station" items="${listStations}">
					<td>Trạm ${station.stationCode}</td>
				</c:forEach>
			</tr>
			<c:forEach var="team" items="${listTeams}">
				<tr>
					<td>VQTX_${team.teamCode}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<footer style="background-color: #CCCCCC;">
	<p>Liên hệ:</p>
	<p>Admin - 01646056680 (Mr.Huy)</p>
	<span><font color="blue">Phát triển bởi CLB Học sinh -
			Sinh viên thị xã Long Khánh HSLK.Info®</font></span> </footer>
</body>
</html>