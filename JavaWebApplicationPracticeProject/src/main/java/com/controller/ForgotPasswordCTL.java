package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ForgotPasswordCTL
 */
@WebServlet(name="ForgotPasswordCTL", urlPatterns={"/ForgotPasswordCTL"})
public class ForgotPasswordCTL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotPasswordCTL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		utility.ServletUtility.forward(com.controller.JWAView.ResetPasswordView, request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		model.UserModel model = new model.UserModel();
		String password = request.getParameter("password");
		String loginId = request.getParameter("login");
		
		if(password != null && loginId != null) {
			long i = model.resetPassword(password, loginId);
			
			if(i > 0) {
				utility.ServletUtility.setSuccessMessage("Password Reset Successfully", request);
			}else {
				utility.ServletUtility.setErrorMessage("Password Reset Failed", request);
			}
		}else {
			utility.ServletUtility.setErrorMessage("Password Reset Failed", request);

		}
		
		utility.ServletUtility.forward(com.controller.JWAView.ResetPasswordView, request, response);
	}

}
