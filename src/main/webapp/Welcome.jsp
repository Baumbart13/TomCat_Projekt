<%--
  Created by IntelliJ IDEA.
  User: Baumbart13
  Date: 19.12.2021
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welkom Viruz</title>
</head>
<body>
    <h1>You are now in the final screen where are all your notes</h1>
    <p><a href="https://youtu.be/0QEXdLCu1Kk?t=30">Noice</a></p>
    <form action="AppendToNotesServlet">
        <label>New note</label>
        <input type="text" name="new_note"><br>
        <input type="submit" value="note_added">
    </form>
<script>
    function RequestUserChat(arr) {
        const x = new XMLHttpRequest();
        x.onload = function(){
            alert(this.responseText);
            var arr = JSON.parse(this.responseText);
            var html = (arr) => {
                var s = "<table>";
                for(i = 0; i < arr.length; ++i){
                    var o = arr[i];
                    s += "<tr>";
                    s += "<td>" + o.note_timestamp + "</td>";
                    s += "<td>" + o.note_message + "</td>";
                }
                s += "</table>";
                return s;
            }
        }
    }
</script>
</body>
</html>
