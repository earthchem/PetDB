
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class MethodInfo1DCtlQuery extends NonPersistentDynamicCtlQuery 
{
	public MethodInfo1DCtlQuery(String filter)
	{
                super(filter);
        }


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new MethodInfo1DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "select m.method_code, p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year), r.ref_num,"
			+ " i.institution, m.method_name,  dq.method_comment"
			+ " from reference r, person p, author_list al,"
			+ " institution I, method m, data_quality dq"
			+ " where m.method_num = dq.method_num"
			+ " and i.institution_num = dq.institution_num"
			+ " and p.person_num(+) = al.person_num"
			+ " and (al.ref_num(+) = r.ref_num and al.author_order = 1)"
			+ " and (r.ref_num = dq.ref_num and r.status = 'COMPLETED')"
			+ " and dq.data_quality_num = " + v_filter;
		return 1;
	} 	  

}
