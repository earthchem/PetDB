
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class RockModeWrapper extends Wrapper 
{

	String updated = "true";
	DynamicCtlQuery dcq1;

	public RockModeWrapper(String str)
	{
                controlLists = new Vector();
                dcq1 = new RockModeDCtlQuery(str);
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

        public int update(String str)
        {
		synchronized(controlLists)
		{
			
			if  ( ((RockModeDCtlQuery)dcq1).getFilter().equals(str) ){
				updated = "false";
				return 1;
			}

			updated = "true";
                	if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
                	((RockModeDCtlQuery)dcq1).updateData(str);
                	controlLists.add(dcq1.getDataSet());
			return 1;
		}
	}

        public String toString()
        {
                return updated + " =UPDATED " + super.toString() + "\n" +  dcq1.toString();
        }

	public int closeQueries()
	{
		if (dcq1 != null) dcq1.close();
		return 1;
	}
}

