<%@ include file="conn.jspf" %>
<%@ page import="com.azprogrammer.mappings.*" %>
<%@ page import="com.azprogrammer.mappings.web.*" %>

<html>
<head>
<link rel="StyleSheet" href="stylsheet.css" type="text/css" media="screen">
<title>SQL Client</title>
</head>
<body>
<%
	BeanMetaData bean = BeanGenerator.createBean(request);
        session.setAttribute(bean.getBeanName(),bean);
%>
<BR>
<a href="source?type=bean&part=bean&beanName=<%=bean.getBeanName()%>"><%=bean.getBeanName()%>.java</a><BR>
<a href="source?type=bean&part=key&beanName=<%=bean.getBeanName()%>"><%=bean.getBeanName()%>Key.java</a><BR>
<a href="source?type=bean&part=director&beanName=<%=bean.getBeanName()%>"><%=bean.getBeanName()%>Director.java</a><BR>
<a href="source?type=bean&part=query&beanName=<%=bean.getBeanName()%>"><%=bean.getBeanName()%>Query.java</a><BR><BR>
  <BR><BR>
<a href="source?type=jsp&part=list&beanName=<%=bean.getBeanName()%>"><%=bean.getBeanName()%>List.jsp</a><BR>
<a href="source?type=jsp&part=addNew&beanName=<%=bean.getBeanName()%>">addNew<%=bean.getBeanName()%>.jsp</a><BR>
<a href="source?type=jsp&part=edit&beanName=<%=bean.getBeanName()%>">edit<%=bean.getBeanName()%>.jsp</a><BR>
<a href="source?type=jsp&part=save&beanName=<%=bean.getBeanName()%>">save<%=bean.getBeanName()%>.jsp</a><BR><BR>
  <BR><BR>
<a href="source?type=zip&part=save&beanName=<%=bean.getBeanName()%>">save<%=bean.getBeanName()%>.zip</a>
<!--

<form action="servlet/Source.java" method="post" target="_blank">
<textarea cols="140" rows="34" name="javaCode"><%//=bean.getCompleteCode()%></textarea>
<input type="Submit" value="save source code">

</form>
-->
</body>
</html>
