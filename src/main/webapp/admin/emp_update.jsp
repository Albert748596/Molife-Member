<%@page import="com.admin.VO.AdminVO"%>
<%@page import="java.util.List"%>
<%@page import="com.admin.service.AdminService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
AdminService admSvc = new AdminService();
List<AdminVO> list = admSvc.getAll();
pageContext.setAttribute("list", list);
%>
<%
AdminVO adminVO = (AdminVO) request.getAttribute("adminVO");
%>

<html>
<head>
<meta charset="UTF-8">
<title>員工資料編輯</title>

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
				<h3>員工資料編輯 - emp_update.jsp</h3>
				<h4>
					<a href="admin/select_page.jsp"><img src="images/back1.gif"
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

	<FORM METHOD="post" ACTION="/molife/adminController" name="empupdate"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>員工密碼:</td>
				<td><input type="TEXT" name="empPsd" size="45"
					value="<%=(adminVO == null) ? "" : adminVO.getEmpPsd()%>" /></td>
			</tr>
			<tr>
				<td>員工名稱:</td>
				<td><input type="TEXT" name="empName" size="45"
					value="<%=(adminVO == null) ? "" : adminVO.getEmpName()%>" /></td>
			</tr>
			<tr>
				<td>員工信箱:</td>
				<td><input type="TEXT" name="empEmail" size="45"
					value="<%=(adminVO == null) ? "" : adminVO.getEmpEmail()%>" /></td>
			</tr>
			<tr>
				<td>員工頭像圖片上傳:</td>
				<td><input type="file" name="empPicId" id="empPicId" />
					<button type="submit">Upload Image</button></td>
				<div id=blob_holder></div>
			</tr>

			<jsp:useBean id="admtSvc" scope="page"
				class="com.admin.service.AdminService" />

		</table>
		<br> <input type="hidden" name="action" value="updatebyEMP">
		<input type="hidden" name="adminId" value="<%=adminVO.getAdminId()%>">
		<input type="submit" value="確認修改">
	</FORM>

</body>
</html>