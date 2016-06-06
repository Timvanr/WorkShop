<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Klant menu</title>
<style type="text/css">
</style>
</head>
<body>
<h2>Welkom in het Klant Menu</h2>


<form action="Klant/NewAccount" method=post>
<table cellpadding=4 cellspacing=2 border=5>
<th bgcolor="#CCCCFF" colspan=2>
<font size=5>Klant Toevoegen</font>
<br>
<font size=1><sup>*</sup> Verplichte velden</font>
</th>
<tr>
<td valign=top> 
<b>Accountnaam<sup>*</sup></b> 
<br>
<input type="text" name="naam" value="" size=20 maxlength=25 required></td>
</tr>
<tr bgcolor="#c8d8f8">
<td valign=top> 
<b>Wachtwoord<sup>*</sup></b> 
<br>
<input type="password" name="wachtwoord" value="" size=20 maxlength=25 required></td>
</tr>
<tr bgcolor="#c8d8f8">
<td valign=top> 
<b>Voornaam<sup>*</sup></b> 
<br>
<input type="text" name="voornaam" value="" size=20 maxlength=100 required></td>
</tr>
<td  valign=top>
<b>Tussenvoegsel<sup></sup></b> 
<br>
<input type="text" name="tussenvoegsel" value="" size=10  maxlength=10></td>
</tr>
<tr bgcolor="#c8d8f8">
<td  valign=top>
<b>Achternaam<sup>*</sup></b>
<br>
<input type="text" name="achternaam" value="" size=20 maxlength=100 required></td>
</tr>
<tr>
<td valign=top>
<b>E-Mail<sup>*</sup></b> 
<br>
<input type="email" name="email" value="" size=20  maxlength=125 required placeholder="Enter a valid email address">
<br></td>

<br>
</th>
<tr bgcolor="#c8d8f8">
<td valign=top> 
<b>Straatnaam<sup>*</sup></b> 
<br>
<input type="text" name="straatnaam" value="" size=20 maxlength=100 required></td>
</tr>
<td  valign=top>
<b>Huisnummer<sup>*</sup></b>
<br>
<input type="number" name="huisnummer" value="" size=5 maxlength=5 required></td>
</tr>
<tr bgcolor="#c8d8f8">
<td  valign=top>
<b>Toevoeging<sup></sup></b> 
<br>
<input type="text" name="toevoeging" value="" size=5 maxlength=5></td>
<tr>
<td  valign=top>
<b>Postcode<sup>*</sup></b>
<br>
<input type="text" name="postcode" pattern="[1-9][0-9]{3}\s?[a-zA-Z]{2}" value="" size=20 maxlength=6 required ></td>
</tr>
<tr bgcolor="#c8d8f8">
<td valign=top>
<b>Woonplaats<sup>*</sup></b> 
<br>
<input type="text" name="woonplaats" value="" size=20  maxlength=125 required>
<br></td>
</tr>
<td  valign=top>
<b>Adrestype<sup></sup></b> 
<br>
<select>
			<option value="1">1: Bezorgadres</option>
  			<option value="2">2: Factuuradres</option>
  			<option value="3">3: Bezoekadres</option>
<select></td>
</tr>
<td  align=center colspan=2>
<input type="submit" value="Submit"> <input type="reset"  
value="Reset">
</td>
</tr>
</table>
</form>


</body>
</html>