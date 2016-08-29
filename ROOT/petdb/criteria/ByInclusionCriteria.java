package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;
import petdb.data.*;
import petdb.config.*;

public class ByInclusionCriteria extends ByChemistryCriteria implements ChemistryCriteria 
{
	public static String Heating_All  = "";
	public static String Heating_No  = "N";
	public static String Heating_Yes  = "Y";
	public static String Heating  = "Heating";
	public static String HostMineral= "1";
	public static String Mineral = "2";
	
	private String heating = Heating_All;
	boolean h = false;	 //true if heating
	boolean hm = false;	 //true if host mineral
	boolean m = false;   //true if mineral
	
	/* Construct default inclusion search criteria, default to melt inclusion */
	public ByInclusionCriteria(String str) 
	{
		parameters = new Hashtable();
		dataWrapper = new ByInclusionWrapper(str);
		qryModel = new ByInclusionQryModel();
		setAnalysisType(ByInclusionWrapper.Melt); //Default to melt inclusion
	}

	public void initCriteria()
	{
		heating = Heating_All;

	}

	public int getChemItemCount()
        {
                DataRecordDS data = (DataRecordDS) dataWrapper.getControlList("0");
                String[] s_keys  =((DataRecordDS)data).getOrderedKeys();
                return super.getChemItemCount(s_keys);
        }


	public void setHeating(String s)
	{
		if (heating.equals(s))
		{
			h = true;
		} else {
			heating = s;
			h = true;
		}
		
		if ( h && hm) 
		{ 
			((ByInclusionWrapper)dataWrapper).setMeltSelected(getSelectedString(HostMineral));
			h = false;
			hm = false;
		}
		return;
		
	}

	public String getHeating(){ return heating;}

        public boolean isSet()
        {
                return (parameters.size() != 0 ? true : false);
        }

	 

	public int setValues(String key, String[] values)
	{
		int r = super.setValues(key,values);
		if (key.equals(Heating))
			h =  true;
		if (key.equals(Mineral))
			m = true;
		if (key.equals(HostMineral))
			hm = true;
		
		if ( h && hm) 
		{ 
			((ByInclusionWrapper)dataWrapper).setMeltSelected(getSelectedString(HostMineral));
			h = false;
			hm = false;
		}

		if ( m && hm)
		{ 
			//((ByInclusionWrapper)dataWrapper).setMINSelected(getSelectedString(HostMineral));
			m = false;
			hm = false;
		}
		return r;
	}

	private String getSelectedString(String key1)
	{

		String[] k1 = getValuesArray(key1);
		String ret = "";
		if (k1 != null)  
		{
			for (int j=0; j<k1.length; j++)
				if ((j== 0))
					if (heating.equals(Heating_All))
						ret += k1[j] + Heating_No + "," + k1[j] + Heating_Yes;
					else 
						ret += k1[j] + heating;
				else 
					if (heating.equals(Heating_All))
						ret += "," + k1[j] + Heating_No + "," + k1[j] + Heating_Yes;
					else 
						ret +=","+ k1[j]+ heating;
		}
		else {
			if (heating.equals(Heating_All))
				ret += "";// Heating_No + "," + Heating_Yes;
			else 
				ret += heating;
		}
		return ret;	
	}
		
	

	public String getAnalysisType(){ return filter1;}

	public boolean setAnalysisType(String s)
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
                + " chemistry c, analysis a, batch b, inclusion inc"
                + " where m.method_num = iml.method_num"
                + " and iml.itemmethodlist_num = c.itemmethodlist_num"
                + " and c.itemtypelist_num = itl.itemtypelist_num"
                + " and itl.item_type_num = " + type
                + " and itl.item_measured_num =" + item_num
                + " and c.analysis_num = a.analysis_num"
                + " and a.batch_num = b.batch_num"
                + " and (" + DisplayConfigurator.toReplace(samples,'b') + ")"
		+ " and b.batch_num = inc.batch_num";
		//+ " and inc.inclusion_type = '" + filter1 + "'";  // modified BF 4/17/2008   
		//FIXME: Database is messed up. Filter1 should be 'GL' or 'GLASS', not both.
		if(filter1.equals("MELT"))
		{
			qry += " and inc.inclusion_type in ('GL', 'GLASS')";
		}
		else qry += " and inc.inclusion_type = '" + filter1 + "'";  // modified BF 4/17/2008  
			

		if (heating.equals(Heating_Yes))
			heating = "('Y','YES')";
		if (heating.equals(Heating_No))
			heating = "('N','NO')";
                	
		if (isSet(ByInclusionCriteria.HostMineral))
                             qry +=" and inc.host_mineral_num in "
				 + getValuesAsStr(ByInclusionCriteria.HostMineral,true);
		
		if (!heating.equals(ByInclusionCriteria.Heating_All))
                             qry +=
                                " and inc.heating in " + heating;
                qry += " order by m.method_code";

