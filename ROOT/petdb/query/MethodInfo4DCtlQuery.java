
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class MethodInfo4DCtlQuery extends NonPersistentDynamicCtlQuery 
{
	public MethodInfo4DCtlQuery(String filter)
	{
                super(filter);
        }


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new MethodInfo4DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "select it.item_code, n.norm_standard_name, n.norm_value"
			+ " from item_measured it, normalization n, normalization_list nl"
			+ " where it.item_measured_num = n.item_measured_num"
			+ " and n.normalization_num = nl.normalization_num"
			+ " and nl.data_quality_num=" + v_filter
			+ " order by it.item_code";
		return 1;
	} 	  

}
