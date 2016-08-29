package petdb.query;

import java.sql.*;
import java.util.*;

import petdb.config.DatabaseAdapter;

public class PetDBCounters
{
	static String referencesQRY="select count (ref_num) as Counter from petdb.reference where status = 'COMPLETED'";
	static String samplesQRY="select count (sample_num) as Counter from sample";
	static String stationsQRY="select count (station_num) as Counter from station";
	static String chemicalQRY="select count (chemistry_num) as Counter from chemistry";
	static String mineralQRY="select count (chemistry_num) as Counter from chemistry where analysis_num in (select analysis_num from analysis where batch_num in (select batch_num from batch where material_num=6))";
	static String rockQRY="select count (chemistry_num) as Counter from chemistry where analysis_num in (select analysis_num from analysis where batch_num in (select batch_num from batch where material_num=7 or material_num=8))";
	static String volcanicglassQRY="select count (chemistry_num) as Counter  from chemistry where analysis_num in (select analysis_num from analysis where batch_num in (select batch_num from batch where material_num=3))";
	static String inclusionsQRY="select count (chemistry_num) as Counter from chemistry where analysis_num in (select analysis_num from analysis where batch_num  in (select batch_num from batch where material_num=5))";

	protected String errorMsg = null;
	protected Connection dbConn = null;

	protected boolean canExecute = true;

	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;

	protected DatabaseAdapter dbAdapt = null;
	protected String qry = "";
	
	/* Constructor: to create an instance which does not have query assigned yet. 
	 * canExecute is assigned to false. The instance need to be setQRYType 
	 * after it is created. */
    public PetDBCounters()
	{ 
		canExecute = false;
	}
	
    /* There several pre-defined queries stored. It will set proper query according to the number pass-in.
     * 1: References 
     * 2: Samples 
     * 3: Stations
     * 4: Chemicals
     * 5: Minerals
     * 6: Rocks
     * 7: Volcanic Glass
     * 8: Inclusions
     * */
	protected void setQRYType(int type)
	{
		switch (type)
		{
		  case 1:
			  qry=referencesQRY;
			  canExecute=true;
			  break;
		  case 2: 
			  qry=samplesQRY;
			  canExecute=true;
			  break;
		  case 3:
			  qry=stationsQRY;
			  canExecute=true;
			  break;
		  case 4:
			  qry=chemicalQRY;
			  canExecute=true;
			  break;
		  case 5:
			  qry=mineralQRY;
			  canExecute=true;
			  break;
		  case 6:
			  qry=rockQRY;
			  canExecute=true;
			  break;
		  case 7:
			  qry=volcanicglassQRY;
			  canExecute=true;
			  break;
		  case 8: 
			  qry=inclusionsQRY;
			  canExecute=true;
			  break;
		  default:
			  canExecute=false;
			  break;
		}
	}
	
	/*Get the number for specific type. The type was set by 'setQRYType'. This function should be called after executeQuery is called. */
	protected int getCounterNum() throws SQLException
	{
		if(rs != null)
		{
			while (rs.next()) {
			return rs.getInt("Counter");
			}
		}
        return -1;
	}
	
	public int getCounterNum(int type)
	{
        int num=-1;
		setQRYType(type);
		if(executeQuery()==-1) return -999;
        try {
        	num= getCounterNum();
        }
        catch (SQLException e)
        {
            canExecute=false;
        }
        return num;
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
			errorMsg = qry + " \nException:" + e.getMessage();
			
			return -1;
		}

		try
		{
			pstmt = dbConn.prepareStatement(qry);
			rs = pstmt.executeQuery();
		}
		catch(SQLException e)
		{
			errorMsg = qry + " \nSQLException:" + e.getMessage();
			return -1;
		}
		
		return 0;
	}

	final protected int initDBConn()
	{
		dbAdapt = DatabaseAdapter.getDatabaseAdapter();
		petdb.config.QueryCounter.created++;
		try {
			dbConn = dbAdapt.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorMsg = "SQLException:" + e.getMessage();
		}
		return 0;
	}
	final public int closeQuery()
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
			
				petdb.config.QueryCounter.deleted++;
				//dbAdapt.closeConnection(dbConn); /* Do not close connection it is still used by main UI program */
			
		}
		}
		} catch (Exception e) {return -1; } 
		return 0;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}