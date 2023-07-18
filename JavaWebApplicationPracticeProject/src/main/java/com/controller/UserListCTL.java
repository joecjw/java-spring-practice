package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UserBeans;
import model.UserModel;
import utility.DataUtility;
import utility.ServletUtility;

/**
 * Servlet implementation class UserListCTL
 */
@WebServlet(name="UserListCTL",  urlPatterns={"/UserListCTL"})
public class UserListCTL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserListCTL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getSession().getAttribute("user")==null) {
			utility.ServletUtility.redirect(com.controller.JWAView.LoginCTL, request, response);
		}else {
			if(request.getParameter("operation")!=null) {
				if(request.getParameter("operation").compareToIgnoreCase("delete") == 0) {
				  long id = DataUtility.getLong(request.getParameter("id"));
				  if(id>0) {
					//Delete
				    UserModel model=new UserModel();
				    UserModel.delete(id);
				    ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
				  }
				}
			}
			UserModel model=new UserModel();
		    List list=null;
		    long userId = (Long) request.getSession().getAttribute("userId");
		    list=model.UserViewlist(userId);
		    System.out.println(list.size());
		    
		    if(list==null || list.size()==0){
		      ServletUtility.setErrorMessage("Record Not Found", request);
		    }
		    ServletUtility.setList(list, request);
		    ServletUtility.forward(JWAView.UserView, request, response);
		}
	 }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
