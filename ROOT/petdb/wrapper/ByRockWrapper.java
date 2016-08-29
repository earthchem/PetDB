
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByRockWrapper extends Wrapper 
{
	public ByRockWrapper()
	{
		controlLists = new Vector();
		int t = populate();
	}

	protected int populate()
	{
		synchronized(controlLists)
		{
			controlLists.add(new Rock_ClassCtlQuery().getDataSet());
			controlLists.add(new Rock_MethodCtlQuery().getDataSet());
			controlLists.add(new Rock_AltCtlQuery().getDataSet());
			return 1;
		}
	}

	public String getStrForKey(String o_index, String i_index)
        {
		if (o_index.equals("0"))
		{
		
                	UniformKeyedValueDS ds = (UniformKeyedValueDS)getControlList(o_index);
		
               		return  (ds != null) ? ds.getValue4Key(i_index) : null;
        }
		else 
			return super.getStrForKey(o_index,i_index);
	}
 
	
}

