<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ include file="conn.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DB Browser</title>
</head>
<frameset cols="25%,*">
	<frame src="tree.jsp" name="contents" leftmargin="1" topmargin="0" marginwidth="0" marginheight="0">
	<frame src="main.jsp" name="main" scrolling=auto>
</frameset>

<noframes>
<body>
<h1>Frames Not Supported</h1>
<p>
You appear to be using a browser that does not support frames.  Please download <a href="http://www.mozilla.com/firefox">firefox</a> and try again.
</p>
</body>
</noframes>

</html>