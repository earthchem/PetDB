
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByGeoWrapper extends Wrapper 
{
	public ByGeoWrapper()
	{
		controlLists = new Vector();
		int t = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
			controlLists.add(new GeoCtlQuery().getDataSet());
			return 1;
		}
	}

	
}

