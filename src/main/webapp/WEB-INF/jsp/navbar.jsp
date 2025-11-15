<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Navbar</title>
</head>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

body {
	margin: 0;
	font-family: "Poppins", sans-serif;
	background: #11171B;
    background: linear-gradient(90deg, rgba(17, 23, 27, 1) 0%, rgba(33, 66, 84, 1) 49%, rgba(1, 6, 10, 1) 100%);
}

.navbar {
	display: flex;
	justify-content: space-between;
	align-items: center;
	background: #ffffff36;
	padding: 20px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
	transition: all 0.3s ease;
	height: 27px;
}

.navbar-logo {
	display: flex;
	align-items: center;
}

.navbar-logo img {
	height: 40px;
	margin-right: 10px;
	transition: transform 0.3s ease;
}

.navbar-logo img:hover {
	transform: scale(1.1);
}

.brand-name {
	font-size: 20px;
	font-weight: bold;
	color: white;
	transition: color 0.3s ease;
}

.navbar-links {
	display: flex;
	align-items: center;
	color: white;
}

.navbar-links a {
	margin-left: 20px;
	text-decoration: none;
	    color: #ffffff;
	font-weight: 500;
	position: relative;
	transition: all 0.3s ease;
}

.navbar-links a::after {
	content: "";
	display: block;
	height: 2px;
	width: 0;
	background: white;
	transition: width 0.3s ease;
	position: absolute;
	bottom: -3px;
	left: 0;
}

.navbar-links a:hover {
	color: white;
}

.navbar-links a:hover::after {
	width: 100%;
}

@media ( max-width : 768px) {
	.navbar {
		flex-direction: column;
		align-items: flex-start;
	}
	.navbar-links {
		flex-direction: column;
		align-items: flex-start;
		width: 100%;
		margin-top: 10px;
	}
	.navbar-links a {
		margin: 8px 0;
	}
}
</style>


<body>

	<div class="navbar">
		<div class="navbar-logo">
			<a href="${pageContext.request.contextPath}/"> <img
				src="${pageContext.request.contextPath}/images/todologo.png"
				alt="Logo">
			</a> <span class="brand-name">ToDo App</span>
		</div>
		<div class="navbar-links">
			<a href="${pageContext.request.contextPath}/">Home</a> 
			<%-- 
			   <a href="${pageContext.request.contextPath}/task">ToDo List</a> 
			   <a href="${pageContext.request.contextPath}/AddEmployeeDetail">Employee</a>  
			--%>
			<%-- <a href="${pageContext.request.contextPath}/textEditor">Editor</a>  --%>
			
			 <a href="${pageContext.request.contextPath}/faceDetect">Face Detection</a> 
			<%-- <a href="${pageContext.request.contextPath}/face">Face</a> --%>
			<%-- <a href="${pageContext.request.contextPath}/upload">Upload Imgs</a> --%>
		</div>
	</div>
</body>
</html>