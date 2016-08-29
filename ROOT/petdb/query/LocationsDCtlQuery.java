
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class LocationsDCtlQuery extends NonPersistentDynamicCtlQuery 
{
	public LocationsDCtlQuery(Criteria c) 
	{
		super();
		setCriteria(c);
	}

	public LocationsDCtlQuery(String str) 
	{
		super();
		v_filter = str;
		int t = setFilter();
		
	}

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new SampleIDDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}


	public int updateData(String str)
	{
			changed = true;
			v_filter = str;
			return setFilter();
	}

	protected int setFilter()
	{
	qry = "	select p.ID,p.sample_ID, p.station_ID,"
		+ " p.rock_description, p.alteration, p.sampling,"
		+ " " + DisplayConfigurator.Latitude_Mask.replace(DisplayConfigurator.Location_Abrv,'p')+","
 		+ " " + DisplayConfigurator.Longitude_Mask.replace(DisplayConfigurator.Location_Abrv,'p')+","
		+ " p.elevation_max,"
		+ " p.elevation_min, "
		+ " " +  DisplayConfigurator.DataExistence_Mask.replace(DisplayConfigurator.Location_Abrv,'p')+","
		+ " p.station_num, p.IGSN IGSN "
		+ " from "
		+ " (select distinct s.sample_num ID,s.sample_id sample_ID,"
		+ " st.station_num station_num, st.station_id station_ID, "
		//+ " rt.rocktype_code||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name) rock_description,"
		+ " decode(nvl(rt.rocktype_name, ' '),' ',' ',' '||rt.rocktype_name)  rock_description,"
		+ " " +  DisplayConfigurator.DataType_Mask.replace(DisplayConfigurator.Location_Abrv,'b')+","
	        + " CASE WHEN a.alteration_name='' THEN '' WHEN a.alteration_name IS NULL THEN '' ELSE a.alteration_name || '(' || p.last_name || decode( nvl( r.pub_year, '' ), '', '', ', ' || r.pub_year ) || ')' END alteration,"
		// + " a.alteration_name||'('||p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year)||')' alteration,"
		+ " stl.samp_technique_desc sampling,"
	   	+ " l.latitude,"
                + " l.longitude,"
		+ " l.elevation_max, l.elevation_min, igsn.igsn"
		+ " from person p, author_list al, reference r,"
		+ " alteration a,  rocktype rt, rockclass rc,  samp_technique_list stl,"
		+ " location l, station_by_location sbl, station st,"
		+ " batch b, sample s, sample_comment sc,  igsn_info igsn  "
		+ " where "
		+ " p.person_num 		   = al.person_num"
		+ " and (al.ref_num(+)		   = r.ref_num and al.author_order = 1)"
		+ " and (r.ref_num 		   = sc.ref_num and r.status = 'COMPLETED')"  
		+ " and sc.alteration_num 	   =a.alteration_num(+) "
		+ " and rc.rocktype_num            =rt.rocktype_num(+)"
		+ " and sc.rockclass_num           =rc.rockclass_num (+)"
		+ " and st.samp_technique_num      =stl.samp_technique_num"
		+ " and s.station_num              =st.station_num"
		+ " and sbl.location_num           =l.location_num (+)"
		+ " and (s.station_num             =sbl.station_num(+) and sbl.location_order = 1)"
		+ " and b.sample_num               =s.sample_num"
		+ " and s.sample_num               =sc.sample_num"
		+ " and s.sample_num               = igsn.sample_num (+)"
		+ " and sc.ref_num		   = " + v_filter;
     		qry += " ) p "
		+ " group by  p.ID,p.sample_ID, p.station_ID, p.rock_description, p.alteration,"
		+ " p.sampling, p.latitude, p.longitude, p.elevation_max, p.elevation_min, p.station_num, p.IGSN";
   //  		DataDCtlQuery.writeQueryToFile(qry,"C:\\Users\\Lulin Song\\Downloads\\locationDqry.txt");
		return 1;
	} 	  


}
