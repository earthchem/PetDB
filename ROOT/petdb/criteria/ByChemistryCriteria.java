package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;
import petdb.wrapper.Wrapper;
import petdb.data.*;

public abstract class ByChemistryCriteria extends Criteria 
{
	public static String MINERAL  = "0";
	
	public static String ITEM  = "Items";
	public static String TYPE  = "Type";
	public static String D_ITEM  = "D_Items";
	public static String LIMIT_1 = "Lim1";
	public static String LIMIT_2 = "Lim2";
	public static String METHOD = "Method";
	
	String filter1= "";
	
        /** 
         *  AND_OR_QUERY_FLAG - Logical AND/OR For Chemical Elements
         *  --------------------------------------------------------
         *  true -  OR query
         *  false - AND query
         *
         **/ 
	protected boolean AND_OR_QUERY_FLAG = true;
        
        public String[] getParam(String name)
	{
		return (String[])parameters.get(name);
	}

	public String[] getFields()
	{
		return (String[]) parameters.get(ITEM);
	}

	
	public abstract int getChemItemCount();

	public int getChemItemCount(String[] s_keys)
	{
		int field_count =0;
        	for (int j=0; j<s_keys.length; j++)
        	{
                	String[]  fields = getParam(s_keys[j]);
                	if (fields == null ) continue;
                	for (int i=0; i< fields.length; i++)
                        	field_count ++;
        	}
		return field_count;
	}


	public int getDItemCount()
	{
		return ((String[])parameters.get(ITEM)).length; 
	}
	
        public Wrapper getWrapper()
        {
                return dataWrapper;
        }
       
	public String isCSet()
	{
		return (isSet() ? "Super SET," 
				+ (isSet(ByInclusionCriteria.HostMineral) ? " HSET" : " H!SET")
				+ (isSet(ByMineralCriteria.Mineral_List) ? " MSET" : " M!SET")
			 : " Super !Set" 
				+ (isSet(ByInclusionCriteria.HostMineral) ? " HSET" : " H!SET")
				+ (isSet(ByMineralCriteria.Mineral_List) ? " MSET" : " M!SET")
 			);
	}
 
	public boolean isSet()
        {
                /*
		return  ( 
			(super.isSet())
			&&
			(
				(!isSet(ByInclusionCriteria.HostMineral))
				&&
				(!isSet(ByMineralCriteria.Mineral_List))
			)
			)
			;
		*/
		return (
			(super.isSet())
			&&
			(!isSet(ByInclusionCriteria.HostMineral))
			&&
			(!isSet(ByMineralCriteria.Mineral_List))
			);
        }


	public boolean setFilter1(String s)
	{
		filter1 = s;
		if (((ByChemistryWrapper)dataWrapper).selected(filter1))
			return false; 
		parameters.clear();
		((ByChemistryWrapper)dataWrapper).setSelected(filter1);
		return true;
	}

	public String toString()
	{
		return super.toString() + "Filter = " + filter1;
	}

	public String getDescription()
	{
		String str = "";
		String[] item = (String[])parameters.get(ITEM);
		String[] lim1 = (String[])parameters.get(LIMIT_1);
		String[] lim2 = (String[])parameters.get(LIMIT_2);
		String[] meth = (String[])parameters.get(METHOD);
		if (item != null)
		for (int i = 0; i<item.length; i++)
			str +=item[i] + " " + lim1[i] + " " + lim2[i] + " " + meth[i] + "\n";
		str =str.replaceAll("''","'");
		return str;
	}
        
        public void setORQuery()
        { 
            AND_OR_QUERY_FLAG = true; 
        }
        
        public void setANDQuery()
        { 
            AND_OR_QUERY_FLAG = false; 
        }
        
        public boolean isORQuery() 
        { 
            return AND_OR_QUERY_FLAG; 
        }
        
        public boolean isANDQuery()
        { 
            return !AND_OR_QUERY_FLAG; 
        }

