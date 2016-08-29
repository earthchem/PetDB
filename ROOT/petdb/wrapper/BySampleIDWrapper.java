
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class BySampleIDWrapper extends Wrapper 
{

	DynamicCtlQuery dcq;

	public BySampleIDWrapper(String str, String type)
	{
                controlLists = new Vector();
                dcq = new SampleIDDCtlQuery(str, type);
		int t = populate();
	}

    public BySampleIDWrapper(String str, String type, String match)
	{
                controlLists = new Vector();
                dcq = new SampleIDDCtlQuery(str, type, match);
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

        public int update(String str, String type)
        {
		synchronized(controlLists)
		{
                	if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
                	((SampleIDDCtlQuery)dcq).updateData(str,type);
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

