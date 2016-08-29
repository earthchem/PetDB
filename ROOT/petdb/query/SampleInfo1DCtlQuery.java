
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class SampleInfo1DCtlQuery extends NonPersistentDynamicCtlQuery 
{
	public SampleInfo1DCtlQuery(String filter)
        {
                super(filter);
        }

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new SampleInfo1DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "Select distinct s.sample_id,"
			+ DisplayConfigurator.Latitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
			+ DisplayConfigurator.Longitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
			+ DisplayConfigurator.Elevation_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
			+ " tsl.tectonic_setting_name,"
			+ " decode(nvl(gl.location_type, '-'), '-', ' ',' '||gl.location_type ) || ':' || gl.location_name  location,"
			+ " l.location_comment ,"
			+ " rt.rocktype_name ||decode(nvl(rt.rocktype_desc, '-'), '-', '','-'|| rt.rocktype_desc) rock, "
			+ " rc.rockclass,"
		    	+ " decode( (sc.sample_comment||': '||sc.rocktexture),': ', ' ',(sc.sample_comment||': '||sc.rocktexture)),"
			+ " a.alteration_name,"
			+ " g.geol_age_prefix||' '|| g.geol_age,"
			+   DisplayConfigurator.Age_Mask.replace(DisplayConfigurator.Location_Abrv,'g')+","
			+ " e.expedition_name||decode(nvl(e.leg, '-'), '-', ' ','-'||e.leg) name_leg, e.expedition_num,"
			+ " decode(nvl(sh.ship_name, '-'), '-', ' ', '('||sh.ship_name||', '||e.exp_year_from||')'),"
			+ " decode(nvl(p.last_name, '-'), '-', ' ',p.last_name||decode(nvl(p.first_name,''),'',', '||p.first_name)) name,"
			+ " st.samp_date, stl.samp_technique_desc,"
			+ " st.station_id station, "
			+ " st.station_num,"
			+ " inst.institution, inst.department, sc.ref_num"
			+ " from geograph_loc gl, location l, station_by_location sbl, station st, tectonic_setting_list tsl,"
			+ " sample s, rocktype rt, rockclass rc, sample_comment sc, alteration a, sample_age g,"
			+ " expedition e, institution inst, ship sh, person p, samp_technique_list stl,"
			+ " chief_scientist cs, station_comment stc"
			+ " where stc.station_num(+) = st.station_num"
			+ " and sh.ship_num(+) = e.ship_num"
			+ " and p.person_num (+) = cs.person_num"
			+ " and inst.institution_num(+) = e.institution_num"
			+ " and e.expedition_num(+) = st.expedition_num"
			+ " and cs.expedition_num(+) = st.expedition_num"
			+ " and stl.samp_technique_num(+) = st.samp_technique_num"
			+ " and l.location_num(+) = sbl.location_num"
			+ " and (sbl.station_num(+) = st.station_num and sbl.location_order = 1)"
			+ " and St.station_num = s.station_num"
			+ " and tsl.tectonic_setting_num(+) = l.tectonic_setting_num"
			+ " and gl.location_num(+) = l.location_num"
			+ " and rc.rockclass_num(+) = sc.rockclass_num"
			+ " and rt.rocktype_num(+) = rc.rocktype_num"
			+ " and a.alteration_num (+)= sc.alteration_num"
			+ " and sc.sample_num(+) = s.sample_num"
			+ " and g.sample_num (+) = s.sample_num"
			+ " and s.sample_num = " + v_filter 
			+ " order by location, name";    
//		DataDCtlQuery.writeQueryToFile(qry,"C:\\Users\\Lulin Song\\Downloads\\mytestqry.txt");
//		DataDCtlQuery.writeQueryToFile(qry,"C:\\Users\\bhchen\\Downloads\\mytestqry.txt");
		return 1;
	} 	  
}

