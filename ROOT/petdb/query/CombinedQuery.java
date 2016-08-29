
package petdb.query;


import java.util.*;
import java.sql.*;
import petdb.criteria.*;
import petdb.data.*;
import petdb.config.*;

class CombinedQuery extends Query
{

	protected CombinedCriteria ccriteria;

	public CombinedQuery()
	{
		ccriteria = null;
	}

	public CombinedQuery(CombinedCriteria cc)
	{
		ccriteria = cc;
		setQuery();
	}

	public int setCombinedCriteria(CombinedCriteria cc)
	{
		ccriteria = cc;
		qry = "";
		return setQuery();
	}
		
	public String getQryStr()
	{
		return " " + DisplayConfigurator.toBeReplaced + ".sample_num IN ("+qry+")";
	}
	

	protected int setQuery()
	{
		int i = 0;
		Criteria s_c = ccriteria.getCriteria(CombinedCriteria.BySampleIDCriteria);
		if ((s_c == null) || (!s_c.isSet(BySampleIDCriteria.INCLUDE)) )
		{ 
		for (Enumeration e = ccriteria.getKeys() ; e.hasMoreElements() ;) 
		{
			String key = (String)e.nextElement();
			
			if (!key.equals(CombinedCriteria.BySampleIDCriteria))
			{
			Criteria criteria = (Criteria)ccriteria.getCriteria(key);
	 		
			if ( (criteria.isSet()) && (criteria.getQryStr().length() != 0) )
			{
					if (i == 0 ) 
					{ 
		 				qry += " (" +
			 				criteria.getQryStr() + ")";
		 				i++;
					}
					else  
 		 				qry += " AND sample.SAMPLE_NUM IN (" + criteria.getQryStr() + ")";
			}
			}
		}
		Criteria criteria = (Criteria)ccriteria.getCriteria(CombinedCriteria.BySampleIDCriteria);
		if ((criteria!= null) && criteria.isSet())	
			qry += criteria.getQryStr();
		} else {
		if ((s_c!= null) && (s_c.isSet()) )	
			qry += s_c.getQryStr();
		
		} 
		
		
		return 1;
	}



	public synchronized void prepareData() 
	{
		/*{  Commented out temporary it caused compiling failure. -- Lulin Song
		try 
		{
			//ds = (DataSet) new DSRecordValue(); //was commented when I got the code. --Lulin
		} 
		catch (Exception e) 
		{
		}
		}*/
	}

	public String toString(){
		return qry;
	}


}
