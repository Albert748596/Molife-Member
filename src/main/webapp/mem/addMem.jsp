<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>

<%
MemVO memVO = (MemVO) request.getAttribute("memVO");
%>

--<%=memVO == null%>--${memVO.memId}--


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>會員註冊 - addmem.jsp</title>


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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>

</head>
<body bgcolor='white'>


	<table id="table-1">
		<tr>
			<td>
				<h3>會員註冊 - addMem.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="select_page.jsp"><img src="images/tomcat.png"
						width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>會員註冊:</h3>


	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>



	<FORM METHOD="post" ACTION="/molife/memberController"
		name="memRegistery" enctype="multipart/form-data">
		<table>
			<tr>
				<td>姓氏:</td>
				<td><input type="TEXT" name="memLname" size="45"
					value="<%=(memVO == null) ? "" : memVO.getMemLname()%>" /></td>
				<%-- 					value="${ memVO.memLname eq null ? '' : memVO.memLname}" /></td> --%>
			</tr>
			<tr>
				<td>名稱:</td>
				<td><input type="TEXT" name="memFname" size="45"
					value="<%=(memVO == null) ? "" : memVO.getMemFname()%>" /></td>
			</tr>
			<tr>
				<td>暱稱:</td>
				<td><input type="TEXT" name="memNickname" size="45"
					value="<%=(memVO == null) ? "" : memVO.getMemNickname()%>" /></td>
			</tr>
			<tr>
				<td>信箱:</td>
				<td><input type="TEXT" name="memEmail" size="45"
					value="<%=(memVO == null) ? "" : memVO.getMemEmail()%>" /></td>
			</tr>
			<tr>
				<td>密碼:</td>
				<td><input type="TEXT" name="memPsd" size="45"
					value="<%=(memVO == null) ? "" : memVO.getMemPsd()%>" /></td>
			</tr>
			<tr>
				<td>確認密碼:</td>
				<td><input type="TEXT" name="memPsd" size="45"
					value="<%=(memVO == null) ? "" : memVO.getMemPsd()%>" /></td>
			</tr>
			<tr>
				<td>手機:</td>
				<td><input type="TEXT" name="memPhone" size="45"
					value="<%=(memVO == null) ? "" : memVO.getMemPhone()%>" /></td>
			</tr>
			<tr>
				<td>居住地址:</td>
				<td><input type="TEXT" name="memAddress" size="100"
					value="<%=(memVO == null) ? "" : memVO.getMemAddress()%>" /></td>
			</tr>
			<tr>
				<td>頭像圖片上傳:</td>
				<td><input type="file" name="memPicId" id="memPicId" />
					<button type="submit">Upload Image</button></td>
				<div id=blob_holder></div>
			</tr>

			<jsp:useBean id="memSvc" scope="page"
				class="com.mem.model.MemService" />

		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="確認註冊">
	</FORM>
</body>


</html>