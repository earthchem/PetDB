
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class TblInRefInfo1DCtlQuery extends NonPersistentDynamicCtlQuery 
{

	public TblInRefInfo1DCtlQuery(String filter)
	{
		super(filter);
	}
	
	public TblInRefInfo1DCtlQuery(String filter, String ageCondition)
	{
		super(filter, ageCondition);
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
		qry = "select distinct it.item_type_code,im.item_code,itl.display_order "
			+ " from  batch b, analysis an, chemistry c,"
			+ " item_measured im, itemtype_list itl, item_type it"
			+ " where "
			+ " it.item_type_num = itl.item_type_num"
			+ " and itl.itemtypelist_num = c.itemtypelist_num"
			+ " and im.item_measured_num = c.item_measured_num"
			+ " and c.analysis_num = an.analysis_num"
			+ " and an.batch_num = b.batch_num"
			+ " and b.table_in_ref_num = " + v_filter + ageCondition
			+ " order by it.item_type_code, itl.display_order";
		//DataDCtlQuery.writeQueryToFile(qry,"C:\\Users\\bhchen\\Downloads\\table_ref_1_qry.txt");
		return 1;
	} 	  
}
