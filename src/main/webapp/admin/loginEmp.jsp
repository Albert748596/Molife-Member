<%@page import="com.admin.VO.AdminVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%
AdminVO adminVO = (AdminVO) request.getAttribute("adminVO");
%>
<%-- <% AdminVO adminVO = (AdminVO) request.getAttribute("adminVO");%>> --%>

--<%=adminVO == null%>--${adminVO.adminId}--


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>員工登入 - loginEmp.jsp</title>

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
				<h3>員工登入 - loginEmp.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="/admin/select_page.jsp"><img src="images/tomcat.png"
						width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>員工登入:</h3>


	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>



	<FORM METHOD="post" ACTION="/molife/adminController" name="login"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>員工帳號:</td>
				<td><input type="TEXT" name="empAcc" size="45" /></td>
			</tr>
			<tr>
				<td>員工密碼:</td>
				<td><input type="TEXT" name="empPsd" size="45" /></td>
			</tr>

			<jsp:useBean id="admSvc" scope="page"
				class="com.admin.service.AdminService" />

		</table>
		<br> <input type="hidden" name="action" value="login"> <input
			type="submit" value="登入">
	</FORM>
</body>


</html>