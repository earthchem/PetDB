package admin.dbAccess;

/**
* Query class builds a connection to the PetDB database, runs the query
* and builds the data structure.
* This is an abstract class, the prepareData() method is implemented by the subclasses.
*/

import java.sql.*;
import admin.config.DatabaseAdapter;

abstract class Query
{
/**
* Query class builds a connection to the PetDB database, runs the query
* and builds the data structure.
* This is an abstract class, the prepareData() method is implemented by the subclasses.
*/


	protected String errorMsg = null;
	protected Connection dbConn = null;

	protected boolean canExecute = true;

	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;

	protected admin.config.DatabaseAdapter dbAdapt = null;
	protected String qry = "";

	final protected int executeQuery(boolean scrollable)
	{
		
		if (canExecute == false)
		{
			return -1;
		}
		
		try
		{
			if ((dbConn == null) || (dbConn.isClosed()) )
				if  (initDBConn() != 0)
					return -1;
		
		} 
		catch (Exception e )
		{
			errorMsg = qry + ": \n" + e.getMessage();
			return closeQuery();
		}
		
		try
		{
			if (scrollable)
			pstmt = dbConn.prepareStatement(qry,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      	ResultSet.CONCUR_READ_ONLY);
			else 
			pstmt = dbConn.prepareStatement(qry);

			rs = pstmt.executeQuery();
		}
		catch(SQLException e)
		{
            System.err.println("Error in executeQuery(boolean) "+e.getMessage());
			errorMsg = qry + ": \n" + e.getMessage();
			return closeQuery();
		}
		
		return 0;
	}

	final protected int executeQuery()
	{
		if (canExecute == false)
		{
			return -1;
		}

		try 
		{
			if ((dbConn == null) || (dbConn.isClosed()) )
				if (initDBConn() != 0)
				return -1;
		
		} 
		catch (Exception e )
		{
			errorMsg = qry + ": \n" + e.getMessage();
			return closeQuery();
		}

		try
		{
			pstmt = dbConn.prepareStatement(qry);
			rs = pstmt.executeQuery();
		}
		catch(SQLException e)
		{
			errorMsg = qry + ": \n" + e.getMessage();
            System.err.println(errorMsg);
			return closeQuery();
		}
		
		return 0;
	}

	final protected int initDBConn()
	{
		dbAdapt = admin.config.DatabaseAdapter.getDatabaseAdapter();
		admin.config.QueryCounter.created++;
		try {
			dbConn = dbAdapt.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	protected void finalize()
	{
		int i = closeQuery();
	}

	
	public void close() {int i = closeQuery();}
	
	final protected int closeQuery()
	{
		try {
		if ((dbConn != null) && (!dbConn.isClosed()))
		{ 
		if (pstmt != null)
		{
			try
			{
				pstmt.close();
			}
			catch(SQLException e)
			{
				return -1;
			}
		}

		if (rs != null)
		{
			try
			{
			 	rs.close();
			}
			catch(SQLException e)
			{
				return -1;
			}
		}

		if( dbAdapt != null )
		{
			try
			{
				admin.config.QueryCounter.deleted++;
				dbAdapt.closeConnection(dbConn);
			}
			catch(Exception e)
			{
				return -1;
			}
		}
		}
		} catch (Exception e) {return -1; } 
		return 0;
	}

/**
* The method executes the query against the database.
* @param scrol	   <code>true/false</code> if the resulting result set has to be scrollable or not.
* @return	   <code>1</code> if all the connection, query and build the result data structure went well; <code>-1</code> otherwise.
*/
	public int runQuery(boolean scrol)
	{
		return executeQuery(scrol);
	}

	public int runQuery()
	{
		return executeQuery();
	}

	public String getQueryStr(){return qry;}

	public synchronized void prepareData() {;}

	public String getErrorMsg()
	{
		return errorMsg;
	}


	public String toString()
	{
		synchronized(qry) {
			return ("\n Query = " + qry + "\n statement = " + pstmt + "\n connnection  = " + dbConn );
		}
	}

}


