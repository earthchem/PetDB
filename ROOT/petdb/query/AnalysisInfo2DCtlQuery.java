
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class AnalysisInfo2DCtlQuery extends NonPersistentDynamicCtlQuery 
{

	public AnalysisInfo2DCtlQuery(String filter)
	{
		super(filter);
	}
	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new AnalysisInfo2DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "select im.item_code, c.value_meas"
			+ " from chemistry c, item_measured im"
			+ " where im.item_measured_num = c.item_measured_num"
			+ " and c.analysis_num =" + v_filter
			+ " order by im.item_code";
		return 1;
	} 	  
}
