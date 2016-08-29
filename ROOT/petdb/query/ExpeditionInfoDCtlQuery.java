
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class ExpeditionInfoDCtlQuery extends NonPersistentDynamicCtlQuery 
{

	public ExpeditionInfoDCtlQuery(String filter)
        {
                super(filter);
        }


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new ExpeditionInfoDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "select distinct e.expedition_name||decode(nvl(e.leg, '-'), '-', ' ','-'||e.leg) name_leg,"
			+ " e.expedition_num, sh.ship_name,"
			+ " e.exp_year_from ||"
			+ " decode(nvl(e.exp_year_to, 0),0,' ',decode(e.exp_year_to,e.exp_year_from,' ','-'||e.exp_year_to)) year,"
			+ " ch.person_num, p.last_name ||decode(nvl(p.first_name, '-'), '-', ' ','-'||p.first_name) chief,"
 			+ " i.institution_num,  i.institution institution, ea.appcode, ea.ea_expcode "
			+ " from person p, chief_scientist ch, institution i, ship sh, expedition e, ea_expedition ea "
			+ " where p.person_num(+) = ch.person_num"
			+ " and  ch.expedition_num(+) = e.expedition_num"
			+ " and  i.institution_num(+) = e.institution_num"
			+ " and sh.ship_num (+) = e.ship_num"
			+ " and ea.expedition_num (+) =  e.expedition_num" 
			+ " and e.expedition_num =" + v_filter;
		return 1;
	} 	  
}
