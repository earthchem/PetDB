package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;
import petdb.data.*;
import petdb.config.*;

public class ByDataCriteria extends ByChemistryCriteria implements ChemistryCriteria 
{
	public static String Compiled  = "Compiled";
	public static String Individual = "Individual";
	
	private String data_type = Individual;
	
	public ByDataCriteria(String str) 
	{	
		parameters = new Hashtable();
		dataWrapper = new ByDataWrapper(str);
		qryModel = new ByDataQryModel();
		setAnalysisType(ByDataWrapper.Both);
		setDataType(Individual);
	}

	public void initCriteria()
	{
		data_type = Individual;
		setAnalysisType(ByDataWrapper.Both);
	}


	public String getAnalysisType(){ return filter1;}

	public boolean setAnalysisType(String s)
	{
		return setFilter1(s);
	}

	public int getChemItemCount()
	{
		DataRecordDS data = (DataRecordDS) dataWrapper.getControlList("0");
        	String[] s_keys  =((DataRecordDS)data).getOrderedKeys();
		return super.getChemItemCount(s_keys);
	}

	public String getDataType() 
	{
		return ( (!Compiled.equals(data_type)) && (!Individual.equals(data_type)) ) ? Individual : data_type;
	}
 
	public void setDataType(String s)
	{
		data_type = s;
	}
	
	public boolean isSet()
        {
                return (parameters.size() != 0 ? true : false);
        }



	public String getInnerQuery(){ return ((ByDataQryModel)qryModel).getInnerQuery();}
	public String getOuterCondition(){return ((ByDataQryModel)qryModel).getOuterCondition();}

	public String getChemistryQry(String v_filter)
	{

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
                + " and (" + DisplayConfigurator.toReplace(samples,'b') + ")"
		+ " and b.material_num in " + a_t 
                + " order by m.method_code";
		return qry;
	}
}

class ByDataQryModel extends ByChemistryQryModel
{

	String getInnerQuery() {return innerQry;}
	String getOuterCondition() {return outerCondition;}

