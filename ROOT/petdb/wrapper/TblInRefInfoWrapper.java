
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class TblInRefInfoWrapper extends Wrapper 
{

	DynamicCtlQuery dcq;
	DynamicCtlQuery dcq1;
    DynamicCtlQuery dcq2;
	DynamicCtlQuery dcq3;
    
	public TblInRefInfoWrapper(String str)
	{
                controlLists = new Vector();
                dcq = new TblInRefInfo1DCtlQuery(str, " and it.item_type_code <> 'AGE'");
                dcq1 = new TblInRefInfo2DCtlQuery(str," and it.item_type_code <> 'AGE'");
                dcq2 = new TblInRefInfo1DCtlQuery(str," and it.item_type_code = 'AGE'");
                dcq3 = new TblInRefInfo2DCtlQuery(str," and it.item_type_code = 'AGE'");
		int t = populate2();
	}

    public TblInRefInfoWrapper(String str, boolean download)
	{
                controlLists = new Vector();
                dcq = new TblInRefInfo1DCtlQuery(str);
                dcq1 = new TblInRefInfo2DCtlQuery(str);
		int t = populate();
	}

        protected int populate()
        {
		synchronized(controlLists)
		{
                	controlLists.add(dcq.getDataSet());
                	controlLists.add(dcq1.getDataSet());
        		return 1;
		}
	}

         protected int populate2()
        {
		synchronized(controlLists)
		{
                	controlLists.add(dcq.getDataSet());
                	controlLists.add(dcq1.getDataSet());
                    controlLists.add(dcq2.getDataSet());
                	controlLists.add(dcq3.getDataSet());
        		return 1;
		}
	}
        

        public int update(String str)
        {
		synchronized(controlLists)
		{
			
			if  ( ((TblInRefInfo1DCtlQuery)dcq).getFilter().equals(str) )
				return 1;
                	if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
                	((TblInRefInfo1DCtlQuery)dcq).updateData(str);
                	((TblInRefInfo2DCtlQuery)dcq1).updateData(str);
                	controlLists.add(dcq.getDataSet());
                	controlLists.add(dcq1.getDataSet());
			return 1;
		}
	}

        public String toString()
        {
                return super.toString() + "\n" +  dcq.toString() + " \n" + dcq1.toString();
        }

	public int closeQueries()
	{
		dcq.close();	
		dcq1.close();	
        dcq2.close();	
		dcq3.close();	
		return 1;
	}


}

