
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByMineralWrapper extends ByChemistryWrapper 
{

	DynamicCtlQuery dcq1;
	public static String Mineral_List = "1";

	public ByMineralWrapper(String str)
	{
		super(str, DataDCtlQuery.Mineral);
                dcq1 = new MineralDCtlQuery(str,MineralDCtlQuery.Mineral);
		int t = populate(dcq1);
	}

        protected int populate(DynamicCtlQuery dcq1)
        {
		synchronized(controlLists)
		{
                	controlLists.add(dcq1.getDataSet());
        		return 1;
		}
	}

        public int update(String str)
        {
		int  t = super.update(str);
		if (updated.equals("true"))
		{
                	((MineralDCtlQuery)dcq1).updateData(str);
                	controlLists.add(dcq1.getDataSet());
		}
		return t;
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

