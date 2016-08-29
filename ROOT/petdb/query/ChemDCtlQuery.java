
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class ChemDCtlQuery extends DynamicCtlQuery 
{
	private ChemistryCriteria cc;

	public ChemDCtlQuery(String filter)
	{ 
		super(filter); 
	}

	public ChemDCtlQuery(ChemistryCriteria cc, String filter)
	{ 
	        super();
                v_filter = filter;
		this.cc = cc;	
                int t = setFilter();
	}

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new AValuePerKeyDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

        public int updateData(ChemistryCriteria cc, String filter)
        {
                if (!v_filter.equals(filter))
                {
                        v_filter = filter;
                        changed = true;
                        this.cc = cc;
			return setFilter();
                }
                        else return -1;
        }

	protected int setFilter()
	{
		
		qry = cc.getChemistryQry(v_filter);
		
		//System.out.println("ChemDCtlQuery ====>"+qry);
		return 1;
	} 	  
}
