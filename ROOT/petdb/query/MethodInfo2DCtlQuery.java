
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class MethodInfo2DCtlQuery extends NonPersistentDynamicCtlQuery 
{
	public MethodInfo2DCtlQuery(String filter)
	{
                super(filter);
        }


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new MethodInfo2DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "select it.item_code, mp.precision_type, mp.precision_min, mp.precision_max"
			+ " from item_measured it, method_precis mp"
			+ " where it.item_measured_num =  mp.item_measured_num"
			+ " and mp.data_quality_num =" + v_filter
			+ " order by it.item_code";
		return 1;
	} 	  

}
