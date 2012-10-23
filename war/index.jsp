<!doctype html>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ taglib
	prefix="fn"
	uri="http://java.sun.com/jsp/jstl/functions"
%>

<html>
<head>
<meta
	http-equiv="content-type"
	content="text/html; charset=UTF-8"
>

<link
	type="text/css"
	rel="stylesheet"
	href="Mipymee.css"
>


<title>Web Application Starter Project</title>

<script
	type="text/javascript"
	language="javascript"
	src="mipymee/mipymee.nocache.js"
></script>

</head>


<body>
	<%
		String urlDevMode = "?gwt.codesvr=127.0.0.1:9997";
		//String urlDevMode="";

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			//pageContext.setAttribute("user", user);
			if(userService.isUserAdmin()){
				response.sendRedirect("Mipymee.html" + urlDevMode);
			}else{
				response.sendRedirect("Mipymee.html" + urlDevMode);
			}
		}
	%>


	<!-- OPTIONAL: include this if you want history support -->
	<iframe
		src="javascript:''"
		id="__gwt_historyFrame"
		tabIndex='-1'
		style="position: absolute; width: 0; height: 0; border: 0"
	></iframe>

	<!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
	<noscript>
		<div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">Your web browser must have JavaScript enabled in order for this application to display correctly.</div>
	</noscript>

</body>
</html>
