
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class RockModeDCtlQuery extends DynamicCtlQuery 
{

	public RockModeDCtlQuery(String filter)
	{
		super(filter);
	}

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new RockModeDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
	qry = "select a.rock_mode_num, sc.alias, s.sample_id, "
		+ " ml.mineral_code, rm.volume, "
		+ " p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year), r.ref_num " 
		+ " from sample s, sample_comment sc, rock_mode_analysis a,"
		+ " rock_mode rm, mineral_list ml, reference r, author_list al, person p"
		+ " where p.person_num(+) = al.person_num" 
		+ " and (al.ref_num(+) = r.ref_num and al.author_order =1)" 
		+ " and r.ref_num(+) = a.ref_num"
		+ " and rm.rock_mode_num = a.rock_mode_num"
		+ " and ml.mineral_num = rm.mineral_num"
		+ " and s.sample_num = a.sample_num"
		+ " and sc.sample_num = a.sample_num"
		+ " and (" + DisplayConfigurator.toReplace(v_filter,'a')+ ")"
		+ " order by s.sample_id, a.rock_mode_num";
		return 1;
	} 	  

}
