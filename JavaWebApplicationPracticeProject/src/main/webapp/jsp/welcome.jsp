<%@ page import="utility.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=BIG5"

    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//for HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //for HTTP 1.0
	response.setHeader("Expores","0"); //for Proxies
%>
<h2 style="color: green; text-align:center;"><%=ServletUtility.getSuccessMessage(request)%></h2>
<h1 style = "text-align:center;">Welcome User: <%=session.getAttribute("user")%></h1>

<div class = "upload-result-msg" style="color: green;text-align:center; font-weight:bold ">
	<%=ServletUtility.getSuccessMessage(request)%>
</div>

<div class = "upload-result-msg" style="color:red;text-align:center; font-weight:bold ">
	<%=ServletUtility.getErrorMessage(request)%>
</div>

<form action="<%=com.controller.JWAView.WelcomeCTL%>" enctype="multipart/form-data" method="post" style="display: flex;flex:1;justify-content: center;align-items: center;">
  	<input type="file" id="myFile" name="filename" multiple>
  	<input type="submit" value="Upload">
</form>

<%@ include file="footer.jsp"%>
</body>
</html>
 