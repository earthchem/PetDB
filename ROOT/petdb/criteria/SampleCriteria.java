
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.data.*;
import petdb.wrapper.*;

public class SampleCriteria extends Criteria {

	public static String SAMPLEIDs = "0";

	public SampleCriteria(CombinedCriteria cc, String session_id) 
	{
		parameters = new Hashtable();
		dataWrapper = new SummaryWrapper(cc, session_id);
		qryModel = new SampleQryModel();
	}

	public boolean isClear()
	{
		return ((SummaryWrapper)dataWrapper).isClear();
	}

	public  int clear()
	{
		dataWrapper.clear();
		return super.clear();
	}
	
	public int update(CombinedCriteria cc)
	{
		clear();
	 	int i =((SummaryWrapper)dataWrapper).update(cc);
		return i;
	}
 

	public String getSamples(){
		if (isSet(SAMPLEIDs))
			return getValuesAsStr(SAMPLEIDs);
		else 
		{
			Vector temp = ((DataSet)getWrapper().getControlList(SAMPLEIDs)).getValues();
			String ret = "";
			if (temp != null)
				for (int i = 0; i< temp.size(); i++)
				{
					if (i==0)
						ret += (String)temp.elementAt(i);
					if (i!=0)
						ret += ", " + (String)temp.elementAt(i);
				}
		 	return "(" +ret +")";
		}
	}

}

class SampleQryModel extends QueryModel
{
        public String getQueryStr(Criteria criteria)
        {

        String query =  "SELECT s.sample_num FROM sample s"
                + " WHERE s.sample_num IN "
                + criteria.getValuesAsStr(SampleCriteria.SAMPLEIDs);
        return query;
        }
        

	public String getQueryStr(Criteria criteria, String filter)
        {
		return getQueryStr(criteria);
	}

}

