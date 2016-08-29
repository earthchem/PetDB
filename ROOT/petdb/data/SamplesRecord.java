package petdb.data;

import java.util.*; 
import java.sql.*; 

public class SamplesRecord extends Record
{
	public static int AUTH_ID	=0;
	public static int AUTH 		=1; 
	public static int YEAR 		=3; 
	public static int TITLE		=4; 

	public SamplesRecord(ResultSet rs, int count)
        {
                super(rs, count);
        }

	protected Object value(int index, String  val)
	{
		if ((index != AUTH_ID) && (index != AUTH))
			return super.value(index,val);
		else {
			Vector v = new Vector();
			v.add(val);
			return v;
		}
	}

	public void update(int index, String value)
	{
		Vector v;
		if ((v= (Vector)record.elementAt(index)) != null)
			v.add(value);
	}


	public String getValue(int index)
	{
		if ((index != AUTH_ID) && (index != AUTH))
			return (String)record.elementAt(index); 
		else {
			String val ="";
			Vector v = (Vector)record.elementAt(index);
			for (int i=0; i< v.size(); i++)
				if (i != 0)
					val += ", " + (String)v.elementAt(i);
				else val+= (String)v.elementAt(i);
			return val;
		}  	
	}

	public String display()
	{
		return (String)((Vector)record.elementAt(AUTH)).elementAt(0)
			+ ": " + (String)record.elementAt(YEAR) ;


	}


}

