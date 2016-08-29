
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class SampleIDDCtlQuery extends NonPersistentDynamicCtlQuery 
{
	String type = "View";
    private String match = equal;
	public static String View = "View";
	public static String Srch_ID = "srch_id";
	public static String Srch_Alias = "srch_alias";
    public static String Srch_IGSN = "srch_igsn";
	public static String Srch_Exped = "srch_exped";
    public static String equal = "equal";
    public static String begin = "begin";
    public static String end = "end";

	public SampleIDDCtlQuery(Criteria c) 
	{
		super();
		setCriteria(c);
	}

	public SampleIDDCtlQuery(String str, String type) 
	{
		super();
		v_filter = str;
		this.type = type; 
		int t = setFilter();
		
	}
    
    public SampleIDDCtlQuery(String str, String type, String match) 
	{
		super();
		v_filter = str;
		this.type = type; 
        this.match = match;
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


	public int updateData(String str, String type)
	{
		changed = true;
		v_filter = str;
		this.type = type; 
		return setFilter();
	}

	protected int setFilter()
	{
	qry = "	select p.ID,p.sample_ID, p.station_ID,"
		+ " p.rock_description, p.alteration, p.sampling,"
 		+ " " + DisplayConfigurator.Latitude_Mask.replace(DisplayConfigurator.Location_Abrv,'p')+","
 		+ " " + DisplayConfigurator.Longitude_Mask.replace(DisplayConfigurator.Location_Abrv,'p')+","
	 	+ " p.elevation_max,"
		+ " p.elevation_min,"
		+ " " + DisplayConfigurator.DataExistence_Mask.replace(DisplayConfigurator.Location_Abrv,'p')+","
		+ " p.station_num, p.IGSN IGSN "
		+ " from "
		+ " (select distinct s.sample_num ID,s.sample_id sample_ID,"
		+ " st.station_num station_num, st.station_id station_ID, igsn.igsn IGSN, "
		+ " rc.rockclass rock_description,"
		+ " " + DisplayConfigurator.DataType_Mask.replace(DisplayConfigurator.Location_Abrv,'b')+","
        + " CASE WHEN a.alteration_name='' THEN '' WHEN a.alteration_name IS NULL THEN '' ELSE a.alteration_name || '(' || p.last_name || decode( nvl( r.pub_year, '' ), '', '', ', ' || r.pub_year ) || ')' END alteration,"
		+ " stl.samp_technique_desc sampling,"
	   	+ " l.latitude,"
                + " l.longitude,"
		+ " l.elevation_max, l.elevation_min"
		+ " from person p, author_list al, reference r,"
		+ " alteration a,  rocktype rt, rockclass rc,  samp_technique_list stl,"
		+ " location l, station_by_location sbl, station st,"
		+ " batch b, sample_comment sc, sample s, igsn_info igsn "
		+ " where "
                + " p.person_num                   = al.person_num"
                + " and (al.ref_num(+)             = r.ref_num and al.author_order = 1)"
                + " and (r.ref_num                  = sc.ref_num and r.status = 'COMPLETED')"
		+ " and a.alteration_num(+) 		= sc.alteration_num"
		+ " and rt.rocktype_num(+) 		= rc.rocktype_num"
		+ " and rc.rockclass_num (+) 		= sc.rockclass_num"
		+ " and sc.sample_num (+) 		= s.sample_num"
		+ " and s.sample_num = igsn.sample_num (+)"    //???????????????????
		+ " and stl.samp_technique_num(+) 	= st.samp_technique_num"
		+ " and l.location_num (+) 		= sbl.location_num"
		+ " and (sbl.station_num(+) 		= st.station_num and sbl.location_order = 1)";

		if (!type.equals(Srch_Exped))
			qry += " and st.station_num 	= s.station_num";
		
		qry    += ""
		+ " and b.sample_num(+)			= s.sample_num";
    

		if (type.equals(View))
			qry += " and  (" + DisplayConfigurator.toReplace(v_filter,'s')+ ")";
		else if (type.equals(Srch_ID))
            qry += " and  s.sample_id "+ getPattern(v_filter.toUpperCase());
		else if (type.equals(Srch_Alias))
			qry += " and s.sample_num 		= sc.sample_num "
            + " and  sc.alias "+ getPattern(v_filter.toUpperCase());            
        else if (type.equals(Srch_IGSN))
    		qry += " and igsn.igsn "+ getPattern(v_filter.toUpperCase()); 	
       	else if (type.equals(Srch_Exped))
			qry += " and  s.station_num             = st.station_num"
			+ " and st.expedition_num             	= " + v_filter;

     		qry += " ) p "
            
		+ " group by  p.ID,p.sample_ID, p.station_ID, p.rock_description, p.alteration,"
		+ " p.sampling, p.latitude, p.longitude, p.elevation_max, p.elevation_min, p.station_num, p.IGSN order by p.sample_ID";
     	
     	//DEBUG
		//DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\bhchen\\Downloads\\sampleiddctlqry.txt");
		return 1;
	} 	  

    private String getPattern(String word) {
        if(match.equals(equal)) return " = '"+word+"'";
        else if(match.equals(begin)) return " like '"+word+"%'";
        else if(match.equals(end)) return " like '%"+word+"'";
        else return " like '%"+word+"%'";
    }
}
