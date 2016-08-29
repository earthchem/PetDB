
package petdb.wrapper;

import java.util.*;

import petdb.data.*;
import petdb.query.*;
import petdb.config.*;
import petdb.criteria.*;

public class SummaryWrapper extends Wrapper 
{
	SummaryCQuery scq;
	boolean sessionparam_build = false;
	String session_id ="";
  	int delete_r = -1;
	int insert_r = -1;

	public SummaryWrapper(CombinedCriteria cc, String s)
	{
                controlLists = new Vector();
		session_id = s;
                if (cc.isSet()) 
			 scq = new SummaryCQuery(cc);
		else 
			 scq = new SummaryCQuery();
		int ret = populate();
		
	}

	public boolean isClear()
	{
		return (controlLists.size() == 0 ? true : false); 

	}

	
	public String getQryStr(boolean session)
	{
		if (controlLists != null)
		{
			CriteriaDS ds = (CriteriaDS)getControlList("0");
			if (ds.getCount()>1000)
			{
				if (session) 
				{
					if (!sessionparam_build)
					{
						buildSP();
					} 
					return getQry();
				} else {
					if (sessionparam_build)
						return getQry();
					else  
						return scq.getQryStr();
				}
			}	
			else 
				return ds.getSampleIDs();
		}
		return "";
	}
    
    public void setTagAlongSampleNum(String itemCodes)
	{
		if (controlLists != null)
		{
			CriteriaDS ds = (CriteriaDS)getControlList("0");
			if (ds.getCount()>1000)
			{
                    DeleteQuery dq = new DeleteQuery(session_id, true);
                    delete_r = dq.runQuery();
                    InsertQuery iq = new InsertQuery(session_id, itemCodes,true);
                    insert_r = iq.runQuery();
            } 
		}
    }	


	public boolean isBuild(){return sessionparam_build;} 
    

	private void buildSP()
	{
		DeleteQuery dq = new DeleteQuery(session_id);
		delete_r = dq.runQuery();
		InsertQuery iq = new InsertQuery(session_id, scq.getQryStr());
		insert_r = iq.runQuery();
		sessionparam_build = true;
		//return r;
	}
    
    public String getTagAlongSampleNum()
	{
        if (controlLists != null)
		{
			CriteriaDS ds = (CriteriaDS)getControlList("0");
			if (ds.getCount()>1000)
			{
                   return "TO_BE_REPLACED.sample_num IN ("
			+ " select param_value from sessionparam "
			+ " where param_name = 'sample_num_ta'"
			+ " and session_id = '" + session_id + "'"
			+ " )";
            } 
		}
        return null;
	}  

	private String getQry()
	{
		return DisplayConfigurator.toBeReplaced + ".sample_num in ("
			+ " select param_value from sessionparam "
			+ " where param_name = 'sample_num'"
			+ " and session_id = '" + session_id + "'"
			+ " )";
	}  

	protected void finalize(){
		DeleteQuery dq = new DeleteQuery(session_id);
		dq.runQuery();
	}

	public String getQryStr()
	{
		if (controlLists != null)
		{
			CriteriaDS ds = (CriteriaDS)getControlList("0");
			if (ds.getCount()>1000)
				return scq.getQryStr();	
			else 
				 return ds.getSampleIDs();
		}
		return "";
	}


	protected int populate()
	{
		synchronized(controlLists)
		{
			//System.out.println("SummaryWrapper add to controlLists scq="+scq.getClass().toString());
            controlLists.add(scq.getDataSet());
			return 1;
        	}
	}

        public  int update(CombinedCriteria cc)
        {
		synchronized(controlLists)
		{
                	if (controlLists.size() != 0 ) controlLists.remove(0);
                	scq.setCombinedCriteria(cc);
			sessionparam_build = false;
                	controlLists.add(scq.getDataSet());
			return 1;
		}

        }

	public String toString()
	{
		return "Delete = " + delete_r + " Insert = " + insert_r
			+  " " + scq.toString();
	
	}

	public int closeQueries()
	{
		if (scq != null) scq.close();
		return 1;
	}

	
}

