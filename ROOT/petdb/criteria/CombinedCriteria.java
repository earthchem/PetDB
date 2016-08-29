
package petdb.criteria;

import java.util.*;
import java.sql.*;

public class CombinedCriteria implements Cloneable 
{

	public static String ByGeoCriteria 	= "ByGeoCriteria"; 
	public static String ByLongLatCriteria  = "ByLongLatCriteria"; 
	public static String ByTectCriteria 	= "ByTectCriteria"; 
	public static String ByRockCriteria 	= "ByRockCriteria"; 
	public static String ByExpCriteria 	= "ByExpCriteria" ;
	public static String ByPubCriteria 	= "ByPubCriteria"; 
	public static String ByDataCriteria 	= "ByDataCriteria"; 
	public static String ByMineralCriteria 	= "ByMineralCriteria"; 
	public static String ByInclusionCriteria = "ByInclusionCriteria";
	public static String ByMeltInclusionCriteria = "ByMeltInclusionCriteria";
	public static String ByFluidInclusionCriteria = "ByFluidInclusionCriteria";
	public static String ByDataAvailCriteria = "ByDataAvailCriteria"; 
	public static String ByDataVersionCriteria = "ByDataVersionCriteria"; 
	public static String BySampleIDCriteria	= "BySampleIDCriteria"; 
	public static String ByRockModeCriteria ="ByRockModeCriteria"; 
    public static String ByDiamondCriteria ="ByDiamondCriteria";
    public static String ByXenolithCriteria ="ByXenolithCriteria";		
	public static String SampleCriteria	= "SampleCriteria";
	
	private String last_criteria;
	
	private String criteria_type= ByDataCriteria;

	private Hashtable criterias;
	
	Criteria sampleCriteria;
   

	private String quickSearch;

	public CombinedCriteria() 
	{
		criterias = new Hashtable();
	}

	
	
	public void initDataCriteria()
	{
		Criteria c = getCriteria(ByDataCriteria);
		if (c!= null) c.initCriteria();
		c = getCriteria(ByMineralCriteria);
		if (c!= null) c.initCriteria();
		c = getCriteria(ByInclusionCriteria);
		if (c!= null) c.initCriteria();

	}
    
	 public String getQuickSearch() {
			return quickSearch;
		}



		public void setQuickSearch(String quickSearch) {
			this.quickSearch = quickSearch;
		}

	public String getActiveChemistryCriteria() { return criteria_type;}
	public void setActiveChemistryCriteria(String c_t) {  criteria_type = c_t;}

	public int runCriteria(String session_id)
	{
		clear(ByDataCriteria);
		clear(ByMineralCriteria);
		clear(ByInclusionCriteria);
		clear(ByRockModeCriteria);
           
		if (sampleCriteria == null)
		{        
			sampleCriteria = new SampleCriteria(this, session_id);
			return 1;
		}
		else 
			return ((SampleCriteria)sampleCriteria).update(this);
	}


    public int runCriteria2(String session_id)
	{
		clear(ByDataCriteria);
		clear(ByMineralCriteria);
		clear(ByInclusionCriteria);
		clear(ByRockModeCriteria);
             
			sampleCriteria = new SampleCriteria(this, session_id);
			return 1;
	}
    
	public int clearSampleID()
	{
		Criteria c = getCriteria(BySampleIDCriteria);
		if (c != null)
			if (c.isSet()) c.clear();
		return 1; 
	}

	public int clear(String name)
	{
		//System.out.println("Clear Criteria = " + name);
		if (criterias.containsKey(name))
		{
			last_criteria = name;
			if (	(!ByDataCriteria.equals(name))
				&& 
				(!ByMineralCriteria.equals(name))
				&&
				(!ByInclusionCriteria.equals(name))
				&&
				(!ByRockModeCriteria.equals(name))
			)
			{
				Criteria c = getCriteria(BySampleIDCriteria);
				if (c != null) c.clear();
			}
			if (sampleCriteria != null) ((SampleCriteria)sampleCriteria).clear(); 
			return ((Criteria)criterias.get(name)).clear();
		}	
		else return -1;
	}

	public int clear() 	
	{
                synchronized(criterias)
                {
                        for (Enumeration e = criterias.keys() ; e.hasMoreElements() ;)
                        {
                           
                        	Object key  = e.nextElement();
                        	Criteria value = (Criteria)criterias.get(key);
   				int t = value.clear();
                        }
			if (sampleCriteria != null) {
                if (sampleCriteria.getWrapper() != null) sampleCriteria.getWrapper().clear();            
                int  tt = sampleCriteria.clear();
            }
		}
		if (sampleCriteria != null) ((SampleCriteria)sampleCriteria).clear(); 
		return 1;
	}


	public int addCriteria(String name, Criteria c) 
	{
		synchronized(criterias)
		{
			if (criterias != null){
				last_criteria = name;
				criterias.put(name,c);
			}
			return 1;
		}
	}

	public Criteria getLast()
	{
		return getCriteria(last_criteria);
	}

	public Criteria getCriteria(String name) 	
	{
		synchronized(criterias)
		{
			last_criteria = name;
			return ((criterias !=  null) ? (Criteria)criterias.get(name) : null);
		}
	}


	public Criteria getSampleCriteria(String session_id)
	{
		if (sampleCriteria == null)
			return new SampleCriteria(this, session_id);
		else return sampleCriteria;
	}

	public int size() 
	{ 
		synchronized(criterias)
		{
			int size = 0;
               		for (Enumeration e = criterias.keys() ; e.hasMoreElements() ;)
               		{
                   		Object key  = e.nextElement();
                   		Criteria value = (Criteria)criterias.get(key);
                   		if (value.isSet()) size +=1;
               		}
			return size; 
		}
	}

	public boolean isSet(String name)
	{
		Criteria c = getCriteria(name);
		if ( c != null)
			return c.isSet();
		else return false;
	}

	public boolean isSet()
	{
	       boolean not_set = true;
               for (Enumeration e = criterias.keys() ; e.hasMoreElements() ;)
               {
                   Object key  = e.nextElement();
                   Criteria value = (Criteria)criterias.get(key);
                   not_set = !value.isSet();
		   if (!not_set) break;
               }

		return  (!not_set);
	
	}

	public Enumeration getKeys()
	{
		synchronized(criterias)
		{
			return (criterias != null ? criterias.keys() : null);
		}
	}

	public String getSDsc(String name)
	{
		String ret = getDsc(name);
		ret = ret.substring(0, (ret.length() > 60 ? 60 : ret.length()));  
		return ret += "...";
	}

	public String getDsc(String name)
	{
		Criteria c = getCriteria(name);
		if (c != null)
			return c.getDescription();
		else return "";
	}


	public String toString()
	{
                synchronized(criterias)
                {
                        String str = "";
                        for (Enumeration e = criterias.keys() ; e.hasMoreElements() ;)
                        {
                        Object key  = e.nextElement();
                        Object value = criterias.get(key);
                        str += key.toString() + " " + value.toString() + "\n";
                        }
                        return str;
                }
		
		
	}

	public Object clone() throws CloneNotSupportedException
	{
		CombinedCriteria cc = (CombinedCriteria)super.clone();
		cc.criterias = (Hashtable)criterias.clone();
                for (Enumeration e = criterias.keys(); e.hasMoreElements(); )
                {
              		String  key = (String)e.nextElement();
                        Criteria v  = (Criteria)criterias.get(key);
                        cc.criterias.put(key,(Criteria)v.clone());
                }
		cc.last_criteria = last_criteria;
		return cc;

	}
	
}


