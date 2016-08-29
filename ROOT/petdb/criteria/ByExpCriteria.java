
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;

public class ByExpCriteria extends CompositeCriteria 
{

	public static String EXPIDs  = "0";
	public static String EXPNUMs  = "NUMs";
	public static String EXPNAMEs  = "NAMEs";
	String type = EXPNUMs;

	public ByExpCriteria(String type) 
	{
		this.type = type;	
		parameters = new Hashtable();
		qryModel = new ByExpQryModel();
		subCriteria = new ByExp2Criteria();
	}

	public String getType() { return type;}
	public void setType(String t) { type = t;}

        public Wrapper getWrapper()
        {
                if (dataWrapper == null)
                        dataWrapper = new ByExpWrapper(subCriteria);

                else ((ByExpWrapper)dataWrapper).update(subCriteria);
                return dataWrapper;
        }

	public String getDescription()
	{
		return super.getDescription(EXPIDs);

	}

}

class ByExpQryModel extends QueryModel
{

        public String getQueryStr(Criteria criteria)
        {

        	String query = "";

		if ( ((ByExpCriteria)criteria).getType().equals(ByExpCriteria.EXPNUMs))
		{

			query += "SELECT DISTINCT s.sample_num FROM sample s, station ss"
                		+ " WHERE s.station_num = ss.station_num "
				+ " AND ss.expedition_num IN " + criteria.getValuesAsStr(ByExpCriteria.EXPIDs,true);
		} else { 
			query += "SELECT DISTINCT s.sample_num FROM sample s, station ss, expedition e"
                		+ " WHERE s.station_num = ss.station_num "
				+ " AND ss.expedition_num = e.expedition_num "
				+ " AND e.expedition_name IN " + criteria.getValuesAsStr(ByExpCriteria.EXPIDs,false);
       		}
		 return query;
        }
        
	public String getQueryStr(Criteria criteria, String filter)
        {
	String query = getQueryStr(criteria);
		query += " AND s.sample_num in " + filter;
	return query;
	}

}

