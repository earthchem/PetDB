package admin.dbAccess;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginResponse extends HttpServlet {
	
  public void doPost(HttpServletRequest request,HttpServletResponse response)
                   throws ServletException, IOException 
  {
		String f = "debug ";
		HttpSession session=request.getSession();
		session.removeAttribute("userAuthorized");
		
		ServletContext context = getServletContext();
	    boolean logged = false;
	    String errMsg=null;
	    f += " before if ";  
	    if ( (request.getParameter("p") != null) && (request.getParameter("u") != null) )
	    {
          String user_pw = request.getParameter("p");
          String user_name = request.getParameter("u");

         /* String user_q  = "SELECT  * "
                	        + " FROM   useraccount u "
                        	+ " WHERE  u.user_alias ='" + user_name + "'"
                        	+ " AND u.user_pw = '" + user_pw + "'";
                            */
           String user_q  = "SELECT  *  FROM  useraccount u WHERE  u.user_alias =? AND u.user_pw = ?";
		  f += " " + user_q;
		
		  DatabaseAccess da;
		  try {
			    da = new DatabaseAccess(context.getInitParameter("datasource"));
			   // QueryResultSet tmpRS = da.executeQuery(user_q);
               QueryResultSet tmpRS = da.executeQuery(user_q,user_name,user_pw );
			    if (tmpRS!= null) f += " " + tmpRS;  
	            if ((tmpRS != null) && (tmpRS.getRecordCount()==1)) {
	        	    //System.out.println("Setting up session attribute");
	                session.setAttribute("userAuthorized",new Boolean(true));
	                logged = true;
	            } else {
	                	logged = false;
	                	//System.out.println("Wrong user name or password");
	                	errMsg="Wrong user name or password";
	                	session.removeAttribute("userAuthorized");
	        	}
			
		      } catch (NamingException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			    errMsg="Not able to connect to database";
		      } catch (SQLException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			    errMsg="Not able to connect to database";
		      }        
	     }
		
	     // the <logged> variable assigned true if a record with the entered <u> and <p> is matched
	     // in the PetDB table UserAccount.
	     if (logged) {
	    	response.sendRedirect("admin_frame.jsp");
	     } else { 
	    	 if(errMsg != null)
		         response.sendRedirect("index.jsp?errmsg="+errMsg);
	    	 else
	    		 response.sendRedirect("index.jsp");
	     }
    }

 
 /*
 public void doPost(HttpServletRequest request,HttpServletResponse response)
                   throws ServletException, IOException 
  {
        HttpSession session=request.getSession();
		session.removeAttribute("userAuthorized");
		
		ServletContext context = getServletContext();
	    boolean logged = false;
	    String errMsg=null;
	    if ( (request.getParameter("p") != null) && (request.getParameter("u") != null) )
	    {
          String user_pw = request.getParameter("p");
          String user_name = request.getParameter("u");

          String q  = "SELECT  *  FROM  useraccount u WHERE  u.user_alias =? AND u.user_pw = ?";
          Connection con = null;	
          PreparedStatement prepStmt = null;
          ResultSet rs = null;
		  try {
                con =  SchemaCollection.getDBConnection(context.getInitParameter("datasource"));
               
                prepStmt = con.prepareStatement(q);
                prepStmt.setString(1, user_name);
                prepStmt.setString(2, user_pw);
                rs = prepStmt.executeQuery();
                QueryResultSet tmpRS = new QueryResultSet(rs);               
                
	            if ((tmpRS != null) && (tmpRS.getRecordCount()==1)) {
	                session.setAttribute("userAuthorized",new Boolean(true));
	                logged = true;
	            } else {
	                	logged = false;
	                	errMsg="Wrong user name or password";
	                	session.removeAttribute("userAuthorized");
	        	}
			
		      } catch (NamingException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			    errMsg="Not able to connect to database";
		      } catch (SQLException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			    errMsg="Not able to connect to database";
		      }        	   
            finally {
                try {
                  if(rs != null) rs.close();
                  if(prepStmt != null) prepStmt.close();
                  if(con != null) con.close();
                } catch (Exception e) {
                  e.printStackTrace();
                } 
                 if (logged) {
                    response.sendRedirect("admin_frame.jsp");
                 } else { 
                     if(errMsg != null)
                         response.sendRedirect("index.jsp?errmsg="+errMsg);
                     else
                         response.sendRedirect("index.jsp");
                 }
              }
        }
    }
    */
}