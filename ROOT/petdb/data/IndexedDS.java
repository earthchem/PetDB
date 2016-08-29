package petdb.data;

import java.util.*; 

/* Contains two Vectors, index Vector and data Vector. The index Vector contains all the keys to the data Vector. It implements DataSet. */
public abstract class IndexedDS implements DataSet {
	//FIXME: Why not use hashtable
	protected Vector index;
	protected Vector data;
	
	public IndexedDS()
	{
		index = new Vector();
		data = new Vector();
	}

    public Vector getKeys() 
	{
		synchronized (index)
		{
            return (index != null ? index : null);
		}
     }


    public Object getValue(String key)
	{

		synchronized (index){
			int d = (index != null ? index.indexOf(key) : -1);
			if (d >= 0 ) 
                		return  (data != null ? data.elementAt(d) : null);
        		else return null;
		}
	}

	public Vector getValues() 
	{
		
		synchronized (data){
			return (data != null ? data : null);
		}
	}

	public String getKeyAt(String s_index)
	{
		synchronized(index)
		{
		return (String)index.elementAt(Integer.decode(s_index).intValue()-1);
		}
	}


}

