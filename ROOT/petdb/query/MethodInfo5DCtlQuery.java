
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class MethodInfo5DCtlQuery extends NonPersistentDynamicCtlQuery 
{
	public MethodInfo5DCtlQuery(String filter)
	{
                super(filter);
        }


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new OrderedChemicals(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "select distinct it.item_type_code, im.item_code, it.display_order, itl.display_order"
			+ " from chemistry c, item_measured im, itemtype_list itl, item_type it, analysis an"
			+ " where it.item_type_num = itl.item_type_num"
			+ " and itl.itemtypelist_num = c.itemtypelist_num"
			+ " and im.item_measured_num = c.item_measured_num"
			+ " and c.analysis_num = an.analysis_num"
			+ " and an.data_quality_num =" + v_filter
			+ " order by it.display_order, itl.display_order"; 
		return 1;
	} 	  

}
