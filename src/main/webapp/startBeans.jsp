<%@ include file="conn.jspf" %>
<%@ page import="java.util.*" %>
<%@ page import="com.azprogrammer.mappings.*" %>
<%@page import="com.azprogrammer.db.web.WebUtil"%>
<html>
<head>
<link rel="StyleSheet" href="stylesheet.css" type="text/css" media="screen">
<title>SQL Client</title>
<script language="JavaScript">

			function confirmCriteria()
			{
					var keySelected = false;
					for (i = 0; i < document.beanForm.keyBox.length; i++){
						if(document.beanForm.keyBox[i].checked){
							keySelected = true;
							document.beanForm.keyCol[i].value = 'on';
						}
						else{
							document.beanForm.keyCol[i].value = 'off';
						}
					}
					for (i = 0; i < document.beanForm.useBox.length; i++){
						if(document.beanForm.useBox[i].checked){
							document.beanForm.useFlag[i].value = 'on';
						}
						else{
							document.beanForm.useFlag[i].value = 'off';
						}
					}

                    for (i = 0; i < document.beanForm.aiBox.length; i++){
						if(document.beanForm.aiBox[i].checked){
							document.beanForm.aiFlag[i].value = 'on';
						}
						else{
							document.beanForm.aiFlag[i].value = 'off';
						}
					}

                    for (i = 0; i < document.beanForm.iuBox.length; i++){
						if(document.beanForm.iuBox[i].checked){
							document.beanForm.iuFlag[i].value = 'on';
						}
						else{
							document.beanForm.iuFlag[i].value = 'off';
						}
					}
					
					if(!keySelected){
						alert('You must select at least one primary key field.');
						return false;
					}
					else{


						return true;
					}

			}

</script>


</head>
<body>
<BR>


<%
	HashMap keyColumns = new HashMap();
	//String schema = WebUtil.getString(request,"schema");
	//String table = WebUtil.getString(request,"table");
	String schema = request.getParameter("schema");
	String table = request.getParameter("table");
	try{
		Statement state = con.createStatement();
		ResultSet rs = con.getMetaData().getBestRowIdentifier(null,schema,table,2,false);
		while(rs.next()){
				keyColumns.put(rs.getString("COLUMN_NAME"),"yes");
		}
		state.close();
	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}
%>
<BR>
<HR>
<h2>Bean Info:</h2>
<form method="post" action="createJava.jsp" name="beanForm" onsubmit="return confirmCriteria()">
Bean Name: <input type="Text" name="beanName" value="<%=BeanUtil.nameFromTableName(table)%>">
package: <input type="Text" name="packageName" size="35" value="<%=("com.azprogrammer.")%>"><BR>
Table Name:<input type="text" value="<%=table%>" name="tableName"><BR>
View Name:<input type="text" value="<%=table%>" name="viewName"><BR>
Sequence Name(optional):<input type="Text" name="sequenceName" size="30"><input type=button onClick='ifield = document.beanForm.sequenceName; chooser = window.open("lookupSequence.jsp?schema=<%=schema%>", "chooser", "toolbar=no,menubar=no,scrollbars=yes,width=300,height=200"); chooser.ifield = ifield' value="...">
<input type="Hidden" value="<%=schema%>" name="schemaName">

