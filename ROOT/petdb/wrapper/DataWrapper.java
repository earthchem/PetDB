
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class DataWrapper extends Wrapper 
{

	public static String ByDataCriteria = CombinedCriteria.ByDataCriteria;
	public static String ByMineralCriteria = CombinedCriteria.ByMineralCriteria;
	public static String ByInclusionCriteria = CombinedCriteria.ByInclusionCriteria;
	public static String ByRockModeCriteria = CombinedCriteria.ByRockModeCriteria;
	public static String ByMeltInclusionCriteria = CombinedCriteria.ByMeltInclusionCriteria;
	public static String ByFluidInclusionCriteria = CombinedCriteria.ByFluidInclusionCriteria;
	private String criteria_type = ByDataCriteria;
	String updated = "true";
	DynamicCtlQuery dcq1;

	public DataWrapper(String str)
	{
                controlLists = new Vector();
                dcq1 = new DataSummaryDCtlQuery(str);
		int t = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
                	controlLists.add(dcq1.getDataSet());
        		return 1;
		}
	}

	public void initWrapper()
	{
		criteria_type = ByDataCriteria;
	}

	public String getCriteriaType(){ return criteria_type;}
	public void setCriteriaType(String t){ criteria_type = t;}

        public int update(String str)
        {
		synchronized(controlLists)
		{
			
			/*	if  ( ((DataSummaryDCtlQuery)dcq1).getFilter().equals(str) ){
				updated = "false";
				return 1;
			}
			*/

			updated = "true";
			criteria_type = ByDataCriteria;
                	if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
                	((DataSummaryDCtlQuery)dcq1).updateData(str);
                	controlLists.add(dcq1.getDataSet());
			return 1;
		}
	}

        public String toString()
        {
                return updated + " =UPDATED " + super.toString() + "\n" +  dcq1.toString() + " Type = " + criteria_type;
        }

	public int closeQueries()
	{
		//if (dcq1 != null) dcq1.close();
		return 1;
	}
}

