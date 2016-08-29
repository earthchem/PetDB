
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByDataAvailWrapper extends Wrapper 
{
	public ByDataAvailWrapper()
	{
		controlLists = new Vector();
		int t= populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
			controlLists.add(new ItemTypeCtlQuery().getDataSet());
			controlLists.add(new MineralCtlQuery().getDataSet());
			return 1;
		}
	}	 
}
