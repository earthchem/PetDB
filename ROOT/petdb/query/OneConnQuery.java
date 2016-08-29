package petdb.query;

/**
* Query class builds a connection to the PetDB database, runs the query
* and builds the data structure.
* This is an abstract class, the prepareData() method is implemented by the subclasses.
*/


import java.sql.*;

import petdb.config.DatabaseAdapter;

abstract class OneConnQuery
{
/**
* Query class builds a connection to the PetDB database, runs the query
* and builds the data structure.
* This is an abstract class, the prepareData() method is implemented by the subclasses.
*/


	protected String errorMsg = null;
	protected static Connection dbConn = null;

	protected boolean canExecute = true;

	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;

	protected  static DatabaseAdapter dbAdapt = null;
	protected String qry = "";

	final protected int executeQuery(boolean scrollable)
	{
		
		if (canExecute == false)
		{
			return -1;
		}

		if (initDBConn() != 0)
		{
			return -1;
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
                System.out.println("Error in executeQuery(boolean) "+e.getMessage());
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

		if (initDBConn() != 0)
		{
			return -1;
		}

		try
		{
			pstmt = dbConn.prepareStatement(qry);
			rs = pstmt.executeQuery();
		}
		catch(SQLException e)
		{
			errorMsg = qry + ": \n" + e.getMessage();
			return closeQuery();
		}
		
		return 0;
	}

	static synchronized protected int initDBConn()
	{
		if (dbConn == null)
		{
		
			dbAdapt =  DatabaseAdapter.getDatabaseAdapter();

		    petdb.config.QueryCounter.created++;
		    try {
			    dbConn = dbAdapt.getConnection();
		    } catch (SQLException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		}
		return 0;
	}

	protected void finalize()
	{
		int i = closeQuery();
		if( dbAdapt != null )
		{
			try
			{
				petdb.config.QueryCounter.deleted++;
				dbAdapt.closeConnection(dbConn);
			}
			catch(Exception e)
			{
				;  //return -1;
			}
		}
	}

	
	public void close() {int i = closeQuery();}
	
	final protected int closeQuery()
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

		/*
		if( dbAdapt != null )
		{
			try
			{
				petdb.config.QueryCounter.deleted++;
				dbAdapt.closeConnection();
			}
			catch(SQLException e)
			{
				return -1;
			}
		}
		*/
		return 0;
	}

/**
* The method executes the query against the database.
* @param scrol	   <code>true/false</code> if the resulting result set has to be scrollable or not.
* @return	   <code>1</code> if all the connection, query and build the result data structure went well; <code>-1</code> otherwise.
*/
	public int runQuery(boolean scrol)
	{
		//if (initDBConn() == 0) 
			return executeQuery(scrol);
		//return -1;
	}

	public int runQuery()
	{
		//if (initDBConn() == 0) 
			return executeQuery();
		//return -1;
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


