
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ExpeditionInfoWrapper extends Wrapper 
{

	DynamicCtlQuery dcq;

	public ExpeditionInfoWrapper(String str)
	{
                controlLists = new Vector();
                dcq = new ExpeditionInfoDCtlQuery(str);
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
			
			if  ( ((ExpeditionInfoDCtlQuery)dcq).getFilter().equals(str) )
				return 1;
                	if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
                	((ExpeditionInfoDCtlQuery)dcq).updateData(str);
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

