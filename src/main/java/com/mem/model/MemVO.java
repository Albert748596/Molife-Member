package com.mem.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

public class MemVO implements Serializable {
  private Integer memId;
  
  private String memEmail;
  
  private String memPsd;
  
  private String memLname;
  
  private String memFname;
  
  private String memNickname;
  
  private String memPhone;
  
  private String memAddress;
  
  private byte[] memPicId;
  
  private Timestamp registrationDate;
  
  private Timestamp lastEditDate;
  
  private Timestamp lastOnlineDate;
  
  private Timestamp lastPostDate;
  
  private Integer memStatus;
  
  private Integer postSuspended;
  
  private Integer postReportedNum;
  
  
  public Integer getMemId() {
    return this.memId;
  }
  
  public void setMemId(Integer memId) {
    this.memId = memId;
  }
  
  public String getMemEmail() {
    return this.memEmail;
  }
  
  public void setMemEmail(String memEmail) {
    this.memEmail = memEmail;
  }
  
  public String getMemPsd() {
    return this.memPsd;
  }
  
  public void setMemPsd(String memPsd) {
    this.memPsd = memPsd;
  }
  
  public String getMemLname() {
    return this.memLname;
  }
  
  public void setMemLname(String memLname) {
    this.memLname = memLname;
  }
  
  public String getMemFname() {
    return this.memFname;
  }
  
  public void setMemFname(String memFname) {
    this.memFname = memFname;
  }
  
  public String getMemNickname() {
    return this.memNickname;
  }
  
  public void setMemNickname(String memNickname) {
    this.memNickname = memNickname;
  }
  
  public String getMemPhone() {
    return this.memPhone;
  }
  
  public void setMemPhone(String memPhone) {
    this.memPhone = memPhone;
  }
  
  public String getMemAddress() {
    return this.memAddress;
  }
  
  public void setMemAddress(String memAddress) {
    this.memAddress = memAddress;
  }
  
  public byte[] getMemPicId() {
    return this.memPicId;
  }
  
  public void setMemPicId(byte[] memPicId) {
    this.memPicId = memPicId;
  }
  
  public Timestamp getRegistrationDate() {
    return this.registrationDate;
  }
  
  public void setRegistrationDate(Timestamp registrationDate) {
    this.registrationDate = registrationDate;
  }
  
  public Timestamp getLastEditDate() {
    return this.lastEditDate;
  }
  
  public void setLastEditDate(Timestamp lastEditDate) {
    this.lastEditDate = lastEditDate;
  }
  
  public Timestamp getLastOnlineDate() {
    return this.lastOnlineDate;
  }
  
  public void setLastOnlineDate(Timestamp lastOnlineDate) {
    this.lastOnlineDate = lastOnlineDate;
  }
  
  public Timestamp getLastPostDate() {
    return this.lastPostDate;
  }
  
  public void setLastPostDate(Timestamp lastPostDate) {
    this.lastPostDate = lastPostDate;
  }
  
  public Integer getMemStatus() {
    return this.memStatus;
  }
  
  public void setMemStatus(Integer memStatus) {
    this.memStatus = memStatus;
  }
  
  public Integer getPostSuspended() {
    return this.postSuspended;
  }
  
  public void setPostSuspended(Integer postSuspended) {
    this.postSuspended = postSuspended;
  }
  
  public Integer getPostReportedNum() {
    return this.postReportedNum;
  }
  
  public void setPostReportedNum(Integer postReportedNum) {
    this.postReportedNum = postReportedNum;
  }
}

