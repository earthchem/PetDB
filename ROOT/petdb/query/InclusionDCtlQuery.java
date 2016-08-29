package petdb.query;

import java.sql.*;
import java.util.*;
import java.io.*;

import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class InclusionDCtlQuery extends DynamicCtlQuery 
{
	public static String Inclusion = "Inclusion"; //FIXME only care about melt and fluid for now
	public static String Melt = "Melt";
	public static String Mineral = "Mineral";
	public static String Fluid = "Fluid";
 	
	private String type = Mineral;

	public InclusionDCtlQuery(String filter, String type)
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
            + " and inc.inclusion_type <> 'VESICLE'"
			+ " and (" + DisplayConfigurator.toReplace(v_filter,'b') + ")"
			+ " order by ml.mineral_code";
			
			return 1;
		}
		else if (type.equals(Mineral))
		{
			qry = "select distinct ml.mineral_num, ml.mineral_code"
			+ " from mineral_list ml, mineral m, batch b"
			+ " where ml.mineral_num = m.mineral_num"
		//	+ " and m.batch_num (+)  = b.batch_num"  //FIXME: Why? It is Commented out by Lulin
			+ " and (" + DisplayConfigurator.toReplace(v_filter,'b') + ")"
			+ " order by ml.mineral_code";
		} 
		else if (type.equals(Fluid))
		{
			qry = "select distinct ml.mineral_num, ml.mineral_code"
			+ " from mineral_list ml, inclusion inc, batch b"
			+ " where ml.mineral_num = inc.mineral_num"
			+ " and inc.inclusion_type in ('FLUID','FL','FLU') "
	//		+ " and inc.batch_num (+)  = b.batch_num"  //FIXME: Why? It is Commented out by Lulin
			+ " and (" + DisplayConfigurator.toReplace(v_filter,'b') + ")"
			+ " order by ml.mineral_code";
			return 1;
		}
		else if (type.equals(Melt))
		{
			qry = "select distinct ml.mineral_num, ml.mineral_code"
			+ " from mineral_list ml, inclusion inc, batch b"
			+ " where ml.mineral_num = inc.mineral_num"
			+ " and inc.inclusion_type in ('GL', 'GLASS')"
		//	+ " and inc.batch_num (+)  = b.batch_num"  //FIXME: Why? It is Commented out by Lulin
			+ " and (" + DisplayConfigurator.toReplace(v_filter,'b') + ")"
			+ " order by ml.mineral_code";
			return 1;
		}

		return 0;
	} 	  
}
