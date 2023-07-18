<%@page import="utility.DataUtility"%>
<%@page import="utility.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>User Registration</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <script>
 $( function() {
      $( "#datepicker" ).datepicker({
          dateFormat : 'mm/dd/yy',
          changeMonth: true,
        changeYear: true
      });
    } );
  </script>
</head>
<%@ include file="header.jsp"%>
  <main class="login-form" style="padding-top: 75px">
  <div class="cotainer">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card">
          <div class="card-header">
            User Registration
          </div>
          <div class = "reg-result-msg" style="color: green;text-align:center; font-weight:bold ">
  			<%=ServletUtility.getSuccessMessage(request)%>
          </div>
          <div class = "reg-result-msg" style="color:red;text-align:center; font-weight:bold ">
        	<%=ServletUtility.getErrorMessage(request)%>
          </div>
          <div class="card-body">
          <jsp:useBean id="bean" class="beans.UserBeans" scope="request"></jsp:useBean>
            <form action="<%=com.controller.JWAView.RegistrationCTL%>" method="post">
              <input type="hidden" name="id" value="<%=DataUtility.getStringData(bean.getId())%>"> 
              
              <div class="form-group row">
                <label for="email_address" 
                  class="col-md-4 col-form-label text-md-right">First Name<font color="red"></font></label>
                <div class="col-md-6">
                  <input type="text"   class="form-control" placeholder="Enter First Name"
                    name="firstName" value="<%=(bean.getFirstName() == null)? "":bean.getFirstName()%>">
                    <font  color="red"></font>
                </div>
              </div>
              
              <div class="form-group row">
                <label for="email_address" 
                  class="col-md-4 col-form-label text-md-right">Last Name<font color="red"></font></label>
                <div class="col-md-6">
                  <input type="text"   class="form-control" placeholder="Enter Last Name"
                    name="lastName" value="<%=(bean.getLastName() == null)? "":bean.getLastName()%>" >
                    <font  color="red"></font>
                </div>
              </div>
              
              <div class="form-group row">
                <label for="email_address" 
                  class="col-md-4 col-form-label text-md-right">Login Id<font color="red"></font></label>
                <div class="col-md-6">
                  <input type="text" id="email_address"  class="form-control" placeholder="Enter Login Id"
                    name="login" value="<%=(bean.getLogin() == null)? "":bean.getLogin()%>" >
                    <font  color="red"></font>
                </div>
              </div>
              
              
              <div class="form-group row">
                <label for="email_address" 
                  class="col-md-4 col-form-label text-md-right">Password<font color="red"></font></label>
                <div class="col-md-6">
                  <input type="password" id="email_address"  class="form-control" placeholder="Enter password"
                    name="password" value="<%=(bean.getPassword() == null)? "":bean.getPassword()%>" >
                    <font  color="red"></font>
                </div>
              </div>
              
              <div class="form-group row">
                <label for="email_address" 
                  class="col-md-4 col-form-label text-md-right">Date Of Birth<font color="red"></font></label>
                <div class="col-md-6">
                  <input type="text"  id="datepicker" class="form-control" placeholder="Enter Date Of Birth"
                    name="dob" value="<%=(bean.getDob() == null)? "":bean.getDob()%>" >
                    <font  color="red"></font>
                </div>
              </div>
              
              <div class="form-group row">
                <label for="email_address" 
                  class="col-md-4 col-form-label text-md-right">Mobile No.<font color="red"></font></label>
                <div class="col-md-6">
                  <input type="text" id="email_address"  class="form-control" placeholder="Enter Mobile No"
                    name="mobile" value="<%=(bean.getMobileNo() == null)? "":bean.getMobileNo()%>" >
                    <font  color="red"></font>
                </div>
              </div>
              
              <%if(bean.getId() > 0){%> 
	              <div class="col-md-6 offset-md-4">
	                <input type="submit" class="btn btn-primary" name="operation" value="Update">
	              </div>
              <%}else{%>
	              <div class="col-md-6 offset-md-4">
	                <input type="submit" class="btn btn-primary" name="operation" value="Register">
	              </div>
              <%}%>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  </main>
  <div style="margin-top: 120px">
  </div> 
  <%@ include file="footer.jsp"%>
</body>
</html>