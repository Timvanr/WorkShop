<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
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


</body>
</html>