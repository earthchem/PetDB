package petdb.data;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;
import jxl.*;
import jxl.write.*;
import petdb.wrapper.*;
import petdb.query.*;
import petdb.criteria.*;

public class ReferenceDownload extends HttpServlet 
{


	private boolean readAndWrite(HttpServletRequest request, HttpServletResponse  response) 
	throws Exception  
	{
	
	CCriteriaCollection c_c_collection;
	HttpSession session = request.getSession();

        int ident =0;
        String filter ="";
        Wrapper wrapper = null;
        String state ="";
         
        if (request.getParameter("All") != null)
        {
                wrapper = new ByPubWrapper("",ReferenceDCtlQuery.All);
        }
        else  if (request.getParameter("filtered") != null )
        {
                String theQuery = (String)request.getParameter("filtered");
                if ( (filter = (String)session.getAttribute(theQuery)) != null && !filter.trim().equals(""))
                {
                        wrapper = new ByPubWrapper(filter,ReferenceDCtlQuery.References);
                        ident = filter.hashCode();
                } else 
			throw new Exception("Your session has expired."
			+ " Please start by building a new Query!");
        } else 
		throw new Exception("Your session has expired." 
		+ "Please start by building a new Query!");

        	ExcelDS excelDS = (ExcelDS)wrapper.getControlList("0");
        	((PubRecordDS)excelDS).goFirst();
        	Vector columns = new Vector(excelDS.getColumnCount());


        	ServletOutputStream writeAt = response.getOutputStream();

        	ExlDS exlDS = new ExlDS(excelDS, columns, writeAt);
        	WritableSheet sheet = exlDS.getSheet(ExlDS.DataSheet);

        	exlDS.write();

        	exlDS.getWorkbook().write();
        	exlDS.closeBook();
        	writeAt.flush();
        	writeAt.close();

        	int r = wrapper.closeQueries();

		return true;

	}
	
public void doGet(HttpServletRequest request, HttpServletResponse  response)
	throws java.io.IOException, ServletException 
{
	HttpSession session = request.getSession();

	ServletConfig config = this.getServletConfig();
	if (config == null)
	{
		return;
	}

	ServletContext application = config.getServletContext();
	if (application == null)
	{
		return;
	}
	
	String fileName = "references.xls";
		
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "file; filename=" + fileName);
	
	try
	{
		if (readAndWrite(request,response))
			return;
		else 
			throw new java.io.IOException("Your Session has Expired");
	} 
	catch (Exception e) 
	{
		throw new java.io.IOException("Error writing to the Excel Page" + e.getMessage());
	}
}

public void doPost(HttpServletRequest request, HttpServletResponse  response)
	throws java.io.IOException, ServletException 
{
	doGet(request, response);
}

}
