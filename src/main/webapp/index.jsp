<%@ include file="conn.jspf" %>

<html>
<head>

<title>SQL Client</title>
<link rel="StyleSheet" href="stylsheet.css" type="text/css" media="screen">
</head>
<body>
<%
try{
	DatabaseMetaData md = con.getMetaData();
	out.println("Database URL :" + md.getURL() + "<BR>");
	out.println("Database Name :" + md.getDatabaseProductName() + "<BR>");
	out.println("Version :" + md.getDatabaseProductVersion() + "<BR>");
	out.println("Driver Version :" + md.getDriverVersion() + "<BR>");
}
catch(Exception e){
}
%>
<BR><BR>
<a href="schemas.jsp?term=schema">Schemas</a> / <a href="schemas.jsp?term=catalog">Catalogs</a>
<BR><BR>
<a href="query.jsp">Query</a>
<BR><BR>
<a href="update.jsp">Update</a>


</body>
</html>
