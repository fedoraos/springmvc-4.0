<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="resource/css/bootstrap.min.css">
<title>Welcome</title>
</head>
<body>
<c:forEach var="user" items="${users}">
    <span>username:</span> ${user.userName}<br/>

</c:forEach>
het
dfsdfsdfsdfd
</body>
</html>