		return qry;


	}
	public String getInnerQuery(){ return ((ByInclusionQryModel)qryModel).getInnerQuery();}
	public String getOuterCondition(){return ((ByInclusionQryModel)qryModel).getOuterCondition();}

}

class ByInclusionQryModel extends ByChemistryQryModel
{

	String getInnerQuery() {return innerQry;}
	String getOuterCondition() {return outerCondition;}


    public String getQueryStr(Criteria criteria, String filter)
    {
	    String heating = ((ByInclusionCriteria)criteria).getHeating();
	    if (!heating.equals(ByInclusionCriteria.Heating_All)) 
	    {
		    if (heating.equals(ByInclusionCriteria.Heating_Yes))
			    heating = "('Y','YES')";
		    if (heating.equals(ByInclusionCriteria.Heating_No))
			    heating = "('N','NO')";
        }
        String query =  "select distinct ";
        buildQueryParts(criteria, filter);
        query = query + outer_select
                + ", prepared.data_quality_num, s.sample_id,"
                + " inc.inclusion_type||' inclusion ', ml.mineral_code, ml.mineral_name,"
		        + " decode(nvl(inc.heating, ''), '', ' ','N','NO','NO','NO',inc.heating||' at '||inc.heating_temperature),"
		        + " inc.rim_or_core,"
                + " " + DisplayConfigurator.Latitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
                + " " + DisplayConfigurator.Longitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
                + " l.elevation_min,"
                + " tsl.tectonic_setting_name,"
		       // + " rt.rocktype_code||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name)," //to display rockclass instead of rocktype_code--LS
                + " rc.rockclass||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name),"
                + " p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year),"
		        + " r.ref_num, prepared.method_code, s.sample_num,"
		        + " l.latitude, l.longitude,"
		        + " e.expedition_name||decode(nvl(e.leg, '-'), '-', ' ','-'||e.leg) name_leg, e.expedition_num, info.igsn sample_igsn"
                + " from person p, author_list al, reference r, rocktype rt,"
                + " rockclass rc, sample_comment sc, location l, station_by_location sbl,"
                + " tectonic_setting_list tsl, inclusion inc, mineral_list ml, station st, expedition e, sample s, igsn_info info,";

        innerQry = " (select b.sample_num, a.analysis_num, mth.method_code, dq.ref_num, a.data_quality_num, inc.batch_num "
                   +   inner_select
                   + " from item_measured im, chemistry c, method mth,"
                   + " data_quality dq, analysis a, inclusion inc, batch b"
                   + " where c.analysis_num = a.analysis_num"
                   + " and im.item_measured_num = c.item_measured_num"
                   + " and mth.method_num = dq.method_num"
                   + " and dq.data_quality_num = a.data_quality_num"
                   + " and a.batch_num = b.batch_num"
                   + " and b.batch_num = inc.batch_num";
                        
        if( ((ByInclusionCriteria)criteria).getAnalysisType().equalsIgnoreCase("MELT")==true ) //FIXME Lulin should be only 'GL' but the database needs to clean up
              innerQry += " and inc.inclusion_type in ('GL', 'GLASS') ";
        else
              innerQry += " and inc.inclusion_type = '" + ((ByInclusionCriteria)criteria).getAnalysisType() + "'"; //modified BF 4/17/2008

        if (criteria.isSet(ByInclusionCriteria.HostMineral))
              innerQry +=" and inc.host_mineral_num in " + criteria.getValuesAsStr(ByInclusionCriteria.HostMineral,true);
        
	    if (!heating.equals(ByInclusionCriteria.Heating_All))
              innerQry +=" and inc.heating in " + heating;
        innerQry +=" and (" + DisplayConfigurator.toReplace(filter,'b')+ ")"
                 + " group by b.sample_num, a.analysis_num, mth.method_code, dq.ref_num, a.data_quality_num,"
			     + " inc.batch_num ) prepared";
        query += innerQry + " where st.station_num(+) 	 = s.station_num"
		        + " and e.expedition_num(+) 	 = st.expedition_num"
		        + " and rt.rocktype_num(+)       = rc.rocktype_num "
                + " and rc.rockclass_num (+)     = sc.rockclass_num"
                + " and sc.ref_num               = prepared.ref_num"
                + " and sc.sample_num(+)         = s.sample_num"
                + " and tsl.tectonic_setting_num (+) = l.tectonic_setting_num"
                + " and l.location_num (+)       = sbl.location_num"
                + " and (sbl.station_num(+)       = s.station_num  and sbl.location_order =1)"
                + " and p.person_num(+)          = al.person_num"
                + " and (al.ref_num (+)          = r.ref_num and al.author_order = 1)"
                + " and r.ref_num (+)            = prepared.ref_num"
                + " and s.sample_num             = prepared.sample_num"
                + " and ml.mineral_num           = inc.host_mineral_num"
                + " and inc.batch_num            = prepared.batch_num" + " and info.sample_num(+)      = prepared.sample_num";

        query +=  " and " + outerCondition
                + " order by s.sample_id, prepared.data_quality_num";

        return query;
    }
}	

