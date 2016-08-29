
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class FinalSampleWrapper extends Wrapper 
{

	DynamicCtlQuery dcq;

	public FinalSampleWrapper(Criteria c, String filter)
	{
                controlLists = new Vector();
		dcq = new FinalSampleDCtlQuery(c,filter);
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

        public int update(Criteria c, String filter)
        {
		synchronized(controlLists)
		{
                	if (controlLists.size() != 0 ) controlLists.remove(0);
                	((FinalSampleDCtlQuery)dcq).updateData(c,filter);
                	controlLists.add(dcq.getDataSet());
			return 1;
		}
	}

        public int update(String filter)
        {
		synchronized(controlLists)
		{
                	if (controlLists.size() != 0 ) controlLists.remove(0);
                	((FinalSampleDCtlQuery)dcq).updateData(filter);
                	controlLists.add(dcq.getDataSet());
			return 1;
		}
	}

        public String toString()
        {
                return dcq.toString();
        }
      
	protected void finalize()
        {
//		        System.out.println("Finalizing: close queries");
                int i = closeQueries();
        }


	public int closeQueries()
	{
		if (dcq != null) dcq.close();
		return 1;
	}


}