<hr>
<h2>Properties:</h2>
<table>
<tr>
<td></td>
<th>Key Field?</th>
<th>Use?</th>
<th>auto increment?</th>
<th>read only?</th>
<th>COLUMN_NAME</th>
<th>Property Name</th>
<th>Column constant</th>
<th>Java Type</th>
<th>Data Type</th>
<th>Dec Digits</th>
<th>Query field</th>
<th>Query Type</th>
<%

	try{
		Statement state = con.createStatement();
		ResultSet rs = con.getMetaData().getColumns(null,schema,table,null);
		ResultSetMetaData md = rs.getMetaData();
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
			out.println("<th><font size=\"-2\">"+rownum+"</font></th>");

			String colName = rs.getString("COLUMN_NAME");
                        String comments = WebUtil.clearNull(rs.getString("REMARKS")).toUpperCase();
                        String isAI = "";
                        try{
                        	isAI = WebUtil.clearNull(rs.getString("IS_AUTOINCREMENT")).toUpperCase();
                        }catch(Exception aiE){
                            System.out.println(aiE.getMessage());
                        }
                        
                        System.out.println("comments:" + comments + " isAI:" + isAI);
                     
                        String aiChecked = "";
                        if((comments.lastIndexOf("AUTO_INCREMENT") > -1) || (isAI.lastIndexOf("YES") > -1)){
                          aiChecked = "checked";
                        }

			String javaType = BeanUtil.getJavaType(rs.getInt("DATA_TYPE"),rs.getString("DECIMAL_DIGITS"));
			boolean keyCol = "yes".equals(keyColumns.get(colName));
			String checked = "";
			if(keyCol){
				checked = "checked";
			}
			%>
			<td><input type="Checkbox" <%= checked %> name="keyBox"></td>
			<input type="Hidden" name="keyCol" value="<%= checked %>">
			<td><input type="Checkbox" checked name="useBox"></td>
			<input type="Hidden" name="useFlag" value="on">
            <td><input type="Checkbox" name="aiBox" <%= aiChecked %>></td>
			<input type="Hidden" name="aiFlag" value="off">
			<td><input type="Checkbox" name="iuBox"></td>
			<input type="Hidden" name="iuFlag" value="off">
			<td><%= colName %></td>
			<input type="Hidden" name="colName" value="<%=colName%>">
			<td><input type="Text" name="propName" value="<%=BeanUtil.propNameFromCol(colName)%>"></td>
			<td><input type="Text" name="colVariable" value="COLUMN_<%=colName.toUpperCase()%>" size="30"></td>
			<td>
			<select name="javaType" size="1">
				<option value="String" <%if("String".equals(javaType)){out.print("selected");}%>>String</option>
				<option value="int" <%if("int".equals(javaType)){out.print("selected");}%>>int</option>
				<option value="long" <%if("long".equals(javaType)){out.print("selected");}%>>long</option>
				<option value="double" <%if("double".equals(javaType)){out.print("selected");}%>>double</option>
				<option value="java.util.Date" <%if("java.util.Date".equals(javaType)){out.print("selected");}%>>Date</option>
				<option value="boolean" <%if("boolean".equals(javaType)){out.print("selected");}%>>boolean</option>
                                <option value="java.sql.Timestamp" <%if("java.sql.Timestamp".equals(javaType)){out.print("selected");}%>>Timestamp</option>
			</select></td>

			<td><%=rs.getString("TYPE_NAME")%></td>
			<td><%=rs.getString("DECIMAL_DIGITS")%></td>
			<td>
			<select name="queryField">
				<option value="N" selected>No</option>
				<option value="Y">Yes</option>
			</select>
			</td>
			<td>
			<select name="queryType">
				<option value="TYPE_EQUAL">Equal</option>
				<option value="TYPE_INEQUAL">Not Equal</option>
				<option value="TYPE_GREATER_THAN">Greater Than</option>
				<option value="TYPE_LESS_THAN">Less Than</option>
				<option value="TYPE_GREATER_EQUAL">Greater or Equal</option>
				<option value="TYPE_LESS_EQUAL">Less or Equal</option>
				<option value="TYPE_LIKE_CASE_SENSITIVE">Like Case Sensitive</option>
				<option value="TYPE_LIKE">Like NOT Case Sensitive</option>
				<option value="TYPE_IGNORE_CASE_EQUAL">Eqaul Ignore Case</option>
			</select>
			</td>
			<%
			toggle = !toggle;
			out.println("</tr>");
		}
		state.close();
	}
	catch(Exception e){
		out.println("<b>Error retrieving results:</b><br>"+e.toString());
	}
%>
</table>
<input type="Submit" value="Create Java Source">
</form>

</body>
</html>
