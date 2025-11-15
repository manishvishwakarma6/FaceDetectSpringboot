<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="manifest" href="/manifest.json">
<meta name="theme-color" content="#000000">
<script>
if ("serviceWorker" in navigator) {
    navigator.serviceWorker.register("/service-worker.js")
    .then(() => console.log("SW registered"))
    .catch(err => console.log("SW error:", err));
}
</script>


    <title>Face Detection</title>
    <style>
      
        .container2 {
            max-width: 1500px;
            margin: 50px auto;
            padding: 30px;
                background-color: #f9f9f959;
            border-radius: 12px;
            color:white;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        input[type="file"] {
            display: block;
            margin: 20px auto;
            padding: 10px;
            border: 2px dashed #007bff;
            border-radius: 8px;
            background-color: #11111159;
            cursor: pointer;
        }
        button {
           background-color: #005b8bd9;
    color: white;
    border: none;
    padding: 12px 35px;
    border-radius: 10px;
    cursor: pointer;
    font-size: 17px;
        }
        .loader {
            border: 10px solid #f3f3f3;
            border-top: 10px solid #3498db;
            border-radius: 50%;
            width: 50px;
            height: 50px;
            animation: spin 1s linear infinite;
            margin: 0 auto;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
</head>
<body>

<!-- ============= Home Page============= -->

	<%@ include file="navbar.jsp"%>

	<!-- ================================= -->

<div class="container2">
    <form method="post" action="face" enctype="multipart/form-data" onsubmit="showProcessingMessage()">
        <input type="file" name="file" required />
        
        <!-- Placeholder for the message -->
        <div id="uploading_msg" style="color: blue; margin-bottom: 10px;"></div>
        
        <button type="submit" id="upload_btn">upload</button>
    </form>
    
    <c:if test="${not empty successMessage}">
        <div style="color: green;">${successMessage}</div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div style="color: red;">${errorMessage}</div>
    </c:if>
</div>

<script>
    function showProcessingMessage() {
        document.getElementById("upload_btn").disabled = true;
        document.getElementById("uploading_msg").innerText = "Uploading...";
    }
</script>

 
</body>
</html>
