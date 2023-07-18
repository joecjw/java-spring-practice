<%@page import="com.controller.JWAView"%>
<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
 <main class="reset-password-form" style="padding-top: 75px">
 <div class="cotainer">
   <div class="row justify-content-center">
     <div class="col-md-8">
       <div class="card">
         <div class="card-header">
           Reset Your Password 
           <h6 style="color: green;"></h6>
         </div>
         <div class = "reset-result-msg" style="color: green;text-align:center; font-weight:bold ">
 			<%=utility.ServletUtility.getSuccessMessage(request)%>
         </div>
         <div class = "reset-result-msg" style="color:red;text-align:center; font-weight:bold ">
       	<%=utility.ServletUtility.getErrorMessage(request)%>
         </div>
         <div class="card-body">
           <form action="<%=JWAView.ForgotPasswordCTL%>" method="post">
             <div class="form-group row">
               <label for="email_address" 
                 class="col-md-4 col-form-label text-md-right">Login Id<font color="red">*</font></label>
               <div class="col-md-6">
                 <input type="text" id="login"  class="form-control" placeholder="Enter Login Id"
                   name="login" value="" >
                   <font  color="red"></font>
               </div>
             </div>
             <div class="form-group row">
               <label for="new-password"
                 class="col-md-4 col-form-label text-md-right">New Password<font color="red">*</font></label>
               <div class="col-md-6">
                 <input type="password" id="password" class="form-control" placeholder="Enter New Password"
                   name="password" value="" >
                   <font color="red"> </font>
               </div>
             </div>
             <div class="col-md-6 offset-md-4">
               <input type="submit" class="btn btn-primary" name="operation" value="Reset">
             </div>
           </form>
         </div>
       </div>
     </div>
   </div>
 </div>
 </main>
<%@ include file="footer.jsp" %>
</body>
</html>