<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/org/resource/css/bootstrap.min.css">
<title>Welcome</title>
<style>
 .container {
 	margin-top: 20px;
 }
 .jumbotron {
 	background: pink;
 }
</style>

</head>


	<section class ="container">
		<div class="jumbotron">
			<h1>
				Products
			</h1>
			<p>${url}</p>
			<spring:message code="${exception}"/>1
		</div>
		<div class="container">
			<p>
				<a href="<spring:url value="/products" />" class="btn btn-primary">
					<span class="glyphicon-hand-left glyphicon"></span> products
				</a>
			</p>
		</div>
		
	</section>
</html>
