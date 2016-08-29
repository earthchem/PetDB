
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByExpInnerWrapper extends Wrapper 
{
	public ByExpInnerWrapper()
	{
		controlLists = new Vector();
		int t = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
			controlLists.add(new Exp_ExpCtlQuery().getDataSet());
			controlLists.add(new Exp_ShipCtlQuery().getDataSet());
			controlLists.add(new Exp_YearCtlQuery().getDataSet());
			controlLists.add(new Exp_ScieCtlQuery().getDataSet());
			controlLists.add(new Exp_InstCtlQuery().getDataSet());
			return 1;
		} 
	}
}

