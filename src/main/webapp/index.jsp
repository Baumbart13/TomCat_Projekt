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
</head>
<body>
<h1>Your computer haz virus</h1>
<p>Already a member? <a href="Login.jsp">Login</a></p><br/>
<p>Never been here before? Register <a href="Register.jsp">here</a></p>
<button type="button" onclick="ListAllEntriesButton()">
    List all entries
</button>

<br/><br/>
<div id="user_table">

</div>

<script language="JavaScript">
    function ListAllEntriesButton() {
        const x = new XMLHttpRequest();
        x.onload = function () {
            alert(this.responseText);
            var arr = JSON.parse(this.responseText);
            var html = (arr) => {
                var s = "<table>";
                for (i = 0; i < arr.length; ++i) {
                    var o = arr[i];
                    s += "<tr>";
                    s += "<td>" + o.email + "</td>";
                    s += "<td>" + o.foreName + "</td>";
                    s += "<td>" + o.lastName + "</td>";
                    s += "<td>" + o.username + "</td>";
                    s += "<td>" + o.password + "</td>";
                    s += "<td>" + o.birthday + "</td>";
                    s += "<td>" + o.join_date + "</td>";
                }
                s += "</table>";
                return s;
            }
            document.getElementById("user_table").innerHTML = html;
        }
        x.open("POST", "GetAllUsersServlet", true);
        x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        x.send();
    }
</script>
</body>
</html>
