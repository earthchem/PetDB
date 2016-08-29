
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class StationInfo3DCtlQuery extends NonPersistentDynamicCtlQuery 
{
	String type = StationInfo1DCtlQuery.ID;

	public StationInfo3DCtlQuery(String filter)
        {
                super(filter);
        }

        public StationInfo3DCtlQuery(String filter, String t)
        {
                super();
                v_filter = filter;
                this.type = t;
                int tt = setFilter();
        }


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new StationInfo3DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "Select distinct decode(sbl.location_order,1,'on',2,'off',''),"
 			+ " " + DisplayConfigurator.Latitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
 			+ " " + DisplayConfigurator.Longitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
			+ " l.elevation_min, l.elevation_max, l.land_or_sea,"
			+ " tsl.tectonic_setting_name, l.location_comment, sbl.location_order, l.loc_precision"
			+ " from geograph_loc gl, location l, station_by_location sbl,"
			+ " tectonic_setting_list tsl";
			if (StationInfo1DCtlQuery.ID.equals(type))
				qry +=", station s "
					+ " where s.station_id = '" + v_filter+"'" 
					+ " and sbl.station_num = s.station_num";
			else 	qry+= " where sbl.station_num = " + v_filter;
			
			qry += " And l.location_num = sbl.location_num "
			+ " And gl.location_num = l.location_num"
			+ " And tsl.tectonic_setting_num = l.tectonic_setting_num and sbl.location_order in (1,2) "
			+ " Order by sbl.location_order";
		//	DataDCtlQuery.writeQueryToFile(qry,"C:\\Users\\bhchen\\Downloads\\stationPrecision_qry.txt");
		return 1;
	}
}

