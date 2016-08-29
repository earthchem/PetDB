package admin.dbAccess;

import admin.data.ReferenceExcelDownload;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReferenceDS {
//	protected DatabaseAccess da;
	protected ResultSet r_set;
    public String refNum;
    protected List dsList;
	
	public ReferenceDS(String qry)
	{
	}
   
    public ReferenceDS(String qry, int size)
	{
        SimpleQuery sq = new SimpleQuery(qry); 
        dsList = sq.getList(size);
	}
     
     public List getList() {
        return dsList;
    }
        

    public ResultSet getResultSet() {return r_set;};
    
    public String refNum() {return refNum;}
    
    protected static String getTableInRefTable(String v_filter)  {
	  return  " select table_in_ref, table_title from table_in_ref where ref_num =" + v_filter;
   }
     protected static String getStation2DS (String v_filter) {
       return  "select s.STATION_ID, s.EXPEDITION_NUM, s.STATION_ID, sh.SHIP_NAME, e.LEG, sc.STATION_COMMENT, st.SAMP_TECHNIQUE_CODE, n.NAVMETHOD_code, s.SAMP_DATE, "+
				" l.LATITUDE, l.LONGITUDE, l.ELEVATION_MIN, l.ELEVATION_MAX, "+
				" l.LOC_PRECISION, l.LOCATION_COMMENT, l.LAND_OR_SEA, ts.TECTONIC_SETTING_NAME, l.LOCATION_NUM, sl.LOCATION_ORDER "+
				" from STATION s INNER JOIN STATION_BY_LOCATION sl ON s.STATION_NUM = sl.STATION_NUM "+
				" LEFT OUTER JOIN SAMP_TECHNIQUE_LIST st ON  s.SAMP_TECHNIQUE_NUM = st.SAMP_TECHNIQUE_NUM "+
				" LEFT OUTER JOIN (select * from  STATION_COMMENT where ref_num = " + v_filter + ") sc ON s.STATION_NUM = sc.STATION_NUM "+
				" LEFT OUTER JOIN NAVMETHOD n ON s.NAVMETHOD_NUM = n.NAVMETHOD_NUM "+
				" LEFT OUTER JOIN EXPEDITION e ON s.EXPEDITION_NUM = e.EXPEDITION_NUM "+
				" LEFT OUTER JOIN SHIP sh ON e.SHIP_NUM = sh.SHIP_NUM "+
				" INNER JOIN LOCATION l ON sl.LOCATION_NUM = l.LOCATION_NUM "+
				" LEFT OUTER JOIN TECTONIC_SETTING_LIST ts ON l.TECTONIC_SETTING_NUM = ts.TECTONIC_SETTING_NUM "+
				" where sl.LOCATION_ORDER <> 5 and s.STATION_NUM IN "+
				" (SELECT SA.STATION_NUM FROM SAMPLE sa, BATCH ba, TABLE_IN_REF ti "+
				" WHERE  ba.SAMPLE_NUM = sa.SAMPLE_NUM and ba.TABLE_IN_REF_NUM = ti.TABLE_IN_REF_NUM and ti.REF_NUM = " + v_filter + " ) order by s.STATION_NUM, sl.LOCATION_ORDER"; 
     }
     
      protected static String getSample2DS (String v_filter)  {
        return "select s.sample_id, ss.alias, st.station_id, ss.sample_comment, rt.ROCKTYPE_CODE, rc.ROCKCLASS, "+
				" ss.ROCKCLASS_DETAIL, ss.ROCKTEXTURE,a.ALTERATION_CODE, a.ALTERATION_NAME, sa.geol_age, sa.geol_age_prefix, sa.age_min,sa.age_max "+
				" from sample s, station st, sample_comment ss left outer join alteration a on ss.ALTERATION_NUM = a.alteration_num "+
			    " left outer join sample_age sa on ss.SAMPLE_NUM  = sa.SAMPLE_NUM and ss.REF_NUM  =sa.REF_NUM "+
			    " left outer join rockclass rc join ROCKTYPE rt on rc.rocktype_num = rt.ROCKTYPE_NUM on ss.ROCKCLASS_NUM = rc.ROCKCLASS_NUM	 "+
				" where ss.sample_num = s.sample_num and ss.REF_NUM = " + v_filter +
				" and st.station_num = s.station_num and s.sample_num in "+
				" (select b.sample_num from batch b, analysis an, data_quality d "+
				" where b.BATCH_NUM = an.BATCH_NUM and an.DATA_QUALITY_NUM = d.DATA_QUALITY_NUM and d.REF_NUM = ss.REF_NUM and ss.REF_NUM = " + v_filter+")";       
      }
      
       protected static String getMethod2DS(String v_filter) {
       return  "select m.METHOD_CODE,d.INSTITUTION_NUM,d.METHOD_COMMENT,d.DATA_QUALITY_NUM from data_quality d, method m "+
				" where m.METHOD_NUM = d.METHOD_NUM and d.ref_num ="+ v_filter +" order by d.DATA_QUALITY_NUM";
       }
       
       
        protected static String getRock2DS (String v_filter) {
            return "select b.BATCH_NUM, t.TABLE_IN_REF, a.ANALYSIS_COMMENT,a.NUM_ANALYSES, a.CALC_AVE, m.MATERIAL_CODE, C.VALUE_MEAS||decode(c.STDEV, null, '',', '||c.STDEV), c.ITEM_MEASURED_NUM||d.DATA_QUALITY_NUM val_col"+ 
				" from batch b, table_in_ref t, data_quality d, ANALYSIS a, CHEMISTRY C, MATERIAL m "+
				" where a.DATA_QUALITY_NUM = d.DATA_QUALITY_NUM and d.ref_num ="+ v_filter+
				" and b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and a.BATCH_NUM = b.BATCH_NUM and b.MATERIAL_NUM = m.MATERIAL_NUM "+
				" AND A.ANALYSIS_NUM = C.ANALYSIS_NUM and a.BATCH_NUM not in "+
				" (select b.BATCH_NUM from batch b, table_in_ref t, mineral m, inclusion i "+
				" where b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and (m.BATCH_NUM = b.BATCH_NUM or i.BATCH_NUM = b.BATCH_NUM)and t.REF_NUM ="+ v_filter+" ) "+
				" ORDER BY b.BATCH_NUM ";
        }
        
       public static String  getMineral2DS (String v_filter) {
        return "select b.BATCH_NUM, t.TABLE_IN_REF, a.ANALYSIS_COMMENT, mi.SPOT_ID,a.NUM_ANALYSES, a.CALC_AVE,ml.mineral_code, cl.CRYSTAL_CODE, mi.RIM_OR_CORE, mi.MINERAL_SIZE, "+
				" C.VALUE_MEAS||decode(c.STDEV, null, '',', '||c.STDEV), c.ITEM_MEASURED_NUM||d.DATA_QUALITY_NUM val_col, SC.ALIAS  "+
				" from batch b, table_in_ref t, data_quality d, ANALYSIS a, CHEMISTRY C, SAMPLE_COMMENT SC, mineral_list ml, mineral mi LEFT OUTER JOIN CRYSTAL_LIST cl on mi.CRYSTAL_NUM = cl.CRYSTAL_NUM	"+	
				" where a.DATA_QUALITY_NUM = d.DATA_QUALITY_NUM and b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and a.BATCH_NUM = b.BATCH_NUM AND A.ANALYSIS_NUM = C.ANALYSIS_NUM "+
				" and mi.BATCH_NUM = b.BATCH_NUM and ml.MINERAL_NUM = mi.MINERAL_NUM and SC.SAMPLE_NUM = B.SAMPLE_NUM and sc.REF_NUM= d.REF_NUM AND SC.REF_NUM = "+v_filter+" order by t.TABLE_IN_REF_NUM, b.BATCH_NUM";

       }
       
        protected static String  getInclusion2DS (String v_filter) {
            return  "select  b.BATCH_NUM, t.TABLE_IN_REF, inc.SPOT_ID,a.NUM_ANALYSES, a.CALC_AVE, inc.INCLUSION_TYPE, ml.mineral_code, ml2.MINERAL_CODE, inc.MINERAL_BATCH_NUM,null,  inc.INCLUSION_SIZE, inc.RIM_OR_CORE,"+
				 " inc.HEATING, inc.HEATING_TEMPERATURE, C.VALUE_MEAS||decode(c.STDEV, null, '',', '||c.STDEV), c.ITEM_MEASURED_NUM||d.DATA_QUALITY_NUM val_col, SC.ALIAS "+
				 " from batch b, table_in_ref t, ANALYSIS a, CHEMISTRY C, data_quality d, INCLUSION inc left join mineral_list ml on inc.MINERAL_NUM = ml.MINERAL_NUM " +
				 " left join mineral_list ml2 on inc.HOST_MINERAL_NUM = ml2.MINERAL_NUM, SAMPLE_COMMENT SC "+	
				 " where b.BATCH_NUM = a.BATCH_NUM and b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and a.ANALYSIS_NUM = c.ANALYSIS_NUM and a.DATA_QUALITY_NUM = d.DATA_QUALITY_NUM "+
				 " and b.BATCH_NUM = inc.BATCH_NUM and d.REF_NUM = sc.REF_NUM and b.SAMPLE_NUM = sc.SAMPLE_NUM and d.REF_NUM = " + v_filter +" order by t.TABLE_IN_REF_NUM, b.BATCH_NUM";
        }
    
     public List getList(int size) {
        return dsList;
    }
        
}