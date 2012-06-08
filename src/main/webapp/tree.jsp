<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ include file="conn.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script language="JavaScript" src="scripts/ajax.jsp?name=Expand"></script>
<title>tree</title>

<script language="javascript">

	function expandNode(nodeStr,schemaName,nType,sType,depth){
		var toExpand = document.getElementById(nodeStr);
		if(document.getElementById(nodeStr)){
			if(document.getElementById("openclose_" + nodeStr)){
				var newImageText = "<a href=\"javascript:void(0)\" onclick=\"collapseNode('" + nodeStr +"','" + schemaName + "','" + nType + "','" + sType + "','" + depth + "');\"><img border=\"0\" src=\"images/minus.gif\" align=\"left\"></a>";
				document.getElementById("openclose_" + nodeStr).innerHTML = newImageText;
			}
			document.getElementById(nodeStr).innerHTML = "retrieving...";
		}
		xmlRequestExpand("expandNodeXML.jsp?node=" + nodeStr + "&schema=" + schemaName + "&nType=" + nType + "&sType=" + sType + "&depth=" + depth);
	}
	
	function collapseNode(nodeStr,schemaName,nType,sType,depth){
		
		if(document.getElementById(nodeStr)){
			if(document.getElementById("openclose_" + nodeStr)){
				var newImageText = "<a href=\"javascript:void(0)\" onclick=\"expandNode('" + nodeStr +"','" + schemaName + "','" + nType + "','" + sType + "','" + depth + "');\"><img border=\"0\" src=\"images/plus.gif\" align=\"left\"></a>";
				document.getElementById("openclose_" + nodeStr).innerHTML = newImageText;
			}
			document.getElementById(nodeStr).innerHTML = "";
		}
	}
	
	function handleResponseExpand(){
		response = xmlHttpRequestExpand.responseXML.documentElement;
		var callingNode = response.getElementsByTagName('callingNode')[0].firstChild.data;
		var callingNodeType = response.getElementsByTagName('callingNodeType')[0].firstChild.data;
		var callingNodeDepth = response.getElementsByTagName('callingNodeDepth')[0].firstChild.data;
		var callingSchema = response.getElementsByTagName('callingSchema')[0].firstChild.data;
		var callingSchemaType = response.getElementsByTagName('callingSchemaType')[0].firstChild.data;
		
		rowNodes = response.getElementsByTagName('row');
	
		if(rowNodes.length == 0){
			document.getElementById(callingNode).innerHTML= "No Results Found";
		}else{
			var displayText = "";

			//document.getElementById("custResults").innerHTML= " " + rowNodes.length + " Results Found";
			
			var n = "";
			var nt = "";
			var s = "";
			var st = "";
			var e = "";
			var d = "";
			
			var startTable = "<table border=\"0\"><tr><td>";
			
			for (var j=0; j< callingNodeDepth; j++) {
				startTable = startTable + "&nbsp;&nbsp;"
			}
			
			startTable = startTable + "</td><td align=\"left\">";
			
			displayText = displayText + startTable;
			
			for (var i=0; i< rowNodes.length; i++) {
				
				n = rowNodes[i].childNodes[0].firstChild.data;
				nt = rowNodes[i].childNodes[1].firstChild.data;
				s = rowNodes[i].childNodes[2].firstChild.data;
				st = rowNodes[i].childNodes[3].firstChild.data;
				e = rowNodes[i].childNodes[4].firstChild.data;
				d = rowNodes[i].childNodes[5].firstChild.data;
				var expandLink = "";
				var newNodeName = st + "_" + s + "_" + nt + "_" + n;
				
				
				if(e == "Y"){
					displayText = displayText + "<div id=\"openclose_" + newNodeName + "\">";
					displayText = displayText + "<a href=\"javascript:void(0)\" onclick=\"expandNode('" + newNodeName + "','" + s + "','" + nt + "','" + st + "')\";><img border=\"0\" src=\"images/plus.gif\" align=\"left\"></a>";
					displayText = displayText + "</div>";
				}
				
				displayText = displayText + "<img src=\"images/" + nt +".gif\">" + n + "<BR>&nbsp;&nbsp;&nbsp;<div id=\"" + newNodeName + "\"></div>";
				
				//displayText = displayText + "<img border=\"0\" src=\"images/" + nt + ".gif\"><a href=\"javascript:void(0)\" onclick=\"addIdentifier('" + rowNodes[i].childNodes[0].firstChild.data + "');\">" + rowNodes[i].childNodes[0].firstChild.data + "</a><BR>";
			}
			
			displayText = displayText + "</td></tr></table>";
			
			document.getElementById(callingNode).innerHTML= displayText;

		}
	
	
	}
	
</script>

</head>
<body bgcolor="#CCCCCC">
<a href="logout.jsp" target="_top">Disconnect</a><BR>
<%
try{
	DatabaseMetaData md = con.getMetaData();
	out.println("" + md.getUserName() + "<BR>");
	//out.println("" + md.getURL() + "<BR>");
	//out.println("" + md.getDatabaseProductName() + "<BR>");
	//out.println("" + md.getDatabaseProductVersion() + "<BR>");

}
catch(Exception e){
}
%>
<hr width="80%">

<div id="openclose_catalogs">
	<a href="javascript:void(0)" onclick="expandNode('catalogs','all','DBs','catalog','0')";><img border="0" src="images/plus.gif" align="left"></a>
</div><img src="images/schema.gif">catalogs<BR>
&nbsp;&nbsp;&nbsp;<div id="catalogs"></div>
<div id="openclose_schemas">
	<a href="javascript:void(0)" onclick="expandNode('schemas','all','DBs','schema','0')";><img border="0" src="images/plus.gif"  align="left"></a>
</div><img src="images/schema.gif">schemas<BR>
&nbsp;&nbsp;&nbsp;<div id="schemas"></div>
</body>
</html>