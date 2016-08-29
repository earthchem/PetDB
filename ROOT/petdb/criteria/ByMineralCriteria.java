package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;
import petdb.data.*;
import petdb.config.*;

public class ByMineralCriteria extends ByChemistryCriteria implements ChemistryCriteria 
{
	public static String Mineral_List = "1";
 
	public ByMineralCriteria(String str) 
	{	
		parameters = new Hashtable();
		dataWrapper = new ByMineralWrapper(str);
		qryModel = new ByMineralQryModel();
		setMineralType("");
	}

    public int setValues(String key, String[] values)
    {
        int r = super.setValues(key,values);
        if (key.equals(Mineral_List))
            ((ByMineralWrapper)dataWrapper).setSelected(getCSV(Mineral_List,true));
        return r;
    }
	
	/* Get total number of chemicals selected in pg3.jsp */
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



	public String getMineralType(){ return filter1;}

	public boolean setMineralType(String s)
	{
		return setFilter1(s);
	}
        public String getChemistryQry(String v_filter)
        {
        int colon = v_filter.indexOf(':');
        int s_colon = v_filter.indexOf(';');
        String item_num = colon < 0 ? "" : v_filter.substring(0,colon);
        String type = colon <0 ? "" : v_filter.substring(colon+1,s_colon);
        String samples = s_colon <0 ? "" : v_filter.substring(s_colon+1,v_filter.length());

                String qry = " select distinct m.method_num, m.method_code "
                + " from method m, itemtype_list itl, itemmethod_list iml,"
                + " chemistry c, analysis a, batch b, mineral min"
                + " where m.method_num = iml.method_num"
                + " and iml.itemmethodlist_num = c.itemmethodlist_num"
                + " and c.itemtypelist_num = itl.itemtypelist_num"
                + " and itl.item_type_num = " + type
                + " and itl.item_measured_num =" + item_num
                + " and c.analysis_num = a.analysis_num"
                + " and a.batch_num = b.batch_num"
                + " and (" + DisplayConfigurator.toReplace(samples,'b') + ")"
                + " and b.batch_num = min.batch_num";
		if (isSet(Mineral_List))
			qry +=
		 	" and min.mineral_num in " + getValuesAsStr(Mineral_List,true);
                qry += " order by m.method_code";

                return qry;


        }
	public String getInnerQuery(){ return ((ByMineralQryModel)qryModel).getInnerQuery();}
	public String getOuterCondition(){return ((ByMineralQryModel)qryModel).getOuterCondition();}

}

class ByMineralQryModel extends ByChemistryQryModel
{

	String getInnerQuery() {return innerQry;}
	String getOuterCondition() {return outerCondition;}

    public String getQueryStr(Criteria criteria, String filter)
    {
        String query =  "select distinct ";
	    buildQueryParts(criteria, filter);
	    query = query + outer_select 
		        + ", prepared.data_quality_num, s.sample_id"
		        + ", ml.mineral_code||decode(nvl(m.spot_id,''), '','',' spot'||m.spot_id), ml.mineral_name,"
		        + " cl.crystal_code, cl.crystal_description, m.rim_or_core,"
	            + " " + DisplayConfigurator.Latitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
                + " " + DisplayConfigurator.Longitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
		        + " l.elevation_min,"
		        + " tsl.tectonic_setting_name,"
		      //+ " rt.rocktype_code||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name),"
		        + " rc.rockclass||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name),"
		        + " p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year),"
		        + "  r.ref_num, prepared.method_code, s.sample_num,"
		        + " l.latitude, l.longitude,"
		        + " e.expedition_name||decode(nvl(e.leg, '-'), '-', ' ','-'||e.leg) name_leg, e.expedition_num, info.igsn sample_igsn, prepared.ANALYSIS_COMMENT"
		        + " from person p, author_list al, reference r, rocktype rt,"
		        + " rockclass rc, sample_comment sc, location l, station_by_location sbl,"
		        + " tectonic_setting_list tsl,mineral m, mineral_list ml, crystal_list cl,station st, expedition e, sample s, igsn_info info,";

	    innerQry = ""
			    + " (select b.sample_num, a.analysis_num, a.ANALYSIS_COMMENT, mth.method_code, dq.ref_num,a.data_quality_num, m.batch_num "
			    +   inner_select
			    + " from item_measured im, chemistry c, method mth,"
			    + " data_quality dq, analysis a, mineral m, batch b"
			    + " where c.analysis_num = a.analysis_num"
			    + " and im.item_measured_num = c.item_measured_num"
			    + " and mth.method_num = dq.method_num"
			    + " and dq.data_quality_num = a.data_quality_num"
			    + " and a.batch_num = b.batch_num"
			    + " and b.batch_num = m.batch_num";
			
		if (criteria.isSet(ByMineralCriteria.Mineral_List))
				innerQry +=" and m.mineral_num in " + criteria.getValuesAsStr(ByMineralCriteria.Mineral_List,true);
		innerQry +=
			     " and (" + DisplayConfigurator.toReplace(filter,'b')+ ")"
			     + " group by b.sample_num, a.analysis_num, a.ANALYSIS_COMMENT, mth.method_code, dq.ref_num, a.data_quality_num,"
			     + " m.batch_num ) prepared";
		query += innerQry 	
		      + " where st.station_num(+) 	 = s.station_num"
		      + " and e.expedition_num(+) 	 = st.expedition_num"
		      + " and rt.rocktype_num(+)       = rc.rocktype_num "   
		      + " and rc.rockclass_num (+)     = sc.rockclass_num"
		      + " and sc.ref_num 	         = prepared.ref_num"
		      + " and sc.sample_num(+)         = s.sample_num"
		      + " and tsl.tectonic_setting_num (+) = l.tectonic_setting_num"
		      + " and l.location_num (+)       = sbl.location_num"
		      + " and (sbl.station_num(+)      = s.station_num and sbl.location_order =1)"
		      + " and p.person_num(+)          = al.person_num"
		      + " and (al.ref_num (+)          = r.ref_num and al.author_order = 1)"
		      + " and r.ref_num (+)            = prepared.ref_num"
		      + " and s.sample_num             = prepared.sample_num"
		      + " and cl.crystal_num(+)	 = m.crystal_num"
		      + " and ml.mineral_num(+)	 = m.mineral_num"
		      + " and m.batch_num		 = prepared.batch_num" + " and info.sample_num(+)      = prepared.sample_num";
 
         /* outerCondition = ""
                + ( condition.length() == 0
                        ? " (" + general_condition + ") <> 0 "
                        : " (" + condition + " OR (" + general_condition+")<>0)"

                  );
		*/
        query +=  " and " + outerCondition
                + " order by s.sample_id, prepared.data_quality_num";
       // DataDCtlQuery.writeQueryToFile(query,"C:\\Users\\Lulin Song\\Downloads\\bymineralcriteria_qry.txt");
        return query;
    }

}

