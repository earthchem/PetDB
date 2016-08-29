
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class SampleInfo3DCtlQuery extends NonPersistentDynamicCtlQuery 
{

	public SampleInfo3DCtlQuery(String filter)
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
		qry = "select distinct"
			+ " decode(b.material_num,8,3,7,3,b.material_num)||"
			+ "it.item_type_code, im.item_code,itl.display_order"
			+ " from batch b, analysis an, chemistry c, item_measured im,"
			+ " itemtype_list itl, item_type it"
			+ " where it.item_type_num = itl.item_type_num"
			+ " and itl.itemtypelist_num = c.itemtypelist_num"
			+ " and im.item_measured_num = c.item_measured_num"
			+ " and c.analysis_num = an.analysis_num"
			+ " and an.batch_num = b.batch_num"
			+ " and b.sample_num = " + v_filter
			+ " order by "
			+ "decode(b.material_num,8,3,7,3,b.material_num)||it.item_type_code,itl.display_order";
		//DataDCtlQuery.writeQueryToFile(qry,"C:\\Users\\Lulin Song\\Downloads\\sampleinfo3d_qry.txt");
		return 1;
	} 	  
}
