package petdb.data;

import java.util.*;
import java.sql.*;
//import petdb.query.*;

public class UniformKeyedValueDS  extends IndexedDS {
	
	
	Vector key4value = new Vector();
	
	public UniformKeyedValueDS(ResultSet rs) {
		super();
		int i = buildDS(rs);
	}

	
	protected int buildDS(ResultSet rs) {
		
	 synchronized (index){	
		String key = "";
		String prv_key = "";
		Vector value, k4v;
		//System.out.println("ResultSet = " + rs);
		try {
			int counter = -1;
			while (rs.next()) 
			{
				key = rs.getString(1);
				if (!prv_key.equals(key)) {
					counter ++;
					index.add(key); 
					data.add(new Vector());
					key4value.add(new Vector());
					prv_key = key;
				}
				value = (Vector)data.elementAt(counter);
				k4v = (Vector)key4value.elementAt(counter);
				value.add(value(rs));
				k4v.add(key4value(rs));
			}
			return 1;
		}
		catch (Exception e) 
		{
			System.out.println("Error at: " + e.getMessage());
			return -1;
		}
	 }
	}

	public String getValue4Key(String key)
	{
		int o_i = 0;
		int i_i = 0;
		String ret_val = key;
		boolean found = false;
		for (o_i =0; o_i< key4value.size(); o_i++)
		{
			Vector next = (Vector)key4value.elementAt(o_i);
			for (i_i=0; i_i<next.size(); i_i++)
			{
				if ( ((String)next.elementAt(i_i)).equals(key) )
				{  
					found = true;
					break;
				}
			}
			if (found) break;
		}
		if (found)
		{
			ret_val = (String)index.elementAt(o_i);
			Vector v = (Vector)data.elementAt(o_i);
			ret_val += (v != null ? ":"+(String)v.elementAt(i_i) : "");
		}
		return ret_val;
	}
	protected Object key4value(ResultSet rs) throws Exception
	{
		return rs.getString(3); 
	}

	protected Object value(ResultSet rs) throws Exception
	{
		return rs.getString(2); 
	}

	public String getStrKey4Value(String key)
	{
		String str ="";
		Vector v = (Vector) getKey4Value(key);
		if (v != null)
			for (int i=0; i< v.size(); i++)
				if (i == 0)
					str += (String)v.elementAt(i); 
				else 
					str += ", " + (String)v.elementAt(i); 
		return str;

	}

	public Object getKey4Value(String key)
	{
              synchronized (index){
                        int d = (index != null ? index.indexOf(key) : -1);
                        if (d >= 0 )
                                return  (key4value != null ? key4value.elementAt(d) : null);
                        else return null;
                }

	}

	public String getStrValue(String key)
	{
		String str ="";
		Vector v = (Vector) getValue(key);
		if (v != null)
			for (int i=0; i< v.size(); i++)
				if (i == 0)
					str += (String)v.elementAt(i); 
				else 
					str += ", " + (String)v.elementAt(i); 
		return str;
	}

}

