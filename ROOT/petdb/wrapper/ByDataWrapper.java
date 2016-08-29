
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByDataWrapper extends ByChemistryWrapper 
{
	public static String  Glass = "GL";
        public static String  Rock = "ROCK,WR";
        public static String  Both = "GL,ROCK,WR";

	public ByDataWrapper(String str)
	{
		super(str,DataDCtlQuery.Rock);
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