    public String getQueryStr(Criteria criteria, String filter)
    {
        String replace ="";
        if ( ((ByDataCriteria)criteria).getDataType().equals(ByDataCriteria.Individual) ) replace = DisplayConfigurator.toReplace(filter,'b');
        else replace = DisplayConfigurator.toReplace(filter,'c');       
        if(((ByChemistryCriteria )criteria).isANDQuery()) replace = getReplace(criteria, filter,replace);
      
		buildQueryParts(criteria, filter);
        String query =  "select distinct ";
	    String analysis_type = ((ByDataCriteria)criteria).getAnalysisType();
	    String a_t = "";
	    if  (analysis_type.equals(ByDataWrapper.Both)) a_t = "(3,7,8)"; 
	    if  (analysis_type.equals(ByDataWrapper.Rock)) a_t = "(7,8)"; 
	    if  (analysis_type.equals(ByDataWrapper.Glass)) a_t = "(3)";
 
	    if ( ((ByDataCriteria)criteria).getDataType().equals(ByDataCriteria.Individual) )
	    {
	        query = query + outer_select + ", prepared.data_quality_num, s.sample_id,"
		           + " m.material_code, m.material_name,"
                   + " " + DisplayConfigurator.Latitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
                   + " " + DisplayConfigurator.Longitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
		           + " l.elevation_min,"
		           + " tsl.tectonic_setting_name,"
		         //+ " rt.rocktype_code||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name), "
		           + " rc.rockclass||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name), "
		           + " p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year),"
		           + " r.ref_num, prepared.method_code, s.sample_num,"
		           + " l.latitude, l.longitude,"
		           + " e.expedition_name||decode(nvl(e.leg, '-'), '-', ' ','-'||e.leg) name_leg, e.expedition_num, info.igsn sample_igsn, a.analysis_comment"  
		           + " from material m, person p, author_list al, reference r, rocktype rt," //<-- FROM
		           + " rockclass rc, sample_comment sc, location l, station_by_location sbl,"
		           + " tectonic_setting_list tsl, station st, expedition e, sample s, igsn_info info, analysis a,";

			innerQry = ""
			       + " (select b.sample_num, a.analysis_num, mth.method_code, dq.ref_num, a.data_quality_num, b.material_num "
			       +   inner_select
			       + " from item_measured im, chemistry c, method mth,"
			       + " data_quality dq, analysis a, batch b"
			       + " where c.analysis_num = a.analysis_num"
			       + " and im.item_measured_num = c.item_measured_num"
			       + " and mth.method_num = dq.method_num"
			       + " and dq.data_quality_num = a.data_quality_num"
			       + " and a.batch_num = b.batch_num"
			       + " and b.material_num in " + a_t  
		//	       + " and (" + DisplayConfigurator.toReplace(filter,'b') + ")"
                    + " and (" + replace + ")"
			       + " group by b.sample_num, a.analysis_num, mth.method_code, dq.ref_num, a.data_quality_num, b.material_num ) prepared";
		   query += innerQry 	
	 	           + " where st.station_num(+) 	= s.station_num"
		           + " and e.expedition_num(+) 	= st.expedition_num"
		           + " and m.material_num 	 	= prepared.material_num"
		           + " and rt.rocktype_num(+)      = rc.rocktype_num "   
		           + " and rc.rockclass_num (+)    = sc.rockclass_num"
		           + " and sc.ref_num 	        = prepared.ref_num"
		           + " and sc.sample_num(+)        = s.sample_num"
		           + " and tsl.tectonic_setting_num (+) = l.tectonic_setting_num"
		           + " and l.location_num (+)      = sbl.location_num"
		           + " and (sbl.station_num(+)     = s.station_num and sbl.location_order =1)"
		           + " and p.person_num(+)         = al.person_num"
		           + " and (al.ref_num (+)         = r.ref_num and al.author_order = 1)"
		           + " and r.ref_num (+)           = prepared.ref_num"
		           + " and s.sample_num            = prepared.sample_num"
		           + " and info.sample_num(+)      = prepared.sample_num"
		           + " and a.analysis_num (+)      = prepared.analysis_num";

		   /*
		   outerCondition = ""
		             + ( condition.length() == 0
		             ? " (" + general_condition + ") <> 0 " 
			         : " (" + condition + " OR (" + general_condition+")<>0)"

		   );
		   */ 

		   query +=  " and " + outerCondition 
	 	         + " order by s.sample_id, prepared.data_quality_num";
    	} 
	    else 
	    {
	       query = query + outer_select + ", prepared.data_quality_num, s.sample_id,"
		           + "  m.material_code,m.material_name,"
                   + " " + DisplayConfigurator.Latitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
                   + " " + DisplayConfigurator.Longitude_Mask.replace(DisplayConfigurator.Location_Abrv,'l')+","
		           + " l.elevation_min,"
		           + " tsl.tectonic_setting_name,"
		         //+ " rt.rocktype_code||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name), "
		           + " rc.rockclass||decode(nvl(rt.rocktype_name, ' '),' ',' ',', '||rt.rocktype_name), "
		           + " p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year),"
		           + " r.ref_num, prepared.method_code, s.sample_num,"
 		           + " l.latitude, l.longitude,"
		           + " e.expedition_name||decode(nvl(e.leg, '-'), '-', ' ','-'||e.leg) name_leg, e.expedition_num, info.igsn sample_igsn"
		           + " from material m, person p, author_list al, reference r, rocktype rt,"
		           + " rockclass rc, sample_comment sc, location l, station_by_location sbl,"
		           + " tectonic_setting_list tsl, station st, expedition e, sample s,igsn_info info,";
			
			innerQry = ""
			+ " (select c.sample_num, c.data_quality_num, mth.method_code, c.ref_num, c.material_num"
			+   inner_select
			+ " from item_measured im, method mth, data_quality dq, sample_chemistry c"
			+ " where im.item_measured_num 	= c.item_measured_num"
			+ " and mth.method_num 		= dq.method_num"
			+ " and dq.data_quality_num 	= c.data_quality_num"
			+ " and c.material_num in " + a_t
		//	+ " and (" + DisplayConfigurator.toReplace(filter,'c')+ ")"
             + " and (" + replace + ")"
			+ " group by c.sample_num, c.data_quality_num, mth.method_code, c.ref_num, c.material_num) prepared";
			
		    query += innerQry
		          + " where  st.station_num(+)     = s.station_num"
		          + " and e.expedition_num(+)    	 = st.expedition_num"
		          + " and  m.material_num	 	 = prepared.material_num"
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
                

		    /*
		      outerCondition = ""
		                     + ( condition.length() == 0
                             ? " (" + general_condition + ") <> 0 "
                             : " (" + condition + " OR (" + general_condition+")<>0)"

                );
		    */

		    query += " and " + outerCondition
	 	          + " order by s.sample_id,m.material_code, prepared.data_quality_num";
	}

//	DataDCtlQuery.writeQueryToFile(innerQry,"C:\\Users\\Bai-Hao\\Downloads\\bydatacriteria_qry.txt");
//	DataDCtlQuery.writeQueryToFile(outerCondition,"C:\\Users\\Bai-Hao\\Downloads\\bydatacriteria_outerqry.txt");
//	DataDCtlQuery.writeQueryToFile(outerCondition,"C:\\Users\\Lulin Song\\Downloads\\bydatacriteria_innerqry.txt");
	
    return query;
    }
        
