
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class FinalSampleDCtlQuery extends NonPersistentDynamicCtlQuery 
{
	String state ="";

	public FinalSampleDCtlQuery(Criteria c, String str) 
	{
		super();
		state =":: S conn = " + ( (dbConn == null) ? " null" : dbConn.toString());
		v_filter = str;
//		DataDCtlQuery.writeQueryToFile(v_filter,"C:\\Users\\Lulin Song\\Downloads\\finalsample_Vfilter.txt");
		setCriteria(c);
		int t = setFilter();
//		DataDCtlQuery.writeQueryToFile(qry,"C:\\Users\\Lulin Song\\Downloads\\finalsample_finalqryr.txt");
	}

	public int updateData(Criteria c, String str)
	{
		state =":: U conn = " + ( (dbConn == null) ? " null" : dbConn.toString());
		v_filter = str;
                setCriteria(c);
                int t = setFilter();
		return t;
	}


	public synchronized void prepareData() 
	{
		try {

			if (criteria instanceof ByDataCriteria)
			{
				if (! ((ByDataCriteria)criteria).getDataType().equals(ByDataCriteria.Compiled) )
					ds = (DataSet) new DataFSDS(rs, 
								((ByChemistryCriteria)criteria).getChemItemCount());
				else 
                {
					if (((ByChemistryCriteria)criteria).isORQuery())
                    {
                        ds = (DataSet) new VectorFSDS(rs, ((ByChemistryCriteria)criteria).getChemItemCount());
                    }
                    else if (((ByChemistryCriteria)criteria).isANDQuery())
                    {
                        ds = (DataSet) new VectorFSDSAndQuery(rs, ((ByChemistryCriteria)criteria).getChemItemCount());
                    }
                                
                }
			}else if (criteria instanceof ByMineralCriteria)
			{
				ds = (DataSet) new MineralFSDS(rs, 
					 			((ByChemistryCriteria)criteria).getChemItemCount());
			}
			else if (criteria instanceof ByInclusionCriteria) 
			{
				ds = (DataSet) new InclusionFSDS(rs, 
					 			((ByChemistryCriteria)criteria).getChemItemCount());
			}
			else 
			{
				ds = (DataSet) new RockModeFSDS(rs, 
					 			((ByChemistryCriteria)criteria).getChemItemCount());
			}
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = criteria.getQryStr(v_filter);
		return 1;
	}
 
       public  synchronized DataSet getDataSet()
        {
                if (changed)
                        if (runQuery(true) == 0) {
			state +=":: R conn = " + ( (dbConn == null) ? " null" : dbConn.toString());
                                prepareData();
                                changed = false;
			state +=":: E conn = " + ( (dbConn == null) ? " null" : dbConn.toString()); 
                                return ds;
                        }
                        else return null;
                else return ds;
        }

	public String toString()
	{
		return  qry; //state + " L conn = " + dbConn.toString();
	} 
 	  

}
