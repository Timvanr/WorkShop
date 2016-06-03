<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Workshop Web-App</title>
<style type="text/css">
</style>
</head>
<form action="Klant/save.html" method=post>
<table cellpadding=4 cellspacing=2 border=0>
<th bgcolor="#CCCCFF" colspan=2>
<font size=5>Klant Toevoegen</font>
<br>
<font size=1><sup>*</sup> Verplichte velden</font>
</th>
<tr bgcolor="#c8d8f8">
<td valign=top> 
<b>Voornaam<sup>*</sup></b> 
<br>
<input type="text" name="voornaam" value="" size=15 maxlength=20></td>
<td  valign=top>
<b>Achternaam<sup>*</sup></b>
<br>
<input type="text" name="achternaam" value="" size=15 maxlength=20></td>
</tr>
<tr bgcolor="#c8d8f8">
<td valign=top>
<b>E-Mail<sup>*</sup></b> 
<br>
<input type="text" name="email" value="" size=25  maxlength=125>
<br></td>
<td  valign=top>
<b>Tussenvoegsel<sup></sup></b> 
<br>
<input type="text" name="tussenvoegsel" value="" size=5  maxlength=5></td>
</tr>
<tr bgcolor="#c8d8f8">
<td valign=top colspan=2>
</td>
</tr>

<td  align=center colspan=2>
<input type="submit" value="Submit"> <input type="reset"  
value="Reset">
</td>
</tr>
</table>
</center>
</form>
<br>

<form action="Klant/findKlant">
Klant ID: <input type="text" name="id"> <input type="submit" value="Zoek Klant">
</form><br>

<table border="5" bordercolor="black">

	<tr>
		<th width="80">id</th>
		<th width="120">voornaam</th>
		<th width="80">tussenvoegsel</th>
		<th width="120">achternaam</th>
		<th width="120">email</th>
		<th width="60">edit delete</th>
	</tr>
	<c:forEach items="${klanten}" var="klant">
		<tr>
			<td width="80">${klant.id}</td>
			<td width="120">${klant.voornaam}</td>
			<td width="120">${klant.tussenvoegsel}</td>
			<td width="80">${klant.achternaam}</td>
			<td width="120">${klant.email}</td>
			<td width="60"><a
				href="${pageContext.request.contextPath}/team/edit/${team.id}.html">Edit</a><br>
				<a href="<c:url value='deleteKlant${klant.id}.html'/>">Delete</a><br>

			</td>
		</tr>
	</c:forEach>
</table>

<br>
<div
	style="font-family: verdana; padding: 10px; border-radius: 10px; font-size: 12px; text-align: center;">

	Jaa, dit zijn alle klanten uit mijn database!</div>

<a href="NewFile.html">FactuurMenu</a>
</body>
</html>