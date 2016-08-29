
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class LocationsWrapper extends Wrapper 
{

	DynamicCtlQuery dcq;

	public LocationsWrapper(String str)
	{
                controlLists = new Vector();
                dcq = new LocationsDCtlQuery(str);
		int t = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
                	controlLists.add(dcq.getDataSet());
        		return 1;
		}
	}

        public int update(String str)
        {
		synchronized(controlLists)
		{
			
                	if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
                	((LocationsDCtlQuery)dcq).updateData(str);
                	controlLists.add(dcq.getDataSet());
			return 1;
		}
	}

        public String toString()
        {
                return super.toString() + "\n" +  dcq.toString() + " \n";
        }
       

	public int closeQueries()
        {
                if (dcq != null) dcq.close();
                return 1;
        }


}

