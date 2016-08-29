package petdb.data;

import java.util.*; 
import java.sql.*; 

/* Create a vector and fill the vector with contents from ResultSet. */
public abstract class Record
{ 
	protected Vector record;
	
	/* Create a vector and fill the vector with content from ResultSet. 
	 * The size of the vector will be 'count'. 
	 * The content from ResultSet will start from 'dth' column.
	 * @rs: a Object of ResultSet which contains SQL search results.
	 * @count: Total number of columns in ResultSet will be stored in 'record' vector
	 * @d:   To get column content from 'dth' column in ResultSet 'rs'
	*/
	public Record(ResultSet rs, int count, int d)
	{
		try 
		{
			record = new Vector(count);
			
			for (int i = 0; i< count; i ++)
			{
				record.add(i, value(i,rs.getString(i+d)));
			}
		 }
		catch (Exception e)
		{
			System.out.println("Error when reading the resultSet");
		}
		
	}
	
	/* Create a vector and fill the vector with content from ResultSet. 
	 * The size of the vector will be 'count'. 
	 * The content from ResultSet will start from  third column.
	 * @rs: a Object of ResultSet which contains SQL search results.
	 * @count: Total number of columns in ResultSet will be stored in 'record' vector
	*/
	public Record(ResultSet rs, int count)
	{
		try 
		{
			record = new Vector(count);
			
			for (int i = 0; i< count; i ++)
			{
				record.add(i, value(i,rs.getString(i+2)));
			}
		 }
		catch (Exception e)
		{
			System.out.println("Error when reading the resultSet");
		}
		
	}
	
	
	/* Overwriten by its children. If pass-in value is null, it will return a String with "0". Other wise a Vector object is returned.
	 * @index:  index for the 'record' Vector starting from 0
	 * @value:  value for the 'index'th item in the 'record' Vector
    */
	protected Object value(int index, String value)
	{
		if (value == null )
			return new String("0"); // If the search result is null, default to a string as "0".
		return value;		/* FIXME: Donot understand this --Lulin*/
	}
 
	public String display()
	{
		return "this Record";
	}

	public Object getColumn(int index)
	{
		return record.elementAt(index);
	}

    public void print()
    {
    	//for(int i=0;i<record.size();i++)
    	//  System.out.println("Record :: record["+i+"] ="+record.elementAt(i));
    }
}

