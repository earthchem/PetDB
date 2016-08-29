package admin.data;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import java.sql.*;

import admin.config.*;
import admin.dbAccess.*;

public class IGSNSearch extends HttpServlet {
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException 
    {
        doPost(request,response);
    }
            
	public void doPost(HttpServletRequest request,HttpServletResponse response)
              throws ServletException, IOException 
    {
       String type=request.getParameter("type");
       String id=request.getParameter("id");
       String db_schema = DatabaseAccess.getDataSourceSchema();
   //    System.out.println("db_schema="+db_schema);
       Boolean yes = (Boolean)request.getSession().getAttribute("userAuthorized");
       if(yes==null)
       { 
    	   IGSNUpdate.printOutError(response, "Session Expired. Please logout and login again.");   
    	   return;
       }
       if(yes.booleanValue()==false ){
    	   IGSNUpdate.printOutError(response, "Session Expired. Please logout and login again.");   
    	   return;
       }
       try {
         DatabaseAccess da = new DatabaseAccess(db_schema);
	     QueryResultSet	resultSet = da.executeQuery(getQuery(type,id));
	     response.setContentType("application/xml");
	     PrintWriter out = response.getWriter();

         if(resultSet == null ) 
	     {
	    	 //System.out.println("Database error.");
	    	 out.print("Database error.");
	    	 return ;
	     }
	     int rowcount = resultSet.getRecordCount();
	     int colcount = resultSet.getColumnCount();
	     Vector cLabels = resultSet.getColumnsLabels();
	   //  for(int i=0;i<colcount;i++)
	   // 	 System.out.print(cLabels.elementAt(i)+" ");
	   //  System.out.println(" ");
	     out.print("<Data>");
	     out.print("<TotalRows>"+rowcount+"</TotalRows>");
	     out.print("<TotalCols>"+colcount+"</TotalCols>");
	     for(int i=0;i<rowcount;i++)
	     {
	    	 Record rec = resultSet.getRecordAt(i);
	    	 if(rec.size() == 0 ) continue;
	    	 out.print("<row>");
	    	 for(int j=0;j<colcount;j++)
	    	 {
	    		 out.print("<"+cLabels.elementAt(j)+">"+rec.valueAt(j));
	    	//	 System.out.print(rec.valueAt(j)+" ");
	    		 out.print("</"+cLabels.elementAt(j)+">");
	    	 }
	    //	 System.out.println(" ");
	    	 out.print("</row>");
	     }
	     out.print("</Data>");
	    // System.out.println(" ");	     
       } 
       catch (SQLException e )
       {
    	   System.err.println(e.getMessage());
       } 
       catch (NamingException e )
       {
    	   System.err.println(e.getMessage());
       }
	   
    }
    
	private String getQuery(String type, String id)
	{
	  String IGSNtableName="IGSN_INFO";
	  String qry =" ";
	  if( type.equalsIgnoreCase("sample")== true)
	  {
	     qry ="select s.sample_id, s.sample_num,i.igsn from ";
	     qry +="Sample s, IGSN_INFO i where s.sample_num = i.sample_num (+) and s.sample_id LIKE '%"+id+"%' order by s.sample_id";
	  }
	  if( type.equalsIgnoreCase("station")== true)
	  {
	     qry ="select s.station_id STATION_ID, s.station_num STATION_NUM,i.igsn IGSN from ";
	     qry +="Station s, IGSN_INFO i where s.station_num = i.station_num (+) and s.station_id LIKE '%"+id+"%' order by s.station_id";
	  }
	  return qry;
	}
}

