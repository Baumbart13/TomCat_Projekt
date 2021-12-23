<%--
  Created by IntelliJ IDEA.
  User: Baumbart13
  Date: 06.12.2021
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>Systemtest.
<html>
<head>
    <link rel="stylesheet" href="style/default_style.css">
</head>
<body>
    <h1>Anmelden</h1>
    <%
        String errorMessage = (String)request.getAttribute("error");
        if(errorMessage != null){
            out.append("<p class=\"error\">"+errorMessage+"</p>");
        }
    %>
    <form action="LoginServlet" method="post">
        <label>User:</label>
        <input type="text" name="usermail" value="" placeholder="eminem@marshall.matters"/>
        <br/>
        <label>Password:</label>
        <input type="password" name="userpass" value="" placeholder="Haley"/>
        <br/>
        <input type="submit" value="Log in">
    </form>
    <h3>Not signed up yet? Just click <a href="Register.jsp">here.</a></h3>
    <h3>Back to <a href="index.jsp">index</a> site.</h3>
</body>
</html>
