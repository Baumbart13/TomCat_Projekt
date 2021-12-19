<%--
  Created by IntelliJ IDEA.
  User: Baumbart13
  Date: 06.12.2021
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    var errorMessage = (String)request.getAttribute("error");
%>
<html>
<head>
    <title>My computer haz 3 virus</title>
    <style>
        .error{
            color:red;
        }
    </style>
</head>
<body>
<h1>Register</h1>
<%
    if(errorMessage != null){
        out.append("<p class=\"error\">"+errorMessage+"</p>");
    }
%>
<form action="<RegisterServlet>" method="post">
    <label>E-Mail:</label>
    <input type="email" name="usermail" value="" placeholder="eminem@marshall.matters"/>
    <br/>
    <label>Firstname:</label>
    <input type="text" name="firstname" value="" placeholder="Max"/>
    <br/>
    <label>Lastname:</label>
    <input type="text" name="lastname" value="" placeholder="Kellerkind"/>
    <br/>
    <label>Username:</label>
    <input type="text" name="username" value="" placeholder="Baumkind13"/>
    <br/>
    <label>Password:</label>
    <input type="password" name="userpass" value="" placeholder="Haley"/>
    <br/>
    <input type="submit" value="Register Me!">
</form>
</body>
</html>
