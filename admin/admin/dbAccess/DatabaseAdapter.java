package admin.dbAccess; 

import java.sql.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseAdapter {
	
        Connection 	conn;
        String dataSource;

      /**
       * Load database driver and establish database connection.
       *
       * @returns  Connection to target database is established.                 
       */

       public DatabaseAdapter (String dataURL)
       {
            dataSource = dataURL;
       }


       public void initConnection () throws NamingException, SQLException
       {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup(dataSource);    
            conn = ds.getConnection();
       }


       public Connection getConnection()
	   {
		 return conn;
	   }



       public void closeConnection()
        throws java.sql.SQLException
	   {
	     conn.close();
	   }

	   public void finalize()
	   {
		  try
		  {
			closeConnection();
		  }
		  catch (java.sql.SQLException e) {}
	   }

}