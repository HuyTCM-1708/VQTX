<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" pageEncoding="utf-8"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Home</title>
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
		<h1>Vòng quanh thị xã - 2015</h1>
		<h1>CLB Học sinh - Sinh viên thị xã Long Khánh</h1>
		<P>The time on the server is ${serverTime}.</P>
		<c:if test="${not empty msg}">
			<span><font color="red">${msg}</font></span>
		</c:if>
		<form action="login" method="post">
			<table>
				<tr>
					<td>Username</td>
					<td><input type="text" name="txtUsername" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="txtPassword"></td>
				</tr>
				<tr>
					<td><input type="submit" name="btnLogin" value="Login" /></td>
					<td><input type="reset" value="Reset" /></td>
				</tr>
			</table>
		</form>
	</div>
	<footer style="background-color: #CCCCCC;">
		<p>Liên hệ:</p>
		<p>Admin - 01646056680 (Mr.Huy)</p>
		<span><font color="blue">Phát triển bởi CLB Học sinh -
				Sinh viên thị xã Long Khánh HSLK.Info®</font></span>
	</footer>
</body>
</html>
