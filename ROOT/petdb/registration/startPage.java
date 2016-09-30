package petdb.registration;

import java.io.IOException;
import java.util.Vector;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;

import petdb.config.DatabaseAdapter;


import petdb.wrapper.WrapperCollection;
import petdb.query.MethodTableQuery;
import petdb.query.ItemMeasuredTableQuery;
import petdb.query.MineralListTableQuery;
import petdb.data.AValuePerKeyDS;

public class startPage extends HttpServlet {
	
  public void doPost(HttpServletRequest request,HttpServletResponse response)
                   throws ServletException, IOException 
  {
		ServletContext context = getServletContext();
        /* Create DatabaseAdapter singleton */
		try {
		  DatabaseAdapter.createDatabaseAdapter(context.getInitParameter("datasource"));
		}
		catch ( NamingException e) 
		{  
			e.getStackTrace();
			//System.out.println(context.getInitParameter("datasource"));
			String requestURI = request.getRequestURI();
			//System.out.println("requestURI="+requestURI);
			requestURI = requestURI.replaceAll("/start", "");
			request.getSession().setAttribute("error", "Unable to access data source: "+context.getInitParameter("datasource"));
			response.sendRedirect(requestURI+"/error2.jsp");
			return;
		}
		
		//Clean all session information
		if(!request.getSession().isNew()) //not new session such as session is not closed but the program is redeployed
		{
			//clean up all previous attributes.
			request.getSession().invalidate(); 
			//Clean wrapper collections.
			WrapperCollection.clear();
			//create new session;
			request.getSession(true);
		}
		
		// Set up some information from database
		MethodTableQuery m= new MethodTableQuery();
		request.getSession().setAttribute("Method",m.getDataSet());
		
		ItemMeasuredTableQuery im= new ItemMeasuredTableQuery();
		request.getSession().setAttribute("ItemMeasured",im.getDataSet());
		
		MineralListTableQuery ml= new MineralListTableQuery();
		request.getSession().setAttribute("MineralList",ml.getDataSet());
		
		//AValuePerKeyDS ds = (AValuePerKeyDS) request.getSession().getAttribute("ItemMeasured");
		//if (ds != null)
		//{
		//	Vector methods = ds.getKeys();
		//	if (methods != null)
		//	for (int i=0; i < methods.size(); i++)
		//	{
		//		String key = (String)methods.elementAt(i);
		//		String val = (String)ds.getValue(key);
		//		System.out.println(key+" ==> "+val);
		//	}
		//}
        // Get all init parameters
		
		//System.out.println(context.getInitParameter("datasource"));
		String requestURI = request.getRequestURI();
		
		requestURI = requestURI.replaceAll("/start", "");
		response.sendRedirect(requestURI+"/pg2.jsp");
  }
  public void doGet(HttpServletRequest request,HttpServletResponse response)
          throws ServletException, IOException 
  {
     doPost(request,response);
  }
}