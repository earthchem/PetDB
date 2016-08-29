
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ReferenceInfoWrapper extends Wrapper 
{

	DynamicCtlQuery dcq;
	DynamicCtlQuery dcq1;

	public ReferenceInfoWrapper(String str)
	{
                controlLists = new Vector();
                dcq = new ReferenceInfo1DCtlQuery(str);
                dcq1 = new ReferenceInfo2DCtlQuery(str);
		int t = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
                	controlLists.add(dcq.getDataSet());
                	controlLists.add(dcq1.getDataSet());
        		return 1;
		}
	}

        public int update(String str)
        {
		synchronized(controlLists)
		{
			
			if  ( ((ReferenceInfo1DCtlQuery)dcq).getFilter().equals(str) )
				return 1;
                	if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
                	((ReferenceInfo1DCtlQuery)dcq).updateData(str);
                	((ReferenceInfo2DCtlQuery)dcq1).updateData(str);
                	controlLists.add(dcq.getDataSet());
                	controlLists.add(dcq1.getDataSet());
			return 1;
		}
	}

        public String toString()
        {
                return super.toString() + "\n" +  dcq.toString() + " \n" + dcq1.toString();
        }

	public int closeQueries()
        {
                if (dcq != null) dcq.close();
                if (dcq1 != null) dcq1.close();
                return 1;
        }



}

