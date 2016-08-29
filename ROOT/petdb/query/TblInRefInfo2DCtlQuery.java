
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class TblInRefInfo2DCtlQuery extends NonPersistentDynamicCtlQuery 
{

	public TblInRefInfo2DCtlQuery(String filter) 
	{
		super(filter);
	}
    
    public TblInRefInfo2DCtlQuery(String filter, String ageCondition)
	{
		super(filter, ageCondition);
	}
    
    
	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new TblInRefInfo2DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
          
		qry = "select distinct b.batch_num, sc.alias, s.sample_id, m.material_name,"
			+ " min_ml.mineral_code,inc_ml.mineral_code,"
			+ " it.item_type_code, im.item_code, c.value_meas,itl.display_order, igsn.igsn, MT.METHOD_CODE, c.unit "
			+ " from  sample s, sample_comment sc, analysis an, material m, chemistry c,"
			+ " mineral_list inc_ml, mineral_list min_ml, mineral min, inclusion inc, table_in_ref tr,"
			+ " item_type it, itemtype_list itl, item_measured im, batch b, igsn_info igsn, METHOD MT, DATA_QUALITY DQ"
			+ " where "
			+ " min.batch_num (+) 	= b.batch_num"
			+ " and inc.batch_num(+) 	= b.batch_num"
			+ " and min_ml.mineral_num(+) 	=  min.mineral_num"
			+ " and inc_ml.mineral_num (+)	= inc.mineral_num"
			+ " and sc.sample_num(+) 	= b.sample_num"
			+ " and s.sample_num 		= b.sample_num"
			+ " and s.sample_num 		= igsn.sample_num (+) "
			+ " and it.item_type_num 	= itl.item_type_num"
			+ " and itl.itemtypelist_num 	= c.itemtypelist_num"
			+ " and im.item_measured_num 	= c.item_measured_num"
			+ " and c.analysis_num 		= an.analysis_num"
			+ " and an.batch_num 		= b.batch_num"
			+ " and m.material_num 		= b.material_num"
            + " AND MT.METHOD_NUM = DQ.METHOD_NUM AND DQ.DATA_QUALITY_NUM =AN.DATA_QUALITY_NUM"
            + " and tr.TABLE_IN_REF_NUM =b.TABLE_IN_REF_NUM and tr.REF_NUM =sc.REF_NUM"
			+ " and b.table_in_ref_num 	= " + v_filter + ageCondition
			+ " order by b.batch_num, s.sample_id, sc.alias,it.item_type_code,MT.METHOD_CODE,itl.display_order";  
            
		//DataDCtlQuery.writeQueryToFile(qry,"C:\\Users\\bhchen\\Downloads\\table_ref_2_qry.txt");
		return 1;
	}
 	  
 	public String getFilter()
	{
		return v_filter;
	}
}

