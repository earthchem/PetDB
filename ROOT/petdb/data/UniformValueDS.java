package petdb.data;

import java.util.*;
import java.sql.*;
import petdb.config.*;

/* Extends from IndexedDS who Contains two Vectors, index Vector and data Vector. 
 * The index Vector contains all the keys to the data Vector.
 * This class will fill in two vectors from ResultSet
 */
public class UniformValueDS  extends IndexedDS {
	
		
	public UniformValueDS(ResultSet rs) {
		super();
		int i = buildDS(rs);
	}

	
	protected int buildDS(ResultSet rs) {
		
	 synchronized (index){	
		String key = "";
		String prv_key = "";
		Vector value;
		
		try {
			int counter = -1;
			while (rs.next()) 
			{
				key = rs.getString(1);
				if (!prv_key.equals(key)) {
					counter ++;
					index.add(key); 
					data.add(new Vector());
					prv_key = key;
				}
				value = (Vector)data.elementAt(counter);
				value.add(value(rs));
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
	
	/* Get Value form second column of ResultSet */
	protected Object value(ResultSet rs) throws Exception
	{
		return rs.getString(2); 

	}

	/* When data stored in data vector is also a vector, the function will return a string which 
	 * consist of by every value seperated by ',' comma. 
	 */
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

