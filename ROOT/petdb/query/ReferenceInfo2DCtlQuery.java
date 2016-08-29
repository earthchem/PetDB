/*$Id:*/
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class ReferenceInfo2DCtlQuery extends NonPersistentDynamicCtlQuery 
{
	public ReferenceInfo2DCtlQuery(String filter)
        {
                super(filter);
        }


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new ReferenceInfo2DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "select distinct tir.table_title, tir.table_in_ref, tir.table_in_ref_num,"
			+ " count( distinct b.batch_num), count(distinct c.item_measured_num)"
			+ " from  chemistry c, analysis an, batch b, table_in_ref tir"
			+ " where c.analysis_num = an.analysis_num"
			+ " and an.batch_num = b.batch_num"
			+ " and b.table_in_ref_num = tir.table_in_ref_num"
			+ " and tir.ref_num =" + v_filter
			+ " group by tir.table_title, tir.table_in_ref, tir.table_in_ref_num"
			+ " order by tir.table_in_ref";
//		DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\Lulin Song\\Downloads\\refino2dqry.txt");
		return 1;
	} 	  
}
