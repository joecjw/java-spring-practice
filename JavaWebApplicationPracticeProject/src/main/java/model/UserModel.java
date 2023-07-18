package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import beans.UserBeans;
import utility.JDBCDataSource;

import jdk.nashorn.internal.ir.WhileNode;


public class UserModel {
  
  public static long nextPk() {
    long pk = 0;
    Connection conn;
    try {
      conn = JDBCDataSource.getConnection();
      PreparedStatement stmt = conn.prepareStatement("select Max(id) from user");
      ResultSet rs = stmt.executeQuery();
      while(rs.next()){
        pk = rs.getLong(1);
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return pk+1;
    
  }
  
  
  public static long addUser(UserBeans user) {
    int i = 0;
    try {
      Connection conn = JDBCDataSource.getConnection();
      PreparedStatement stmt = conn.prepareStatement("insert into user values(?,?,?,?,?,?,?)");
      stmt.setLong(1, nextPk());
      stmt.setString(2 , user.getFirstName() );
      stmt.setString(3 , user.getLastName() );
      stmt.setString(4 , user.getLogin() );
      stmt.setString(5 , user.getPassword() );
      stmt.setDate(6 , new java.sql.Date(user.getDob().getTime()) );
      stmt.setString(7 , user.getMobileNo() );
        i =     stmt.executeUpdate();
      
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    
    return i;
  }

  
  //Login User......
  public static UserBeans UserLogin(String login,String password) {
    Connection con;
    UserBeans user = null;
    try {
      con = JDBCDataSource.getConnection();
      PreparedStatement stmt = con.prepareStatement("Select * from user where login=? and password = ?");
      stmt.setString(1,login);
      stmt.setString(2,password);
      ResultSet rs = stmt.executeQuery();
      if(rs.next()) {
        user = new UserBeans();
        System.out.println("ID: "+rs.getLong("id"));
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("fname"));
        user.setLastName(rs.getString("lname"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setDob(rs.getDate("dob"));
        user.setMobileNo(rs.getString("mobile"));    
      }
      
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return user;
  }
  public static List list() {
	  ArrayList list=new ArrayList();
	  Connection conn=null;
	  try {
	     conn=JDBCDataSource.getConnection();
	    PreparedStatement pstmt=conn.prepareStatement("Select * from user");
	    ResultSet rs= pstmt.executeQuery();
	    while (rs.next()) {
	    UserBeans user=new UserBeans();
	    user.setId(rs.getLong("id"));
	    user.setFirstName(rs.getString("fname"));
	    user.setLastName(rs.getString("lname"));
	    user.setLogin(rs.getString("login"));
	    user.setPassword(rs.getString("password"));
	    user.setDob(rs.getDate("dob"));
	    user.setMobileNo(rs.getString("mobile"));
	    list.add(user);
	    }
	  } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	  }finally {
	    JDBCDataSource.closeConnection(conn);
	  }
	  return list;
	}
  
  public static List UserViewlist(long id) {
	  ArrayList list=new ArrayList();
	  Connection conn=null;
	  try {
	     conn=JDBCDataSource.getConnection();
	    PreparedStatement pstmt=conn.prepareStatement("Select * from user where id!=?");
	    pstmt.setLong(1,id);
	    ResultSet rs= pstmt.executeQuery();
	    while (rs.next()) {
	    UserBeans user=new UserBeans();
	    user.setId(rs.getLong("id"));
	    user.setFirstName(rs.getString("fname"));
	    user.setLastName(rs.getString("lname"));
	    user.setLogin(rs.getString("login"));
	    user.setPassword(rs.getString("password"));
	    user.setDob(rs.getDate("dob"));
	    user.setMobileNo(rs.getString("mobile"));
	    list.add(user);
	    }
	  } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	  }finally {
	    JDBCDataSource.closeConnection(conn);
	  }
	  return list;
	}
  
  public static UserBeans FindByPk(long id) {
	    Connection con;
	    UserBeans user = null;
	    try {
	      con = JDBCDataSource.getConnection();
	      PreparedStatement stmt = con.prepareStatement("Select * from user where id=?");
	      stmt.setLong(1,id);
	      ResultSet rs = stmt.executeQuery();
	      if(rs.next()) {
	        user = new UserBeans();
	        System.out.println("ID: "+rs.getLong("id"));
	        user.setId(rs.getLong("id"));
	        user.setFirstName(rs.getString("fname"));
	        user.setLastName(rs.getString("lname"));
	        user.setLogin(rs.getString("login"));
	        user.setPassword(rs.getString("password"));
	        user.setDob(rs.getDate("dob"));
	        user.setMobileNo(rs.getString("mobile"));  
	      }
	      
	    } catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    
	    return user;
  }
  
  public static long UpdateUser(UserBeans user) {
	    int i = 0;
	    try {
	      Connection conn = JDBCDataSource.getConnection();
	      PreparedStatement stmt = conn
	          .prepareStatement("update user set fname=?, lname=?, login=?,password=?,dob=?,mobile=? where id=?");
	      stmt.setString(1, user.getFirstName());
	      stmt.setString(2, user.getLastName());
	      stmt.setString(3, user.getLogin());
	      stmt.setString(4, user.getPassword());
	      stmt.setDate(5, new java.sql.Date(user.getDob().getTime()));
	      stmt.setString(6, user.getMobileNo());
	      stmt.setLong(7, user.getId());
	      i = stmt.executeUpdate();
	    } catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    return i;
	  }
  
  public static long delete(long id) {
	  int i = 0;
	  try {
	    Connection conn = JDBCDataSource.getConnection();
	    PreparedStatement stmt = conn.prepareStatement("DELETE from user where id=?");
	    stmt.setLong(1, id);
	    i = stmt.executeUpdate();
	  } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	  }
	  return i;
	}
  
  public static long resetPassword(String password, String loginId) {
	  int i = 0;
	  try {
	    Connection conn = JDBCDataSource.getConnection();
	    PreparedStatement stmt = conn.prepareStatement("update user set password=? where login=?");
	    stmt.setString(1, password);
	    stmt.setString(2, loginId);
	    i = stmt.executeUpdate();
	  } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	  }
	  return i;
  }
}