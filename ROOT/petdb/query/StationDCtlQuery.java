
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class StationDCtlQuery extends NonPersistentDynamicCtlQuery 
{
	static public String All = "All";
	static public String Exp_Based = "Expedition_based";
	
	private String type = All;
 
	public StationDCtlQuery(String filter, String t) 
	{
		super();
                v_filter = filter;
                this.type = t;
                int r = setFilter();
		
	}

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new StationDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}



	protected int setFilter()
	{
	qry = " select distinct st.station_num station_num, st.station_id station_ID,"
		+ " stl.samp_technique_desc sampling,"
		+ " " + DisplayConfigurator.Latitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
		+ " " + DisplayConfigurator.Longitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
		+ " l.elevation_max,"
		+ " l.elevation_min, e.expedition_name, tsl.tectonic_setting_name, l.latitude, l.longitude"
		+ " from samp_technique_list stl, location l, station_by_location sbl, station st,"
		+ " expedition e, tectonic_setting_list tsl"
		+ " where tsl.tectonic_setting_num = l.tectonic_setting_num"
		+ " and e.expedition_num = st.expedition_num"
		+ " and stl.samp_technique_num(+) = st.samp_technique_num"
		+ " and l.location_num (+) = sbl.location_num"
		+ " and sbl.location_order = 1"
		+ " and sbl.station_num(+) = st.station_num";
	
		if (type.equals(Exp_Based))
			qry+= " and st.expedition_num = " + v_filter;
		
		qry+= " order by st.station_id";
 
		return 1;
	} 	  


}
