
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;


public class ByRockCriteria extends Criteria {

	static public String ROCK_CLASS	= "0";
	static public String METHOD 	= "1";
	static public String ALTERATION = "2";

	public ByRockCriteria() 
	{
		parameters = new Hashtable();
		qryModel = new ByRockQryModel();
		dataWrapper = WrapperCollection.get(WrapperCollection.ByRockWrapper);
	}
 
       public String getDescription()
       {
		String m;
		String a;
                String c = "";
		String str = ""; //CRITERIA";
                String[] v = (String[])parameters.get(ROCK_CLASS);
                
		if (v != null)
                        for (int i = 0; i< v.length; i++)
                                if (i ==0)
					c += dataWrapper.getStrForKey(ROCK_CLASS,v[i]); 
                                else    c += ", " +  dataWrapper.getStrForKey(ROCK_CLASS,v[i]);
		if (c != null) 
			if (c.length() !=0)
				 str += "CLASS: " + c; 
		if ((m = super.getDescription(METHOD)) != null)
			if (m.length() != 0)
				   str += " METHOD: " + m; 
		if ((a = super.getDescription(ALTERATION)) != null)
			if  (a.length() != 0)
				 str += " ALTERATION: " + a; 
		    str =str.replaceAll("''","'");
            return str;
        }

}

class ByRockQryModel extends QueryModel
{

	public String getQueryStr(Criteria criteria)
	{

	String query ="";

	if ( (criteria.isSet(ByRockCriteria.METHOD))
	    && (!criteria.isSet(ByRockCriteria.ROCK_CLASS))
	    && (!criteria.isSet(ByRockCriteria.ALTERATION))
	  )
	{

	 query += " SELECT DISTINCT s.sample_num FROM sample s, station ss "
		+ " WHERE s.station_num = ss.station_num "
		+ " AND ss.samp_technique_num in " + criteria.getValuesAsStr(ByRockCriteria.METHOD);
	 return query;
	}
	else if ( (!criteria.isSet(ByRockCriteria.METHOD))
	    && (criteria.isSet(ByRockCriteria.ROCK_CLASS))
	    && (!criteria.isSet(ByRockCriteria.ALTERATION))
	)
	{
		query += "SELECT DISTINCT st.sample_num FROM sample_Comment st"
			+ " WHERE st.rockclass_num in " + criteria.getValuesAsStr(ByRockCriteria.ROCK_CLASS);
	return query;
	}
	else if ((!criteria.isSet(ByRockCriteria.METHOD))
	    && (!criteria.isSet(ByRockCriteria.ROCK_CLASS))
	    && (criteria.isSet(ByRockCriteria.ALTERATION))
	)
	{
		query += " SELECT DISTINCT st.sample_num FROM sample_Comment st "
			+ " WHERE st.alteration_num in " +  criteria.getValuesAsStr(ByRockCriteria.ALTERATION,true);       
	return query;
	}
	else if ((!criteria.isSet(ByRockCriteria.METHOD))
	    && (criteria.isSet(ByRockCriteria.ROCK_CLASS))
	    && (criteria.isSet(ByRockCriteria.ALTERATION))
	)
	{
	           query += "SELECT DISTINCT st.sample_num FROM sample_Comment st"
                        + " WHERE st.rockclass_num in " +  criteria.getValuesAsStr(ByRockCriteria.ROCK_CLASS)
			+ " AND st.alteration_num in " +  criteria.getValuesAsStr(ByRockCriteria.ALTERATION,true);       
	return query;
        }
	else if ((criteria.isSet(ByRockCriteria.METHOD))
	    && (criteria.isSet(ByRockCriteria.ROCK_CLASS))
	    && (!criteria.isSet(ByRockCriteria.ALTERATION))
	)
	{

		query +=" SELECT DISTINCT st.sample_num "
			+ " FROM sample_Comment st, sample s, station ss "
			+ " WHERE st.sample_num = s.sample_num "
			+ " AND s.station_num = ss.station_num "
			+ " AND st.rockclass_num in " +  criteria.getValuesAsStr(ByRockCriteria.ROCK_CLASS)
			+ " AND ss.samp_technique_num in " + criteria.getValuesAsStr(ByRockCriteria.METHOD);
	return query;
	}
	else if ((criteria.isSet(ByRockCriteria.METHOD))
	    && (!criteria.isSet(ByRockCriteria.ROCK_CLASS))
	    && (criteria.isSet(ByRockCriteria.ALTERATION))
	)
	{
	query += "SELECT DISTINCT s.sample_num FROM sample s, station ss,sample_Comment st"
		+ " WHERE st.sample_num = s.sample_num "
		+ " AND s.sample_num= st.sample_num "
		+ " AND s.station_num = ss.station_num "
		+ " AND ss.samp_technique_num in " + criteria.getValuesAsStr(ByRockCriteria.METHOD)
		+ " AND st.alteration_num in " +  criteria.getValuesAsStr(ByRockCriteria.ALTERATION,true);
	return query;
	}
	else if ((criteria.isSet(ByRockCriteria.METHOD))
	    && (criteria.isSet(ByRockCriteria.ROCK_CLASS))
	    && (criteria.isSet(ByRockCriteria.ALTERATION))
	)
	{

	query +=" SELECT DISTINCT st.sample_num FROM sample_Comment st, sample s, station ss"
		+ " WHERE st.sample_num = s.sample_num "
		+ " AND s.station_num = ss.station_num "
		+ " AND st.rockclass_num in " + criteria.getValuesAsStr(ByRockCriteria.ROCK_CLASS)
                + " AND st.alteration_num in " +  criteria.getValuesAsStr(ByRockCriteria.ALTERATION,true)
		+ " AND ss.samp_technique_num in " + criteria.getValuesAsStr(ByRockCriteria.METHOD);
	return query;
	}

	return query;
	}


	public String getQueryStr(Criteria criteria, String filter){

		String query = getQueryStr(criteria);
   	     if ( (criteria.isSet(ByRockCriteria.METHOD))
        	    && (!criteria.isSet(ByRockCriteria.ROCK_CLASS))
            	    && (!criteria.isSet(ByRockCriteria.ALTERATION))
          	)
		{


			query += " AND ss.sample_num in " + filter;
		}
		else 
		{
			query += " AND st.sample_num in " + filter;
		}
	return query;

	}


}

