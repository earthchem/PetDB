
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class AnalysisInfo1DCtlQuery extends NonPersistentDynamicCtlQuery 
{
	
	public AnalysisInfo1DCtlQuery(String filter)
	{
		super(filter);
	}

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new AnalysisInfo1DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}


	protected int setFilter()
	{
		qry = "select distinct an.analysis_num, s.sample_id, s.sample_num,"
			+ "  p.last_name||', '||p.first_name||'('||r.pub_year||')',"
			+ " r.ref_num, m.method_code, dq.data_quality_num, mt.material_name"
			+ " from method m, material mt, data_quality dq, batch b, sample s, reference r, author_list al,"
			+ " person p,analysis an"
			+ " where p.person_num(+) = al.person_num"
			+ " and (al.ref_num (+)= r.ref_num and al.author_order = 1)"
			+ " and (r.ref_num = dq.ref_num and r.status = 'COMPLETED')"
			+ " and m.method_num = dq.method_num"
			+ " and dq.data_quality_num = an.data_quality_num"
			+ " and s.sample_num = b.sample_num"
			+ " and mt.material_num = b.material_num"
			+ " and b.batch_num = an.batch_num"
			+ " and an.analysis_num =" + v_filter;

		return 1;
	} 	  

}
