package petdb.data;

import java.util.*; 
import java.sql.*; 

public class RockRecord extends Record
{
	public static int Label		=0;
	public static int ID 		=1; 

	public RockRecord(ResultSet rs, int count)
        {
                super(rs, count);
        }

	protected Object value(int index, String  val)
	{
		Vector v = new Vector();
		v.add(val);
		return v;
	}

	public void update(int index, String value)
	{
		Vector v;
		if ((v= (Vector)record.elementAt(index)) != null)
			v.add(value);
	}


	public String getValue(int index)
	{
		String val ="";
		Vector v = (Vector)record.elementAt(index);
		for (int i=0; i< v.size(); i++)
			if (i != 0)
				val += ", " + (String)v.elementAt(i);
			else val+= (String)v.elementAt(i);
		return val;
	}


}

