
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;
import petdb.config.*;
import javax.servlet.http.*;

public class ByDataAvailCriteria extends Criteria 
{

	public static String Material_Analysis	= "0";
	public static String Inclusion_Analysis	= "1";
	public static String Mineral_Analysis	= "2";

	public static String Any_Inclusion	= "Any_Inclusion";
	public static String Any_Mineral	= "Any_Mineral";
	public static String Any_Material	= "Any_Material";
	public static String Rock		= "Rock";
	public static String Glass		= "Glass";

	boolean any_inclusion = false;
	boolean any_mineral  = false;
	
	public ByDataAvailCriteria() 
	{
		parameters = new Hashtable();
		dataWrapper = new ByDataAvailWrapper();
		qryModel = new ByDataAvailQryModel();
	}

        public String getDescription()
        {
		String ret = "";
		if (isAnyMineral()) ret += "Any Mineral Analysis";
		else 
			if (isSet(Mineral_Analysis))
				ret += "Mineral Analysis: " + getDescription(Inclusion_Analysis,Mineral_Analysis); 
		
		if (isAnyInclusion())
			if (ret.length() == 0)
				 ret += "Any Melt Inclusion";
			else ret += "; Any Melt Inclusion";
		else 
		{
			if (isSet(Inclusion_Analysis))
				if (ret.length() == 0)
					 ret += "Inclusion in: " + getDescription(Inclusion_Analysis);
				else ret += "; Inclusion in: " + getDescription(Inclusion_Analysis);
		}

		
		if (isSet(Any_Material))
		{
			String m="";
			if (isSelected(ByDataAvailCriteria.Any_Material,ByDataAvailCriteria.Glass))
				m = "Glass";
			if (isSelected(ByDataAvailCriteria.Any_Material,ByDataAvailCriteria.Rock))
				m += (m.length()==0) ? "Rock" : ",Rock";
			
			if (ret.length() == 0)
				 ret += " " + m + " Analysis";
			else ret += "; " + m + " Analysis";
		}
		if (isSet(Material_Analysis))
			if (ret.length() == 0)
				 ret += "Rock Analysis: " + getDescription(Material_Analysis);
			else ret += "; Rock Analysis: " + getDescription(Material_Analysis);
		ret =ret.replaceAll("''","'");
                return ret;

        }
 

       public String getDescription(String name, String p_name)
        {
                String[] val; 
		String code;
                if ( (val = (String[])parameters.get(p_name))!= null)
                {
                        String str ="";
                        if ((dataWrapper != null) && (dataWrapper.getListIndex(name) != -1))
                        {
                                for (int i = 0 ; i< val.length; i++)
				{
					code = dataWrapper.getStrForKey(name,val[i]);
                                        if (i == 0)
                                                str +=ByDataAvailConfigurator.getDescription(p_name,code);
                                        else
                                                str +="; " +ByDataAvailConfigurator.getDescription(p_name,code);
				}
                        } else {
                                for (int i = 0 ; i< val.length; i++)
                                        if (i == 0)
                                                str += ByDataAvailConfigurator.getDescription(p_name,val[i]);
                                        else
                                                str +="; " +  ByDataAvailConfigurator.getDescription(p_name,val[i]);
                        }
                        return str;
                }
                else return "";
        }

        public String getDescription(String name)
        {
                String[] val;
                if ( (val = (String[])parameters.get(name))!= null)
                {
                        String str ="";
			String code  = "";
                        if ((dataWrapper != null) && (dataWrapper.getListIndex(name) != -1))
                        {
                                for (int i = 0 ; i< val.length; i++)
				{
					code = dataWrapper.getStrForKey(name,val[i]);
                                        if (i == 0)
                                                str +=ByDataAvailConfigurator.getDescription(name,code);
                                        else
                                                str +="; " + ByDataAvailConfigurator.getDescription(name,code);
                        	}
			} else {
                                for (int i = 0 ; i< val.length; i++)
                                        if (i == 0)
                                                str += ByDataAvailConfigurator.getDescription(name,val[i]);
                                        else
                                                str +="; " +ByDataAvailConfigurator.getDescription(name,val[i]);
                        }
                        return str;
                }
                else return "";
        }

	public void setAnyInclusion(){ any_inclusion = true;}
	public void setAnyMineral(){ any_mineral = true;}
	public boolean isAnyInclusion(){return any_inclusion;}
	public boolean isAnyMineral(){return any_mineral;}
	
	public int clear()
        {
                int ret = super.clear();
		any_inclusion = false;
		any_mineral = false;
                return 1;
        }

        public boolean isSet()
        {
                return ((parameters.size() != 0 ? true : false) || (any_inclusion)||(any_mineral));
        }

