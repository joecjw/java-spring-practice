package com.controller;

public interface JWAView {
	
	public String APP_CONTEXT = "/JavaWebApplicationPracticeProject";
	public String Page_FOLDER = "/jsp";
	
	public String NavView =  "/main.jsp";
	public String HomeView = Page_FOLDER + "/home.jsp";
	public String LoginView =  Page_FOLDER + "/LoginView.jsp";
	public String RegistrationView =  Page_FOLDER + "/RegistrationView.jsp";
	public String WelcomeView =  Page_FOLDER + "/welcome.jsp";
	public String UserView =  Page_FOLDER + "/UserView.jsp";
	public String ResetPasswordView =  Page_FOLDER + "/ResetPasswordView.jsp";
	
	public String NavCTL = APP_CONTEXT + "/NavCTL";
	public String HomeCTL = APP_CONTEXT + "/HomeCTL";
	public String LoginCTL = APP_CONTEXT + "/LoginCTL";
	public String RegistrationCTL = APP_CONTEXT + "/RegistrationCTL";
	public String WelcomeCTL = APP_CONTEXT + "/WelcomeCTL";
	public String UserListCTL = APP_CONTEXT + "/UserListCTL";
	public String ForgotPasswordCTL = APP_CONTEXT + "/ForgotPasswordCTL";
}