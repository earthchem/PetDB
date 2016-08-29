package petdb.data;

import java.util.*; 
import java.sql.*;

public class RockModeDS extends RowBasedDS 
{

        public static int RockMode_Num 	 	=1;
	public static int Alias     		=2;
	public static int Sample_ID     	=3;
        public static int Mineral 	 	=4;
        public static int Mineral_Volume 	=5;
        public static int Reference		=6;
        public static int Ref_Num 		=7;

	public RockModeDS(ResultSet rs)
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
