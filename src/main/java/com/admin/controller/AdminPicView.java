package com.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin.VO.AdminVO;
import com.admin.service.AdminService;

@WebServlet("/AdminPicView")
public class AdminPicView extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif, image/jpg, image/jpeg, image/png");
		
		Integer adminId = Integer.valueOf(req.getParameter("adminId"));
		
		ServletOutputStream out = res.getOutputStream();
		
		AdminService admSvc = new AdminService();
		AdminVO adminVO = admSvc.getOneEmp(adminId);
		out.write(adminVO.getEmpPicId());
		out.close();
		
		
	}
	

}
