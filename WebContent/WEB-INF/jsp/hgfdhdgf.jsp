<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<title>Workshop Web-App</title>
<style type="text/css">
</style>
</head>
<table border="5" bordercolor="black">
	
	<tr>
		<th width="80">id</th>
		<th width="120">voornaam</th>
		<th width="80">tussenvoegsel</th>
		<th width="120">achternaam</th>
		<th width="120">email</th>
		<th width="60">edit
		delete</th>
	</tr>
	<c:forEach items="${klanten}" var="klant">
		<tr>
			<td width="80">${klant.id}</td>
			<td width="120">${klant.voornaam}</td>
			<td width="120">${klant.tussenvoegsel}</td>
			<td width="80">${klant.achternaam}</td>
			<td width="120">${klant.email}</td>
			<td width="60"><a href="${pageContext.request.contextPath}/team/edit/${team.id}.html">Edit</a><br>
				<a href="${pageContext.request.contextPath}/team/delete/${team.id}.html">Delete</a><br>
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