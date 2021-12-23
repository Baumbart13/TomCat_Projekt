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
    <link rel="stylesheet" href="style/default_style.css">
</head>
<body>
    <h1>You are now in the final screen where are all your notes</h1>
    <p><a href="https://youtu.be/0QEXdLCu1Kk?t=30">Noice</a></p>
    <form action="AppendToNotesServlet">
        <label>New note</label>
        <input type="text" name="new_note"><br>
        <input type="submit" value="note_added">
    </form>
    <button type="button" onclick="ListAllEntriesButton()">
        List all entries
    </button>
    <div id="notes_table">

    </div>
<script>
    function ListAllEntriesButton(){
        const x = new XMLHttpRequest();
        x.onload = function(){
            alert(this.responseText);
            var arr = JSON.parse(this.responseText);
            var html = convertNotesArrayToHtml(arr, true);
            document.getElementById("notes_table").innerHTML = html;
        }
        x.open("POST", "GetAllNotesServlet", true);
        x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        x.send();
    }
    function RequestUserChat() {
        const x = new XMLHttpRequest();
        x.onload = function(){
            alert(this.responseText);
            var arr = JSON.parse(this.responseText);
            var html = convertNotesArrayToHtml(arr);
            document.getElementById("notes_table").innerHTML = html;
        }
        x.open("POST", "GetAllNotesServlet", true);
        x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        x.send();
    }
    function convertNotesArrayToHtml(arr, debug = false){
        var s = "<table>";
        for(i = 0; i < arr.length; ++i){
            var o = arr[i];
            s += "<tr>";
            if(debug){
                s+="<td>" + o.user + "</td>";
            }
            s += "<td>" + o.note_index + "</td>";
            s += "<td>" + o.note_message + "</td>";
            s += "</td>";
        }
        s += "</table>";
        return s;
    }
</script>
</body>
</html>
