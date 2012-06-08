<%@ include file="conn.jspf" %>
<%@page import="com.azprogrammer.db.web.WebUtil"%>
<%

int MAX_ROW_COUNT = 100;
%>

<html>
<head>
<link rel="StyleSheet" href="stylesheet.css" type="text/css" media="screen">
<title>SQL Client</title>
</head>
<body>
At most, this tool will retrieve <%=MAX_ROW_COUNT%> rows per query.
<HR><BR>
<%
String selectSQL = WebUtil.getString(request,"selectSQL");
if("".equals(selectSQL)){
	selectSQL = "SELECT SYSDATE FROM DUAL";
}
%>
<form method="post">
Statement:<BR>
<textarea cols="80" rows="12" name="selectSQL"><%= selectSQL %></textarea><BR>
<input type="Hidden" name="cmd" value="fromQuery"><input type="Submit" value="execute">
</form>

<%
if("fromQuery".equals(request.getParameter("cmd"))){
	Statement state = null;
	try{
		state = con.createStatement();
		ResultSet rs = state.executeQuery(selectSQL);
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
			if(rowNum == (MAX_ROW_COUNT + 1)){
				break;
			}
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
		state.close();
	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
		try{
			state.close();
		}
		catch(Exception closeStateExp){
		}
	}




}
%>
</body>
</html>
