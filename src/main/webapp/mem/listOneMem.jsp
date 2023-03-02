<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.mem.model.MemVO"%>
<%@ page import="com.mem.model.MemService"%>



<%
MemVO memVO = (MemVO) request.getAttribute("memVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>會員單筆資料查詢</title>


<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>
</head>
<body bgcolor='white'>

	<h4>此頁暫練習採用 Script 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>會員資料 - ListOneMem.jsp</h3>
				<h4>
					<a href="/molife/mem/select_page.jsp"><img
						src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>會員編號</th>
			<th>會員姓式</th>
			<th>會員名稱</th>
			<th>會員暱稱</th>
			<th>會員信箱</th>
			<th>會員密碼</th>
			<th>會員手機</th>
			<th>會員地址</th>
			<th>會員頭像</th>
			<th>會員註冊時間</th>
			<th>修改</th>
		</tr>
		<tr>
			<td>${memVO.memId}</td>
			<td>${memVO.memLname}</td>
			<td>${memVO.memFname}</td>
			<td>${memVO.memNickname}</td>
			<td>${memVO.memEmail}</td>
			<td>${memVO.memPsd}</td>
			<td>${memVO.memPhone}</td>
			<td>${memVO.memAddress}</td>
			<td><img src="/molife/MemPicView?memId=${memVO.memId}"
				width="200" height="200" id="mempic"></td>
			<td>${memVO.registrationDate}</td>

			<td>
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/memberController"
					style="margin-bottom: 0px;" enctype="multipart/form-data">
					<input type="submit" value="修改"> <input type="hidden"
						name="memId" value="${memVO.memId}"> <input type="hidden"
						name="action" value="getOne_For_Update">
				</FORM>
			</td>
		</tr>
	</table>

</body>
</html>