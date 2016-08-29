
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class StationListWrapper extends Wrapper 
{	
	DynamicCtlQuery dcq;
 
	public StationListWrapper(String f, String t)
	{
		controlLists = new Vector();
		dcq = new StationDCtlQuery(f,t); 
		int r = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
			controlLists.add(dcq.getDataSet());
			return  1;
		}
	}

        public int update(String str)
        {
                synchronized(controlLists)
                {
                        if (controlLists.size() != 0 ) controlLists.remove(0);
                        ((StationDCtlQuery)dcq).updateData(str);
                        controlLists.add(dcq.getDataSet());
                        return 1;
                }
        }


	public String toString()
	{
		return super.toString() + dcq.toString();
	}

        public int closeQueries()
        {
                if (dcq != null ) dcq.close();
                return 1;
        }
  
	
}

