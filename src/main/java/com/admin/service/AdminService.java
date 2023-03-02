package com.admin.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.Id;

import java.util.Arrays;
import com.admin.AdminJDBCDAO;
import com.admin.VO.AdminVO;
import com.admin.dao.AdminDAOInterface;
import com.admin.dao.impl.AdminDaoImpl;

public class AdminService {
	 AdminDAOInterface dao;
	
	public AdminService() {
//		dao = new AdminJDBCDAO();
		dao = new AdminDaoImpl();
	}
	
	public AdminVO addAdmin(String empAcc, String empPsd, String empName, byte[] empPicId, String empEmail, Integer empAuthId, Integer empStatus) {
		
		AdminVO adminVO = new AdminVO();
		adminVO.setEmpAcc(empAcc);
		adminVO.setEmpPsd(empPsd);
		adminVO.setEmpName(empName);
		adminVO.setEmpPicId(empPicId);
		adminVO.setEmpEmail(empEmail);
		adminVO.setEmpAuthId(empAuthId);
		adminVO.setEmpStatus(empStatus);
		dao.insert(adminVO);		
		
		return adminVO;
	}
	
	//預留給 Struts 2 或 Spring MVC 用
	public void addAdmin(AdminVO adminVO) {
		dao.insert(adminVO);
	}
	
	public AdminVO updateByEmp(String empPsd, String empName, byte[] empPicId, String empEmail, Integer adminId) {
		
		AdminVO adminVO = new AdminVO();
		
		adminVO.setEmpPsd(empPsd);
		adminVO.setEmpName(empName);
		adminVO.setEmpPicId(empPicId);
		adminVO.setEmpEmail(empEmail);
		adminVO.setAdminId(adminId);		
		dao.empedit(adminVO);
		
		return adminVO;
	}
	
	//預留給 Struts 2 用的
	public void updateByEmp(AdminVO adminVO) {
		dao.empedit(adminVO);
	}
	
	public AdminVO updateByBoss(String empPsd, String empName, byte[] empPicId, String empEmail, Integer empAuthId, Integer empStatus, Integer adminId) {
		
		AdminVO adminVO = new AdminVO();
		
		adminVO.setEmpPsd(empPsd);
		adminVO.setEmpName(empName);
		adminVO.setEmpPicId(empPicId);
		adminVO.setEmpEmail(empEmail);
		adminVO.setEmpAuthId(empAuthId);
		adminVO.setEmpStatus(empStatus);
		adminVO.setAdminId(adminId);
		dao.bossedit(adminVO);
		
		return adminVO;
	}
	
	//預留給 Struts 2 用的
	public void updateByBoss(AdminVO adminVO) {
		dao.bossedit(adminVO);
	}
	
	
	public void deleteEmp(Integer adminId) {
		dao.delete(adminId);
	}
	
	
	public AdminVO getOneEmp(Integer adminId) {
		return dao.findByPrimaryKey(adminId);
	}

	public AdminVO login(AdminVO adminVO) {
		final String empAcc = adminVO.getEmpAcc();
		final String empPsd = adminVO.getEmpPsd();
		if (!checkValue(empAcc) || !checkValue(empPsd)) {
			System.out.println("帳號或密碼錯誤");
			return null;
		}
		return dao.login(adminVO);
		
	}
	
	private boolean checkValue(String value) {
		if (value == null || Objects.equals(value, "")) {
				System.out.println(value);
				return false;
		}
		return true;
	}
		
	public List<AdminVO> getAll(){
		return dao.getAll();
	}
	
}
