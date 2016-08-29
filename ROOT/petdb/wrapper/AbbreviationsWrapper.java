
package petdb.wrapper;

import java.util.*;

import petdb.data.*;
import petdb.query.*;
import petdb.criteria.*;

public class AbbreviationsWrapper extends Wrapper 
{
	static ControlQuery[] queries = new ControlQuery[8];
	static boolean status = false;
	
	public static int Alteration 	= 0;
        public static int RockType   	= 1;
        public static int Crystal	= 2;
        public static int Material	= 3;
        public static int Mineral 	= 4;
        public static int Method	= 5;
        public static int Navigation	= 6;
        public static int SamplingTech 	= 7;
 
        //public static int RimCore	= "5";
	
 	static private AbbreviationsWrapper aw_instance;
	
	public static AbbreviationsWrapper getAbbreviationsWrapper()
	{
		if (aw_instance ==null)
			aw_instance = new AbbreviationsWrapper();
		return aw_instance; 
	}
	
	protected AbbreviationsWrapper()
        {
		controlLists 		= new Vector();
		queries[Alteration] 	= new AlterationCtlQuery();
		queries[RockType] 	= new RockTypeCtlQuery();
		queries[Crystal] 	= new CrystalCtlQuery(); 
		queries[Material] 	= new MaterialCtlQuery(); 
		queries[Mineral] 	= new MineralCtlQuery();
		queries[Method] 	= new MethodCtlQuery();  
		queries[Navigation] 	= new NavigationCtlQuery(); 
		queries[SamplingTech] 	= new RockMethodCtlQuery();
		
                int t = populate();
        }

        protected int populate()
        {
                synchronized(controlLists)
                {
			for (int i=0; i<queries.length; i++)
                        	controlLists.add( ((ControlQuery)queries[i]).getDataSet());
                        return 1;
                }
        }


}
