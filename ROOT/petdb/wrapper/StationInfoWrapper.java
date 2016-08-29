
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class StationInfoWrapper extends Wrapper 
{

	DynamicCtlQuery dcq;
	DynamicCtlQuery dcq1;
	DynamicCtlQuery dcq2;
	DynamicCtlQuery dcq3;

	/* Pass-in string 'str' is Sample_NUM or Sample_ID in Sample table. 
	 * Pass-in 'type' tells which one it is. It is either StationInfo1DCtlQuery.NUM
	 * or StationInfo1DCtlQuery.ID
	 */
	public StationInfoWrapper(String str, String type)
	{
                controlLists = new Vector();
                dcq = new StationInfo1DCtlQuery(str, type);
                dcq1 = new StationInfo3DCtlQuery(str, type);
                dcq2= new StationInfo2DCtlQuery(str, type);
                dcq3= new StationExpedInfo1DCtlQuery(str, type);
		int t = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
                	controlLists.add(dcq.getDataSet());
                	controlLists.add(dcq1.getDataSet());
                	controlLists.add(dcq2.getDataSet());
                	controlLists.add(dcq3.getDataSet());
        		return 1;
		}
	}

        public int update(String str)
        {
		synchronized(controlLists)
		{
			
			if  ( ((StationInfo1DCtlQuery)dcq).getFilter().equals(str) )
				return 1;
                	if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
                	((StationInfo1DCtlQuery)dcq).updateData(str);
                	((StationInfo3DCtlQuery)dcq1).updateData(str);
                	((StationInfo2DCtlQuery)dcq2).updateData(str);
                	((StationExpedInfo1DCtlQuery)dcq3).updateData(str);
                	controlLists.add(dcq.getDataSet());
                	controlLists.add(dcq1.getDataSet());
                	controlLists.add(dcq2.getDataSet());
                	controlLists.add(dcq3.getDataSet());
			return 1;
		}
	}

        public String toString()
        {
                return super.toString() + "\n" +  dcq.toString() + " \n" +  dcq1.toString();
        }

	public int closeQueries()
        {
                if (dcq != null) dcq.close();
                if (dcq1 != null) dcq1.close();
                if (dcq2 != null) dcq2.close();
                if (dcq3 != null) dcq3.close();
                return 1;
        }



}

