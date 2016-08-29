package petdb.data;

import java.util.*; 
import java.sql.*;

public class SampleInfoAnalysisDS extends RowBasedDS 
{
    public static int Analysis_Num 	 	=1;
	public static int Reference    		=2;
	public static int Ref_Num    		=3;
	public static int Alias 	    	=4;
    public static int Method 	 	    =5;
    public static int Method_Num 	 	=6;
    public static int Material 	 	    =7;
    public static int Type_Code 		=8;
    public static int Item_Code 		=9;
    public static int Value			    =10;
    public static int Analysis_Comment	=11;
    
	public SampleInfoAnalysisDS(ResultSet rs)
	{
		super(rs);
	}

	public double getdoubleValue(int index) throws Exception
	{  
        String s = r_set.getString(index);    
        if(s != null)  return new Double(s).doubleValue();
        else return 0;
	}
	
	
}
