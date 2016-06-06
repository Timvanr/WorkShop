<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Zocht u deze soms?</title>
</head>
<body>

Uw gezochte klant:<br><br>
<table border=2 bordercolor="black" bgcolor=#CCCCFF>
<tr>
		<th width="80">id</th>
		<th width="120">voornaam</th>
		<th width="80">tussenvoegsel</th>
		<th width="120">achternaam</th>
		<th width="120">email</th>
		<th width="120">Nieuwe Bestelling</th>
		<th width="120">Bestelling bekijken</th>		
	</tr>	
	<tr>
		<td>${klant.id}</td>
		<td>${klant.voornaam }</td>
		<td>${klant.tussenvoegsel}</td>
		<td>${klant.achternaam }</td>
		<td>${klant.email }</td>
		<td width="60"><a href="maakBestelling.html">Nieuw</a><br>
		<td width="60"><a href="<c:url value='findBestelling${klant.id}.html'/>">Bekijk</a><br>		
		 </tr>
</table>

</body>
</html>