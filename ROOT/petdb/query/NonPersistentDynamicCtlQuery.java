
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class NonPersistentDynamicCtlQuery extends DynamicCtlQuery 
{
	public NonPersistentDynamicCtlQuery()
	{ 
		;
	}

        public NonPersistentDynamicCtlQuery(Criteria c)
        {
                super(c);
        }

        public NonPersistentDynamicCtlQuery(String filter)
        {
                super(filter);
        }
	
        public NonPersistentDynamicCtlQuery(String filter, String ageCondition)
        {
                super(filter, ageCondition);
        }
        
        
        public  synchronized DataSet getDataSet()
        {
                if (changed)
                        if (runQuery() == 0) {
                                prepareData();
                                changed = false;
                                return ds;
                        }
                        else return null;
                else return ds;
        }

}

