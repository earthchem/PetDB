package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;
import petdb.data.*;
import petdb.config.*;

public class ByRockModeCriteria extends ByChemistryCriteria implements ChemistryCriteria 
{
	public ByRockModeCriteria(String str) 
	{	
		parameters = new Hashtable();
		dataWrapper = new ByRockModeWrapper(str);
		qryModel = new ByRockModeQryModel();
	}

	public void initCriteria()
	{
		; //	data_type = Individual;
	}

	public int getChemItemCount()
	{
		DataRecordDS data = (DataRecordDS) dataWrapper.getControlList("0");
        	String[] s_keys  =((DataRecordDS)data).getOrderedKeys();
		return super.getChemItemCount(s_keys);
	}

	public boolean isSet()
        {
                return (parameters.size() != 0 ? true : false);
        }



	public String getInnerQuery(){ return ((ByRockModeQryModel)qryModel).getInnerQuery();}
	public String getOuterCondition(){return ((ByRockModeQryModel)qryModel).getOuterCondition();}

	public String getChemistryQry(String v_filter)
	{
		return "";
		/*
        	int colon = v_filter.indexOf(':');
        	int s_colon = v_filter.indexOf(';');
        	String item_num = colon < 0 ? "" : v_filter.substring(0,colon);
        	String type = colon <0 ? "" : v_filter.substring(colon+1,s_colon);
        	String samples = s_colon <0 ? "" : v_filter.substring(s_colon+1,v_filter.length());
	
		String analysis_type = getAnalysisType();
		String a_t = "";
		if  (analysis_type.equals(ByDataWrapper.Both)) a_t = "(3,7,8)"; 
		if  (analysis_type.equals(ByDataWrapper.Rock)) a_t = "(7,8)"; 
		if  (analysis_type.equals(ByDataWrapper.Glass)) a_t = "(3)";

			String qry = " select distinct m.method_num, m.method_code "
                	+ " from method m, itemtype_list itl, itemmethod_list iml,"
                	+ " chemistry c, analysis a, batch b"
                	+ " where m.method_num = iml.method_num"
                	+ " and iml.itemmethodlist_num = c.itemmethodlist_num"
                	+ " and c.itemtypelist_num = itl.itemtypelist_num"
                	+ " and itl.item_type_num = " + type
                	+ " and itl.item_measured_num =" + item_num
                	+ " and c.analysis_num = a.analysis_num"
                	+ " and a.batch_num = b.batch_num"
                	+ " and (" + DisplayConfigurator.toReplace(samples,'b')+ ")"
			+ " and b.material_num in " + a_t 
                	+ " order by m.method_code";
			return qry;
		*/
	}
}

class ByRockModeQryModel extends ByChemistryQryModel
{

	String getInnerQuery() {return innerQry;}
	String getOuterCondition() {return outerCondition;}

        public String getQueryStr(Criteria criteria, String filter)
        {
	
	target_f = "ml.mineral_code";
	target_v = "rm.volume";
	buildQueryParts(criteria, filter);
        String query =  "select distinct ";
	
	query = query + outer_select + ", prepared.rock_mode_num, s.sample_id,"
                + " " + DisplayConfigurator.Latitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
                + " " + DisplayConfigurator.Longitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
		+ " l.elevation_min,"
		+ " tsl.tectonic_setting_name,"
		//+ " rt.rocktype_code||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name), "
		+ " rc.rockclass||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name), "
		+ " p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year),"
		+ " r.ref_num, s.sample_num,"
 		+ " l.latitude, l.longitude,"
		+ " e.expedition_name||decode(nvl(e.leg, '-'), '-', ' ','-'||e.leg) name_leg, e.expedition_num, info.igsn sample_igsn"
		+ " from person p, author_list al, reference r, rocktype rt,"
		+ " rockclass rc, sample_comment sc, location l, station_by_location sbl,"
		+ " tectonic_setting_list tsl, station st, expedition e, sample s, igsn_info info,";
			
			innerQry = ""
			+ " (select c.sample_num, c.rock_mode_num, c.ref_num" 
			+   inner_select
			+ " from rock_mode_analysis c, rock_mode rm, mineral_list ml"
			+ " where c.rock_mode_num = rm.rock_mode_num"
			+ " and ml.mineral_num = rm.mineral_num"
			+ " and (" + DisplayConfigurator.toReplace(filter,'c') + ")"
			+ " group by c.sample_num, c.rock_mode_num, c.ref_num) prepared ";
			
		query += innerQry
		+ " where st.station_num(+)     = s.station_num"
		+ " and e.expedition_num(+)    	 = st.expedition_num"
		+ " and rt.rocktype_num(+)    	 = rc.rocktype_num "   
		+ " and rc.rockclass_num (+)     = sc.rockclass_num"
		+ " and sc.ref_num 	         = prepared.ref_num"
		+ " and sc.sample_num            = s.sample_num"
		+ " and tsl.tectonic_setting_num (+) = l.tectonic_setting_num"
		+ " and l.location_num (+)       = sbl.location_num"
		+ " and (sbl.station_num(+)      = s.station_num and sbl.location_order =1)"
		+ " and p.person_num(+)          = al.person_num"
		+ " and (al.ref_num (+)          = r.ref_num and al.author_order = 1)"
		+ " and r.ref_num (+)            = prepared.ref_num"
		+ " and s.sample_num             = prepared.sample_num" + " and info.sample_num(+)      = prepared.sample_num";
                
		query += " and " + outerCondition
	 	+ " order by s.sample_id, prepared.rock_mode_num";

        return query;
        }

}

