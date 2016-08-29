
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByExpWrapper extends Wrapper 
{

	DynamicCtlQuery dcq;

	public ByExpWrapper(Criteria c)
	{
                controlLists = new Vector();
                if (c instanceof ByChemistryCriteria)
		{
                	dcq = new ExpeditionDCtlQuery(((ByChemistryCriteria)c).getInnerQuery(),
						((ByChemistryCriteria)c).getOuterCondition());
		} 
		else 
		{
			dcq = new ExpDCtlQuery(c);
		}
		int t = populate();
	}

	public ByExpWrapper(String str)
	{
                controlLists = new Vector();
                dcq = new ExpeditionDCtlQuery(str);
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
                        if (controlLists.size() != 0 ) controlLists.remove(0);
                        ((ExpeditionDCtlQuery)dcq).updateData(str);
                        controlLists.add(dcq.getDataSet());
                        return 1;
                }
        }

        public int update(Criteria c)
        {
		synchronized(controlLists)
		{
                	if (controlLists.size() != 0 ) controlLists.remove(0);
			if (c instanceof ByChemistryCriteria)
                        	((ExpeditionDCtlQuery)dcq).updateData(((ByChemistryCriteria)c).getInnerQuery(),
									((ByChemistryCriteria)c).getOuterCondition());
                	else 
				dcq.setCriteria(c);
                	controlLists.add(dcq.getDataSet());
			return 1;
		}
	}

        public String toString()
        {
                return super.toString() + dcq.toString();
        }

	public Object clone() throws CloneNotSupportedException
	{
		ByExpWrapper bew = (ByExpWrapper)super.clone();
		return bew;
	}

        public int closeQueries()
        {
                if (dcq != null) dcq.close();
                return 1;
        }




}

