
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;
import petdb.config.*;

public class ByDataVersionCriteria extends Criteria {

	public static String Version = "0";

	public ByDataVersionCriteria() 
	{
		parameters = new Hashtable();
		qryModel = new ByDataVersionQryModel();
		//subCriteria = new ByPub2Criteria();
	}
 


        public Wrapper getWrapper()
        {
                if (dataWrapper == null)
                        dataWrapper = WrapperCollection.get(WrapperCollection.DateEnteredWrapper);
                // else ((DateEnteredWrapper)dataWrapper).update();
                return dataWrapper;
        }
 
        public String getDescription()
        {
                return super.getDescription(Version);

        }


}

class ByDataVersionQryModel extends QueryModel
{

        public String getQueryStr(Criteria criteria)
        {

        String query =  "SELECT b.sample_num FROM batch b, table_in_ref t,"
		+ " reference r"
                + " WHERE  b.table_in_ref_num = t.table_in_ref_num "
		+ " AND t.ref_num = r.ref_num "
		+ " AND to_char(r.data_entered_date,'"
                + DisplayConfigurator.DateEnteredFormat + "') >= '"
		+ criteria.getValueAsStr(ByDataVersionCriteria.Version) + "'"
		+ " AND r.status = 'COMPLETED'";
        return query;
        }

        public String getQueryStr(Criteria criteria, String filter)
        {
        String query =  "SELECT b.sample_num FROM batch b, table_in_ref t"
                + " WHERE  b.table_in_ref_num = t.table_in_ref_num "
		+ " AND t.ref_num in  " +  criteria.getValuesAsStr(ByDataVersionCriteria.Version)
		+ " AND b.sample_num in " + filter;
	return query;
	}
}