        public void updateQuickSearch(Map map, String source, HttpServletRequest request) {
        	String arr[] = null;
            String ipAddress = IPAddress.getIpAddr(request);
            String insert = "insert into QUICK_SEARCH (IP_ADDRESS,SEARCH_DATE,DATASOURCE_NAME,SEARCH_NAME,SEARCH_GROUP) values ('"+ipAddress+"',SYSDATE,'"+source+"','";;
            
            if (isAnyMineral()) new SimpleQuery(insert+Any_Mineral+"','Mineral Analysis')");
            if (isAnyInclusion()) new SimpleQuery(insert+Any_Inclusion+"','Melt Inclusions')");    
            if (isSelected(ByDataAvailCriteria.Any_Material,ByDataAvailCriteria.Rock)) {
                 new SimpleQuery(insert+"whole rock analyses"+"','Rock Analyses')");
            }
            if (isSelected(ByDataAvailCriteria.Any_Material,ByDataAvailCriteria.Glass)) {
                 new SimpleQuery(insert+"glass analyses"+"','Rock Analyses')");
            }            
            if (isSet(ByDataAvailCriteria.Material_Analysis)) {
                arr = (String[])parameters.get(ByDataAvailCriteria.Material_Analysis);
                insertArray(arr, "rock",insert, map, "Rock Analyses");
            }
            if (isSet(ByDataAvailCriteria.Mineral_Analysis)) {
                arr = (String[])parameters.get(ByDataAvailCriteria.Mineral_Analysis);
                insertArray(arr, "mineral",insert, map, "Mineral Analysis");
            }
            if (isSet(ByDataAvailCriteria.Inclusion_Analysis)) {
                arr = (String[])parameters.get(ByDataAvailCriteria.Inclusion_Analysis);
                insertArray(arr, "inclusion",insert, map, "Melt Inclusions");
            }
        }
       
        private void insertArray(String [] arr, String key, String insert, Map map, String group) {
            for(int i = 0; i < arr.length; i++)
            {
                    String v = (String) map.get(key+":"+arr[i]); 
                    new SimpleQuery(insert+v+"','"+group+"')");
            }
        }

}

class ByDataAvailQryModel extends QueryModel
{

        public String getQueryStr(Criteria criteria)
        {
		String query 		="";
		boolean inclusion 	=false;
		boolean mineral 	=false;
		boolean material 	=false;

		if (
			(criteria.isSet(ByDataAvailCriteria.Inclusion_Analysis))
			||
			(((ByDataAvailCriteria)criteria).isAnyInclusion())
		   )
                {
			query = " select b.sample_num"
				+" from  batch b, inclusion i"
				+" where b.batch_num = i.batch_num ";
            
            if(((ByDataAvailCriteria)criteria).isAnyInclusion()) query += " and i.inclusion_type in ('GL','GLASS') ";  
			
			if (criteria.isSet(ByDataAvailCriteria.Inclusion_Analysis))
				query +=" and i.host_mineral_num in "
				+  criteria.getValuesAsStr(ByDataAvailCriteria.Inclusion_Analysis,true);
			inclusion = true; 
		}
		
		
		if (
			(criteria.isSet(ByDataAvailCriteria.Mineral_Analysis))
			||
			(((ByDataAvailCriteria)criteria).isAnyMineral())
		   )
		{
			if ( inclusion)
				query +=" intersect ";
		
			query +=" select b.sample_num"
				+" from batch b, mineral m"
				+" where b.batch_num = m.batch_num";
				
			if (criteria.isSet(ByDataAvailCriteria.Mineral_Analysis))
				query += " and m.mineral_num in "
				+  criteria.getValuesAsStr(ByDataAvailCriteria.Mineral_Analysis,true);
			mineral = true;
		}

		
		if (
			(criteria.isSet(ByDataAvailCriteria.Material_Analysis))
			||
			(criteria.isSet(ByDataAvailCriteria.Any_Material))
		   )
		{
			String b = "";	
			if (
				(inclusion)
				||
				(mineral)
			    )
				query +=" intersect ";

			query += " select b.sample_num";

			if (criteria.isSet(ByDataAvailCriteria.Material_Analysis))
			{
				query += " from batch b";

				String[] vs = criteria.getValuesArray(ByDataAvailCriteria.Material_Analysis);
				String c1 = "";
				String c2 = "";
				for (int i = 0; i<vs.length; i++)
				{
					query += ", dn_sampleitemtype dn" + i;
					if (i == 0 )
						c1 += " where  b.sample_num = dn" + i + ".sample_num";
					else 
						c1 += "  and dn"+ (i-1)+ ".sample_num = dn" + i + ".sample_num";
					c2 += " and dn" + i + ".item_type_num =" + vs[i];      
				}
 				query += c1 + c2; 
				if  (criteria.isSet(ByDataAvailCriteria.Any_Material)) query += " and ";
			} else 
				query +=" from batch b"
					+ " where ";

			String materialType = "";
			if (criteria.isSet(ByDataAvailCriteria.Any_Material))
			{
				if (criteria.isSelected(ByDataAvailCriteria.Any_Material,ByDataAvailCriteria.Glass))
					materialType = "(3)";
				if (criteria.isSelected(ByDataAvailCriteria.Any_Material,ByDataAvailCriteria.Rock))
					materialType = (materialType.length()==0 ? "(7,8)" : "(3,7,8)");
				query += " b.material_num in " + materialType;
			}
			 
				
		}
        	return (criteria.isSet() ? "(" +query+")" : "");
        }


	public String getQueryStr(Criteria criteria, String filter)
	{
		String query = getQueryStr(criteria);
		query += " AND s.sample_num in " + filter;

		return query;
	}

}

