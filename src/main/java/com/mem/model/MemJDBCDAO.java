package com.mem.model;

import com.mem.dao.MemDAO_interface;
import com.mem.model.MemVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemJDBCDAO implements MemDAO_interface {
  String driver = "com.mysql.cj.jdbc.Driver";
  
  String url = "jdbc:mysql://localhost:3306/molife?serverTimezone=Asia/Taipei";
  
  String userid = "root";
  
  String passwd = "password";
  
  private static final String INSERT_STMT = "INSERT INTO member (member_email, member_password, member_last_name, member_first_name, member_nickname, member_phone, member_address, member_picture_id, registration_date, last_edit_date, last_online_date, last_post_date, member_status, post_suspended, post_reported_num) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  
  private static final String GET_ALL_STMT = "SELECT member_id, member_email, member_password, member_last_name, member_first_name, member_nickname, member_phone, member_address, member_picture_id, registration_date, last_edit_date, last_online_date, last_post_date, member_status, post_suspended, post_reported_num FROM member order by member_id";
  
  private static final String GET_ONE_STMT = "SELECT member_id, member_email, member_password, member_last_name, member_first_name, member_nickname, member_phone, member_address, member_picture_id, registration_date, last_edit_date, last_online_date, last_post_date, member_status, post_suspended, post_reported_num FROM member where member_id = ?";
  
  private static final String DELETE = "DELETE FROM member where member_id = ?";
  
  private static final String UPDATE = "UPDATE member set member_password=?, member_last_name=?, member_first_name=?, member_nickname=?, member_phone=?, member_address=?, member_picture_id=?, last_edit_date=?, last_online_date=?, last_post_date=?, member_status=?, post_suspended=?, post_reported_num=? where member_id = ?";
  
  private static final String LOGIN = "SELECT * FROM member where member_email=? and member_password=?";

  public void insert(MemVO memVO) {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, userid, passwd);
      pstmt = con.prepareStatement(INSERT_STMT);
      
      pstmt.setString(1, memVO.getMemEmail());
      pstmt.setString(2, memVO.getMemPsd());
      pstmt.setString(3, memVO.getMemLname());
      pstmt.setString(4, memVO.getMemFname());
      pstmt.setString(5, memVO.getMemNickname());
      pstmt.setString(6, memVO.getMemPhone());
      pstmt.setString(7, memVO.getMemAddress());
      pstmt.setBytes(8, memVO.getMemPicId());
      pstmt.setTimestamp(9, memVO.getRegistrationDate());
      pstmt.setTimestamp(10, memVO.getLastEditDate());
      pstmt.setTimestamp(11, memVO.getLastOnlineDate());
      pstmt.setTimestamp(12, memVO.getLastPostDate());
      pstmt.setInt(13, memVO.getMemStatus().intValue());
      pstmt.setInt(14, memVO.getPostSuspended().intValue());
      pstmt.setInt(15, memVO.getPostReportedNum().intValue());
      pstmt.executeUpdate();
    } catch (SQLException se) {
      throw new RuntimeException("A database error occured. " + se.getMessage());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (pstmt != null)
        try {
          pstmt.close();
        } catch (SQLException se) {
          se.printStackTrace(System.err);
        }  
      if (con != null)
        try {
          con.close();
        } catch (Exception e) {
          e.printStackTrace(System.err);
        }  
    } 
  }
  
  public void update(MemVO memVO) {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, userid, passwd);
      pstmt = con.prepareStatement(UPDATE);
      pstmt.setString(1, memVO.getMemPsd());
      pstmt.setString(2, memVO.getMemLname());
      pstmt.setString(3, memVO.getMemFname());
      pstmt.setString(4, memVO.getMemNickname());
      pstmt.setString(5, memVO.getMemPhone());
      pstmt.setString(6, memVO.getMemAddress());
      pstmt.setBytes(7, memVO.getMemPicId());
      pstmt.setTimestamp(8, memVO.getLastEditDate());
      pstmt.setTimestamp(9, memVO.getLastOnlineDate());
      pstmt.setTimestamp(10, memVO.getLastPostDate());
      pstmt.setInt(11, memVO.getMemStatus().intValue());
      pstmt.setInt(12, memVO.getPostSuspended().intValue());
      pstmt.setInt(13, memVO.getPostReportedNum().intValue());
      pstmt.setInt(14, memVO.getMemId().intValue());
      pstmt.executeUpdate();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Couldn't load database driver. " + 
          e.getMessage());
    } catch (SQLException se) {
      throw new RuntimeException("A database error occured. " + se.getMessage());
    } finally {
      if (pstmt != null)
        try {
          pstmt.close();
        } catch (SQLException se) {
          se.printStackTrace(System.err);
        }  
      if (con != null)
        try {
          con.close();
        } catch (Exception e) {
          e.printStackTrace(System.err);
        }  
    } 
  }
  
  public void delete(Integer member_id) {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, userid, passwd);
      pstmt = con.prepareStatement(DELETE);
      pstmt.setInt(1, member_id.intValue());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("A database error occurred. " + e.getMessage());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      try {
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public MemVO findByPrimaryKey(Integer member_id) {
    MemVO memVO = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, userid, passwd);
      pstmt = con.prepareStatement(GET_ONE_STMT);
      pstmt.setInt(1, member_id.intValue());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        memVO = new MemVO();
        memVO.setMemId(Integer.valueOf(rs.getInt("MEMBER_ID")));
        memVO.setMemEmail(rs.getString("MEMBER_EMAIL"));
        memVO.setMemPsd(rs.getString("MEMBER_PASSWORD"));
        memVO.setMemLname(rs.getString("MEMBER_LAST_NAME"));
        memVO.setMemFname(rs.getString("MEMBER_FIRST_NAME"));
        memVO.setMemNickname(rs.getString("MEMBER_NICKNAME"));
        memVO.setMemPhone(rs.getString("MEMBER_PHONE"));
        memVO.setMemAddress(rs.getString("MEMBER_ADDRESS"));
        memVO.setMemPicId(rs.getBytes("MEMBER_PICTURE_ID"));
        memVO.setRegistrationDate(rs.getTimestamp("REGISTRATION_DATE"));
        memVO.setLastEditDate(rs.getTimestamp("LAST_EDIT_DATE"));
        memVO.setLastEditDate(rs.getTimestamp("LAST_ONLINE_DATE"));
        memVO.setLastEditDate(rs.getTimestamp("LAST_POST_DATE"));
        memVO.setMemStatus(Integer.valueOf(rs.getInt("MEMBER_STATUS")));
        memVO.setPostReportedNum(Integer.valueOf(rs.getInt("POST_SUSPENDED")));
        memVO.setPostReportedNum(Integer.valueOf(rs.getInt("POST_REPORTED_NUM")));
      } 
    } catch (SQLException e) {
      throw new RuntimeException("A database error occurred. " + e.getMessage());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      try {
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return memVO;
  }
  
  public List<MemVO> getAll() {
    List<MemVO> list = new ArrayList<>();
    MemVO memVO = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, userid, passwd);
      pstmt = con.prepareStatement(GET_ALL_STMT);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        memVO = new MemVO();
        memVO.setMemId(Integer.valueOf(rs.getInt("MEMBER_ID")));
        memVO.setMemEmail(rs.getString("MEMBER_EMAIL"));
        memVO.setMemPsd(rs.getString("MEMBER_PASSWORD"));
        memVO.setMemLname(rs.getString("MEMBER_LAST_NAME"));
        memVO.setMemFname(rs.getString("MEMBER_FIRST_NAME"));
        memVO.setMemNickname(rs.getString("MEMBER_NICKNAME"));
        memVO.setMemPhone(rs.getString("MEMBER_PHONE"));
        memVO.setMemAddress(rs.getString("MEMBER_ADDRESS"));
        memVO.setMemPicId(rs.getBytes("MEMBER_PICTURE_ID"));
        memVO.setRegistrationDate(rs.getTimestamp("REGISTRATION_DATE"));
        memVO.setLastEditDate(rs.getTimestamp("LAST_EDIT_DATE"));
        memVO.setLastEditDate(rs.getTimestamp("LAST_ONLINE_DATE"));
        memVO.setLastEditDate(rs.getTimestamp("LAST_POST_DATE"));
        memVO.setMemStatus(Integer.valueOf(rs.getInt("MEMBER_STATUS")));
        memVO.setPostReportedNum(Integer.valueOf(rs.getInt("POST_SUSPENDED")));
        memVO.setPostReportedNum(Integer.valueOf(rs.getInt("POST_REPORTED_NUM")));
        list.add(memVO);
      } 
    } catch (SQLException e) {
      throw new RuntimeException("A database error occurred. " + e.getMessage());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      try {
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public static void main(String[] args) {
    MemDAO_interface dao = new com.mem.model.MemJDBCDAO();
  }

  @Override
	public MemVO login(String memEmail, String memPsd) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
		    con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(LOGIN);

			pstmt.setString(1, memEmail);
			pstmt.setString(2, memPsd);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memVO = new MemVO();
				memVO.setMemId(rs.getInt("MEMBER_ID"));
				memVO.setMemEmail(rs.getString("MEMBER_EMAIL"));
				memVO.setMemPsd(rs.getString("MEMBER_PASSWORD"));
				memVO.setMemLname(rs.getString("MEMBER_LAST_NAME"));
				memVO.setMemFname(rs.getString("MEMBER_FIRST_NAME"));
				memVO.setMemNickname(rs.getString("MEMBER_NICKNAME"));
				memVO.setMemPhone(rs.getString("MEMBER_PHONE"));
				memVO.setMemAddress(rs.getString("MEMBER_ADDRESS"));
				memVO.setMemPicId(rs.getBytes("MEMBER_PICTURE_ID"));
				memVO.setRegistrationDate(rs.getTimestamp("REGISTRATION_DATE"));
				memVO.setLastEditDate(rs.getTimestamp("LAST_EDIT_DATE"));
				memVO.setLastOnlineDate(rs.getTimestamp("LAST_ONLINE_DATE"));
				memVO.setLastPostDate(rs.getTimestamp("LAST_POST_DATE"));
				memVO.setMemStatus(Integer.valueOf(rs.getInt("MEMBER_STATUS")));
				memVO.setPostSuspended(Integer.valueOf(rs.getInt("POST_SUSPENDED")));
				memVO.setPostReportedNum(Integer.valueOf(rs.getInt("POST_REPORTED_NUM")));
			}
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("讀不到資料庫driver"+e.getMessage());	
			// 處理 任何 driver 錯誤
		} catch (SQLException se) {
			throw new RuntimeException("發生資料庫錯誤." + se.getMessage());
			// 清理 JDBC 資源
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return memVO;
	}
}
