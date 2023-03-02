package com.mem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

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
import com.mem.model.MemService;
import com.mem.model.MemVO;

@WebServlet("/memberController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("member_View".equals(action)) {

			List<String> erroMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", erroMsgs);

			String str = req.getParameter("memId");
			if (str == null || (str.trim()).length() == 0) {
				erroMsgs.add("請輸入會員編號");
			}
			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/mem/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			Integer memId = null;
			try {
				memId = Integer.valueOf(str);
			} catch (Exception e) {
				erroMsgs.add("會員編號格式不正確");
			}

			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/mem/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			MemService memSvc = new MemService();
			MemVO memVO = memSvc.getOneMem(memId);
			if (memVO == null) {
				erroMsgs.add("查無資料");
			}
			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/mem/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			req.setAttribute("memVO", memVO);
			String url = "/mem/listOneMem.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
		/******************************************************************************************/

		if ("getOne_For_Update".equals(action)) {// 來自listAllMem.jsp 或ListOMem.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			// 接收請求參數
			Integer memId = Integer.valueOf(req.getParameter("memId"));

			// 開始查詢
			MemService memSvc = new MemService();
			MemVO memVO = memSvc.getOneMem(memId);
			// 查詢完成，轉交

			req.setAttribute("memVO", memVO);
			String url = "/mem/update_mem_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		/******************************************************************************************/
		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			Integer memId = Integer.valueOf(req.getParameter("memId").trim());

			String memLname = req.getParameter("memLname");
			String memLnameReg = "^[(\u4e00-\u9fa5)]{1,10}$";
			if (memLname == null || memLname.trim().length() == 0) {
				errorMsgs.add("會員姓氏: 請勿空白");
			} else if (!memLname.trim().matches(memLnameReg)) {
				errorMsgs.add("會員姓氏: 只能是中文字，且長度只能在1到10之間");
			}

			String memFname = req.getParameter("memFname");
			String memFnameReg = "^[(\u4e00-\u9fa5)]{1,10}$";
			if (memFname == null || memFname.trim().length() == 0) {
				errorMsgs.add("會員名字: 請勿空白");
			} else if (!memFname.trim().matches(memFnameReg)) {
				errorMsgs.add("會員名字: 只能是中文字，且長度只能在1到10之間");
			}

			String memNickname = req.getParameter("memNickname");
			String memNicknameReg = "^[(\u4e00-\u9fa5)]{1,10}$";
			if (memNickname == null || memNickname.trim().length() == 0) {
				errorMsgs.add("會員暱稱: 請勿空白");
			} else if (!memNickname.trim().matches(memNicknameReg)) {
				errorMsgs.add("會員暱稱: 只能是中文字，且長度只能在1到10之間");
			}

			String memPsd = req.getParameter("memPsd");
			String memPsdReg = "^[(a-zA-Z0-9_)]{6,16}$";
			if (memPsd == null || memPsd.trim().length() == 0) {
				errorMsgs.add("會員密碼: 請勿空白");
			} else if (!memPsd.trim().matches(memPsdReg)) {
				errorMsgs.add("會員密碼請使用英文與數字之組合，長度介於6至16個字以內");
			}

			String memPhone = req.getParameter("memPhone");
			String memPhoneReg = "^09[0-9]{8}$";
			if (memPhone == null || memPhone.trim().length() == 0) {
				errorMsgs.add("手機號碼: 請勿空白");
			} else if (!memPhone.trim().matches(memPhoneReg)) {
				errorMsgs.add("手機號碼請符合台灣手機號碼格式");
			}

			String memAddress = req.getParameter("memAddress");
			java.sql.Timestamp lastEditDate = null;
			java.sql.Timestamp lastOnlineDate = null;
			java.sql.Timestamp lastPostDate = null;
			Integer memStatus = 0;
			Integer postSuspended = 0;
			Integer postReportedNum = 0;

			// 會員頭像上傳
			InputStream in = req.getPart("memPicId").getInputStream();
			byte[] memPicId = null;
			if (in.available() != 0) {
				memPicId = new byte[in.available()];
				in.read(memPicId);
				in.close();
			} else {
				errorMsgs.add("memPicId,會員頭像:請上傳圖片");
			}

			MemVO memVO = new MemVO();
			memVO.setMemLname(memLname);
			memVO.setMemFname(memFname);
			memVO.setMemNickname(memNickname);
			memVO.setMemPsd(memPsd);
			memVO.setMemPhone(memPhone);
			memVO.setMemAddress(memAddress);
			memVO.setMemPicId(memPicId);
			memVO.setLastEditDate(lastEditDate);
			memVO.setLastOnlineDate(lastOnlineDate);
			memVO.setLastPostDate(lastPostDate);
			memVO.setMemStatus(memStatus);
			memVO.setPostSuspended(postSuspended);
			memVO.setPostReportedNum(postReportedNum);
			memVO.setMemId(memId);

			// Send the user back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memVO", memVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/mem/update_mem_input.jsp");
				failureView.forward(req, res);
				System.out.println(errorMsgs);
				return;
			}

			/*************************** 2.開始修改資料 *****************************************/
			MemService memSvc = new MemService();
			memVO = memSvc.updateMem(memPsd, memLname, memFname, memNickname, memPhone, memAddress, memPicId,
					lastEditDate, lastOnlineDate, lastPostDate, memStatus, postSuspended, postReportedNum, memId);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("memVO", memVO);
			String url = "/mem/listOneMem.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		/******************************************************************************************/

		if ("insert".equals(action)) { // 來自memadd.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			String memLname = req.getParameter("memLname");
			String memLnameReg = "^[(\u4e00-\u9fa5)]{1,10}$";
			if (memLname == null || memLname.trim().length() == 0) {
				errorMsgs.add("會員姓氏: 請勿空白");
			} else if (!memLname.trim().matches(memLnameReg)) {
				errorMsgs.add("會員姓氏: 只能是中文字，且長度只能在1到10之間");
			}

			String memFname = req.getParameter("memFname");
			String memFnameReg = "^[(\u4e00-\u9fa5)]{1,10}$";
			if (memFname == null || memFname.trim().length() == 0) {
				errorMsgs.add("會員名字: 請勿空白");
			} else if (!memFname.trim().matches(memFnameReg)) {
				errorMsgs.add("會員名字: 只能是中文字，且長度只能在1到10之間");
			}

			String memNickname = req.getParameter("memNickname");
			String memNicknameReg = "^[(\u4e00-\u9fa5)]{1,10}$";
			if (memNickname == null || memNickname.trim().length() == 0) {
				errorMsgs.add("會員暱稱: 請勿空白");
			} else if (!memNickname.trim().matches(memNicknameReg)) {
				errorMsgs.add("會員暱稱: 只能是中文字，且長度只能在1到10之間");
			}

			String memEmail = req.getParameter("memEmail");
			String memEmailReg = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
			if (memEmail == null || memEmail.trim().length() == 0) {
				errorMsgs.add("會員信箱: 請勿空白");
			} else if (!memEmail.trim().matches(memEmailReg)) {
				errorMsgs.add("會員信箱: 請符合電子信箱格式");
			}

			String memPsd = req.getParameter("memPsd");
			String memPsdReg = "^[(a-zA-Z0-9_)]{6,16}$";
			if (memPsd == null || memPsd.trim().length() == 0) {
				errorMsgs.add("會員密碼: 請勿空白");
			} else if (!memPsd.trim().matches(memPsdReg)) {
				errorMsgs.add("會員密碼請使用英文與數字之組合，長度介於6至16個字以內");
			}

			String memPhone = req.getParameter("memPhone");
			String memPhoneReg = "^09[0-9]{8}$";
			if (memPhone == null || memPhone.trim().length() == 0) {
				errorMsgs.add("手機號碼: 請勿空白");
			} else if (!memPhone.trim().matches(memPhoneReg)) {
				errorMsgs.add("手機號碼請符合台灣手機號碼格式");
			}

			String memAddress = req.getParameter("memAddress");
			java.sql.Timestamp registrationDate = new Timestamp(System.currentTimeMillis());
			java.sql.Timestamp lastEditDate = null;
			java.sql.Timestamp lastOnlineDate = null;
			java.sql.Timestamp lastPostDate = null;
			Integer memStatus = 0;
			Integer postSuspended = 0;
			Integer postReportedNum = 0;

			// 會員頭像上傳
			InputStream in = req.getPart("memPicId").getInputStream();
			byte[] memPicId = null;
			if (in.available() != 0) {
				memPicId = new byte[in.available()];
				in.read(memPicId);
				in.close();
			} else {
				errorMsgs.add("memPicId,會員頭像:請上傳圖片");
			}

//				
//				req.getPart("memPicId").write(getServletContext().getRealPath("/images_uploaded")+"file.gif");
//							

			// Integer memId = Integer.valueOf(req.getParameter("memId").trim());

			MemVO memVO = new MemVO();

			memVO.setMemEmail(memEmail);
			memVO.setMemPsd(memPsd);
			memVO.setMemLname(memLname);
			memVO.setMemFname(memFname);
			memVO.setMemNickname(memNickname);
			memVO.setMemPhone(memPhone);
			memVO.setMemAddress(memAddress);
			memVO.setMemPicId(memPicId);
			memVO.setRegistrationDate(registrationDate);
			memVO.setLastEditDate(lastEditDate);
			memVO.setLastOnlineDate(lastOnlineDate);
			memVO.setLastPostDate(lastPostDate);
			memVO.setMemStatus(memStatus);
			memVO.setPostSuspended(postSuspended);
			memVO.setPostReportedNum(postReportedNum);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memVO", memVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/mem/addMem.jsp");
				failureView.forward(req, res);
				return;
			}

			MemService memSvc = new MemService();
			memVO = memSvc.addMem(memEmail, memPsd, memLname, memFname, memNickname, memPhone, memAddress, memPicId,
					registrationDate, lastEditDate, lastOnlineDate, lastPostDate, memStatus, postSuspended,
					postReportedNum);

			req.setAttribute("success", "-(註冊成功)");
			String url = "/mem/listAllMem.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		/******************************************************************************************/

		if ("delete".equals(action)) { // 來自listAllMem.jsp

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			// 接收請求參數
			Integer memId = Integer.valueOf(req.getParameter("memId"));

//				開始刪除資料
			MemService memSvc = new MemService();
			memSvc.deleteMem(memId);

//				刪除完成，準備轉接
			String url = "/mem/listAllMem.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		/******************************************************************************************/

		if ("login".equals(action)) {// 來自loginMem.jsp

			List<String> erroMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", erroMsgs);

//				1.接收請求參數
			String memEmail = req.getParameter("memEmail");
			String memPsd = req.getParameter("memPsd");
			if (memEmail == null || (memEmail.trim()).length() == 0) {
				erroMsgs.add("請輸入會員帳號");
			}
			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/admin/loginEmp.jsp");
				failureView.forward(req, res);
				return;
			}
			if (memPsd == null || (memPsd.trim()).length() == 0) {
				erroMsgs.add("請輸入密碼");
			}
			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/admin/loginEmp.jsp");
				failureView.forward(req, res);
				return;
			}

