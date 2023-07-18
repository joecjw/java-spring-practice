package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import model.UserModel;
import utility.DataUtility;
import utility.JDBCDataSource;
import utility.ServletUtility;

/**
 * Servlet implementation class LoginCTL
 */
@WebServlet(name="LoginCTL", urlPatterns={"/LoginCTL"})	
public class LoginCTL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCTL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		HttpSession session=request.getSession(false);
	    try {   
	    	if("logout".equalsIgnoreCase(op)) {
		      session.invalidate();
		      ServletUtility.setSuccessMessage("Logout Sucessfully",request);
		    }
	    }catch(Exception e){
	    	System.out.println(e);
	    }
		utility.ServletUtility.forward(JWAView.LoginView, request, response);	 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   UserBeans user = new UserBeans();
	   String login=request.getParameter("login");
	   String pwd=request.getParameter("password");
	   HttpSession session=request.getSession(true);
	   user = UserModel.UserLogin(login,pwd);
	   if(user != null) {
		   session.setAttribute("user", user.getFirstName()+" "+user.getLastName());
		   session.setAttribute("userId", user.getId());
		   utility.ServletUtility.redirect(JWAView.WelcomeCTL, request, response);
	   }else {
	       ServletUtility.setErrorMessage("Invalid User", request);
	       utility.ServletUtility.forward(JWAView.LoginView, request, response);
	   }
	}
}
