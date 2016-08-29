
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class MethodListWrapper extends Wrapper 
{	
	DynamicCtlQuery dcq;
 
	public MethodListWrapper(Criteria c)
	{
		controlLists = new Vector();
		dcq =  new MethodDCtlQuery(((ByChemistryCriteria)c).getInnerQuery(),((ByChemistryCriteria)c).getOuterCondition());
		int t = populate();
	}

	public MethodListWrapper(String str)
	{
		controlLists = new Vector();
		dcq = new MethodDCtlQuery(str);
		int t = populate();
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
                        ((MethodDCtlQuery)dcq).updateData(str);
                        controlLists.add(dcq.getDataSet());
                        return 1;
                }
        }


	public int update(Criteria c)
	{
		synchronized (controlLists)
		{
			if (controlLists.size() != 0 ) controlLists.remove(0);
                        if (c instanceof ByChemistryCriteria)
				((MethodDCtlQuery)dcq).updateData(((ByChemistryCriteria)c).getInnerQuery(),
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

	public int closeQueries()
        {
                if (dcq != null) dcq.close();
                return 1;
        }
  
	
}

