<%@page import="java.io.*"%><%@page contentType="application/json" %><%
	OutputStream o = response.getOutputStream();
	if(request.getAttribute("data")!=null)
  	o.write(request.getAttribute("data").toString().getBytes());
  o.flush();
  o.close();
  return; 
%>