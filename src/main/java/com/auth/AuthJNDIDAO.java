package com.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.admin.VO.AdminVO;
import com.auth.VO.AuthVO;
import com.auth.dao.AuthDAOInterface;

public class AuthJNDIDAO implements AuthDAOInterface{
	
	  private static DataSource ds = null;
	  static {
		  try {
			  Context ctx = new InitialContext();
			  ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		  } catch (NamingException e) {
			  e.printStackTrace();
		  } 
	  }

	private static final String INSERT_STMT = "INSERT INTO auth (auth_name) VALUES(?)";
	private static final String GET_ALL_STMT = "SELECT employee_auth_id , auth_name FROM auth";
	private static final String GET_ONE_STMT = "SELECT employee_auth_id , auth_name FROM auth where employee_auth_id = ?";
	private static final String GET_Emps_ByempAuthId_STMT = "SELECT employee_auth_id , auth_name FROM auth where employee_auth_id = ? order by employee_auth_id";
	private static final String DELETE_EMPs = "DELETE FROM auth where employee_auth_id = ?";
	private static final String DELETE_AUTH = "DELETE FROM auth where employee_auth_id = ?";
	private static final String UPDATE = "UPDATE auth set auth_name = ? where employee_auth_id = ?";


	@Override
	public void insert(AuthVO authVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, authVO.getAuthName());
			pstmt.executeUpdate("set auto_increment_offset=1;");
			pstmt.executeUpdate("set auto_increment_increment=1;");
			pstmt.executeUpdate();

		
//			//處理 所有SQL 錯誤
		} catch (SQLException se) {
			throw new RuntimeException("資料庫錯誤出現!" + se.getMessage());
//			清理JDBC資源
		} finally {
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

	}

	@Override
	public void update(AuthVO authVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, authVO.getAuthName());
			pstmt.setInt(2, authVO.getEmpAuthId());

			pstmt.executeUpdate();
			
			// 處理 所有SQL 錯誤
		} catch (SQLException se) {
			throw new RuntimeException("資料庫錯誤出現!" + se.getMessage());
//			清理JDBC資源
		} finally {
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

	}

	@Override
	public void delete(Integer empAuthId) {

		int updateCount_EMPs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

//			 1 在執行更新之前
			con.setAutoCommit(false);

			// 先刪除員工
			pstmt = con.prepareStatement(DELETE_EMPs);
			pstmt.setInt(1, empAuthId);
			updateCount_EMPs = pstmt.executeUpdate();
			// 再刪除部門
			pstmt = con.prepareStatement(DELETE_AUTH);
			pstmt.setInt(1, empAuthId);
			pstmt.executeUpdate();

			// 2設定於pstmt.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除權限編號" + empAuthId + "時，共有管理員" + updateCount_EMPs + "人同時被刪除");
			
			// 處理 所有SQL 錯誤
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3設定於當有exception發生時的catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					// TODO: handle exception
					throw new RuntimeException("rollback 錯誤 發生" + excep.getMessage());
				}
			}
			throw new RuntimeException("資料庫錯誤出現!" + se.getMessage());
//			清理JDBC資源
		} finally {
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

	}

	@Override
	public AuthVO findByPrimaryKey(Integer empAuthId) {

		AuthVO authVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, empAuthId);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				authVO = new AuthVO();
				authVO.setEmpAuthId(rs.getInt("employee_auth_id"));
				authVO.setAuthName(rs.getNString("auth_name"));
			}
			

			// 處理SQL錯誤
		} catch (SQLException se) {
			throw new RuntimeException("發生資料庫錯誤!" + se.getMessage());
			// 清理JDBC資源
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
		return authVO;
	}

	@Override
	public List<AuthVO> getAll() {

		List<AuthVO> list = new ArrayList<AuthVO>();
		AuthVO authVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				authVO = new AuthVO();
				authVO.setEmpAuthId(rs.getInt("employee_auth_id"));
				authVO.setAuthName(rs.getString("auth_name"));
				list.add(authVO); // 把一比資料存在list裡
			}
			
			// 處理SQL錯誤
		} catch (SQLException se) {
			throw new RuntimeException("發生資料庫錯誤!" + se.getMessage());
			// 清理JDBC資源
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

		return list;
	}

	@Override
	public Set<AdminVO> getEmpsByAuthId(Integer empAuthId) {
		Set<AdminVO> set = new LinkedHashSet<AdminVO>();
		AdminVO adminVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Emps_ByempAuthId_STMT);
			pstmt.setInt(1, empAuthId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminVO = new AdminVO();
				adminVO.setAdminId(rs.getInt("admin_id"));
				adminVO.setEmpAcc(rs.getString("employee_account"));
				adminVO.setEmpPsd(rs.getString("employee_password"));
				adminVO.setEmpName(rs.getString("employee_name"));
				adminVO.setEmpPicId(rs.getBytes("employee_picture_id"));
				adminVO.setEmpEmail(rs.getNString("employee_email"));
				adminVO.setEmpAuthId(rs.getInt("employee_auth_id"));
				adminVO.setEmpStatus(rs.getInt("employee_status"));
				set.add(adminVO);
			}
				
			// 處理SQL錯誤
		} catch (SQLException se) {
			throw new RuntimeException("發生資料庫錯誤!" + se.getMessage());
			// 清理JDBC資源
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

		return set;
	}

}
