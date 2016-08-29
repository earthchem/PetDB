
package petdb.criteria;

import java.util.*;
import java.sql.*;

public class CCriteriaCollection 
{

	public static String CurrentCCriteria = "currentCCriteria";
	private Hashtable ccriterias;
	private String currentCCriteria = CurrentCCriteria; 
	
	public CCriteriaCollection()
	{
		ccriterias = new Hashtable();
	}

	public int saveCCriteria(String name, CombinedCriteria ccriteria) throws Exception
	{
		if (ccriterias.containsKey(name))
			ccriterias.remove(name);
		ccriterias.put(name,ccriteria.clone());
		return 1;
	}

	public  CombinedCriteria getCurrentCCriteria()
	{
		if (ccriterias.containsKey(currentCCriteria))
			return (CombinedCriteria)ccriterias.get(currentCCriteria);
		else
			if (currentCCriteria.equals(CurrentCCriteria))
			{
				ccriterias.put(currentCCriteria, new CombinedCriteria());
				return (CombinedCriteria)ccriterias.get(currentCCriteria);
			} else  	
				return null;
	}

	public int retriveCCriteria(String name) throws Exception
	{
		if (ccriterias.containsKey(name))
		{
			ccriterias.remove(CurrentCCriteria);
			ccriterias.put(CurrentCCriteria, ((CombinedCriteria)(ccriterias.get(name))).clone());
			return  1;
		}
		return -1;
	}

	public Vector getSavedCCriterias()
	{
		Vector  ret_value =new Vector(ccriterias.keySet());
		Collections.sort(ret_value, new VectorComparator());
		return ret_value;
	}

	public int clearCCriteria( String name)
	{
		if (ccriterias.containsKey(name))
		{
			ccriterias.remove(name);
			return 1;
		}
		else return -1; 
	} 

	public int clearAll()
	{
		ccriterias.clear();
		return 1; 
	} 

}

class VectorComparator implements Comparator
{
	public int compare(Object o1, Object o2)
	{
		return ((String)o1).compareTo((String)o2);
	}
	
	public boolean equals(Object o)
	{
		return true; 
	}
}

