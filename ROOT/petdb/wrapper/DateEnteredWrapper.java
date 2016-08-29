
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class DateEnteredWrapper extends Wrapper 
{
	public DateEnteredWrapper()
	{
		controlLists = new Vector();
		int t= populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
			controlLists.add(new DateEnteredCtlQuery().getDataSet());
			return 1;
		}
	}
	
}
