
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class MethodInfoWrapper extends Wrapper 
{

	DynamicCtlQuery dcq1;
	DynamicCtlQuery dcq2;
	DynamicCtlQuery dcq3;
	DynamicCtlQuery dcq4;
	DynamicCtlQuery dcq5;
	DynamicCtlQuery dcq6;
	
	public MethodInfoWrapper(String str)
	{
                controlLists = new Vector();
                dcq1 = new MethodInfo1DCtlQuery(str);
                dcq2 = new MethodInfo2DCtlQuery(str);
                dcq3 = new MethodInfo3DCtlQuery(str);
                dcq4 = new MethodInfo4DCtlQuery(str);
                dcq5 = new MethodInfo5DCtlQuery(str);
                dcq6 = new MethodInfo6DCtlQuery(str);
		int t = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
                	controlLists.add(dcq1.getDataSet());
                	controlLists.add(dcq2.getDataSet());
                	controlLists.add(dcq3.getDataSet());
                	controlLists.add(dcq4.getDataSet());
                	controlLists.add(dcq5.getDataSet());
                	controlLists.add(dcq6.getDataSet());
        		return 1;
		}
	}

        public int update(String str)
        {
		synchronized(controlLists)
		{
			
			if  ( ((MethodInfo1DCtlQuery)dcq1).getFilter().equals(str) )
				return 1;
                	if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
                	((MethodInfo1DCtlQuery)dcq1).updateData(str);
                	((MethodInfo2DCtlQuery)dcq2).updateData(str);
                	((MethodInfo3DCtlQuery)dcq3).updateData(str);
                	((MethodInfo4DCtlQuery)dcq4).updateData(str);               
                	((MethodInfo4DCtlQuery)dcq5).updateData(str);
                	((MethodInfo6DCtlQuery)dcq6).updateData(str);
                	controlLists.add(dcq1.getDataSet());
                	controlLists.add(dcq2.getDataSet());
                	controlLists.add(dcq3.getDataSet());
                	controlLists.add(dcq4.getDataSet());
                	controlLists.add(dcq5.getDataSet());
                	controlLists.add(dcq6.getDataSet());
			return 1;
		}
	}

        public String toString()
        {
                return super.toString() + "\n" +  dcq4.toString() + " \n" +  dcq5.toString();
        }
	
	public int closeQueries()
        {
                if (dcq1 != null) dcq1.close();
                if (dcq2 != null) dcq2.close();
                if (dcq3 != null) dcq3.close();
                if (dcq4 != null) dcq4.close();
                if (dcq5 != null) dcq5.close();
                if (dcq6 != null) dcq6.close();
                return 1;
        }



}

