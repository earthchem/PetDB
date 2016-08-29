
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByPubInnerWrapper extends Wrapper 
{
	public ByPubInnerWrapper()
	{
		controlLists = new Vector();
		int t = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
			controlLists.add(new Pub_AuthCtlQuery().getDataSet());
			controlLists.add(new Pub_YearCtlQuery().getDataSet());
			controlLists.add(new Pub_JourCtlQuery().getDataSet());
			return 1;
		} 
	}
}

