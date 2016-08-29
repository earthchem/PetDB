package admin.config;

import java.sql.*;
import javax.naming.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

/* Get data source JINI name from web.xml and Get database connection from Weblogic Server. */
/* This is singleton means only one instance will be created in whole web app */
public class DatabaseAdapter{

	private DataSource ds   = null;
	private static String databaseJININame; /* JINI name configured in Weblogic Server */
	private static DatabaseAdapter ref=null;
	
	private DatabaseAdapter(String name) throws NamingException
	{
		databaseJININame=name;
		InitialContext context = null;
		context = new InitialContext();
        ds = (javax.sql.DataSource) context.lookup (databaseJININame);
			
	}
	
	/* Create a DatabaseAdapter instance if it is not created otherwise it will returned the singleton.
	 *  It should be called once. Well if called twice, it won't create another instance.
	 *  */
	public static void createDatabaseAdapter(String name) throws NamingException
	{	    if (ref == null)
	        ref = new DatabaseAdapter( name);		
	}

	/* Use this function to get DatabaseAdapter after it is created.*/
	public static DatabaseAdapter getDatabaseAdapter()
	{	
		return ref;
	}

    public Object clone()
		throws CloneNotSupportedException
	{
	    throw new CloneNotSupportedException(); 
	    // Do not allow clone of this class since it is singleton
	}
       
    public Connection getConnection() throws SQLException
	{
		return ds.getConnection();
	}



    public void closeConnection(Connection conn)
        throws java.sql.SQLException
	{
	   conn.close();
	}

	public void finalize(Connection conn)
	{
		try
		{
			closeConnection(conn);
		}
		catch (java.sql.SQLException e) {}
	}

}
