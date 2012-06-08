<%@ include file="../conn.jspf" %>
<html>
<head>
<link rel="StyleSheet" href="../stylesheet.css" type="text/css" media="screen">
<title>SQL Client</title>
</head>
<body>
<BR>
<%


	//Statement state = null;
	try{
		DatabaseMetaData dbmd= con.getMetaData();
		ResultSet rs = dbmd.getSchemas();
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
	

%>
</body>
</html>
