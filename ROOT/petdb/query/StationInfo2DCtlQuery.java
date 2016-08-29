
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class StationInfo2DCtlQuery extends NonPersistentDynamicCtlQuery 
{

	String type = StationInfo1DCtlQuery.ID;

	public StationInfo2DCtlQuery(String filter)
        {
                super(filter);
        }

       	public StationInfo2DCtlQuery(String filter, String t)
        {
                super();
                v_filter = filter;
                this.type = t;
                int tt = setFilter();
        }


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new StationInfo2DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = "select pp.ID, pp.sample_ID, pp.rock_description, pp.alteration,"
			+ " decode(sum(decode(pp.material_num,3,1,5,10,6,100,0)),1, 'Rock',10, 'Inclusion',"
			+ "100,'Mineral',111,'Rock, Mineral,Inclusion',101,'Rock, Mineral',"
			+ " 110,'Mineral, Inclusion',11,'Rock, Inclusion','')"
			+ " data_existence, pp.IGSN IGSN"
			+ " from"
			+ " (select distinct s.sample_num ID,s.sample_id sample_ID,"
			//+ " rt.rocktype_code||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name)"
			+ " decode(nvl(rt.rocktype_name, ' '),' ',' ',' '||rt.rocktype_name)"
			+ " rock_description,"
			+ " decode(b.material_num,7,3,8,3,b.material_num) material_num, igsn.igsn IGSN,"
		        + " CASE WHEN a.alteration_name='' THEN '' WHEN a.alteration_name IS NULL THEN '' ELSE a.alteration_name || '(' || p.last_name || decode( nvl( r.pub_year, '' ), '', '', ', ' || r.pub_year ) || ')' END alteration"
		        // + " a.alteration_name||'('||p.last_name||decode(nvl(r.pub_year,''),'',')',', '||r.pub_year||')') alteration"
			+ " from person p, author_list al, reference r,"
			+ " alteration a,  rocktype rt, rockclass rc,"
			+ " batch b, sample_comment sc, sample s, igsn_info igsn";
			if (StationInfo1DCtlQuery.ID.equals(type))
				qry += ", station st ";
			
			qry += " where "
                		+ " p.person_num                = al.person_num"
                		+ " and (al.ref_num(+)          = r.ref_num and al.author_order = 1)"
                		+ " and (r.ref_num               = sc.ref_num and r.status = 'COMPLETED')"
				+ " and sc.alteration_num	= a.alteration_num(+)"
				+ " and rt.rocktype_num(+)     	= rc.rocktype_num"
				+ " and rc.rockclass_num (+)  	= sc.rockclass_num"
				+ " and sc.sample_num         	= s.sample_num"
				+ " and b.sample_num            =  s.sample_num"
				+ " and s.sample_num = igsn.sample_num (+)";
			if (StationInfo1DCtlQuery.ID.equals(type))
				qry += " and s.station_num           = st.station_num"
					+ " and st.station_id          = '" + v_filter +"'";
			else 	qry += " and s.station_num           = " + v_filter; 
			
			qry += ") pp"
				+ " group by  pp.ID, pp.sample_ID, pp.rock_description, pp.alteration, pp.IGSN order by pp.ID";
			
			//DEBUG
			//DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\Lulin Song\\Downloads\\stationinfo2dctlqry.txt");
			
		return 1;
	} 	  
 
}
