
package petdb.criteria;

import java.util.*;

import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;


public class ByGeoCriteria extends Criteria 
{

	public static String LOCATION = "0";

	public ByGeoCriteria() 
	{
		parameters = new Hashtable();
		qryModel = new ByGeoQryModel();
		dataWrapper = WrapperCollection.get(WrapperCollection.ByGeoWrapper);
		//new ByGeoWrapper();
	}
 
        public String getDescription()
        {
		String str = "";
		String[] v = (String[])parameters.get(LOCATION);
		if (v != null)
			for (int i = 0; i< v.length; i++)
				if (i ==0)
			 		str += v[i];
				else 	str += ", " + v[i];
		    str =str.replaceAll("''","'");
            return  str;
        }


}

class ByGeoQryModel extends QueryModel
{

        public String getQueryStr(Criteria criteria)
        {

                String query =  " SELECT distinct s.sample_num from sample s "
                + " WHERE  s.station_num in "
                + " (SELECT DISTINCT sl.STATION_NUM "
                + "     FROM STATION_BY_LOCATION sl, LOCATION l, GEOGRAPH_LOC gl "
                + "     WHERE sl.LOCATION_NUM=l.LOCATION_NUM "
                + "     AND l.LOCATION_NUM=gl.LOCATION_NUM "
                + "     AND CONCAT(CONCAT(gl.location_type,':'),gl.location_name) "
                + "     IN " + criteria.getValuesAsStr(ByGeoCriteria.LOCATION)
                + " )";
        return query;
        }

        public String getQueryStr(Criteria criteria, String filter)
        {

	String query =  getQueryStr(criteria);
		query += " AND s.sample_num in " + filter;
	return query;
	}


}
