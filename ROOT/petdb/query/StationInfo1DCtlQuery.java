
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class StationInfo1DCtlQuery extends NonPersistentDynamicCtlQuery 
{	

	public static String ID = "ID";
	public static String NUM = "NUM";
 
	String type = ID;

	public StationInfo1DCtlQuery(String filter)
	{
                super(filter);
        }

	public StationInfo1DCtlQuery(String filter, String t)
	{
	        super();
                v_filter = filter;
                this.type = t;
                int tt = setFilter();
	}


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new StationInfo1DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "Select distinct st.station_id,"
			+ " e.expedition_name||decode(nvl(e.leg, '-'), '-', ' ','-'||e.leg) name_leg, "
			+ " e.expedition_num, stl.samp_technique_desc,"
			+ " decode(nvl(gl.location_type, '-'), '-', ' ',''||gl.location_type ) || ':' || gl.location_name  location, info.igsn station_igsn, stc.station_comment, "
			+"  decode( st.NAVMETHOD_NUM, nav.NAVMETHOD_NUM, nav.NAVMETHOD_NAME, '') navigation_name,stc.station_name "
		//	+ " gl.location_name||decode(nvl(gl.location_type, '-'), '-', ' ',':'||gl.location_type) location"
			+ " from station st, expedition e, samp_technique_list stl, geograph_loc gl,"
			+ " station_by_location sbl, igsn_info info , station_comment stc, NAVMETHOD nav";

		if (StationInfo1DCtlQuery.ID.equals(type))
			qry += " where St.station_id = '" + v_filter + "'";
		else 	qry += " where St.station_num = " + v_filter;

		qry += " And e.expedition_num(+) = st.expedition_num "
			+ " And stl.samp_technique_num(+) = st.samp_technique_num"
			+ " And sbl.station_num(+) = st.station_num"
			+ " And stc.station_num(+) = st.station_num"
			+ " And gl.location_num(+) = sbl.location_num"+ " and  st.station_num = info.station_num(+)";
		
	//	DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\Lulin Song\\Downloads\\stationinfo1dctlqry.txt");
		return 1;
	} 	  

}
