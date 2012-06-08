<%@ include file="conn.jspf" %>
<%@ page import="java.util.*" %>
<%@page import="java.sql.*"%>
<html>
<head>
<link rel="StyleSheet" href="stylesheet.css" type="text/css" media="screen">
<title>SQL Client</title>
</head>
<body>

<a href="index.jsp"><img src="images/up.gif" alt="Back To Main Menu"></a>
<BR>
<%

	try{
		DatabaseMetaData dbmd= con.getMetaData();
		ResultSet rs = null;
                if("schema".equals(request.getParameter("term"))){
                  rs = dbmd.getSchemas();
                }else{
                  rs = dbmd.getCatalogs();
                }
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
			String schema = rs.getString(1);
			%>
			<th><font size="-2"><%=rowNum%></font></th><td><a href="schemaInfo.jsp?schema=<%=schema%>"><%=schema%></a></td></tr>
			<%
			toggle = !toggle;
		}
		out.println("</table>");
	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}

%>
</body>
</html>
