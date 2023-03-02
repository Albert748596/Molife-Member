package com.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin.VO.AdminVO;
import com.admin.service.AdminService;
import com.mem.model.MemVO;

@WebServlet("/adminController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {

			List<String> erroMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", erroMsgs);

//			1.接收請求參數
			String str = req.getParameter("adminId");

			if (str == null || (str.trim()).length() == 0) {
				erroMsgs.add("請輸入員工編號");

			}
			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/admin/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			Integer adminId = null;
			try {
				adminId = Integer.valueOf(str);
			} catch (Exception e) {
				erroMsgs.add("員工編號格式不正確");
			}

			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/admin/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			/******************************************************************************************/
			AdminService admSvc = new AdminService();
			AdminVO adminVO = admSvc.getOneEmp(adminId);
			if (adminVO == null) {
				erroMsgs.add("無此員工");
			}
			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("url//loging.jsp");
				failureView.forward(req, res);
				return;
			}

			req.setAttribute("adminVO", adminVO);
			String url = "/admin/listOneEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		/******************************************************************************************/

		if ("emp_update".equals(action)) {// //來自listAllMem.jsp 或ListOneMem.jsp的請求

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer adminId = Integer.valueOf(req.getParameter("adminId"));

			// 查詢
			AdminService admSvc = new AdminService();
			AdminVO adminVO = admSvc.getOneEmp(adminId);
			// 查詢完成，轉交

			req.setAttribute("adminVO", adminVO);
			String url = "/admin/emp_update.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		/******************************************************************************************/
		if ("boss_Update".equals(action)) {// //來自listAllMem.jsp 或ListOneMem.jsp的請求

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer adminId = Integer.valueOf(req.getParameter("adminId"));

			// 查詢
			AdminService admSvc = new AdminService();
			AdminVO adminVO = admSvc.getOneEmp(adminId);
			// 查詢完成，轉交

			req.setAttribute("adminVO", adminVO);
			String url = "/admin/boss_update.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		/******************************************************************************************/
		if ("updatebyEMP".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			Integer adminId = Integer.valueOf(req.getParameter("adminId").trim());

			String empPsd = req.getParameter("empPsd");
			String empPsdReg = "^[(a-zA-Z0-9_)]{3,10}$";
			if (empPsd == null || empPsd.trim().length() == 0) {
				errorMsgs.add("會員密碼: 請勿空白");
			} else if (!empPsd.trim().matches(empPsdReg)) {
				errorMsgs.add("會員密碼請使用英文與數字之組合，長度介於3至10個字以內");
			}

			String empName = req.getParameter("empName");
			String empNameReg = "^[(\u4e00-\u9fa5)]{1,10}$";
			if (empName == null || empName.trim().length() == 0) {
				errorMsgs.add("員工名稱: 請勿空白");
			} else if (!empName.trim().matches(empNameReg)) {
				errorMsgs.add("員工名稱: 只能是中文字，且長度只能在1到10之間");
			}

			String empEmail = req.getParameter("empEmail");
			String empEmailReg = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
			if (empEmail == null || empEmail.trim().length() == 0) {
				errorMsgs.add("員工信箱: 請勿空白");
			} else if (!empEmail.trim().matches(empEmailReg)) {
				errorMsgs.add("員工信箱: 請符合電子信箱格式");
			}

			InputStream in = req.getPart("enpPicId").getInputStream();
			byte[] empPicId = null;
			if (in.available() != 0) {
				empPicId = new byte[in.available()];
				in.read(empPicId);
				in.close();
			} else {
				errorMsgs.add("empPicId, 員工頭像:請上傳圖片");
			}

			AdminVO adminVO = new AdminVO();

			adminVO.setEmpPsd(empPsd);
			adminVO.setEmpName(empName);
			adminVO.setEmpEmail(empEmail);
			adminVO.setEmpPicId(empPicId);
			adminVO.setAdminId(adminId);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("adminVO", adminVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/admin/admin/emp_update.jsp");
				failureView.forward(req, res);
				System.out.println(errorMsgs);
				return;
			}

			/*************************** 2.開始修改資料 *****************************************/
			AdminService admSvc = new AdminService();
			adminVO = admSvc.updateByEmp(empPsd, empName, empPicId, empEmail, adminId);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("adminVO", adminVO);
			String url = "/admin/listOneEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		/******************************************************************************************/
		if ("updatebyBoss".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			Integer adminId = Integer.valueOf(req.getParameter("adminId").trim());

			String empPsd = req.getParameter("empPsd");
			String empPsdReg = "^[(a-zA-Z0-9_)]{3,10}$";
			if (empPsd == null || empPsd.trim().length() == 0) {
				errorMsgs.add("會員密碼: 請勿空白");
			} else if (!empPsd.trim().matches(empPsdReg)) {
				errorMsgs.add("會員密碼請使用英文與數字之組合，長度介於3至10個字以內");
			}

			String empName = req.getParameter("empName");
			String empNameReg = "^[(\u4e00-\u9fa5)]{1,10}$";
			if (empName == null || empName.trim().length() == 0) {
				errorMsgs.add("員工暱稱: 請勿空白");
			} else if (!empName.trim().matches(empNameReg)) {
				errorMsgs.add("員工暱稱: 只能是中文字，且長度只能在1到10之間");
			}

			String empEmail = req.getParameter("empEmail");
			String empEmailReg = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
			if (empEmail == null || empEmail.trim().length() == 0) {
				errorMsgs.add("員工信箱: 請勿空白");
			} else if (!empEmail.trim().matches(empEmailReg)) {
				errorMsgs.add("員工信箱: 請符合電子信箱格式");
			}

			InputStream in = req.getPart("empPicId").getInputStream();
			byte[] empPicId = null;
			if (in.available() != 0) {
				empPicId = new byte[in.available()];
				in.read(empPicId);
				in.close();
			} else {
				errorMsgs.add("empPicId, 員工頭像:請上傳圖片");
			}

			Integer empAuthId = Integer.valueOf(req.getParameter("empAuthId"));
			String empAuthIdReg = "^[123]+$";
			if (empAuthId == null) {
				errorMsgs.add("請填入員工權限編號，勿空白");
			}else if(!empAuthId.toString().matches(empAuthIdReg)){
				errorMsgs.add("員工權限編號只能填1~3");
			}

			Integer empStatus = Integer.valueOf(req.getParameter("empStatus"));
			String empStatusReg = "^[01]+$";
			if (empStatus == null) {
				errorMsgs.add("請填入員工狀態號碼，勿空白");
			}else if(!empStatus.toString().matches(empStatusReg)){
				errorMsgs.add("狀態只能填入0或1");
			}

			AdminVO adminVO = new AdminVO();

			adminVO.setEmpPsd(empPsd);
			adminVO.setEmpName(empName);
			adminVO.setEmpPicId(empPicId);
			adminVO.setEmpEmail(empEmail);
			adminVO.setEmpAuthId(empAuthId);
			adminVO.setEmpStatus(empStatus);
			adminVO.setAdminId(adminId);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("adminVO", adminVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/admin/boss_update.jsp");
				failureView.forward(req, res);
				System.out.println(errorMsgs);
				return;
			}

			/*************************** 2.開始修改資料 *****************************************/
			AdminService admSvc = new AdminService();
			adminVO = admSvc.updateByBoss(empPsd, empName, empPicId, empEmail, empAuthId, empStatus, adminId);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("adminVO", adminVO);
			String url = "/admin/listAllEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		/******************************************************************************************/
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String empAcc = req.getParameter("empAcc");
			String empAccReg = "^Emp[0-9]{3}$";
			if (empAcc == null || empAcc.trim().length() == 0) {
				errorMsgs.add("員工帳號 : 請勿空白");
			} else if (!empAcc.trim().matches(empAccReg)) {
				errorMsgs.add("員工帳號請使用Emp001~Emp999之間的組合");
			}

			String empPsd = req.getParameter("empPsd");
			String empPsdReg = "^[(a-zA-Z0-9_)]{3,10}$";
			if (empPsd == null || empPsd.trim().length() == 0) {
				errorMsgs.add("會員密碼: 請勿空白");
			} else if (!empPsd.trim().matches(empPsdReg)) {
				errorMsgs.add("會員密碼請使用英文與數字之組合，長度介於3至10個字以內");
			}

			String empName = req.getParameter("empName");
			String empNameReg = "^[(\u4e00-\u9fa5)]{1,10}$";
			if (empName == null || empName.trim().length() == 0) {
				errorMsgs.add("員工暱稱: 請勿空白");
			} else if (!empName.trim().matches(empNameReg)) {
				errorMsgs.add("員工暱稱: 只能是中文字，且長度只能在1到10之間");
			}

			String empEmail = req.getParameter("empEmail");
			String empEmailReg = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
			if (empEmail == null || empEmail.trim().length() == 0) {
				errorMsgs.add("員工信箱: 請勿空白");
			} else if (!empEmail.trim().matches(empEmailReg)) {
				errorMsgs.add("員工信箱: 請符合電子信箱格式");
			}

			InputStream in = req.getPart("empPicId").getInputStream();
			byte[] empPicId = null;
			if (in.available() != 0) {
				empPicId = new byte[in.available()];
				in.read(empPicId);
				in.close();
			} else {
				errorMsgs.add("empPicId, 員工頭像:請上傳圖片");
			}
			
			Integer empAuthId = Integer.valueOf(req.getParameter("empAuthId"));

			Integer empStatus = Integer.valueOf(req.getParameter("empStatus"));
			
			AdminVO adminVO = new AdminVO();

			
			adminVO.setEmpAcc(empAcc);
			adminVO.setEmpPsd(empPsd);
			adminVO.setEmpName(empName);
			adminVO.setEmpEmail(empEmail);
			adminVO.setEmpPicId(empPicId);
			adminVO.setEmpAuthId(empAuthId);
			adminVO.setEmpStatus(empStatus);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("adminVO", adminVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/admin/addEmp.jsp");
				failureView.forward(req, res);
				System.out.println(errorMsgs);
				return;
			}

			/*************************** 2.開始修改資料 *****************************************/
			AdminService admSvc = new AdminService();
			adminVO = admSvc.addAdmin(empAcc, empPsd, empName, empPicId, empEmail, empAuthId, empStatus);

			req.setAttribute("success", "新增會員成功");
			String url = "/admin/listAllEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		/******************************************************************************************/

		if ("delete".equals(action)) { // 來自listAllAdmin.jsp

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			Integer adminId = Integer.valueOf(req.getParameter("adminId"));

			AdminService admSvc = new AdminService();
			admSvc.deleteEmp(adminId);

			String url = "/admin/listAllEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		/******************************************************************************************/

		if ("login".equals(action)) {// 來自loginEmp.jsp

			List<String> erroMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", erroMsgs);

//			1.接收請求參數
			String empacc = req.getParameter("empAcc");
			String emppsd = req.getParameter("empPsd");
			if (empacc == null || (empacc.trim()).length() == 0) {
				erroMsgs.add("請輸入員工帳號");
			}
			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/admin/loginEmp.jsp");
				failureView.forward(req, res);
				return;
			}
			if (emppsd == null || (emppsd.trim()).length() == 0) {
				erroMsgs.add("請輸入密碼");
			}
			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/admin/loginEmp.jsp");
				failureView.forward(req, res);
				return;
			}

//			AdminVO adminVO = ;

			/******************************************************************************************/
			AdminService admSvc = new AdminService();
			AdminVO adminVO = new AdminVO();
			adminVO.setEmpAcc(empacc);
			adminVO.setEmpPsd(emppsd);
			adminVO = admSvc.login(adminVO);
			if (adminVO == null) {
				erroMsgs.add("帳號不存在");
			}
			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/admin/loginEmp.jsp");
				failureView.forward(req, res);
				return;
			}
			
			else {
				if (req.getSession(false) != null) {
					req.changeSessionId();// 產生新的sessionId
				}
				req.getSession().setAttribute("adminVO", adminVO);
				req.getSession().setAttribute("login", true);

				Cookie cookie = new Cookie("adminId", adminVO.getAdminId().toString());
				Cookie cookie2 = new Cookie("memberName", adminVO.getEmpName());
				cookie.setMaxAge(7 * 24 * 60 * 60);
				cookie2.setMaxAge(7 * 24 * 60 * 60);
				cookie.setPath("/");
				cookie2.setPath("/");
				res.addCookie(cookie);
				res.addCookie(cookie2);
			}
			
			
			
			req.setAttribute("adminVO", adminVO);
			String url = "/admin/listOneEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

	}

}
