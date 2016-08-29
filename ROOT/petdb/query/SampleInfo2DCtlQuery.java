
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class SampleInfo2DCtlQuery extends NonPersistentDynamicCtlQuery 
{

	public SampleInfo2DCtlQuery(String filter)
        {
                super(filter);
        }

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new SampleInfo2DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{

		qry = "select distinct pp.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year), r.ref_num, sc.alias, "
			+ " " + DisplayConfigurator.DataExistence_Mask.replace(DisplayConfigurator.Location_Abrv,'p')
			+ " from person pp, reference r, author_list al, sample_comment sc,"
			+ " (select distinct tir.ref_num ref_num,"
			+ " " + DisplayConfigurator.DataType_Mask.replace(DisplayConfigurator.Location_Abrv,'b') 
			+ " from table_in_ref tir, batch b"
			+ " where tir.table_in_ref_num = b.table_in_ref_num"
			+ " and b.sample_num = " +  v_filter + ") p" 
			+ " where pp.person_num(+) = al.person_num"
			+ " and (al.author_order = 1 and al.ref_num(+) = r.ref_num)"
			+ " and (r.ref_num = sc.ref_num and r.status = 'COMPLETED')"
			+ " and sc.ref_num = p.ref_num"
			+ " and sc.sample_num =" + v_filter
			+ " group by pp.last_name||', '||r.pub_year, r.ref_num, sc.alias"
			+ " order by r.ref_num";
		return 1;
	} 	  
}
