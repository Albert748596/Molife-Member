package com.auth.VO;

public class AuthVO {

	private Integer empAuthId;
		
	private String authName;

	@Override
	public String toString() {
		return "Auth_VO [authId=" + empAuthId + ", authName=" + authName + "]";
	}

	public Integer getEmpAuthId() {
		return empAuthId;
	}

	public void setEmpAuthId(Integer empAuthId) {
		this.empAuthId = empAuthId;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}


	
	
}
