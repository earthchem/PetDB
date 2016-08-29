
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByTectWrapper extends Wrapper 
{
	public ByTectWrapper()
	{
		controlLists = new Vector();
		int t= populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
			controlLists.add(new TectCtlQuery().getDataSet());
			return 1;
		}
	}
	
}
