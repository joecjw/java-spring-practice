<%@page import="com.controller.JWAView"%>
<%@page import="utility.DataUtility"%>
<%@page import="beans.UserBeans"%>
<%@page import="utility.ServletUtility"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp"%>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//for HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //for HTTP 1.0
	response.setHeader("Expores","0"); //for Proxies
			
    int index = 1;
    List list = ServletUtility.getList(request);
	  if(list!=null && list.size()!=0){
        Iterator it = list.iterator();
%>
<div class = "deleteUser-success-msg" style="color: green;text-align:center; ">
	<%=utility.ServletUtility.getSuccessMessage(request)%>
</div>
<table class="table table-striped">
  <thead>
    <tr>
      <th scope="col">Id</th>
      <th scope="col">Name</th>
      <th scope="col">Login</th>
      <th scope="col">MobileNo</th>
      <th scope="col">Date of Birth</th>
      <th scope="col">Action</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
    <tbody>
        <%
	          while(it.hasNext()){
        		UserBeans user = (UserBeans)it.next();
		%>	
			    <tr>
			      <th scope="row"><%=index++%></th>
			      <td><%=user.getFirstName()+" "+user.getLastName()%></td>
			      <td><%=user.getLogin()%></td>
			      <td><%=user.getMobileNo()%></td>
			      <td><%=DataUtility.getDateString(user.getDob())%></td>
			      <td><a href="/JavaWebApplicationPracticeProject/RegistrationCTL?id=<%=user.getId()%>">Edit</a></td>
			      <td><a href="/JavaWebApplicationPracticeProject/UserListCTL?id=<%=user.getId()%>&operation=delete">Delete</a></td>
	    		</tr>
			<%}%>

  </tbody>
</table>
		<%}else{%>
			<div class = "getUserList-error-msg" style="color: red;text-align:center; ">
				<%=utility.ServletUtility.getErrorMessage(request)%>
			</div>
		<%}%>
<%@ include file="footer.jsp"%>
</body>
</html>

