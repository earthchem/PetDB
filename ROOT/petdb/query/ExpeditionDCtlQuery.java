
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class ExpeditionDCtlQuery extends NonPersistentDynamicCtlQuery 
{

	String s_filter = null;
        
	public ExpeditionDCtlQuery(String filter)
        {
                super(filter);
		s_filter = null;
        }

        public ExpeditionDCtlQuery(String filter, String s_filter)
        {
                super();
                v_filter = filter;
                this.s_filter = s_filter;
                int i = setFilter();
		
        }

        public ExpeditionDCtlQuery(Criteria c)
        {
                super(c);
        }

	public int updateData(String str, String str2)
        {
                changed = true;
                v_filter = str;
                s_filter = str2;
                return setFilter();
        }

	public synchronized void prepareData() 
	{
		try {
			//if (criteria == null)
				ds = (DataSet) new ExpeditionDS(rs);
			//else 
				//ds = (DataSet) new ExpRecordDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
	qry = "select distinct e.expedition_num,"
		+ " e.expedition_name||decode(nvl(e.leg, '-'), '-', ' ','-'||e.leg) name_leg," 
 		+ " sh.ship_num, sh.ship_name ship,"
		+ " e.exp_year_from ||"
		+ " decode(nvl(e.exp_year_to, 0),0,' ',decode(e.exp_year_to,e.exp_year_from,' ','-'||e.exp_year_to)) year,"
 		+ " ch.person_num,"
		+ " p.last_name||decode(nvl(p.first_name, '-'), '-', ' ',', '||p.first_name) chief," 
 		+ " i.institution_num,  i.institution institution"
 		+ " from person p, chief_scientist ch, institution i, ship sh, expedition e";
	if (v_filter.length() != 0)
 		qry += ", station st, sample s";
	
	if (s_filter != null)
		qry +=", " + v_filter;
 		
	qry+= " where p.person_num(+) = ch.person_num"
 		+ " and  ch.expedition_num(+) = e.expedition_num"
 		+ " and  i.institution_num(+) = e.institution_num" 
		+ " and sh.ship_num (+) = e.ship_num";
	
	if (v_filter.length() != 0)
		qry +=" and e.expedition_num = st.expedition_num"
		+ " and st.station_num = s.station_num";

	if (s_filter != null)
		qry+= " and s.sample_num = prepared.sample_num "
		 + " and " + s_filter;
	else
		if (v_filter.length() != 0)
			qry+= " and  (" + DisplayConfigurator.toReplace(v_filter,'s')+ ")";
		
	qry += " order by name_leg"; 
		return 1;
	} 	  

}
