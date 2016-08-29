
package petdb.query;

import java.sql.*;

import petdb.config.DatabaseAdapter;


abstract class EditingQuery
{
	protected String errorMsg = null;
	protected Connection dbConn = null;

	protected boolean canExecute = true;

	protected Statement stmt = null;
	protected DatabaseAdapter dbAdapt = null;
	protected String qry = "";

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
			stmt = dbConn.createStatement();

			int r = stmt.executeUpdate(qry);
			r = stmt.executeUpdate("commit");
			return r; 
		}
		catch(SQLException e)
		{
			errorMsg = qry + ": \n" + e.getMessage();
			return closeQuery();
		}
		
	}


	final protected int initDBConn()
	{
		dbAdapt = DatabaseAdapter.getDatabaseAdapter();
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


	final protected int closeQuery()
	{
		if (stmt != null)
		{
			try
			{
				stmt.close();
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
				dbAdapt.closeConnection(dbConn);
			}
			catch(Exception e)
			{
				return -1;
			}
		}
		return 0;
	}

	public abstract int setQuery(String id, String query);

	public int runQuery()
	{
		int r = -1;
		//if (initDBConn() == 0) 
		r =  executeQuery();
		closeQuery();
		return r;
	}


	public String getErrorMsg()
	{
		return errorMsg;
	}


	public String toString()
	{
		synchronized(qry) {
			return ("\n Query = " + qry +
				 "\n statement = " + stmt + "\n connnection  = " + dbConn );
		}
	}

}

