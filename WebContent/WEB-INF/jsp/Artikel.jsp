<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="utf-8" />
<title>Insert title here</title>
<style type="text/css">
</style>
</head>
<body>

<table border="5" bordercolor="black">
	<tr>
		<th width="80" >id</th>
		<th width="120">naam</th>
		<th width="120">prijs</th>
		<th width="120">omschrijving</th>
		<th width="60">edit delete</th>
	</tr>
	<c:forEach items="${artikelen}" var="artikel">
		<tr>
			<td width="80">${artikel.id}</td>
			<td width="120">${artikel.naam}</td>
			<td width="80">&euro; ${artikel.prijs}</td>
			<td width="120">${artikel.omschrijving}</td>
			<td width="60"><a
				href="${pageContext.request.contextPath}/team/edit/${team.id}.html">Edit</a><br>
				<a href="<c:url value='deleteKlant${klant.id}.html'/>">Delete</a><br>

			</td>
		</tr>
	</c:forEach>
</table>

</body>
</html>