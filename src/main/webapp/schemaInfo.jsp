<%@ include file="conn.jspf" %>
<%@ page import="java.util.*" %>

<html>
<head>
<link rel="StyleSheet" href="stylesheet.css" type="text/css" media="screen">
<title>SQL Client</title>
</head>
<body>
<a href="schemas.jsp"><img src="images/up.gif" alt="Back To Main Menu"></a>
<BR>

<%
String schema = request.getParameter("schema");

if((schema != null) && (!"".equals(schema))){


%>
<h2> <%=schema%> schema</h2>
			<a href="schemaInfo.jsp?schema=<%=schema%>&cmd=tables"><img src="images/table.gif" alt="Tables"></a>
			<a href="schemaInfo.jsp?schema=<%=schema%>&cmd=views"><img src="images/view.gif" alt="Views"></a>
			<a href="schemaInfo.jsp?schema=<%=schema%>&cmd=synonyms"><img src="images/synonym.gif" alt="Synonyms"></a>
			<a href="schemaInfo.jsp?schema=<%=schema%>&cmd=sequences"><img src="images/sequence.gif" alt="Sequences"></a>
			<a href="schemaInfo.jsp?schema=<%=schema%>&cmd=procedures"><img src="images/procedure.gif" alt="Procedures"></a>
			<hr>
<%
if("tables".equals(request.getParameter("cmd"))){
	try{
		DatabaseMetaData dbmd= con.getMetaData();
		ResultSet rs = dbmd.getTables(null,schema,"%",new String[]{"TABLE"});
		%>
		<b>Tables:</b>
		<table><tr><td></td>
		<th>NAME</th><th></th></tr>
		<%
		//rows
		boolean toggle = false;
		int rowNum = 0;
		while(rs.next()){
			rowNum++;
			if(toggle){
				out.println("<tr class=\"toggleon\">");
			}
			else{
				out.println("<tr>");
			}
			%>
			<th><font size="-2"><%=rowNum%></font></th>
			<td nowrap><a href="tableInfo.jsp?schema=<%=schema%>&table=<%=rs.getString("TABLE_NAME")%>"><%=rs.getString("TABLE_NAME")%></a></td>
			<td><form method="post" action="query.jsp"><input type="Submit" value="query"></td>
			<input type="Hidden" name="cmd" value="fromQuery">
			<input type="Hidden" name="selectSQL" value="Select * from <%=(schema + "." + rs.getString("TABLE_NAME"))%>">
			</form>
			</tr>
			<%
			toggle = !toggle;
		}
		out.println("</table>");

	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}
}
else if("views".equals(request.getParameter("cmd"))){
	try{
		DatabaseMetaData dbmd= con.getMetaData();
		ResultSet rs = dbmd.getTables(null,schema,"%",new String[]{"VIEW"});
		%>
		<b>Views:</b>
		<table><tr><td></td>
		<th>NAME</th><th></th></tr>
		<%
		//rows
		boolean toggle = false;
		int rowNum = 0;
		while(rs.next()){
			rowNum++;
			if(toggle){
				out.println("<tr class=\"toggleon\">");
			}
			else{
				out.println("<tr>");
			}
			%>
			<th><font size="-2"><%=rowNum%></font></th>
			<td nowrap><a href="tableInfo.jsp?schema=<%=schema%>&table=<%=rs.getString("TABLE_NAME")%>"><%=rs.getString("TABLE_NAME")%></a></td>
			<form method="post" action="query.jsp"><td><input type="Submit" value="query"></td>
			<input type="Hidden" name="cmd" value="fromQuery">
			<input type="Hidden" name="selectSQL" value="Select * from <%=(schema + "." + rs.getString("TABLE_NAME"))%>">
			</form>
			</tr>
			<%
			toggle = !toggle;
		}
		out.println("</table>");

	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}
}
else if("synonyms".equals(request.getParameter("cmd"))){
	try{
		DatabaseMetaData dbmd= con.getMetaData();
		ResultSet rs = dbmd.getTables(null,schema,"%",new String[]{"SYNONYM"});
		%>
		<b>Synonyms:</b>
		<table><tr><td></td>
		<th>NAME</th><th></th></tr>
		<%
		//rows
		boolean toggle = false;
		int rowNum = 0;
		while(rs.next()){
			rowNum++;
			if(toggle){
				out.println("<tr class=\"toggleon\">");
			}
			else{
				out.println("<tr>");
			}
			%>
			<th><font size="-2"><%=rowNum%></font></th>
			<td nowrap><%=rs.getString("TABLE_NAME")%></td>
			<form method="post" action="query.jsp"><td><input type="Submit" value="query"></td>
			<input type="Hidden" name="cmd" value="fromQuery">
			<input type="Hidden" name="selectSQL" value="Select * from <%=(schema + "." + rs.getString("TABLE_NAME"))%>">
			</form>
			</tr>
			<%
			toggle = !toggle;
		}
		out.println("</table>");

	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}
}
else if("sequences".equals(request.getParameter("cmd"))){
	try{
		DatabaseMetaData dbmd= con.getMetaData();
		ResultSet rs = dbmd.getTables(null,schema,"%",new String[]{"SEQUENCE"});
		%>
		<b>Sequences:</b>
		<table><tr><td></td>
		<th>NAME</th><th></th></tr>
		<%
		//rows
		boolean toggle = false;
		int rowNum = 0;
		while(rs.next()){
			rowNum++;
			if(toggle){
				out.println("<tr class=\"toggleon\">");
			}
			else{
				out.println("<tr>");
			}
			out.println("<th><font size=\"-2\">"+rowNum+"</font></th>");
			out.println("<td nowrap>"+rs.getString("TABLE_NAME")+"</td>");
			toggle = !toggle;
			out.println("</tr>");
		}
		out.println("</table>");

	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}
}
else if("procedures".equals(request.getParameter("cmd"))){

	try{
		DatabaseMetaData dbmd= con.getMetaData();
		ResultSet rs = dbmd.getProcedures(null,schema,"%");
		ResultSetMetaData md = rs.getMetaData();
		out.println("<table>");
		out.println("<tr><td></td>");

		//column names
		for(int i = 0; i < md.getColumnCount(); i++){
			out.println("<th>"+md.getColumnName(i+1)+"</th>");
		}
		out.println("</tr>");

		//rows
		boolean toggle = false;
		int rowNum = 0;
		while(rs.next()){
			rowNum++;
			if(toggle){
				out.println("<tr class=\"toggleon\">");
			}
			else{
				out.println("<tr>");
			}
			for(int i = 0; i < md.getColumnCount(); i++){
				if(i == 0){
					out.println("<th><font size=\"-2\">"+rowNum+"</font></th>");
				}
				out.println("<td nowrap>"+rs.getString(i+1)+"</td>");

			}
			toggle = !toggle;
			out.println("</tr>");
		}
		out.println("</table>");

	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}
}


}
%>
</body>
</html>
