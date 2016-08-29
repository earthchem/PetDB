



<%@ page import="petdb.data.DataBrowser" %>

   <%@ page import="java.io.*"  %>
<%

String type = request.getParameter("type");
String pageNum = request.getParameter("page");
String data = null;
if(!"diamond".equals(type)&&!"xenolith".equals(type)) out.println("Error: Incorrect type!");
else {
       if(pageNum==null) data = new DataBrowser(type).getData();
       else data = new DataBrowser(type).getDataByPage(new Integer(pageNum).intValue());
response.setContentType("application/json; charset=UTF-8");  

int size = data.length();
InputStream in = new ByteArrayInputStream(data.getBytes("UTF-8"));
ServletOutputStream out1 = response.getOutputStream();

byte[] outputByte = new byte[size];
/*while(in.read(outputByte, 0, size) != -1)
{
	out1.write(outputByte, 0, size);
}*/
 out1.write(data.getBytes());
in.close();
out1.flush();
out1.close();
}
%>
 

 
<%--

    String type = request.getParameter("type");
    if(!"diamond".equals(type)&&!"xenolith".equals(type)) out.println("Error: Incorrect type!");
    else new DataBrowser(type);
    
 
    
--%>

