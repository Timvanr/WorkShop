<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Workshop Web-App</title>
</head>
<body>
${accBericht}
<form action="findKlant" method="GET">
Account Naam: <input type="text" name="naam" required> 
Wachtwoord: <input type ="password" name="wachtwoord" required>
<input type="submit" value="Zoek Klant">
</form>
<br>

</body>
</html>