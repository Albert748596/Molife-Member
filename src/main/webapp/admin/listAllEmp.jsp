<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.admin.VO.AdminVO"%>
<%@page import="java.util.List"%>
<%@page import="com.admin.service.AdminService"%>
<!DOCTYPE html>

<%
AdminService admSvc = new AdminService();
List<AdminVO> list = admSvc.getAll();
pageContext.setAttribute("list", list);
%>


<html>
<head>
<meta charset="UTF-8">
<title>所有會員資料查詢</title>

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
				<h3>所有員工資料 - listAllMem.jsp</h3>
				<h4>
					<a href="select_page.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<th>員工編號</th>
			<th>員工帳號</th>
			<th>員工密碼</th>
			<th>員工名稱</th>
			<th>員工頭像</th>
			<th>員工信箱</th>
			<th>員工權限編號</th>
			<th>員工狀態</th>			
			<th>修改</th>
			<th>刪除</th>




		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="adminVO" items="${list}" begin="<%=pageIndex%>"	end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${adminVO.adminId}</td>
				<td>${adminVO.empAcc}</td>
				<td>${adminVO.empPsd}</td>
				<td>${adminVO.empName}</td>				
				<td><img src="/molife/AdminPicView?adminId=${adminVO.adminId}"
					width="200" height="200" id="empPicid"></td>				
				<td>${adminVO.empEmail}</td>
				<td>${adminVO.authVO.authName}</td>
				<td>${adminVO.empStatus}</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/adminController"
						style="margin-bottom: 0px;"
						enctype="multipart/form-data">
						<input type="submit" value="修改"> <input type="hidden"
							name="adminId" value="${adminVO.adminId}"> <input type="hidden"
							name="action" value="boss_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/molife/adminController"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> <input type="hidden"
							name="adminId" value="${adminVO.adminId}"> <input type="hidden"
							name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
</html>