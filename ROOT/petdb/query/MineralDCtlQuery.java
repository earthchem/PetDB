
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class MineralDCtlQuery extends DynamicCtlQuery 
{
	public static String Inclusion = "Inclusion";
	public static String Mineral = "Mineral";
	public static String Host = "Host Mineral";
	
	private String type = Mineral;

	public MineralDCtlQuery(String filter, String type)
	{
		super();
                v_filter = filter;
                this.type = type;
                int i = setFilter();
	}

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new AValuePerKeyDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		if (type.equals(Inclusion))
		{
			qry = "select distinct ml.mineral_num, ml.mineral_code"
			+ " from mineral_list ml, inclusion inc, batch b"
			+ " where ml.mineral_num = inc.mineral_num"
			+ " and inc.inclusion_type is not null"
			+ " and inc.batch_num (+)  = b.batch_num"
			+ " and (" + DisplayConfigurator.toReplace(v_filter,'b') + ")"
			+ " order by ml.mineral_code";
			
			return 1;
		}
		else if (type.equals(Mineral))
		{
			qry = "select distinct ml.mineral_num, ml.mineral_code"
			+ " from mineral_list ml, mineral m, batch b"
			+ " where ml.mineral_num = m.mineral_num"
			+ " and m.batch_num (+)  = b.batch_num"
			+ " and (" + DisplayConfigurator.toReplace(v_filter,'b') + ")"
			+ " order by ml.mineral_code";
		
			return 1;
		}
		else //type is 'Host Mineral' etc.
		{
			qry = "select distinct ml.mineral_num, ml.mineral_code"
                	+ " from mineral_list ml, inclusion inc, batch b"
                	+ " where ml.mineral_num = inc.host_mineral_num"
               		+ " and inc.inclusion_type = 'GL'"
                	+ " and inc.batch_num (+)  = b.batch_num"
                	+ " and (" + DisplayConfigurator.toReplace(v_filter,'b') + ")"
                	+ " order by ml.mineral_code";
 
			return 1;
		}
	} 	  
}
