<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adres Menu</title>
</head>
<body>
<h2>Welkom in het Adres Menu</h2>

<form action="Adres/save.html" method=post>
<table cellpadding=4 cellspacing=2 border=0>
<th bgcolor="#CCCCFF" colspan=2>
<font size=5>Adres Toevoegen</font>
<br>
<font size=1><sup>*</sup> Verplichte velden</font>
</th>
<tr bgcolor="#c8d8f8">
<td valign=top> 
<b>Straatnaam<sup>*</sup></b> 
<br>
<input type="text" name="straatnaam" value="" size=15 maxlength=20></td>
<td  valign=top>
<b>Huisnummer<sup>*</sup></b>
<br>
<input type="text" name="huisnummer" value="" size=15 maxlength=20></td>
</tr>
<tr bgcolor="#c8d8f8">
<td  valign=top>
<b>toevoeging<sup></sup></b> 
<br>
<input type="text" name="toevoeging" value="" size=15 maxlength=20></td>
<td  valign=top>
<b>Postcode<sup>*</sup></b>
<br>
<input type="text" name="postcode" value="" size=15 maxlength=20></td>
</tr>
<tr bgcolor="#c8d8f8">
<td valign=top>
<b>Woonplaats<sup>*</sup></b> 
<br>
<input type="text" name="woonplaats" value="" size=25  maxlength=125>
<br></td>
<td>
<input type="submit" value="Submit"> <input type="reset" value="Reset">
</td>
</tr>
</table>
</form>
<br>
<table border="5" bordercolor="black">
	<tr>
		<th width="80">id</th>
		<th width="120">straatnaam</th>
		<th width="80">huisnummer</th>
		<th width="120">toevoeging</th>
		<th width="120">postcode</th>
		<th width="120">woonplaats</th>
		<th width="60">edit delete</th>
	</tr>
	<c:forEach items="${adressen}" var="adres">
		<tr>
			<td width="80">${adres.id}</td>
			<td width="120">${adres.straatnaam}</td>
			<td width="120">${adres.huisnummer}</td>
			<td width="80">${adres.toevoeging}</td>
			<td width="120">${adres.postcode}</td>
			<td width="120">${adres.woonplaats}</td>
			<td width="60"><a
				href="${pageContext.request.contextPath}/team/edit/${team.id}.html">Edit</a><br>
				<a href="<c:url value='deleteAdres${adres.id}.html'/>">Delete</a><br>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>