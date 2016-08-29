
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByChemWrapper extends Wrapper 
{

	DynamicCtlQuery dcq;

	public ByChemWrapper(ChemistryCriteria cc, String str)
	{
                controlLists = new Vector();
                dcq = new ChemDCtlQuery(cc, str);
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

        public int update(ChemistryCriteria cc, String str)
        {
		synchronized(controlLists)
		{
                	if (controlLists.size() != 0 ) controlLists.remove(0);
                	((ChemDCtlQuery)dcq).updateData(cc,str);
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
                if (dcq != null) dcq.close();
                return 1;
        }




}

