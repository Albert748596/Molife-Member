package com.mem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemService;
import com.mem.model.MemVO;

@WebServlet("/MemPicView")
public class MemPicView extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub

		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif, image/jpg, image/jpeg, image/png");
		Integer memId = Integer.valueOf(req.getParameter("memId"));
		ServletOutputStream out = res.getOutputStream();

		MemService memSvc = new MemService();
		MemVO memVO = memSvc.getOneMem(memId);
		out.write(memVO.getMemPicId());
		out.close();

//		PrintWriter out = res.getWriter();
//		out.print("123");
//		

	}

}
