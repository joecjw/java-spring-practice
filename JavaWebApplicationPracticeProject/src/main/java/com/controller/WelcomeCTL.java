package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import utility.DataUtility;
import utility.ServletUtility;

/**
 * Servlet implementation class WelcomeCTL
 */
@WebServlet(name="WelcomeCTL", urlPatterns={"/WelcomeCTL"})	
public class WelcomeCTL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelcomeCTL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")==null) {
			utility.ServletUtility.redirect(com.controller.JWAView.LoginCTL, request, response);
		}else {
			utility.ServletUtility.forward(com.controller.JWAView.WelcomeView, request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		boolean error = false; 
		try {
	            List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
	            for (FileItem fileItem : items) {
	                System.out.println("fileNAme: "+fileItem.getName());
	                String fileName = fileItem.getName();
	                fileItem.write(new File("C:\\Users\\Joe Chan\\JavaWebApplicationPracticeProject\\src\\main\\webapp\\userImages", fileName));
	            }
	            
	        } catch (FileUploadException e) {
	            // TODO Auto-generated catch block
	        	 error = true;
				 utility.ServletUtility.setErrorMessage("Upload Failed", request);
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	        	 error = true;
				 utility.ServletUtility.setErrorMessage("Upload Failed", request);
	        }
			 if(error == false) {
				 utility.ServletUtility.setSuccessMessage("Successfully Uploaded", request);
			 }
		 
			 utility.ServletUtility.forward(com.controller.JWAView.WelcomeView, request, response);
	    }
}
