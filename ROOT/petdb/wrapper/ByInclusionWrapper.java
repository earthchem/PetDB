
package petdb.wrapper;

import java.util.*;

import petdb.criteria.*;
import petdb.data.*;
import petdb.query.*;

public class ByInclusionWrapper extends ByChemistryWrapper 
{

	DynamicCtlQuery dcq1;
	
	//public static String Glass = "GL";
	public static String Mineral = "MIN";
	public static String Fluid = "FLUID";
	public static String Melt = "MELT";
	
	//private String gl_selected = "";
	private String min_selected = "";
	private String melt_selected = "";
	private String fluid_selected = "";
    
	public ByInclusionWrapper(String str)
	{
		super(str, DataDCtlQuery.Inclusion);
		//dcq1 = new MineralDCtlQuery(str, MineralDCtlQuery.Host);
        //FIXME (LS) : change to following since it is Inclusion Wrapper why use Mineral Host type 
		dcq1 = new InclusionDCtlQuery(str, InclusionDCtlQuery.Inclusion);
		int t = populate(dcq1); //Add DataSet to controlLists
	}

	//public void setGLSelected(String s) { gl_selected = s;}
	//public String getGLSelected() { return gl_selected;}
	
	public void setMINSelected(String s) { min_selected = s;}
	public String getMINSelected() { return min_selected;}
        
	public void setFluidSelected(String s) { fluid_selected = s;}
	public String getFluidSelected() { return fluid_selected;}
	
	public void setMeltSelected(String s) { melt_selected = s;}
	public String getMeltSelected() { return melt_selected;}
    
	protected int populate(DynamicCtlQuery dcq1)
    {
		synchronized(controlLists)
		{
                	controlLists.add(dcq1.getDataSet());
        		return 1;
		}
	}

    public int update(String str)
    {
		int  t = super.update(str);
		if (updated.equals("true"))
		{
            ((InclusionDCtlQuery)dcq1).updateData(str);
            controlLists.add(dcq1.getDataSet());
		}
		return t;
	}		
	
        private boolean isRelevantToMIN(DataRecord dr)
        {
                return (
                         (min_selected.length() == 0 )
                         ||
                         (((DataIIRecord)dr).isRelevantToAll(min_selected,4))
                        );
        }
        private boolean isRelevantToFluid(DataRecord dr)
        {
                return (
                         (fluid_selected.length() == 0 )
                         ||
                         (((DataIIRecord)dr).isRelevantToAll(fluid_selected,4))
                        );
        }
//       private boolean isRelevantToGL(DataRecord dr)
//        {
//                return (
//                         (gl_selected.length() == 0 )
//                         ||
//                         (((DataIIRecord)dr).isRelevantToAny(gl_selected,3))
//                        );
//        }
       
       private boolean isRelevantToMelt(DataRecord dr)
       {
    	  // System.out.println(" melt_selected.length()= "+melt_selected.length());
    	  // System.out.println(" (((DataIIRecord)dr).isRelevantToAny(melt_selected,3)) = "+(((DataIIRecord)dr).isRelevantToAny(melt_selected,3)));
               return (
                        (melt_selected.length() == 0 )
                        ||
                        (((DataIIRecord)dr).isRelevantToAny(melt_selected,3))
                       );
       }

        public boolean isRelevant(DataRecord dr)
        {   
        	//System.out.println(" selected.length()= "+selected.length()+" selected="+selected);
        	//System.out.println(" (super.isRelevant(dr))= "+ super.isRelevant(dr) );
        	//dr.print();
            boolean rtn = false;
            if( selected.equals("MELT")) //melt is not a type in the database only GL and GLASS. In the Record only real type is stored.
            {  selected = "GL"; //hard-coded FIXME
            	rtn=(
                        (selected.length() == 0 )
                        ||
                        ( (super.isRelevant(dr))
				          &&
				          ( isRelevantToMelt(dr) )
                        )
                    );
            	if (rtn==false)
            	{
            		selected = "GLASS"; //hard-coded FIXME
                	rtn=(
                            (selected.length() == 0 )
                            ||
                            ( (super.isRelevant(dr))
    				          &&
    				          ( isRelevantToMelt(dr) )
                            )
                        );
            	}
            	selected = "MELT";//FIXME hard-coded put the original value back
            }
            else if ( selected.equals("FLUID"))
            {
            	rtn = (
                        (selected.length() == 0 )
                        ||
                        ( 	(  super.isRelevant(dr))
				            &&
				            (
				               isRelevantToFluid(dr)
				            )
                        )
                      );
            } else {
                rtn = (
                        (selected.length() == 0 )
                        ||
                        ( 	(  super.isRelevant(dr))
				            &&
				            (
				               isRelevantToMIN(dr)
				            )
                        )
                      );
            }
        
        	return  rtn;
        }

        public String toString()
        {
                return updated + " =UPDATED " + super.toString() + "\n" +  dcq1.toString();
        }
	
        public int closeQueries()
        {
                if (dcq1 != null ) dcq1.close();
                return 1;
        }


}