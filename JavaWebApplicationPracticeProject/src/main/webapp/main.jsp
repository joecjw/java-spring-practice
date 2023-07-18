<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<meta charset="BIG5">
<%@ include file="jsp/header.jsp"%>
<body>
	<h2 style = "padding: 20px; text-align: center; color:white; background-color: black">Welcome</h2>
	<div id="carouselExampleControls" class="carousel slide" data-ride="carousel" style = "color:white">
	  <div class="carousel-inner">
	    <div class="carousel-item active">
	      <img src="/JavaWebApplicationPracticeProject/image/slide-1.jpg" 
	       class="d-block w-100" alt="..." height = "800px">
	    </div>
	    <div class="carousel-item">
	      <img src="/JavaWebApplicationPracticeProject/image/slide-2.jpg"
	       class="d-block w-100" alt="..."height = "800px">
	    </div>
	    <div class="carousel-item">
	      <img src="/JavaWebApplicationPracticeProject/image/slide-3.jpg"
	       class="d-block w-100" alt="..."height = "800px">
	    </div>
	  </div>
	  <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
	    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	    <span class="sr-only">Previous</span>
	  </a>
	  <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
	    <span class="carousel-control-next-icon" aria-hidden="true"></span>
	    <span class="sr-only">Next</span>
	  </a>
	</div>
</body>
<%@ include file="jsp/footer.jsp"%>
</html>