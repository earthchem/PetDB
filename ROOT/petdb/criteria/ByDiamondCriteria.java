
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;

public class ByDiamondCriteria extends Criteria {

	public ByDiamondCriteria() 
	{
		parameters = new Hashtable();
		qryModel = new ByDiamondQryModel();
	}
 


        public String getDescription()
        {
            return "";
        }

}

class ByDiamondQryModel extends QueryModel
{

        public String getQueryStr(Criteria criteria)
        {
            String query = "select  s.SAMPLE_NUM from MINERAL m, batch b, sample s, table_in_ref t, reference r "+
            "where m.BATCH_NUM = b.BATCH_NUM and b.SAMPLE_NUM = s.SAMPLE_NUM and m.MINERAL_NUM = 151 "+
            "and b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and t.REF_NUM = r.REF_NUM and r.status = 'COMPLETED'";
            return query;
        }
      
         public String getQueryStr(Criteria criteria, String filter)
        {
            String query = getQueryStr(criteria);
            query += " AND s.sample_num in " + filter;
            return query;
	}

}

