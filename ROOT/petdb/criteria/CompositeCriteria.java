
package petdb.criteria;

import java.util.*;
import petdb.query.*;
import petdb.wrapper.*;

public abstract class  CompositeCriteria extends Criteria
{
	Criteria subCriteria;

	public Wrapper getInnerWrapper()
	{
		return subCriteria.getWrapper();

	}

	public Criteria getSubCriteria()
	{
		return subCriteria;

	}

	public abstract Wrapper getWrapper();

	public int clear()
	{
		int t = super.clear();
		return subCriteria.clear();
	}
}


