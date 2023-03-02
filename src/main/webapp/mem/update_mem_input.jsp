<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>


<%
MemVO memVO = (MemVO) request.getAttribute("memVO"); //MemServlet.java (Concroller) 存入req的memVO物件 (包括幫忙取出的memVO, 也包括輸入資料錯誤時的memVO物件)
%>
--<%=memVO == null%>--${memVO.memId}--


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>會員資料修改</title>

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
	width: 800px;
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

	<table id="table-1">
		<tr>
			<td>
				<h3>會員資料修改 - update_mem_input.jsp</h3>
				<h4>
					<a href="/molife/mem/select_page.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>會員資料修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="/molife/memberController" name="Update"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>姓氏:</td>
				<td><input type="TEXT" name="memLname" size="45"
					value="<%=memVO.getMemLname()%>" /></td>
			</tr>
			<tr>
				<td>名稱:</td>
				<td><input type="TEXT" name="memFname" size="45"
					value="<%=memVO.getMemFname()%>" /></td>
			</tr>
			<tr>
				<td>暱稱:</td>
				<td><input type="TEXT" name="memNickname" size="45"
					value="<%=memVO.getMemNickname()%>" /></td>
			</tr>
			<tr>
				<td>密碼:</td>
				<td><input type="TEXT" name="memPsd" size="45"
					value="<%=memVO.getMemPsd()%>" /></td>
			</tr>
			<tr>
				<td>確認密碼:</td>
				<td><input type="TEXT" name="memPsd" size="45"
					value="<%=memVO.getMemPsd()%>" /></td>
			</tr>
			<tr>
				<td>手機:</td>
				<td><input type="TEXT" name="memPhone" size="45"
					value="<%=memVO.getMemPhone()%>" /></td>
			</tr>
			<tr>
				<td>居住地址:</td>
				<td><input type="TEXT" name="memAddress" size="100"
					value="<%=memVO.getMemAddress()%>" /></td>
			</tr>
			<tr>
				<td>頭像圖片上傳:</td>
				<td><input type="file" name="memPicId" id="memPicId" />
					<button type="submit">Upload Image</button></td>
				<div id=blob_holder></div>
			</tr>

			<jsp:useBean id="memtSvc" scope="page"
				class="com.mem.model.MemService" />

		</table>
		<br> <input type="hidden" name="action" value="update">
		<input type="hidden" name="memId" value="<%=memVO.getMemId()%>">
		<input type="submit" value="確認修改">
	</FORM>
</body>
</html>


