
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;

public class ByXenolithCriteria extends Criteria {

	public ByXenolithCriteria() 
	{
		parameters = new Hashtable();
		qryModel = new ByXenolithQryModel();
	}
 


        public String getDescription()
        {
            return "";
        }

}

class ByXenolithQryModel extends QueryModel
{

        public String getQueryStr(Criteria criteria)
        {
            String query = " select distinct s.SAMPLE_NUM from batch b, sample_comment s, table_in_ref t, reference r, rockclass rc "+
            " where b.SAMPLE_NUM = s.SAMPLE_NUM and s.rockclass_num = rc.rockclass_num and rc.rocktype_num = 37 and rc.ROCKCLASS_num in (157,122,142,171,158,172,170,144) "+
            " and b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and t.REF_NUM = r.REF_NUM and r.status = 'COMPLETED'";
            return query;
        }
      
         public String getQueryStr(Criteria criteria, String filter)
        {
            String query = getQueryStr(criteria);
            query += " AND s.sample_num in " + filter;
            return query;
	}

}

