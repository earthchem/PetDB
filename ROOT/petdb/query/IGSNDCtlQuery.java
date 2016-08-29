package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class IGSNDCtlQuery extends NonPersistentDynamicCtlQuery 
{
	public IGSNDCtlQuery(String filter)
        {
                super(filter);
        }

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new IGSNInfo1DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "Select  igsn.igsn, igsn.sample_num, igsn.station_num"
			+ " from sample s, igsn_info igsn"
			+ " where igsn.sample_num = " + v_filter;  
		
		//System.out.println("qry="+qry);
		return 1;
	} 	  
}

