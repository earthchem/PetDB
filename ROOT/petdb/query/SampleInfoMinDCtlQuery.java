
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class SampleInfoMinDCtlQuery extends NonPersistentDynamicCtlQuery 
{

	public SampleInfoMinDCtlQuery(String filter)
        {
                super(filter);
        }

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new SampleInfoAnalysisDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{

		qry = " select distinct an.analysis_num, p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year)"
			+ " , r.ref_num,"
			+ " sc.alias, m.method_code,dq.data_quality_num,"
			+ " ml.mineral_code||decode(nvl(min.spot_id,''), '','',' spot '||min.spot_id),"
			+ " it.item_type_code, im.item_code, c.value_meas, an.analysis_comment"
			+ " from person p, author_list al, reference r, method m, mineral min, mineral_list ml, data_quality dq,"
			+ " sample_comment sc, analysis an, chemistry c, item_type it,"
			+ " itemtype_list itl, item_measured im, batch b"
			+ " where p.person_num = al.person_num"
			+ " and (al.ref_num = r.ref_num and al.author_order = 1)"
			+ " and (r.ref_num = dq.ref_num and r.status = 'COMPLETED')"
			+ " and sc.ref_num = dq.ref_num"
			+ " and m.method_num = dq.method_num"
			+ " and dq.data_quality_num = an.data_quality_num"
			+ " and sc.sample_num(+) = b.sample_num"
			+ " and it.item_type_num = itl.item_type_num"
			+ " and itl.itemtypelist_num = c.itemtypelist_num"
			+ " and im.item_measured_num = c.item_measured_num"
			+ " and c.analysis_num = an.analysis_num"
			+ " and an.batch_num = b.batch_num"
			+ " and ml.mineral_num = min.mineral_num"
			+ " and min.batch_num = b.batch_num"
			+ " and b.sample_num =" + v_filter
			+ " and b.material_num =6"
			+ " order by an.analysis_num, p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year), sc.alias,"
			+ " m.method_code, dq.data_quality_num,"
			+ " ml.mineral_code||decode(nvl(min.spot_id,''), '','',' spot '||min.spot_id), it.item_type_code";  
          //  DataDCtlQuery.writeQueryToFile(qry,"C:\\Users\\bhchen\\Downloads\\SampleInfoMinDCtlQuery.txt");
		return 1;
	} 	  
}
