
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByPubWrapper extends Wrapper 
{	
	DynamicCtlQuery dcq;  //COMPLETED
    DynamicCtlQuery dcq2; //IN_PROGRESS
    DynamicCtlQuery dcq3; //ALERT
 
	public ByPubWrapper(Criteria c)
	{
		controlLists = new Vector();
		if (c instanceof ByChemistryCriteria)
			dcq =  new ReferenceDCtlQuery(((ByChemistryCriteria)c).getInnerQuery(),
						((ByChemistryCriteria)c).getOuterCondition(),
						ReferenceDCtlQuery.Chemical);
		else {
            dcq = new PubDCtlQuery(c);
            Criteria c2 =(Criteria) c.clone();
            ((ByPub2Criteria)c2).setStatus(ByPub2Criteria.IN_PROGRESS);
			dcq2 = new PubDCtlQuery(c2);  
            
            Criteria c3 =(Criteria) c.clone();
            ((ByPub2Criteria)c3).setStatus(ByPub2Criteria.ALERT);
			dcq3 = new PubDCtlQuery(c3);  
       
        }
		int t = populate();
	}

	public ByPubWrapper(String str, String type)
	{
		controlLists = new Vector();
		dcq = new ReferenceDCtlQuery(str, type);
		int t = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
			controlLists.add(dcq.getDataSet());
            if(dcq2 != null) controlLists.add(dcq2.getDataSet());
            if(dcq3 != null) controlLists.add(dcq3.getDataSet());
			return  1;
		}
	}

	public String getQueryStr() {return dcq.getQueryStr();}

        public int update(String str)
        {
                synchronized(controlLists)
                {  
                        int size = controlLists.size();
                        for(int i = 0; i < size; i++) controlLists.remove(0);   
                        ((ReferenceDCtlQuery)dcq).updateData(str);
                        controlLists.add(dcq.getDataSet());
                        return 1;
                }
        }


	public int update(Criteria c)
	{
		synchronized (controlLists)
		{
            int size = controlLists.size();
            for(int i = 0; i < size; i++) controlLists.remove(0);   
        
            if (c instanceof ByChemistryCriteria)
				((ReferenceDCtlQuery)dcq).updateData(((ByChemistryCriteria)c).getInnerQuery(),
									((ByChemistryCriteria)c).getOuterCondition());
			else {
				 dcq.setCriteria(c);
                 controlLists.add(dcq.getDataSet());
                 
                 Criteria c2 =(Criteria) c.clone();
                ((ByPub2Criteria)c2).setStatus(ByPub2Criteria.IN_PROGRESS);
                 dcq2.setCriteria(c2);
                 controlLists.add(dcq2.getDataSet());		
                 
                 Criteria c3 =(Criteria) c.clone();
                ((ByPub2Criteria)c3).setStatus(ByPub2Criteria.ALERT);
                 dcq3.setCriteria(c3);
                 controlLists.add(dcq3.getDataSet());	
            }
			return 1;
		}
		
	}

	public String toString()
	{
		return super.toString() + dcq.toString();
	}

	public int closeQueries()
        {
                if (dcq != null) dcq.close();
                if (dcq2 != null) dcq2.close();
                if (dcq3 != null) dcq3.close();
                return 1;
        }
  
	
}

