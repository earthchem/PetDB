
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByRockModeWrapper extends ByChemistryWrapper 
{
	public static String  Glass = "MIN";

	public ByRockModeWrapper(String str)
	{
		super(str,DataDCtlQuery.RockMode);
	}

	public boolean isRelevant(DataRecord dr)
        {
                return (
                         (selected.length() == 0 )
                         ||
                         (((DataRecord)dr).isRelevantToAny(selected))
                        );
        }

	

}

