<%@ include file="conn.jspf" %><%@ page import="java.sql.*" %><%	response.setContentType("text/xml"); %><?xml version="1.0" encoding="UTF-8" standalone="yes"?><response>
<%

/******************

	for each node:
	s= schema
	st = schema type
	n = node name
	nt = node type
	e = expandable Y/N
	d = depth

*************************/

try{
	

	String schema;
	String schemaType;
	String node;
	String nodeType;
	int nodeDepth = 0;
	
	schema = request.getParameter("schema");
	schemaType = request.getParameter("sType");
	node = request.getParameter("node");
	nodeType = request.getParameter("nType");
	try{
		nodeDepth = Integer.parseInt(request.getParameter("depth"));
	}catch(Exception numE){
		//do nothing
	}
	

		
		
		StringBuffer sb = new StringBuffer();		
		sb.append("<callingNode><![CDATA[" + node + "]]></callingNode>");
		sb.append("<callingNodeType><![CDATA[" + nodeType + "]]></callingNodeType>");
		sb.append("<callingNodeDepth>" + nodeDepth + "</callingNodeDepth>");
		sb.append("<callingSchema><![CDATA[" + schema + "]]></callingSchema>");
		sb.append("<callingSchemaType><![CDATA[" + schemaType + "]]></callingSchemaType>");
		
		DatabaseMetaData dbmd= con.getMetaData();
		ResultSet rs = null;
		
		if("DBs".equals(nodeType)){
				String colStr ="";
                if("schema".equals(schemaType)){
                  rs = dbmd.getSchemas();
                  colStr = "TABLE_SCHEM";
                }else{
                  rs = dbmd.getCatalogs();
                  colStr = "TABLE_CAT";
                }
                
                while(rs.next()){
    				sb.append("<row>");
    				sb.append("<n><![CDATA[" + rs.getString(colStr) + "]]></n>");
    				sb.append("<nt>schema</nt>");
    				sb.append("<s><![CDATA[" + rs.getString(colStr) + "]]></s>");
    				sb.append("<st>" + schemaType + "</st>");
    				sb.append("<e>Y</e>");
    				sb.append("<d>" + (nodeDepth + 1) + "</d>");
    				sb.append("</row>");
    			}
                
                rs.close();
		}else if("schema".equals(nodeType)){
				sb.append("<row><n>tables</n><nt>tables</nt><s>" + schema + "</s><st>" + schemaType + "</st><e>Y</e><d>" + (nodeDepth + 1) + "</d></row>");
				sb.append("<row><n>views</n><nt>views</nt><s>" + schema + "</s><st>" + schemaType + "</st><e>Y</e><d>" + (nodeDepth + 1) + "</d></row>");
				sb.append("<row><n>functions</n><nt>functions</nt><s>" + schema + "</s><st>" + schemaType + "</st><e>Y</e><d>" + (nodeDepth + 1) + "</d></row>");
				sb.append("<row><n>sequences</n><nt>sequences</nt><s>" + schema + "</s><st>" + schemaType + "</st><e>Y</e><d>" + (nodeDepth + 1) + "</d></row>");
			
		}else if("tables".equals(nodeType)){
			rs = dbmd.getTables(null,schema,"%",new String[]{"TABLE"});
			 while(rs.next()){
 				sb.append("<row>");
 				sb.append("<n><![CDATA[" + rs.getString("TABLE_NAME") + "]]></n>");
 				sb.append("<nt>table</nt>");
 				sb.append("<s><![CDATA[" + schema + "]]></s>");
 				sb.append("<st>" + schemaType + "</st>");
 				sb.append("<e>Y</e>");
 				sb.append("<d>" + (nodeDepth + 1) + "</d>");
 				sb.append("</row>");
 			}
		}	
		
		out.print(sb.toString());

}catch(Exception e){
	out.println(e.toString());
}
%></response>