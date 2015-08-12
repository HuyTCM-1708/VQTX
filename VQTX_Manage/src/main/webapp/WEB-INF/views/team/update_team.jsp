<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="width=device-width,height=90%,  user-scalable = no">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Thay đổi thông tin đội chơi</title>
</head>
<body>
<h4>Cập nhật thông tin đội chơi:</h4>
	<form action="updateTeam" onsubmit="return confirm('Thông tin đội sẽ không được thay đổi\n Bạn đã chắc chắn mọi thông tin là chính xác?');">
		<table>
			<tr>
				<th>Tên đội:</th>
				<td><input type="text" name="teamName"></td>
			</tr>
			<tr>
				<th>Tên đội trưởng:</th>
				<td><input type="text" name="teamLead"></td>
			</tr>
			<tr>
				<th>SĐT đội trưởng:</th>
				<td><input type="text" name="phone"></td>
			</tr>
			<tr>
				<th>Thành viên 1:</th>
				<td><input type="text" name="member1"></td>
			</tr>
			<tr>
				<th>Thành viên 2:</th>
				<td><input type="text" name="member2"></td>
			</tr>
			<tr>
				<th>Thành viên 3:</th>
				<td><input type="text" name="member3"></td>
			</tr>
			<tr>
				<th>Thành viên 4:</th>
				<td><input type="text" name="member4"></td>
			</tr>
			<tr>
				<th>Thành viên 5:</th>
				<td><input type="text" name="member5"></td>
			</tr>
			<tr>
				<th>Thành viên 6:</th>
				<td><input type="text" name="member6"></td>
			</tr>
			<tr>
				<th>Thành viên 7:</th>
				<td><input type="text" name="member7"></td>
			</tr>
			<tr>
				<th></th>
				<th><input type="submit" value="Xác nhận"></th>
			</tr>
		</table>
	</form>
</body>
</html>