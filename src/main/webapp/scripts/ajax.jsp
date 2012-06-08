<%@ page import="java.util.*" %><%

	// Set to expire far in the past.
	response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
	// Set standard HTTP/1.1 no-cache headers.
	response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
	response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	// Set standard HTTP/1.0 no-cache header.
	response.setHeader("Pragma", "no-cache");
	
	response.setContentType("text/javascript");




 for (Enumeration e = request.getParameterNames() ; e.hasMoreElements(); ) {
	String parmName = e.nextElement().toString();
		if((parmName != null) && (parmName.startsWith("n"))){
	        	
String name = request.getParameter(parmName);
			if(name == null){
				name = "";
			}
				name = name.trim();

			%>

var xmlHttpRequest<%= name%>;

function _processXmlResponse<%= name%>()
{
  if (xmlHttpRequest<%= name%>.readyState == 4) {


	if (xmlHttpRequest<%= name%>.status == 200) {
      		handleResponse<%= name%>();

    	} else {
      		//handle error responses (ie., 404, 500) if defined 
		if ( window.handleErrorResponse<%= name%> )
		{
   			handleErrorResponse<%= name%>();
		}
    	}
    
  }
}

function xmlRequest<%= name%>(url)
{
  if (window.XMLHttpRequest) {
    xmlHttpRequest<%= name%> = new XMLHttpRequest();
  } else {
    xmlHttpRequest<%= name%> = new ActiveXObject("Microsoft.XMLHTTP");
  }
  if (xmlHttpRequest<%= name%>) {
    xmlHttpRequest<%= name%>.onreadystatechange = _processXmlResponse<%= name%>;
    xmlHttpRequest<%= name%>.open("GET", url, true);
    if (window.XMLHttpRequest) {
      xmlHttpRequest<%= name%>.send(null);
    } else {
      xmlHttpRequest<%= name%>.send();
    }
  }
}

			<%
	         		
	         	}
	     }



%>
