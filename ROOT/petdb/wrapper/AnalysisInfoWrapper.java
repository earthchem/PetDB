
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class AnalysisInfoWrapper extends Wrapper 
{

	DynamicCtlQuery dcq;
	DynamicCtlQuery dcq1;

	public AnalysisInfoWrapper(String str)
	{
                controlLists = new Vector();
                dcq = new AnalysisInfo1DCtlQuery(str);
                dcq1 = new AnalysisInfo2DCtlQuery(str);
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
			
			if  ( ((AnalysisInfo1DCtlQuery)dcq).getFilter().equals(str) )
				return 1;
                	if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
                	((AnalysisInfo1DCtlQuery)dcq).updateData(str);
                	((AnalysisInfo2DCtlQuery)dcq1).updateData(str);
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

