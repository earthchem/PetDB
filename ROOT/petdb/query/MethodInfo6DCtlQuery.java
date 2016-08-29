
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class MethodInfo6DCtlQuery extends NonPersistentDynamicCtlQuery 
{
	public MethodInfo6DCtlQuery(String filter)
	{
                super(filter);
        }


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet)  new MethodInfo6DS(rs);	
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "select i.ITEM_CODE, i2.ITEM_CODE, F.FCORR_STANDARD_NAME, F.FCORR_VALUE "+
				" from FRACT_CORRECT F, DATA_QUALITY d, ITEM_MEASURED i,ITEM_MEASURED i2, FRACT_CORRECT_LIST FL "+
				" where FL.data_quality_num = d.DATA_QUALITY_NUM AND F.FRACT_CORRECT_NUM = FL.FRACT_CORRECT_NUM "+
				" and f.ITEM_FCORR_NUM = i.ITEM_MEASURED_NUM and f.ITEM_MEASURED_NUM =i2.ITEM_MEASURED_NUM and d.DATA_QUALITY_NUM = "+ v_filter +
				" order by i.item_code "; 
		return 1;
	} 	  

}
