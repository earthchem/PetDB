/*$Id:*/
package admin.data;

import admin.config.DatabaseAdapter;
import admin.dbAccess.SimpleQuery;
import javax.servlet.*;

import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.naming.NamingException;
import jxl.*;
import jxl.write.*;
//import petdb.criteria.*;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.NumberFormat;



/* The class is triggered by the 'Excel' button in reference_list.jsp page. It will call proper classes to retrieve data
 * from database and assemble them into a Microsoft Excel file and return it for user to download. 
*/

public class ReferenceExcelDownload extends HttpServlet 
{
	/* Get actual class name to be printed on */
//	static Logger log = Logger.getLogger(ReferenceExcelDownload.class.getName());
	private static String schema; 
    
    public static String getSchema() {return schema;}
    
	public boolean readAndWrite(HttpServletRequest request, HttpServletResponse  response) 
	throws IOException  
	{
        ServletContext context = getServletContext();
        /* Create DatabaseAdapter singleton */
		try {
		  DatabaseAdapter.createDatabaseAdapter(context.getInitParameter("datasource"));
		}
		catch ( NamingException e) 
		{  
			e.getStackTrace();			
		}   
        
        schema = getServletContext().getInitParameter("datasource");
		String refnum = (String) request.getParameter("ref_num");	
		String fileName = refnum+".xls";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachement; filename=" + fileName);
		
        /* Get the OutputStream object from HttpResponse object. The OutputStream will be filled with data. */
        ServletOutputStream writeAt = null;
        try {
             writeAt = response.getOutputStream();
        } catch(Exception e)
        { 
            throw new java.io.IOException(e.getMessage());
        }	
     
        ReferenceExcel exlBook=null;
        try{
        	exlBook = new ReferenceExcel( refnum, (OutputStream)writeAt, false);
            exlBook.addTableInRefSheet(new TableInRefTable2DS(refnum));              
            exlBook.addCruiseSheet(new Cruises2DS(null));  
        	exlBook.addStationSheet(new Station2DS(refnum));   
        	exlBook.addSampleSheet(new Sample2DS(refnum)); 
            exlBook.addRockModeSheet(new RockMode2DS(null)); 
        	exlBook.addMethodSheet(new Method2DS(refnum));        	
        	exlBook.addRockSheet(new Rock2DS(refnum));             
            exlBook.addMineralSheet(new Mineral2DS(refnum));          
        	exlBook.addInclusionSheet(new Inclusion2DS(refnum)); 
  		    exlBook.writeAndCloseBook();
  		    
        } catch(Exception e)
        { 
            System.err.println("Error write to ReferenceExcel"+e.getMessage());
            throw new java.io.IOException(e.getMessage());
        }
        
        /* Write out second sheet 'TABLE_TITLES' */
	//	int r = refwrapper.closeQueries();

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
	
		readAndWrite(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse  response)
	throws java.io.IOException, ServletException 
	{
		doGet(request, response);
	}

}
