<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<head>
<title>Workshop Web-App</title>
<style type="text/css">
</style>
</head>
<h1>add Klant</h1>
<c:url var="addKlant" value="/addKlant"></c:url>
<form:form action="${addKlant}" commandName="Klant">
	<table>
		<tr>
			<td><form:label path="voornaam">
					<spring:message text="Voornaam" />
				</form:label></td>
			<td><form:input path="klant.voornaam" readonly="true" size="25"
					disabled="true" /> <form:hidden path="voornaam" /></td>
		</tr>
		<tr>
			<td><form:label path="tussenvoegsel">
					<spring:message text="tussenvoegsel" />
				</form:label></td>
			<td><form:input path="klant.tussenvoegsel" /></td>
		</tr>
		<tr>
			<td><form:label path="achternaam">
					<spring:message text="Achternaam" />
				</form:label></td>
			<td><form:input path="klant.achternaam" /></td>
		</tr>
		<tr>
		<tr>
			<td><form:label path="email">
					<spring:message text="Email" />
				</form:label></td>
			<td><form:input path="klant.email" /></td>
		</tr>
		<tr>

			<td colspan="2"><input type="submit"
				value="<spring:message text="Add Person"/>" /></td>
		</tr>
	</table>
</form:form>
<br>
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
</body>
</html>