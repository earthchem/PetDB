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

public class ExpeditionDownload extends HttpServlet 
{


	private boolean readAndWrite(HttpServletRequest request, HttpServletResponse  response) 
	throws Exception  
	{
		
 	       CCriteriaCollection c_c_collection;

        	Wrapper wrapper = new ByExpWrapper("");
        	ExcelDS excelDS = (ExcelDS)wrapper.getControlList("0");
        	((RowBasedDS)excelDS).next();
        	Vector columns = new Vector(excelDS.getColumnCount());

        	ServletOutputStream writeAt = response.getOutputStream();

        	ExlDS exlDS = new ExlDS(excelDS, columns, writeAt);

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
	
	String fileName = "expeditions.xls";
		
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
		throw new java.io.IOException("Error writing to the Excel Page");
	}
}

public void doPost(HttpServletRequest request, HttpServletResponse  response)
	throws java.io.IOException, ServletException 
{
	doGet(request, response);
}

}
