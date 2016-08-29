
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class SampleInfoWrapper extends Wrapper 
{

	DynamicCtlQuery dcq;
	DynamicCtlQuery dcq2;
	DynamicCtlQuery dcqR;
	DynamicCtlQuery dcqM;
	DynamicCtlQuery dcqI;
	DynamicCtlQuery dcqIGSN;
	
	/* pass-in string is Sample_NUM in Sample table */
	public SampleInfoWrapper(String str)
	{
        controlLists = new Vector();
        dcq = new SampleInfo1DCtlQuery(str);             /* DataSet: 1 */
        dcq2 = new SampleInfo3DCtlQuery(str);            /* DataSet: 2 */
        dcqR = new SampleInfoRockDCtlQuery(str);         /* DataSet: 3  */
        dcqM = new SampleInfoMinDCtlQuery(str);          /* DataSet: 4 */
        dcqI = new SampleInfoIncDCtlQuery(str);          /* DataSet: 5 */
        dcqIGSN = new IGSNDCtlQuery(str);                /* DataSet: 6 */
		int t = populate();
	}

    protected int populate()
    {
		synchronized(controlLists)
		{
            controlLists.add(dcq.getDataSet());
            controlLists.add(dcq2.getDataSet());
            controlLists.add(dcqR.getDataSet());
            controlLists.add(dcqM.getDataSet());
            controlLists.add(dcqI.getDataSet());
            controlLists.add(dcqIGSN.getDataSet());
            return 1;
		}
	}

    public int update(String str)
    {
		synchronized(controlLists)
		{
			
			if  ( ((SampleInfo1DCtlQuery)dcq).getFilter().equals(str) )
				return 1;
            if (controlLists.size() != 0 )
			{
				 controlLists.clear();
			}
            ((SampleInfo1DCtlQuery)dcq).updateData(str);
            ((StationInfo2DCtlQuery)dcq2).updateData(str);
            ((StationInfo2DCtlQuery)dcqR).updateData(str);
            ((StationInfo2DCtlQuery)dcqM).updateData(str);
            ((StationInfo2DCtlQuery)dcqI).updateData(str);
            ((IGSNDCtlQuery)dcqIGSN).updateData(str);
            int g =populate();
			return g;
		}
	}

    public String toString()
    {
        return super.toString() + "\n" +  dcq.toString() +
			 " \n" + dcq2.toString() +
			 " \n" + dcqR.toString() +
			 " \n" + dcqM.toString() +
			 " \n" + dcqI.toString() +
             " \n" + dcqIGSN.toString();
    }

	public int closeQueries()
    {
        if (dcq != null) dcq.close();
        if (dcq2 != null) dcq2.close();
        if (dcqR != null) dcqR.close();
        if (dcqM != null) dcqM.close();
        if (dcqI != null) dcqI.close();
        if (dcqIGSN != null) dcqIGSN.close();
        return 1;
   }
}

