<%@ include file="conn.jspf" %>
<%@page import="com.azprogrammer.db.web.WebUtil"%>
<html>
<head>
<link rel="StyleSheet" href="stylesheet.css" type="text/css" media="screen">
<title>SQL Client</title>

<script language="JavaScript">
function select(f)
{
ifield.value = f;
top.close();
return false;
}
</script>
</head>
<body>
<BR>

<%
String schema = WebUtil.getString(request,"schema");

if(!"".equals(schema)){


%>
<h2> <%=schema%> schema</h2>
<hr>
<%


	try{
		DatabaseMetaData dbmd= con.getMetaData();
		ResultSet rs = dbmd.getTables(null,schema,"%",new String[]{"SEQUENCE"});
		%>
		<b>Sequences:</b>
		<table><tr><td></td>
		<th>NAME</th></tr>
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
			out.println("<td nowrap><a href=\"\" onClick='return select(\"" + rs.getString("TABLE_NAME")  +"\")'>"+rs.getString("TABLE_NAME")+"</a></td>");
			toggle = !toggle;
			out.println("</tr>");
		}
		out.println("</table>");

	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}

	
	try{
		DatabaseMetaData dbmd= con.getMetaData();
		ResultSet rs = dbmd.getTables(null,schema,"%",new String[]{"TABLE"});
		%>
		<b>Tables:</b>
		<table><tr><td></td>
		<th>NAME</th></tr>
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
			out.println("<td nowrap><a href=\"\" onClick='return select(\"" + rs.getString("TABLE_NAME")  +"\")'>"+rs.getString("TABLE_NAME")+"</a></td>");
			toggle = !toggle;
			out.println("</tr>");
		}
		out.println("</table>");

	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}



}
%>
</body>
</html>
