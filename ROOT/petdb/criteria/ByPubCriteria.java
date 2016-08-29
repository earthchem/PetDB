
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;

public class ByPubCriteria extends CompositeCriteria {

	public static String REFIDs= "0";
    public static String REFIDs2= "1";
    public static String REFIDs3= "2";

	public ByPubCriteria() 
	{
		parameters = new Hashtable();
		qryModel = new ByPubQryModel();
		subCriteria = new ByPub2Criteria();
	}
 


        public Wrapper getWrapper()
        {
                if (dataWrapper == null) {
                        dataWrapper = new ByPubWrapper(subCriteria);
                }

                else {
                    ((ByPubWrapper)dataWrapper).update(subCriteria);
                }
                return dataWrapper;
        }
 
        public String getDescription()
        {
                return super.getDescription(REFIDs);

        }



}

class ByPubQryModel extends QueryModel
{

        public String getQueryStr(Criteria criteria)
        {

        String query =  "SELECT b.sample_num FROM batch b, table_in_ref t"
                + " WHERE  b.table_in_ref_num = t.table_in_ref_num "
		+ " AND t.ref_num in  " +  criteria.getValuesAsStr(ByPubCriteria.REFIDs);
        return query;
        }

        public String getQueryStr(Criteria criteria, String filter)
        {
        String query =  "SELECT b.sample_num FROM batch b, table_in_ref t"
                + " WHERE  b.table_in_ref_num = t.table_in_ref_num "
		+ " AND t.ref_num in  " +  criteria.getValuesAsStr(ByPubCriteria.REFIDs)
		+ " AND b.sample_num in " + filter;
	return query;
	}
}

