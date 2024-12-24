<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP Boilerplate</title>
    <%@include file = "AllComponent/AllCssHeaders.jsp"%>
    <style>
            body {
                margin: 0;
                padding: 0;
                font-family: Arial, sans-serif;
            }
            .background-image {
                background: url('AllComponent/Images/Book.jpg') no-repeat center center;
                background-size: cover;
                height: 400px; /* Adjust the height as needed */
                position: relative;
            }
        </style>
</head>
<body>
    <h1>Welcome to JSP</h1>
    <p>This is a basic JSP file.</p>
     <%@include file = "AllComponent/NavBar.jsp"%>

     <div class="background-image container-fluid">
     </div>

     <div class="card" style="width: 18rem;">
       <img src="AllComponent/Images/javaBook.jpg" class="card-img-top" alt="...">
       <div class="card-body">
         <h5 class="card-title">Java</h5>
       </div>
       <ul class="list-group list-group-flush">
         <li class="list-group-item">Author: Saxena</li>
         <li class="list-group-item">Price: 350</li>
         <li class="list-group-item">Categeory: New</li>
       </ul>
       <div class="card-body">
         <a href="#" class="card-link">View Details</a>
         <a href="#" class="card-link">Add to Card</a>
       </div>
     </div>

    <%@include file = "AllComponent/AllCssJavaScript.jsp"%>
</body>
</html>

