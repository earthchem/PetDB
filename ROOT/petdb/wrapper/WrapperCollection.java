
package petdb.wrapper;

import java.util.*;

import petdb.data.*;
import petdb.query.*;
import petdb.criteria.*;

public class WrapperCollection 
{

 	static Hashtable wrappers = new Hashtable();
	static boolean status = false;
	public static String ByGeoWrapper      = "ByGeoWrapper";
        public static String ByTectWrapper     = "ByTectWrapper";
        public static String ByExpInnerWrapper = "ByExpInnerWrapper";
        public static String ByPubInnerWrapper = "ByPubInnerWrapper" ;
        public static String ByRockWrapper 	= "ByRockWrapper" ;
        public static String DateEnteredWrapper	= "DateEnteredWrapper" ;
//Not used in anywhere else. Commented out by LS
//	public static int populateCollection()
//	{
//		synchronized(wrappers)
//		{
//			wrappers.put(ByGeoWrapper, new ByGeoWrapper());
//			wrappers.put(ByTectWrapper, new ByTectWrapper());
//			wrappers.put(ByExpInnerWrapper, new ByExpInnerWrapper());
//			wrappers.put(ByPubInnerWrapper,new ByPubInnerWrapper());
//			wrappers.put(ByRockWrapper, new ByRockWrapper());
//			wrappers.put(DateEnteredWrapper, new DateEnteredWrapper());
//			status = true;
//		}
//		return 1;
//	}
        
	public static boolean isPopulated(){ return status;}
	
	/* Clear everything in wrappers collection */
	public static void clear(){ wrappers.clear();}
	
	public static Wrapper get(String name)
	{
		synchronized(wrappers)
		{
			if (wrappers.containsKey(name))
				return (Wrapper)wrappers.get(name);
			else {

				if (name.equals(ByGeoWrapper))
					wrappers.put(ByGeoWrapper, new ByGeoWrapper());
				else if (name.equals(ByTectWrapper))
				    wrappers.put(ByTectWrapper, new ByTectWrapper());
				else if (name.equals(ByExpInnerWrapper))
					wrappers.put(ByExpInnerWrapper, new ByExpInnerWrapper());
				else if  (name.equals(ByPubInnerWrapper))
					wrappers.put(ByPubInnerWrapper,new ByPubInnerWrapper());
				else if (name.equals(ByRockWrapper))
					wrappers.put(ByRockWrapper, new ByRockWrapper());
				else if (name.equals(DateEnteredWrapper))
					wrappers.put(DateEnteredWrapper, new DateEnteredWrapper());
				else ;
				if (wrappers.containsKey(name))
					return (Wrapper)wrappers.get(name);
				else return null;
			}
		}
	}

}
