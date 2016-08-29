
package petdb.wrapper;

import java.util.*;
import javax.servlet.http.*;

import petdb.data.*;
import petdb.query.*;
import petdb.criteria.*;

public abstract class Wrapper implements Cloneable, HttpSessionActivationListener 
{

	protected Vector controlLists; /* Store DataSet Implemented classes such as DataSummaryDS, etc. */
	
	protected int populate() { return 1;}
	
	public synchronized DataSet getControlList(String name) 
	{
		int i = -1;
		if ( (i = getListIndex(name)) != -1)
			return (DataSet)controlLists.elementAt(i);
		else return null;
	}

	public String toString()
	{
		return "Wrapper";
	}

	public void clear()
	{
		if (controlLists != null) controlLists.clear();
	}	
 
	public void initWrapper(){;}
        

	public String getLabelForKey(String o_index, String i_index)
        {
                DataSet t_DS = getControlList(o_index);
                return  (t_DS != null) ? t_DS.getKeyAt(i_index) : null;
        }

        public String getStrForKey(String o_index, String i_index)
        {
                DataSet t_DS = getControlList(o_index);
                return  (t_DS != null) ? t_DS.getStrValue(i_index) : null;
        }


	public int getListIndex(String name)
	{
		int i = -1;
		try {
			i = Integer.decode(name).intValue();
		} catch (Exception e){
			return  -1; 
		}
		return (controlLists.size() >i) ? i : -1;	 
	}


	public Object clone() throws CloneNotSupportedException
	{
		Wrapper w = (Wrapper)super.clone();
		w.controlLists = (Vector)controlLists.clone();
		
		for (int i=0; i < controlLists.size(); i++)
		{
			w.controlLists.setElementAt(controlLists.elementAt(i),i);
		}
		
		return w;
	}

	public int closeQueries() {return 1;}
	

	public void sessionDidActivate(HttpSessionEvent se) {;} 
 	
	public void sessionWillPassivate(HttpSessionEvent se) { closeQueries(); } 

}

