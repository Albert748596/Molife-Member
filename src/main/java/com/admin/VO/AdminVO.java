package com.admin.VO;

import java.io.Serializable;
import java.util.Arrays;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class AdminVO implements Serializable{


	private Integer adminId;	
	private String empAcc;	
	private String empPsd;	
	private String empName;	
	private byte[] empPicId;	
	private String empEmail;	
	private Integer empAuthId;		
	private Integer empStatus;

	public AdminVO() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "AdminVO [adminId=" + adminId + ", empAcc=" + empAcc + ", empPsd=" + empPsd + ", empName=" + empName
				+ ", empPicId=" + Arrays.toString(empPicId) + ", empEmail=" + empEmail + ", empAuthId=" + empAuthId
				+ ", empStatus=" + empStatus + "]";
	}	
	
	
	public AdminVO(Integer adminId, String empAcc, String empPsd, String empName, 
			byte[] empPicId, String empEmail, Integer empAuthId, Integer empStatus) {
		
		super();
		this.adminId = adminId;
		this.empAcc = empAcc;
		this.empPsd = empPsd;
		this.empName = empName;
		this.empPicId = empPicId;
		this.empEmail = empEmail;
		this.empAuthId = empAuthId;
		this.empStatus = empStatus;		
	}
	
	
	
	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getEmpAcc() {
		return empAcc;
	}

	public void setEmpAcc(String empAcc) {
		this.empAcc = empAcc;
	}

	public String getEmpPsd() {
		return empPsd;
	}

	public void setEmpPsd(String empPsd) {
		this.empPsd = empPsd;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public byte[] getEmpPicId() {
		return empPicId;
	}

	public void setEmpPicId(byte[] empPicId) {
		this.empPicId = empPicId;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}


	public Integer getEmpAuthId() {
		return empAuthId;
	}

	public void setEmpAuthId(Integer empAuthId) {
		this.empAuthId = empAuthId;
	}

	public Integer getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(Integer empStatus) {
		this.empStatus = empStatus;
	}	
	
	public void initFormReq(HttpServletRequest req) {
		this.empAcc = req.getParameter("empAcc");
		this.empPsd = req.getParameter("empPsd");
	}
	
	
	
	
	
	
//	 讓authname 可以藉由 auth_id join進Admin	
	public com.auth.VO.AuthVO getAuthVO(){
		com.auth.service.AuthService authSvc = new com.auth.service.AuthService();
		com.auth.VO.AuthVO authVO = authSvc.getOneAuth(empAuthId);
		return authVO;
	}	
	
}
