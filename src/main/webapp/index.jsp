<%--
  Created by IntelliJ IDEA.
  User: Baumbart13
  Date: 06.12.2021
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello, this is JSP</title>
    <link rel="stylesheet" href="style/default_style.css">
</head>
<body>
<h1>Welcome to your notes</h1>
<p>Already a member? <a href="Login.jsp">Login</a></p><br/>
<p>Never been here before? Register <a href="Register.jsp">here</a></p><br/>
<button type="button" onclick="ListAllEntriesButton()">
    List all entries
</button>

<br/><br/>
<div id="user_table">

</div>

<script language="JavaScript">
    function ListAllEntriesButton() {
        const x = new XMLHttpRequest();
        x.onload = function(){
            alert(this.responseText);
            var arr = JSON.parse(this.responseText);
            var html =convertUserArrayToJson(arr);
            document.getElementById("user_table").innerHTML = html;
        }
        x.open("POST", "GetAllUsersServlet", true);
        x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        x.send();
    }

    function convertUserArrayToJson(arr) {
        var s = "<table>";
        s += "<tr><th>E-Mail</th><th>Forename</th><th>Lastname</th><th>Username</th><th>Password</th><th>Birthday</th>" +
            "<th>Member since</th></tr>";
        for (i = 0; i < arr.length; ++i) {
            var o = arr[i];
            // [{"email":"asd@asd.asd","forename":"123456789","lastname":"123456789","username":"123456789"}]
            s += "<tr>";
            s += "<td>" + o.email + "</td>";
            s += "<td>" + o.forename + "</td>";
            s += "<td>" + o.lastname + "</td>";
            s += "<td>" + o.username + "</td>";
            s += "<td>" + o.password + "</td>";
            s += "<td>" + o.birthday + "</td>";
            s += "<td>" + o.join_date + "</td>";
        }
        s += "</table>";
        return s;
    }
</script>
</body>
</html>
