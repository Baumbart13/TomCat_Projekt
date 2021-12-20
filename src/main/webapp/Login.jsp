<%--
  Created by IntelliJ IDEA.
  User: Baumbart13
  Date: 06.12.2021
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>Systemtest.
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
        <input type="email" name="usermail" value="" placeholder="eminem@marshall.matters"/>
        <br/>
        <label>Password:</label>
        <input type="password" name="userpass" value="" placeholder="Haley"/>
        <br/>
        <input type="submit" value="Log in">
    </form>
</body>
</html>
