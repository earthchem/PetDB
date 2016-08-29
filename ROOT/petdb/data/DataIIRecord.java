package petdb.data;

import java.util.*; 
import java.sql.*; 

public class DataIIRecord extends DataRecord
{
	public static int TypeII 	=3; //'item_code' is stored at index = 3 such as GL,FLUID etc.

	public DataIIRecord(ResultSet rs, int count)
        {
		super(rs,count);
		String v = (String)record.elementAt(TypeII); //FIXME Why. Useless variable.
	}


	protected Object value(int index, String  val)
	{
		return super.value(index,val);
	}

	public void update(int index, String value)
	{
		String v;
		v= (String)record.elementAt(index);
		if (value.equals(v)) return;
		record.setElementAt(value +","+ v,index);
	}

	public boolean isRelevantToAny(String type, int index)
	{
		//System.out.println("DataIIRecord :::: isRelevantToAny ::: checking if (" + getValue(index) + ")relevant to : " + type); 
		String record_type = getValue(index);
		boolean v = false;
		if ( 
			(record_type == null) 
			|| 
			(record_type.length() == 0) 
		) 	return false;
		

		if (type.length() == 1)
		{
			v = (record_type.indexOf(type) != -1);
		}
		else
		{
		StringTokenizer st = new StringTokenizer(type,",");
     		while (st.hasMoreTokens()) 
		{
         		String c = st.nextToken();
			if (
				(record_type.indexOf(","+c+",") > 0 )
				||
                                (
                                 (record_type.indexOf(","+c) == (record_type.length() - c.length() -1))
                                 &&
                                 (record_type.indexOf(","+c) != -1)
                                )
				|| 
				(record_type.indexOf(c + ",") == 0)
				||
				(record_type.equals(c))
			)
			{
				v = true;
				break;
			}	
     		}
 		}
		return v;
	}

	public boolean isRelevantToAll(String type, int index)
	{
		//System.out.println("DataIIRecord :::: isRelevantToAll ::: checking if (" + getValue(index) + ") relevant to : " + type); 
		String record_type = getValue(index);
		boolean v = true;
		if ( 
			(record_type == null) 
			|| 
			(record_type.length() == 0) 
		) 	return false;
		
		StringTokenizer st = new StringTokenizer(type,",");
     		while (st.hasMoreTokens()) {
         		String c = st.nextToken();
			if (
				(record_type.indexOf(","+c+",") > 0 )
				||
                                (
                                 (record_type.indexOf(","+c) == (record_type.length() - c.length() -1))
                                 &&
                                 (record_type.indexOf(","+c) != -1)
                                )
				|| 
				(record_type.indexOf(c + ",") == 0)
				||
				(record_type.equals(c))
			)
			{
				v = false;
				break;
			}	
     		}
 
		return v;
	}


	public String display()
	{
		return  ("N: "+ getValue(Name) + " ID: "
			+ getValue(ID) + " T: " + getValue(Type) + " TII: "
			+ getValue(TypeII));
	}

}

