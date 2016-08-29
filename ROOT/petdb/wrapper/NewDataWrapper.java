
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class NewDataWrapper extends Wrapper 
{	
	DynamicCtlQuery dcq;
 
	public NewDataWrapper(String str, String type)
	{
		controlLists = new Vector();
		dcq = new ReferenceDCtlQuery(str, type);
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

	public String getQueryStr() {return dcq.getQueryStr();}

        public int update(String str)
        {
                synchronized(controlLists)
                {
                        if (controlLists.size() != 0 ) controlLists.remove(0);
                        ((ReferenceDCtlQuery)dcq).updateData(str);
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

