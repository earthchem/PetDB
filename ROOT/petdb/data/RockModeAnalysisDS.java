package petdb.data;

import java.util.*; 
import java.sql.*;

public class RockModeAnalysisDS extends RowBasedDS 
{

        public static int Rock_Mode_Num	 	=1;
	public static int Alias    		=2;
	public static int Sample_ID    		=3;
	public static int Reference 	    	=4;
        public static int Ref_Num 	 	=5;
        public static int Count 	 	=6;
        public static int Mineral_code 	 	=7;
        public static int Volume 		=8;

	public RockModeAnalysisDS(ResultSet rs)
	{
		super(rs);
	}

	public double getDoubleValue(int index) throws Exception
	{
		  
        String s = r_set.getString(index);    
        if(s != null)  return new Double(s).doubleValue();
        else return 0;
	}
	
	
}
