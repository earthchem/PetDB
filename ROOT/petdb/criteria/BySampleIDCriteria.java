
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;

public class BySampleIDCriteria extends Criteria {

	public static String SampleID = "0";
	public static String INCLUDE = "include";
	public static String EXCLUDE = "exclude";

	public BySampleIDCriteria(String flt) 
	{
		parameters = new Hashtable();
		qryModel = new BySampleIDQryModel();
		dataWrapper = new BySampleIDWrapper(flt,SampleIDDCtlQuery.View);
	}
 


        public String getDescription()
        {
                return super.getDescription(INCLUDE);

        }



}

class BySampleIDQryModel extends QueryModel
{

        public String getQueryStr(Criteria criteria)
        {

        String query = " and sample.sample_num ";
	if (criteria.isSet(BySampleIDCriteria.INCLUDE))
		query = " " + criteria.getValuesAsStr(BySampleIDCriteria.INCLUDE,true) + " ";
	else if (criteria.isSet(BySampleIDCriteria.EXCLUDE))
		query += " NOT IN " + criteria.getValuesAsStr(BySampleIDCriteria.EXCLUDE,true) + " ";
        return query;
        }
        public String getQueryStr(Criteria criteria, String filter)
        {
	String query = getQueryStr(criteria);
		query += " AND s.sample_num in " + filter;
		return query;
	}

}

