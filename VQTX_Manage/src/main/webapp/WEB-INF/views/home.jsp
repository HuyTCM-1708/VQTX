<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" pageEncoding="utf-8"%>
<html>
<head>
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
<div class="col-4"></div>
<div class="col-4">
    <h1>Vòng quanh thị xã - HSLK.Info</h1>

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
</body>
</html>