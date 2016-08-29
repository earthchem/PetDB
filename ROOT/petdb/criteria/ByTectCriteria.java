
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;

public class ByTectCriteria extends Criteria {

	public static String TECTONIC = "0";

	public ByTectCriteria() 
	{
		parameters = new Hashtable();
		qryModel = new ByTectQryModel();
		dataWrapper =WrapperCollection.get(WrapperCollection.ByTectWrapper);
	}
 


        public String getDescription()
        {
                return super.getDescription(TECTONIC);

        }



}

class ByTectQryModel extends QueryModel
{

        public String getQueryStr(Criteria criteria)
        {

        String query =  "SELECT s.sample_num FROM sample s"
                + " WHERE s.station_num IN "
                + " ( SELECT sbl.station_num "
                + " FROM station_by_location sbl, location l "
                + " WHERE sbl.location_num =l.location_num "
                + " AND l.tectonic_setting_num IN " + criteria.getValuesAsStr(ByTectCriteria.TECTONIC,true)
                + " ) ";
        return query;
        }
        public String getQueryStr(Criteria criteria, String filter)
        {
	String query = getQueryStr(criteria);
		query += " AND s.sample_num in " + filter;
		return query;
	}

}

