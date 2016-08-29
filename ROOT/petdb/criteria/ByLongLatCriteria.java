
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;

public class ByLongLatCriteria extends Criteria 
{

	public static String L_SOUTH 	= "L_SOUTH";
	public static String L_NORTH	= "L_NORTH";
	public static String L_WEST	= "L_WEST";
	public static String L_EAST	= "L_EAST";
	public static String D_TOP	= "D_TOP";
	public static String D_BOTTOM	= "D_BOTTOM";

	public ByLongLatCriteria() 
	{
		parameters = new Hashtable();
		qryModel = new ByLongLatQryModel();
	}

        public String getDescription()
        {
                return "NORTH: " + super.getDescription(L_NORTH)
                + " SOUTH: " + super.getDescription(L_SOUTH)
                + " WEST: " + super.getDescription(L_WEST)
                + " EAST: " + super.getDescription(L_EAST)
                + " TOP: " + super.getDescription(D_TOP)
                + " BOTTOM: " + super.getDescription(D_BOTTOM);

        }


}

class ByLongLatQryModel extends QueryModel
{

        public String getQueryStr(Criteria criteria)
        {

                String query =  " SELECT s.sample_num FROM sample s "
                        + " WHERE s.station_num IN "
                        + " (SELECT sbl.station_num "
                        + " FROM station_by_location sbl, location l "
                        + " WHERE  sbl.location_num =l.location_num ";
                
		if (criteria.isSet(ByLongLatCriteria.D_BOTTOM))
                  query+= " AND l.elevation_min >= " + criteria.getValueAsStr(ByLongLatCriteria.D_BOTTOM);

                if (criteria.isSet(ByLongLatCriteria.D_TOP))
                  query+= " AND l.elevation_max <= " + criteria.getValueAsStr(ByLongLatCriteria.D_TOP);

                if (criteria.isSet(ByLongLatCriteria.L_EAST))
                  query+= " AND l.longitude  <= " + criteria.getValueAsStr(ByLongLatCriteria.L_EAST);

                if (criteria.isSet(ByLongLatCriteria.L_WEST))
                  query+= " AND l.longitude  >= " + criteria.getValueAsStr(ByLongLatCriteria.L_WEST);

                if (criteria.isSet(ByLongLatCriteria.L_SOUTH))
                  query+= " AND l.latitude >= " + criteria.getValueAsStr(ByLongLatCriteria.L_SOUTH);

                if (criteria.isSet(ByLongLatCriteria.L_NORTH))
                  query+= " AND l.latitude <= " + criteria.getValueAsStr(ByLongLatCriteria.L_NORTH);

                  query += " )";

        	return query;
        }

	public String getQueryStr(Criteria criteria, String filter)
	{
		String query = getQueryStr(criteria);
		query += " AND s.sample_num in " + filter;
		return query;
	}

}

