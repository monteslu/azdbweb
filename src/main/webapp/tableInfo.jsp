<%@ include file="conn.jspf" %>
<html>
<head>
<link rel="StyleSheet" href="stylesheet.css" type="text/css" media="screen">
<title>SQL Client</title>
</head>
<body>
<BR>

<h2>Primary Key Info:</h2>



<%
	boolean hasKey = false;
	//String schema = WebUtil.getString(request,"schema");
	//String table = WebUtil.getString(request,"table");
	String schema = request.getParameter("schema");
	String table = request.getParameter("table");
	try{
		Statement state = con.createStatement();
		ResultSet rs = con.getMetaData().getBestRowIdentifier(null,schema,table,2,false);//getPrimaryKeys(null,schema,table);
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
		int rownum = 0;
		while(rs.next()){
			rownum++;
			hasKey = true;
			if(toggle){
				out.println("<tr class=\"toggleon\">");
			}
			else{
				out.println("<tr>");
			}

			for(int i = 0; i < md.getColumnCount(); i++){
				if(i == 0){
					out.println("<th><font size=\"-2\">"+rownum+"</font></th>");
				}
				out.println("<td nowrap>"+rs.getString(i+1)+"</td>");

			}
			toggle = !toggle;
			out.println("</tr>");
		}
		out.println("</table>");
		state.close();
	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}
%>
<BR>
<HR>
<h2>Column Info</h2>


<%

	try{
		Statement state = con.createStatement();
		ResultSet rs = con.getMetaData().getColumns(null,schema,table,null);
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
		int rownum = 0;
		while(rs.next()){
			rownum++;
			if(toggle){
				out.println("<tr class=\"toggleon\">");
			}
			else{
				out.println("<tr>");
			}

			for(int i = 0; i < md.getColumnCount(); i++){
				if(i == 0){
					out.println("<th><font size=\"-2\">"+rownum+"</font></th>");
				}
				out.println("<td nowrap>"+rs.getString(i+1)+"</td>");

			}
			toggle = !toggle;
			out.println("</tr>");
		}
		out.println("</table>");
		state.close();
	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}
%>

<%//if(hasKey){%>
<form method="post" action="startBeans.jsp">
<input type="Hidden" value="<%=schema%>" name="schema">
<input type="Hidden" value="<%=table%>" name="table">
<input type="Submit" value="Setup DBObject">
</form>
<%//}%>
</body>
</html>
