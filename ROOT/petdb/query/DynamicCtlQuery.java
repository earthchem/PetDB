
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class DynamicCtlQuery extends ControlQuery implements Cloneable 
{
/*	protected DataSet ds; */ /* FIXME(LS): This is confusing because ControlQuery already declare DataSet ds. 
	                                    This will shadow the object in the supper class.
	                       */
	protected Criteria criteria;
  	protected String v_filter = "";
    protected boolean changed = true;
    protected String ageCondition = "";
	
	public DynamicCtlQuery()
	{ }

        public DynamicCtlQuery(Criteria c)
        {
                super();
                setCriteria(c);
        }

        public DynamicCtlQuery(String filter)
        {
                super();
                v_filter = filter;
                int t = setFilter();
        }
        
        public DynamicCtlQuery(String filter, String ageCondition)
        {
                super();
                this.ageCondition = ageCondition;
                v_filter = filter;
                int t = setFilter();
               
        }
        
	
	protected int setFilter() { return 1;}

	public String getFilter() {return v_filter;}
 
        public int updateData(String filter)
        {
                if (!v_filter.equals(filter))
                {
                       	v_filter = filter;
                       	changed = true;
                       	return setFilter();
                }
			else return -1;
        }

	public int setCriteria(Criteria c)
	{
		criteria = c;
		changed = ((criteria != null) && criteria.isSet());
		return setQuery();
	}

	protected int setQuery() 
	{
		qry = criteria.getQryStr();
		return 1; 
	}
	
        public synchronized DataSet getDataSet()
        {
                if (changed)
                        if (runQuery() == 0) {
                                prepareData();
                                changed = false;
				closeQuery();
                                return ds;
                        }
                        else {
				closeQuery();
				return null;
			}
                else {
			closeQuery();
			return ds;
			}
        }

}

