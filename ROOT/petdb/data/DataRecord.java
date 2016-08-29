package petdb.data;

import java.util.*; 
import java.sql.*; 

public class DataRecord extends Record
{
	public static int Name		=0;
	public static int ID		=1; 
	public static int Type 		=2; 

	/* Create a vector and fill the vector with contents from ResultSet. The vector called 'record' is inherited from class 'Record'
	 * The size of the vector will be 'count'. 
	 * The contents from ResultSet will start from 3rd column.
	 * 
	 * @rs: a Object of ResultSet which contains SQL search results.
	 * @count: Total number of columns in ResultSet will be stored in 'record' vector
	 * 
	 * Example: contents in the record look like the following
	 * 
	 * record[0]=AN
	 * record[1]=2
	 * record[2]=GL
	 * record[3]=35Y
	*/
	public DataRecord(ResultSet rs, int count)
        {
		super(rs,count,3);
	}


	protected Object value(int index, String  val)
	{
		return super.value(index,val);
	}

	public void update(int index, String value)
	{
		String v;
		v= (String)record.elementAt(index);
		record.remove(index);
		record.add(v + "," + value);
	}

	public boolean isRelevantToAny(String type)
	{
		String record_type = getValue(Type);
		boolean v = false;
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
				v = true;
				break;
			}	
     		}
 
		return v;
	}

	public boolean isRelevantToAll(String type)
	{
		String record_type = getValue(Type);
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


	public String getValue(int index)
	{
		return (String)record.elementAt(index); 
	}

	public String display()
	{
		return  ("N: "+ getValue(Name) + "ID: "+ getValue(ID) + "T: " + getValue(Type));
	}


}

