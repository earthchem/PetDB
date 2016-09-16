<%@ include file="headCode.jspf"%>
<%
String start_time = request.getParameter("start");
String end_time = request.getParameter("end");
String timeCondition = "Where download_date > to_date('"+start_time+"','YYYY-MM-DD') and download_date < to_date('"+end_time+"','YYYY-MM-DD')";
DownloadPurposeStatisticsDSQuery qry = new DownloadPurposeStatisticsDSQuery(timeCondition);
DownloadPurposeStatisticsDS ds = (DownloadPurposeStatisticsDS) qry.getDataSet();
int columnCnt = ds.getColumnCount();
Vector v = ds.getColumnNames();
String outputXML = new String("<?xml version='1.0' standalone='yes'?>");
    
outputXML +="<STATISTICS type=\"DOWNLOAD_PURPOSE\">";
   
while(ds.next())
{
        outputXML +="<RECORD>";  
        for (int i=1;i<=columnCnt;i++)
        {
            try {
                outputXML +="<" + v.elementAt(i-1) + ">"+ds.getValue(i)+"</" + v.elementAt(i-1) + ">"; 
            }
            catch (Exception e) {
                out.clear();
                out.println("<STATISTICS type=\"DOWNLOAD_PURPOSE\"><error>"+e.getMessage()+"</error></STATISTICS>");
                response.setContentType("text/xml");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        } 
        outputXML +="</RECORD>";  
}
if(start_time == null && end_time ==null)
{
    outputXML +="<error>Please specify 'start' and 'end' time.</error>";
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
}
else
{
    response.setStatus(HttpServletResponse.SC_OK);
}
outputXML +="</STATISTICS>";
out.clear();
out.println(outputXML.trim());
response.setContentType("text/xml");
%>