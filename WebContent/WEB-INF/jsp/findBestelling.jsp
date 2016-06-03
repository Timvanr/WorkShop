<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<table border="5" bordercolor="black">
	<tr>
		<th width="80" >id</th>
		<th width="120">klant_id</th>
		<th width="120">prijs</th>
		<th width="60">Bekijk Bestelling</th>
	</tr>
	<c:forEach items="${bestelling}" var="bestelling">
		<tr>
			<td width="80">${bestelling.id}</td>
			<td width="120">${bestelling.klant_id}</td>
			<td width="80">${bestelling.datum}</td>
			<td width="60"><a href="<c:url value='findArtikeleninBestelling${bestelling.id}.html'/>">Bekijk</a><br>
			</td>
		</tr>
	</c:forEach>
</table>
<br>
<table border="5" bordercolor="black">
	<tr>
		<th width="500" >Artikel</th>
		<th width="60">aantal</th>
		<th width="60">Bekijk Bestelling</th>
	</tr>
	<c:forEach items="${artikelen}" var="artikel">
		<tr>
			<td width="500">${artikel.key}</td>
			<td width="60">${artikel.value}</td>
			<td width="60"><a href="<c:url value='findArtikeleninBestelling${bestelling.id}.html'/>">Bekijk</a><br>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>