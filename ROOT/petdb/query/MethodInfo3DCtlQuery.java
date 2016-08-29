
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class MethodInfo3DCtlQuery extends NonPersistentDynamicCtlQuery 
{
	public MethodInfo3DCtlQuery(String filter)
	{
                super(filter);
        }


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new MethodInfo3DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "select it.item_code, s.standard_name, s.standard_value, "
			+ " s.stdev, s.stdev_type, s.unit"
			+ " from item_measured it, standard s"
			+ " where it.item_measured_num = s.item_measured_num"
			+ " and s.data_quality_num =" + v_filter
			+ " order by it.item_code";
		return 1;
	} 	  

}
