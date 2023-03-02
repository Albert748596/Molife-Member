package com.mem.model;

import com.admin.AdminJDBCDAO;
import com.admin.AdminJNDIDAO;
import com.mem.dao.MemDAO_interface;
import com.mem.model.MemDAO;
import com.mem.model.MemVO;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class MemService {
//	private MemDAO_interface dao = (MemDAO_interface) new MemDAO();
	
	MemDAO_interface dao;
	
	public MemService() {
//		dao = new MemJDBCDAO();
		dao = new MemJNDIDAO();
		
	};

	public MemVO addMem(String memEmail, String memPsd, String memLname, String memFname, String memNickname,
			String memPhone, String memAddress, byte[] memPicId, Timestamp registrationDate, Timestamp lastEditDate,
			Timestamp lastOnlineDate, Timestamp lastPostDate, Integer memStatus, Integer postSuspended,
			Integer postReportedNum) {
			
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
		this.dao.insert(memVO);
		return memVO;
	}

	public MemVO updateMem(String memPsd, String memLname, String memFname, String memNickname, String memPhone,
			String memAddress, byte[] memPicId, Timestamp lastEditDate, Timestamp lastOnlineDate,
			Timestamp lastPostDate, Integer memStatus, Integer postSuspended, Integer postReportedNum, Integer memId) {
		MemVO memVO = new MemVO();
		memVO.setMemPsd(memPsd);
		memVO.setMemLname(memLname);
		memVO.setMemFname(memFname);
		memVO.setMemNickname(memNickname);
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
		this.dao.update(memVO);
		return memVO;
	}

	public void deleteMem(Integer memId) {
		this.dao.delete(memId);
	}

	public MemVO getOneMem(Integer memId) {
		return this.dao.findByPrimaryKey(memId);
	}

	public List<MemVO> getAll() {
		return this.dao.getAll();
	}
	
	public MemVO login(String memEmail, String memPsd) {
		if (!checkValue(memEmail)||!checkValue(memPsd)) {
			return null;
		}		
		return dao.login(memEmail, memPsd);
	}
	
	private boolean checkValue(String value) {
		if (value == null || Objects.equals(value, "")) {
				System.out.println(value);
				return false;
		}
		return true;
	}
	
	
}
