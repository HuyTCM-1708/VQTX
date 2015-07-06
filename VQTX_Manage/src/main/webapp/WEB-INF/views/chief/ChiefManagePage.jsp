<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VQTX-Trạm trưởng</title>
<script src="${pageContext.servletContext.contextPath}/resources/jquery-1.11.3.js"></script>
</head>
<script type="text/javascript">
	function validate() {
	    var teamCode = document.forms["scoreForm"]["teamCode"].value;
	    if(teamCode == "0") {
	        alert("Xin vui lòng chọn mã đội!");
	        return false;
	    }
	    var score1 = document.forms["scoreForm"]["txtScore1"].value;
	    if (isNaN(score1)) {
	        alert("Điểm thử thách 1: điểm nhập vào bắt buộc phải là chữ số nguyên!");
	        return false;
	    }
	    var score2 = document.forms["scoreForm"]["txtScore2"].value;
	    if (isNaN(score2)) {
	        alert("Điểm thử thách 2: điểm nhập vào bắt buộc phải là chữ số nguyên!");
	        return false;
	    }
	    var score3 = document.forms["scoreForm"]["txtScore3"].value;
	    if (isNaN(score3)) {
	        alert("Điểm thử thách 3: điểm nhập vào bắt buộc phải là chữ số nguyên!");
	        return false;
	    }
	    var penalty = document.forms["scoreForm"]["penalty"].value;
	    if (isNaN(penalty)) {
	        alert("Điểm trừ: điểm nhập vào bắt buộc phải là chữ số nguyên!");
	        return false;
	    }
	}
</script>
<script type="text/javascript">
	function getScore(stationCode,teamCode) {
	    $.ajax({
	        dataType: "json",
	        url:"getScore",
	        data: {
	            stationCode : stationCode,
		        teamCode : teamCode
	        },
	        success: function( data ) {
	            if(data.score1 != ""){
	            $("#score1").val(data.score1);
	            //$("#score1").prop('readonly', true);
	            document.getElementById("score1").readOnly = true;
	            } else {
	                $("#score1").val("");
	                document.getElementById("score1").readOnly = false;
	            }
	            if(data.score2 != ""){
		            $("#score2").val(data.score2);
		            document.getElementById("score2").readOnly = true;
		            } else {
		                $("#score2").val("");
		                document.getElementById("score2").readOnly = false;
		            }
	            if(data.score3 != ""){
		            $("#score3").val(data.score3);
		            document.getElementById("score3").readOnly = true;
		            } else {
		                $("#score3").val("");
		                document.getElementById("score3").readOnly = false;
		            }
	            if(data.penalty != ""){
		            $("#penalty").val(data.penalty);
		            } else {
		                $("#penalty").val("");
		            }
	            if(data.note != ""){
		            $("#note").val(data.note);
		            } else {
		                $("#note").val("");
		            }
	          }
	    });
	}
</script>
<body>
    <div>
        <h1>Xin chào, ${USER.username}</h1>
        <c:if test="${not empty cookie.currStation}">
            <h1>Trạm: VQTX_Trạm_${cookie.currStation.value}</h1>
            <c:url var="deleteCurrStation" value="/deleteCurrStation" />
            <a href="${deleteCurrStation}">Chọn trạm khác</a>
            <form name="scoreForm" action="addScore" method="post" onsubmit="return validate()">
                <input type="hidden" value="${cookie.currStation.value}" name="stationCode">
                <table>
                    <tr>
                        <th>MÃ ĐỘI</th>
                        <th><select name="teamCode" onchange="getScore(${cookie.currStation.value},this.value)">
                                <option value="0"></option>
                                <c:forEach var="team" items="${listTeams}">
                                    <option value="${team.teamCode}">VQTX_DOI_${team.teamCode}</option>
                                </c:forEach>
                        </select></th>
                    </tr>
                    <tr>
                        <th>Điểm 1</th>
                        <th><input id="score1" type="text" name="txtScore1" /></th>
                        <td><input type="submit" name="submitScore1" />
                        <td>
                    </tr>
                    <tr>
                        <th>Điểm 2</th>
                        <th><input id="score2" type="text" name="txtScore2" /></th>
                        <td><input type="submit" name="submitScore2" />
                        <td>
                    </tr>
                    <tr>
                        <th>Điểm 3</th>
                        <th><input id="score3" type="text" name="txtScore3" /></th>
                        <td><input type="submit" name="submitScore3" />
                        <td>
                    </tr>
                    <tr>
                        <th>Điểm trừ</th>
                        <th><input id="penalty" type="text" name="penalty" /></th>
                        <td><input type="submit" name="submitPenalty" />
                        <td>
                    </tr>
                    <tr>
                        <th>Ghi chú</th>
                        <th><textarea id="note" rows="4" cols="30" name="note" /></textarea></th>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="float: right;"><input type="submit" value="Lưu" /> <input type="reset"
                            value="Reset" /></td>
                    </tr>
                </table>
            </form>
        </c:if>
        <c:if test="${empty cookie.currStation}">
            <form action="chooseStation" method="post">
                Mã trạm: <select name="slStation">
                    <c:forEach var="station" items="${listStations}">
                        <option value="${station.stationCode}">${station.chief}</option>
                    </c:forEach>
                </select> <input type="submit" value="Chọn trạm" />
            </form>
        </c:if>
    </div>
    <div id="txtHint">Customer info will be listed here...</div>
</body>
</html>