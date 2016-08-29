
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByChemistryWrapper extends Wrapper 
{

	String updated = "true";
	DynamicCtlQuery dcq;
	String selected = "";

	public ByChemistryWrapper(String str, String type)
	{
        controlLists = new Vector();               
        // System.out.println("ByChemistryWrapper =====> str="+str+" type="+type);
        dcq = new DataDCtlQuery(str, type);
		int t = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
                	controlLists.add(dcq.getDataSet());
        		return 1;
		}
	}

        public int update(String str)
        {
		synchronized(controlLists)
		{
			
			/*
			if  ( ((DataDCtlQuery)dcq).getFilter().equals(str) ){
				updated = "false";
				return 1;
			}
			*/
				updated = "true";
                	if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
                	((DataDCtlQuery)dcq).updateData(str);
                	controlLists.add(dcq.getDataSet());
			return 1;
		}
	}

	public String getSelected()
	{
		return selected;
	}
			
	public void setSelected(String s)
        {
                selected = s;
        }

        public boolean selected(String s)
        {
                return (selected.equals(s));
        }

	public boolean isRelevant(DataRecord dr)
        {
                return (
			 (selected.length() == 0 )
			 ||
			 (((DataRecord)dr).isRelevantToAny(selected))
			);
        }


        public String toString()
        {
                return updated + " =UPDATED " + super.toString() + "\n" +  dcq.toString();
        }

        public int closeQueries()
        {
                if (dcq != null) dcq.close();
                return 1;
        }

}

