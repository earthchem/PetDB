
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class StationExpedInfo1DCtlQuery extends NonPersistentDynamicCtlQuery 
{	

	public static String ID = "ID";
	public static String NUM = "NUM";
 
	String type = ID;

	public StationExpedInfo1DCtlQuery(String filter)
	{
                super(filter);
        }

	public StationExpedInfo1DCtlQuery(String filter, String t)
	{
	        super();
                v_filter = filter;
                this.type = t;
                int tt = setFilter();
	}


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new StationExpedInfo1DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "Select distinct st.station_id,"
			+ " e.exp_year_from || decode(nvl(e.exp_year_to, 0),0,' ',decode(e.exp_year_to,e.exp_year_from,' ','-'||e.exp_year_to)) year,"
			+ " e.expedition_name exped_name,"
			+ " (p.first_name||' '|| p.last_name) scientist_name"
			+ " from station st, expedition e, person p, chief_scientist ch, ship sh";

		if (StationExpedInfo1DCtlQuery.ID.equals(type))
			qry += " where st.station_id = '" + v_filter + "'";
		else 	qry += " where st.station_num = " + v_filter;

		qry += " and e.expedition_num(+) = st.expedition_num "
				+ " and ch.expedition_num(+) = st.expedition_num"
				+ " and sh.ship_num (+) = e.ship_num"
				+ " and ch.person_num = p.person_num (+)";
		
	//	DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\Lulin Song\\Downloads\\station_exped_info1dctlqry.txt");
		return 1;
	} 	  

}
