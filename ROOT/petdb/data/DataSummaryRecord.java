package petdb.data;

import java.util.*; 
import java.sql.*; 

public class DataSummaryRecord extends Record
{
	public static int GLASS		=0;
	public static int WHOLEROCK	=1; 
	public static int INCLUSION	=2; 
	public static int GLASS_INC	=3; 
	public static int MIN_INC	=4; 
	public static int MINERAL	=5; 
	public static int ROCKMODE	=6; 
	public static int FLUID	    =7; //Fluid Inclusion
	
	
	public DataSummaryRecord(ResultSet rs, int count)
        {
                super(rs, count);
        }

	protected Object value(int index, String  val)
	{
		return super.value(index,val);
	}


	public String getValue(int index)
	{
		return (String)record.elementAt(index); 
	}

}