        /**
         * Specifically for ByDataCriteria, 
         * Compiled Chemistry outercondition must always be 
         * in the form of OR query. Otherwise ByDataCriteria
         * should use buildQueryParts of ByChemistryCriteria.
         * 
         * @param   Criteria    criteria object
         * @param   String      filter
         * @see     petdb.criteria.ByChemistryCriteria.buildQueryParts(Criteria criteria, String filter)
         */
        protected void buildQueryParts(Criteria criteria, String filter)
        {   
            if (criteria instanceof ByDataCriteria)
            {
                if ( !((ByDataCriteria)criteria).getDataType().equals(ByDataCriteria.Compiled) )
                {
                    super.buildQueryParts(criteria, filter);
                }   
                else if ( ((ByDataCriteria)criteria).getDataType().equals(ByDataCriteria.Compiled) )
                {
                    //System.out.println("invoked ByDataCriteria.buildQueryParts");
                    boolean very_first  = true;
                    outerCondition ="";
                    innerQry = "";
                    inner_select ="";
                    outer_select="";
                    general_condition ="";
                    condition ="";
                    String g_condition = "";
 
                    String[] item;
                
                    DataSet ds = criteria.getWrapper().getControlList("0");
                    String[] s_keys  =((DataRecordDS)ds).getOrderedKeys();
                    int counter = 0;
                
                
                
                    for (int j=0; j<s_keys.length; j++)
                    {
                        item   = ((ByChemistryCriteria)criteria).getParam(s_keys[j]);
                        if (item == null) continue;
                        // THIS IS FOR LOGICAL AND ON CHEMICAL ELEMENTS - loop is wierd so, add SQL AND to query string
                        //if (general_condition.length() != 0 && ((ByChemistryCriteria)criteria).isANDQuery()) general_condition += " and ";
                    for (int i=0; i< item.length; i++)
                    {
                        inner_select += " ,sum(decode(" + target_f +",'" + item[i] + "',"+ target_v+"+10,0)) " + regulate(s_keys[j],item[i]);
                        if ((i == 0 ) && (very_first)) {
                            outer_select +=  " decode(prepared." + regulate(s_keys[j],item[i]) +
                                ",0,' ',prepared." + regulate(s_keys[j],item[i]) +"-10) ";
                        } 
                        else {
                            outer_select +=  ", decode(prepared." + regulate(s_keys[j],item[i]) +
                                 ",0,' ',prepared." + regulate(s_keys[j], item[i]) +"-10) ";
                        }
        
                                        // Check if passed criteria needs logical OR on selected chemical elements
                                        //if (((ByChemistryCriteria)criteria).isORQuery()){
                                            
                                            if (general_condition.length() == 0) 
                                                general_condition +=  " (prepared." +  regulate(s_keys[j],item[i]);
                                            else
                                                general_condition +=  " +  prepared." +  regulate(s_keys[j],item[i]);
                                        //}
                                        // Check if passed criteria needs logical AND on selected chemical elements
                                        //if (((ByChemistryCriteria)criteria).isANDQuery()){
                                        //    
                                        //    if ( i < item.length - 1)
                                        //        general_condition +=  " prepared." +  regulate(s_keys[j],item[i]) + " <> 0 and ";
                                        //    else
                                        //        general_condition +=  " prepared." +  regulate(s_keys[j],item[i]) + " <> 0 ";
                                        //}
                                        
                        if (inItems(criteria,item[i]))
                        {
                            counter++;
                            if (g_condition.length() == 0)
                                g_condition +=  
                                    "( prepared." +  regulate(s_keys[j],item[i]) + " =0 "; 
                            else 
                                g_condition +=  
                                    " OR prepared." +  regulate(s_keys[j],item[i]) + " =0 ";
                        }	
        
                    }
                    very_first = false;
                }
                if (g_condition.length() != 0 )
                    if (counter == 1) g_condition =" OR " + g_condition + ") ";
                    else g_condition =" AND " + g_condition + ") ";
        
                        // THIS IS FOR LOGICAL OR ON CHEMICAL ELEMENTS
                general_condition += ") <> 0 ";
                        
                item   = ((ByChemistryCriteria)criteria).getParam(ByChemistryCriteria.ITEM);
                String[] type = ((ByChemistryCriteria)criteria).getParam(ByChemistryCriteria.TYPE);
                String[] limit_1 = ((ByChemistryCriteria)criteria).getParam(ByChemistryCriteria.LIMIT_1);
                String[] limit_2 = ((ByChemistryCriteria)criteria).getParam(ByChemistryCriteria.LIMIT_2);
                String[] method  = ((ByChemistryCriteria)criteria).getParam(ByChemistryCriteria.METHOD);
                boolean l1 = false;
                boolean l2 = false;
                boolean m  = false;
                
                if (item != null)
                
                for (int i=0; i< item.length; i++)
                {
                    if ( inner_select.indexOf("'" + item[i] + "'") < 0)
                    {
                        inner_select += " ,sum(decode("+target_f +",'" + item[i] +
                             "'," +target_v + " +10,0)) " + regulate(type[i],item[i]);
                    }
                    String temp_c = " prepared." + regulate(type[i],item[i]) + " =0 OR (";
        
                    if  ((l1 = !limit_1[i].equals("")) == true)  
                        temp_c += " (prepared." + regulate(type[i],item[i]) + " -10) " + limit_1[i];
                    
                    if ( ( l2 = !limit_2[i].equals("") ) == true)
                                if (l1)
                                        temp_c += " and (prepared." + regulate(type[i],item[i]) + " -10) " + limit_2[i];
                                else
                                        temp_c += " (prepared." + regulate(type[i],item[i])  + " -10) " + limit_2[i];
        
                   
                    if ( (m = !method[i].equals("")) == true)
                                if (l1 || l2)
                                        temp_c += " and prepared.method_code in ('" + convertToList(method[i]) + "')";
                                else
                                        temp_c += " (prepared."
                                                +  regulate(type[i],item[i]) + " <> 0 and "
                                                + " prepared.method_code in ('" + convertToList(method[i]) + "'))";
        
                    
                    if (l1 || l2 || m) 
                    {
                        if (condition.length() != 0 )
                            condition += " AND (" + temp_c + "))";  	
                        else condition += " ( " + temp_c + "))";  	
                    }
                }
        
                    outerCondition = ""
                        + ( condition.length() == 0
                                ? " (" + general_condition + ") "
                                : " (" + condition + " ) AND " + general_condition + " "
                          );
        
                    //System.out.println("---------------------------------" );
                    //System.out.println(general_condition);
                    //System.out.println("");
                        }
                    }
            

	}
    
    private String getReplace(Criteria criteria, String filter, String replace)
    {
        String itemCodes =  criteria.getItemCodes();    
        int size = itemCodes.split(",").length;
        String [] arr = replace.split("\\(");
        replace = arr[0]+" (select sc2.SAMPLE_NUM from SAMPLE_CHEMISTRY sc2, ITEM_MEASURED im2 where sc2.ITEM_MEASURED_NUM = im2.ITEM_MEASURED_NUM and im2.ITEM_CODE in ("+itemCodes+") and sc2.SAMPLE_NUM in ("+
                    arr[1]+" group by  sc2.SAMPLE_NUM having count(distinct im2.ITEM_CODE) ="+size+")";
        return replace;
    }

}