	public String getInnerQuery(){ return ((ByChemistryQryModel)qryModel).getInnerQuery();}
        public String getOuterCondition(){return ((ByChemistryQryModel)qryModel).getOuterCondition();}


}

class ByChemistryQryModel extends QueryModel
{

	protected String outerCondition ="";
	protected String innerQry = "";
	protected String inner_select ="";
	protected String outer_select="";
	protected String general_condition ="";
	protected String condition ="";
	protected String target_f = "im.item_code";
	protected String target_v = "c.value_meas";
	
	String getInnerQuery() {return innerQry;}
	String getOuterCondition() {return outerCondition;}
		
	protected boolean inItems(Criteria c,String item)
	{
		String[] items   = ((ByChemistryCriteria)c).getParam(ByChemistryCriteria.ITEM);
		String[] limit_1 = ((ByChemistryCriteria)c).getParam(ByChemistryCriteria.LIMIT_1);
		String[] limit_2 = ((ByChemistryCriteria)c).getParam(ByChemistryCriteria.LIMIT_2);
		
		if (items == null) return false;
		for (int i=0; i<items.length;i++)
			if (items[i].equals(item))
				if  ( (limit_1[i].equals("")) && (limit_2[i].equals("")) )  return false;
				else return true;
			
		return false;
	}

	private boolean noNumericLimits(String[] l1, String[] l2)
	{
		if ((l1 == null) || (l2 == null))
			return true;
		if (l1 != null)
		for (int i = 0; i< l1.length; i++)
			if (!l1[i].equals("")) return false; 
		
		if (l2 != null)
		for (int i = 0; i< l2.length; i++)
			if (!l2[i].equals("")) return false; 
		
		return true;
	}

	protected void buildQueryParts(Criteria criteria, String filter)
	{   
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
                        if (general_condition.length() != 0 && ((ByChemistryCriteria)criteria).isANDQuery()) general_condition += " and ";
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
                                if (((ByChemistryCriteria)criteria).isORQuery()){
                                    
                                    if (general_condition.length() == 0) 
                                        general_condition +=  " (prepared." +  regulate(s_keys[j],item[i]);
                                    else
                                        general_condition +=  " +  prepared." +  regulate(s_keys[j],item[i]);
                                }
                                // Check if passed criteria needs logical AND on selected chemical elements
                                if (((ByChemistryCriteria)criteria).isANDQuery()){
                                    
                                    if ( i < item.length - 1)
					general_condition +=  " prepared." +  regulate(s_keys[j],item[i]) + " <> 0 and ";
                                    else
                                        general_condition +=  " prepared." +  regulate(s_keys[j],item[i]) + " <> 0 ";
                                }
                                
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
		if (general_condition.length() != 0 && ((ByChemistryCriteria)criteria).isORQuery()) general_condition += ") <> 0 ";
                
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
	} //end of BuildParts
	
	/* Will be overwritten by child */
    public String getQueryStr(Criteria criteria, String filter){ String query =""; return query; }



	protected String regulate(String t,String s)
	{
		StringBuffer sb = new StringBuffer(s);
		for (int i = 0; i < sb.length(); i++)
		{
			if (sb.charAt(i) == '(')
				sb.replace(i,i+1,"_");
			else if (sb.charAt(i) == '/')
				sb.replace(i,i+1,"_");
			else if (sb.charAt(i) == ')')
				sb.replace(i,i+1,"_");
		}
		sb.insert(0,t+"_");
		sb.insert(0,'\"');
		sb.insert(sb.length(),'\"');
		return sb.toString();
	}
		
	

	protected String convertToList(String s)
	{
		String ret = "";
		StringTokenizer st = new StringTokenizer(s,",");
		
		while (st.hasMoreTokens())
			if (ret.length() == 0) ret += st.nextToken();
			else ret += "','"+st.nextToken();

		return ret;
	}

        public String getQueryStr(Criteria criteria)
        {
		return "";
	}	
}