//				AdminVO adminVO = ;

			/******************************************************************************************/
			MemService memSvc = new MemService();
			MemVO memVO = new MemVO();
			memVO.setMemEmail(memEmail);
			memVO.setMemPsd(memPsd);
			memVO = memSvc.login(memEmail, memPsd);
			if (memEmail == null) {
				erroMsgs.add("帳號不存在");
			} else if (memPsd == null) {
				erroMsgs.add("不輸入密碼也想登入喔");
			}
			if (!erroMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/mem/loginMem.jsp");
				failureView.forward(req, res);
				return;
			}

			else {
				if (req.getSession(false) != null) {
					req.changeSessionId();// 產生新的sessionId
				}
				req.getSession().setAttribute("memVO", memVO);
				req.getSession().setAttribute("login", true);

				Cookie cookie = new Cookie("memberId", memVO.getMemId().toString());
				Cookie cookie2 = new Cookie("memberNickName", memVO.getMemNickname());
				cookie.setMaxAge(7 * 24 * 60 * 60);
				cookie2.setMaxAge(7 * 24 * 60 * 60);
				cookie.setPath("/");
				cookie2.setPath("/");
				res.addCookie(cookie);
				res.addCookie(cookie2);
			}

			req.setAttribute("memVO", memVO);
			String url = "/mem/listOneMem.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

	}

}
