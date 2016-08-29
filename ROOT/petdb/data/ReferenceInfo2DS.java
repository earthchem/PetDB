package petdb.data;

import java.util.*; 
import java.sql.*;

public class ReferenceInfo2DS extends RowBasedDS 
{
	public static int Table_Title 		=1;
	public static int Table 	    	=2;
        public static int Table_Num 	 	=3;
        public static int Row_Count 		=4;
        public static int Item_Count 		=5;

	public ReferenceInfo2DS(ResultSet rs)
	{
		super(rs);
	}
	
}
