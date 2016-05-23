<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>



<form method="POST" action='KlantController' name="frmAddUser">

<table class="formtable">
<tr><td class="label">Voornaam: </td><td><input type="text" name="Voornaam"/><br/></td></tr>
<tr><td class="label">Tussenvoegsel: </td><td><input type="text" name="Tussenvoegsel"/><br/></td></tr>
<tr><td class="label">Achternaam: </td><td><input type="text" name="Achternaam"/><br/></td></tr>
<tr><td class="label">Emailadres: </td><td><input type="text" name="Emailadres"/><br/></td></tr>
<tr><td class="label"> </td><td><input class="control"  value="Maak nieuwe klant aan" type="submit" /></td></tr>
<td><p><a href="KlantController?action=saveKlant">Voeg Klant Toe</a></p></td>
</table>

</form>

</body>
</html>