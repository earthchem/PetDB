package admin.data;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import java.sql.*;

import admin.config.*;
import admin.dbAccess.*;

public class IGSNUpdate extends HttpServlet {
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException 
    {
        doPost(request,response);
    }
            
	public void doPost(HttpServletRequest request,HttpServletResponse response)
              throws ServletException, IOException 
    {
       String type=request.getParameter("type");
       
       
      
       Boolean yes = (Boolean)request.getSession().getAttribute("userAuthorized");
       if(yes==null)  throw new IOException("Session Expired.");
       if(yes.booleanValue()==false ) throw new IOException("Session Expired.");
       
       String updateOneStr = request.getParameter("updateOne");
       boolean success = false;
       int rowcount = 1;
	   int colcount =3;
       if( ("YES").equalsIgnoreCase(updateOneStr) )
       {
           String iyes=request.getParameter("iyes");
           String num = request.getParameter("num");
           String igsn= request.getParameter("igsn");
           try{
             success=updateOneEntry(type,iyes,num,igsn);
           }
           catch (NamingException e )
   	       {
   	    	   success = false;
   	    	   System.err.println(e.getMessage());
   	    	   printOutError(response, e.getMessage());
   	    	   return ;
   	       }
           catch (SQLException e )
   	       {
   	    	   success = false;
   	    	   System.err.println(e.getMessage());
   	    	   printOutError(response, e.getMessage());
   	    	   return;
   	       }
            catch (Exception e)
           {
              success = false;
              System.err.println(e.getMessage());
              printOutError(response, e.getMessage());
              return;
           }
       }
       else
       {
    	  // success=updateAllEntries(type);
       }
       
       if(success)//if database modification is successfull return xml data
       {
	       response.setContentType("application/xml");
	       PrintWriter out = response.getWriter();

	       out.print("<Data>");
	       out.print("<TotalRows>"+rowcount+"</TotalRows>");
	       out.print("<success>yes</success>");
	       out.print("<error>no</error>");
	       out.print("</Data>");  
       }
	   else
	   {
		   response.setContentType("application/xml");
		   PrintWriter out = response.getWriter();
           out.print("<Data>");
           out.print("<TotalRows>"+rowcount+"</TotalRows>");
           out.print("<success>no</success>");
           out.print("<error>no</error>");
           out.print("</Data>");  
       }
    }
    
	private boolean updateOneEntry(String type,String insertYes,String num,String igsn) throws Exception,SQLException,NamingException
	{
		String db_schema = DatabaseAccess.getDataSourceSchema();
        DatabaseAccess da = new DatabaseAccess(db_schema);
	  	int totalRowChanged =  da.executeUpdate(getQueryForOneEntry(type,insertYes,num,igsn));
	  	if(totalRowChanged ==0 ) return false;
	  	else return true;
    }
	
	private boolean updateAllEntries(String type) throws Exception,SQLException,NamingException
	{
		String db_schema = DatabaseAccess.getDataSourceSchema();
	         DatabaseAccess da = new DatabaseAccess(db_schema);
	  	   //  QueryResultSet	resultSet = da.executeQuery(ForOneEnry(type,id,num,igsn));
             return true;	   
	}
	
	private String getQueryForOneEntry(String type, String insert, String num,String igsn)
	{
	  String IGSNtableName="IGSN_INFO";
	  String qry =" ";
	  if( type.equalsIgnoreCase("sample")== true)
	  {
		if(insert.equalsIgnoreCase("yes") == true)
	        qry ="insert into IGSN_INFO (IGSN,SAMPLE_NUM) values ('"+igsn+"',"+num+")";
		else
	        qry ="update IGSN_INFO set IGSN='"+igsn+"' where SAMPLE_NUM='"+num+"'";
		
		//System.out.println("qry=>"+qry);
	  }
	  if( type.equalsIgnoreCase("station")== true)
	  {
		  if(insert.equalsIgnoreCase("yes") == true)
		        qry ="insert into IGSN_INFO (IGSN,STATION_NUM) values ('"+igsn+"',"+num+")";
		  else
		        qry ="update IGSN_INFO set IGSN='"+igsn+"' where STATION_NUM='"+num+"'";
		 // System.out.println("qry=>"+qry);
	  }
	  return qry;
	}
	
	public static void printOutError(HttpServletResponse response, String errMsg) throws IOException
	{
	    response.setContentType("application/xml"); 
	    PrintWriter out = response.getWriter();
        out.print("<Data>");
        out.print("<error>"+errMsg+"</error>");
        out.print("</Data>"); 
	}
}

